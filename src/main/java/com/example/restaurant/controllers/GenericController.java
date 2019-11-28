package com.example.restaurant.controllers;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface GenericController {

    String list();

    String CRUD();

    String find(String id, HttpSession session);

    void action(String action, String id, HttpServletRequest request);

    Object findObjectById(String id);

    int findIdByName(String name);

    int findArrayIdById(String id);
}
