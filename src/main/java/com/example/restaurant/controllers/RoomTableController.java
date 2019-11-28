package com.example.restaurant.controllers;

import com.example.restaurant.entity.RoomTable;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.example.restaurant.utils.ListLoader.roomTableList;
import static com.example.restaurant.utils.Utils.JSON_UTF_8;

@Controller
public class RoomTableController implements GenericController {

    @Override
    @RequestMapping(value = "/roomTable", method = RequestMethod.POST, produces = JSON_UTF_8)
    @ResponseBody
    public String list() {
        Gson gson = new Gson();
        return gson.toJson(roomTableList);
    }

    @Override
    @GetMapping("/roomTableCRUD")
    @ResponseBody
    public String CRUD() {
        return "dashboard/crud/tableCRUD";
    }

    @Override
    @RequestMapping(value = "/roomTable-find", method = RequestMethod.POST)
    public String find(String id, HttpSession session) {
        RoomTable roomTable = ("0".equals(id) ? new RoomTable() : (RoomTable) findObjectById(id));
        session.setAttribute("rt", roomTable);

        session.setAttribute("categories",
                Arrays.stream(RoomTable.CategoryType.values()).map(Enum::toString).collect(Collectors.toList()));

        return "dashboard/crud/tableCRUD";
    }

    @Override
    @RequestMapping(value = "/roomTable-action", method = RequestMethod.POST)
    @ResponseBody
    public void action(String action, String id, HttpServletRequest request) {

        RoomTable.CategoryType category = RoomTable.CategoryType.getEnumFromCategory(request.getParameter("category"));

        switch (action) {
            case "edit":
                int tableId = findArrayIdById(request.getParameter("id"));
                roomTableList.get(tableId).setName(request.getParameter("name"));
                roomTableList.get(tableId).setCategory(category);
                roomTableList.get(tableId).setRoom(request.getParameter("room"));
                roomTableList.get(tableId).setSeats(Integer.parseInt(request.getParameter("seats")));
                System.out.println("Edited: " + roomTableList.get(tableId));
                break;
            case "create":
                RoomTable roomTable = RoomTable.builder()
                        .id(roomTableList.get(roomTableList.size() - 1).getId() + 1)
                        .name(request.getParameter("name"))
                        .category(category)
                        .room(request.getParameter("room"))
                        .seats(Integer.parseInt(request.getParameter("seats")))
                        .build();
                roomTableList.add(roomTable);
                System.out.println("Created: " + roomTableList.get(roomTableList.size() - 1));
                break;
            case "delete":
                int arrayIdById = findArrayIdById(id);
                System.out.println("Deleted: " + roomTableList.get(arrayIdById));
                roomTableList.remove(arrayIdById);
        }
    }

    @Override
    public Object findObjectById(String id) {
        long longId = (id == null) ? 0 : Long.parseLong(id);

        return roomTableList.stream().filter(rt -> rt.getId() == longId).findFirst().orElse(null);

    }

    @Override
    public int findIdByName(String name) {
        return roomTableList.indexOf(roomTableList.stream().filter(rt -> rt.getName().equals(name)).findFirst().orElse(null));
    }

    @Override
    public int findArrayIdById(String id) {
        return roomTableList.indexOf(roomTableList.stream().filter(rt -> id.equals(rt.getId() + "")).findFirst().orElse(null));
    }
}
