package com.liuyanggang.microdream.base;

import android.annotation.SuppressLint;

import com.qmuiteam.qmui.arch.QMUIActivity;

/**
 * @ClassName BaseActivity
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/18
 * @Version 1.0
 */
@SuppressLint("Registered")
public class BaseActivity extends QMUIActivity {


    @Override
    protected boolean translucentFull() {
        return true;
    }
}
