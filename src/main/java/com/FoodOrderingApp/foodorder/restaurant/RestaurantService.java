package com.FoodOrderingApp.foodorder.restaurant;

import com.FoodOrderingApp.foodorder.DTO.PaginatedMenuResponse;
import com.FoodOrderingApp.foodorder.menuitem.MenuItem;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RestaurantService {

    // crud operations


    // create a restaurant
    Restaurant createRestaurant(Restaurant theRestaurant);
    // read a restaurant
    Restaurant readRestaurant(Long theId);

     // read restaurant menu paginated
     PaginatedMenuResponse readRestaurantMenu(Long theId, int pageNumber, int pageSize);

    // update a restaurant
    Restaurant updateRestaurant(Long theId,Restaurant theRestaurant);
    // delete a restaurant
    void deleteRestaurant(Long theId);
}
