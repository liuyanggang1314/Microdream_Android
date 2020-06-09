package com.liuyanggang.microdream.activity.chat;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.base.BaseActivity;
import com.liuyanggang.microdream.fragment.chat.GroupFragment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName GroupActivity
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/6/9
 * @Version 1.0
 */
public class GroupActivity extends BaseActivity {
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
        initView();
        initTopBar();
        initGroupInfo();
    }

    private void initGroupInfo() {
        GroupFragment fragment = new GroupFragment();
        fragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().add(R.id.fragmeLayout, fragment).commit();
    }


    private void initView() {
        ButterKnife.bind(this);
    }

    private void initTopBar() {
        mTopBar.setTitle("群聊详情");
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> {
            finish();
        });
    }
}
