package controller;

import entity.Cigarette;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.CigaretteService;
import service.UserLevel;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * Created by mihail on 1/6/19.
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    CigaretteService cigaretteService;
    @Autowired
    HttpServletRequest request;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@ModelAttribute("login") User user) {
        user = userService.login(user);
        if (user != null) {
            request.getSession().setAttribute("login", user);
            request.getSession().setAttribute("next", nextDateLevel(user));
        }
        System.out.println("logining");
        return userService.getResultMessage();
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout() {
        request.getSession().setAttribute("login", null);
    }

    @ResponseBody
    @RequestMapping(value = "/user-add", method = RequestMethod.POST)
    public String registration(@ModelAttribute("registration") User user) {
        user = userService.addUser(user);

        if (user != null) {
            request.getSession().setAttribute("login", user);
            request.getSession().setAttribute("next", nextDateLevel(user));
            request.getSession().setAttribute("crud-result", userService.getResultMessage());
            return "ok";
        } else {
            return userService.getResultMessage();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        userService.delete(id);
    }

    @ResponseBody
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public String edit(@ModelAttribute("edit") User user) {
        user = userService.update(user);
        if (user != null) {
            request.getSession().setAttribute("login", user);
        }
        return userService.getResultMessage();
    }

    @RequestMapping(value = "/user-get-all", method = RequestMethod.GET)
    public String getAll() {
        request.getSession().setAttribute("all-users", userService.getAll());
        return "/admin-page";
    }

    @ResponseBody
    @RequestMapping(value = "/smoke", method = RequestMethod.GET)
    public void smoke() {
        User user = (User) request.getSession().getAttribute("login");
        Cigarette cigarette = cigaretteService.updateOnSmoke(user);
        user.setCigarette(cigarette);
        request.getSession().setAttribute("login", user);
        request.getSession().setAttribute("next", nextDateLevel(user));
    }

    private LocalDateTime nextDateLevel(User user) {
        LocalDateTime out = LocalDateTime.parse(user.getDateRegistration());
        UserLevel userLevel = UserLevel.ZERO.getByValue(user.getCigarette().getLevel());

        if (userLevel != UserLevel.NINE) {
            userLevel = userLevel.getByValue(userLevel.getValue() + 1);
            out = out.plusDays(userLevel.getDayAfterRegistration());
        }

        return out;
    }

}
