package com.ldc.spring.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * created by liudacheng on 2018/9/4.
 */
public class JacksonUtil {

    public static String toJson(Object object) throws JsonProcessingException {
        if (object != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        }
        return null;
    }

    public static <T> T toObject(String json, Class<T> clazz) throws IOException {
        if (StringUtils.isNotEmpty(json)) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, clazz);
        }
        return null;
    }

}
