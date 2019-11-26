package com.example.restaurant.controllers;

import com.example.restaurant.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.example.restaurant.utils.Utils.JSON_UTF_8;

@Controller
public class UserController {

    private static final String USER_JSON = "src/main/resources/json/user.json";

    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = JSON_UTF_8)
    @ResponseBody
    public String listUsers() {
        return Utils.readInput(USER_JSON);
    }
}
