package net.renfei.mascloud.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author renfei
 */
public class JsonUtil {
    public static String toJsonString(Object o) {
        return JSON.toJSONString(o, new SerializerFeature[]{SerializerFeature.IgnoreNonFieldGetter});
    }

    public static <T> T fromJsonString(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }
}
