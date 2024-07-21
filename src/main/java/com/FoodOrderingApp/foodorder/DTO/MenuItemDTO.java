package com.FoodOrderingApp.foodorder.DTO;

import com.FoodOrderingApp.foodorder.menuitem.MenuItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MenuItemDTO {
    private String itemName;
    private String description;
    private String ingredients;
    private double price;
    private String category;

    public MenuItemDTO(MenuItem theMenuItem) {
        this.itemName = theMenuItem.getItemName();
        this.description = theMenuItem.getDescription();
        this.ingredients = theMenuItem.getIngredients();
        this.price = theMenuItem.getPrice();
        this.category = theMenuItem.getCategory();
    }
}
