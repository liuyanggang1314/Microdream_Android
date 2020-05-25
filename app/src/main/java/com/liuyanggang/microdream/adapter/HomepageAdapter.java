package com.liuyanggang.microdream.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.entity.HomepageEntity;

import java.util.List;

/**
 * @ClassName HomepageAdapter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/25
 * @Version 1.0
 */
public class HomepageAdapter extends BaseQuickAdapter<HomepageEntity, BaseViewHolder> {
    public HomepageAdapter(int layoutResId, @Nullable List<HomepageEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomepageEntity item) {
        helper.setText(R.id.nikeName, item.getNikeName())
                .setText(R.id.text, item.getTxt())
                .setText(R.id.update_time, item.getUpdateTime());

        Glide.with(getContext()).load(item.getAvatar())
                .placeholder(R.drawable.image_fill)
                .error(R.drawable.logo)
                .centerCrop()
                .into((ImageView) helper.getView(R.id.avatar));
        Glide.with(getContext()).load(item.getImage1())
                .placeholder(R.drawable.image_fill)
                .error(R.drawable.logo)
                .centerCrop()
                .into((ImageView) helper.getView(R.id.image1));
        Glide.with(getContext()).load(item.getImage2())
                .placeholder(R.drawable.image_fill)
                .error(R.drawable.logo)
                .centerCrop()
                .into((ImageView) helper.getView(R.id.image2));
        Glide.with(getContext()).load(item.getImage3())
                .placeholder(R.drawable.image_fill)
                .error(R.drawable.logo)
                .centerCrop()
                .into((ImageView) helper.getView(R.id.image3));
        Glide.with(getContext()).load(item.getImage4())
                .placeholder(R.drawable.image_fill)
                .error(R.drawable.logo)
                .centerCrop()
                .into((ImageView) helper.getView(R.id.image4));
        Glide.with(getContext()).load(item.getImage5())
                .placeholder(R.drawable.image_fill)
                .error(R.drawable.logo)
                .centerCrop()
                .into((ImageView) helper.getView(R.id.image5));
        Glide.with(getContext()).load(item.getImage6())
                .placeholder(R.drawable.image_fill)
                .error(R.drawable.logo)
                .centerCrop()
                .into((ImageView) helper.getView(R.id.image6));

    }
}
