package com.example.restaurant.entity;

import lombok.Data;

@Data
public class RoomTable {

    private String name;
    private String room;
    private int seats = 1;
    private CategoryType category;

    public enum CategoryType {
        lowcost, medium, highend
    }

}

