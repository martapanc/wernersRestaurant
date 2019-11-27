package com.example.restaurant.controllers;

import com.example.restaurant.entity.FoodClass;
import com.example.restaurant.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.example.restaurant.utils.Utils.JSON_UTF_8;

@Controller
public class FoodClassController {

    private static final String FOOD_CLASS_JSON = "src/main/resources/json/foodClass.json";

    @RequestMapping(value = "/foodClass", method = RequestMethod.POST, produces = JSON_UTF_8)
    @ResponseBody
    public String listFoodClasses() {
        return Utils.readInput(FOOD_CLASS_JSON);
    }

    @GetMapping("/foodClassCRUD")
    @ResponseBody
    public String foodClassCRUD() {
        return "dashboard/crud/foodClassCRUD";
    }

    @RequestMapping(value = "/foodClass-find", method = RequestMethod.POST)
    public String findFoodClass(@RequestParam String id, HttpSession session) {
        FoodClass foodClass = ("0".equals(id) ? new FoodClass() : find(id));
        session.setAttribute("fc", foodClass);

        return "dashboard/crud/foodClassCRUD";
    }

    private FoodClass find(String id) {
        Gson gson = new Gson();
        long longId = (id == null) ? 0 : Long.parseLong(id);

        List<FoodClass> foodClassList = gson.fromJson(Utils.readInput(FOOD_CLASS_JSON), new TypeToken<List<FoodClass>>() {}.getType());
        return foodClassList.stream().filter(fc -> fc.getId() == longId).findFirst().orElse(null);
    }

    @RequestMapping(value = "/foodClassAction", method = RequestMethod.POST, produces = JSON_UTF_8)
    @ResponseBody
    public void foodClassAction(@RequestParam String action, @RequestParam String id) {

        switch (action) {
            case "edit":
            case "find":
            case "create":
            case "delete":
                System.out.println(action);
        }
    }
}
