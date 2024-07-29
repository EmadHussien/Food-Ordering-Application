package com.FoodOrderingApp.foodorder.customer;

import com.FoodOrderingApp.foodorder.address.Address;
import com.FoodOrderingApp.foodorder.cart.Cart;
import com.FoodOrderingApp.foodorder.itemreview.ItemReview;
import com.FoodOrderingApp.foodorder.order.Order;
import com.FoodOrderingApp.foodorder.payment.Payment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Size(min = 11 , message = "Must be at least 11 numbers")
    @Column(name = "phone_number")
    private String phoneNumber;
    @Email(message = "Must be email like: example@example.com")
    @NotBlank(message = "Email is mandatory")
    @Column(name = "email",nullable = false , unique = true)
    private String email;
    @Column(name = "address")
    @Embedded
    private Address address;
    @Column(name = "createdAt", nullable = false , updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToMany(mappedBy = "customer" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<ItemReview> customerReviews;

    @OneToMany(mappedBy = "customer" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToMany(mappedBy = "customer",fetch= FetchType.LAZY , cascade = CascadeType.ALL)
    private List<Payment> payments;

    public void addOrder(Order order) {
        if(orders == null)
        {
            orders = new ArrayList< Order>();
        }
        orders.add(order);
        order.setCustomer(this);
    }

    public void addReviews(ItemReview itemReview)
    {
        if(customerReviews == null)
        {
            customerReviews = new ArrayList<ItemReview>();
        }
        customerReviews.add(itemReview);
        itemReview.setCustomer(this);
    }


    public void addPayments(Payment thePayment)
    {
        if(payments == null)
        {
            payments = new ArrayList<Payment>();
        }
        payments.add(thePayment);
        thePayment.setCustomer(this);
    }





    // All-args constructor except id
    public Customer(String firstName, String lastName, String phoneNumber, String email, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.cart = new Cart();
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
        // TODO :
    /*
    private String password; // For authentication
    private boolean isActive; // Account status
     */
}
