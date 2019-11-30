package com.example.restaurant.controllers;

import com.example.restaurant.entity.Role;
import com.example.restaurant.entity.User;
import com.example.restaurant.utils.AccessManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {

        if (AccessManager.isUserLoggedIn(session)) {
            session.setAttribute("dashboard", AccessManager.isAllowed(session, Role.Section.DASHBOARD));
            session.setAttribute("tables", AccessManager.isAllowed(session, Role.Section.TABLES));
            session.setAttribute("customer", AccessManager.isAllowed(session, Role.Section.CUSTOMER));
            session.setAttribute("scheduler", AccessManager.isAllowed(session, Role.Section.SCHEDULER));
            session.setAttribute("calendar", AccessManager.isAllowed(session, Role.Section.CALENDAR));

            User user = AccessManager.getUserData(session);
            session.setAttribute("avatar", user.getAvatar());
            session.setAttribute("name", user.getFirstName() + " " + user.getLastName());

            return "dashboard/index";
        }

        return "redirect:/homepage";
    }

    @GetMapping("/tables/{tableName}")
    public String foodClasses(HttpSession session, @PathVariable("tableName") String tableName) {
        if (AccessManager.isUserLoggedIn(session) && AccessManager.isAllowed(session, Role.Section.TABLES)) {
            switch (tableName) {
                case "foodClass":
                    return "dashboard/tables/foodClassTable";
                case "items":
                    return "dashboard/tables/itemTable";
                case "reservations":
                    return "dashboard/tables/reservationTable";
                case "restaurant":
                    return "dashboard/tables/restaurantTablesTable";
                case "takeaway":
                    return "dashboard/tables/takeawayOrderTable";
                case "users":
                    return "dashboard/tables/userTable";
                default:
                    return "dashboard/index";
            }
        }
        return "redirect:/homepage";
    }

    @GetMapping("/dashboard/documentation")
    public String documentation(HttpSession session) {

        if (AccessManager.isUserLoggedIn(session)) {
            return "dashboard/pages/documentation";
        }
        return "redirect:/homepage";
    }

    @GetMapping("/dashboard/{page}")
    public String pages(HttpSession session, @PathVariable String page) {

        switch (page) {
            case "documentation":
                if (AccessManager.isUserLoggedIn(session)) {
                    return "dashboard/pages/" + page;
                }
            case "scheduler": {
                if (AccessManager.isUserLoggedIn(session) && AccessManager.isAllowed(session, Role.Section.SCHEDULER)) {
                    return "dashboard/pages/" + page;
                }
            }
            case "tableReservation":
            case "takeawayOrder": {
                if (AccessManager.isUserLoggedIn(session) && AccessManager.isAllowed(session, Role.Section.CUSTOMER)) {
                    return "dashboard/pages/" + page;
                }
            }
            case "reservationInvoice":
            case "takeawayCheckout":
                if (AccessManager.isUserLoggedIn(session) && AccessManager.isAllowed(session, Role.Section.CUSTOMER)) {
                    return "dashboard/pages/checkout/" + page;
                }
        }
        return "redirect:/homepage";
    }
}
