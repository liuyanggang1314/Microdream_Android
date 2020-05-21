package com.liuyanggang.microdream.base;

import android.annotation.SuppressLint;
import android.view.KeyEvent;

import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;

import es.dmoral.toasty.Toasty;

/**
 * @ClassName BaseActivity
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/18
 * @Version 1.0
 */
@SuppressLint("Registered")
public class BaseActivity extends QMUIActivity {

    private long exitTime = 0;
    private static long allExittime=2000;

    @Override
    protected boolean translucentFull() {
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (!QMUISwipeBackActivityManager.getInstance().canSwipeBack()) {
            if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.getAction() == KeyEvent.ACTION_DOWN) {
                if ((System.currentTimeMillis() - exitTime) > allExittime) {
                    //弹出提示，可以有多种方式
                    Toasty.info(this, "再按一次退出应用", 2000).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    System.exit(0);
                }

                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
