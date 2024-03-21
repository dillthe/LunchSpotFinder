package com.github.yumyum.map.web.controller;

import com.github.yumyum.map.repository.entity.RestaurantEntity;
import com.github.yumyum.map.service.RestaurantService;
//import com.github.yumyum.map.web.dto.restaurant.RestaurantDTO;
import com.github.yumyum.map.web.dto.restaurant.RestaurantDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "식당 운영정보(시간, 좌석수, 주차, wifi, 놀이방, 반려동물, 다국어메뉴, 휴무일정, 예약방식, 홈페이지 URL, 배달정보 등) 가져오기")
    @GetMapping("/restaurants/operation")
    public String getRestaurantOperation() {
        return restaurantService.getRestaurantOperation();
    }

    @Operation(summary = "음식 이미지 가져오기")
    @GetMapping("/restaurants/food-image")
    public String getFoodImage() {
        return restaurantService.getFoodImage();
    }
    @Operation(summary = "식당 이미지 가져오기")
    @GetMapping("/restaurants/rstr-image")
    public String getRestaurantImage() {
        return restaurantService.getRestaurantImage();
    }
    @Operation(summary = "메뉴 정보 가져오기")
    @GetMapping("/menu")
    public String getMenu() {
        return restaurantService.getMenu();
    }
    @Operation(summary = "평점 정보 가져오기")
    @GetMapping("/reviews")
    public String getReviews() {
        return restaurantService.getReviews();
    }

    @Operation(summary = "특정 식당 평점 정보 가져오기")
    @GetMapping("/reviews/{id}")
    public String getReviews(@PathVariable String id) {
        return restaurantService.getReviewsById(id);
    }



    @Operation(summary = "외부 API 식당 정보 DB에 저장하기")
    @GetMapping("/restaurants/save")
    public ResponseEntity<String> saveRestaurants() {
        restaurantService.saveRestaurantsFromExternalAPI();
        return ResponseEntity.ok("외부 API 정보가 데이터베이스에 저장되었습니다.");
    }

    @Operation(summary = "사용자 지정 반경 내 식당 정보 조회-위도 경도 및 반경 정보 필요")
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

    @Operation(summary = "지정 반경 설정, 음식 카테고리별 식당 조회 - 위도 경도 및 반경 정보 필요, ")
    @GetMapping("/restaurants/within-radius-and-category")
    public ResponseEntity<List<RestaurantEntity>> getRestaurantsWithinRadiusAndCategory(
            @RequestParam("latitude") BigDecimal latitude,
            @RequestParam("longitude") BigDecimal longitude,
            @RequestParam("radius") BigDecimal radius,
            @RequestParam("cuisine") String cuisine) {
        List<RestaurantEntity> restaurants = restaurantService.findRestaurantsWithinRadiusAndCategory(latitude, longitude, radius, cuisine);
        return ResponseEntity.ok(restaurants);
    }

    //    @Operation(summary = "JSON 정보 임시로 넣어보기")
//    @PostMapping("/api/saveRes")
//    public String addToRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
//        Long rstrId = restaurantService.saveRestaurant(restaurantDTO);
//        return "restaurant ID: " + rstrId;
//    }
//
//    @Operation(summary = "메뉴 설명 가져오기")
//    @GetMapping("/menu/info")
//    public String getMenuDescription() {
//        return restaurantService.getMenuDscrn();
//    }
}