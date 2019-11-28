package com.example.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoomTable {

    private int id;
    private String name;
    private String room;
    private int seats = 1;
    private CategoryType category;

    public enum CategoryType {
        lowcost("lowcost"), medium("medium"), highend("highend");

        public final String category;

        CategoryType(String category) {
            this.category = category;
        }

        public static CategoryType getEnumFromCategory(String category) {
            return Arrays.stream(values()).filter(c -> c.category.equals(category)).findFirst().orElse(highend);
        }
    }

}

