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
    @GetMapping("/rstr")
    public String getRestaurants() {
        return restaurantService.getRestaurants();
    }

//    @Operation(summary = "JSON 정보 임시로 넣어보기 - 성공")
//    @PostMapping("/api/saveRes")
//    public String addToRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
//        Long rstrId = restaurantService.saveRestaurant(restaurantDTO);
//        return "restaurant ID: " + rstrId;
//    }
    @Operation(summary = "외부 API 식당 정보 DB에 저장하기 - 성공")
    @GetMapping("/saveRestaurants")
    public ResponseEntity<String> saveRestaurants() {
        restaurantService.saveRestaurantsFromExternalAPI();
        return ResponseEntity.ok("외부 API 정보가 데이터베이스에 저장되었습니다.");
    }


    @GetMapping("/within-radius")
    public ResponseEntity<List<RestaurantEntity>> getRestaurantsWithinRadius(
            @RequestParam("latitude") BigDecimal latitude,
            @RequestParam("longitude") BigDecimal longitude,
            @RequestParam("radius") BigDecimal radius) {

        List<RestaurantEntity> restaurants = restaurantService.findRestaurantsWithinRadius(latitude, longitude, radius);
        return ResponseEntity.ok(restaurants);
    }

//    @Operation(summary = "반경 n km내 레스토랑 찾기")
//    @GetMapping("/rstrInRadius")
//    public List<RestaurantEntity> getRestaurantsWithinRadiusForMember(@RequestParam String memberId, @RequestParam double radius) {
//        return restaurantService.findRestaurantsWithinRadiusForMember(memberId, radius);
//    }




//    @Operation(summary = "반경 n km내 레스토랑 찾기")
//    @GetMapping("/restaurants/{memberId}")
//    public List<RestaurantDTO> getRestaurantsWithinRadius(@PathVariable String memberId) {
//        Integer memberIdInt=Integer.valueOf(memberId);
//        double radiusInKm = 5.0; // 예시로 5km 반경으로 설정
//        return restaurantService.getRestaurantsWithinRadius(memberIdInt, radiusInKm);
//    }


//    @Operation(summary = "반경 n km내 레스토랑 찾기")
//    @GetMapping("/nearby")
//    public ResponseEntity<List<RestaurantDTO>> getRestaurantsNearby(@RequestParam BigDecimal latitude,
//                                                                    @RequestParam BigDecimal longitude) {
//        List<RestaurantDTO> nearbyRestaurants = restaurantService.getRestaurantsWithinRadius(latitude, longitude, 30);
//        return ResponseEntity.ok(nearbyRestaurants);
//    }
}