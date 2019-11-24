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
        ADMIN(1), DBMANAGER(3), WAITER(2), CUSTOMER(4), OVERLORD(5);

        public final int roleId;

        RoleEnum(int roleId) {
            this.roleId = roleId;
        }

        public static RoleEnum getEnumFromId(int id) {
            for (RoleEnum e : values()) {
                if (e.roleId == id) {
                    return e;
                }
            }
            return null;
        }

    }

    public enum Section {
        SCHEDULER, TABLES, CUSTOMER, DASHBOARD, CALENDAR
    }
}
