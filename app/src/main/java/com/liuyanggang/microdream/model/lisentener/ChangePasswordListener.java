package com.liuyanggang.microdream.model.lisentener;

/**
 * @ClassName ChangePasswordIPresenter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/24
 * @Version 1.0
 */
public interface ChangePasswordListener {
    /**
     * 密码修改成功
     */
    void onChangePasswordSuccess();

    /**
     * 密码修改失败
     * @param error
     */
    void onChangePasswordError(String error);
}
