package com.liuyanggang.microdream.view;

import java.util.Map;

/**
 * @ClassName MainIView
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/24
 * @Version 1.0
 */
public interface MainIView extends IView {
    /**
     * 成功获取用户数据
     */
    void onGetUserInfoSuccess();

    /**
     * 获取用户信息错误返回
     *
     * @param error
     */
    void onGetUserInfoError(String error);

    Map<String,String> onGetPasswordInfo();

    void onChangePasswordSuccess();

    void onChangePasswordError(String error);
}
