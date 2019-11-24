package com.example.restaurant.entity;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class User {

    @SerializedName("full_name")
    private String fullName;
    private Role.RoleEnum role;
    @SerializedName("role_id")
    private int roleId;
    private String email;
    private String password;
    private int points;
    @SerializedName("billing_address")
    private String billingAddress;
    @SerializedName("phone_number")
    private String phoneNumber;
    @SerializedName("vip_status")
    private boolean vipStatus;
    private String avatar;
    private Timestamp creationDate;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isVipStatus() {
        return vipStatus;
    }

    public void setVipStatus(boolean vipStatus) {
        this.vipStatus = vipStatus;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullname) {
        this.fullName = fullname;
    }

    public Role.RoleEnum getRole() {
        return role;
    }

    public void setRole(Role.RoleEnum role) {
        this.role = role;
    }

    public void setRole(int id) {
        this.role = Role.RoleEnum.getEnumFromId(id);
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creation_date) {
        this.creationDate = creation_date;
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
