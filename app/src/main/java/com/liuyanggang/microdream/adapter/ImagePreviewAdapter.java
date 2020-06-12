package com.liuyanggang.microdream.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.entity.ImageEntity;

import java.util.List;

/**
 * @ClassName QDRecyclerViewAdapter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/6/11
 * @Version 1.0
 */
public class ImagePreviewAdapter extends BaseQuickAdapter<ImageEntity, BaseViewHolder>{

    public ImagePreviewAdapter(int layoutResId, @Nullable List<ImageEntity> data) {
        super(layoutResId, data);
        addChildClickViewIds(R.id.image_photo);
        addChildLongClickViewIds(R.id.image_photo);
    }

    @Override
    protected void convert(BaseViewHolder helper, ImageEntity item) {
        Glide.with(getContext()).load(item.getUrl())
                .placeholder(R.drawable.image_fill)
                .error(R.drawable.logo)
                .into((ImageView) helper.getView(R.id.image_photo));

    }
}
