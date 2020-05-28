package com.liuyanggang.microdream.components;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.liuyanggang.microdream.R;
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
public class ImgPreviewDialog extends QMUIDialog {
    private Context mContext;

    @BindView(R.id.photoView)
    PhotoView photoView;

    public ImgPreviewDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public ImgPreviewDialog(Context context, int styleRes) {
        super(context, styleRes);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_imgpreview);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        initData();
        //初始化界面控件的事件
        initEvent();
    }

    private void initData() {
        Glide.with(getContext()).load("http://sjbz.fd.zol-img.com.cn/t_s1080x1920c/g2/M00/08/0A/ChMlWV7HoMiIB-VPAEDRuaaZmMYAAPXmgEEWWIAQNHR773.jpg")
                .placeholder(R.drawable.image_fill)
                .error(R.drawable.logo)
                .into(photoView);
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
       /* back.setOnClickListener(v -> {
            if (onClickCloseListener != null) {
                onClickCloseListener.onColseClick();
            }
        });
        btnEnter.setOnClickListener(v -> {
            if (onClickCloseListener != null) {
                onClickCloseListener.onEnterClick();
            }
        });*/
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
        attributes.width = screenWidth;
        attributes.height = screenHeight;
        getWindow().setAttributes(attributes);
        View decorView =getWindow().getDecorView();

        int uiFullOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiFullOptions);
    }

    /**
     * 设置确定取消按钮的回调
     */
    OnClickCloseListener onClickCloseListener;

    public ImgPreviewDialog setOnClickCloseListener(OnClickCloseListener onClickCloseListener) {
        this.onClickCloseListener = onClickCloseListener;
        return this;
    }

    public interface OnClickCloseListener {
        /**
         * 点击取消按钮事件
         */
        void onColseClick();

        void onEnterClick();
    }

}
