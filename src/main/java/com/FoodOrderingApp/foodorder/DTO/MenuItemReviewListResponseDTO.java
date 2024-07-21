package com.FoodOrderingApp.foodorder.DTO;


import com.FoodOrderingApp.foodorder.itemreview.ItemReview;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MenuItemReviewListResponseDTO {
    private Long id;
    private int rate;
    private String review;
    private String customerName;
    private LocalDateTime createdAt;

    public MenuItemReviewListResponseDTO(ItemReview itemReview) {
        this.id = itemReview.getId();
        this.rate = itemReview.getRate();
        this.review = itemReview.getReview();
        this.createdAt = itemReview.getCreatedAt();
        this.customerName = itemReview.getCustomer().getFirstName() +" "+ itemReview.getCustomer().getLastName();
    }
}
