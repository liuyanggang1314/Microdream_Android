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
import com.liuyanggang.microdream.utils.GenerateUserSig;
import com.liuyanggang.microdream.utils.MMKVUtil;
import com.liuyanggang.microdream.utils.VersionUtil;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

import static com.liuyanggang.microdream.MicrodreamApplication.getContext;
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
        overridePendingTransition(R.anim.fragment_open_enter, 0);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);
        initInfo();
        methodRequiresTwoPermission();
    }

    private void initInfo() {
        String copyrightInfo = VersionUtil.getCopyrightInfo(getApplicationContext());
        copyright.setText(copyrightInfo);
        AnimationUtil.initAnimationBounceInDown(linearLayout);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, getApplicationContext());
        methodRequiresTwoPermission();
    }


    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA};
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
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.fragment_open_exit);
    }

    @Override
    public Intent onLastActivityFinish() {
        if (MMKVUtil.getBooleanInfo("isRememberMe")) {
            return new Intent(getContext(), MainActivity.class);
        } else {
            MMKVUtil.setStringInfo(TOKEN_STRING, null);
            return new Intent(getContext(), LoginActivity.class);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MMKVUtil.getStringInfo(TOKEN_STRING) != null) {
            String userSig = GenerateUserSig.genTestUserSig(MMKVUtil.getStringInfo("username"));
            TUIKit.login(MMKVUtil.getStringInfo("username"), userSig, new IUIKitCallBack() {
                @Override
                public void onError(String module, final int code, final String desc) {

                }

                @Override
                public void onSuccess(Object data) {

                }
            });
        }
    }
}
