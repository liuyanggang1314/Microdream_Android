package com.liuyanggang.microdream.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.base.BaseActivity;
import com.liuyanggang.microdream.components.RegisteredDialog;
import com.liuyanggang.microdream.entity.UserEntity;
import com.liuyanggang.microdream.presenter.RegisteredIPresenter;
import com.liuyanggang.microdream.utils.AnimationUtil;
import com.liuyanggang.microdream.utils.KeybordUtil;
import com.liuyanggang.microdream.utils.ToastyUtil;
import com.liuyanggang.microdream.view.RegisterView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.tapadoo.alerter.Alerter;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hutool.core.util.ReUtil;
import es.dmoral.toasty.Toasty;

import static com.liuyanggang.microdream.entity.MicrodreamEntity.REGEX_EMAIL;
import static com.liuyanggang.microdream.entity.MicrodreamEntity.REGEX_MOBILE;

/**
 * @ClassName RegisteredActivity
 * @Description TODO 注册
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/22
 * @Version 1.0
 */
public class RegisteredActivity extends BaseActivity implements RegisterView {
    private RegisteredIPresenter mPresenter;
    private RegisteredDialog registeredDialog;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.edit_username)
    EditText editUsername;
    @BindView(R.id.edit_nikename)
    EditText editNikename;
    @BindView(R.id.edit_tel)
    EditText editTel;
    @BindView(R.id.edit_email)
    EditText editEmail;
    @BindView(R.id.btn_registered)
    Button btnRegistreed;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.edit_sex)
    Button editSex;
    private QMUITipDialog tipDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_);
        initView();
        setData();
        initTopBar();
        initListener();
    }


    private void setData() {
        this.mPresenter = new RegisteredIPresenter(this);
    }

    private void initView() {
        ButterKnife.bind(this);
    }

    private void initListener() {
        btnRegistreed.setOnClickListener(v -> {
            KeybordUtil.closeKeybord(this);
            if (!validate()) {
                AnimationUtil.initAnimationShake(linearLayout);
                ToastyUtil.setNormalWarning(getApplicationContext(), "请先填写信息", Toasty.LENGTH_SHORT);
            } else {
                tipDialog = new QMUITipDialog.Builder(this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                        .setTipWord("正在注册")
                        .create();
                tipDialog.show();
                mPresenter.register();
            }
        });
        editSex.setOnClickListener(v -> {
            final String[] items = new String[]{"男", "女"};
            final int checkedIndex = 0;
            new QMUIDialog.CheckableDialogBuilder(this)
                    .setCheckedIndex(checkedIndex)
                    .addItems(items, (dialog, which) -> {
                        editSex.setText(items[which]);
                        dialog.dismiss();
                    })
                    .create(R.style.MyDialogPink).show();
        });
    }

    private void initTopBar() {
        mTopBar.setTitle("注册");
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> {
            finish();
        });
    }

    /**
     * 校验
     *
     * @return
     */
    private boolean validate() {
        boolean valid = true;
        String usename = editUsername.getText().toString().replaceAll(" ", "");
        String nikeName = editNikename.getText().toString().replaceAll(" ", "");
        String email = editEmail.getText().toString().replaceAll(" ", "");
        String phone = editTel.getText().toString().replaceAll(" ", "");
        String sex = editSex.getText().toString().replaceAll(" ", "");
        if (usename.isEmpty()) {
            editUsername.setError("账号不能为空");
            valid = false;
        } else {
            editUsername.setError(null);
        }

        if (nikeName.isEmpty()) {
            editNikename.setError("昵称不能为空");
            valid = false;
        } else {
            editNikename.setError(null);
        }

        if (email.isEmpty() || !ReUtil.isMatch(REGEX_EMAIL, email)) {
            editEmail.setError("请输入正确邮箱地址");
            valid = false;
        } else {
            editEmail.setError(null);
        }
        if (phone.isEmpty() || !ReUtil.isMatch(REGEX_MOBILE, phone)) {
            editTel.setError("请输入正确手机号码");
            valid = false;
        } else {
            editTel.setError(null);
        }
        if (sex.isEmpty()) {
            editSex.setError("请选择性别");
            valid = false;
        } else {
            editSex.setError(null);
        }

        return valid;
    }


    @Override
    public UserEntity getRegisterInfo() {
        UserEntity userEntity = new UserEntity();
        String usename = editUsername.getText().toString().replaceAll(" ", "");
        String nikeName = editNikename.getText().toString().replaceAll(" ", "");
        String email = editEmail.getText().toString().replaceAll(" ", "");
        String phone = editTel.getText().toString().replaceAll(" ", "");
        String sex = editSex.getText().toString().replaceAll(" ", "");
        userEntity.setUsername(usename);
        userEntity.setNickName(nikeName);
        userEntity.setEmail(email);
        userEntity.setPhone(phone);
        userEntity.setEnabled(true);
        userEntity.setGender(sex);
        return userEntity;
    }

    @Override
    public void onRegisterSeccess() {
        tipDialog.dismiss();
        registeredDialog = new RegisteredDialog(RegisteredActivity.this);
        registeredDialog.show();
        registeredDialog.setOnClickCloseListener(() -> {
            registeredDialog.dismiss();
            finish();
        });
    }

    @Override
    public void onRegisterError(String error) {
        tipDialog.dismiss();
        AnimationUtil.initAnimationShake(linearLayout);
        Alerter.create(this)
                .setTitle(R.string.app_name)
                .setText(error)
                .setDuration(1000)
                .setIcon(R.mipmap.logo)
                .enableInfiniteDuration(true)
                .enableSwipeToDismiss()
                .setBackgroundResource(R.drawable.atlas_background)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mPresenter = null;
    }

}
