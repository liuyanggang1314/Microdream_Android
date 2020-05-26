package com.liuyanggang.microdream.base;

import android.annotation.SuppressLint;
import android.view.KeyEvent;
import android.widget.Toast;

import com.liuyanggang.microdream.utils.ToastyUtil;
import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;

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

    /**
     * 开启透明状态栏
     * @return
     */
    @Override
    protected boolean translucentFull() {
        return true;
    }

    /**
     * 实体按键监听事件
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (!QMUISwipeBackActivityManager.getInstance().canSwipeBack()) {
            if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.getAction() == KeyEvent.ACTION_DOWN) {
                long allExittime = 2000;
                if ((System.currentTimeMillis() - exitTime) > allExittime) {
                    //弹出提示，可以有多种方式
                    ToastyUtil.setNormalWarning(getApplicationContext(), "再按一次退出应用", Toast.LENGTH_SHORT);
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
