package com.example.restaurant.controllers;

import com.example.restaurant.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FoodClassController {

//    @PostMapping("/foodClass")
//    public ResponseEntity foodClassAction(@RequestParam String action) {
//
//        String input = Utils.readInput("src/main/resources/json/foodClass.json");
//
//        return ResponseEntity.ok(input);
//    }

    @RequestMapping(value = "/foodClass", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String listFoodClasses() {
        return Utils.readInput("src/main/resources/json/foodClass.json");
    }
}
