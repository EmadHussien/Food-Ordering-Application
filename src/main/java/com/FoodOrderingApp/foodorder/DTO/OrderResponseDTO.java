package com.FoodOrderingApp.foodorder.DTO;

import com.FoodOrderingApp.foodorder.order.Order;
import com.FoodOrderingApp.foodorder.orderitem.OrderItem;
import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class OrderResponseDTO {
    private Long orderId;
    private LocalDateTime orderDateTime;
    private int totalQuantity;
    private BigDecimal totalAmount;
    private Order.OrderStatus status;
    private List<OrderItem> items;



    public OrderResponseDTO(Order order) {
        this.orderId = order.getId();
        this.orderDateTime = order.getOrderDateTime() ;
        this.totalQuantity = order.getQuantity();
        this.totalAmount = order.getTotalAmount();
        this.status = order.getStatus();
        this.items = order.getOrderItemList();
    }
}
