package com.FoodOrderingApp.foodorder.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class OrderItemDTO {
    private int quantity;
    private String specialInstructions;
    private Long menuItemId;
}
