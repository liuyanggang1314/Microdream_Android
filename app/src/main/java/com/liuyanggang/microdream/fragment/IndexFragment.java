package com.liuyanggang.microdream.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.adapter.ImageTitleAdapter;
import com.liuyanggang.microdream.adapter.IndexAdapter;
import com.liuyanggang.microdream.base.BaseFragment;
import com.liuyanggang.microdream.entity.DataBean;
import com.liuyanggang.microdream.entity.IndexEntity;
import com.liuyanggang.microdream.presenter.IndexIPeresenter;
import com.liuyanggang.microdream.utils.CustomAnimation;
import com.liuyanggang.microdream.utils.ToastyUtil;
import com.liuyanggang.microdream.view.IndexIView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.indicator.RectangleIndicator;
import com.youth.banner.transformer.ZoomOutPageTransformer;
import com.youth.banner.util.BannerUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @ClassName IndexFragment
 * @Description TODO 主页
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/24
 * @Version 1.0
 */
public class IndexFragment extends BaseFragment implements IndexIView {
    private IndexIPeresenter mPresenter;
    private QMUITipDialog tipDialog;
    private PageInfo pageInfo = new PageInfo();
    private Unbinder unbinder;
    private IndexAdapter adapter;
    private List<IndexEntity> datas;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private Banner banner;
    @BindView(R.id.qmuiPullRefreshLayout)
    QMUIPullRefreshLayout qmuiPullRefreshLayout;


    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_index, null);
        unbinder = ButterKnife.bind(this, view);
        initRecyclerView();
        setData();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBanner();
        initListener();
    }

    private void initBanner() {
        banner.setAdapter(new ImageTitleAdapter(null));
        banner.setIndicator(new RectangleIndicator(getActivity()));
        banner.setIndicatorNormalWidth((int) BannerUtils.dp2px(12));
        banner.setIndicatorSpace((int) BannerUtils.dp2px(4));
        banner.setIndicatorRadius(0);
        banner.setIndicatorSelectedColor(getActivity().getColor(R.color.pink));
        banner.setDelayTime(5000);
        banner.setPageTransformer(new ZoomOutPageTransformer());
        banner.setBannerGalleryMZ(30, 0.8f);
    }

    private void initListener() {
        banner.setOnBannerListener((data, position) -> {
            DataBean dataBean = (DataBean) data;
            startWebExplorerActivity(dataBean.getLink(), getActivity());
        });
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
                mPresenter.getIndexList();
                mPresenter.getIndexBannerList();
            }
        });

        adapter.setOnItemClickListener((adapter, view, position) -> {
            IndexEntity indexEntity = (IndexEntity) adapter.getData().get(position);
            startWebExplorerActivity(indexEntity.getLink(), getActivity());
        });
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new IndexAdapter(R.layout.fragment_index_item, datas);
        adapter.setAnimationEnable(true);
        adapter.setAnimationFirstOnly(false);
        adapter.setAdapterAnimation(new CustomAnimation());
        adapter.addHeaderView(getHeaderView());
        adapter.addHeaderView(getLayoutInflater().inflate(R.layout.layout_horizontal_line, recyclerView, false));
        getEmptyView();
        //添加Android自带的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        initLoadMore();
    }

    private void setData() {
        this.mPresenter = new IndexIPeresenter(this);
        this.mPresenter.getIndexList();
        this.mPresenter.getIndexBannerList();
        getLoadingView();
        tipdialog("获取数据ing...");
    }

    private View getHeaderView() {
        View view = getLayoutInflater().inflate(R.layout.fragment_index_item_banner, recyclerView, false);
        banner = view.findViewById(R.id.banner);
        return view;
    }

    /**
     * 取recyclerview错误布局
     *
     * @return
     */
    private void getErrorView() {
        View errorView = getLayoutInflater().inflate(R.layout.layout_errorview, recyclerView, false);
        errorView.setOnClickListener(v -> {

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
    public void onStart() {
        super.onStart();
        banner.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        this.mPresenter = null;
    }


    /**
     * 初始化加载更多
     */
    private void initLoadMore() {
        adapter.getLoadMoreModule().setEnableLoadMore(true);
        adapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
            pageInfo.nextPage();
            //加载更多
            mPresenter.getIndexList();
        });
        //adapter.getLoadMoreModule().setAutoLoadMore(true);
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        //adapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
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

    @Override
    public void onBannerSeccess(List<DataBean> beanList) {
        banner.setDatas(beanList);
    }

    @Override
    public void onBannerError(String error) {
        ToastyUtil.setNormalDanger(getActivity(), "轮播图获取失败", Toast.LENGTH_SHORT);
    }

    @Override
    public Integer getCurrent() {
        return pageInfo.page;
    }

    @Override
    public void onIndexSeccess(List<IndexEntity> indexList) {
        tipDialog.dismiss();
        qmuiPullRefreshLayout.finishRefresh();
        if (null == indexList || indexList.size() == 0) {
            getEmptyView();
        } else if (indexList.size() < 10) {
            adapter.setList(indexList);
            adapter.getLoadMoreModule().loadMoreEnd();
        } else {
            adapter.setList(indexList);
        }
    }

    @Override
    public void onIndexError(String error) {
        tipDialog.dismiss();
        qmuiPullRefreshLayout.finishRefresh();
        adapter.getLoadMoreModule().setEnableLoadMore(false);
        ToastyUtil.setNormalDanger(getActivity(), error, Toast.LENGTH_SHORT);
        getErrorView();
    }

    @Override
    public void onIndexLoadmoreSeccess(List<IndexEntity> indexList) {
        if (null == indexList || indexList.size() == 0) {
            adapter.getLoadMoreModule().setEnableLoadMore(false);
            adapter.getLoadMoreModule().loadMoreComplete();
        } else {
            adapter.addData(indexList);
            adapter.getLoadMoreModule().loadMoreComplete();
        }

    }


    /**
     * 加载UI
     *
     * @param info
     */
    private void tipdialog(String info) {
        tipDialog = new QMUITipDialog.Builder(getActivity())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(info)
                .create();
        tipDialog.show();
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
