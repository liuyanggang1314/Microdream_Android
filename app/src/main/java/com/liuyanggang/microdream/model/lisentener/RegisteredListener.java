package com.liuyanggang.microdream.model.lisentener;

/**
 * @ClassName RegisteredLisenter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/23
 * @Version 1.0
 */
public interface RegisteredListener {
    /**
     * 注册成功
     */
    void onSeccess();

    /**
     * 注册失败
     *
     * @param error
     */
    void onError(String error);
}
