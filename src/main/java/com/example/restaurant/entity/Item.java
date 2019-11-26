package com.example.restaurant.entity;

import lombok.Data;

@Data
public class Item {

    private int id;
    private String name;
    private String foodClass;
    private int foodClass_id;
    private double price;
    private boolean available;

}

