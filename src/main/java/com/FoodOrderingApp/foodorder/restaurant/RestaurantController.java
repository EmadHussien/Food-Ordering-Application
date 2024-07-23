package com.FoodOrderingApp.foodorder.restaurant;

import com.FoodOrderingApp.foodorder.DTO.PaginatedMenuResponse;
import com.FoodOrderingApp.foodorder.menuitem.MenuItem;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestaurantController {
    private RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/restaurants")
    public Restaurant createRestaurant(@Valid @RequestBody Restaurant theRestaurant)
    {
        return restaurantService.createRestaurant(theRestaurant);
    }

    @GetMapping("restaurants/{restaurantId}/menu")
    public PaginatedMenuResponse getRestaurantMenu(@PathVariable Long restaurantId,
                                                   @RequestParam(defaultValue = "0") int pageNumber,
                                                   @RequestParam(defaultValue = "10") int pageSize) {
        return restaurantService.readRestaurantMenu(restaurantId, pageNumber, pageSize);
    }


    @GetMapping("/restaurants/{restaurantId}")
    public Restaurant readRestaurant(@PathVariable Long restaurantId)
    {
        return restaurantService.readRestaurant(restaurantId);
    }

    @PutMapping("/restaurants/{restaurantId}")
    public Restaurant updateRestaurant(@PathVariable Long restaurantId , @RequestBody Restaurant theRestaurant)
    {
        return restaurantService.updateRestaurant(restaurantId,theRestaurant);
    }

    @DeleteMapping("/restaurants/{restaurantId}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable Long restaurantId)
    {
         restaurantService.deleteRestaurant(restaurantId);
         return new ResponseEntity<>("Restaurant with id - "+ restaurantId + " has been deleted successfully", HttpStatus.OK);

    }

}
