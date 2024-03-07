package com.github.yumyum.map.service.restaurantAPI;

import com.github.yumyum.map.repository.RestaurantRepository;
import com.github.yumyum.map.repository.entity.RestaurantEntity;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Slf4j
@Service
public class RestaurantAPIService {
    private static final String API_URL = "https://seoul.openapi.redtable.global";
    private static final String SERVICE_KEY = "2Bddfov1kOBIF6qf9y9XVS9aWPuSSHaauwrAxK1mvtwFQy2r7FUpWSPfwAUFQv8G";

    private final RestTemplate restTemplate;
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
}

//    public void fetchDataAndSaveToDB(String apiUrl) {
//        apiUrl = API_URL;
//        ResponseEntity<ApiResponse> response = RestTemplate.getForEntity(apiUrl, ApiResponse.class);
//        ApiResponse responseBody= response.getBody();
//
//        ApiResponse apiResponse = (ApiResponse) responseBody;
//        RestaurantEntity restaurantEntity = new RestaurantEntity();
//        restaurantEntity.setName(apiResponse.getName());
//        restaurantEntity.setAddress(responseBody.annotationType().getName());
//        restaurantEntity.setName(responseBody.annotationType().getName());
//        restaurantEntity.setName(responseBody.annotationType().getName());
//        restaurantEntity.setValue(responseBody.getValue());
//        restaurantRepository.save(restaurantEntity);
//}
//    private String name; /     private String address;
//    private Point locationPoint; /     private String phoneNumber;
//    private String cuisine;

//    public String buildRequestUrl() {
//        String encodedServiceKey = getEncodedServiceKey();
//        return API_URL + "?serviceKey=" + encodedServiceKey;
//    }