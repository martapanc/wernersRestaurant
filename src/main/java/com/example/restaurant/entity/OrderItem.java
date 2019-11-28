package com.example.restaurant.entity;

import lombok.Data;

@Data
public class OrderItem {

    private int id;
    private int quantity;
    private String comment;
    private Item item;
    private int item_id;
}
