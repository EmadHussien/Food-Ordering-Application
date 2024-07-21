package com.FoodOrderingApp.foodorder.customer;

import com.FoodOrderingApp.foodorder.cart.Cart;
import com.FoodOrderingApp.foodorder.cart.CartDAO;
import com.FoodOrderingApp.foodorder.restaurant.Restaurant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CustomerServiceImpl implements CustomerService{
    private CustomerDAO customerDAO;
    private CartDAO cartDAO;

    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDAO , CartDAO cartDAO) {
        this.customerDAO = customerDAO;
        this.cartDAO = cartDAO;
    }

    @Override
    @Transactional
    public Customer createCustomer(Customer theCustomer) {
        // Ensure the customer has a cart
        if (theCustomer.getCart() == null) {
            Cart cart = new Cart();
            cart.setTotalAmount(BigDecimal.ZERO);
            cart.setCartQuantity(0);
            cart = cartDAO.save(cart); // Save the cart to get its ID
            theCustomer.setCart(cart);
        }
        return customerDAO.save(theCustomer);
    }

    @Override
    public Customer readCustomer(Long theId) {
        return customerDAO.findById(theId)
                .orElseThrow(() -> new RuntimeException("Did not find Customer with the id - " + theId));
    }

    @Override
    @Transactional
    public Customer updateCustomer(Long theId, Customer theCustomer) {
        Customer foundCustomer = readCustomer(theId);
        BeanUtils.copyProperties(theCustomer, foundCustomer,"id","createdAt");
        return customerDAO.save(foundCustomer);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long theId) {
        customerDAO.deleteById(theId);
    }
}
