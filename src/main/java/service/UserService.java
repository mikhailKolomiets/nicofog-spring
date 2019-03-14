package service;

import dao.CRUDbase;
import entity.Cigarette;
import entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by mihail on 11/10/18.
 */
@Service
public class UserService {

    @Autowired
    @Qualifier(value = "userHibernate")
    private CRUDbase<User> userRepository;
    private String resultMessage;

    /**
     * Bridge with controller and repository
     * Check by user exist, if issue -> null
     */
    public User addUser(User user) {

        user.setRole("user");
        user.setMoney(0);
        user.setDateRegistration(LocalDateTime.now().toString());
        if (userRepository.getByName(user.getName()) == null) {
            Cigarette cigarette = new Cigarette();
            cigarette.setLastSmokeTime(LocalDateTime.now());
            user.setCigarette(cigarette);
            user = userRepository.add(user);
        } else {
            resultMessage = "user " + user.getName() + " is exist";
            return null;
        }

        if (user == null) {
            resultMessage = "Can't add user";
        } else {
            resultMessage = user.getName() + " added success";
        }

        return user;
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public User getById(long id) {
        return userRepository.getById(id);
    }

    public User update(User user) {
        user = userRepository.update(user);
        resultMessage = user == null ?
                "User null can't update" :
                user.getRole();
        return user;
    }

    public User login(User user) {
        resultMessage = "";
        String userName = user.getName();
        String password = user.getPassword();
        user = getByName(userName);
        if (user == null) {
            resultMessage = "User " + userName + " no exist";
        } else {
            if (!user.getPassword().equals(password)) {
                resultMessage = "Incorrect password";
            } else {
                resultMessage = "ok";
                return user;
            }
        }
        return null;
    }

    public User getByName(String name) {
        return userRepository.getByName(name);
    }

    public void delete(long id) {
        resultMessage = userRepository.deleteById(id) ? "User deleted" : "User cant delete";
    }

    public String getResultMessage() {
        return resultMessage;
    }
}
