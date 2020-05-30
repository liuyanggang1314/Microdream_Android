package com.liuyanggang.microdream.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.liuyanggang.microdream.MainActivity;
import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.base.BaseActivity;
import com.liuyanggang.microdream.components.LoginDialog;
import com.liuyanggang.microdream.presenter.LoginIPresenter;
import com.liuyanggang.microdream.utils.AnimationUtil;
import com.liuyanggang.microdream.utils.KeybordUtil;
import com.liuyanggang.microdream.utils.ToastyUtil;
import com.liuyanggang.microdream.utils.VersionUtil;
import com.liuyanggang.microdream.view.LoginIView;
import com.qmuiteam.qmui.span.QMUITouchableSpan;
import com.qmuiteam.qmui.widget.textview.QMUISpanTouchFixTextView;
import com.tapadoo.alerter.Alerter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.liuyanggang.microdream.entity.MicrodreamEntity.PASSWORD_LESS;
import static com.liuyanggang.microdream.entity.MicrodreamEntity.PASSWORD_MORE;


/**
 * @ClassName BaseActivity
 * @Description TODO 登录
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/18
 * @Version 1.0
 */
public class LoginActivity extends BaseActivity implements LoginIView {

    private LoginIPresenter mPresenter;
    private LoginDialog loginDialog;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.lottieAnimationView)
    LottieAnimationView lottieAnimationView;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.input_username)
    EditText inputUserName;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.checkBox)
    CheckBox checkBox;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.registered)
    Button registered;
    @BindView(R.id.copyright)
    QMUISpanTouchFixTextView copyright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setData();
        initListener();
    }

    private void initListener() {
        btnLogin.setOnClickListener(v -> {
            KeybordUtil.closeKeybord(this);
            if (!validate()) {
                AnimationUtil.initAnimationShake(linearLayout);
                ToastyUtil.setNormalWarning(getApplicationContext(), "请先填写信息", Toast.LENGTH_SHORT);
            } else {
                loginDialog = new LoginDialog(LoginActivity.this);
                loginDialog.show();
                mPresenter.login();
            }
        });
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkBox.setTextColor(getResources().getColor(R.color.app_color_blue, null));
            } else {
                checkBox.setTextColor(getResources().getColor(R.color.pink, null));
            }
        });
        registered.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisteredActivity.class));
        });
        logo.setOnClickListener(v -> {
            AnimationUtil.initAnimationBounceInDown(logo);
        });
    }


    /**
     * 初始化界面
     */
    private void initView() {
        ButterKnife.bind(this);
        //开启播放速度
        lottieAnimationView.setSpeed(0.5f);

        copyright.setMovementMethodDefault();
        copyright.setText(generateSp(VersionUtil.getCopyrightInfo(this)));
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
                    startWebExplorerActivity(getString(R.string.microdream_web), LoginActivity.this);
                }
            }, index, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            start = end;
        }
        return sp;
    }

    private void setData() {
        this.mPresenter = new LoginIPresenter(this);
    }

    /**
     * 校验
     *
     * @return
     */
    private boolean validate() {
        boolean valid = true;

        String username = inputUserName.getText().toString().replaceAll(" ", "");
        String password = inputPassword.getText().toString().replaceAll(" ", "");
        if (username.isEmpty()) {
            inputUserName.setError("请输入正确账号");
            valid = false;
        } else {
            inputUserName.setError(null);
        }

        if (password.isEmpty() || password.length() < PASSWORD_LESS || password.length() > PASSWORD_MORE) {
            inputPassword.setError("请输入正确密码(密码6-16位)");
            valid = false;
        } else {
            inputPassword.setError(null);
        }

        return valid;
    }

    @Override
    public String getUserName() {
        return inputUserName.getText().toString().replaceAll(" ", "");
    }

    @Override
    public String getPassword() {
        return inputPassword.getText().toString().replaceAll(" ", "");
    }

    @Override
    public boolean getIsRemember() {
        return checkBox.isChecked();
    }


    @Override
    public void onLoginSeccess() {
        loginDialog.dismiss();
        ToastyUtil.setNormalSuccess(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT);
        finish();
    }

    @Override
    public void onLoginError(String error) {
        AnimationUtil.initAnimationShake(linearLayout);
        loginDialog.dismiss();
        Alerter.create(this)
                .setTitle(R.string.app_name)
                .setText(error)
                .setDuration(2000)
                .setIcon(R.mipmap.logo)
                .enableSwipeToDismiss()
                .setBackgroundResource(R.drawable.atlas_background)
                .show();
    }

    @Override
    public Intent onLastActivityFinish() {
        return new Intent(this, MainActivity.class);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mPresenter = null;
    }
}
