package com.liuyanggang.microdream.base;

import com.qmuiteam.qmui.arch.QMUIFragment;

/**
 * @ClassName BaseFragment
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/18
 * @Version 1.0
 */
public abstract class BaseFragment extends QMUIFragment {

    /**
     * 开启透明状态栏
     * @return
     */
    @Override
    protected boolean translucentFull() {
        return true;
    }

}
