package com.example.restaurant.controllers;

import com.example.restaurant.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {

    @RequestMapping(value = "/item", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listItemsForFoodClass() {
        return Utils.readInput("src/main/resources/json/item.json");
    }
}
