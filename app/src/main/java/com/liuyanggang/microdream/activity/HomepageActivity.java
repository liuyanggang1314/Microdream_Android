package com.liuyanggang.microdream.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liuyanggang.microdream.MainActivity;
import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.adapter.HomepageAdapter;
import com.liuyanggang.microdream.base.BaseActivity;
import com.liuyanggang.microdream.entity.HomepageEntity;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName HomepageActivity
 * @Description TODO 个人主页
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/25
 * @Version 1.0
 */
public class HomepageActivity extends BaseActivity {
    private List<HomepageEntity> datas;
    private HomepageAdapter adapter;
    @BindString(R.string.choose_photo)
    String choosePhoto;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mCollapsingTopBarLayout)
    QMUICollapsingTopBarLayout mCollapsingTopBarLayout;
    @BindView(R.id.topbar)
    QMUITopBar mTopBar;
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.slfe_background)
    ImageView slfeBackground;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        initView();
        initTopBar();
        initRecyclerView();
        onListener();
    }

    private void initTopBar() {
        mCollapsingTopBarLayout.setStatusBarScrim(getDrawable(R.drawable.scooter_background));
        mCollapsingTopBarLayout.setContentScrim(getDrawable(R.drawable.scooter_background));
        mCollapsingTopBarLayout.setTitle("Microdream");

        mTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());
        mTopBar.addRightImageButton(R.mipmap.camera_fill, R.id.topbar_right_change_button).setOnClickListener(v -> {
            QMUIBottomSheet.BottomListSheetBuilder builder = new QMUIBottomSheet.BottomListSheetBuilder(this);
            builder.setGravityCenter(true)
                    .setSkinManager(QMUISkinManager.defaultInstance(this))
                    .setAddCancelBtn(true)
                    .setAllowDrag(true)
                    .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                        dialog.dismiss();
                        startActivity(new Intent(HomepageActivity.this, MoodeditActivity.class));
                    });
            builder.addItem(getDrawable(R.mipmap.heart_fill), choosePhoto);
            builder.build().show();
        });
    }

    private void onListener() {
        mCollapsingTopBarLayout.setScrimUpdateListener(animation -> {

        });
    }

    private void initRecyclerView() {
        initdata();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new HomepageAdapter(R.layout.acticity_homepage_item, datas);
        adapter.setAnimationEnable(true);
        adapter.setAnimationFirstOnly(false);
        View errorView = getLayoutInflater().inflate(R.layout.layout_emptyview, recyclerView, false);
        adapter.setEmptyView(errorView);
        //添加Android自带的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private void initdata() {
        datas = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            HomepageEntity homepageEntity = new HomepageEntity();
            homepageEntity.setAvatar("https://img2.woyaogexing.com/2020/05/15/57bfa3a8f46943dd892729c06b3f96cf!400x400.jpeg");
            homepageEntity.setDelete(false);
            homepageEntity.setNikeName("liu");
            homepageEntity.setTxt("我喜欢你");
            homepageEntity.setUpdateTime("2020-05-26 17:20:00");
            homepageEntity.setImage1("https://img2.woyaogexing.com/2020/05/15/57bfa3a8f46943dd892729c06b3f96cf!400x400.jpeg");
            homepageEntity.setImage2("https://img2.woyaogexing.com/2020/05/15/57bfa3a8f46943dd892729c06b3f96cf!400x400.jpeg");
            homepageEntity.setImage3("https://img2.woyaogexing.com/2020/05/15/57bfa3a8f46943dd892729c06b3f96cf!400x400.jpeg");
            homepageEntity.setImage4("https://img2.woyaogexing.com/2020/05/15/57bfa3a8f46943dd892729c06b3f96cf!400x400.jpeg");
            homepageEntity.setImage5("https://img2.woyaogexing.com/2020/05/15/57bfa3a8f46943dd892729c06b3f96cf!400x400.jpeg");
            homepageEntity.setImage6("https://img2.woyaogexing.com/2020/05/15/57bfa3a8f46943dd892729c06b3f96cf!400x400.jpeg");
            datas.add(homepageEntity);
        }
    }

    private void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public Intent onLastActivityFinish() {
        return new Intent(this, MainActivity.class);
    }
}
