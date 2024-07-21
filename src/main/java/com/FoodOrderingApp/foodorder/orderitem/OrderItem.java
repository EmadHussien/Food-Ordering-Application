package com.FoodOrderingApp.foodorder.orderitem;

import com.FoodOrderingApp.foodorder.DTO.MenuItemDTO;
import com.FoodOrderingApp.foodorder.menuitem.MenuItem;
import com.FoodOrderingApp.foodorder.order.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "order_items")

public class OrderItem {

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
    @JoinColumn(name= "menuItem_id")
    @JsonIgnore
    private MenuItem menuItem;

    @ManyToOne(fetch = FetchType.LAZY ,cascade = {
            CascadeType.DETACH , CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH
    })
    @JoinColumn(name= "order_id")
    @JsonIgnore
    private Order order;


    @Transient  // This ensures the field is not persisted to the database
    private MenuItemDTO item;

    public OrderItem(int quantity, String specialInstructions) {
        this.quantity = quantity;
        this.specialInstructions = specialInstructions;
    }
}
