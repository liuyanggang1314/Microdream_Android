package com.liuyanggang.microdream.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.ZoomButtonsController;

import androidx.annotation.Nullable;

import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.base.BaseActivity;
import com.liuyanggang.microdream.components.MicodreamWebView;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUILangHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.qmuiteam.qmui.widget.webview.QMUIWebView;
import com.qmuiteam.qmui.widget.webview.QMUIWebViewClient;
import com.qmuiteam.qmui.widget.webview.QMUIWebViewContainer;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName WebExplorerFragment
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/29
 * @Version 1.0
 */
public class MicrodreamWebExplorerActivity extends BaseActivity {
    public static final String EXTRA_URL = "EXTRA_URL";
    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_NEED_DECODE = "EXTRA_NEED_DECODE";

    private final static int PROGRESS_PROCESS = 0;
    private final static int PROGRESS_GONE = 1;
    private QMUIPopup mNormalPopup;


    @BindView(R.id.topbar)
    protected QMUITopBarLayout mTopBarLayout;
    @BindView(R.id.webview_container)
    QMUIWebViewContainer mWebViewContainer;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    protected MicodreamWebView mWebView;


    private String mUrl;
    private String mTitle;
    private ProgressHandler mProgressHandler;
    private boolean mIsPageFinished = false;
    private boolean mNeedDecodeUrl = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String url = bundle.getString(EXTRA_URL);
            mTitle = bundle.getString(EXTRA_TITLE);
            mNeedDecodeUrl = bundle.getBoolean(EXTRA_NEED_DECODE, false);
            if (url != null && url.length() > 0) {
                handleUrl(url);
            }
        }

        mProgressHandler = new ProgressHandler();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_webview_explorer);
        ButterKnife.bind(this);
        initTopbar();
        initWebView();
    }


    protected void initTopbar() {
        mTopBarLayout.addLeftBackImageButton().setOnClickListener(v -> finish());

        mTopBarLayout.addRightImageButton(R.mipmap.ellipsis,R.id.topbar_right_change_button).setOnClickListener(v -> {
            initRightButton();
        });

        updateTitle(mTitle);
    }

    private void initRightButton() {
        String[] listItems = new String[]{
                "刷新",
                "浏览器打开",
                "复制链接地址"
        };
        List<String> data = new ArrayList<>();
        Collections.addAll(data, listItems);
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.layout_simple_list_item, data);
        AdapterView.OnItemClickListener onItemClickListener = (adapterView, view, i, l) -> {
            switch (i) {
                case 0:
                    mWebView.reload();
                    break;
                case 1:
                    Uri uri = Uri.parse(mUrl);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    break;
                case 2:
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", mUrl);
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    break;
            }
            if (mNormalPopup != null) {
                mNormalPopup.dismiss();
            }
        };
        mNormalPopup = QMUIPopups.listPopup(this,
                QMUIDisplayHelper.dp2px(this, 180),
                QMUIDisplayHelper.dp2px(this, 300),
                adapter,
                onItemClickListener)
                .animStyle(QMUIPopup.ANIM_AUTO)
                .preferredDirection(QMUIPopup.DIRECTION_TOP)
                .shadow(true)
                .offsetYIfTop(QMUIDisplayHelper.dp2px(this, 5))
                .onDismiss(() -> {

                })
                .show(findViewById(R.id.topbar_right_change_button));
    }

    private void updateTitle(String title) {
        if (title != null && !title.equals("")) {
            mTitle = title;
            mTopBarLayout.setTitle(mTitle);
        }
    }

    protected boolean needDispatchSafeAreaInset() {
        return false;
    }

    protected void initWebView() {
        mWebView = new MicodreamWebView(this);
        boolean needDispatchSafeAreaInset = needDispatchSafeAreaInset();
        mWebViewContainer.addWebView(mWebView, needDispatchSafeAreaInset);
        mWebViewContainer.setCustomOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> onScrollWebContent(scrollX, scrollY, oldScrollX, oldScrollY));
        FrameLayout.LayoutParams containerLp = (FrameLayout.LayoutParams) mWebViewContainer.getLayoutParams();
        mWebViewContainer.setFitsSystemWindows(!needDispatchSafeAreaInset);
        containerLp.topMargin = needDispatchSafeAreaInset ? 0 : QMUIResHelper.getAttrDimen(this, R.attr.qmui_topbar_height);
        mWebViewContainer.setLayoutParams(containerLp);

        mWebView.setWebChromeClient(getWebViewChromeClient());
        mWebView.setWebViewClient(getWebViewClient());
        mWebView.requestFocus(View.FOCUS_DOWN);
        setZoomControlGone(mWebView);
        configWebView(mWebViewContainer, mWebView);
        mWebView.loadUrl(mUrl);
    }

    protected void configWebView(QMUIWebViewContainer webViewContainer, QMUIWebView webView) {

    }

    protected void onScrollWebContent(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

    }

    private void handleUrl(String url) {
        if (mNeedDecodeUrl) {
            String decodeURL;
            try {
                decodeURL = URLDecoder.decode(url, "utf-8");
            } catch (UnsupportedEncodingException ignored) {
                decodeURL = url;
            }
            mUrl = decodeURL;
        } else {
            mUrl = url;
        }
    }

    protected WebChromeClient getWebViewChromeClient() {
        return new ExplorerWebViewChromeClient(this);
    }

    protected QMUIWebViewClient getWebViewClient() {
        return new ExplorerWebViewClient(needDispatchSafeAreaInset());
    }

    private void sendProgressMessage(int progressType, int newProgress, int duration) {
        Message msg = new Message();
        msg.what = progressType;
        msg.arg1 = newProgress;
        msg.arg2 = duration;
        mProgressHandler.sendMessage(msg);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            // 返回前一个页面
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebViewContainer.destroy();
        mWebView = null;
    }

    public static void setZoomControlGone(WebView webView) {
        webView.getSettings().setDisplayZoomControls(false);
        @SuppressWarnings("rawtypes")
        Class classType;
        Field field;
        try {
            classType = WebView.class;
            field = classType.getDeclaredField("mZoomButtonsController");
            field.setAccessible(true);
            ZoomButtonsController zoomButtonsController = new ZoomButtonsController(
                    webView);
            zoomButtonsController.getZoomControls().setVisibility(View.GONE);
            try {
                field.set(webView, zoomButtonsController);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static class ExplorerWebViewChromeClient extends WebChromeClient {
        private MicrodreamWebExplorerActivity mFragment;

        public ExplorerWebViewChromeClient(MicrodreamWebExplorerActivity fragment) {
            mFragment = fragment;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            // 修改进度条
            if (newProgress > mFragment.mProgressHandler.mDstProgressIndex) {
                mFragment.sendProgressMessage(PROGRESS_PROCESS, newProgress, 100);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            mFragment.updateTitle(view.getTitle());
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            callback.onCustomViewHidden();
        }

        @Override
        public void onHideCustomView() {

        }
    }

    protected class ExplorerWebViewClient extends QMUIWebViewClient {

        public ExplorerWebViewClient(boolean needDispatchSafeAreaInset) {
            super(needDispatchSafeAreaInset, true);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (QMUILangHelper.isNullOrEmpty(mTitle)) {
                updateTitle(view.getTitle());
            }
            if (mProgressHandler.mDstProgressIndex == 0) {
                sendProgressMessage(PROGRESS_PROCESS, 30, 500);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            sendProgressMessage(PROGRESS_GONE, 100, 0);
            if (QMUILangHelper.isNullOrEmpty(mTitle)) {
                updateTitle(view.getTitle());
            }
        }
    }

    private class ProgressHandler extends Handler {

        private int mDstProgressIndex;
        private int mDuration;
        private ObjectAnimator mAnimator;


        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PROGRESS_PROCESS:
                    mIsPageFinished = false;
                    mDstProgressIndex = msg.arg1;
                    mDuration = msg.arg2;
                    mProgressBar.setVisibility(View.VISIBLE);
                    if (mAnimator != null && mAnimator.isRunning()) {
                        mAnimator.cancel();
                    }
                    mAnimator = ObjectAnimator.ofInt(mProgressBar, "progress", mDstProgressIndex);
                    mAnimator.setDuration(mDuration);
                    mAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (mProgressBar.getProgress() == 100) {
                                sendEmptyMessageDelayed(PROGRESS_GONE, 500);
                            }
                        }
                    });
                    mAnimator.start();
                    break;
                case PROGRESS_GONE:
                    mDstProgressIndex = 0;
                    mDuration = 0;
                    mProgressBar.setProgress(0);
                    mProgressBar.setVisibility(View.GONE);
                    if (mAnimator != null && mAnimator.isRunning()) {
                        mAnimator.cancel();
                    }
                    mAnimator = ObjectAnimator.ofInt(mProgressBar, "progress", 0);
                    mAnimator.setDuration(0);
                    mAnimator.removeAllListeners();
                    mIsPageFinished = true;
                    break;
                default:
                    break;
            }
        }
    }
}
