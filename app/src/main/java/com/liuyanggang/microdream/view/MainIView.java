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

    /**
     * 获取修改密码的信息
     *
     * @return
     */
    Map<String, String> onGetPasswordInfo();

    /**
     * 修改密码成功
     */
    void onChangePasswordSuccess();

    /**
     * 修改密码失败
     *
     * @param error
     */
    void onChangePasswordError(String error);

    /**
     * 退出登录成功
     */
    void onLogoutSuccess();

    /**
     * 退出登录失败
     *
     * @param error
     */
    void onLogoutError(String error);

}
