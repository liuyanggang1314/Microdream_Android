package com.liuyanggang.microdream.view;

import com.liuyanggang.microdream.entity.ImageEntity;

import java.util.List;

/**
 * @ClassName ExaminationIView
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/31
 * @Version 1.0
 */
public interface ImageIView extends IView {

    Integer getCurrent();

    void onImageSeccess(List<ImageEntity> imageEntities, Integer pages);

    void onImageError(String error);

    void onLoadMore(List<ImageEntity> imageEntities, Integer current);
}
