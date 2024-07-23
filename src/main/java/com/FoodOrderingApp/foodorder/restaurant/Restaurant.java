package com.FoodOrderingApp.foodorder.restaurant;

import com.FoodOrderingApp.foodorder.address.Address;
import com.FoodOrderingApp.foodorder.menuitem.MenuItem;
import com.FoodOrderingApp.foodorder.order.Order;
import jakarta.persistence.*;
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
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "restaurant_name")
    @NotBlank(message = "Restaurant name is mandatory")
    private String restaurantName;

    @Column(name = "address")
    @Embedded
    private Address address;
    @Size(min = 11,message = "Phone number must be at least 11 numbers")
    @NotBlank(message = "Phone number is mandatory")
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "description")
    private String description;
    @Column(name = "cuisine")
    private String cuisine; // e.g., Italian, Chinese, etc.
    @Column(name = "workingHours")
    private String workingHours; // e.g., "Mon-Fri: 9AM-10PM, Sat-Sun: 10AM-11PM"

    @Column(name = "createdAt", nullable = false , updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt;


    @OneToMany(mappedBy = "restaurant" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<MenuItem> menuItems;


    @OneToMany(mappedBy = "restaurant" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<Order> orders;

    public void addOrder(Order order) {
        if(orders == null)
        {
            orders = new ArrayList<Order>();
        }
        orders.add(order);
        order.setRestaurant(this);
    }


    public Restaurant(String restaurantName, Address address, String phoneNumber, String email, String description, String cuisine, String workingHours) {
        this.restaurantName = restaurantName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.description = description;
        this.cuisine = cuisine;
        this.workingHours = workingHours;
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

    // TODO
    //  Other fields you might consider:
    /*
     private boolean isActive;
     */
}
