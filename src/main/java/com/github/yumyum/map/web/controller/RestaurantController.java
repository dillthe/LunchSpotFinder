package com.github.yumyum.map.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.yumyum.map.repository.entity.RestaurantEntity;
import com.github.yumyum.map.service.RestaurantService;
import com.github.yumyum.map.service.mapper.RestaurantMapper;
import com.github.yumyum.map.web.dto.restaurant.RestaurantBody;
import com.github.yumyum.map.web.dto.restaurant.RestaurantDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
public class RestaurantController {
    private final RestaurantService restaurantService;

    @Operation(summary = "외부 API 식당 정보 가져오기")
    @GetMapping("/api/rstr")
    public String getRestaurants() {
        return restaurantService.getRestaurants();
    }


    @Operation(summary = "외부 API 식당 정보 로컬 DB에 저장하기")
    @PostMapping("/api/saveRstrApi")
    public ResponseEntity<String> saveRestaurantsFromExternalAPI() throws JsonProcessingException {
        restaurantService.saveRestaurantsFromExternalAPI();
        return ResponseEntity.ok("Data saved successfully");
    } //저장이 안됨.

    @Operation(summary = "JSON 정보 임시로 넣어보기")
    @PostMapping("/api/saveRes")
    public ResponseEntity<String> addToRestaurant(@RequestBody RestaurantBody restaurantBody){
        restaurantService.saveRestaurant(restaurantBody);
        return ResponseEntity.ok("상품이 장바구니에 추가되었습니다.");
    }
//    @PostMapping("/api/save")
//    public ResponseEntity<String> createRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
//        try {
////            RestaurantEntity restaurant = RestaurantMapper.INSTANCE.restaurantDTOtoRestaurantEntity(restaurantDTO);
////            // Check if rstrId is null and set a default value if needed
////            if (restaurant.getRstrId() == null) {
////                restaurant.setRstrId(1); // Set your default value here
////            }
//            restaurantService.saveRestaurant(restaurantDTO);
//            return ResponseEntity.ok("저장성공!");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
//        }
//    }

    // 헤더값 가져오는 예시, 바디값 가져오는 예시로 가져오기.
//    @PostMapping("/endpoint")
//    public ResponseEntity<String> myController(@RequestBody MyRequest request) {
//        Header header = request.getHeader();
//        // header.getResultCode(), header.getResultMsg() 등으로 값을 가져와서 사용
//        return ResponseEntity.ok("Success");
//    }

//    @PostMapping("/api/restaurants/external")
//    public ResponseEntity<String> saveRestaurantsFromExternalAPI() {
//        try {
//            restaurantService.saveRestaurantsFromExternalAPI();
//            return ResponseEntity.ok("데이터 저장 성공");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("데이터 저장 실패: " + e.getMessage());
//        }
//    }
}