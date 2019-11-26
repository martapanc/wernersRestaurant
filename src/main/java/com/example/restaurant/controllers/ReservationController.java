package com.example.restaurant.controllers;

import com.example.restaurant.entity.Reservation;
import com.example.restaurant.entity.User;
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
public class ReservationController {

    private static final String RESERVATION_JSON = "src/main/resources/json/reservation.json";
    private static final String USER_JSON = "src/main/resources/json/user.json";

    @RequestMapping(value = "/reservation", method = RequestMethod.POST, produces = JSON_UTF_8)
    @ResponseBody
    public String listReservations() {
        return Utils.readInput(RESERVATION_JSON);
    }

    @RequestMapping(value = "/latest-reservations", method = RequestMethod.POST, produces = JSON_UTF_8)
    @ResponseBody
    public String listLatestReservations() {

        Gson gson = new Gson();

        List<Reservation> reservationList = gson.fromJson(Utils.readInput(RESERVATION_JSON), new TypeToken<List<Reservation>>() {}.getType());
        List<User> userList = gson.fromJson(Utils.readInput(USER_JSON), new TypeToken<List<User>>() {}.getType());

        for (Reservation res : reservationList) {
            User user = userList.stream().filter(u -> u.getId() == res.getUser_id()).findFirst().orElse(null);
            if (user != null) {
                res.setCustomerName(user.getFullName());
                res.setUser_id(user.getId());
            } else {
                res.setCustomerName("unregistered user");
                res.setUser_id('-');
            }
        }
        return gson.toJson(reservationList);
    }
}
