package com.liuyanggang.microdream;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.android.material.navigation.NavigationView;
import com.liuyanggang.microdream.activity.AboutActivity;
import com.liuyanggang.microdream.activity.HomepageActivity;
import com.liuyanggang.microdream.activity.LoginActivity;
import com.liuyanggang.microdream.activity.PersonalInformationActivity;
import com.liuyanggang.microdream.activity.chat.ContactActivity;
import com.liuyanggang.microdream.base.BaseActivity;
import com.liuyanggang.microdream.components.ChangePasswordDialog;
import com.liuyanggang.microdream.components.CleanDataCacheDialog;
import com.liuyanggang.microdream.components.LogoutDialog;
import com.liuyanggang.microdream.components.UnauthorizedDialog;
import com.liuyanggang.microdream.entity.MessageEventEntity;
import com.liuyanggang.microdream.entity.TabEntity;
import com.liuyanggang.microdream.fragment.ConversationFragment;
import com.liuyanggang.microdream.fragment.ExaminationFragment;
import com.liuyanggang.microdream.fragment.ImageFragment;
import com.liuyanggang.microdream.fragment.IndexFragment;
import com.liuyanggang.microdream.manager.AppManager;
import com.liuyanggang.microdream.presenter.MainIPresenter;
import com.liuyanggang.microdream.utils.DrawerLayoutUtil;
import com.liuyanggang.microdream.utils.MMKVUtil;
import com.liuyanggang.microdream.utils.RsaUtil;
import com.liuyanggang.microdream.utils.ToastyUtil;
import com.liuyanggang.microdream.view.MainIView;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.arch.QMUIFragmentPagerAdapter;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.tapadoo.alerter.Alerter;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationManagerKit;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

import static com.liuyanggang.microdream.MicrodreamApplication.getContext;
import static com.liuyanggang.microdream.entity.HttpEntity.MICRODREAM_SERVER_IMG;
import static com.liuyanggang.microdream.entity.HttpEntity.UNAUTHORIZED_STRING;
import static com.liuyanggang.microdream.entity.MicrodreamEntity.DRAWERLEFTEDGESIZEDP;

/**
 * @ClassName MainActivity
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/19
 * @Version 1.0
 */
public class MainActivity extends BaseActivity implements MainIView, ConversationManagerKit.MessageUnreadWatcher {
    private ChangePasswordDialog changePasswordDialog;
    private MainIPresenter mPresenter;
    private QMUITipDialog tipDialog;
    private LogoutDialog logoutDialog;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;

