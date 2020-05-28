package com.liuyanggang.microdream.view;

import com.liuyanggang.microdream.entity.UserEntity;

/**
 * @ClassName MainIView
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/24
 * @Version 1.0
 */
public interface PersonallnformationIView extends IView {
    /**
     * 获取头像地址
     *
     * @return 头像地址
     */
    String getAvatarPath();

    /**
     * 更新头像成功
     */
    void onUpdateAvatarSuccess(String avatarName);

    /**
     * 更新头像失败
     *
     * @param error 失败信息
     */
    void onUpdateAvatarError(String error);

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    UserEntity getUser();

    /**
     * 用户信息更新成功返回
     */
    void onUpdateUserSuccess();

    /**
     * 用户信息更新失败返回
     *
     * @param error
     */
    void onUpdateUserError(String error);

}
