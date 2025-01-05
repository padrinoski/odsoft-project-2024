package pt.psoft.g1.psoftg1.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ObjectToJsonConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String convertObjectToJson(Object object) throws JsonProcessingException {
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(object);
    }
}
