package com.liuyanggang.microdream.view;

import java.util.List;

/**
 * @ClassName MoodEditIView
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/28
 * @Version 1.0
 */
public interface MoodEditIView extends IView {
    void onMoodSaveSueccess();

    void onModdSavaError(String error);

    String getContent();

    List<String> getimages();
}
