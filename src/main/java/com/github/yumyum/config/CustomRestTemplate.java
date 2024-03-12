//package com.github.yumyum.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//public class CustomRestTemplate extends RestTemplate {
//
//    public CustomRestTemplate() {
//        super();
//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
//        converter.setObjectMapper(new ObjectMapper());
//        messageConverters.add(converter);
//        this.setMessageConverters(messageConverters);
//    }
//}