package com.github.yumyum.map.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yumyum.exceptions.InvalidValueException;
import com.github.yumyum.exceptions.NotAcceptException;
import com.github.yumyum.map.repository.MemberRepository;
import com.github.yumyum.map.repository.RestaurantRepository;
import com.github.yumyum.map.repository.entity.MemberEntity;
import com.github.yumyum.map.repository.entity.RestaurantEntity;
import com.github.yumyum.map.service.mapper.RestaurantMapper;
import com.github.yumyum.map.web.dto.restaurant.RestaurantDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Slf4j
@Service
public class RestaurantService {

    private final String API_URL = "https://seoul.openapi.redtable.global";
    private final String SERVICE_KEY = "2Bddfov1kOBIF6qf9y9XVS9aWPuSSHaauwrAxK1mvtwFQy2r7FUpWSPfwAUFQv8G";

    private final RestaurantRepository restaurantRepository;
    private final MemberRepository memberRepository;

    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);

    public String getEncodedServiceKey() {
        return URLEncoder.encode(SERVICE_KEY, StandardCharsets.UTF_8);
    }

    public String buildRequestUrl(String path) {
        return UriComponentsBuilder.fromUriString(API_URL)
                .path(path)
                .queryParam("serviceKey", getEncodedServiceKey())
                .build()
                .toUriString();
    }

    //외부 API 식당 정보 불러오기
    public String getRestaurants() {
        return restTemplate.getForObject(buildRequestUrl("/api/rstr"), String.class);
    }


//    임시로 JSON 데이터 저장해보기 - 성공
//    public Long saveRestaurant(RestaurantDTO restaurantDTO) {
//        RestaurantEntity restaurantEntity = RestaurantMapper.INSTANCE.restaurantDTOtoRestaurantEntity(restaurantDTO);
//        RestaurantEntity restaurantEntityCreated;
//        try {
//            restaurantEntityCreated = restaurantJpaRepository.save(restaurantEntity);
//        } catch (RuntimeException exception) {
//            throw new NotAcceptException("Restaurant 정보를 저장하는 도중에 Error 가 발생하였습니다.");
//        }
//        return restaurantEntityCreated.getRstrId();
//    }

    //외부 API 정보 DB에 저장하기 - 성공
    public void saveRestaurantsFromExternalAPI() {
        RestTemplate restTemplate = new RestTemplate();
        String json = restTemplate.getForObject(buildRequestUrl("/api/rstr"), String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readTree(json);
        } catch (IOException e) {
            e.printStackTrace();
            throw new InvalidValueException("JSON data를 읽을 수 없습니다.");
        }

        if (rootNode != null && rootNode.has("body")) {
            JsonNode bodyNode = rootNode.get("body");
            for (JsonNode restaurantNode : bodyNode) {
                RestaurantEntity restaurant = mapper.convertValue(restaurantNode, RestaurantEntity.class);
                restaurantRepository.save(restaurant);
            }
        } else {
            throw new NotAcceptException("JSON data를 저장할 수 없습니다.");
        }
    }

    public List<RestaurantEntity> findRestaurantsWithinRadius(BigDecimal latitude, BigDecimal longitude, BigDecimal radius) {
        List<RestaurantEntity> restaurants = restaurantRepository.findRestaurantsWithinRadiusForMember(latitude, longitude, radius);
        return restaurants;
    }

    //
//    public List<RestaurantEntity> findRestaurantsWithinRadiusForMember(String memberId, double radius) {
//        Integer memberIdInt = Integer.valueOf(memberId);
//        return restaurantRepository.findRestaurantsWithinRadiusForMember(memberId, radius);
//    }





//    public List<RestaurantDTO> getRestaurantsWithinRadius(Integer memberId, double radiusInKm) {
//        MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("멤버를 찾을 수 없습니다."));
//        Point memberPoint = new Point((int) memberEntity.getLatitude().doubleValue(), (int) memberEntity.getLongtitude().doubleValue());
//
//        List<RestaurantEntity> restaurants = restaurantRepository.findByLocationNear(memberPoint, new Distance(radiusInKm, Metrics.KILOMETERS));
//
//        return restaurants.stream()
//                .map(RestaurantMapper.INSTANCE::restaurantEntityToRestaurantDTO)
//                .collect(Collectors.toList());
//    }



//    내 위치 반경 기준 식당 표시
//    public List<RestaurantDTO> getRestaurantsWithinRadius(BigDecimal latitude, BigDecimal longitude, double radiusInKm) {
//        BigDecimal radiusInDegrees = BigDecimal.valueOf(radiusInKm / 111.12);
//
//        BigDecimal minLat = latitude.subtract(radiusInDegrees);
//        BigDecimal maxLat = latitude.add(radiusInDegrees);
//        BigDecimal minLon = longitude.subtract(radiusInDegrees);
//        BigDecimal maxLon = longitude.add(radiusInDegrees);
//
//        List<RestaurantEntity> restaurants = restaurantRepository.findRestaurantsInBoundingBox(
//                minLat, maxLat, minLon, maxLon);
//
//        return restaurants.stream()
//                .filter(restaurant -> isWithinRadius(latitude, longitude, restaurant, BigDecimal.valueOf(radiusInKm)))
//                .map(RestaurantMapper.INSTANCE::restaurantEntityToRestaurantDTO)
//                .collect(Collectors.toList());
//    }
//
//    private boolean isWithinRadius(BigDecimal latitude, BigDecimal longitude, RestaurantEntity restaurant, BigDecimal radiusInKm) {
//        BigDecimal restaurantLat = new BigDecimal(String.valueOf(restaurant.getRsLatitude()));
//        BigDecimal restaurantLon = new BigDecimal(String.valueOf(restaurant.getRsLongtitude()));
//        BigDecimal distance = calculateDistance(latitude, longitude, restaurantLat, restaurantLon);
//
//        return distance.compareTo(radiusInKm) <= 0;
//    }
//
//    private BigDecimal calculateDistance(BigDecimal lat1, BigDecimal lon1, BigDecimal lat2, BigDecimal lon2) {
//        // Use a formula to calculate the distance between two points on Earth (e.g., Haversine formula)
//        // For simplicity, you can use existing libraries like GeoTools or GeoDistance
//        // Here is a simplified version (not very accurate)
//        double deltaLat = Math.toRadians(lat2.subtract(lat1).doubleValue());
//        double deltaLon = Math.toRadians(lon2.subtract(lon1).doubleValue());
//        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
//                Math.cos(Math.toRadians(lat1.doubleValue())) * Math.cos(Math.toRadians(lat2.doubleValue())) *
//                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        double earthRadius = 6371; // in km
//        return BigDecimal.valueOf(earthRadius * c).setScale(2, RoundingMode.HALF_UP);
//    }


}