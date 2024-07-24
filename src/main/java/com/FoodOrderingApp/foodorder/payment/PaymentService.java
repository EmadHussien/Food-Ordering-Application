package com.FoodOrderingApp.foodorder.payment;

import com.FoodOrderingApp.foodorder.customer.Customer;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentProcessor paymentProcessor;

    @Autowired
    public PaymentService(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public Charge processPayment(Long amount, String currency , String token, Customer customer) throws Exception {
        return paymentProcessor.processPayment(amount,currency,token,customer);
    }
}
