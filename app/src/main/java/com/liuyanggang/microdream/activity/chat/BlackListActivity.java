package com.liuyanggang.microdream.activity.chat;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.base.BaseActivity;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.tencent.qcloud.tim.uikit.modules.contact.ContactListView;
import com.tencent.qcloud.tim.uikit.utils.TUIKitConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.liuyanggang.microdream.MicrodreamApplication.getContext;

/**
 * @ClassName GroupListActivity
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/6/8
 * @Version 1.0
 */
public class BlackListActivity extends BaseActivity {

    @BindView(R.id.group_list)
    ContactListView mListView;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);
        initView();
        initTopBar();
        init();
    }

    private void initTopBar() {
        mTopBar.setTitle(getString(R.string.blacklist));
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> {
            finish();
        });
    }

    private void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDataSource();
    }

    private void init() {
        mListView.setOnItemClickListener((position, contact) -> {
            Intent intent = new Intent(getContext(), FriendProfileActivity.class);
            intent.putExtra(TUIKitConstants.ProfileType.CONTENT, contact);
            startActivity(intent);
        });
    }

    public void loadDataSource() {
        mListView.loadDataSource(ContactListView.DataSource.BLACK_LIST);
    }
}
