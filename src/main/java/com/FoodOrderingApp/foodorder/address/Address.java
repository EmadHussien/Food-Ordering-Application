package com.FoodOrderingApp.foodorder.address;


import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Address {
    private int streetNumber;
    private String street;
    private String city;
    private String country;
}
