package com.example.restaurant.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;

@Data
public class TakeawayOrder {

    private String status;
    private Set<OrderItem> orderItemList;
    private Timestamp orderDate;
    private String address;
    private double cost;
    private String comment;
}
