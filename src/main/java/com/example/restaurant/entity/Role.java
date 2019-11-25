package com.example.restaurant.entity;

import lombok.Data;

import java.util.Arrays;

@Data
public class Role {

    private RoleEnum name;

    public enum RoleEnum {
        ADMIN(1), DBMANAGER(3), WAITER(2), CUSTOMER(4), OVERLORD(5);

        public final int roleId;

        RoleEnum(int roleId) {
            this.roleId = roleId;
        }

        public static RoleEnum getEnumFromId(int id) {
            return Arrays.stream(values()).filter(e -> e.roleId == id).findFirst().orElse(null);
        }
    }

    public enum Section {
        SCHEDULER, TABLES, CUSTOMER, DASHBOARD, CALENDAR
    }
}
