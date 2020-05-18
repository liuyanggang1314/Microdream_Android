package com.liuyanggang.microdream.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.textview)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        textView.setText("123456");
    }
}
