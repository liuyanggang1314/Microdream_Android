package com.liuyanggang.microdream.presenter;

import com.liuyanggang.microdream.entity.ExaminationEntity;
import com.liuyanggang.microdream.model.ExaminationIModel;
import com.liuyanggang.microdream.model.lisentener.ExaminationListener;
import com.liuyanggang.microdream.view.ExaminationIView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @ClassName HomepageIPeresenter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/28
 * @Version 1.0
 */
public class ExaminationIPeresenter extends IPresenter {
    public ExaminationIPeresenter(ExaminationIView examinationIView) {
        this.mImodel = new ExaminationIModel();
        this.mViewReference = new WeakReference<>(examinationIView);
    }

    public void getExaminationList() {
        if (mImodel != null && mViewReference != null && mViewReference.get() != null) {
            ExaminationIView examinationIView = (ExaminationIView) mViewReference.get();
            Integer current = examinationIView.getCurrent();
            String moduleName = examinationIView.getModuleName();
            examinationIView = null;
            //此时LoginListener作为匿名内部类是持有外部类的引用的。
            ((ExaminationIModel) mImodel).getExaminationList(current, moduleName, new ExaminationListener() {
                @Override
                public void onHomepageSeccess(List<ExaminationEntity> examinationEntities, Integer pages) {
                    if (mViewReference.get() != null) {
                        ((ExaminationIView) mViewReference.get()).onHomepageSeccess(examinationEntities, pages);
                    }
                }

                @Override
                public void onHomepageError(String error) {
                    if (mViewReference.get() != null) {
                        ((ExaminationIView) mViewReference.get()).onHomepageError(error);
                    }
                }

                @Override
                public void onLoadMore(List<ExaminationEntity> examinationEntities, Integer current) {
                    if (mViewReference.get() != null) {
                        ((ExaminationIView) mViewReference.get()).onLoadMore(examinationEntities, current);
                    }
                }
            });
        }
    }

}
