package com.FoodOrderingApp.foodorder.cartitem;

import com.FoodOrderingApp.foodorder.DTO.MenuItemDTO;
import com.FoodOrderingApp.foodorder.cart.Cart;
import com.FoodOrderingApp.foodorder.menuitem.MenuItem;
import com.FoodOrderingApp.foodorder.order.Order;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity")
    private int quantity;
    @Column(name = "special_instructions")
    private String specialInstructions;

    @ManyToOne(fetch = FetchType.LAZY ,cascade = {
            CascadeType.DETACH , CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH
    })
    @JoinColumn(name= "cart_id")
    @JsonIgnore
    private Cart cart;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY ,cascade = {
            CascadeType.DETACH , CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH
    })
    @JoinColumn(name= "menuItem_id")
    private MenuItem menuItem;

    @Transient  // This ensures the field is not persisted to the database
    private MenuItemDTO item;

    public CartItem(int quantity, String specialInstructions) {
        this.quantity = quantity;
        this.specialInstructions = specialInstructions;
    }
    /*
    Cart (1) --- (Many) CartItem: One cart can have many cart items
    MenuItem (1) --- (Many) CartItem: One menu item can be represented in many cart items

    MenuItem: Pepperoni Pizza, Quantity: 2, Instructions: "Extra cheese"
    MenuItem: Garlic Bread, Quantity: 1, Instructions: None

*/
}
