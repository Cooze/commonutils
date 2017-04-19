package org.cooze.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sun.istack.internal.Nullable;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * @author cooze
 * @version 1.0.0
 * @desc
 * @date 2017/4/19
 */
public class JsonUtil {
    private ObjectMapper mapper;

    public JsonUtil() {
        this((JsonInclude.Include) null);
    }

    public JsonUtil(JsonInclude.Include include) {
        this.mapper = new ObjectMapper();
        if (include != null) this.mapper.setSerializationInclusion(include);

        this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public static JsonUtil nonNullMapper() {
        return new JsonUtil(JsonInclude.Include.NON_NULL);
    }

    public static JsonUtil nonEmptyMapper() {
        return new JsonUtil(JsonInclude.Include.NON_EMPTY);
    }

    public static JsonUtil defaultMapper() {
        return new JsonUtil();
    }

    /**
     * Json范型转Java范型对象
     *
     * @param jsonStr         json字符串
     * @param collectionClass 外部类／集合类
     * @param elementClasses  范型类／集合子元素
     * @param <T>             范型
     * @return 返回集合
     * @throws Exception 异常
     */
    public <T> T jsonToObject(String jsonStr, Class<?> collectionClass, Class<?>... elementClasses) throws Exception {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        return mapper.readValue(jsonStr, javaType);
    }


    /**
     * java对象转json
     *
     * @param object
     * @return
     * @throws Exception
     */
    public String toJson(Object object) throws Exception {
        return this.mapper.writeValueAsString(object);
    }

    /**
     * 浅拷贝json转java对象
     *
     * @param jsonString
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T jsonToObj(@Nullable String jsonString, Class<T> clazz) throws Exception {
        return StringUtils.isEmpty(jsonString) ? null : this.mapper.readValue(jsonString, clazz);
    }

    /**
     * 深层拷贝集合类型
     *
     * @param jsonString
     * @param javaType
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T jsonToObj(@Nullable String jsonString, JavaType javaType) throws Exception {
        return StringUtils.isEmpty(jsonString) ? null : this.mapper.readValue(jsonString, javaType);
    }

    /**
     * 获取java集合类型
     *
     * @param collectionClass
     * @param elementClass
     * @return
     */
    public JavaType buildCollectionType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
        return this.mapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
    }

    /**
     * 获取java类型的map集合
     *
     * @param mapClass
     * @param keyClass
     * @param valueClass
     * @return
     */
    public JavaType buildMapType(Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
        return this.mapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
    }

    /**
     * 更新对象
     *
     * @param jsonString
     * @param object
     * @throws IOException
     */
    public void update(String jsonString, Object object) throws IOException {
        this.mapper.readerForUpdating(object).readValue(jsonString);
    }

    /**
     *
     * @param functionName
     * @param object
     * @return
     * @throws Exception
     */
    public String toJsonP(String functionName, Object object) throws Exception {
        return this.toJson(new JSONPObject(functionName, object));
    }

    /**
     *
     */
    public void enableEnumUseToString() {
        this.mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        this.mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
    }

    public ObjectMapper getMapper() {
        return this.mapper;
    }


}
