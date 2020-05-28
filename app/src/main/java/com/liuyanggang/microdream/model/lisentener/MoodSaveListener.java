package com.liuyanggang.microdream.model.lisentener;

/**
 * @ClassName MoodSaveListener
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/28
 * @Version 1.0
 */
public interface MoodSaveListener {
    void onMoodSaveSueccess();

    void onModdSavaError(String error);
}
