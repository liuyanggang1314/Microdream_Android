package com.liuyanggang.microdream.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.activity.LoginActivity;
import com.liuyanggang.microdream.activity.image.ImagePreviewActivity;
import com.liuyanggang.microdream.adapter.ImageAdapter;
import com.liuyanggang.microdream.base.BaseFragment;
import com.liuyanggang.microdream.components.UnauthorizedDialog;
import com.liuyanggang.microdream.entity.ImageEntity;
import com.liuyanggang.microdream.manager.AppManager;
import com.liuyanggang.microdream.presenter.ImageIPeresenter;
import com.liuyanggang.microdream.utils.CustomAnimation;
import com.liuyanggang.microdream.utils.ToastyUtil;
import com.liuyanggang.microdream.view.ImageIView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.liuyanggang.microdream.entity.HttpEntity.UNAUTHORIZED_STRING;

/**
 * @ClassName ImageFragment
 * @Description TODO 图片主页
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/24
 * @Version 1.0
 */
public class ImageFragment extends BaseFragment implements ImageIView {
    private Unbinder unbinder;
    private ImageIPeresenter mPresenter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ImageAdapter adapter;
    private List<ImageEntity> datas;
    private PageInfo pageInfo = new PageInfo();
    @BindView(R.id.qmuiPullRefreshLayout)
    QMUIPullRefreshLayout qmuiPullRefreshLayout;
    private QMUITipDialog tipDialog;

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_image, null);
        unbinder = ButterKnife.bind(this, view);
        initRecyclerView();
        initListener();
        setData();
        return view;
    }

    private void setData() {
        getLoadingView();
        this.mPresenter = new ImageIPeresenter(this);
        mPresenter.getImageList();
    }

    private void initListener() {
        qmuiPullRefreshLayout.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                tipdialog("获取数据ing...");
                refresh();
                mPresenter.getImageList();
            }
        });
        adapter.setOnItemClickListener((adapter, view, position) -> {
            ImageEntity imageEntity = (ImageEntity) adapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putLong("type", imageEntity.getType());
            Intent intent = new Intent(getContext(), ImagePreviewActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new ImageAdapter(R.layout.fragment_image_item, datas);
        adapter.setAnimationEnable(true);
        adapter.setAnimationFirstOnly(false);
        adapter.setAdapterAnimation(new CustomAnimation());
        //adapter.addHeaderView(getHeaderView());
        //adapter.addHeaderView(getLayoutInflater().inflate(R.layout.layout_horizontal_line, recyclerView, false));
        getEmptyView();
        //添加Android自带的分割线
        // recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        initLoadMore();
    }

    /**
     * 加载UI
     *
     * @param info
     */
    private void tipdialog(String info) {
        tipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(info)
                .create();
        tipDialog.show();
    }

    /**
     * 刷新
     */
    private void refresh() {
        // 这里的作用是防止下拉刷新的时候还可以上拉加载
        adapter.getLoadMoreModule().setEnableLoadMore(false);
        // 下拉刷新，需要重置页数
        pageInfo.reset();
    }

    /**
     * 初始化加载更多
     */
    private void initLoadMore() {
        adapter.getLoadMoreModule().setEnableLoadMore(true);
        adapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
            pageInfo.nextPage();
            //加载更多
            this.mPresenter.getImageList();
        });
        //adapter.getLoadMoreModule().setAutoLoadMore(true);
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        //adapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
    }

    /**
     * 取recyclerview错误布局
     *
     * @return
     */
    private void getErrorView() {
        View errorView = getLayoutInflater().inflate(R.layout.layout_errorview, recyclerView, false);
        errorView.setOnClickListener(v -> {
            this.mPresenter.getImageList();
        });
        adapter.setEmptyView(errorView);
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        this.mPresenter = null;
    }

    @Override
    public Integer getCurrent() {
        return pageInfo.page;
    }

    @Override
    public void onImageSeccess(List<ImageEntity> imageEntities, Integer pages) {
        if (tipDialog != null) {
            tipDialog.dismiss();
        }
        qmuiPullRefreshLayout.finishRefresh();
        if (null == imageEntities || imageEntities.size() == 0) {
            adapter.setList(null);
            getEmptyView();
        } else {
            adapter.setList(imageEntities);
            pageInfo.pages = pages;
            if (pages < 2) {
                adapter.getLoadMoreModule().loadMoreEnd();
            }
        }
    }

    @Override
    public void onImageError(String error) {
        if (tipDialog != null) {
            tipDialog.dismiss();
        }
        qmuiPullRefreshLayout.finishRefresh();
        adapter.getLoadMoreModule().setEnableLoadMore(false);
        if (UNAUTHORIZED_STRING.equals(error)) {
            UnauthorizedDialog unauthorizedDialog = new UnauthorizedDialog(getActivity());
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
            ToastyUtil.setNormalDanger(getContext(), error, Toast.LENGTH_SHORT);
            getErrorView();
        }
    }

    @Override
    public void onLoadMore(List<ImageEntity> imageEntities, Integer current) {
        if (tipDialog != null) {
            tipDialog.dismiss();
        }
        adapter.addData(imageEntities);
        adapter.getLoadMoreModule().loadMoreComplete();
        if (current == pageInfo.pages) {
            adapter.getLoadMoreModule().setEnableLoadMore(false);
            adapter.getLoadMoreModule().loadMoreEnd();
        }
    }

    class PageInfo {
        int page = 1;
        int pages = 0;

        void nextPage() {
            page++;
        }

        void reset() {
            page = 1;
            pages = 0;
        }

        boolean isFirstPage() {
            return page == 1;
        }
    }
}
