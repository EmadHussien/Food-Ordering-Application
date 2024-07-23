package com.FoodOrderingApp.foodorder.services;

import com.FoodOrderingApp.foodorder.cart.Cart;
import com.FoodOrderingApp.foodorder.cart.CartDAO;
import com.FoodOrderingApp.foodorder.customer.Customer;
import com.FoodOrderingApp.foodorder.customer.CustomerDAO;
import com.FoodOrderingApp.foodorder.customer.CustomerService;
import com.FoodOrderingApp.foodorder.customer.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerDAO customerDAO;

    @Mock
    private CartDAO cartDAO;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    public void testCreateCustomerInitializesCart() {
        // Setup
        Customer newCustomer = new Customer();
        newCustomer.setFirstName("Emad");
        newCustomer.setLastName("Hussien");
        // Note: We don't set a cart on the customer, as the method should handle this

        Cart newCart = new Cart();
        newCart.setId(1L);
        newCart.setTotalAmount(BigDecimal.ZERO);
        newCart.setCartQuantity(0);

        when(cartDAO.save(any(Cart.class))).thenReturn(newCart);
        when(customerDAO.save(any(Customer.class))).thenAnswer(invocation -> {
            Customer savedCustomer = invocation.getArgument(0);
            savedCustomer.setId(1L);
            return savedCustomer;
        });

        // Execute
        Customer savedCustomer = customerService.createCustomer(newCustomer);

        // Verify
        assertNotNull(savedCustomer);
        assertNotNull(savedCustomer.getId());
        assertEquals("Emad Hussien", savedCustomer.getFirstName()+" "+savedCustomer.getLastName());
        assertNotNull(savedCustomer.getCart());
        assertEquals(BigDecimal.ZERO, savedCustomer.getCart().getTotalAmount());
        assertEquals(0, savedCustomer.getCart().getCartQuantity());
        assertEquals(1L, savedCustomer.getCart().getId());

        // Verify DAO interactions
        verify(cartDAO).save(any(Cart.class));
        verify(customerDAO).save(newCustomer);
    }

    @Test
    public void testUpdateCustomer() {
        // Setup
        Long customerId = 1L;
        Customer existingCustomer = new Customer();
        existingCustomer.setId(customerId);
        existingCustomer.setFirstName("Emad");
        existingCustomer.setLastName("Hussien");
        existingCustomer.setEmail("john@example.com");
        existingCustomer.setCreatedAt(LocalDateTime.now().minusDays(1));

        Customer updatedCustomerData = new Customer();
        updatedCustomerData.setFirstName("Omda");
        updatedCustomerData.setLastName("Soliman");
        updatedCustomerData.setEmail("emad.updated@example.com");

        // Mock behavior
    //    when(customerService.readCustomer(customerId)).thenReturn(existingCustomer);
        when(customerDAO.findById(customerId)).thenReturn(Optional.of(existingCustomer));

        when(customerDAO.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Customer result = customerService.updateCustomer(customerId, updatedCustomerData);

        // Verify
        assertNotNull(result);
        assertEquals(customerId, result.getId());
        assertEquals("Omda Soliman", result.getFirstName()+ " "+result.getLastName());
        assertEquals("emad.updated@example.com", result.getEmail());
        assertEquals(existingCustomer.getCreatedAt(), result.getCreatedAt());

        // Verify interactions

        verify(customerDAO).findById(customerId);
        verify(customerDAO).save(any(Customer.class));
        
    }


}
