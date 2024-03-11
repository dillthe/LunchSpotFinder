package com.github.yumyum.map.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yumyum.map.repository.RestaurantRepository;
import com.github.yumyum.map.repository.entity.Restaurant;
import com.github.yumyum.map.service.mapper.RestaurantMapper;
import com.github.yumyum.map.web.dto.restaurant.RestaurantDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Slf4j
@Service
public class RestaurantService {

    private final String API_URL = "https://seoul.openapi.redtable.global";
    private final String SERVICE_KEY = "2Bddfov1kOBIF6qf9y9XVS9aWPuSSHaauwrAxK1mvtwFQy2r7FUpWSPfwAUFQv8G";

    private final RestTemplate restTemplate;
    private final RestaurantRepository restaurantRepository;
    private RestaurantMapper restaurantMapper;
    private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);

    public List<Restaurant> getAllYourEntities() {
        return restaurantRepository.findAll();
    }

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

    public String getRestaurants() {
        return restTemplate.getForObject(buildRequestUrl("/api/rstr"), String.class);
    }

    @Transactional
    public void saveRestaurantsFromExternalAPI() {
        String apiUrl = buildRequestUrl("/api/rstr");
        try {
            ResponseEntity<Response> responseEntity = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Response>() {
                    }
            );

            Response response = responseEntity.getBody();
            if (response != null && response.getBody() != null) {
                List<RestaurantDTO> restaurantDTOs = response.getBody();
                List<Restaurant> restaurants = restaurantDTOs.stream()
                        .map(RestaurantMapper.INSTANCE::toEntity)
                        .collect(Collectors.toList());
                restaurantRepository.saveAll(restaurants);
            } else {
                logger.warn("API 응답이 null입니다. 기본값을 설정합니다.");
            }
        } catch (Exception e) {
            logger.error("에러 발생: {}", e.getMessage(), e);
        }
    }
}

//    @Transactional
//    public void saveRestaurantsFromExternalAPI() {
//        String apiUrl = buildRequestUrl("/api/rstr");
//        try {
//            ResponseEntity<RestaurantsDTO> responseEntity = restTemplate.getForEntity(apiUrl, RestaurantsDTO.class);
//            if (responseEntity.getBody() != null) {
//                List<RestaurantsDTO> restaurantDTOs = Collections.singletonList(responseEntity.getBody());
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
//}
