package com.example.restaurant.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserSession {

    private User user;
    private Date loginTime;
}
