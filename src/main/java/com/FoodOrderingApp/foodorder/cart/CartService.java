package com.FoodOrderingApp.foodorder.cart;

import com.FoodOrderingApp.foodorder.DTO.CartItemDTO;

import java.nio.file.AccessDeniedException;

public interface CartService {
    // crud operations

    // Get Cart: Retrieve the current cart of a customer.
    Cart getCustomerCart(Long customerId) throws AccessDeniedException;
    // Add Item to Cart: Add an item to the customer's cart.
    Cart addCartItem(Long customerId, CartItemDTO theCartItemDTO);
    // Remove Item from Cart: Remove an item from the customer's cart.
    void removeCartItem(Long customerId,Long theItemId);
    // Clear Cart: Clear all items from the customer's cart.
    void clearCart(Long customerId);
}
