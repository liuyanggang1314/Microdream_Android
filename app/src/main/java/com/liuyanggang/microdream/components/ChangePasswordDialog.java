package com.liuyanggang.microdream.components;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.utils.AnimationUtil;
import com.liuyanggang.microdream.utils.KeybordUtil;
import com.liuyanggang.microdream.utils.ToastyUtil;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.liuyanggang.microdream.entity.MicrodreamEntity.PASSWORD_LESS;
import static com.liuyanggang.microdream.entity.MicrodreamEntity.PASSWORD_MORE;

/**
 * @ClassName ChangePasswordDialog
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/24
 * @Version 1.0
 */
public class ChangePasswordDialog extends QMUIDialog {
    private Context mContext;

    @BindView(R.id.back)
    ImageButton back;

    @BindView(R.id.old_password)
    EditText oldPassword;
    @BindView(R.id.new_password)
    EditText newPassword;
    @BindView(R.id.new_enter_password)
    EditText newEnterPassword;
    @BindView(R.id.btn_change)
    Button btnChangePassword;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;

    public ChangePasswordDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public ChangePasswordDialog(Context context, int styleRes) {
        super(context, styleRes);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_change_password);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化界面控件的事件
        initEvent();
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        back.setOnClickListener(v -> {
            if (onClickCloseListener != null) {
                onClickCloseListener.onColseClick();
            }
        });
        btnChangePassword.setOnClickListener(v -> {
            if (onClickCloseListener != null) {
                KeybordUtil.hideSoftKeyBoard(getWindow());
                if (!validate()) {
                    AnimationUtil.initAnimationShake(linearLayout);
                    ToastyUtil.setNormalWarning(getContext(), "请先填写密码", Toast.LENGTH_SHORT);
                } else {
                    String oldPass = oldPassword.getText().toString().replaceAll(" ", "");
                    String newPass = newPassword.getText().toString().replaceAll(" ", "");
                    onClickCloseListener.getPasswordInfo(oldPass, newPass);
                }
            }
        });
    }

    private boolean validate() {
        boolean valid = true;
        String oldPass = oldPassword.getText().toString().replaceAll(" ", "");
        String newPass = newPassword.getText().toString().replaceAll(" ", "");
        String newEnterPass = newEnterPassword.getText().toString().replaceAll(" ", "");
        if (oldPass.isEmpty() || oldPass.length() < PASSWORD_LESS || oldPass.length() > PASSWORD_MORE) {
            oldPassword.setError("请输入正确密码(密码6-16位)");
            valid = false;
        } else {
            oldPassword.setError(null);
        }
        if (newPass.isEmpty() || newPass.length() < PASSWORD_LESS || newPass.length() > PASSWORD_MORE) {
            newPassword.setError("请输入正确密码(密码6-16位)");
            valid = false;
        } else {
            newPassword.setError(null);
        }
        if (newEnterPass.isEmpty() || !newPass.equals(newEnterPass)) {
            newEnterPassword.setError("新密码不一致");
            valid = false;
        } else {
            newEnterPassword.setError(null);
        }
        return valid;
    }

    /**
     * 初始化界面
     */
    private void initView() {
        ButterKnife.bind(this);
        // 设置窗口大小
        int screenWidth = QMUIDisplayHelper.getScreenWidth(mContext);
        int screenHeight = QMUIDisplayHelper.getScreenHeight(mContext);
        WindowManager.LayoutParams attributes = Objects.requireNonNull(getWindow()).getAttributes();
        // 设置窗口背景透明度
        attributes.width = screenWidth - 100;
        attributes.height = screenWidth - 200;
        getWindow().setAttributes(attributes);
    }

    /**
     * 设置确定取消按钮的回调
     */
    OnClickCloseListener onClickCloseListener;

    public ChangePasswordDialog setOnClickCloseListener(OnClickCloseListener onClickCloseListener) {
        this.onClickCloseListener = onClickCloseListener;
        return this;
    }

    public interface OnClickCloseListener {
        /**
         * 点击取消按钮事件
         */
        void onColseClick();

        void getPasswordInfo(String oldPass, String newPass);
    }

}
