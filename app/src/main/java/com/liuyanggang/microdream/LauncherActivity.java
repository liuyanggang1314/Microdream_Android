package com.liuyanggang.microdream;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.liuyanggang.microdream.activity.LoginActivity;
import com.liuyanggang.microdream.base.BaseActivity;
import com.liuyanggang.microdream.utils.VersionUtils;
import com.qmuiteam.qmui.arch.QMUILatestVisit;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.liuyanggang.microdream.entity.MicrodreamEntity.RC_PERM;

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

    private void initInfo() {
        String copyrightInfo = VersionUtils.getCopyrightInfo(this);
        copyright.setText(copyrightInfo);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_PERM)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            //Toasty.info(this,"已经授权",Toasty.LENGTH_LONG).show();
            new Handler().postDelayed(this::doAfterPermissionsGranted, 2000);
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.permissions),
                    RC_PERM, perms);
        }
    }

    private void doAfterPermissionsGranted() {
        Intent intent = QMUILatestVisit.intentOfLatestVisit(this);
        if (intent == null) {
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }


    @Override
    public void finish() {
        super.finish();
        //overridePendingTransition(R.anim.fragment_open_enter,R.anim.fragment_open_exit);
    }
}
