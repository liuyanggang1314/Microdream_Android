package com.liuyanggang.microdream.adapter;

import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.entity.DataBean;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.util.BannerUtils;

import java.util.List;

/**
 * @ClassName ImageTitleAdapter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/30
 * @Version 1.0
 */
public class ImageTitleAdapter extends BannerAdapter<DataBean, ImageTitleHolder> {

    public ImageTitleAdapter(List<DataBean> mDatas) {
        super(mDatas);
    }

    @Override
    public ImageTitleHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ImageTitleHolder(BannerUtils.getView(parent, R.layout.layout_banner_image_title));
    }

    @Override
    public void onBindView(ImageTitleHolder holder, DataBean data, int position, int size) {
        holder.title.setText(data.title);
        Glide.with(holder.itemView)
                .load(data.imageUrl)
                .placeholder(R.drawable.image_fill)
                .error(R.drawable.logo)
                .into(holder.imageView);
    }



}