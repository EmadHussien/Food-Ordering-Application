package com.FoodOrderingApp.foodorder.DTO;


import com.FoodOrderingApp.foodorder.address.Address;
import com.FoodOrderingApp.foodorder.orderitem.OrderItem;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class OrderRequestDTO {

    private Long customerId;
    private Long restaurantId;
    private String contactPhone;
    private Address deliveryAddress;
    private List<OrderItemDTO> orderItems;

}
