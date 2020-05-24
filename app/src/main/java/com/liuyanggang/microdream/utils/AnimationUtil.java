package com.liuyanggang.microdream.utils;

import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * @ClassName AnimationUtils
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/22
 * @Version 1.0
 */
public class AnimationUtil {
    public static void initAnimationShake(View view) {
        YoYo.with(Techniques.Shake)
                .duration(2000)
                //.pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .playOn(view);
    }

    public static void initAnimationBounceInDown(View view) {
        YoYo.with(Techniques.BounceInDown)
                .duration(2000)
                //.pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .playOn(view);
    }
}
