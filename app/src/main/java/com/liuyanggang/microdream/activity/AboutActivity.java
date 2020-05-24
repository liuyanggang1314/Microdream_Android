package com.liuyanggang.microdream.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.liuyanggang.microdream.MainActivity;
import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.base.BaseActivity;
import com.liuyanggang.microdream.utils.VersionUtil;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.wangyuwei.particleview.ParticleView;

/**
 * @ClassName AboutActivity
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/24
 * @Version 1.0
 */
public class AboutActivity extends BaseActivity {
    private CommonTitleBar commonTitleBar;
    private ParticleView mParticleView;
    @BindView(R.id.version)
    TextView version;
    @BindView(R.id.check_updata)
    Button checkUpdate;
    @BindView(R.id.copyright)
    TextView copyright;
    @BindView(R.id.lottieAnimationView)
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
        initTopbar();
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
        mParticleView = findViewById(R.id.mParticleView);
        version.setText(VersionUtil.getversion(this));
        copyright.setText(VersionUtil.getCopyrightInfo(this));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            mParticleView.startAnim();
            lottieAnimationView.setSpeed(0.5f);
        }
    }

    @Override
    public Intent onLastActivityFinish() {
        return new Intent(this, MainActivity.class);
    }
}
