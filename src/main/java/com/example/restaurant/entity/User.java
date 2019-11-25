package com.example.restaurant.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {

    @SerializedName("full_name") private String fullName;
    private Role.RoleEnum role;
    @SerializedName("role_id") private int roleId;
    private String email;
    private String password;
    private int points;
    @SerializedName("billing_address") private String billingAddress;
    @SerializedName("phone_number") private String phoneNumber;
    @SerializedName("vip_status") private boolean vipStatus;
    private String avatar;
    @SerializedName("creation_date")private Timestamp creationDate;

    public void setRole(int id) {
        this.role = Role.RoleEnum.getEnumFromId(id);
    }

    public String getFirstName() {
        String full = this.fullName;
        String[] a = full.split("\\s");
        return a[0];
    }

    public String getLastName() {
        String full = this.fullName;
        String[] a = full.split("\\s");
        return a[1];
    }
}
