package com.liuyanggang.microdream.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.activity.MicrodreamWebExplorerActivity;
import com.qmuiteam.qmui.arch.QMUIFragment;

import static com.liuyanggang.microdream.activity.MicrodreamWebExplorerActivity.EXTRA_TITLE;
import static com.liuyanggang.microdream.activity.MicrodreamWebExplorerActivity.EXTRA_URL;


/**
 * @ClassName BaseFragment
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/18
 * @Version 1.0
 */
public abstract class BaseFragment extends QMUIFragment {


    protected void startWebExplorerActivity(@NonNull String url, Activity activity) {
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


}
