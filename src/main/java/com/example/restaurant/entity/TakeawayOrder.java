package com.example.restaurant.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Set;

@Data
public class TakeawayOrder {

    private int id;
    private String status;
    private Set<OrderItem> orderItemList;
    @SerializedName("order_date") private String orderDate;
    @SerializedName("phone_number") private String phoneNumber;
    @SerializedName("customer_name") private String customerName;
    @SerializedName("guest_id") private String guestId;
    private String address;
    private double cost;
    private String formattedCost;
    private String comment;
}
