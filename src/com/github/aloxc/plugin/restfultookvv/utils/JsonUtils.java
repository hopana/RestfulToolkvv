package com.github.aloxc.plugin.restfultookvv.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.intellij.openapi.util.text.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhaow
 * @modify liyh
 */
public class JsonUtils {
    private static JsonFactory jsonFactory = new JsonFactory();

    private static ObjectMapper mapper = null;

    static {
        jsonFactory.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        jsonFactory.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper = new ObjectMapper(jsonFactory);
    }

    private JsonUtils() {
    }

    /**
     * 获取jackson json lib的ObjectMapper对象
     *
     * @return -- ObjectMapper对象
     */
    public static ObjectMapper getMapper() {
        return mapper;
    }

    /**
     * 获取jackson json lib的JsonFactory对象
     *
     * @return -- JsonFactory对象
     */
    public static JsonFactory getJsonFactory() {
        return jsonFactory;
    }

    /**
     * 将json转成java bean
     *
     * @param <T>   -- 多态类型
     * @param json  -- json字符串
     * @param clazz -- java bean类型(Class)
     * @return -- java bean对象
     */
    public static <T> T toBean(String json, Class<T> clazz) {

        T rtv = null;
        try {
            rtv = mapper.readValue(json, clazz);
        } catch (Exception ex) {
            throw new IllegalArgumentException("json字符串转成java bean异常", ex);
        }
        return rtv;
    }

    /**
     * 将java bean转成json
     *
     * @param bean -- java bean
     * @return -- json 字符串
     */
    public static String toJson(Object bean) {

        String rtv = null;
        try {
            rtv = mapper.writeValueAsString(bean);
        } catch (Exception ex) {
            throw new IllegalArgumentException("java bean转成json字符串异常", ex);
        }
        return rtv;
    }

    /**
     * 将json转成java bean
     *
     * @param <T>  -- 多态类型,可以是简单的,也可以是复合的.例如,可以是TypeReference&ltDemoUser&gt,
     *             也可以是TypeReference&ltList&ltDemoUser&gt&gt
     * @param json -- json字符串
     * @return -- java bean对象,可以是简单bean对象,也可以是对象List.例如,List&ltDemoUser&gt
     * @throws IllegalArgumentException 如果参数json为null或者转换过程出错.
     */
    @SuppressWarnings("unchecked")
    public static <T> T toBean(String json, TypeReference<T> refer) {
        if (StringUtils.isEmpty(json)) {
            throw new IllegalArgumentException("json can not null");
        }
        T entity = null;
        try {
            entity = mapper.reader(refer).readValue(json);

        } catch (Exception e) {
            throw new IllegalArgumentException("json字符串转成java bean异常", e);
        }
        return entity;
    }

    /**
     * 把数组类型的json转为list&ltBean&gt
     *
     * @param <T>   -- 多态类型
     * @param json  -- json字符串
     * @param clazz -- java bean类型(Class)
     * @return List&ltT&gt
     * @throws IllegalArgumentException 如果参数json为null,或者转换过程出错.
     */
    public static <T> List<T> toBeanList(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            throw new IllegalArgumentException("json can not null");
        }
        List<T> result = null;
        try {
            JsonNode jn = mapper.readTree(json);
            result = new ArrayList<>();
            if (jn.isArray()) {
                Iterator<JsonNode> iter = jn.iterator();
                while (iter.hasNext()) {
                    parseBeanAddToList(iter.next(), result, clazz);
                }
            } else {
                parseBeanAddToList(jn, result, clazz);
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("json字符串转成java List<bean>异常", e);
        }
        return result;
    }

    private static <T> void parseBeanAddToList(JsonNode js, List<T> list, Class<T> clazz) throws JsonParseException,
            JsonMappingException, IOException {
        T rtv = mapper.readValue(js.toString(), clazz);
        list.add(rtv);
    }

    public static boolean isValidJson(String json) {
        if (StringUtil.isEmptyOrSpaces(json)) {
            return false;
        }

        return isValidJsonObject(json) || isValidJsonArray(json);
    }

    public static String format(String str) {
        JsonParser parser = new JsonParser();
        JsonElement parse = parser.parse(str);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(parse);
        return json;
    }

    private static boolean isGsonFormat(String targetStr, Class clazz) {
        try {
            new Gson().fromJson(targetStr, clazz);
            return true;
        } catch (JsonSyntaxException ex) {
            return false;
        }
    }

    public static boolean isValidJsonObject(String targetStr) {
        return isGsonFormat(targetStr, JsonObject.class);
    }

    public static boolean isValidJsonArray(String targetStr) {
        return isGsonFormat(targetStr, JsonArray.class);
    }

}