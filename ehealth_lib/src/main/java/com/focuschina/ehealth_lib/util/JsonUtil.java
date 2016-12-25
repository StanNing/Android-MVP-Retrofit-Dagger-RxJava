package com.focuschina.ehealth_lib.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Copyright (C) Focus Technology
 *
 * @ClassName:  JsonUtil
 * @Description: TODO: (Json 解析工具类)
 * @author: Ning Jiajun
 * @date:   16/6/1 下午5:40
 *
 */
public class JsonUtil {

    /**
     * Model to JsonStr
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        String jsonStr = "";
        try {
            jsonStr = new Gson().toJson(object);
        } catch (Exception e) {
            LogUtil.err(e);
        }
        return jsonStr;
    }

    /**
     * jsonStr to Model
     * @param jsonData
     * @param parseClass
     * @param <T>
     * @return
     */
    public static <T> T toClass(String jsonData, Class<T> parseClass) {
        T t = null;
        try {
            t = new Gson().fromJson(jsonData, parseClass);
        } catch (JsonSyntaxException e) {
            LogUtil.err(e);
        }
        return t;
    }
}
