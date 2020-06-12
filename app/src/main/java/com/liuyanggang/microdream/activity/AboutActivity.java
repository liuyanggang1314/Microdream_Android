package com.liuyanggang.microdream.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.liuyanggang.microdream.MainActivity;
import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.base.BaseActivity;
import com.liuyanggang.microdream.manager.AppUpgradeManager;
import com.liuyanggang.microdream.utils.AnimationUtil;
import com.liuyanggang.microdream.utils.ToastyUtil;
import com.liuyanggang.microdream.utils.VersionUtil;
import com.tapadoo.alerter.Alerter;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.base.CheckCallback;
import org.lzh.framework.updatepluginlib.model.Update;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName AboutActivity
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/24
 * @Version 1.0
 */
public class AboutActivity extends BaseActivity {
    private CommonTitleBar commonTitleBar;
    @BindView(R.id.version)
    TextView version;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.check_updata)
    Button checkUpdate;
    @BindView(R.id.copyright)
    TextView copyright;
    @BindView(R.id.lottieAnimationView)
    LottieAnimationView lottieAnimationView;
    @BindView(R.id.lottieAnimationView_heart)
    LottieAnimationView lottieAnimationViewHeart;
    @BindString(R.string.heartinfo)
    String heartInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
        initTopbar();
        initListener();
    }

    private void initListener() {
        checkUpdate.setOnClickListener(v -> {
            AnimationUtil.initAnimationBounceIn(checkUpdate);
            lottieAnimationViewHeart.playAnimation();
            UpdateConfig.getConfig().setCheckCallback(new CheckCallback() {
                @Override
                public void onCheckStart() {

                }

                @Override
                public void hasUpdate(Update update) {

                }

                @Override
                public void noUpdate() {
                    ToastyUtil.setNormalInfo(getApplicationContext(), "已是最新版本", Toast.LENGTH_SHORT);
                }

                @Override
                public void onCheckError(Throwable t) {

                }

                @Override
                public void onUserCancel() {

                }

                @Override
                public void onCheckIgnore(Update update) {

                }
            });
            AppUpgradeManager.check();
        });
        lottieAnimationViewHeart.setOnClickListener(v -> {
            lottieAnimationViewHeart.playAnimation();
            Alerter.create(this)
                    .setTitle(R.string.app_name)
                    .setText(heartInfo)
                    .setDuration(3000)
                    .setIcon(R.mipmap.logo)
                    .enableSwipeToDismiss()
                    .setBackgroundResource(R.drawable.atlas_background)
                    .show();
        });
        logo.setOnClickListener(v -> {
            lottieAnimationViewHeart.playAnimation();
        });
        copyright.setOnClickListener(view -> {
            startWebExplorerActivity(getString(R.string.microdream_web));
        });
    }

    private void initTopbar() {
        commonTitleBar.setListener((v, action, extra) -> {
            if (action == commonTitleBar.ACTION_LEFT_BUTTON) {
                finish();
            }
        });
    }

    private void initView() {
        ButterKnife.bind(this);
        commonTitleBar = findViewById(R.id.back);
        version.setText(VersionUtil.getversion(this));
        copyright.setText(VersionUtil.getCopyrightInfo(this));
        lottieAnimationView.setSpeed(0.5f);
        lottieAnimationViewHeart.setProgress(1f);
        lottieAnimationViewHeart.setSpeed(0.5f);
    }

    @Override
    public Intent onLastActivityFinish() {
        return new Intent(this, MainActivity.class);
    }
}
