package com.liuyanggang.microdream.model.lisentener;

/**
 * @ClassName LogoutLinstener
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/24
 * @Version 1.0
 */
public interface LogoutLinstener {
    /**
     * 登出成功
     */
    void onLogoutSeccess();

    /**
     * 登出失败
     *
     * @param error
     */
    void onLogoutError(String error);
}
