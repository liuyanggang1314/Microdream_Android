package com.liuyanggang.microdream.model.lisentener;

/**
 * @ClassName RegisteredLisenter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/23
 * @Version 1.0
 */
public interface PersonallnformationListener {
    /**
     * 用户信息更新成功
     */
    void onPersonallformationSeccess();

    /**
     * 用户信息更新失败
     *
     * @param error
     */
    void onPersonallformationError(String error);
}
