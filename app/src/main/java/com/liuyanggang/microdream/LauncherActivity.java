package com.liuyanggang.microdream;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.liuyanggang.microdream.activity.LoginActivity;
import com.liuyanggang.microdream.base.BaseActivity;
import com.liuyanggang.microdream.utils.AnimationUtil;
import com.liuyanggang.microdream.utils.MMKVUtil;
import com.liuyanggang.microdream.utils.VersionUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

import static com.liuyanggang.microdream.entity.MicrodreamEntity.RC_PERM;
import static com.liuyanggang.microdream.entity.MicrodreamEntity.TOKEN_STRING;

/**
 * @ClassName LauncherActivity
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/19
 * @Version 1.0
 */
public class LauncherActivity extends BaseActivity {

    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.app_name)
    TextView appNamme;
    @BindView(R.id.copyright)
    TextView copyright;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);
        initInfo();
        methodRequiresTwoPermission();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            AnimationUtil.initAnimationBounceInDown(linearLayout);
        }
    }

    private void initInfo() {
        String copyrightInfo = VersionUtil.getCopyrightInfo(getApplicationContext());
        copyright.setText(copyrightInfo);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, getApplicationContext());
        methodRequiresTwoPermission();
    }


    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            new Handler().postDelayed(this::doAfterPermissionsGranted, 2500);
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.permissions),
                    RC_PERM, perms);
        }
    }

    /**
     * 跳转
     */
    private void doAfterPermissionsGranted() {
        finish();
    }

    @Override
    public Intent onLastActivityFinish() {
        if (MMKVUtil.getBooleanInfo("isRememberMe")) {
            return new Intent(this, MainActivity.class);
        } else {
            MMKVUtil.setStringInfo(TOKEN_STRING, null);
            return new Intent(this, LoginActivity.class);
        }
    }

}
