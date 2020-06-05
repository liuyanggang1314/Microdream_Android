package com.liuyanggang.microdream.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.activity.MicrodreamWebExplorerActivity;
import com.liuyanggang.microdream.manager.AppManager;
import com.liuyanggang.microdream.utils.ToastyUtil;
import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;

import static com.liuyanggang.microdream.activity.MicrodreamWebExplorerActivity.EXTRA_TITLE;
import static com.liuyanggang.microdream.activity.MicrodreamWebExplorerActivity.EXTRA_URL;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
        overridePendingTransition(R.anim.slide_right_in,0);
    }

    protected void startWebExplorerActivity(String url, Activity activity) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_URL, url);
        bundle.putString(EXTRA_TITLE, getString(R.string.app_name));
        startActivity(new Intent(activity, MicrodreamWebExplorerActivity.class).putExtras(bundle));
    }

    /**
     * 开启透明状态栏
     *
     * @return
     */
    @Override
    protected boolean translucentFull() {
        return true;
    }

    /**
     * 实体按键监听事件
     *
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,R.anim.slide_right_out);
    }
}
