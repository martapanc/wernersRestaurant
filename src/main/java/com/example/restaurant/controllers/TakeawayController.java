package com.example.restaurant.controllers;

import com.example.restaurant.entity.TakeawayOrder;
import com.example.restaurant.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import static com.example.restaurant.utils.Utils.JSON_UTF_8;

@Controller
public class TakeawayController {

    private static final String TAKEAWAY_ORDERS_JSON = "src/main/resources/json/takeawayOrder.json";
    private static DecimalFormat df = new DecimalFormat("#.##€");

    @RequestMapping(value = "/latest-takeaway-orders", method = RequestMethod.POST, produces = JSON_UTF_8)
    @ResponseBody
    public String listLatestReservations() {

        df.setRoundingMode(RoundingMode.UP);
        Gson gson = new Gson();
        List<TakeawayOrder> takeawayOrderList = gson.fromJson(Utils.readInput(TAKEAWAY_ORDERS_JSON), new TypeToken<List<TakeawayOrder>>() {
        }.getType());

        for (TakeawayOrder order : takeawayOrderList) {
            order.setFormattedCost(String.format("%.2f€", order.getCost()));
        }
        return gson.toJson(takeawayOrderList);
    }
}
