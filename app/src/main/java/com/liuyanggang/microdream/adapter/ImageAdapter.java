package com.liuyanggang.microdream.adapter;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.entity.ImageEntity;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView2;

import java.util.List;

/**
 * @ClassName HomepageAdapter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/25
 * @Version 1.0
 */
public class ImageAdapter extends BaseQuickAdapter<ImageEntity, BaseViewHolder> implements LoadMoreModule {

    public ImageAdapter(int layoutResId, @Nullable List<ImageEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ImageEntity item) {
        helper.setText(R.id.img_name, item.getName());
        Glide.with(getContext()).load(item.getUrl())
                .placeholder(R.drawable.image_fill)
                .error(R.drawable.logo)
                .into((QMUIRadiusImageView2) helper.getView(R.id.img));
    }

}
