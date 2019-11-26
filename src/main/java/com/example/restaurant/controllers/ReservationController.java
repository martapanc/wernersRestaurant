package com.example.restaurant.controllers;

import com.example.restaurant.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.example.restaurant.utils.Utils.JSON_UTF_8;

@Controller
public class ReservationController {

    private static final String RESERVATION_JSON = "src/main/resources/json/reservation.json";

    @RequestMapping(value = "/reservation", method = RequestMethod.POST, produces = JSON_UTF_8)
    @ResponseBody
    public String listReservations() {
        return Utils.readInput(RESERVATION_JSON);
    }
}
