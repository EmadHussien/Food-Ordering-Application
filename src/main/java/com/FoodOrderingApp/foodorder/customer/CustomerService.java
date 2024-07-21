package com.FoodOrderingApp.foodorder.customer;

import com.FoodOrderingApp.foodorder.DTO.PaginatedMenuResponse;
import com.FoodOrderingApp.foodorder.restaurant.Restaurant;

public interface CustomerService {
    // crud operations

    // create a Customer
    Customer createCustomer(Customer customer);

    // read a customer
    Customer readCustomer(Long theId);

    // update a customer
    Customer updateCustomer(Long theId, Customer customer);

    // delete a customer
    void deleteCustomer(Long theId);
}
