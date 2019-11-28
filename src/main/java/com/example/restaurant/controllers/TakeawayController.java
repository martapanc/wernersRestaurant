package com.example.restaurant.controllers;

import com.example.restaurant.entity.OrderItem;
import com.example.restaurant.entity.TakeawayOrder;
import com.example.restaurant.entity.manyToMany.TakeawayOrder_OrderItem;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.restaurant.utils.ListLoader.itemList;
import static com.example.restaurant.utils.ListLoader.orderItemList;
import static com.example.restaurant.utils.ListLoader.orderItemTakeawayOrderList;
import static com.example.restaurant.utils.ListLoader.takeawayOrderList;
import static com.example.restaurant.utils.Utils.JSON_UTF_8;

@Controller
public class TakeawayController {

    private static DecimalFormat df = new DecimalFormat("#.##€");

    @RequestMapping(value = "/latest-takeaway-orders", method = RequestMethod.POST, produces = JSON_UTF_8)
    @ResponseBody
    public String listLatestReservations() {

        df.setRoundingMode(RoundingMode.UP);

        for (TakeawayOrder order : takeawayOrderList) {
            order.setFormattedCost(String.format("%.2f€", order.getCost()));
        }
        return new Gson().toJson(takeawayOrderList);
    }

    @RequestMapping(value = "/takeaway", method = RequestMethod.POST, produces = JSON_UTF_8)
    @ResponseBody
    public String list() {
        for (TakeawayOrder order : takeawayOrderList) {

            List<Integer> orderItemIdList = orderItemTakeawayOrderList.stream()
                    .filter(entry -> entry.getTakeawayOrderId() ==  order.getId())
                    .map(TakeawayOrder_OrderItem::getOrderItemId)
                    .collect(Collectors.toList());

            Set<OrderItem> orderItemSet = new HashSet<>();

            orderItemList.forEach(orderItem -> {
                orderItemIdList.stream().mapToInt(id -> id).filter(id -> orderItem.getId() == id).forEach(id -> {
                    orderItem.setItem(itemList.stream().filter(i -> i.getId() == orderItem.getItem_id()).findFirst().orElse(null));
                    orderItemSet.add(orderItem);
                });
            });

            order.setOrderItemList(orderItemSet);
        }
        return new Gson().toJson(takeawayOrderList);
    }

}
