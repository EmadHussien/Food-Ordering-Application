package com.FoodOrderingApp.foodorder.menuitem;

import com.FoodOrderingApp.foodorder.DTO.MenuItemReviewListResponseDTO;
import com.FoodOrderingApp.foodorder.cartitem.CartItem;
import com.FoodOrderingApp.foodorder.itemreview.ItemReview;
import com.FoodOrderingApp.foodorder.orderitem.OrderItem;
import com.FoodOrderingApp.foodorder.restaurant.Restaurant;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "menu_items")
@NoArgsConstructor
@Getter
@Setter
@ToString

public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "item_name")
    private String itemName;
    @Column(name = "description")
    private String description;
    @Column(name = "ingredients")
    private String ingredients;

    @Column(name = "price")
    private double price;
    @Column(name = "category")
    private String category; // e.g., "Appetizer", "Main Course", "Dessert"
    @Column(name = "is_available")
    @JsonProperty("isAvailable")
    private boolean isAvailable; // To mark if the item is currently available
    @Column(name = "createdAt", nullable = false , updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY ,cascade = {
            CascadeType.DETACH , CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH
    })
    @JoinColumn(name= "restaurant_id")
    @JsonIgnore
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menuItem" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ItemReview> itemReviewList;

    @Transient
    private List<MenuItemReviewListResponseDTO> itemReviews;

    @JsonIgnore
    @OneToMany(mappedBy = "menuItem" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList;

    @JsonBackReference
    @OneToMany(mappedBy = "menuItem" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<CartItem> cartItemList;



    public MenuItem(String itemName, String description, String ingredients, double price, String category, boolean isAvailable) {
        this.itemName = itemName;
        this.description = description;
        this.ingredients = ingredients;
        this.price = price;
        this.category = category;
        this.isAvailable = isAvailable;
    }


    public void addReviews(ItemReview itemReview)
    {
        if(itemReviewList == null)
        {
            itemReviewList = new ArrayList<ItemReview>();
        }
        itemReviewList.add(itemReview);
        itemReview.setMenuItem(this);
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
    // if scaled
    /*
    private String imageUrl; // URL to an image of the item
    */

}
