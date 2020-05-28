package com.liuyanggang.microdream.model.lisentener;

import com.liuyanggang.microdream.entity.HomepageEntity;

import java.util.List;

/**
 * @ClassName HomepageListener
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/28
 * @Version 1.0
 */
public interface GetMoodListListener {
    /**
     * 成功
     *
     * @param homepageEntities
     */
    void onHomepageSeccess(List<HomepageEntity> homepageEntities, Integer pages);

    /**
     * 失败
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
}
