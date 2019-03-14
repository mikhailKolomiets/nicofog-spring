package dao.mysql;

import dao.CRUDbase;
import entity.Cigarette;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by mihail on 12/9/18.
 */
@Repository(value = "cigaretteMysql")
public class CigaretteRepository extends MySqlProvider implements CRUDbase<Cigarette> {

    private String throwableMessage;

    /**
     * Create a new data for count cigarettes for user
     * Return cigarette entity with id
     */
    public Cigarette add(Cigarette cigarette) {
        try {
            Statement statement = getConnection().createStatement();

            int key = statement.executeUpdate("INSERT INTO cigarette (last_smoke_time,cigarettes_per_day, average_time)" +
                    " VALUES ('" + LocalDateTime.now().toString() + "',0,0)", Statement.RETURN_GENERATED_KEYS);

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                cigarette.setId(resultSet.getInt(key));
            }
        } catch (SQLException e) {
            return null;
        }
        return cigarette;
    }

    public Cigarette getById(long id) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM cigarette WHERE id=?;");
            preparedStatement.setInt(1, (int) id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Cigarette cigarette = new Cigarette();
                cigarette.setId(id);
                cigarette.setLastSmokeTime(LocalDateTime.parse(resultSet.getString("last_smoke_time")));
                cigarette.setAverageTime(resultSet.getInt("average_time"));
                cigarette.setLevel(resultSet.getInt("level"));
                cigarette.setCigarettesPerDay(resultSet.getInt("cigarettes_per_day"));
                cigarette.setAllCigarettesSmoke(resultSet.getInt("all_cigarettes_smoke"));
                return cigarette;
            }
        } catch (SQLException e) {
            throwableMessage = e.toString();
        }
        return null;
    }

    public Cigarette update(Cigarette cigarette) {

        try {
            String sql = "UPDATE cigarette SET last_smoke_time='" + cigarette.getLastSmokeTime().toString() + "'," +
                    "average_time=" + cigarette.getAverageTime() + "," +
                    "level=" + cigarette.getLevel() + "," +
                    "cigarettes_per_day=" + cigarette.getCigarettesPerDay() + "," +
                    "all_cigarettes_smoke=" + cigarette.getAllCigarettesSmoke() + " " +
                    "WHERE id=" + cigarette.getId();
            getConnection().createStatement().execute(sql);
            return cigarette;

        } catch (Exception e) {
            throwableMessage = e.toString();
        }
        return null;
    }

    @Override
    public List<Cigarette> getAll() {
        return null;
    }

    public boolean deleteById(long id) {
        try {
            String sql = "DELETE FROM cigarette WHERE id=?";
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, (int) id);
            return preparedStatement.execute();
        } catch (Exception e) {
            throwableMessage = e.toString();
        }
        return false;
    }

    @Override
    public String getThrowableMessage() {
        return throwableMessage;
    }
}
