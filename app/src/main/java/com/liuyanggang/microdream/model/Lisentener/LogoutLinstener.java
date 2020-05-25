package com.liuyanggang.microdream.model.Lisentener;

/**
 * @ClassName LogoutLinstener
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/24
 * @Version 1.0
 */
public interface LogoutLinstener {
    void onLogoutSeccess();

    void onLogoutError(String error);
}
