package com.liuyanggang.microdream.model.lisentener;

/**
 * @ClassName DeleteMoodListener
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/28
 * @Version 1.0
 */
public interface DeleteMoodListener {
    /**
     * 成功
     */
    void onDeleteMoodSeccess();

    /**
     * 失败
     *
     * @param error
     */
    void onDeleteMoodError(String error);

}
