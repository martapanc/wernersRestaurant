package com.example.restaurant.controllers;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.example.restaurant.utils.ListLoader.userList;
import static com.example.restaurant.utils.Utils.JSON_UTF_8;

@Controller
public class UserController {

    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = JSON_UTF_8)
    @ResponseBody
    public String listUsers() {
        userList.forEach(user -> user.setRole(user.getRoleId()));
        return new Gson().toJson(userList);
    }
}
