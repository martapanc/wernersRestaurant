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

    @GetMapping("/reservation")
    public String tableReservation() {
        return "pages/reservation/tableRes";
    }

    @GetMapping("/takeaway")
    public String takeaway() {
        return "pages/takeaway/takeaway";
    }

    @GetMapping("/login")
    public String login() {
        return "pages/login/login";
    }

    @GetMapping("/register")
    public String register() {
        return "pages/login/register";
    }

    @GetMapping("/recover-password")
    public String recoverPassword() {
        return "pages/login/recoverPassword";
    }
}
