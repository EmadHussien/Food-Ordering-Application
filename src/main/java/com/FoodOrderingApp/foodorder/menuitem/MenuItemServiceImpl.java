package com.FoodOrderingApp.foodorder.menuitem;

import com.FoodOrderingApp.foodorder.DTO.MenuItemReviewListResponseDTO;
import com.FoodOrderingApp.foodorder.restaurant.Restaurant;
import com.FoodOrderingApp.foodorder.restaurant.RestaurantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuItemServiceImpl implements MenuItemService{

    private RestaurantService restaurantService;
    private MenuItemDAO menuItemDAO;
    @Autowired
    public MenuItemServiceImpl(RestaurantService restaurantService, MenuItemDAO menuItemDAO) {
        this.restaurantService = restaurantService;
        this.menuItemDAO = menuItemDAO;
    }

    @Override
    @Transactional
    public MenuItem createMenuItem(Long restaurantId, MenuItem theMenuItem) {
        Restaurant restaurant = restaurantService.readRestaurant(restaurantId);
        theMenuItem.setRestaurant(restaurant);
        return menuItemDAO.save(theMenuItem);
    }

    @Override
    public MenuItem readMenuItem(Long theId) {
        MenuItem menuItem =  menuItemDAO.findById(theId)
                .orElseThrow(() -> new RuntimeException("Did not find Restaurant with the id - " + theId));

        List<MenuItemReviewListResponseDTO> menuItemReviewListResponseDTO =
                menuItem.getItemReviewList().stream()
                        .map(itemReview -> new MenuItemReviewListResponseDTO(itemReview))
                        .collect(Collectors.toList());

        menuItem.setItemReviews(menuItemReviewListResponseDTO);

        return menuItem;
    }

    @Override
    @Transactional
    public MenuItem updateMenuItem(Long theId, MenuItem theMenuItem) {
        MenuItem foundMenuItem = readMenuItem(theId);
        BeanUtils.copyProperties(theMenuItem, foundMenuItem,"id","createdAt");
        return menuItemDAO.save(foundMenuItem);
    }

    @Override
    @Transactional
    public void deleteMenuItem(Long theId) {
        menuItemDAO.deleteById(theId);
    }
}
