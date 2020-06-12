package com.liuyanggang.microdream.view;

import com.liuyanggang.microdream.entity.ImageEntity;

import java.util.List;

/**
 * @ClassName ImageByTypeIView
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/6/11
 * @Version 1.0
 */
public interface ImageByTypeIView extends IView{
    Long getType();

    void onImageByTypeSeccess(List<ImageEntity> imageEntities);

    void onImageByTypeError(String error);
}
