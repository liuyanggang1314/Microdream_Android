package com.liuyanggang.microdream.components;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.airbnb.lottie.LottieAnimationView;
import com.liuyanggang.microdream.R;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName CodeDialog
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/20
 * @Version 1.0
 */
public class RegisteredDialog extends QMUIDialog {
    private Context mContext;
    @BindView(R.id.lottieAnimationView)
    LottieAnimationView lottieAnimationView;
    @BindView(R.id.back)
    ImageButton back;

    public RegisteredDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public RegisteredDialog(Context context, int styleRes) {
        super(context, styleRes);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_registered);
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
        attributes.width = (int) (screenWidth / 1.5);
        attributes.height = (int) (screenWidth / 1.5);
        getWindow().setAttributes(attributes);
    }

    /**
     * 设置确定取消按钮的回调
     */
    OnClickCloseListener onClickCloseListener;

    public RegisteredDialog setOnClickCloseListener(OnClickCloseListener onClickCloseListener) {
        this.onClickCloseListener = onClickCloseListener;
        return this;
    }

    public interface OnClickCloseListener {
        /**
         * 点击取消按钮事件
         */
        void onColseClick();
    }

}
