package com.FoodOrderingApp.foodorder;

import com.FoodOrderingApp.foodorder.address.Address;
import com.FoodOrderingApp.foodorder.customer.Customer;
import com.FoodOrderingApp.foodorder.itemreview.ItemReview;
import com.FoodOrderingApp.foodorder.menuitem.MenuItem;
import com.FoodOrderingApp.foodorder.restaurant.Restaurant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodorderApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodorderApplication.class, args);
		System.out.println("Hello World....");

	}

}

