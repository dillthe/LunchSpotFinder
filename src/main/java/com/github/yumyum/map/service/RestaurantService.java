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


    //외부 API 정보 DB에 저장하기
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

    //사용자 반경 내 식당 정보 조회
    public List<RestaurantEntity> findRestaurantsWithinRadius(BigDecimal latitude, BigDecimal longitude, BigDecimal radius) {
        List<RestaurantEntity> restaurants = restaurantRepository.findRestaurantsWithinRadiusForMember(latitude, longitude, radius);
        return restaurants;
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
}