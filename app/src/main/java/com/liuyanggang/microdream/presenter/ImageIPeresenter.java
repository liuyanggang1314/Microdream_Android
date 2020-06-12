package com.liuyanggang.microdream.presenter;

import com.liuyanggang.microdream.entity.ImageEntity;
import com.liuyanggang.microdream.model.ImageIModel;
import com.liuyanggang.microdream.model.lisentener.ImageByTypeListener;
import com.liuyanggang.microdream.model.lisentener.ImageListener;
import com.liuyanggang.microdream.view.ImageByTypeIView;
import com.liuyanggang.microdream.view.ImageIView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @ClassName HomepageIPeresenter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/28
 * @Version 1.0
 */
public class ImageIPeresenter extends IPresenter {
    public ImageIPeresenter(ImageIView imageIView) {
        this.mImodel = new ImageIModel();
        this.mViewReference = new WeakReference<>(imageIView);
    }

    public ImageIPeresenter(ImageByTypeIView imageIView) {
        this.mImodel = new ImageIModel();
        this.mViewReference = new WeakReference<>(imageIView);
    }

    public void getImageList() {
        if (mImodel != null && mViewReference != null && mViewReference.get() != null) {
            ImageIView imageIView = (ImageIView) mViewReference.get();
            Integer current = imageIView.getCurrent();
            imageIView = null;
            //此时LoginListener作为匿名内部类是持有外部类的引用的。
            ((ImageIModel) mImodel).getImageList(current, new ImageListener() {
                @Override
                public void onImageSeccess(List<ImageEntity> imageEntities, Integer pages) {
                    if (mViewReference.get() != null) {
                        ((ImageIView) mViewReference.get()).onImageSeccess(imageEntities, pages);
                    }
                }

                @Override
                public void onImageError(String error) {
                    if (mViewReference.get() != null) {
                        ((ImageIView) mViewReference.get()).onImageError(error);
                    }
                }

                @Override
                public void onImageMore(List<ImageEntity> imageEntities, Integer current) {
                    if (mViewReference.get() != null) {
                        ((ImageIView) mViewReference.get()).onLoadMore(imageEntities, current);
                    }
                }
            });
        }
    }

    public void getImageByType() {
        if (mImodel != null && mViewReference != null && mViewReference.get() != null) {
            ImageByTypeIView imageIView = (ImageByTypeIView) mViewReference.get();
            Long type = imageIView.getType();
            imageIView = null;
            //此时LoginListener作为匿名内部类是持有外部类的引用的。
            ((ImageIModel) mImodel).getImageByType(type, new ImageByTypeListener() {
                @Override
                public void onImageSeccess(List<ImageEntity> imageEntities) {
                    if (mViewReference.get() != null) {
                        ((ImageByTypeIView) mViewReference.get()).onImageByTypeSeccess(imageEntities);
                    }
                }

                @Override
                public void onImageError(String error) {
                    if (mViewReference.get() != null) {
                        ((ImageByTypeIView) mViewReference.get()).onImageByTypeError(error);
                    }
                }
            });
        }
    }

}
