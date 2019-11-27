package com.example.restaurant.controllers;

import com.example.restaurant.entity.FoodClass;
import com.example.restaurant.entity.Item;
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

import static com.example.restaurant.controllers.FoodClassController.getFoodClassList;
import static com.example.restaurant.utils.Utils.JSON_UTF_8;

@Controller
public class ItemController {

    private static final String ITEM_JSON = "src/main/resources/json/item.json";
    private static final String CLASS_JSON = "src/main/resources/json/foodClass.json";

    private static List<FoodClass> foodClassList = getFoodClassList();
    private static List<Item> itemList = getItemList();

    @RequestMapping(value = "/item", method = RequestMethod.POST, produces = JSON_UTF_8)
    @ResponseBody
    public String listItemsForFoodClass() {
        Gson gson = new Gson();
        return gson.toJson(itemList);
    }

    @GetMapping("/itemCRUD")
    @ResponseBody
    public String itemCRUD() {
        return "dashboard/crud/itemCRUD";
    }

    @RequestMapping(value = "/item-classes", method = RequestMethod.POST, produces = JSON_UTF_8)
    @ResponseBody
    public String listItemsWithFoodClasses() {

        Gson gson = new Gson();
        List<Item> itemList = gson.fromJson(Utils.readInput(ITEM_JSON), new TypeToken<List<Item>>() {}.getType());
        List<FoodClass> foodClassList = gson.fromJson(Utils.readInput(CLASS_JSON), new TypeToken<List<FoodClass>>() {}.getType());

        for (Item item : itemList) {
            String foodClass = foodClassList.stream().filter(fc -> fc.getId() == item.getFoodClass_id()).findFirst().map(FoodClass::getName).orElse("");
            item.setFoodClass(foodClass);
        }

        return gson.toJson(itemList);
    }

    @RequestMapping(value = "/item-find", method = RequestMethod.POST)
    public String findItem(@RequestParam String id, HttpSession session) {
        Item item = ("0".equals(id) ? new Item() : findById(id));
        session.setAttribute("itm", item);
        session.setAttribute("fc", foodClassList);

        return "dashboard/crud/itemCRUD";
    }

    @RequestMapping(value = "/item-action", method = RequestMethod.POST)
    @ResponseBody
    public void foodClassAction(@RequestParam String action, @RequestParam String id, @RequestParam(required = false) String name,
                                @RequestParam(required = false) String image) {
        switch (action) {
            case "edit":
                int classId = findByName(id);
//                foodClassList.get(classId).setImage(image);
//                foodClassList.get(classId).setName(name);
                System.out.println("Edited: " + itemList.get(classId));
                break;
            case "create":
//                FoodClass foodClass = new FoodClass();
//                foodClass.setId(itemList.get(itemList.size() - 1).getId() + 1);
//                foodClass.setName(name);
//                foodClass.setImage(image);
//                itemList.add(foodClass);
                System.out.println("Created: " + itemList.get(itemList.size() - 1));
                break;
            case "delete":
                int arrayIdById = findArrayIdById(id);
                System.out.println("Deleted: " + itemList.get(arrayIdById));
//                itemList.remove(arrayIdById);
        }
    }

    public static List<Item> getItemList() {
        Gson gson = new Gson();
        return gson.fromJson(Utils.readInput(ITEM_JSON), new TypeToken<List<Item>>() {}.getType());
    }

    private Item findById(String id) {
        long longId = (id == null) ? 0 : Long.parseLong(id);

        return itemList.stream().filter(fc -> fc.getId() == longId).findFirst().orElse(null);
    }

    private int findByName(String name) {
        return itemList.indexOf(itemList.stream().filter(fc -> fc.getName().equals(name)).findFirst().orElse(null));
    }

    private int findArrayIdById(String id) {
        return itemList.indexOf(itemList.stream().filter(fc -> id.equals(fc.getId() + "")).findFirst().orElse(null));
    }
}
