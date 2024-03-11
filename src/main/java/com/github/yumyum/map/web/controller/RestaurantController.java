package com.github.yumyum.map.web.controller;

import com.github.yumyum.map.repository.entity.Restaurant;
import com.github.yumyum.map.service.RestaurantService;
import com.github.yumyum.map.web.dto.restaurant.RestaurantDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping("/api/rstr")
    public String getRestaurants() {
        return restaurantService.getRestaurants();
    }


    @PostMapping("/api/restaurants/external")
    public ResponseEntity<String> saveRestaurantsFromExternalAPI() {
        try {
            restaurantService.saveRestaurantsFromExternalAPI();
            return ResponseEntity.ok("데이터 저장 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("데이터 저장 실패: " + e.getMessage());
        }
    }
}