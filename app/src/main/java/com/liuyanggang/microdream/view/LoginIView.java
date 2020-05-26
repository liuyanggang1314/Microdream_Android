package com.liuyanggang.microdream.view;

/**
 * @ClassName LoginIView
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/19
 * @Version 1.0
 */
public interface LoginIView extends IView {
    /**
     * 获取用户名
     *
     * @return
     */
    String getUserName();

    /**
     * 获取密码
     *
     * @return
     */
    String getPassword();

    /**
     * 获取是否记住登录密码
     *
     * @return
     */
    boolean getIsRemember();

    /**
     * 登录成功监听
     */
    void onLoginSeccess();

    /**
     * 登录失败监听
     *
     * @param error
     */
    void onLoginError(String error);

}
