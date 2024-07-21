package com.FoodOrderingApp.foodorder.menuitem;

import com.FoodOrderingApp.foodorder.restaurant.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MenuItemController {
    private MenuItemService menuItemService;

    @Autowired
    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @PostMapping("/restaurants/{restaurantId}/menu")
    public MenuItem createMenuitem(@PathVariable Long restaurantId, @RequestBody MenuItem theMenuItem)
    {
        System.out.println(theMenuItem.isAvailable());
        return menuItemService.createMenuItem(restaurantId,theMenuItem);
    }

    @GetMapping("/restaurants/{restaurantId}/menu/{menuItemId}")
    public MenuItem readMenuitem(@PathVariable Long menuItemId)
    {
        return menuItemService.readMenuItem(menuItemId);
    }


    @PutMapping("/restaurants/{restaurantId}/menu/{menuItemId}")
    public MenuItem updateMenuItem(@PathVariable Long restaurantId , @PathVariable Long menuItemId, @RequestBody MenuItem theMenuItem)
    {
        return menuItemService.updateMenuItem(menuItemId,theMenuItem);
    }

    @DeleteMapping("/restaurants/{restaurantId}/menu/{menuItemId}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable Long menuItemId)
    {
        menuItemService.deleteMenuItem(menuItemId);
        return new ResponseEntity<>("Menu Item with id - "+ menuItemId + " has been deleted successfully", HttpStatus.OK);

    }



}
