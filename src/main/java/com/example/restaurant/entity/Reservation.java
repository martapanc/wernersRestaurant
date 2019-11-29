package com.example.restaurant.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Reservation {

    private int id;
    @SerializedName("start_date") private String startDate;
    @SerializedName("end_date") private String endDate;
    @SerializedName("roomTable_id") private int roomTableId;
    private String comment;
    private User user;
    @SerializedName("customer_name") private String customerName;
    private int user_id;

}
