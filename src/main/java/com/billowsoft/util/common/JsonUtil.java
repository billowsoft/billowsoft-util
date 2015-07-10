package com.billowsoft.util.common;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JSON 与 Object 互相转换工具类。json到object使用jackson，object到json使用gson
 */
public class JsonUtil {

    private static Gson gson = new Gson();
    private static Gson onlyIncludeExposeFieldGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    private static ObjectMapper OBJECT_MAPPER;

    private static Map<TypeReference, ObjectReader> OBJECT_READERS =
            new ConcurrentHashMap<TypeReference, ObjectReader>();

    public static String toJson(Object object){
        return gson.toJson(object);
    }

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("MMM d, yyyy H:mm:ss a"));
    }

    public static String toJsonOnlyForExposeFields(Object object) {
        return onlyIncludeExposeFieldGson.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> clz){
        try {
            return OBJECT_MAPPER.readValue(json, clz);
        } catch (Exception e) {
            throw new RuntimeException("Convert json string to list object failed, input string is: " + json, e);
        }
    }

    /**
     * 将 list 的 json 数据转成对象，json 中的日期格式为参数指定的格式. 这个比GSON版本的更快
     *
     * @param jsonStr
     * @param elementType
     * @return
     */
    public static <T> T toListObj(String jsonStr, TypeReference<T> type) {
        ObjectReader reader = OBJECT_READERS.get(type);
        if (reader == null) {
            reader = OBJECT_MAPPER.reader(type);
            OBJECT_READERS.put(type, reader);
        }
        try {
            return reader.readValue(jsonStr);
        } catch (Exception e) {
            throw new RuntimeException("Convert json string to list object failed, input string is: " + jsonStr, e);
        }
    }

}
