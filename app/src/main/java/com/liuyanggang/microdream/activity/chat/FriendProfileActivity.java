package com.liuyanggang.microdream.activity.chat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.base.BaseActivity;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.contact.ContactItemBean;
import com.tencent.qcloud.tim.uikit.modules.contact.FriendProfileLayout;
import com.tencent.qcloud.tim.uikit.utils.TUIKitConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.liuyanggang.microdream.MicrodreamApplication.getContext;

/**
 * @ClassName FriendProfileActivity
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/6/8
 * @Version 1.0
 */
public class FriendProfileActivity extends BaseActivity {
    @BindView(R.id.friend_profile)
    FriendProfileLayout layout;
    @BindView(R.id.friend_titlebar)
    TitleBarLayout mTitleBar;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_friend_profile);
        initView();
        initTopBar();
        initFriendProfileLayout();
    }

    private void initTopBar() {
        mTopBar.setTitle("详细信息");
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> {
            finish();
        });
    }

    private void initFriendProfileLayout() {

        layout.initData(getIntent().getSerializableExtra(TUIKitConstants.ProfileType.CONTENT));
        mTitleBar.setVisibility(View.GONE);
        layout.setOnButtonClickListener(new FriendProfileLayout.OnButtonClickListener() {
            @Override
            public void onStartConversationClick(ContactItemBean info) {
                ChatInfo chatInfo = new ChatInfo();
                chatInfo.setType(TIMConversationType.C2C);
                chatInfo.setId(info.getId());
                String chatName = info.getId();
                if (!TextUtils.isEmpty(info.getRemark())) {
                    chatName = info.getRemark();
                } else if (!TextUtils.isEmpty(info.getNickname())) {
                    chatName = info.getNickname();
                }
                chatInfo.setChatName(chatName);
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("chatInfo", chatInfo);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void onDeleteFriendClick(String id) {
                finish();
            }
        });
    }

    private void initView() {
        ButterKnife.bind(this);
    }

}
