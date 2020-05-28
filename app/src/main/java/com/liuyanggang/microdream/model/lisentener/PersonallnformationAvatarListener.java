package com.liuyanggang.microdream.model.lisentener;

/**
 * @ClassName RegisteredLisenter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/23
 * @Version 1.0
 */
public interface PersonallnformationAvatarListener {
    /**
     * 头像更新成功
     */
    void onPersonallformationAvatarSeccess(String avatarName);

    /**
     * 头像更新失败
     *
     * @param error
     */
    void onPersonallformationAvatarError(String error);
}
