package com.liuyanggang.microdream.base;

import android.view.View;

import com.qmuiteam.qmui.arch.QMUIFragment;

/**
 * @ClassName BaseFragment
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/18
 * @Version 1.0
 */
public class BaseFragment extends QMUIFragment {
    @Override
    protected View onCreateView() {
        return null;
    }

    @Override
    protected boolean translucentFull() {
        return true;
    }

}