    private String[] mTitles = {"首页", "公务员", "图片", "chat"};
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.commonTabLayout)
    CommonTabLayout mTabLayout;
    private ArrayList<QMUIFragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private int[] mIconUnselectIds = {R.mipmap.tab_01, R.mipmap.tab_02, R.mipmap.tab_03, R.mipmap.tab_04};
    private int[] mIconSelectIds = {R.mipmap.tab_1, R.mipmap.tab_2, R.mipmap.tab_3, R.mipmap.tab_4};

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigationView)
    NavigationView navigationView;
    private TextView nikeName;
    private TextView userName;
    private ImageView avatar;
    private ConstraintLayout constraintLayout;
    private Map<String, String> passwordMap;
    @BindString(R.string.shennlu)
    String examinationSubtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initMsg();
        initTopBar();
        setFragmrnts();
        initViewPage();
        initNavigationView();
        initListener();
        setData();
    }

    private void initMsg() {
        // 未读消息监视器
        ConversationManagerKit.getInstance().addUnreadWatcher(this);
    }

    /**
     * 数据初始化
     */
    private void setData() {
        this.mPresenter = new MainIPresenter(this);
        String username = MMKVUtil.getStringInfo("username");
        String nickname = MMKVUtil.getStringInfo("nickName");
        String avatarName = MMKVUtil.getStringInfo("avatarName");
        userName.setText(username);
        nikeName.setText(nickname);
        Glide.with(getApplicationContext()).load(MICRODREAM_SERVER_IMG + avatarName)
                .placeholder(R.drawable.image_fill)
                .error(R.drawable.logo)
                .optionalCircleCrop()
                .into(avatar);
    }

    /**
     * 菜单点击
     */
    private void initListener() {
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_1: //个人信息
                    startActivity(new Intent(getContext(), PersonalInformationActivity.class));
                    break;
                case R.id.menu_2: //密码修改
                    initChangePassword();
                    break;
                case R.id.menu_3://反馈
                    startWebExplorerActivity(getString(R.string.feedback_web));
                    break;
                case R.id.menu_4: //清除缓存
                    initCleanDataCache();
                    break;
                case R.id.menu_5: //退出登录
                    logout();
                    break;
                case R.id.menu_6: // 关于
                    startActivity(new Intent(getContext(), AboutActivity.class));
                    break;
            }
            //drawerLayout.closeDrawers();//关闭侧滑
            return true;
        });
        constraintLayout.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), HomepageActivity.class));
        });
    }

    /**
     * 退出登录
     */
    private void logout() {
        logoutDialog = new LogoutDialog(this);
        try {
            Objects.requireNonNull(logoutDialog.getWindow()).setWindowAnimations(R.style.DialogAnimations);
        } catch (Exception ignored) {

        }
        logoutDialog.setOnClickCloseListener(new LogoutDialog.OnClickCloseListener() {
            @Override
            public void onColseClick() {
                logoutDialog.dismiss();
            }

            @Override
            public void onEnterClick() {
                tipdialog("正在退出");
                mPresenter.logout();
            }
        });
        logoutDialog.show();
    }

    /**
     * 缓存清除
     */
    private void initCleanDataCache() {
        CleanDataCacheDialog cleanDataCacheDialog = new CleanDataCacheDialog(this);
        try {
            Objects.requireNonNull(cleanDataCacheDialog.getWindow()).setWindowAnimations(R.style.DialogAnimations);
        } catch (Exception ignored) {

        }
        cleanDataCacheDialog.setOnClickCloseListener(new CleanDataCacheDialog.OnClickCloseListener() {
            @Override
            public void onColseClick() {
                cleanDataCacheDialog.dismiss();
            }

            @Override
            public void onEnterClick() {
                ToastyUtil.setNormalSuccess(getContext(), getString(R.string.clear_cache_success), Toast.LENGTH_SHORT);
                cleanDataCacheDialog.dismiss();
            }
        });
        cleanDataCacheDialog.show();
    }

    /**
     * 初始化密码修改窗口
     */
    private void initChangePassword() {
        changePasswordDialog = new ChangePasswordDialog(this);
        try {
            Objects.requireNonNull(changePasswordDialog.getWindow()).setWindowAnimations(R.style.DialogAnimations);
        } catch (Exception ignored) {

        }

        changePasswordDialog.show();
        changePasswordDialog.setOnClickCloseListener(new ChangePasswordDialog.OnClickCloseListener() {
            @Override
            public void onColseClick() {
                changePasswordDialog.dismiss();
            }

            @Override
            public void getPasswordInfo(String oldPass, String newPass) {
                tipdialog("正在修改");
                passwordMap = new HashMap<>();
                passwordMap.put("newPass", RsaUtil.mEncrypt(newPass));
                passwordMap.put("oldPass", RsaUtil.mEncrypt(oldPass));
                mPresenter.changePassword();
            }
        });
    }

    /**
     * 加载UI
     *
     * @param info
     */
    private void tipdialog(String info) {
        tipDialog = new QMUITipDialog.Builder(MainActivity.this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(info)
                .create();
        tipDialog.show();
    }

    /**
     * 初始化左侧栏
     */
    private void initNavigationView() {
        //防止图片灰色 使图片显示原本的颜色
        navigationView.setItemIconTintList(null);
        //DrawerLayoutUtil.setDrawerLeftEdgeSize(this, drawerLayout, 0.3f);
        DrawerLayoutUtil.setDrawerLeftEdgeSizeDp(drawerLayout, DRAWERLEFTEDGESIZEDP);
        //navigationView.setCheckedItem(R.id.menu_1);//默认选择第一个
        View drawview = navigationView.getHeaderView(0);
        nikeName = drawview.findViewById(R.id.nikeName);
        userName = drawview.findViewById(R.id.userName);
        avatar = drawview.findViewById(R.id.avatar);
        constraintLayout = drawview.findViewById(R.id.constraintLayout);
    }

    private void initView() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        overridePendingTransition(R.anim.fragment_open_enter, 0);
    }


    private void initTopBar() {
        mTopBar.setTitle("主页");
    }

    private void setFragmrnts() {
        mFragments.add(new IndexFragment());
        mFragments.add(new ExaminationFragment());
        mFragments.add(new ImageFragment());
        mFragments.add(new ConversationFragment());
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
    }

    private void initViewPage() {
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
                mTopBar.setTitle(mTitles[position]);
                mTopBar.removeAllRightViews();
                if (position == 1) {
                    mTopBar.setSubTitle(examinationSubtext);
                } else {
                    mTopBar.setSubTitle(null);
                }
                if (position == 3) {
                    mTopBar.addRightImageButton(R.mipmap.address_book, R.id.topbar_right_change_button).setOnClickListener(v -> {
                        startActivity(new Intent(getContext(), ContactActivity.class));
                    });
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
                mTopBar.setTitle(mTitles[position]);
                mTopBar.removeAllRightViews();
                if (position == 1) {
                    mTopBar.setSubTitle(examinationSubtext);
                } else {
                    mTopBar.setSubTitle(null);
                }
                if (position == 3) {
                    mTopBar.addRightImageButton(R.mipmap.address_book, R.id.topbar_right_change_button).setOnClickListener(v -> {
                        startActivity(new Intent(getContext(), ContactActivity.class));
                    });
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(5);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEventEntity messageEvent) {
        if (messageEvent.getType() == 1) {
            examinationSubtext = messageEvent.getMessage();
            mTopBar.setSubTitle(examinationSubtext);
        }
    }


    /**
     * 获取用户信息成功返回
     */
    @Override
    public void onGetUserInfoSuccess() {

    }

    /**
     * 获取用户信息错误返回
     *
     * @param error
     */
    @Override
    public void onGetUserInfoError(String error) {

    }

    /**
     * 密码修改值获取
     *
     * @return
     */
    @Override
    public Map<String, String> onGetPasswordInfo() {
        return passwordMap;
    }

    /**
     * 密码修成成功
     */
    @Override
    public void onChangePasswordSuccess() {
        tipDialog.dismiss();
        changePasswordDialog.dismiss();
        Alerter.create(this)
                .setTitle(R.string.app_name)
                .setText("密码修改成功")
                .setDuration(3000)
                .setIcon(R.mipmap.logo)
                .enableSwipeToDismiss()
                .setBackgroundResource(R.drawable.atlas_background)
                .show();
    }

    /**
     * 密码修改失败
     *
     * @param error
     */
    @Override
    public void onChangePasswordError(String error) {
        if (UNAUTHORIZED_STRING.equals(error)) {
            tipDialog.dismiss();
            changePasswordDialog.dismiss();
            UnauthorizedDialog unauthorizedDialog = new UnauthorizedDialog(this);
            unauthorizedDialog.setOnClickCloseListener(new UnauthorizedDialog.OnClickCloseListener() {
                @Override
                public void onColseClick() {
                    unauthorizedDialog.dismiss();
                }

                @Override
                public void onEnterClick() {
                    unauthorizedDialog.dismiss();
                    AppManager.getInstance().finishOtherActivity(MainActivity.class);
                    finish();
                }
            });
            unauthorizedDialog.show();
        } else {
            tipDialog.dismiss();
            ToastyUtil.setNormalPrimary(getApplicationContext(), error, Toasty.LENGTH_LONG);
        }
    }

    /**
     * 退出登录成功
     */
    @Override
    public void onLogoutSuccess() {
        tipDialog.dismiss();
        logoutDialog.dismiss();
        ToastyUtil.setNormalSuccess(getApplicationContext(), "退出成功", Toasty.LENGTH_LONG);
        startActivity(new Intent(getContext(), LoginActivity.class));
        finish();
    }

    /**
     * 退出登录失败
     *
     * @param error
     */
    @Override
    public void onLogoutError(String error) {
        tipDialog.dismiss();
        ToastyUtil.setNormalPrimary(getApplicationContext(), error, Toasty.LENGTH_LONG);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ConversationManagerKit.getInstance().destroyConversation();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.fragment_close_exit);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mPresenter = null;
        EventBus.getDefault().unregister(this);
    }

    /**
     * 消息数量
     *
     * @param count
     */
    @Override
    public void updateUnread(int count) {
        if (count > 0) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);//振动
            vibrator.vibrate(200);
            mTabLayout.showMsg(3, count);
        } else {
            mTabLayout.hideMsg(3);
        }
    }

    private class MyPagerAdapter extends QMUIFragmentPagerAdapter {

        MyPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public QMUIFragment createFragment(int position) {
            return mFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

    }
}
