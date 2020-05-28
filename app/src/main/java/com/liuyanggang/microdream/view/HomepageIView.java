package com.liuyanggang.microdream.view;

import com.liuyanggang.microdream.entity.HomepageEntity;

import java.util.List;

/**
 * @ClassName HomepageIView
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/26
 * @Version 1.0
 */
public interface HomepageIView extends IView {
    /**
     * 第一次获取成功监听
     *
     * @param homepageEntities 心情实体
     */
    void onHomepageSeccess(List<HomepageEntity> homepageEntities, Integer pages);

    /**
     * 第获取失败监听
     *
     * @param error
     */
    void onHomepageError(String error);

    /**
     * 加载更多
     *
     * @return
     */
    void onLoadMore(List<HomepageEntity> homepageEntities, Integer current);

    /**
     * 获取当前页
     *
     * @return
     */
    Integer getCurrent();

    /**
     * 获取心情id
     *
     * @return
     */
    Long getMoodId();

    /**
     * 删除成功
     */
    void onDeleteSeccess();

    /**
     * 删除失败
     *
     * @param error
     */
    void onDeleteError(String error);
}
