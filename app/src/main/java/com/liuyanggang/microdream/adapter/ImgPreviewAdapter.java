package com.liuyanggang.microdream.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.entity.HomepageEntity;

import java.util.List;

/**
 * @ClassName ImgPreviewAdapter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/26
 * @Version 1.0
 */
public class ImgPreviewAdapter extends BaseQuickAdapter<HomepageEntity, BaseViewHolder> implements LoadMoreModule {

    public ImgPreviewAdapter(int layoutResId, @Nullable List<HomepageEntity> data) {
        super(layoutResId, data);
        addChildLongClickViewIds(R.id.photoView);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomepageEntity item) {
        Glide.with(getContext()).load("http://sjbz.fd.zol-img.com.cn/t_s1080x1920c/g2/M00/08/0A/ChMlWV7HoMiIB-VPAEDRuaaZmMYAAPXmgEEWWIAQNHR773.jpg")
                .placeholder(R.drawable.image_fill)
                .error(R.drawable.logo)
                .into((ImageView) helper.getView(R.id.photoView));

    }
}