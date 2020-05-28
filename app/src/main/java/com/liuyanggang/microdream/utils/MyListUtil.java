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
        String[] arr = str.split(",");
        ArrayList<String> a = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            a.add(String.valueOf(new StringBuffer().append(MICRODREAM_SERVER_MOOD).append(arr[i])));
        }

        return a;
    }
}
