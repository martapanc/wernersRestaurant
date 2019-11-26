package com.example.restaurant.controllers;

import com.example.restaurant.entity.Role;
import com.example.restaurant.entity.User;
import com.example.restaurant.utils.AccessManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
                    return "dashboard/pages/foodClassTable";
                case "items":
                    return "dashboard/pages/itemTable";
                case "reservations":
                    return "dashboard/pages/reservationTable";
                case "restaurant":
                    return "dashboard/pages/restaurantTablesTable";
                case "takeaway":
                    return "dashboard/pages/takeawayOrderTable";
                case "users":
                    return "dashboard/pages/userTable";
                default:
                    return "dashboard/index";
            }
        }

        return "redirect:/homepage";
    }
}
