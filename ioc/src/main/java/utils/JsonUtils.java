package utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

/**
 * json配置文件解析工具类
 */
public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    private JsonUtils(){}

    public static ObjectMapper getObjectMapper(){
        return mapper;
    }


    public static <T> T readValue(String json,Class<T> clz){
        try {
            return mapper.readValue(json,clz);
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> T readValue(InputStream in, Class<T> clz){
        try {
            return mapper.readValue(in,clz);
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> T readValue(byte[] bytes,Class<T> clz){
        try {
            return mapper.readValue(bytes,clz);
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> T readValue(String json, TypeReference typeReference){
        try {
            return mapper.readValue(json,typeReference);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T readValue(byte[] bytes, TypeReference typeReference){
        try {
            return mapper.readValue(bytes,typeReference);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T readValue(InputStream in,TypeReference typeReference){
        try {
            return mapper.readValue(in,typeReference);
        } catch (IOException e) {
            return null;
        }
    }

    public String writeValue(Object entity){
        try {
            return mapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public byte[] writeValueByBytes(Object entity){
        try {
            return mapper.writeValueAsBytes(entity);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.getDeserializationConfig().withoutFeatures(new DeserializationFeature[]{DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES});
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(JsonParser.Feature.ALLOW_YAML_COMMENTS, true);
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
