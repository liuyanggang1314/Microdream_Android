package com.liuyanggang.microdream.view;

import com.liuyanggang.microdream.entity.UserEntity;

/**
 * @ClassName RegisterView
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/23
 * @Version 1.0
 */
public interface RegisterView extends IView {
    /**
     * 获取注册信息
     *
     * @return
     */
    UserEntity getRegisterInfo();

    /**
     * 注册成功监听
     */
    void onRegisterSeccess();

    /**
     * 注册失败监听
     *
     * @param error
     */
    void onRegisterError(String error);
}
