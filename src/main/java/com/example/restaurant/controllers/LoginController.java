package com.example.restaurant.controllers;

import com.example.restaurant.entity.User;
import com.example.restaurant.entity.UserSession;
import com.example.restaurant.utils.BCrypt;
import com.example.restaurant.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam String email, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {

        String userJson = Utils.readInput("src/main/resources/json/user.json");

        List<User> users = new Gson().fromJson(userJson, new TypeToken<List<User>>() {
        }.getType());
        User user = users.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);

        if (email != null && password != null) {
            User exampleEntity = new User();
            exampleEntity.setEmail(email);

            if (user != null && BCrypt.checkpw(password, user.getPassword())) {
                UserSession userSession = new UserSession();
                userSession.setUser(user);
                userSession.setLoginTime(new Date());
                session.setAttribute("userSession", userSession);

                return new ModelAndView("redirect:/homepage");
            }

            redirectAttributes.addFlashAttribute("ERROR_MESSAGE", "Login failed. Please check your credentials, or try to reset your password.");
            return new ModelAndView("redirect:/login");
        }

        redirectAttributes.addFlashAttribute("ERROR_MESSAGE", "Login failed: please retry.");
        return new ModelAndView("redirect:/login");
    }
}
