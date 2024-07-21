package com.FoodOrderingApp.foodorder.restaurant;

import com.FoodOrderingApp.foodorder.DTO.PaginatedMenuResponse;
import com.FoodOrderingApp.foodorder.menuitem.MenuItem;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private RestaurantDAO restaurantDAO;
    @Autowired
    public RestaurantServiceImpl(RestaurantDAO restaurantDAO) {
        this.restaurantDAO = restaurantDAO;
    }

    @Override
    @Transactional
    public Restaurant createRestaurant(Restaurant theRestaurant) {
        return restaurantDAO.save(theRestaurant);
    }

    @Override
    public Restaurant readRestaurant(Long theId) {
        return restaurantDAO.findById(theId)
                .orElseThrow(() -> new RuntimeException("Did not find Restaurant with the id - " + theId));
    }

    @Override
    public PaginatedMenuResponse readRestaurantMenu(Long theId, int pageNumber, int pageSize) {
        Restaurant restaurant = readRestaurant(theId);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<MenuItem> menuItems = restaurantDAO.findMenuItemsByRestaurantId(theId, pageable);
        return new PaginatedMenuResponse(menuItems);
    }
    @Override
    @Transactional
    public Restaurant updateRestaurant(Long theId, Restaurant theRestaurant) {
        Restaurant foundRestaurant = readRestaurant(theId);
        BeanUtils.copyProperties(theRestaurant, foundRestaurant,"id","createdAt");
        return restaurantDAO.save(foundRestaurant);
    }

    @Override
    @Transactional
    public void deleteRestaurant(Long theId) {
        restaurantDAO.deleteById(theId);
    }


}
