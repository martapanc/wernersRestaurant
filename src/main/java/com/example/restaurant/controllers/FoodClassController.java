package com.example.restaurant.controllers;

import com.example.restaurant.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.example.restaurant.utils.Utils.JSON_UTF_8;

@Controller
public class FoodClassController {

    private static final String FOOD_CLASS_JSON = "src/main/resources/json/foodClass.json";

    @RequestMapping(value = "/foodClass", method = RequestMethod.POST, produces = JSON_UTF_8)
    @ResponseBody
    public String listFoodClasses() {
        return Utils.readInput(FOOD_CLASS_JSON);
    }
}
