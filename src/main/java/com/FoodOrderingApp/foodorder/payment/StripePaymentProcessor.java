package com.FoodOrderingApp.foodorder.payment;

import com.FoodOrderingApp.foodorder.customer.Customer;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripePaymentProcessor implements PaymentProcessor{
    @Override
    public  Charge processPayment(Long amount, String currency,String token, Customer customer) throws Exception {
        return createCharge(token,amount,currency,customer);
    }

    public Charge createCharge(String token, Long amount,String currency,Customer customer) throws Exception {

        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", (int) (amount * 100)); // Stripe expects the amount in cents
        chargeParams.put("currency", currency);
        chargeParams.put("source", token);
        chargeParams.put("description", "Charge for " + customer.getFirstName()+" "+customer.getLastName());
        chargeParams.put("receipt_email", customer.getEmail());
        return Charge.create(chargeParams);
    }


}
