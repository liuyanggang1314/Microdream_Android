package com.liuyanggang.microdream.model.Lisentener;

/**
 * @ClassName RegisteredLisenter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/23
 * @Version 1.0
 */
public interface RegisteredListener {
    void onSeccess();

    void onError(String error);
}
