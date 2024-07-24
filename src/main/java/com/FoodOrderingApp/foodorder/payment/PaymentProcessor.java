package com.FoodOrderingApp.foodorder.payment;

import com.FoodOrderingApp.foodorder.customer.Customer;
import com.stripe.model.Charge;

public interface PaymentProcessor {
    Charge processPayment(Long amount, String currency, String token, Customer customer) throws Exception;
}
