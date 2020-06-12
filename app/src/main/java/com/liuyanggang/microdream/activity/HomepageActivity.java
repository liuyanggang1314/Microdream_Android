package com.liuyanggang.microdream.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.adapter.HomepageAdapter;
import com.liuyanggang.microdream.base.BaseActivity;
import com.liuyanggang.microdream.components.SureDialog;
import com.liuyanggang.microdream.components.UnauthorizedDialog;
import com.liuyanggang.microdream.entity.HomepageEntity;
import com.liuyanggang.microdream.entity.MessageEventEntity;
import com.liuyanggang.microdream.manager.AppManager;
import com.liuyanggang.microdream.presenter.HomepageIPeresenter;
import com.liuyanggang.microdream.utils.CustomAnimation;
import com.liuyanggang.microdream.utils.MMKVUtil;
import com.liuyanggang.microdream.utils.ToastyUtil;
import com.liuyanggang.microdream.view.HomepageIView;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView2;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Objects;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.liuyanggang.microdream.entity.HttpEntity.MICRODREAM_SERVER_IMG;
import static com.liuyanggang.microdream.entity.HttpEntity.UNAUTHORIZED_STRING;

/**
 * @ClassName HomepageActivity
 * @Description TODO 个人主页
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/25
 * @Version 1.0
 */
public class HomepageActivity extends BaseActivity implements HomepageIView {
    private HomepageIPeresenter mPresenter;
    private HomepageAdapter adapter;
    private PageInfo pageInfo = new PageInfo();
    private List<HomepageEntity> datas;
    private SureDialog sureDialog;
    private QMUITipDialog tipDialog;
    private Long moodId;
    private Integer mPosition;
    @BindString(R.string.choose_photo)
    String choosePhoto;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.mCollapsingTopBarLayout)
    QMUICollapsingTopBarLayout mCollapsingTopBarLayout;
    @BindView(R.id.topbar)
    QMUITopBar mTopBar;
    @BindView(R.id.avatar)
    QMUIRadiusImageView2 avatar;
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
        setData();
    }

    private void setData() {
        this.mPresenter = new HomepageIPeresenter(this);
        mPresenter.getMoodList();
        getLoadingView();
    }

    /**
     * 初始化标题
     */
    private void initTopBar() {
        mCollapsingTopBarLayout.setStatusBarScrim(getDrawable(R.drawable.scooter_background));
        mCollapsingTopBarLayout.setContentScrim(getDrawable(R.drawable.scooter_background));
        mCollapsingTopBarLayout.setTitle(MMKVUtil.getStringInfo("nickName"));

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

    /**
     * 监听事件
     */
    private void onListener() {


        adapter.setOnItemClickListener((adapter, view, position) -> {

        });
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            HomepageEntity homepageEntity = (HomepageEntity) adapter.getData().get(position);
            moodId = homepageEntity.getId();
            mPosition = position;
            sureDialog = new SureDialog(HomepageActivity.this, "确定删除吗？");
            try {
                Objects.requireNonNull(sureDialog.getWindow()).setWindowAnimations(R.style.DialogAnimations);
            } catch (Exception ignored) {

            }
            sureDialog.setOnClickCloseListener(new SureDialog.OnClickCloseListener() {
                @Override
                public void onColseClick() {
                    sureDialog.dismiss();
                }

                @Override
                public void onEnterClick() {
                    tipdialog("正在修改");
                    mPresenter.onDeleteMood();
                }
            });
            sureDialog.show();
        });
    }

    /**
     * 初始化recyclerview
     */
    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new HomepageAdapter(R.layout.activity_homepage_item, datas);
        adapter.setAnimationEnable(true);
        adapter.setAnimationFirstOnly(false);
        adapter.setAdapterAnimation(new CustomAnimation());
        getEmptyView();
        //添加Android自带的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        initLoadMore();
    }

    /**
     * 初始化view
     */
    private void initView() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        Glide.with(getApplicationContext()).load(MICRODREAM_SERVER_IMG + MMKVUtil.getStringInfo("avatarName"))
                .placeholder(R.drawable.image_fill)
                .error(R.drawable.logo)
                .into(avatar);
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

    /**
     * 加载UI
     *
     * @param info
     */
    private void tipdialog(String info) {
        tipDialog = new QMUITipDialog.Builder(HomepageActivity.this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(info)
                .create();
        tipDialog.show();
    }

    /**
     * 初始化加载更多
     */
    private void initLoadMore() {
        //adapter.getLoadMoreModule().setAutoLoadMore(true);
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        //adapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
        adapter.getLoadMoreModule().setEnableLoadMore(true);
        adapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
            pageInfo.nextPage();
            //加载更多
            mPresenter.getMoodList();
        });
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
     * 接口成功返回数据
     *
     * @param homepageEntities
     */
    @Override
    public void onHomepageSeccess(List<HomepageEntity> homepageEntities, Integer pages) {
        if (null == homepageEntities || homepageEntities.size() == 0) {
            adapter.setList(null);
            getEmptyView();
        } else {
            adapter.setList(homepageEntities);
            pageInfo.pages = pages;
            if (pages < 2) {
                adapter.getLoadMoreModule().loadMoreEnd();
            }
        }
    }

    /**
     * 接口返回错误信息
     *
     * @param error
     */
    @Override
    public void onHomepageError(String error) {
        adapter.getLoadMoreModule().setEnableLoadMore(false);
        if (UNAUTHORIZED_STRING.equals(error)) {
            UnauthorizedDialog unauthorizedDialog = new UnauthorizedDialog(this);
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
            getErrorView();
        }
    }

    /**
     * 加载更多
     *
     * @param homepageEntities
     */
    @Override
    public void onLoadMore(List<HomepageEntity> homepageEntities, Integer current) {
        adapter.addData(homepageEntities);
        adapter.getLoadMoreModule().loadMoreComplete();
        if (current == pageInfo.pages) {
            adapter.getLoadMoreModule().setEnableLoadMore(false);
            adapter.getLoadMoreModule().loadMoreEnd();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mPresenter = null;
        EventBus.getDefault().unregister(this);
    }

    /**
     * 获取当前页
     *
     * @return
     */
    @Override
    public Integer getCurrent() {
        return pageInfo.page;
    }

    /**
     * 获取心情id
     *
     * @return
     */
    @Override
    public Long getMoodId() {
        return moodId;
    }

    /**
     * 删除心情成功
     */
    @Override
    public void onDeleteSeccess() {
        tipDialog.dismiss();
        sureDialog.dismiss();
        ToastyUtil.setNormalSuccess(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT);
        adapter.removeAt(mPosition);
    }

    /**
     * 删除心情失败
     *
     * @param error
     */
    @Override
    public void onDeleteError(String error) {
        tipDialog.dismiss();
        sureDialog.dismiss();
        if (UNAUTHORIZED_STRING.equals(error)) {
            UnauthorizedDialog unauthorizedDialog = new UnauthorizedDialog(this);
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

    /**
     * 心情发表成功刷新
     */

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEventEntity messageEvent) {
        if ("onUpdateMoodListListener".equals(messageEvent.getMessage())) {
            refresh();
            mPresenter.getMoodList();
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
