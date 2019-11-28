package com.example.restaurant.utils;

import com.example.restaurant.entity.FoodClass;
import com.example.restaurant.entity.Item;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ListLoader {

    private static final String FOOD_CLASS_JSON = "src/main/resources/json/foodClass.json";
    private static final String ITEM_JSON = "src/main/resources/json/item.json";

    public static List<FoodClass> foodClassList = getFoodClassList();
    public static List<Item> itemList = getItemList();

    private static List<FoodClass> getFoodClassList() {
        Gson gson = new Gson();
        return gson.fromJson(Utils.readInput(FOOD_CLASS_JSON), new TypeToken<List<FoodClass>>() {}.getType());
    }

    private static List<Item> getItemList() {
        Gson gson = new Gson();
        return gson.fromJson(Utils.readInput(ITEM_JSON), new TypeToken<List<Item>>() {}.getType());
    }
}
