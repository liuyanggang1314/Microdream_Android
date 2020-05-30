package com.liuyanggang.microdream.model.lisentener;

import com.liuyanggang.microdream.entity.DataBean;

import java.util.List;

/**
 * @ClassName GetIndexBannerListener
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/30
 * @Version 1.0
 */
public interface GetIndexBannerListener {

    void onBannerSeccess(List<DataBean> beanList);

    void onBannerError(String error);
}
