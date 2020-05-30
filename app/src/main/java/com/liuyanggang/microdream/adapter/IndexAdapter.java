package com.liuyanggang.microdream.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.entity.IndexEntity;

import java.util.List;

/**
 * @ClassName HomepageAdapter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/25
 * @Version 1.0
 */
public class IndexAdapter extends BaseQuickAdapter<IndexEntity, BaseViewHolder> implements LoadMoreModule {

    public IndexAdapter(int layoutResId, @Nullable List<IndexEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IndexEntity item) {
        helper.setText(R.id.title, item.getTitle())
                .setText(R.id.category_name, item.getCategoryName())
                .setText(R.id.post_date, item.getPostDate())
                .setText(R.id.content, item.getContent().replace("<p>", "").replace("</p>\n", "[...]").replace("[&#8230;]", ""))
                .setText(R.id.total_comments, item.getTotalComments())
                .setText(R.id.pageviews, item.getPageViews())
                .setText(R.id.like_count, item.getLikeCount());
        //RichText.from(item.getContent().replace("\n","")).into(helper.getView(R.id.content));
        Glide.with(getContext()).load(item.getImageUrl())
                .placeholder(R.drawable.image_fill)
                .error(R.drawable.logo)
                .into((ImageView) helper.getView(R.id.post_medium_image));

    }
}
