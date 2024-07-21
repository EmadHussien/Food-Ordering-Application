package com.FoodOrderingApp.foodorder.restaurant;

import com.FoodOrderingApp.foodorder.menuitem.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantDAO extends JpaRepository<Restaurant , Long> {
    @Query("SELECT m FROM MenuItem m WHERE m.restaurant.id = :restaurantId")
    Page<MenuItem> findMenuItemsByRestaurantId(@Param("restaurantId") Long restaurantId, Pageable pageable);

}
