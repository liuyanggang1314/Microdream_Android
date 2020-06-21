package com.liuyanggang.microdream.activity.image;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.activity.LoginActivity;
import com.liuyanggang.microdream.adapter.ImagePreviewAdapter;
import com.liuyanggang.microdream.base.BaseActivity;
import com.liuyanggang.microdream.components.UnauthorizedDialog;
import com.liuyanggang.microdream.entity.ImageEntity;
import com.liuyanggang.microdream.manager.AppManager;
import com.liuyanggang.microdream.presenter.ImageIPeresenter;
import com.liuyanggang.microdream.utils.DownloadUtils;
import com.liuyanggang.microdream.utils.ToastyUtil;
import com.liuyanggang.microdream.view.ImageByTypeIView;
import com.qmuiteam.qmui.widget.QMUIVerticalTextView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.pullLayout.QMUIPullLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.liuyanggang.microdream.entity.HttpEntity.UNAUTHORIZED_STRING;

/**
 * @ClassName ImagePreviewActivity
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/6/11
 * @Version 1.0
 */
public class ImagePreviewActivity extends BaseActivity implements ImageByTypeIView {
    private ImageIPeresenter mPresenter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ImagePreviewAdapter adapter;
    private List<ImageEntity> datas;
    private Long type;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.pull_layout)
    QMUIPullLayout mPullLayout;
    @BindView(R.id.image_top)
    QMUIVerticalTextView topQMUIVerticalTextView;
    @BindView(R.id.image_bottom)
    QMUIVerticalTextView bottomQMUIVerticalTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getLong("type");
        } else {
            type = 1L;
        }
        initView();
        initTopbar();
        initRecyclerView();
        initListener();
        setData();
    }

    private void initTopbar() {
        back.setOnClickListener(view -> {
            finish();
        });
    }

    private void initPopup(ImageEntity imageEntity) {
        final String[] items = new String[]{"保存图片"};
        new QMUIDialog.CheckableDialogBuilder(this)
                .addItems(items, (dialog, which) -> {
                    DownloadUtils.downloadimg(imageEntity.getUrl(), getApplicationContext());
                    dialog.dismiss();
                })
                .create(R.style.MyDialogPink).show();
    }

    private void setData() {
        getLoadingView();
        this.mPresenter = new ImageIPeresenter(this);
        mPresenter.getImageByType();
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new ImagePreviewAdapter(R.layout.activity_image_preview_item, datas);
        recyclerView.setAdapter(adapter);
        // PagerSnapHelper每次只能滚动一个item;用LinearSnapHelper则可以一次滚动多个，并最终保证定位
        // mSnapHelper = new LinearSnapHelper();
        SnapHelper mSnapHelper = new PagerSnapHelper();
        mSnapHelper.attachToRecyclerView(recyclerView);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    }

    private void initListener() {
        adapter.setOnItemChildLongClickListener((adapter, view, position) -> {
            ImageEntity imageEntity = (ImageEntity) adapter.getData().get(position);
            initPopup(imageEntity);
            return false;
        });
        mPullLayout.setActionListener(pullAction -> mPullLayout.postDelayed(() -> mPullLayout.finishActionRun(pullAction), 1000));
    }

    private void initView() {
        ButterKnife.bind(this);
        ToastyUtil.setNormalPrimary(getApplicationContext(), "向上滑可查看更多", Toast.LENGTH_SHORT);
        topQMUIVerticalTextView.setVerticalMode(false);
        bottomQMUIVerticalTextView.setVerticalMode(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mPresenter = null;
    }

    @Override
    public Long getType() {
        return type;
    }

    /**
     * 获取recyclerview加载布局
     *
     * @return
     */
    private void getLoadingView() {
        View loadingView = getLayoutInflater().inflate(R.layout.layout_loadingview, recyclerView, false);
        loadingView.setOnClickListener(v -> {

        });
        adapter.setEmptyView(loadingView);
    }

    /**
     * 获取recyclerview空布局
     *
     * @return
     */
    private void getEmptyView() {
        View emptyView = getLayoutInflater().inflate(R.layout.layout_emptyview, recyclerView, false);
        emptyView.setOnClickListener(v -> {

        });
        adapter.setEmptyView(emptyView);
    }

    @Override
    public void onImageByTypeSeccess(List<ImageEntity> imageEntities) {
        if (null == imageEntities || imageEntities.size() == 0) {
            adapter.setList(null);
            getEmptyView();
        } else {
            adapter.setList(imageEntities);
        }
    }

    @Override
    public void onImageByTypeError(String error) {
        if (UNAUTHORIZED_STRING.equals(error)) {
            UnauthorizedDialog unauthorizedDialog = new UnauthorizedDialog(getApplicationContext());
            unauthorizedDialog.setOnClickCloseListener(new UnauthorizedDialog.OnClickCloseListener() {
                @Override
                public void onColseClick() {
                    unauthorizedDialog.dismiss();
                }

                @Override
                public void onEnterClick() {
                    unauthorizedDialog.dismiss();
                    AppManager.getInstance().finishOtherActivity(AppManager.getInstance().currentActivity());
                    startActivity(new Intent(AppManager.getInstance().currentActivity(), LoginActivity.class));
                    AppManager.getInstance().finishActivity(AppManager.getInstance().currentActivity());
                }
            });
            unauthorizedDialog.show();
        } else {
            ToastyUtil.setNormalDanger(getApplicationContext(), error, Toast.LENGTH_SHORT);
        }
    }

}
