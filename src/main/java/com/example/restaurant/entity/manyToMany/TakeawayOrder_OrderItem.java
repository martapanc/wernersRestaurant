package com.example.restaurant.entity.manyToMany;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TakeawayOrder_OrderItem {

    @SerializedName("takeaway_order_id")
    private int takeawayOrderId;
    @SerializedName("order_item_id")
    private int orderItemId;
}
