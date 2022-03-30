package com.xgk.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

/**
 * @author Richard
 */
public class JsonUtils {

    private static final ObjectMapper objectMapper = BeanConfig.createObjectMapper();

    @SneakyThrows
    public static String writeValueAsString(Object value) {
        return objectMapper.writeValueAsString(value);
    }

    @SneakyThrows
    public static  <T> T readValue(String content, Class<T> valueType) {
        return objectMapper.readValue(content, valueType);
    }

    @SneakyThrows
    public static <T> T readValue(String content, TypeReference<T> valueTypeRef) {
        return objectMapper.readValue(content, valueTypeRef);
    }

    @SneakyThrows
    public static  <T> T convertValue(Object fromValue, Class<T> toValueType) {
        return objectMapper.convertValue(fromValue, toValueType);
    }

    @SneakyThrows
    public static <T> T convertValue(Object fromValue, TypeReference<T> toValueTypeRef) {
        return objectMapper.convertValue(fromValue, toValueTypeRef);
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

}
