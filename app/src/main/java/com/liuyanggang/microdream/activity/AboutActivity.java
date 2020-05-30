package com.liuyanggang.microdream.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.liuyanggang.microdream.MainActivity;
import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.base.BaseActivity;
import com.liuyanggang.microdream.manager.AppUpgradeManager;
import com.liuyanggang.microdream.utils.AnimationUtil;
import com.liuyanggang.microdream.utils.VersionUtil;
import com.qmuiteam.qmui.span.QMUITouchableSpan;
import com.qmuiteam.qmui.widget.textview.QMUISpanTouchFixTextView;
import com.tapadoo.alerter.Alerter;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

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
    QMUISpanTouchFixTextView copyright;
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
        copyright.setMovementMethodDefault();
        copyright.setText(generateSp(VersionUtil.getCopyrightInfo(this)));
        lottieAnimationView.setSpeed(0.5f);
        lottieAnimationViewHeart.setProgress(1f);
        lottieAnimationViewHeart.setSpeed(0.5f);
    }

    private SpannableString generateSp(String text) {
        String highlight1 = "微梦想";
        SpannableString sp = new SpannableString(text);
        int start = 0, end;
        int index;
        while ((index = text.indexOf(highlight1, start)) > -1) {
            end = index + highlight1.length();
            sp.setSpan(new QMUITouchableSpan(
                    getColor(R.color.pink),
                    Color.RED,
                    Color.TRANSPARENT,
                    Color.TRANSPARENT) {
                @Override
                public void onSpanClick(View widget) {
                    startWebExplorerActivity(getString(R.string.microdream_web), AboutActivity.this);
                }
            }, index, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            start = end;
        }
        return sp;
    }

    @Override
    public Intent onLastActivityFinish() {
        return new Intent(this, MainActivity.class);
    }
}
