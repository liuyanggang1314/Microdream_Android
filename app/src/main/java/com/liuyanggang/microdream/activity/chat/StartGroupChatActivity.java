package com.liuyanggang.microdream.activity.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.base.BaseActivity;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMManager;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;
import com.tencent.qcloud.tim.uikit.component.LineControllerView;
import com.tencent.qcloud.tim.uikit.component.SelectionActivity;
import com.tencent.qcloud.tim.uikit.modules.chat.GroupChatManagerKit;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.contact.ContactItemBean;
import com.tencent.qcloud.tim.uikit.modules.contact.ContactListView;
import com.tencent.qcloud.tim.uikit.modules.group.info.GroupInfo;
import com.tencent.qcloud.tim.uikit.modules.group.member.GroupMemberInfo;
import com.tencent.qcloud.tim.uikit.utils.TUIKitConstants;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.liuyanggang.microdream.MicrodreamApplication.getContext;

/**
 * @ClassName StartGroupChatActivity
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/6/9
 * @Version 1.0
 */
public class StartGroupChatActivity extends BaseActivity {

    private static final String TAG = StartGroupChatActivity.class.getSimpleName();

    @BindView(R.id.group_create_member_list)
    ContactListView mContactListView;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;


    private LineControllerView mJoinType;
    private ArrayList<GroupMemberInfo> mMembers = new ArrayList<>();
    private int mGroupType = -1;
    private int mJoinTypeIndex = 2;
    private ArrayList<String> mJoinTypes = new ArrayList<>();
    private ArrayList<String> mGroupTypeValue = new ArrayList<>();
    private boolean mCreating;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_start_group_chat);
        initView();
        initTopBar();
        initContactListView();
    }

    private void initView() {
        ButterKnife.bind(this);
    }

    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> {
            finish();
        });
        mTopBar.addRightTextButton(getResources().getString(R.string.sure), R.id.topbar_right_change_button).setOnClickListener(v -> {
            createGroupChat();
        });
    }

    private void initContactListView() {
        String[] array = getResources().getStringArray(R.array.group_type);
        mGroupTypeValue.addAll(Arrays.asList(array));
        array = getResources().getStringArray(R.array.group_join_type);
        mJoinTypes.addAll(Arrays.asList(array));
        GroupMemberInfo memberInfo = new GroupMemberInfo();
        memberInfo.setAccount(TIMManager.getInstance().getLoginUser());
        mMembers.add(0, memberInfo);

        mJoinType = findViewById(R.id.group_type_join);
        mJoinType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showJoinTypePickerView();
            }
        });
        mJoinType.setCanNav(true);
        mJoinType.setContent(mJoinTypes.get(2));


        mContactListView.loadDataSource(ContactListView.DataSource.FRIEND_LIST);
        mContactListView.setOnSelectChangeListener(new ContactListView.OnSelectChangedListener() {
            @Override
            public void onSelectChanged(ContactItemBean contact, boolean selected) {
                if (selected) {
                    GroupMemberInfo memberInfo = new GroupMemberInfo();
                    memberInfo.setAccount(contact.getId());
                    mMembers.add(memberInfo);
                } else {
                    for (int i = mMembers.size() - 1; i >= 0; i--) {
                        if (mMembers.get(i).getAccount().equals(contact.getId())) {
                            mMembers.remove(i);
                        }
                    }
                }
            }
        });

        setGroupType(getIntent().getIntExtra("type", TUIKitConstants.GroupType.PRIVATE));
    }

    public void setGroupType(int type) {
        mGroupType = type;
        switch (type) {
            case TUIKitConstants.GroupType.PUBLIC:
                mTopBar.setTitle(getResources().getString(R.string.create_group_chat));
                mJoinType.setVisibility(View.VISIBLE);
                break;
            case TUIKitConstants.GroupType.CHAT_ROOM:
                mTopBar.setTitle(getResources().getString(R.string.create_chat_room));
                mJoinType.setVisibility(View.VISIBLE);
                break;
            case TUIKitConstants.GroupType.PRIVATE:
            default:
                mTopBar.setTitle(getResources().getString(R.string.create_private_group));
                mJoinType.setVisibility(View.GONE);
                break;
        }
    }

    private void showJoinTypePickerView() {
        Bundle bundle = new Bundle();
        bundle.putString(TUIKitConstants.Selection.TITLE, getResources().getString(R.string.group_join_type));
        bundle.putStringArrayList(TUIKitConstants.Selection.LIST, mJoinTypes);
        bundle.putInt(TUIKitConstants.Selection.DEFAULT_SELECT_ITEM_INDEX, mJoinTypeIndex);
        SelectionActivity.startListSelection(this, bundle, new SelectionActivity.OnResultReturnListener() {
            @Override
            public void onReturn(Object text) {
                mJoinType.setContent(mJoinTypes.get((Integer) text));
                mJoinTypeIndex = (Integer) text;
            }
        });
    }

    private void createGroupChat() {
        if (mCreating) {
            return;
        }
        if (mGroupType < 3 && mMembers.size() == 1) {
            ToastUtil.toastLongMessage(getResources().getString(R.string.tips_empty_group_member));
            return;
        }
        if (mGroupType > 0 && mJoinTypeIndex == -1) {
            ToastUtil.toastLongMessage(getResources().getString(R.string.tips_empty_group_type));
            return;
        }
        if (mGroupType == 0) {
            mJoinTypeIndex = -1;
        }
        final GroupInfo groupInfo = new GroupInfo();
        String groupName = TIMManager.getInstance().getLoginUser();
        for (int i = 1; i < mMembers.size(); i++) {
            groupName = groupName + "、" + mMembers.get(i).getAccount();
        }
        if (groupName.length() > 20) {
            groupName = groupName.substring(0, 17) + "...";
        }
        groupInfo.setChatName(groupName);
        groupInfo.setGroupName(groupName);
        groupInfo.setMemberDetails(mMembers);
        groupInfo.setGroupType(mGroupTypeValue.get(mGroupType));
        groupInfo.setJoinType(mJoinTypeIndex);

        mCreating = true;
        GroupChatManagerKit.createGroupChat(groupInfo, new IUIKitCallBack() {
            @Override
            public void onSuccess(Object data) {
                ChatInfo chatInfo = new ChatInfo();
                chatInfo.setType(TIMConversationType.Group);
                chatInfo.setId(data.toString());
                chatInfo.setChatName(groupInfo.getGroupName());
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("chatInfo", chatInfo);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                mCreating = false;
            }
        });
    }

}
