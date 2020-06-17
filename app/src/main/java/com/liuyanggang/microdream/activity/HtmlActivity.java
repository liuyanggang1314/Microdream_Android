package com.liuyanggang.microdream.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.base.BaseActivity;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.zzhoujay.richtext.RichText;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName HtmlActivity
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/31
 * @Version 1.0
 */
public class HtmlActivity extends BaseActivity {
    @BindView(R.id.html)
    TextView html;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    private static String HTMLINFO="html";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);
        Intent getIntent = getIntent();
        String content = getIntent.getStringExtra("content");
        initView(content);
        initTopBar();
    }

    private void initView(String text) {
        ButterKnife.bind(this);
        RichText.initCacheDir(this);
        RichText.from(text)
                .bind(HTMLINFO)
                .into(html);
    }

    private void initTopBar() {
        mTopBar.setTitle(getString(R.string.app_name));
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RichText.clear(HTMLINFO);
    }
}
