package com.example.restaurant.controllers;

import com.example.restaurant.entity.Reservation;
import com.example.restaurant.entity.RoomTable;
import com.example.restaurant.entity.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.example.restaurant.utils.ListLoader.reservationList;
import static com.example.restaurant.utils.ListLoader.roomTableList;
import static com.example.restaurant.utils.ListLoader.userList;
import static com.example.restaurant.utils.Utils.JSON_UTF_8;

@Controller
public class ReservationController {

    @RequestMapping(value = "/reservation", method = RequestMethod.POST, produces = JSON_UTF_8)
    @ResponseBody
    public String listReservations() {
        return new Gson().toJson(reservationList);
    }

    @RequestMapping(value = "/latest-reservations", method = RequestMethod.POST, produces = JSON_UTF_8)
    @ResponseBody
    public String listLatestReservations() {

        for (Reservation res : reservationList) {
            User user = userList.stream().filter(u -> u.getId() == res.getUser_id()).findFirst().orElse(null);
            res.setUser_id(user != null ? user.getId() : '-');
        }
        return new Gson().toJson(reservationList);
    }

    @RequestMapping(value = "/reservation-scheduler", method = RequestMethod.POST, produces = JSON_UTF_8)
    @ResponseBody
    public String listForScheduler() {
        GsonBuilder gsonBuilder = new GsonBuilder().setFieldNamingStrategy(f -> f.getName().equals("name") ? "title" : f.getName());
        return gsonBuilder.create().toJsonTree(serializeReservations(reservationList)).getAsJsonArray().toString();
    }

    private JsonArray serializeReservations(List<Reservation> resList) {

        GsonBuilder gsonBuilder = new GsonBuilder();

        JsonSerializer<Reservation> serializer = (res, typeOfSrc, context) -> {
            JsonObject jsonObj = new JsonObject();
            RoomTable r = roomTableList.stream().filter(rt -> rt.getId() == res.getRoomTableId()).findFirst().orElse(null);
            jsonObj.addProperty("start", res.getStartDate());
            jsonObj.addProperty("end", res.getEndDate());
            jsonObj.addProperty("resourceId", res.getRoomTableId());
            jsonObj.addProperty("title", res.getCustomerName() + "  [" + (r != null ? r.getSeats() : 2) + "]");
            jsonObj.addProperty("id", res.getId());
            return jsonObj;
        };

        gsonBuilder.registerTypeAdapter(Reservation.class, serializer);
        return (JsonArray) gsonBuilder.create().toJsonTree(resList);
    }
}
