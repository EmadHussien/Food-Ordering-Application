package com.FoodOrderingApp.foodorder.order;

import com.FoodOrderingApp.foodorder.DTO.OrderRequestDTO;
import com.FoodOrderingApp.foodorder.DTO.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderRequestDTO theOrder);
    List<OrderResponseDTO> readOrder(Long OrderId);
}
