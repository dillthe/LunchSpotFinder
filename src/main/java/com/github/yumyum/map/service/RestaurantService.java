package com.github.yumyum.map.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yumyum.exceptions.NotAcceptException;
import com.github.yumyum.map.repository.entity.RestaurantEntity;
import com.github.yumyum.map.repository.restaurant.RestaurantJpaRepository;
import com.github.yumyum.map.service.mapper.RestaurantMapper;
import com.github.yumyum.map.web.dto.restaurant.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


@RequiredArgsConstructor
@Slf4j
@Service
public class RestaurantService {

    private final String API_URL = "https://seoul.openapi.redtable.global";
    private final String SERVICE_KEY = "2Bddfov1kOBIF6qf9y9XVS9aWPuSSHaauwrAxK1mvtwFQy2r7FUpWSPfwAUFQv8G";

    private final RestaurantJpaRepository restaurantJpaRepository;

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

    // 외부 API 식당정보 DB에 저장하기  - 지금은 JSON NODE로 해봤는데 또 안됨. ㅡ_ㅡ ㅋ
    public void saveRestaurantsFromExternalAPI() throws JsonProcessingException {
        String url = buildRequestUrl("/api/rstr");
        ResponseEntity<ApiResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, ApiResponse.class);
        ApiResponse response = responseEntity.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = objectMapper.writeValueAsString(response.getBody());
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode bodyNode = rootNode.get("body");
        if (bodyNode != null && !bodyNode.isNull()) {
            for (JsonNode node : bodyNode) {
                // JSON 객체를 Restaurant 객체로 변환
                RestaurantEntity restaurant = new RestaurantEntity();
                restaurant.setRstrId(node.get("RSTR_ID").asInt());
                restaurant.setRstrNm(node.get("RSTR_NM").asText());
                restaurant.setRstrRdnmadr(node.get("RSTR_RDNMADR").asText());
                restaurant.setRstrLnnoAdres(node.get("RSTR_LNNO_ADRES").asText());
                restaurant.setRstrLa(node.get("RSTR_LA").asDouble());
                restaurant.setRstrLo(node.get("RSTR_LO").asDouble());
                restaurant.setRstrTelno(node.get("RSTR_TELNO").asText());
                restaurant.setBsnsStatmBzcndNm(node.get("BSNS_STATM_BZCND_NM").asText());
                restaurant.setBsnsLcncNm(node.get("BSNS_LCNC_NM").asText());
                restaurant.setRstrIntrcnCont(node.get("RSTR_INTRCN_CONT").asText());
                restaurant.setRstrAreaClsfNm(node.get("RSTR_AREA_CLSF_NM").asText());

                // Restaurant 객체를 데이터베이스에 저장
                restaurantJpaRepository.save(restaurant);
            }
        }

    }

//
//    public void saveRestaurantsFromExternalAPI() {
//        RestTemplate restTemplate = new RestTemplate();
//        ApiResponse response = restTemplate.getForObject(buildRequestUrl("/api/rstr"), ApiResponse.class);

    public Integer saveRestaurant(RestaurantBody restaurantBody) {
        RestaurantEntity restaurantEntity = RestaurantMapper.INSTANCE.idAndRestaurantBodytoRestaurantEntity(null, restaurantBody);
//        restaurantEntity.setRstrId(restaurantDTO.getRSTR_ID());
        RestaurantEntity restaurantEntityCreated;
        try {
            restaurantEntityCreated = restaurantJpaRepository.save(restaurantEntity);
        } catch (RuntimeException exception) {
            throw new NotAcceptException("Restaurant 정보를 저장하는 도중에 Error 가 발생하였습니다.");
        }
        return restaurantEntityCreated.getRstrId();
    }}


//    @Transactional
//    public void saveRestaurantsFromExternalAPI() {
//        RestTemplate restTemplate = new RestTemplate();
//        ApiResponse response = restTemplate.getForObject(buildRequestUrl("/api/rstr"), ApiResponse.class);
//
//        if (response != null && response.getBody() != null) {
//            List<Data> dataList = response.getBody();
//            // dataList를 데이터베이스에 저장하는 로직을 작성
//
//
//        }
//
//        try {
//            ResponseEntity<RestaurantDTO[]> responseEntity = restTemplate.getForObject(apiUrl, RestaurantDTO[].class);
//            if (responseEntity.getBody() != null) {
//                List<RestaurantDTO> restaurantDTOs = Arrays.asList(responseEntity.getBody());
//                List<RestaurantEntity> restaurants = restaurantDTOs.stream()
//                        .map(RestaurantMapper.INSTANCE::toEntity)
//                        .collect(Collectors.toList());
//                restaurantJpaRepository.save(restaurants);
//            } else {
//                System.out.println("API 응답이 null입니다. 기본값을 설정합니다.");
//            }
//        } catch (Exception e) {
//            System.out.println("에러 발생: " + e.getMessage());
//        }
//    }

//    @Transactional
//    public void saveRestaurantsFromExternalAPI() {
//        String apiUrl = buildRequestUrl("/api/rstr");
//        try {
//            ResponseEntity<ApiResponse<RestaurantDTO>> responseEntity = restTemplate.exchange(
//                    apiUrl,
//                    HttpMethod.GET,
//                    null,
//                    new ParameterizedTypeReference<>() {});
//
//            if (responseEntity.getBody() != null) {
//                List<RestaurantDTO> restaurantDTOs = responseEntity.getBody().getBody();
//                List<Restaurant> restaurants = restaurantDTOs.stream()
//                        .map(RestaurantMapper.INSTANCE::toEntity)
//                        .collect(Collectors.toList());
//                restaurantRepository.saveAll(restaurants);
//            } else {
//                System.out.println("API 응답이 null입니다. 기본값을 설정합니다.");
//            }
//        } catch (Exception e) {
//            System.out.println("에러 발생: " + e.getMessage());
//        }
//    }
