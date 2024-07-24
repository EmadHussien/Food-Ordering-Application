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
import com.FoodOrderingApp.foodorder.payment.Payment;
import com.FoodOrderingApp.foodorder.payment.PaymentService;
import com.FoodOrderingApp.foodorder.restaurant.Restaurant;
import com.FoodOrderingApp.foodorder.restaurant.RestaurantService;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    private PaymentService paymentService;


    @Autowired
    public OrderServiceImpl(MenuItemService menuItemService,OrderDAO orderDAO, CustomerService customerService, RestaurantService restaurantService, PaymentService paymentService) {
        this.orderDAO = orderDAO;
        this.customerService = customerService;
        this.restaurantService = restaurantService;
        this.menuItemService = menuItemService;
        this.paymentService = paymentService;
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
    public Order createOrder(OrderRequestDTO theOrder, String token, String currency) throws Exception {
//        Customer: Places an order for food items.
//
//        System: Calculates the total cost and presents payment options.
//
//        Customer: Selects a payment method and enters payment details.
//
//        System: Sends payment details to the payment gateway.
//
//        Payment Gateway: Processes the payment and returns a confirmation.
//
//        System: Confirms the order and sends it to the kitchen.
//
//        Kitchen: Prepares the order.
//        Delivery: Delivers the order to the customer.
//
//        Customer: Receives the order and the digital receipt.

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

        Charge paymentInfo = paymentService.processPayment(order.getTotalAmount().longValue(),currency,token ,customer);
        // creating payment object record
        Payment payment = new Payment(
                paymentInfo.getPaymentMethod(),paymentInfo.getId(),
                (paymentInfo.getAmount()/100), Payment.PaymentStatus.COMPLETED,
                paymentInfo.getReceiptUrl()
                );

        // setting up the relations
        customer.addPayments(payment);
        payment.setOrder(order);
        order.setPayment(payment);
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
