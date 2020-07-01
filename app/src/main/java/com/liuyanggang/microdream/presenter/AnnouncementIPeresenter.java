package com.liuyanggang.microdream.presenter;

import com.liuyanggang.microdream.entity.ExaminationEntity;
import com.liuyanggang.microdream.model.AnnouncementIModel;
import com.liuyanggang.microdream.model.lisentener.AnnouncementListener;
import com.liuyanggang.microdream.view.AnnouncementIView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @ClassName HomepageIPeresenter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/28
 * @Version 1.0
 */
public class AnnouncementIPeresenter extends IPresenter {
    public AnnouncementIPeresenter(AnnouncementIView announcementIView) {
        this.mImodel = new AnnouncementIModel();
        this.mViewReference = new WeakReference<>(announcementIView);
    }

    public void getAnnouncementList() {
        if (mImodel != null && mViewReference != null && mViewReference.get() != null) {
            AnnouncementIView announcementIView = (AnnouncementIView) mViewReference.get();
            Integer current = announcementIView.getCurrent();
            announcementIView = null;
            //此时LoginListener作为匿名内部类是持有外部类的引用的。
            ((AnnouncementIModel) mImodel).getAnnouncementList(current, new AnnouncementListener() {
                @Override
                public void onAnnouncementSeccess(List<ExaminationEntity> examinationEntities, Integer pages) {
                    if (mViewReference.get() != null) {
                        ((AnnouncementIView) mViewReference.get()).onAnnouncementSeccess(examinationEntities, pages);
                    }
                }

                @Override
                public void onAnnouncementError(String error) {
                    if (mViewReference.get() != null) {
                        ((AnnouncementIView) mViewReference.get()).onAnnouncementError(error);
                    }
                }

                @Override
                public void onLoadMore(List<ExaminationEntity> examinationEntities, Integer current) {
                    if (mViewReference.get() != null) {
                        ((AnnouncementIView) mViewReference.get()).onLoadMore(examinationEntities, current);
                    }
                }
            });
        }
    }

}
