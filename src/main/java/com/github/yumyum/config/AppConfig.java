package com.github.yumyum.config;

import com.github.yumyum.map.service.RestaurantService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public RestaurantService restaurantService(RestTemplate restTemplate) {
//        return new RestaurantService(restTemplate);
//    }
}
