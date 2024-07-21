package com.FoodOrderingApp.foodorder.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ItemReviewDTO {
    private int rate;
    private String review;
    private Long customerId;
    private Long menuItemId;

}
