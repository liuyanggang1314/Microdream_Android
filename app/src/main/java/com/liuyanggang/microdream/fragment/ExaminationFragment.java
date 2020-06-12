package com.liuyanggang.microdream.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.activity.HtmlActivity;
import com.liuyanggang.microdream.activity.LoginActivity;
import com.liuyanggang.microdream.adapter.ExaminationAdapter;
import com.liuyanggang.microdream.base.BaseFragment;
import com.liuyanggang.microdream.components.UnauthorizedDialog;
import com.liuyanggang.microdream.entity.ExaminationEntity;
import com.liuyanggang.microdream.entity.MessageEventEntity;
import com.liuyanggang.microdream.manager.AppManager;
import com.liuyanggang.microdream.presenter.ExaminationIPeresenter;
import com.liuyanggang.microdream.utils.CustomAnimation;
import com.liuyanggang.microdream.utils.ToastyUtil;
import com.liuyanggang.microdream.view.ExaminationIView;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.liuyanggang.microdream.entity.HttpEntity.UNAUTHORIZED_STRING;

/**
 * @ClassName IndexFragment
 * @Description TODO 心情主页
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/24
 * @Version 1.0
 */
public class ExaminationFragment extends BaseFragment implements ExaminationIView {
    private ExaminationIPeresenter mPresenter;
    private Unbinder unbinder;
    private ExaminationAdapter adapter;
    private List<ExaminationEntity> datas;
    private PageInfo pageInfo = new PageInfo();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mBoomMenuButton)
    BoomMenuButton mBoomMenuButton;
    @BindView(R.id.qmuiPullRefreshLayout)
    QMUIPullRefreshLayout qmuiPullRefreshLayout;
    @BindString(R.string.shennlu)
    String examinationSubtext;
    private QMUITipDialog tipDialog;

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_examination, null);
        unbinder = ButterKnife.bind(this, view);
        initRecyclerView();
        initBoomMenu();
        initListener();
        setData();
        return view;
    }

    private void initListener() {
        adapter.setOnItemClickListener((adapter, view, position) -> {
            ExaminationEntity examinationEntity = (ExaminationEntity) adapter.getData().get(position);
            String content = examinationEntity.getContent();
            startActivity(new Intent(getActivity(), HtmlActivity.class).putExtra("content", content));
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
                mPresenter.getExaminationList();
            }
        });
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new ExaminationAdapter(R.layout.fragment_examination_item, datas);
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

    private void setData() {
        this.mPresenter = new ExaminationIPeresenter(this);
        mPresenter.getExaminationList();
        getLoadingView();
    }


    private View getHeaderView() {
        View view = getLayoutInflater().inflate(R.layout.fragment_index_item_banner, recyclerView, false);
        return view;
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
     * 初始化加载更多
     */
    private void initLoadMore() {
        adapter.getLoadMoreModule().setEnableLoadMore(true);
        adapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
            pageInfo.nextPage();
            //加载更多
            mPresenter.getExaminationList();
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

    private void initBoomMenu() {
        //mBoomMenuButton.setUse3DTransformAnimation(true);
        mBoomMenuButton.addBuilder(new HamButton.Builder()
                .normalImageRes(R.drawable.logo)
                .normalTextRes(R.string.shennlu)
                .subNormalTextRes(R.string.shennlu)
                .imagePadding(new Rect(30, 30, 30, 30)));
        mBoomMenuButton.addBuilder(new HamButton.Builder()
                .normalImageRes(R.drawable.logo)
                .normalTextRes(R.string.xingce)
                .subNormalTextRes(R.string.xingce)
                .imagePadding(new Rect(30, 30, 30, 30)));
        mBoomMenuButton.addBuilder(new HamButton.Builder()
                .normalImageRes(R.drawable.logo)
                .normalTextRes(R.string.changshi)
                .subNormalTextRes(R.string.changshi)
                .imagePadding(new Rect(30, 30, 30, 30)));
        mBoomMenuButton.addBuilder(new HamButton.Builder()
                .normalImageRes(R.drawable.logo)
                .normalTextRes(R.string.mianshi)
                .subNormalTextRes(R.string.mianshi)
                .imagePadding(new Rect(30, 30, 30, 30)));
        mBoomMenuButton.addBuilder(new HamButton.Builder()
                .normalImageRes(R.drawable.logo)
                .normalTextRes(R.string.shiti)
                .subNormalTextRes(R.string.shiti)
                .imagePadding(new Rect(30, 30, 30, 30)));
        mBoomMenuButton.setDuration(374);
        mBoomMenuButton.setOnBoomListener(new OnBoomListener() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {
                tipdialog("获取数据ing...");
                TextView textView = boomButton.getTextView();
                examinationSubtext = textView.getText().toString();
                EventBus.getDefault().post(new MessageEventEntity(1, examinationSubtext));
                refresh();
                mPresenter.getExaminationList();
            }

            @Override
            public void onBackgroundClick() {

            }

            @Override
            public void onBoomWillHide() {

            }

            @Override
            public void onBoomDidHide() {

            }

            @Override
            public void onBoomWillShow() {

            }

            @Override
            public void onBoomDidShow() {

            }
        });
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        this.mPresenter = null;
    }

    @Override
    public String getModuleName() {
        return examinationSubtext;
    }

    @Override
    public Integer getCurrent() {
        return pageInfo.page;
    }

    @Override
    public void onHomepageSeccess(List<ExaminationEntity> examinationEntities, Integer pages) {
        if (tipDialog != null) {
            tipDialog.dismiss();
        }
        qmuiPullRefreshLayout.finishRefresh();
        if (null == examinationEntities || examinationEntities.size() == 0) {
            adapter.setList(null);
            getEmptyView();
        } else {
            adapter.setList(examinationEntities);
            pageInfo.pages = pages;
            if (pages < 2) {
                adapter.getLoadMoreModule().loadMoreEnd();
            }
        }
    }

    @Override
    public void onHomepageError(String error) {
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
    public void onLoadMore(List<ExaminationEntity> examinationEntities, Integer current) {
        if (tipDialog != null) {
            tipDialog.dismiss();
        }
        adapter.addData(examinationEntities);
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
