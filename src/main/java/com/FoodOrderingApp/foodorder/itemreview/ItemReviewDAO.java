package com.FoodOrderingApp.foodorder.itemreview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemReviewDAO extends JpaRepository<ItemReview, Long> {
}
