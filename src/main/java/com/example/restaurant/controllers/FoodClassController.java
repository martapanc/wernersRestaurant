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
    private static List<FoodClass> foodClassList = getFoodClassList();

    @RequestMapping(value = "/foodClass", method = RequestMethod.POST, produces = JSON_UTF_8)
    @ResponseBody
    public String listFoodClasses() {
        Gson gson = new Gson();
        return gson.toJson(foodClassList);
    }

    @GetMapping("/foodClassCRUD")
    @ResponseBody
    public String foodClassCRUD() {
        return "dashboard/crud/foodClassCRUD";
    }

    @RequestMapping(value = "/foodClass-find", method = RequestMethod.POST)
    public String findFoodClass(@RequestParam String id, HttpSession session) {
        FoodClass foodClass = ("0".equals(id) ? new FoodClass() : findById(id));
        session.setAttribute("fc", foodClass);

        return "dashboard/crud/foodClassCRUD";
    }

    @RequestMapping(value = "/foodClass-action", method = RequestMethod.POST)
    @ResponseBody
    public void foodClassAction(@RequestParam String action, @RequestParam String id, @RequestParam(required = false) String name,
                                @RequestParam(required = false) String image) {
        switch (action) {
            case "edit":
                int classId = findByName(id);
                foodClassList.get(classId).setImage(image);
                foodClassList.get(classId).setName(name);
                System.out.println("Edited: " + foodClassList.get(classId));
                break;
            case "create":
                FoodClass foodClass = new FoodClass();
                foodClass.setId(foodClassList.get(foodClassList.size() - 1).getId() + 1);
                foodClass.setName(name);
                foodClass.setImage(image);
                foodClassList.add(foodClass);
                System.out.println("Created: " + foodClassList.get(foodClassList.size() - 1));
                break;
            case "delete":
                int arrayIdById = findArrayIdById(id);
                System.out.println("Deleted: " + foodClassList.get(arrayIdById));
                foodClassList.remove(arrayIdById);
        }
    }

    public static List<FoodClass> getFoodClassList() {
        Gson gson = new Gson();
        return gson.fromJson(Utils.readInput(FOOD_CLASS_JSON), new TypeToken<List<FoodClass>>() {}.getType());
    }

    private FoodClass findById(String id) {
        long longId = (id == null) ? 0 : Long.parseLong(id);

        return foodClassList.stream().filter(fc -> fc.getId() == longId).findFirst().orElse(null);
    }

    private int findByName(String name) {
        return foodClassList.indexOf(foodClassList.stream().filter(fc -> fc.getName().equals(name)).findFirst().orElse(null));
    }

    private int findArrayIdById(String id) {
        return foodClassList.indexOf(foodClassList.stream().filter(fc -> id.equals(fc.getId() + "")).findFirst().orElse(null));
    }
}
