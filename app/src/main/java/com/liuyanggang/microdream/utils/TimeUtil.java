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


    /**
     * 时间差
     *
     * @param date
     * @return
     */
    public static String getTimeFormatText(Date date) {
        long minute = 60 * 1000;// 1分钟
        long hour = 60 * minute;// 1小时
        long day = 24 * hour;// 1天
        long month = 31 * day;// 月
        long year = 12 * month;// 年

        if (date == null) {
            return null;
        }
        String formatDate = DateUtil.formatDate(date);
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
        if (diff > year) {
            return formatDate;
        }
        if (diff > month) {
            return formatDate;
        }
        if (diff > (day * 7)) {
            return formatDate;
        }
        if (diff > day) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }
}
