package com.FoodOrderingApp.foodorder.cartitem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService{

    private  CartItemDAO cartItemDAO;

    @Autowired
    public CartItemServiceImpl(CartItemDAO cartItemDAO) {
        this.cartItemDAO = cartItemDAO;
    }

    @Override
    public void removeCartItemById(Long cartItemId) {
         cartItemDAO.deleteById(cartItemId);
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) {
        return cartItemDAO.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Did not find Cart item with the id - " + cartItemId));

    }
}
