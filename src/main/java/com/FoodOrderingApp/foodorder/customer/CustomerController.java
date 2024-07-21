package com.FoodOrderingApp.foodorder.customer;

import com.FoodOrderingApp.foodorder.DTO.CartItemDTO;
import com.FoodOrderingApp.foodorder.cart.Cart;
import com.FoodOrderingApp.foodorder.cart.CartService;
import com.FoodOrderingApp.foodorder.cartitem.CartItem;
import com.FoodOrderingApp.foodorder.restaurant.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {
    private CustomerService customerService;
    private CartService cartService;

    @Autowired
    public CustomerController(CustomerService customerService, CartService cartService) {
        this.customerService = customerService;
        this.cartService = cartService;
    }

    @PostMapping("/customers")
    public Customer createCustomer(@RequestBody Customer theCustomer) {
        System.out.println(theCustomer);
        return customerService.createCustomer(theCustomer);
    }

    @GetMapping("/customers/{customerId}")
    public Customer readCustomer(@PathVariable Long customerId)
    {
        return customerService.readCustomer(customerId);
    }

    @PutMapping("/customers/{customerId}")
    public Customer updateCustomer(@PathVariable Long customerId , @RequestBody Customer theCustomer)
    {
        return customerService.updateCustomer(customerId,theCustomer);
    }
    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long customerId)
    {
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>("Customer with id - "+ customerId + " has been deleted successfully", HttpStatus.OK);

    }

    @GetMapping("customers/{customerId}/cart")
    public ResponseEntity<Cart> getCart(@PathVariable Long customerId) {
        Cart cart = cartService.getCustomerCart(customerId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("customers/{customerId}/cart")
    public ResponseEntity<Cart> addItemToCart(@PathVariable Long customerId, @RequestBody CartItemDTO theCartItemDTO) {
        Cart updatedCart = cartService.addCartItem(customerId,theCartItemDTO);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

    @DeleteMapping("customers/{customerId}/cart/{itemId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable Long customerId, @PathVariable Long itemId) {
        cartService.removeCartItem(customerId, itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("customers/{customerId}/cart")
    public ResponseEntity<?> clearCart(@PathVariable Long customerId) {
        cartService.clearCart(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }







}
