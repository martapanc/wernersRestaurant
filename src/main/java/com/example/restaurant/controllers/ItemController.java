package com.example.restaurant.controllers;

import com.example.restaurant.entity.FoodClass;
import com.example.restaurant.entity.Item;
import com.example.restaurant.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.example.restaurant.utils.Utils.JSON_UTF_8;

@Controller
public class ItemController {

    private static final String ITEM_JSON = "src/main/resources/json/item.json";
    private static final String CLASS_JSON = "src/main/resources/json/foodClass.json";

    @RequestMapping(value = "/item", method = RequestMethod.POST, produces = JSON_UTF_8)
    @ResponseBody
    public String listItemsForFoodClass() {
        return Utils.readInput(ITEM_JSON);
    }

    @RequestMapping(value = "/item-classes", method = RequestMethod.POST, produces = JSON_UTF_8)
    @ResponseBody
    public String listItemsWithFoodClasses() {

        Gson gson = new Gson();
        List<Item> itemList = gson.fromJson(Utils.readInput(ITEM_JSON), new TypeToken<List<Item>>() {
        }.getType());
        List<FoodClass> foodClassList = gson.fromJson(Utils.readInput(CLASS_JSON), new TypeToken<List<FoodClass>>() {
        }.getType());

        for (Item item : itemList) {
            String foodClass = foodClassList.stream().filter(fc -> fc.getId() == item.getFoodClass_id()).findFirst().map(FoodClass::getName).orElse("");
            item.setFoodClass(foodClass);
        }

        return gson.toJson(itemList);
    }
}
