package com.example.restaurant.controllers;

import com.example.restaurant.entity.FoodClass;
import com.example.restaurant.entity.Item;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.example.restaurant.utils.ListLoader.foodClassList;
import static com.example.restaurant.utils.ListLoader.itemList;
import static com.example.restaurant.utils.Utils.JSON_UTF_8;

@Controller
public class ItemController implements GenericController {

    @Override
    @RequestMapping(value = "/item", method = RequestMethod.POST, produces = JSON_UTF_8)
    @ResponseBody
    public String list() {
        Gson gson = new Gson();
        return gson.toJson(itemList);
    }

    @Override
    @GetMapping("/itemCRUD")
    @ResponseBody
    public String CRUD() {
        return "dashboard/crud/itemCRUD";
    }

    @RequestMapping(value = "/item-classes", method = RequestMethod.POST, produces = JSON_UTF_8)
    @ResponseBody
    public String listItemsWithFoodClasses() {

        for (Item item : itemList) {
            String foodClass = foodClassList.stream().filter(fc -> fc.getId() == item.getFoodClass_id()).findFirst().map(FoodClass::getName).orElse("");
            item.setFoodClass(foodClass);
        }

        return new Gson().toJson(itemList);
    }

    @Override
    @RequestMapping(value = "/item-find", method = RequestMethod.POST)
    public String find(@RequestParam String id, HttpSession session) {
        Item item = ("0".equals(id) ? new Item() : (Item) findObjectById(id));
        session.setAttribute("itm", item);
        session.setAttribute("fc", foodClassList);

        return "dashboard/crud/itemCRUD";
    }

    @Override
    @RequestMapping(value = "/item-action", method = RequestMethod.POST)
    @ResponseBody
    public void action(@RequestParam String action, @RequestParam String id, HttpServletRequest request) {

        int foodClassId, foodClassListId;

        switch (action) {
            case "edit":
                foodClassId = getFoodClass(request);
                foodClassListId = getFoodClassListId(foodClassId);
                int itemId = findArrayIdById(request.getParameter("id"));
                itemList.get(itemId).setName(request.getParameter("name"));
                itemList.get(itemId).setFoodClass_id(getFoodClass(request));
                itemList.get(itemId).setFoodClass(foodClassList.get(foodClassListId).getName());
                itemList.get(itemId).setPrice(Double.parseDouble(request.getParameter("price")));
                System.out.println("Edited: " + itemList.get(itemId));
                break;
            case "create":
                foodClassId = getFoodClass(request);
                foodClassListId = getFoodClassListId(foodClassId);
                Item item = Item.builder()
                        .id(itemList.get(itemList.size() - 1).getId() + 1)
                        .name(request.getParameter("name"))
                        .foodClass_id(foodClassId)
                        .foodClass(foodClassList.get(foodClassListId).getName())
                        .price(Double.parseDouble(request.getParameter("price")))
                        .available(true)
                        .build();
                itemList.add(item);
                System.out.println("Created: " + itemList.get(itemList.size() - 1));
                break;
            case "delete":
                int arrayIdById = findArrayIdById(id);
                System.out.println("Deleted: " + itemList.get(arrayIdById));
                itemList.remove(arrayIdById);
        }
    }

    private int getFoodClassListId(int foodClassId) {
        return foodClassList.indexOf(foodClassList.stream().filter(f -> foodClassId == f.getId()).findFirst().orElse(null));
    }

    private int getFoodClass(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("foodClass"));
    }

    @Override
    public Object findObjectById(String id) {
        long longId = (id == null) ? 0 : Long.parseLong(id);

        return itemList.stream().filter(fc -> fc.getId() == longId).findFirst().orElse(null);
    }

    @Override
    public int findIdByName(String name) {
        return itemList.indexOf(itemList.stream().filter(fc -> fc.getName().equals(name)).findFirst().orElse(null));
    }

    @Override
    public int findArrayIdById(String id) {
        return itemList.indexOf(itemList.stream().filter(fc -> id.equals(fc.getId() + "")).findFirst().orElse(null));
    }
}
