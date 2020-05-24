package com.liuyanggang.microdream;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.android.material.navigation.NavigationView;
import com.liuyanggang.microdream.activity.AboutActivity;
import com.liuyanggang.microdream.base.BaseActivity;
import com.liuyanggang.microdream.components.ChangePasswordDialog;
import com.liuyanggang.microdream.components.CustomScrollViewPager;
import com.liuyanggang.microdream.entity.TabEntity;
import com.liuyanggang.microdream.fragment.ImageFragment;
import com.liuyanggang.microdream.fragment.IndexFragment;
import com.liuyanggang.microdream.fragment.MoodFragment;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

import static com.liuyanggang.microdream.entity.MicrodreamEntity.DRAWERLEFTEDGESIZEDP;

/**
 * @ClassName MainActivity
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/19
 * @Version 1.0
 */
public class MainActivity extends BaseActivity implements MainIView {
    private ChangePasswordDialog changePasswordDialog;
    private MainIPresenter mPresenter;
    private QMUITipDialog tipDialog;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;

    private String[] mTitles = {"首页", "心情", "图片"};
    @BindView(R.id.viewPager)
    CustomScrollViewPager mViewPager;
    @BindView(R.id.commonTabLayout)
    CommonTabLayout mTabLayout;
    private ArrayList<QMUIFragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private int[] mIconUnselectIds = {R.mipmap.tab_01, R.mipmap.tab_02, R.mipmap.tab_03};
    private int[] mIconSelectIds = {R.mipmap.tab_1, R.mipmap.tab_2, R.mipmap.tab_3};

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigationView)
    NavigationView navigationView;
    private TextView nikeName;
    private TextView userName;
    private ImageView avatar;
    private Map<String, String> passwordMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initTopBar();
        setFragmrnts();
        initViewPage();
        initNavigationView();
        initListener();
        setData();
    }

    private void setData() {
        this.mPresenter = new MainIPresenter(this);
        String username = MMKVUtil.getStringInfo("username");
        String nickname = MMKVUtil.getStringInfo("nickName");
        String avatarPath = MMKVUtil.getStringInfo("avatarPath");
        userName.setText(username);
        nikeName.setText(nickname);
        Glide.with(getApplicationContext()).load("https://img2.woyaogexing.com/2020/05/15/57bfa3a8f46943dd892729c06b3f96cf!400x400.jpeg")
                .placeholder(R.drawable.image_fill)
                .error(R.drawable.logo)
                .optionalCenterCrop()
                .into(avatar);
    }

    /**
     * 菜单点击
     */
    private void initListener() {
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_1: //个人信息

                    break;
                case R.id.menu_2: //密码修改
                    initChangePassword();
                    break;
                case R.id.menu_3://反馈

                    break;
                case R.id.menu_4: //清除缓存

                    break;
                case R.id.menu_5: //退出登录

                    break;
                case R.id.menu_6: // 关于
                    startActivity(new Intent(this, AboutActivity.class));
                    break;
            }
            //drawerLayout.closeDrawers();//关闭侧滑
            return true;
        });
    }

    /**
     * 初始化密码修改窗口
     */
    private void initChangePassword() {
        changePasswordDialog = new ChangePasswordDialog(this);
        changePasswordDialog.getWindow().setWindowAnimations(R.style.DialogAnimations);
        changePasswordDialog.show();
        changePasswordDialog.setOnClickCloseListener(new ChangePasswordDialog.OnClickCloseListener() {
            @Override
            public void onColseClick() {
                changePasswordDialog.dismiss();
            }

            @Override
            public void getPasswordInfo(String oldPass, String newPass) {
                tipDialog = new QMUITipDialog.Builder(MainActivity.this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                        .setTipWord("正在修改")
                        .create();
                tipDialog.show();
                passwordMap = new HashMap<>();
                passwordMap.put("newPass", RsaUtil.mEncrypt(newPass));
                passwordMap.put("oldPass", RsaUtil.mEncrypt(oldPass));
                mPresenter.changePassword();
            }
        });
    }

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
    }

    private void initView() {
        ButterKnife.bind(this);
    }


    private void initTopBar() {
        mTopBar.setTitle("主页");
    }

    private void setFragmrnts() {
        mFragments.add(new IndexFragment());
        mFragments.add(new MoodFragment());
        mFragments.add(new ImageFragment());
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
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(5);
    }


    @Override
    protected boolean translucentFull() {
        return false;
    }

    @Override
    public void onGetUserInfoSuccess() {

    }

    @Override
    public void onGetUserInfoError(String error) {

    }

    @Override
    public Map<String, String> onGetPasswordInfo() {
        return passwordMap;
    }

    @Override
    public void onChangePasswordSuccess() {
        tipDialog.dismiss();
        changePasswordDialog.dismiss();
        Alerter.create(this)
                .setTitle("提示")
                .setText("密码修改成功")
                .setDuration(3000)
                .setIcon(R.mipmap.logo)
                .enableSwipeToDismiss()
                .setBackgroundResource(R.drawable.atlas_background)
                .show();
    }

    @Override
    public void onChangePasswordError(String error) {
        tipDialog.dismiss();
        ToastyUtil.setNormalPrimary(getApplicationContext(), error, Toasty.LENGTH_LONG);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mPresenter = null;
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
