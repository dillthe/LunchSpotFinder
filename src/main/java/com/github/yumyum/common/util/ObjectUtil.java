package com.github.yumyum.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectUtil {

    private static final ObjectMapper OM = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static <T>T readValue(String str, Class<T> typeClass) {
        try {
            return OM.readValue(str, typeClass);
        } catch (JsonProcessingException e) {
            log.error("json parsing error", e);
            return null;
        }
    }

    public static String asString(Object obj) {
        try {
            return OM.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("json parsing error", e);
            return "{\"message\": \"json parsing error\"}";
        }
    }

}
