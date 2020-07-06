package com.liuyanggang.microdream.presenter;

import com.liuyanggang.microdream.model.MoodEditIModel;
import com.liuyanggang.microdream.model.lisentener.MoodSaveListener;
import com.liuyanggang.microdream.view.MoodEditIView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @ClassName LoginIPresenter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/19
 * @Version 1.0
 */
public class MoodEditIPresenter extends IPresenter {

    public MoodEditIPresenter(MoodEditIView moodEditIView) {
        this.mImodel = new MoodEditIModel();
        this.mViewReference = new WeakReference<>(moodEditIView);
    }

    public void onSaveMood() {
        if (mImodel != null && mViewReference != null && mViewReference.get() != null) {
            MoodEditIView moodEditIView = (MoodEditIView) mViewReference.get();
            List<String> images = moodEditIView.getimages();
            String content = moodEditIView.getContent();
            moodEditIView = null;
            //此时LoginListener作为匿名内部类是持有外部类的引用的。
            ((MoodEditIModel) mImodel).onSaveMood(content, images, new MoodSaveListener() {
                @Override
                public void onMoodSaveSueccess() {
                    if (mViewReference.get() != null) {
                        ((MoodEditIView) mViewReference.get()).onMoodSaveSueccess();
                    }
                }

                @Override
                public void onModdSavaError(String error) {
                    if (mViewReference.get() != null) {
                        ((MoodEditIView) mViewReference.get()).onModdSavaError(error);
                    }
                }
            });
        }
    }

    public void onSaveMoodVideo() {
        if (mImodel != null && mViewReference != null && mViewReference.get() != null) {
            MoodEditIView moodEditIView = (MoodEditIView) mViewReference.get();
            List<String> images = moodEditIView.getimages();
            String content = moodEditIView.getContent();
            moodEditIView = null;
            //此时LoginListener作为匿名内部类是持有外部类的引用的。
            ((MoodEditIModel) mImodel).onSaveMoodVideo(content, images, new MoodSaveListener() {
                @Override
                public void onMoodSaveSueccess() {
                    if (mViewReference.get() != null) {
                        ((MoodEditIView) mViewReference.get()).onMoodSaveSueccess();
                    }
                }

                @Override
                public void onModdSavaError(String error) {
                    if (mViewReference.get() != null) {
                        ((MoodEditIView) mViewReference.get()).onModdSavaError(error);
                    }
                }
            });
        }
    }

}
