package com.example.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class RestaurantApplication {

    @RequestMapping("/")
    public String index() {
        return "greeting";
    }

    public static void main(String[] args) {
        SpringApplication.run(RestaurantApplication.class, args);
    }

}
