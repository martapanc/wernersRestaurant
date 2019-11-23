package com.example.restaurant.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/homepage")
    public String homepage() {
        return "index";
    }

    @GetMapping("/documentation")
    public String documentation() {
        return "pages/documentation/docs";
    }
}
