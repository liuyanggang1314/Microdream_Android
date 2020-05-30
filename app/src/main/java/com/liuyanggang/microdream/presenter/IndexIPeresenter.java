package com.liuyanggang.microdream.presenter;

import com.liuyanggang.microdream.entity.DataBean;
import com.liuyanggang.microdream.entity.IndexEntity;
import com.liuyanggang.microdream.model.IndexIModel;
import com.liuyanggang.microdream.model.lisentener.GetIndexBannerListener;
import com.liuyanggang.microdream.model.lisentener.GetIndexListener;
import com.liuyanggang.microdream.view.IndexIView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @ClassName HomepageIPeresenter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/28
 * @Version 1.0
 */
public class IndexIPeresenter extends IPresenter {
    public IndexIPeresenter(IndexIView indexIView) {
        this.mImodel = new IndexIModel();
        this.mViewReference = new WeakReference<>(indexIView);
    }

    public void getIndexBannerList() {
        if (mImodel != null && mViewReference != null && mViewReference.get() != null) {
            IndexIView indexIView = (IndexIView) mViewReference.get();
            indexIView = null;
            //此时LoginListener作为匿名内部类是持有外部类的引用的。
            ((IndexIModel) mImodel).getIndexBannerList(new GetIndexBannerListener() {
                @Override
                public void onBannerSeccess(List<DataBean> beanList) {
                    if (mViewReference.get() != null) {
                        ((IndexIView) mViewReference.get()).onBannerSeccess(beanList);
                    }
                }

                @Override
                public void onBannerError(String error) {
                    if (mViewReference.get() != null) {
                        ((IndexIView) mViewReference.get()).onBannerError(error);
                    }
                }
            });
        }
    }

    public void getIndexList() {
        if (mImodel != null && mViewReference != null && mViewReference.get() != null) {
            IndexIView indexIView = (IndexIView) mViewReference.get();
            Integer current = indexIView.getCurrent();
            indexIView = null;
            //此时LoginListener作为匿名内部类是持有外部类的引用的。
            ((IndexIModel) mImodel).getIndexList(current, new GetIndexListener() {
                @Override
                public void onIndexSeccess(List<IndexEntity> indexList) {
                    if (mViewReference.get() != null) {
                        ((IndexIView) mViewReference.get()).onIndexSeccess(indexList);
                    }
                }

                @Override
                public void onIndexError(String error) {
                    if (mViewReference.get() != null) {
                        ((IndexIView) mViewReference.get()).onIndexError(error);
                    }
                }

                @Override
                public void onIndexLoadmoreSeccess(List<IndexEntity> indexList) {
                    if (mViewReference.get() != null) {
                        ((IndexIView) mViewReference.get()).onIndexLoadmoreSeccess(indexList);
                    }
                }
            });
        }
    }

}
