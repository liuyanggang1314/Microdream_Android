package com.liuyanggang.microdream.activity.chat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.base.BaseActivity;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.contact.ContactListView;

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
public class GroupListActivity extends BaseActivity {

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
        mTopBar.setTitle("群组");
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
            ChatInfo chatInfo = new ChatInfo();
            chatInfo.setType(TIMConversationType.Group);
            String chatName = contact.getId();
            if (!TextUtils.isEmpty(contact.getRemark())) {
                chatName = contact.getRemark();
            } else if (!TextUtils.isEmpty(contact.getNickname())) {
                chatName = contact.getNickname();
            }
            chatInfo.setChatName(chatName);
            chatInfo.setId(contact.getId());
            Intent intent = new Intent(getContext(), ChatActivity.class);
            intent.putExtra("chatInfo", chatInfo);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }

    public void loadDataSource() {
        mListView.loadDataSource(ContactListView.DataSource.GROUP_LIST);
    }
}
