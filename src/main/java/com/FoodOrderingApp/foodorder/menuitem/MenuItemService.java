package com.FoodOrderingApp.foodorder.menuitem;

import com.FoodOrderingApp.foodorder.restaurant.Restaurant;

public interface MenuItemService {
    // crud operations

    // create a menuItem
    MenuItem createMenuItem(Long restaurantId,MenuItem theMenuItem);
    // read a menuItem
    MenuItem readMenuItem(Long theId);
    // update a menuItem
    MenuItem updateMenuItem(Long theId,MenuItem theMenuItem);
    // delete a restaurant
    void deleteMenuItem(Long theId);
}
