package com.example.restaurant.controllers;

import com.example.restaurant.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.example.restaurant.utils.Utils.JSON_UTF_8;

@Controller
public class ItemController {

    private static final String ITEM_JSON = "src/main/resources/json/item.json";

    @RequestMapping(value = "/item", method = RequestMethod.POST, produces = JSON_UTF_8)
    @ResponseBody
    public String listItemsForFoodClass() {
        return Utils.readInput(ITEM_JSON);
    }
}
