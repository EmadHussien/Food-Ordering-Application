package com.FoodOrderingApp.foodorder.itemreview;

import com.FoodOrderingApp.foodorder.DTO.ItemReviewDTO;
import com.FoodOrderingApp.foodorder.customer.Customer;

import java.util.List;

public interface ItemReviewService {
    // crud operations

    // create ItemReview
    ItemReview createItemReview(ItemReviewDTO itemReviewDTO);
    // update a customer
    ItemReview updateItemReview(Long theId, ItemReview theItemReview);

    // delete item review
    void deleteItemReview(Long theId);
}
