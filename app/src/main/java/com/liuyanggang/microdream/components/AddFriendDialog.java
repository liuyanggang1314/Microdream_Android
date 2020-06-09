package com.liuyanggang.microdream.components;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
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

/**
 * @ClassName ChangePasswordDialog
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/24
 * @Version 1.0
 */
public class AddFriendDialog extends QMUIDialog {
    private Context mContext;

    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.btn_enter)
    Button btnEnter;
    @BindView(R.id.info)
    EditText info;
    @BindView(R.id.friend_id)
    EditText friendId;
    @BindView(R.id.dialog_titile)
    TextView dialogTitile;
    @BindView(R.id.linearLayout1)
    LinearLayout linearLayout;
    private String title;

    public AddFriendDialog(Context context, String title) {
        super(context);
        this.mContext = context;
        this.title = title;
    }

    public AddFriendDialog(Context context, int styleRes) {
        super(context, styleRes);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_addfriend);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        initData();
        //初始化界面控件的事件
        initEvent();
    }

    private void initData() {

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
        btnEnter.setOnClickListener(v -> {
            if (onClickCloseListener != null) {
                KeybordUtil.hideSoftKeyBoard(getWindow());
                if (!validate()) {
                    AnimationUtil.initAnimationShake(linearLayout);
                    ToastyUtil.setNormalWarning(getContext(), "请先填写信息", Toast.LENGTH_SHORT);
                } else {
                    String id = friendId.getText().toString().trim();
                    String mAddWording = info.getText().toString().trim();
                    onClickCloseListener.onEnterClick(id, mAddWording);
                }
            }
        });
    }

    private boolean validate() {
        boolean valid = true;
        String id = friendId.getText().toString().replaceAll(" ", "");
        String mAddWording = info.getText().toString().replaceAll(" ", "");
        if (id.isEmpty()) {
            friendId.setError("请输入ID");
            valid = false;
        } else {
            friendId.setError(null);
        }
        if (mAddWording.isEmpty()) {
            info.setError("请输入验证信息");
            valid = false;
        } else {
            info.setError(null);
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
        dialogTitile.setText(title);
    }

    /**
     * 设置确定取消按钮的回调
     */
    OnClickCloseListener onClickCloseListener;

    public AddFriendDialog setOnClickCloseListener(OnClickCloseListener onClickCloseListener) {
        this.onClickCloseListener = onClickCloseListener;
        return this;
    }

    public interface OnClickCloseListener {
        /**
         * 点击取消按钮事件
         */
        void onColseClick();

        void onEnterClick(String id, String mAddWording);
    }

}
