package com.FoodOrderingApp.foodorder.itemreview;

import com.FoodOrderingApp.foodorder.DTO.ItemReviewDTO;
import com.FoodOrderingApp.foodorder.customer.CustomerService;
import com.FoodOrderingApp.foodorder.menuitem.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ItemReviewController {
    private ItemReviewService itemReviewService ;
    private CustomerService customerService;
    private MenuItemService menuItemService;

    @Autowired
    public ItemReviewController(ItemReviewService itemReviewService, CustomerService customerService, MenuItemService menuItemService) {
        this.itemReviewService = itemReviewService;
        this.customerService = customerService;
        this.menuItemService = menuItemService;
    }

    @PostMapping("/item-reviews")
    public ItemReview createItemReview(@RequestBody ItemReviewDTO itemReviewDTO)
    {
        System.out.println(itemReviewDTO);
        ItemReview itemReview = itemReviewService.createItemReview(itemReviewDTO);

        return itemReview;
    }

    @PutMapping ("/item-reviews/{itemId}")
    public ItemReview updateItemReview(@PathVariable Long itemId,@RequestBody ItemReview itemReview)
    {
        System.out.println(itemId);
        System.out.println(itemReview);
        return itemReviewService.updateItemReview(itemId,itemReview);
    }


    @DeleteMapping("/item-reviews/{itemId}")
    public void deleteItemReview(@PathVariable Long itemId)
    {
        itemReviewService.deleteItemReview(itemId);
    }

}
