package com.example.restaurant.utils;

import com.example.restaurant.entity.Role.RoleEnum;
import com.example.restaurant.entity.Role.Section;
import com.example.restaurant.entity.User;
import com.example.restaurant.entity.UserSession;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.HashSet;

public class AccessManager {

    private static HashMap<RoleEnum, HashSet<Section>> permissions = new HashMap<>();

    public static void definePermissions() {

        HashSet<Section> adminSections = new HashSet<>();
        adminSections.add(Section.TABLES);
        adminSections.add(Section.SCHEDULER);
        adminSections.add(Section.DASHBOARD);
        permissions.put(RoleEnum.ADMIN, adminSections);

        HashSet<Section> customerSections = new HashSet<>();
        customerSections.add(Section.CUSTOMER);
        permissions.put(RoleEnum.CUSTOMER, customerSections);

        HashSet<Section> waiterSections = new HashSet<>();
        waiterSections.add(Section.SCHEDULER);
        waiterSections.add(Section.DASHBOARD);
        permissions.put(RoleEnum.WAITER, waiterSections);

        HashSet<Section> dbManagerSections = new HashSet<>();
        dbManagerSections.add(Section.TABLES);
        dbManagerSections.add(Section.DASHBOARD);
        permissions.put(RoleEnum.DBMANAGER, dbManagerSections);

        HashSet<Section> overlordSections = new HashSet<>();
        overlordSections.add(Section.TABLES);
        overlordSections.add(Section.SCHEDULER);
        overlordSections.add(Section.CUSTOMER);
        overlordSections.add(Section.CALENDAR);
        overlordSections.add(Section.DASHBOARD);
        permissions.put(RoleEnum.OVERLORD, overlordSections);
    }

//    public boolean isAllowed(HttpSession session, Section section) {
//        User user = ((UserSession) session.getAttribute("userSession")).getUser();
//        RoleEnum roleName = user.getRole().getName();
//        return permissions.get(roleName).contains(section);
//    }

    public static boolean isAllowed(HttpSession session, Section section) {
        definePermissions();

        User user = ((UserSession) session.getAttribute("userSession")).getUser();
        RoleEnum roleName = RoleEnum.getEnumFromId(user.getRoleId());
        return permissions.get(roleName).contains(section);
    }

    public static User getUserData(HttpSession session) {
        return ((UserSession) session.getAttribute("userSession")).getUser();
    }
}
