package com.liuyanggang.microdream.model.lisentener;

/**
 * @ClassName LoginLisentener
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/19
 * @Version 1.0
 */
public interface LoginLisentener {
    /**
     * 登录成功
     */
    void onSeccess();

    /**
     * 登录失败
     *
     * @param error
     */
    void onError(String error);

}
