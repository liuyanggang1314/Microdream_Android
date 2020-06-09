package com.liuyanggang.microdream.activity.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.base.BaseActivity;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.ChatLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.chat.layout.message.MessageLayout;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;
import com.tencent.qcloud.tim.uikit.utils.TUIKitConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.liuyanggang.microdream.MicrodreamApplication.getContext;

/**
 * @ClassName Chatactivity
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/6/6
 * @Version 1.0
 */
public class ChatActivity extends BaseActivity {
    @BindView(R.id.chat_layout)
    ChatLayout mChatLayout;
    private ChatInfo mChatInfo;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mChatInfo = (ChatInfo) bundle.getSerializable("chatInfo");
        }
        initView();
        initChat();
        initTopBar();
    }

    private void initTopBar() {
        mTopBar.setTitle(mChatInfo.getChatName());
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> {
            finish();
        });
        mTopBar.addRightImageButton(R.mipmap.user_withe, R.id.topbar_right_change_button).setOnClickListener(v -> {
            if (mChatInfo.getType() == TIMConversationType.C2C) {
                Intent intent = new Intent(getContext(), FriendProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(TUIKitConstants.ProfileType.CONTENT, mChatInfo);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getContext(), GroupActivity.class);
                intent.putExtra(TUIKitConstants.Group.GROUP_ID, mChatInfo.getId());
                startActivity(intent);
            }

        });
    }

    private void initChat() {
        //单聊组件的默认UI和交互初始化
        mChatLayout.initDefault();
        /*
         * 需要聊天的基本信息
         */
        mChatLayout.setChatInfo(mChatInfo);
        TitleBarLayout titleBarLayout = mChatLayout.getTitleBar();
        titleBarLayout.setVisibility(View.GONE);
        mChatLayout.getMessageLayout().setOnItemClickListener(new MessageLayout.OnItemClickListener() {
            @Override
            public void onMessageLongClick(View view, int position, MessageInfo messageInfo) {
                //因为adapter中第一条为加载条目，位置需减1
                mChatLayout.getMessageLayout().showItemPopMenu(position - 1, messageInfo, view);
            }

            @Override
            public void onUserIconClick(View view, int position, MessageInfo messageInfo) {
                if (null == messageInfo) {
                    return;
                }
                ChatInfo info = new ChatInfo();
                info.setId(messageInfo.getFromUser());
                Intent intent = new Intent(getContext(), FriendProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(TUIKitConstants.ProfileType.CONTENT, info);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        ButterKnife.bind(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatLayout != null) {
            mChatLayout.exitChat();
        }
    }
}
