package com.FoodOrderingApp.foodorder.configaurtions.stripeConfigs;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
    @Value("${stripe_api_key}")
    private String stripeApiKey;

    @PostConstruct
    public void initStripe() {
        Stripe.apiKey = stripeApiKey;
    }
}
