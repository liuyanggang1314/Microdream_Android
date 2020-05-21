package com.liuyanggang.microdream.model.Lisentener;

/**
 * @ClassName LoginLisentener
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/19
 * @Version 1.0
 */
public interface LoginLisentener {
    void onSeccess();

    void onFails(String fails);
}
