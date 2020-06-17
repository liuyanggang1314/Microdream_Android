package com.liuyanggang.microdream.utils;

import android.content.Context;

import com.liuyanggang.microdream.R;

import es.dmoral.toasty.Toasty;

/**
 * @ClassName ToastyUtils
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/24
 * @Version 1.0
 */
public class ToastyUtil {
    public static void setNormalInfo(Context context, String info, int duration) {
        Toasty.custom(context, info, context.getResources().getDrawable(R.drawable.logo, null),
                context.getColor(R.color.info), context.getColor(R.color.white), duration, true, true).show();
    }

    public static void setNormalWarning(Context context, String info, int duration) {
        Toasty.custom(context, info, context.getResources().getDrawable(R.drawable.logo, null),
                context.getColor(R.color.warning), context.getColor(R.color.white), duration, true, true).show();
    }

    public static void setNormalDanger(Context context, String info, int duration) {
        Toasty.custom(context, info, context.getResources().getDrawable(R.drawable.logo, null),
                context.getColor(R.color.danger), context.getColor(R.color.white), duration, true, true).show();
    }

    public static void setNormalPrimary(Context context, String info, int duration) {
        Toasty.custom(context, info, context.getResources().getDrawable(R.drawable.logo, null),
                context.getColor(R.color.primary), context.getColor(R.color.white), duration, true, true).show();
    }

    public static void setNormalSuccess(Context context, String info, int duration) {
        Toasty.custom(context, info, context.getResources().getDrawable(R.drawable.logo, null),
                context.getColor(R.color.success), context.getColor(R.color.white), duration, true, true).show();
    }
}
