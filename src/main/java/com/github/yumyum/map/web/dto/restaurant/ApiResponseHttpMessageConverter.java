//package com.github.yumyum.map.web.dto.restaurant;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.HttpOutputMessage;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageNotWritableException;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//
//import java.io.IOException;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.util.Collections;
//
//public class ApiResponseHttpMessageConverter extends MappingJackson2HttpMessageConverter {
//
//    public ApiResponseHttpMessageConverter() {
//        super();
//        this.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
//    }
//
//    @Override
//    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        Type responseType = ((ParameterizedType) type).getActualTypeArguments()[0];
//        objectMapper.writerFor(objectMapper.constructType(responseType)).writeValue(outputMessage.getBody(), object);
//    }
//}