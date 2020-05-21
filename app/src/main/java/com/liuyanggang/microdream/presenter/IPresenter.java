package com.liuyanggang.microdream.presenter;

import com.liuyanggang.microdream.model.IModel;
import com.liuyanggang.microdream.view.IView;

import java.lang.ref.WeakReference;

/**
 * @ClassName IPresenter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/19
 * @Version 1.0
 */
public class IPresenter {
    //model
    IModel mImodel;

    //View(加弱引用)
    WeakReference<IView> mViewReference;
}
