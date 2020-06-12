package com.liuyanggang.microdream.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RegisterJson
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/23
 * @Version 1.0
 */
public class RegisterJson {
    public static List<Map<String, String>> getRoles() {
        Map<String, String> map = new HashMap<>();
        List<Map<String, String>> list = new ArrayList();
        map.put("id", "2");
        list.add(map);
        return list;
    }

    public static List<Map<String, String>> getJob() {
        Map<String, String> map = new HashMap<>();
        List<Map<String, String>> list = new ArrayList();
        map.put("id", "13");
        list.add(map);
        return list;
    }

    public static Map<String, String> getDept() {
        Map<String, String> map = new HashMap<>();
        map.put("id", "16");
        return map;
    }
}
