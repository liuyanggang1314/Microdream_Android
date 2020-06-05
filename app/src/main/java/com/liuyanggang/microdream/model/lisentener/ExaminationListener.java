package com.liuyanggang.microdream.model.lisentener;

import com.liuyanggang.microdream.entity.ExaminationEntity;

import java.util.List;

/**
 * @ClassName ExaminationListener
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/31
 * @Version 1.0
 */
public interface ExaminationListener {
    void onHomepageSeccess(List<ExaminationEntity> examinationEntities, Integer pages);

    void onHomepageError(String error);

    void onLoadMore(List<ExaminationEntity> examinationEntities, Integer current);
}
