package com.FoodOrderingApp.foodorder.cartitem;

public interface CartItemService {
    void removeCartItemById(Long cartItemId);
    CartItem findCartItemById(Long cartItemId);
}
