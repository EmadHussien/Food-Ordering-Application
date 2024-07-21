package com.FoodOrderingApp.foodorder.itemreview;

import com.FoodOrderingApp.foodorder.DTO.ItemReviewDTO;
import com.FoodOrderingApp.foodorder.customer.Customer;
import com.FoodOrderingApp.foodorder.customer.CustomerService;
import com.FoodOrderingApp.foodorder.menuitem.MenuItem;
import com.FoodOrderingApp.foodorder.menuitem.MenuItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemReviewServiceImpl implements ItemReviewService {
    private ItemReviewDAO itemReviewDAO;
    private CustomerService customerService;
    private MenuItemService menuItemService;

    @Autowired
    public ItemReviewServiceImpl(ItemReviewDAO itemReviewDAO, CustomerService customerService, MenuItemService menuItemService) {
        this.itemReviewDAO = itemReviewDAO;
        this.customerService = customerService;
        this.menuItemService = menuItemService;
    }

    @Override
    @Transactional
    public ItemReview createItemReview(ItemReviewDTO itemReviewDTO) {
        Customer customer = customerService.readCustomer(itemReviewDTO.getCustomerId());
        MenuItem menuItem = menuItemService.readMenuItem(itemReviewDTO.getMenuItemId());
        ItemReview itemReview = new ItemReview(itemReviewDTO.getRate(),itemReviewDTO.getReview());
        customer.addReviews(itemReview);
        menuItem.addReviews(itemReview);

        return itemReviewDAO.save(itemReview);
    }

    @Override
    @Transactional
    public ItemReview updateItemReview(Long theId, ItemReview theItemReview) {
        ItemReview foundItemReview = itemReviewDAO.findById(theId)
                .orElseThrow(() -> new RuntimeException("Did not find Item review with the id - " + theId));
        BeanUtils.copyProperties(theItemReview, foundItemReview,"id","createdAt","customer","menuItem");
        return itemReviewDAO.save(foundItemReview);
    }

    @Override
    @Transactional
    public void deleteItemReview(Long theId) {
            itemReviewDAO.deleteById(theId);
    }
}
