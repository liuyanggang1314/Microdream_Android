package com.liuyanggang.microdream.model.lisentener;

/**
 * @ClassName MainLisentener
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/24
 * @Version 1.0
 */
public interface GetUserInfoLisentener {
    /**
     * 获取用户信息成功
     */
    void onGetUserInfoSuccess();

    /**
     * 获取用户信息失败
     *
     * @param error
     */
    void onGetUserInfoError(String error);
}
