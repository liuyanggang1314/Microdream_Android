package com.liuyanggang.microdream.utils;

import androidx.annotation.NonNull;

import com.tencent.mmkv.MMKV;

/**
 * @ClassName MMKVUtils
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/19
 * @Version 1.0
 */
public class MMKVUtils {

    /**
     * 存储String值
     *
     * @param key
     * @param value
     */
    public static void setStringInfo(@NonNull String key, @NonNull String value) {
        MMKV kv = MMKV.defaultMMKV();
        kv.encode(key, value);
    }

    /**
     * 获取String值
     *
     * @param key
     * @return
     */
    public static String getStringInfo(@NonNull String key) {
        MMKV kv = MMKV.defaultMMKV();
        return kv.decodeString(key);
    }

    /**
     * 存储String值
     *
     * @param key
     * @param value
     */
    public static void setIntInfo(@NonNull String key, @NonNull Integer value) {
        MMKV kv = MMKV.defaultMMKV();
        kv.encode(key, value);
    }

    /**
     * 获取String值
     *
     * @param key
     * @return
     */
    public static int getIntInfo(@NonNull String key) {
        MMKV kv = MMKV.defaultMMKV();
        return kv.decodeInt(key);
    }

}
