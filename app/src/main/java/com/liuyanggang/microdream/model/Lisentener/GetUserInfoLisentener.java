package com.liuyanggang.microdream.model.Lisentener;

/**
 * @ClassName MainLisentener
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/24
 * @Version 1.0
 */
public interface GetUserInfoLisentener {
    void onGetUserInfoSuccess();

    void onGetUserInfoError(String error);
}
