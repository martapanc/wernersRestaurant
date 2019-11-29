package com.example.restaurant.utils;

import com.example.restaurant.entity.FoodClass;
import com.example.restaurant.entity.Item;
import com.example.restaurant.entity.OrderItem;
import com.example.restaurant.entity.Reservation;
import com.example.restaurant.entity.RoomTable;
import com.example.restaurant.entity.TakeawayOrder;
import com.example.restaurant.entity.User;
import com.example.restaurant.entity.manyToMany.TakeawayOrder_OrderItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class ListLoader {

    private static final String FOOD_CLASS_JSON = "src/main/resources/json/foodClass.json";
    private static final String ITEM_JSON = "src/main/resources/json/item.json";
    private static final String TAKEAWAY_ORDERS_JSON = "src/main/resources/json/takeawayOrder.json";
    private static final String ORDER_ITEM_TAKEAWAY_ORDER_JSON = "src/main/resources/json/orderItem_takeawayOrder.json";
    private static final String ORDER_ITEM_JSON = "src/main/resources/json/orderItem.json";
    private static final String RESERVATION_JSON = "src/main/resources/json/reservation.json";
    private static final String ROOM_TABLE_JSON = "src/main/resources/json/roomTable.json";
    private static final String USER_JSON = "src/main/resources/json/user.json";

    public static List<FoodClass> foodClassList = getFoodClassList();
    public static List<Item> itemList = getItemList();
    public static List<TakeawayOrder> takeawayOrderList = getTakeawayOrderList();
    public static List<TakeawayOrder_OrderItem> orderItemTakeawayOrderList = getOrderItemTakeawayOrderList();
    public static List<OrderItem> orderItemList = getOrderItemList();
    public static List<RoomTable> roomTableList = getRoomTableList();
    public static List<User> userList = getUserList();
    public static List<Reservation> reservationList = getReservationList();

    private static List<FoodClass> getFoodClassList() {
        return new Gson().fromJson(Utils.readInput(FOOD_CLASS_JSON), new TypeToken<List<FoodClass>>() {
        }.getType());
    }

    private static List<Item> getItemList() {
        return new Gson().fromJson(Utils.readInput(ITEM_JSON), new TypeToken<List<Item>>() {
        }.getType());
    }

    private static List<TakeawayOrder> getTakeawayOrderList() {
        return new Gson().fromJson(Utils.readInput(TAKEAWAY_ORDERS_JSON), new TypeToken<List<TakeawayOrder>>() {
        }.getType());
    }

    private static List<TakeawayOrder_OrderItem> getOrderItemTakeawayOrderList() {
        return new Gson().fromJson(Utils.readInput(ORDER_ITEM_TAKEAWAY_ORDER_JSON), new TypeToken<List<TakeawayOrder_OrderItem>>() {
        }.getType());
    }

    private static List<OrderItem> getOrderItemList() {
        return new Gson().fromJson(Utils.readInput(ORDER_ITEM_JSON), new TypeToken<List<OrderItem>>() {
        }.getType());
    }

    private static List<RoomTable> getRoomTableList() {
        return new Gson().fromJson(Utils.readInput(ROOM_TABLE_JSON), new TypeToken<List<RoomTable>>() {
        }.getType());
    }

    private static List<User> getUserList() {
        return new Gson().fromJson(Utils.readInput(USER_JSON), new TypeToken<List<User>>() {
        }.getType());
    }

    private static List<Reservation> getReservationList() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<Reservation> reservations = new Gson().fromJson(Utils.readInput(RESERVATION_JSON), new TypeToken<List<Reservation>>() {
        }.getType());

        for (Reservation r : reservations) {
            LocalDateTime start = LocalDateTime.parse(r.getStartDate(), formatter);
            LocalDateTime end = LocalDateTime.parse(r.getEndDate(), formatter);
            long dayDifference = DAYS.between(start, LocalDateTime.now());

            start = start.plusDays(dayDifference);
            end = end.plusDays(dayDifference);

            r.setStartDate(start.toString());
            r.setEndDate(end.toString());
        }

        return reservations;
    }
}
