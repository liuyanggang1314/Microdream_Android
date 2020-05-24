package com.liuyanggang.microdream.view;

/**
 * @ClassName LoginIView
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/19
 * @Version 1.0
 */
public interface LoginIView extends IView {
    String getUserName();

    String getPassword();

    boolean getIsRemember();


    void onLoginSeccess();

    void onLoginError(String error);

}
