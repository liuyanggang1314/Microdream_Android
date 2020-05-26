package com.liuyanggang.microdream.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.chad.library.adapter.base.animation.BaseAnimation;

import org.jetbrains.annotations.NotNull;

/**
 * @ClassName CustomAnimation
 * @Description TODO 自定义recyclerciew动画
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/26
 * @Version 1.0
 */
public class CustomAnimation implements BaseAnimation {
    @NotNull
    @Override
    public Animator[] animators(@NotNull View view) {
        Animator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.3f, 1);
        Animator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.3f, 1);
        Animator alpha = ObjectAnimator.ofFloat(view, "alpha", 0, 1f);

        scaleY.setDuration(350);
        scaleX.setDuration(350);
        alpha.setDuration(350);

        scaleY.setInterpolator(new DecelerateInterpolator());
        scaleX.setInterpolator(new DecelerateInterpolator());

        return new Animator[]{scaleY, scaleX, alpha};
    }
}