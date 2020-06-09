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
import com.tencent.qcloud.tim.uikit.modules.contact.ContactItemBean;
import com.tencent.qcloud.tim.uikit.modules.contact.ContactListView;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.liuyanggang.microdream.MicrodreamApplication.getContext;

/**
 * @ClassName StartC2CChatActivity
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/6/9
 * @Version 1.0
 */
public class StartC2CChatActivity extends BaseActivity {

    @BindView(R.id.contact_list_view)
    ContactListView mContactListView;
    private ContactItemBean mSelectedItem;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_start_c2c_chat);
        initView();
        initTopBar();
        initContactListView();
    }

    private void initContactListView() {
        mContactListView.setSingleSelectMode(true);
        mContactListView.loadDataSource(ContactListView.DataSource.FRIEND_LIST);
        mContactListView.setOnSelectChangeListener(new ContactListView.OnSelectChangedListener() {
            @Override
            public void onSelectChanged(ContactItemBean contact, boolean selected) {
                if (selected) {
                    if (mSelectedItem == contact) {
                        // 相同的Item，忽略
                    } else {
                        if (mSelectedItem != null) {
                            mSelectedItem.setSelected(false);
                        }
                        mSelectedItem = contact;
                    }
                } else {
                    if (mSelectedItem == contact) {
                        mSelectedItem.setSelected(false);
                    }
                }
            }
        });
    }

    private void initTopBar() {
        mTopBar.setTitle(getResources().getString(R.string.start_conversation));
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> {
            finish();
        });
        mTopBar.addRightTextButton(getResources().getString(R.string.sure), R.id.topbar_right_change_button).setOnClickListener(v -> {
            startConversation();
        });
    }

    private void initView() {
        ButterKnife.bind(this);
    }

    public void startConversation() {
        if (mSelectedItem == null || !mSelectedItem.isSelected()) {
            ToastUtil.toastLongMessage("请选择聊天对象");
            return;
        }
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setType(TIMConversationType.C2C);
        chatInfo.setId(mSelectedItem.getId());
        String chatName = mSelectedItem.getId();
        if (!TextUtils.isEmpty(mSelectedItem.getRemark())) {
            chatName = mSelectedItem.getRemark();
        } else if (!TextUtils.isEmpty(mSelectedItem.getNickname())) {
            chatName = mSelectedItem.getNickname();
        }
        chatInfo.setChatName(chatName);
        Intent intent = new Intent(getContext(), ChatActivity.class);
        intent.putExtra("chatInfo", chatInfo);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
