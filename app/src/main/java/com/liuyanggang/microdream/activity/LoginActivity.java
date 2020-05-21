package com.liuyanggang.microdream.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.liuyanggang.microdream.MainActivity;
import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.components.LoginDialog;
import com.liuyanggang.microdream.presenter.LoginIPresenter;
import com.liuyanggang.microdream.utils.VersionUtils;
import com.liuyanggang.microdream.view.LoginIView;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.tapadoo.alerter.Alerter;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

import static com.liuyanggang.microdream.entity.MicrodreamEntity.PASSWORD_LESS;
import static com.liuyanggang.microdream.entity.MicrodreamEntity.PASSWORD_MORE;


/**
 * @ClassName BaseActivity
 * @Description TODO 32 48 64 96 128
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/18
 * @Version 1.0
 */
public class LoginActivity extends AppCompatActivity implements LoginIView {

    private LoginIPresenter mPresenter;
    private LoginDialog loginDialog;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.lottieAnimationView)
    LottieAnimationView lottieAnimationView;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.copyright)
    TextView copyright;
    @BindView(R.id.input_username)
    EditText inputUserName;
    @BindView(R.id.input_password)
    EditText inputPassword;

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
            if (!validate()) {
                Toasty.info(this, "请先填写信息", Toasty.LENGTH_SHORT).show();
            } else {
                loginDialog = new LoginDialog(LoginActivity.this);
                loginDialog.show();
                mPresenter.login();
            }
        });
    }


    /**
     * 初始化界面
     */
    private void initView() {
        // 沉浸式状态栏
        QMUIStatusBarHelper.translucent(this);
        ButterKnife.bind(this);
        //开启播放速度
        lottieAnimationView.setSpeed(0.5f);
        copyright.setText(VersionUtils.getCopyrightInfo(this));
    }

    private void setData() {
        this.mPresenter = new LoginIPresenter(this);
    }


    private boolean validate() {
        boolean valid = true;

        String username = inputUserName.getText().toString().replaceAll(" ", "");
        String password = inputPassword.getText().toString().replaceAll(" ", "");
        if (username.isEmpty() || inputUserName.length() < 1) {
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
        String userName = inputUserName.getText().toString().replaceAll(" ", "");
        return userName;
    }

    @Override
    public String getPassword() {
        String passWord = inputPassword.getText().toString().replaceAll(" ", "");
        return passWord;
    }


    @Override
    public void onLoginSeccess() {
        loginDialog.dismiss();
        Toasty.success(this, "登录成功", Toasty.LENGTH_LONG).show();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onLoginFails(String fails) {
        loginDialog.dismiss();
        Alerter.create(this)
                .setTitle("提示")
                .setText(fails)
                .setDuration(1000)
                .setIcon(R.mipmap.logo)
                .show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mPresenter = null;
    }
}
