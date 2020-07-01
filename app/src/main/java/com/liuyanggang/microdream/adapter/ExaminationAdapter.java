package com.liuyanggang.microdream.adapter;

import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.entity.ExaminationEntity;

import java.util.List;

/**
 * @ClassName HomepageAdapter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/25
 * @Version 1.0
 */
public class ExaminationAdapter extends BaseQuickAdapter<ExaminationEntity, BaseViewHolder> implements LoadMoreModule {

    public ExaminationAdapter(int layoutResId, @Nullable List<ExaminationEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExaminationEntity item) {
        helper.setText(R.id.category, item.getCategory())
                .setText(R.id.title, item.getTitle())
                .setText(R.id.update_time, item.getUpdateTime());
        if (item.getCategory()==null){
            helper.getView(R.id.category).setVisibility(View.GONE);
        }
    }

}
