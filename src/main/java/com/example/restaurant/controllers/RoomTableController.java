package com.example.restaurant.controllers;

import com.example.restaurant.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.example.restaurant.utils.Utils.JSON_UTF_8;

@Controller
public class RoomTableController {

    private static final String ROOM_TABLE_JSON = "src/main/resources/json/roomTable.json";

    @RequestMapping(value = "/roomTable", method = RequestMethod.POST, produces = JSON_UTF_8)
    @ResponseBody
    public String listRoomTables() {
        return Utils.readInput(ROOM_TABLE_JSON);
    }
}
