package com.github.yumyum.map.web.controller;

import com.github.yumyum.map.service.restaurantAPI.RestaurantAPIService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {
    private final RestaurantAPIService restaurantAPIService;

    public RestaurantController(RestaurantAPIService restaurantAPIService) {
        this.restaurantAPIService = restaurantAPIService;
    }

    @GetMapping("/api/rstr")
    public String getRestaurants() {
        return restaurantAPIService.getRestaurants();
    }
}
