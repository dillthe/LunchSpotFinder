package com.github.yumyum.map.web.controller;

import com.github.yumyum.map.repository.entity.RestaurantEntity;
import com.github.yumyum.map.service.RestaurantService;
//import com.github.yumyum.map.web.dto.restaurant.RestaurantDTO;
import com.github.yumyum.map.web.dto.restaurant.RestaurantDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class RestaurantController implements ApiController{
    private final RestaurantService restaurantService;

    @Operation(summary = "외부 API 식당 정보 가져오기")
    @GetMapping("/restaurants")
    public String getRestaurants() {
        return restaurantService.getRestaurants();
    }

    @Operation(summary = "외부 API 식당 정보 DB에 저장하기")
    @GetMapping("/restaurants/save")
    public ResponseEntity<String> saveRestaurants() {
        restaurantService.saveRestaurantsFromExternalAPI();
        return ResponseEntity.ok("외부 API 정보가 데이터베이스에 저장되었습니다.");
    }

    @Operation(summary = "사용자 반경 내 식당 정보 조회")
    @GetMapping("/restaurants/within-radius")
    public ResponseEntity<List<RestaurantEntity>> getRestaurantsWithinRadius(
            @RequestParam("latitude") BigDecimal latitude,
            @RequestParam("longitude") BigDecimal longitude,
            @RequestParam("radius") BigDecimal radius) {

        List<RestaurantEntity> restaurants = restaurantService.findRestaurantsWithinRadius(latitude, longitude, radius);
        return ResponseEntity.ok(restaurants);
    }

    @Operation(summary ="음식 카테고리별로 식당 조회하기")
    @GetMapping("/restaurants/byCategory/{cuisine}")
    public ResponseEntity<List<RestaurantEntity>> getByCategory(@PathVariable String cuisine){
        List<RestaurantEntity> restaurantsByCategory = restaurantService.restaurantsByCategory(cuisine);
        return ResponseEntity.ok(restaurantsByCategory);
    }

    @Operation(summary = "사용자 반경 내 음식 카테고리별 식당 조회")
    @GetMapping("/restaurants/within-radius-and-category")
    public ResponseEntity<List<RestaurantEntity>> getRestaurantsWithinRadiusAndCategory(
            @RequestParam("latitude") BigDecimal latitude,
            @RequestParam("longitude") BigDecimal longitude,
            @RequestParam("radius") BigDecimal radius,
            @RequestParam("cuisine") String cuisine) {

        if (cuisine != null && cuisine.equals("desired_cuisine")) {
            // Do something
        }
        List<RestaurantEntity> restaurants = restaurantService.findRestaurantsWithinRadiusAndCategory(latitude, longitude, radius, cuisine);
        return ResponseEntity.ok(restaurants);
    }

    //    @Operation(summary = "JSON 정보 임시로 넣어보기")
//    @PostMapping("/api/saveRes")
//    public String addToRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
//        Long rstrId = restaurantService.saveRestaurant(restaurantDTO);
//        return "restaurant ID: " + rstrId;
//    }
}