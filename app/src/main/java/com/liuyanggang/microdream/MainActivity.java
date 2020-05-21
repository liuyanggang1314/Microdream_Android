package com.liuyanggang.microdream;

import android.os.Bundle;
import android.view.View;

import com.liuyanggang.microdream.base.BaseActivity;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private long exitTime = 0;// 用来计算返回键的点击间隔时间
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initTopBar();
    }


    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTopBar.setTitle("主页");
    }

}
