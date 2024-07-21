package com.FoodOrderingApp.foodorder.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CartItemDTO {
    private int quantity;
    private String specialInstructions;
    private Long menuItemId;
}
