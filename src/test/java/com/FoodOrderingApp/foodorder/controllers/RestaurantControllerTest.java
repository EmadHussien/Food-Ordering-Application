package com.FoodOrderingApp.foodorder.controllers;
import com.FoodOrderingApp.foodorder.restaurant.Restaurant;
import com.FoodOrderingApp.foodorder.restaurant.RestaurantController;
import com.FoodOrderingApp.foodorder.restaurant.RestaurantServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RestaurantServiceImpl restaurantService;

    @Test
    public void testReadRestaurant() throws Exception
    {
        Long restaurantId = 123L;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setRestaurantName("Wahmy");
        when(restaurantService.readRestaurant(restaurantId)).thenReturn(restaurant);
        mockMvc.perform(get("/restaurants/{restaurantId}", restaurantId))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(restaurantId))
                .andExpect(jsonPath("$.restaurantName").value("Wahmy"));
        verify(restaurantService, times(1)).readRestaurant(restaurantId);
    }


    @Test
    public void testDeleteRestaurant() throws Exception
    {
        Long restaurantId = 123L;

        doNothing().when(restaurantService).deleteRestaurant(restaurantId);

        mockMvc.perform(delete("/restaurants/{restaurantId}", restaurantId))
                .andExpect(status().isOk())
                .andExpect(content().string("Restaurant with id - " + restaurantId + " has been deleted successfully"));
                verify(restaurantService, times(1)).deleteRestaurant(restaurantId);

    }

}
