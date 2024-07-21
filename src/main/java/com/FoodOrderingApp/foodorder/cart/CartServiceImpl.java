package com.FoodOrderingApp.foodorder.cart;
import com.FoodOrderingApp.foodorder.DTO.CartItemDTO;import com.FoodOrderingApp.foodorder.DTO.MenuItemDTO;
import com.FoodOrderingApp.foodorder.cartitem.CartItem;
import com.FoodOrderingApp.foodorder.cartitem.CartItemService;
import com.FoodOrderingApp.foodorder.customer.Customer;
import com.FoodOrderingApp.foodorder.customer.CustomerService;import com.FoodOrderingApp.foodorder.menuitem.MenuItem;
import com.FoodOrderingApp.foodorder.menuitem.MenuItemService;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CartServiceImpl implements CartService{
    private CartDAO cartDAO ;
    private CustomerService customerService;
    private MenuItemService menuItemService;
    private CartItemService cartItemService;
    @Autowired
    public CartServiceImpl(CartDAO cartDAO , CustomerService customerService , MenuItemService menuItemService , CartItemService cartItemService) {
        this.cartDAO = cartDAO;
        this.customerService = customerService;
        this.menuItemService = menuItemService;
        this.cartItemService = cartItemService;
    }
    private Cart convertToMenuItemDTO(Cart cart)    {
        cart.getCartItemList().forEach(cartItem -> {
            MenuItemDTO menuItemDTO = new MenuItemDTO(cartItem.getMenuItem());
        // Set the MenuItemDTO instead
            cartItem.setItem(menuItemDTO);
                  });
            return cart;
        }

        @Override
        public Cart getCustomerCart(Long customerId) {
            Customer customer = customerService.readCustomer(customerId);
            // Convert CartItems to CartItemDTOs
            Cart cart = customer.getCart();
            return convertToMenuItemDTO(cart);
        }
        @Override
        @Transactional
        public Cart addCartItem(Long customerId, CartItemDTO theCartItemDTO) {
            Customer customer = customerService.readCustomer(customerId);
            CartItem theCartItem = new CartItem(theCartItemDTO.getQuantity(),theCartItemDTO.getSpecialInstructions());

            MenuItem menuItem = menuItemService.readMenuItem(theCartItemDTO.getMenuItemId());

            theCartItem.setMenuItem(menuItem);
            customer.getCart().addCartItem(theCartItem);
            theCartItem.setCart(customer.getCart());
            Cart cart =  cartDAO.save(customer.getCart());
            return convertToMenuItemDTO(cart);
    }
        @Override
        @Transactional
        public void removeCartItem(Long customerId, Long theItemId) {
            // fetch cart item by id
            // fetch the cart it belongs to
            CartItem cartItem = cartItemService.findCartItemById(theItemId);
            Cart cart = cartItem.getCart();
            cart.removeCartItem(cartItem);
            cartDAO.save(cart);
            cartItemService.removeCartItemById(theItemId);
        }

        @Override
        @Transactional
        public void clearCart(Long customerId) {
            Customer customer = customerService.readCustomer(customerId);
            Cart cart = customer.getCart();
            // Delete all cart items associated with this cart from the database
            for (CartItem cartItem : cart.getCartItemList()) {
                cartItemService.removeCartItemById(cartItem.getId());
            }
            cart.resetCart();
            cartDAO.save(cart);
        }
}