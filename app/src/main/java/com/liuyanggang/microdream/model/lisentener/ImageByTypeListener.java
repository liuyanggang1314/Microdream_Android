package com.liuyanggang.microdream.model.lisentener;

import com.liuyanggang.microdream.entity.ImageEntity;

import java.util.List;

/**
 * @ClassName ExaminationListener
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/31
 * @Version 1.0
 */
public interface ImageByTypeListener {
    void onImageSeccess(List<ImageEntity> imageEntities);

    void onImageError(String error);
}
