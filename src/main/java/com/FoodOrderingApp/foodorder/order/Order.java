package com.FoodOrderingApp.foodorder.order;

import com.FoodOrderingApp.foodorder.DTO.MenuItemDTO;
import com.FoodOrderingApp.foodorder.DTO.OrderItemDTO;
import com.FoodOrderingApp.foodorder.address.Address;
import com.FoodOrderingApp.foodorder.cartitem.CartItem;
import com.FoodOrderingApp.foodorder.customer.Customer;
import com.FoodOrderingApp.foodorder.orderitem.OrderItem;
import com.FoodOrderingApp.foodorder.restaurant.Restaurant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "orders")
@NoArgsConstructor
@Getter
@Setter
@ToString

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;


    @Column(name = "order_date", nullable = false , updatable = false)
    private LocalDateTime orderDateTime;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "quantity")
    private int quantity;

    @Column(nullable = false , name = "total_amount")
    private BigDecimal totalAmount;
    @Column(name = "contact_phone")
    private String contactPhone;

    @Embedded
    private Address deliveryAddress;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY ,cascade = {
            CascadeType.DETACH , CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH
    })
    @JoinColumn(name= "customer_id")
    @JsonIgnore
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY ,cascade = {
            CascadeType.DETACH , CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH
    })
    @JoinColumn(name= "restaurant_id")
    @JsonIgnore
    private Restaurant restaurant;

    @OneToMany(mappedBy = "order" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderItem> orderItemList;


    public Order(String contactPhone, Address deliveryAddress) {
        this.quantity = 0;
        this.totalAmount = BigDecimal.valueOf(0);
        this.contactPhone = contactPhone;
        this.deliveryAddress = deliveryAddress;
        this.status = OrderStatus.PENDING;
    }

    public void addOrderItem(OrderItem orderItem)
    {
        if(orderItemList == null)
        {
            orderItemList = new ArrayList<>();
        }
        orderItemList.add(orderItem);
        orderItem.setOrder(this); // Ensure the bidirectional relationship is maintained
        // Update cart quantity and total amount
        updateOrderDetails();
    }
    public void updateOrderDetails() {
        this.quantity = orderItemList.stream().mapToInt(OrderItem::getQuantity).sum();
        this.totalAmount = orderItemList.stream()
                .map(orderItem -> BigDecimal.valueOf(orderItem.getMenuItem().getPrice())
                        .multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }


    @PrePersist
    protected void onCreate() {
        orderDateTime = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum OrderStatus {
        PENDING, CONFIRMED, PREPARING, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
    }
}
