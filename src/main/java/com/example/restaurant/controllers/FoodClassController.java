package com.example.restaurant.controllers;

import com.example.restaurant.entity.FoodClass;
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
import static com.example.restaurant.utils.Utils.JSON_UTF_8;

@Controller
public class FoodClassController implements GenericController {

    @Override
    @RequestMapping(value = "/foodClass", method = RequestMethod.POST, produces = JSON_UTF_8)
    @ResponseBody
    public String list() {
        return new Gson().toJson(foodClassList);
    }

    @Override
    @GetMapping("/foodClassCRUD")
    @ResponseBody
    public String CRUD() {
        return "dashboard/crud/foodClassCRUD";
    }

    @Override
    @RequestMapping(value = "/foodClass-find", method = RequestMethod.POST)
    public String find(@RequestParam String id, HttpSession session) {
        FoodClass foodClass = ("0".equals(id) ? new FoodClass() : (FoodClass) findObjectById(id));
        session.setAttribute("fc", foodClass);

        return "dashboard/crud/foodClassCRUD";
    }

    @Override
    @RequestMapping(value = "/foodClass-action", method = RequestMethod.POST)
    @ResponseBody
    public void action(@RequestParam String action, @RequestParam String id, HttpServletRequest request) {

        switch (action) {
            case "edit":
                int classId = findIdByName(id);
                foodClassList.get(classId).setName(request.getParameter("name"));
                foodClassList.get(classId).setImage(request.getParameter("image"));
                System.out.println("Edited: " + foodClassList.get(classId));
                break;
            case "create":
                FoodClass foodClass = FoodClass.builder()
                        .id(foodClassList.get(foodClassList.size() - 1).getId() + 1)
                        .name(request.getParameter("name"))
                        .image(request.getParameter("image"))
                        .build();
                foodClassList.add(foodClass);
                System.out.println("Created: " + foodClassList.get(foodClassList.size() - 1));
                break;
            case "delete":
                int arrayIdById = findArrayIdById(id);
                System.out.println("Deleted: " + foodClassList.get(arrayIdById));
                foodClassList.remove(arrayIdById);
        }
    }

    @Override
    public Object findObjectById(String id) {
        long longId = (id == null) ? 0 : Long.parseLong(id);

        return foodClassList.stream().filter(fc -> fc.getId() == longId).findFirst().orElse(null);
    }

    @Override
    public int findIdByName(String name) {
        return foodClassList.indexOf(foodClassList.stream().filter(fc -> fc.getName().equals(name)).findFirst().orElse(null));
    }

    @Override
    public int findArrayIdById(String id) {
        return foodClassList.indexOf(foodClassList.stream().filter(fc -> id.equals(fc.getId() + "")).findFirst().orElse(null));
    }
}
