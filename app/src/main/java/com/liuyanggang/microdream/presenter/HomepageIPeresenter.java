package com.liuyanggang.microdream.presenter;

import com.liuyanggang.microdream.entity.HomepageEntity;
import com.liuyanggang.microdream.model.HomepageIModel;
import com.liuyanggang.microdream.model.lisentener.DeleteMoodListener;
import com.liuyanggang.microdream.model.lisentener.GetMoodListListener;
import com.liuyanggang.microdream.view.HomepageIView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @ClassName HomepageIPeresenter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/28
 * @Version 1.0
 */
public class HomepageIPeresenter extends IPresenter {
    public HomepageIPeresenter(HomepageIView homepageIView) {
        this.mImodel = new HomepageIModel();
        this.mViewReference = new WeakReference<>(homepageIView);
    }

    public void getMoodList() {
        if (mImodel != null && mViewReference != null && mViewReference.get() != null) {
            HomepageIView homepageIView = (HomepageIView) mViewReference.get();
            Integer current = homepageIView.getCurrent();
            homepageIView = null;
            //此时LoginListener作为匿名内部类是持有外部类的引用的。
            ((HomepageIModel) mImodel).getMoodList(current, new GetMoodListListener() {
                @Override
                public void onHomepageSeccess(List<HomepageEntity> homepageEntities, Integer pages) {
                    if (mViewReference.get() != null) {
                        ((HomepageIView) mViewReference.get()).onHomepageSeccess(homepageEntities, pages);
                    }
                }

                @Override
                public void onHomepageError(String error) {
                    if (mViewReference.get() != null) {
                        ((HomepageIView) mViewReference.get()).onHomepageError(error);
                    }
                }

                @Override
                public void onLoadMore(List<HomepageEntity> homepageEntities, Integer current) {
                    if (mViewReference.get() != null) {
                        ((HomepageIView) mViewReference.get()).onLoadMore(homepageEntities, current);
                    }
                }
            });
        }
    }

    public void onDeleteMood() {
        if (mImodel != null && mViewReference != null && mViewReference.get() != null) {
            HomepageIView homepageIView = (HomepageIView) mViewReference.get();
            Long id = homepageIView.getMoodId();
            homepageIView = null;
            //此时LoginListener作为匿名内部类是持有外部类的引用的。
            ((HomepageIModel) mImodel).onDeleteMood(id, new DeleteMoodListener() {
                @Override
                public void onDeleteMoodSeccess() {
                    if (mViewReference.get() != null) {
                        ((HomepageIView) mViewReference.get()).onDeleteSeccess();
                    }
                }

                @Override
                public void onDeleteMoodError(String error) {
                    if (mViewReference.get() != null) {
                        ((HomepageIView) mViewReference.get()).onDeleteError(error);
                    }
                }
            });
        }
    }

}
