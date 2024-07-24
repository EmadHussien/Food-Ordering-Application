package com.FoodOrderingApp.foodorder.order;

import com.FoodOrderingApp.foodorder.DTO.OrderRequestDTO;
import com.FoodOrderingApp.foodorder.DTO.OrderResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public Order createOrder(@RequestParam String token ,
                             @RequestParam(defaultValue = "usd") String currency
                            , @RequestBody OrderRequestDTO orderRequestDTO) throws Exception {

        return orderService.createOrder(orderRequestDTO,token,currency);
    }
    @GetMapping("/orders/customer/{customerId}")
    public List<OrderResponseDTO> readOrder(@PathVariable Long customerId)
    {
        return orderService.readOrder(customerId);
    }


}
