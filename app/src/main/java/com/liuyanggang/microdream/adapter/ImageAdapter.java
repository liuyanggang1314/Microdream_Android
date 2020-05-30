package com.liuyanggang.microdream.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.entity.DataBean;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * @ClassName ImageAdapter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/29
 * @Version 1.0
 */
public class ImageAdapter extends BannerAdapter<DataBean, ImageHolder> {

    public ImageAdapter(List<DataBean> mDatas) {
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
        super(mDatas);
    }

    //更新数据
    public void updateData(List<DataBean> data) {
        //这里的代码自己发挥，比如如下的写法等等
        mDatas.clear();
        mDatas.addAll(data);
        notifyDataSetChanged();
    }


    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new ImageHolder(imageView);
    }

    @Override
    public void onBindView(ImageHolder holder, DataBean data, int position, int size) {
        Glide.with(holder.itemView)
                .load(data.imageUrl)
                .placeholder(R.drawable.image_fill)
                .error(R.drawable.logo)
                .into(holder.imageView);
    }

}