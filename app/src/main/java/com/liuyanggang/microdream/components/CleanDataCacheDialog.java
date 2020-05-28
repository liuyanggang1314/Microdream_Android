package com.liuyanggang.microdream.components;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.utils.DataCleanManagerUtil;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import java.io.File;
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
public class CleanDataCacheDialog extends QMUIDialog {
    private Context mContext;

    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.cache_info)
    TextView cacheInfo;
    @BindView(R.id.btn_enter)
    Button btnEnter;

    public CleanDataCacheDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public CleanDataCacheDialog(Context context, int styleRes) {
        super(context, styleRes);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_clean_data_cache);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        initData();
        //初始化界面控件的事件
        initEvent();
    }

    private void initData() {
        File file = new File(getContext().getCacheDir().getPath());
        String cache = "0";
        try {
            cache = DataCleanManagerUtil.getCacheSize(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("缓存共有：");
        stringBuffer.append(cache);
        cacheInfo.setText(stringBuffer);
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
                DataCleanManagerUtil.cleanInternalCache(getContext());
                DataCleanManagerUtil.cleanExternalCache(getContext());
                onClickCloseListener.onEnterClick();
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
        attributes.width = screenWidth - 100;
        attributes.height = screenWidth - 200;
        getWindow().setAttributes(attributes);
    }

    /**
     * 设置确定取消按钮的回调
     */
    OnClickCloseListener onClickCloseListener;

    public CleanDataCacheDialog setOnClickCloseListener(OnClickCloseListener onClickCloseListener) {
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
