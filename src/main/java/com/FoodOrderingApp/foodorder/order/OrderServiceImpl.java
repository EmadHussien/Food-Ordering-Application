package com.FoodOrderingApp.foodorder.order;

import com.FoodOrderingApp.foodorder.DTO.MenuItemDTO;
import com.FoodOrderingApp.foodorder.DTO.OrderItemDTO;
import com.FoodOrderingApp.foodorder.DTO.OrderRequestDTO;
import com.FoodOrderingApp.foodorder.DTO.OrderResponseDTO;
import com.FoodOrderingApp.foodorder.cart.Cart;
import com.FoodOrderingApp.foodorder.customer.Customer;
import com.FoodOrderingApp.foodorder.customer.CustomerService;
import com.FoodOrderingApp.foodorder.menuitem.MenuItem;
import com.FoodOrderingApp.foodorder.menuitem.MenuItemService;
import com.FoodOrderingApp.foodorder.orderitem.OrderItem;
import com.FoodOrderingApp.foodorder.restaurant.Restaurant;
import com.FoodOrderingApp.foodorder.restaurant.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO;
    private CustomerService customerService;
    private RestaurantService restaurantService;
    private MenuItemService menuItemService;

    @Autowired
    public OrderServiceImpl(MenuItemService menuItemService,OrderDAO orderDAO, CustomerService customerService, RestaurantService restaurantService) {
        this.orderDAO = orderDAO;
        this.customerService = customerService;
        this.restaurantService = restaurantService;
        this.menuItemService = menuItemService;
    }
    private List<OrderItem> mapToOrderItemList(List<OrderItemDTO> orderItemDTOs, Order order) {
        return orderItemDTOs.stream().map(dto -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItem(menuItemService.readMenuItem(dto.getMenuItemId()));
            orderItem.setQuantity(dto.getQuantity());
            orderItem.setSpecialInstructions(dto.getSpecialInstructions());
            orderItem.setOrder(order); // Ensure bidirectional relationship
            return orderItem;
        }).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public Order createOrder(OrderRequestDTO theOrder) {
        Order order = new Order(theOrder.getContactPhone(),theOrder.getDeliveryAddress());
        // converting the orderItems from dto to objects
        List<OrderItem> orderItems =
                mapToOrderItemList(theOrder.getOrderItems(), order);

        // need to set the relation with order
        order.setOrderItemList(orderItems);
        Customer customer = customerService.readCustomer(theOrder.getCustomerId());
        Restaurant restaurant = restaurantService.readRestaurant(theOrder.getRestaurantId());
        customer.addOrder(order);
        restaurant.addOrder(order);

        order.updateOrderDetails();
        return orderDAO.save(order);
    }

    @Override
    public List<OrderResponseDTO> readOrder(Long customerId) {
        Customer customer = customerService.readCustomer(customerId);
         List<Order> customerOrders = customer.getOrders();

//        return customerOrders.stream().map(order -> {
//                OrderResponseDTO orderResponseDTO = new OrderResponseDTO(order);
//                orderResponseDTO.getItems().stream().map(
//                        orderItem -> {
//                           var menuItem =  orderItem.getMenuItem();
//                            MenuItemDTO menuItemDTO = new MenuItemDTO(menuItem);
//                            orderItem.setItem(menuItemDTO);
//                            return orderItem;
//                        }
//                );
//                return orderResponseDTO;
//            }).collect(Collectors.toList());
        return customerOrders.stream().map(order -> {
            OrderResponseDTO orderResponseDTO = new OrderResponseDTO(order);

            // Map OrderItems to OrderItemDTOs
            List<OrderItem> orderItems = order.getOrderItemList().stream()
                    .map(orderItem -> {
                        MenuItem menuItem = orderItem.getMenuItem();
                        MenuItemDTO menuItemDTO = new MenuItemDTO(menuItem);
                        orderItem.setItem(menuItemDTO); // Set MenuItemDTO back to OrderItem
                        return orderItem;
                    })
                    .collect(Collectors.toList());

            orderResponseDTO.setItems(orderItems); // Set the updated list of OrderItems

            return orderResponseDTO;
        }).collect(Collectors.toList());
    }
}
