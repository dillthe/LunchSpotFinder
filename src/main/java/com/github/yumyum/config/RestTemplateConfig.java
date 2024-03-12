//package com.github.yumyum.config;
//
//import com.github.yumyum.map.web.dto.restaurant.ApiResponseHttpMessageConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Collections;
//
//@Configuration
//public class RestTemplateConfig {
//
//    @Bean
//    public RestTemplate customRestTemplate() {
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.setMessageConverters(Collections.singletonList(new ApiResponseHttpMessageConverter()));
//        return restTemplate;
//    }
//}