package com.liuyanggang.microdream.utils;

import java.util.Date;

import cn.hutool.core.date.DateUtil;

/**
 * @ClassName TimeUtil
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/27
 * @Version 1.0
 */
public class TimeUtil {
    public static String setCurrentTimeMillis(String dateStr) {
        try {
            Date date = DateUtil.date(Long.parseLong(dateStr));
            String formatDateTime = DateUtil.formatDateTime(date);
            return formatDateTime;
        } catch (Exception e) {
            return "";
        }
    }
}
