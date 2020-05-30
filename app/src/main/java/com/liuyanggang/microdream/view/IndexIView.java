package com.liuyanggang.microdream.view;

import com.liuyanggang.microdream.entity.DataBean;
import com.liuyanggang.microdream.entity.IndexEntity;

import java.util.List;

/**
 * @ClassName IndexIView
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/30
 * @Version 1.0
 */
public interface IndexIView extends IView {
    void onBannerSeccess(List<DataBean> beanList);

    void onBannerError(String error);

    Integer getCurrent();

    void onIndexSeccess(List<IndexEntity> indexList);

    void onIndexError(String error);

    void onIndexLoadmoreSeccess(List<IndexEntity> indexList);
}
