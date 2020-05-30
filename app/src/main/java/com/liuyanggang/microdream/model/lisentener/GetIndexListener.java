package com.liuyanggang.microdream.model.lisentener;

import com.liuyanggang.microdream.entity.IndexEntity;

import java.util.List;

/**
 * @ClassName GetIndexListener
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/30
 * @Version 1.0
 */
public interface GetIndexListener {
    void onIndexSeccess(List<IndexEntity> indexList);

    void onIndexError(String error);

    void onIndexLoadmoreSeccess(List<IndexEntity> indexList);
}
