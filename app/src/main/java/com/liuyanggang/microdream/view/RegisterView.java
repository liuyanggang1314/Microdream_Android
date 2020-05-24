package com.liuyanggang.microdream.view;

import com.liuyanggang.microdream.entity.UserEntity;

/**
 * @ClassName RegisterView
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/23
 * @Version 1.0
 */
public interface RegisterView extends IView{
    UserEntity getRegisterInfo();

    void onRegisterSeccess();

    void onRegisterError(String error);
}
