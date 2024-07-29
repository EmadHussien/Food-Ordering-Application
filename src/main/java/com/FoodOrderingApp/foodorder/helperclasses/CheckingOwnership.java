package com.FoodOrderingApp.foodorder.helperclasses;

import com.FoodOrderingApp.foodorder.customer.Customer;
import com.FoodOrderingApp.foodorder.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
public class CheckingOwnership {

    private static CustomerService customerService;

    @Autowired
    public CheckingOwnership(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Method to retrieve the current user's email from the JWT token
    private static String getCurrentUserMail() {
        // Get the current authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the authentication is an instance of JwtAuthenticationToken
        if (authentication instanceof JwtAuthenticationToken) {
            Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();

            // you can get a custom claim if needed
            String userEmail = jwt.getClaim("email");

            return userEmail;
        }

        // Handle the case where authentication is not a JWT token
        throw new IllegalStateException("Authentication is not a JWT token");
    }


    // Method to check user ownership
    public static boolean isUserResourceOwner(Long userId) {
        // Get the current user's Email from the JWT token
        String tokenUserMail = getCurrentUserMail();

        // Get the current user's Email from the db
        Customer customer = customerService.readCustomer(userId);
        String dbUserMail = customer.getEmail();
        // Check if the current user is the owner of the cart
        return tokenUserMail.equals(dbUserMail);
    }


}
