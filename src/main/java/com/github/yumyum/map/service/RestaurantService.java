package com.github.yumyum.map.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yumyum.exceptions.InvalidValueException;
import com.github.yumyum.exceptions.NotAcceptException;
import com.github.yumyum.exceptions.NotFoundException;
import com.github.yumyum.map.repository.RestaurantRepository;
import com.github.yumyum.map.repository.entity.RestaurantEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.io.IOException;
import java.math.BigDecimal;
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

    private final RestTemplate restTemplate;
//    private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);

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

    //외부 API 식당 운영시간 및 운영정보 불러오기
    public String getRestaurantOperation() { return restTemplate.getForObject(buildRequestUrl("/api/rstr/oprt"), String.class);
    }

    //외부 API 음식 이미지 정보 불러오기
    public String getFoodImage() { return restTemplate.getForObject(buildRequestUrl("/api/food/img"), String.class);
    }
    //외부 API 식당 이미지 정보 불러오기
    public String getRestaurantImage() { return restTemplate.getForObject(buildRequestUrl("/api/rstr/img"), String.class);
    }

    //외부 API 식당 메뉴 정보 불러오기
    public String getMenu() {return restTemplate.getForObject(buildRequestUrl("/api/menu/korean"), String.class);
    }
    //외부 API 식당 평점 정보 불러오기
    public String getReviews() {return restTemplate.getForObject(buildRequestUrl("/api/rstr/qlt"), String.class);
    }

    //아래 서비스는 오픈 API 서비스에 없는 것 같음.
    public String getReviewsById(String id) {
        Integer idInt = Integer.valueOf(id);
        return restTemplate.getForObject(buildRequestUrl("/api/rstr/qlt/" + idInt), String.class);
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

    //사용자 지정 반경 내 식당 정보 조회
    public List<RestaurantEntity> findRestaurantsWithinRadius(BigDecimal latitude, BigDecimal longitude, BigDecimal radius) {
        List<RestaurantEntity> restaurants = restaurantRepository.findRestaurantsWithinRadiusForMember(latitude, longitude, radius);
        return restaurants;
    }

    //음식 카테고리 별로 식당 조회하기
    public List<RestaurantEntity> restaurantsByCategory(String cuisine) {
        List<RestaurantEntity> restaurantByCategory = restaurantRepository.findByCuisine(cuisine);
        return restaurantByCategory;
    }

    //사용자 반경 n km 내 음식 카테고리별로 식당 조회하기 - 음식종류 분류 안하고 싶은 경우에는 위 기본코드를 사용해야함.
    public List<RestaurantEntity> findRestaurantsWithinRadiusAndCategory(BigDecimal latitude, BigDecimal longitude, BigDecimal radius, String cuisine) {
        List<RestaurantEntity> restaurants = restaurantRepository.findRestaurantsWithinRadiusForMember(latitude, longitude, radius);

        if (cuisine != null) {
            List<RestaurantEntity> filteredRestaurants = restaurants.stream()
                    .filter(restaurant -> cuisine.equals(restaurant.getCuisine()))
                    .collect(Collectors.toList());
            if (filteredRestaurants.isEmpty()) {
                throw new NotFoundException( "지정하신 반경 내 해당 음식(" + cuisine + ")을 판매하는 식당을 찾지 못했습니다.");
            } else {
                return filteredRestaurants;
            }
        }
         else {
             if(restaurants.isEmpty()){
                throw new NotFoundException("지정하신 반경 내에 있는 식당을 찾지 못했습니다.");
             }else {
                 return restaurants;
             }
        }
    }



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

//    //외부 API 식당 메뉴 설명 불러오기
//    public String getMenuDscrn() {return restTemplate.getForObject(buildRequestUrl("/api/menu-dscrn/korean"), String.class);
//    }