package com.liuyanggang.microdream.utils;

import com.google.gson.Gson;

/**
 * @ClassName JsonUtils
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/21
 * @Version 1.0
 */
public class JsonUtils {
    public static String getJson(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
