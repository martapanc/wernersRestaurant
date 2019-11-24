package com.example.restaurant.entity;

public class Role {

    private RoleEnum name;

    public RoleEnum getName() {
        return name;
    }

    public void setName(RoleEnum name) {
        this.name = name;
    }

    public enum RoleEnum {
        ADMIN, DBMANAGER, WAITER, CUSTOMER, OVERLORD
    }

}
