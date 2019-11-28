package com.example.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {

    private int id;
    private String name;
    private String foodClass;
    private int foodClass_id;
    private double price;
    private boolean available;


}

