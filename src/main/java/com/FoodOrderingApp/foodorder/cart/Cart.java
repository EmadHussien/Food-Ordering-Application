package com.FoodOrderingApp.foodorder.cart;

import com.FoodOrderingApp.foodorder.cartitem.CartItem;
import com.FoodOrderingApp.foodorder.customer.Customer;
import com.FoodOrderingApp.foodorder.orderitem.OrderItem;
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
@Table(name = "carts")
@NoArgsConstructor
@Getter
@Setter
@ToString

public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(nullable = false , name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "cart_quantity")
    private int cartQuantity; // Total number of items in the cart

    @Column(name = "createdAt", nullable = false , updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "cart" ,cascade = {
            CascadeType.DETACH , CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH
    })
    @JsonIgnore
    private Customer customer;


    @OneToMany(mappedBy = "cart" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<CartItem> cartItemList;


    public void addCartItem(CartItem cartItem)
    {
        if(cartItemList == null)
        {
            cartItemList = new ArrayList<>();
        }
        cartItemList.add(cartItem);
        cartItem.setCart(this); // Ensure the bidirectional relationship is maintained
        // Update cart quantity and total amount
        updateCartDetails();
    }
    public void resetCart() {
        this.cartQuantity = 0;
        this.totalAmount = BigDecimal.valueOf(0);
        cartItemList.clear();
    }
    public void removeCartItem(CartItem cartItem) {
        cartItemList.remove(cartItem);
        cartItem.setCart(null);
        updateCartDetails();
    }

    public void updateCartDetails() {
        this.cartQuantity = cartItemList.stream().mapToInt(CartItem::getQuantity).sum();
        this.totalAmount = cartItemList.stream()
                .map(cartItem -> BigDecimal.valueOf(cartItem.getMenuItem().getPrice())
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

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

}
