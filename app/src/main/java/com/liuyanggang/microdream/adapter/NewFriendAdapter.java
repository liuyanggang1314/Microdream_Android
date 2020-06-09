package com.liuyanggang.microdream.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.liuyanggang.microdream.R;
import com.tencent.imsdk.friendship.TIMFriendPendencyItem;

import java.util.List;

/**
 * @ClassName NewFriendAdapter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/6/8
 * @Version 1.0
 */
public class NewFriendAdapter extends BaseQuickAdapter<TIMFriendPendencyItem, BaseViewHolder> implements LoadMoreModule {

    public NewFriendAdapter(int layoutResId, @Nullable List<TIMFriendPendencyItem> data) {
        super(layoutResId, data);
        addChildClickViewIds(R.id.sure);
    }

    @Override
    protected void convert(BaseViewHolder helper, TIMFriendPendencyItem item) {
        helper.setText(R.id.username, item.getIdentifier());

        Glide.with(getContext()).load(R.drawable.logo)
                .placeholder(R.drawable.image_fill)
                .error(R.drawable.logo)
                .into((ImageView) helper.getView(R.id.avatar));

    }
}