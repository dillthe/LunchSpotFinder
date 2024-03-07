package com.github.yumyum.config;

import com.github.yumyum.map.service.restaurantAPI.RestaurantAPIService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public RestaurantAPIService restaurantAPIService(RestTemplate restTemplate) {
        return new RestaurantAPIService(restTemplate);
    }
}
