package com.liuyanggang.microdream.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildLongClickListener;
import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.adapter.ImgPreviewAdapter;
import com.liuyanggang.microdream.base.BaseActivity;
import com.liuyanggang.microdream.entity.HomepageEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName ImgPreviewActivity
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/26
 * @Version 1.0
 */
public class ImgPreviewActivity extends BaseActivity {
    @BindView(R.id.pagerWrap)
    ViewGroup mPagerWrap;
    RecyclerView mRecyclerView;
    LinearLayoutManager mPagerLayoutManager;
    ImgPreviewAdapter imgPreviewAdapter;
    SnapHelper mSnapHelper;
    private List<HomepageEntity> datas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgpreview);
        initView();
        initRecyclerView();
    }

    private void initRecyclerView() {
        setData();
        mRecyclerView = new RecyclerView(this);
        mPagerLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mPagerLayoutManager);
        imgPreviewAdapter = new ImgPreviewAdapter(R.layout.dialog_imgpreview, datas);
        mRecyclerView.setAdapter(imgPreviewAdapter);
        mPagerWrap.addView(mRecyclerView);
        // PagerSnapHelper每次只能滚动一个item;用LinearSnapHelper则可以一次滚动多个，并最终保证定位
        // mSnapHelper = new LinearSnapHelper();
        mSnapHelper = new PagerSnapHelper();
        mSnapHelper.attachToRecyclerView(mRecyclerView);
        mPagerLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        imgPreviewAdapter.setOnItemChildLongClickListener(new OnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Log.d("361", "onItemLongClick2: " + position);
                return false;
            }
        });
    }

    private void setData() {
        datas = new ArrayList<>();
        ArrayList<String> img = new ArrayList<>();
        img.add("https://img2.woyaogexing.com/2020/05/15/57bfa3a8f46943dd892729c06b3f96cf!400x400.jpeg");
        img.add("https://img2.woyaogexing.com/2020/05/15/57bfa3a8f46943dd892729c06b3f96cf!400x400.jpeg");
        img.add("https://img2.woyaogexing.com/2020/05/15/57bfa3a8f46943dd892729c06b3f96cf!400x400.jpeg");
        img.add("https://img2.woyaogexing.com/2020/05/15/57bfa3a8f46943dd892729c06b3f96cf!400x400.jpeg");
        img.add("https://img2.woyaogexing.com/2020/05/15/57bfa3a8f46943dd892729c06b3f96cf!400x400.jpeg");
        for (int i = 0; i <= 2; i++) {
            HomepageEntity homepageEntity = new HomepageEntity();
            homepageEntity.setImages(img);
            datas.add(homepageEntity);
        }
    }

    private void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.scale_out_center);
    }
}
