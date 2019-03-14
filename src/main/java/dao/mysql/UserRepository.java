package dao.mysql;

import dao.CRUDbase;
import entity.Cigarette;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mihail on 11/9/18.
 */
@Repository(value = "userMysql")
public class UserRepository extends MySqlProvider implements CRUDbase<User> {

    @Autowired
    @Qualifier("cigaretteMysql")
    private CigaretteRepository cigaretteRepository;

    /**
     * Add user in base. Return null if user don't added, else - user
     */
    public User add(User user) {
        Cigarette cigarette = cigaretteRepository.add(new Cigarette());
        try {
            String prepareQuery = "INSERT INTO user (name, role, registration_date, password, cigarette_price, cigarette_id)" +
                    "  VALUES (?,?,?,?,?,?);";
            PreparedStatement ps = getConnection().prepareStatement(prepareQuery);
            ps.setString(1, user.getName());
            ps.setString(2, user.getRole());
            ps.setString(3, user.getDateRegistration());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getCigarettePrice());
            ps.setInt(6, (int) cigarette.getId());
            ps.execute();
            user = getByName(user.getName());
            user.setCigarette(cigarette);
            return user;
        } catch (SQLException e) {
            System.out.println("Cant add new user in base course" + e.toString());
        }
        return null;
    }

    public User getById(long id) {
        try {
            ResultSet resultSet = getConnection().createStatement().executeQuery("SELECT * FROM user WHERE id=" + id + ";");
            User user = new User();
            resultSet.next();
            user.setId(id);
            user.setName(resultSet.getString("name"));
            user.setRole(resultSet.getString("role"));
            user.setCigarettePrice(resultSet.getInt("cigarette_price"));
            user.setDateRegistration(resultSet.getString("registration_date"));
            Cigarette cigarette = cigaretteRepository.getById(resultSet.getInt("cigarette_id"));
            user.setCigarette(cigarette);
            return user;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Find user by name in base
     * Return it by name or null if not found
     */
    @Override
    public User getByName(String name) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * from user WHERE name=?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setRole(resultSet.getString("role"));
                user.setCigarettePrice(resultSet.getInt("cigarette_price"));
                user.setDateRegistration(resultSet.getString("registration_date"));
                user.setPassword(resultSet.getString("password"));
                Cigarette cigarette = cigaretteRepository.getById(resultSet.getInt("cigarette_id"));
                user.setCigarette(cigarette);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Execute update by exist user fields
     * Return null if something wrong, else user.
     */
    public User update(User user) {
        StringBuilder updateInfo = new StringBuilder();
        if (user.getName().length() > 0)
            updateInfo.append("name = '" + user.getName() + "'");
        if (user.getRole().length() > 0) {
            if (updateInfo.length() > 0)
                updateInfo.append(",");
            updateInfo.append("role = '" + user.getRole() + "'");
        }
        if (user.getCigarettePrice() != 0) {
            if (updateInfo.length() > 0)
                updateInfo.append(",");
            updateInfo.append("cigarette_price = " + user.getCigarettePrice());
        }

        try {
            String query = "UPDATE user SET " + updateInfo.toString() + " WHERE id=" + user.getId();
            getConnection().createStatement().execute(query);
            return getById(user.getId());
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Delete user for his id.
     * Return true if delete ok.
     */
    public boolean deleteById(long id) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("DELETE FROM user WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<User> getAll() {
        ArrayList<User> userList = new ArrayList<User>();

        try {
            ResultSet queryResult = getConnection().createStatement().executeQuery("SELECT * FROM user;");
            while (queryResult.next()) {
                User user = new User();
                user.setId(queryResult.getLong("id"));
                user.setName(queryResult.getString("name"));
                user.setRole(queryResult.getString("role"));
                user.setCigarettePrice(queryResult.getInt("cigarette_price"));
                user.setDateRegistration(queryResult.getString("registration_date"));
                Cigarette cigarette = cigaretteRepository.getById(queryResult.getInt("cigarette_id"));
                user.setCigarette(cigarette);

                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Can't get list of users");
        }

        return userList;
    }
}
