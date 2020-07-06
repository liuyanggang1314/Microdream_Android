package com.liuyanggang.microdream.utils;

import java.util.ArrayList;

import static com.liuyanggang.microdream.entity.HttpEntity.MICRODREAM_SERVER_MOOD;

/**
 * @ClassName ListUtil
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/28
 * @Version 1.0
 */
public class MyListUtil {
    public static ArrayList<String> setString(String str) {
        if (str == null) {
            return null;
        } else {
            String[] arr = str.split(",");
            ArrayList<String> a = new ArrayList<>();
            for (String s : arr) {
                a.add(MICRODREAM_SERVER_MOOD + s);
            }
            return a;
        }

    }
}
