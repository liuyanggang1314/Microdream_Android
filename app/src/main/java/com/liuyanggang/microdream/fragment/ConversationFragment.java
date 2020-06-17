package com.liuyanggang.microdream.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.activity.chat.ChatActivity;
import com.liuyanggang.microdream.activity.chat.StartC2CChatActivity;
import com.liuyanggang.microdream.activity.chat.StartGroupChatActivity;
import com.liuyanggang.microdream.base.BaseFragment;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMFriendAllowType;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMGroupManager;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo;
import com.tencent.qcloud.tim.uikit.utils.TUIKitConstants;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @ClassName ImageFragment
 * @Description TODO 图片主页
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/24
 * @Version 1.0
 */
public class ConversationFragment extends BaseFragment {
    private Unbinder unbinder;
    @BindView(R.id.conversation_layout)
    ConversationLayout mConversationLayout;
    @BindView(R.id.conversation_title)
    TitleBarLayout titleBarLayout;
    @BindView(R.id.action_bar_right_bmb)
    BoomMenuButton mBoomMenuButton;
    @BindView(R.id.qmuiPullRefreshLayout)
    QMUIPullRefreshLayout qmuiPullRefreshLayout;

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_conversation, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initChat();
        initBoomMenu();
        initListener();
        return view;
    }

    private void initListener() {
        qmuiPullRefreshLayout.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                initView();
            }
        });
    }

    /**
     * 初始化boom
     */
    private void initBoomMenu() {
        mBoomMenuButton.setShowDuration(450);
        mBoomMenuButton.setHideDuration(375);
        mBoomMenuButton.addBuilder(new HamButton.Builder()
                .normalImageRes(R.drawable.message)
                .normalTextRes(R.string.start_conversation)
                .subNormalTextRes(R.string.start_conversation)
                .imagePadding(new Rect(30, 30, 30, 30)));
        mBoomMenuButton.addBuilder(new HamButton.Builder()
                .normalImageRes(R.drawable.team)
                .normalTextRes(R.string.create_private_group)
                .subNormalTextRes(R.string.create_private_group)
                .imagePadding(new Rect(30, 30, 30, 30)));
        mBoomMenuButton.addBuilder(new HamButton.Builder()
                .normalImageRes(R.drawable.team)
                .normalTextRes(R.string.create_group_chat)
                .subNormalTextRes(R.string.create_group_chat)
                .imagePadding(new Rect(30, 30, 30, 30)));
        mBoomMenuButton.addBuilder(new HamButton.Builder()
                .normalImageRes(R.drawable.team)
                .normalTextRes(R.string.create_chat_room)
                .subNormalTextRes(R.string.create_chat_room)
                .imagePadding(new Rect(30, 30, 30, 30)));
        mBoomMenuButton.setOnBoomListener(new OnBoomListener() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {
                TextView textView = boomButton.getTextView();
                String subtext = textView.getText().toString();
                if (subtext.equals(getString(R.string.start_conversation))) {
                    Intent intent = new Intent(getContext(), StartC2CChatActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
                if (subtext.equals(getString(R.string.create_private_group))) {
                    Intent intent = new Intent(getContext(), StartGroupChatActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    intent.putExtra(TUIKitConstants.GroupType.TYPE, TUIKitConstants.GroupType.PRIVATE);
                    startActivity(intent);
                }
                if (subtext.equals(getString(R.string.create_group_chat))) {
                    Intent intent = new Intent(getContext(), StartGroupChatActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    intent.putExtra(TUIKitConstants.GroupType.TYPE, TUIKitConstants.GroupType.PUBLIC);
                    startActivity(intent);
                }
                if (subtext.equals(getString(R.string.create_chat_room))) {
                    Intent intent = new Intent(getContext(), StartGroupChatActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    intent.putExtra(TUIKitConstants.GroupType.TYPE, TUIKitConstants.GroupType.CHAT_ROOM);
                    startActivity(intent);
                }
            }

            @Override
            public void onBackgroundClick() {

            }

            @Override
            public void onBoomWillHide() {

            }

            @Override
            public void onBoomDidHide() {

            }

            @Override
            public void onBoomWillShow() {

            }

            @Override
            public void onBoomDidShow() {

            }
        });
    }

    /**
     * 初始化个人聊天设置
     */
    private void initChat() {
        HashMap<String, Object> hashMap = new HashMap<>();
        // 加我验证方式
        hashMap.put(TIMUserProfile.TIM_PROFILE_TYPE_KEY_ALLOWTYPE, TIMFriendAllowType.TIM_FRIEND_NEED_CONFIRM);
        TIMFriendshipManager.getInstance().modifySelfProfile(hashMap, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess() {

            }
        });
        TIMGroupManager.getInstance().applyJoinGroup("@TGS#2R4GLPQGB", "统一加入", new TIMCallBack() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess() {

            }
        });
    }

    /**
     * 初始化界面
     */
    private void initView() {
        mConversationLayout.initDefault();
        titleBarLayout.setVisibility(View.GONE);
        mConversationLayout.getConversationList().setOnItemClickListener((view, position, conversationInfo) -> {
            //此处为demo的实现逻辑，更根据会话类型跳转到相关界面，开发者可根据自己的应用场景灵活实现
            startChatActivity(conversationInfo);
        });
        mConversationLayout.getConversationList().setOnItemLongClickListener((view, position, conversationInfo) -> {
            final String[] items = new String[2];
            if (conversationInfo.isTop()) {
                items[0] = "取消顶置";
                items[1] = "删除聊天";
            } else {
                items[0] = "顶置聊天";
                items[1] = "删除聊天";
            }
            new QMUIDialog.CheckableDialogBuilder(getContext())
                    .addItems(items, (dialog, which) -> {
                        if (which == 0) {
                            mConversationLayout.setConversationTop(position, (ConversationInfo) conversationInfo);
                        } else {
                            mConversationLayout.deleteConversation(position, (ConversationInfo) conversationInfo);
                        }
                        dialog.dismiss();
                    })
                    .create(R.style.MyDialogPink).show();
        });
        qmuiPullRefreshLayout.finishRefresh();
    }

    private void startChatActivity(ConversationInfo conversationInfo) {
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setType(conversationInfo.isGroup() ? TIMConversationType.Group : TIMConversationType.C2C);
        chatInfo.setId(conversationInfo.getId());
        chatInfo.setChatName(conversationInfo.getTitle());
        Intent intent = new Intent(getContext(), ChatActivity.class);
        intent.putExtra("chatInfo", chatInfo);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
