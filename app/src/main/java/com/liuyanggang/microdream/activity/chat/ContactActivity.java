package com.liuyanggang.microdream.activity.chat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.base.BaseActivity;
import com.liuyanggang.microdream.components.AddFriendDialog;
import com.liuyanggang.microdream.utils.ToastyUtil;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMGroupManager;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.friendship.TIMFriendRequest;
import com.tencent.imsdk.friendship.TIMFriendResult;
import com.tencent.imsdk.friendship.TIMFriendStatus;
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.tencent.qcloud.tim.uikit.modules.contact.ContactItemBean;
import com.tencent.qcloud.tim.uikit.modules.contact.ContactLayout;
import com.tencent.qcloud.tim.uikit.modules.contact.ContactListView;
import com.tencent.qcloud.tim.uikit.utils.TUIKitConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.liuyanggang.microdream.MicrodreamApplication.getContext;

/**
 * @ClassName ContactActivity
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/6/6
 * @Version 1.0
 */
public class ContactActivity extends BaseActivity {
    @BindView(R.id.contact_layout)
    ContactLayout mContactLayout;
    private ChatInfo mChatInfo;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    private QMUIPopup mNormalPopup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initView();
        initTopBar();
        initContact();
    }

    private void initContact() {
        TitleBarLayout titleBarLayout = mContactLayout.getTitleBar();
        titleBarLayout.setVisibility(View.GONE);

        mContactLayout.getContactListView().setOnItemClickListener(new ContactListView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, ContactItemBean contact) {
                if (position == 0) {
                    startActivity(new Intent(getContext(), NewFriendActivity.class));
                } else if (position == 1) {
                    startActivity(new Intent(getContext(), GroupListActivity.class));
                } else if (position == 2) {
                    startActivity(new Intent(getContext(), BlackListActivity.class));
                } else {
                    Intent intent = new Intent(getContext(), FriendProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(TUIKitConstants.ProfileType.CONTENT, contact);
                    startActivity(intent);
                }
            }
        });
    }

    private void initTopBar() {
        mTopBar.setTitle("通讯录");
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> {
            finish();
        });
        mTopBar.addRightImageButton(R.mipmap.plus, R.id.topbar_right_change_button).setOnClickListener(v -> {
            initRightButton();
        });
    }

    private void initRightButton() {
        String[] listItems = new String[]{
                "添加好友",
                "添加群聊"
        };
        List<String> data = new ArrayList<>();
        Collections.addAll(data, listItems);
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.layout_simple_list_item, data);
        AdapterView.OnItemClickListener onItemClickListener = (adapterView, view, i, l) -> {
            switch (i) {
                case 0:
                    addFriend();
                    break;
                case 1:
                    addGroup();
                    break;
            }
            if (mNormalPopup != null) {
                mNormalPopup.dismiss();
            }
        };
        mNormalPopup = QMUIPopups.listPopup(this,
                QMUIDisplayHelper.dp2px(this, 180),
                QMUIDisplayHelper.dp2px(this, 300),
                adapter,
                onItemClickListener)
                .animStyle(QMUIPopup.ANIM_AUTO)
                .preferredDirection(QMUIPopup.DIRECTION_TOP)
                .shadow(true)
                .offsetYIfTop(QMUIDisplayHelper.dp2px(this, 5))
                .onDismiss(() -> {

                })
                .show(findViewById(R.id.topbar_right_change_button));
    }

    /**
     * 添加好友
     */
    private void addFriend() {
        AddFriendDialog addFriendDialog = new AddFriendDialog(ContactActivity.this, getString(R.string.add_friend));
        try {
            Objects.requireNonNull(addFriendDialog.getWindow()).setWindowAnimations(R.style.DialogAnimations);
        } catch (Exception ignored) {

        }
        addFriendDialog.show();
        addFriendDialog.setOnClickCloseListener(new AddFriendDialog.OnClickCloseListener() {
            @Override
            public void onColseClick() {
                addFriendDialog.dismiss();
            }

            @Override
            public void onEnterClick(String id, String mAddWording) {
                TIMFriendRequest timFriendRequest = new TIMFriendRequest(id);
                timFriendRequest.setAddWording(mAddWording);
                timFriendRequest.setAddSource("android");
                TIMFriendshipManager.getInstance().addFriend(timFriendRequest, new TIMValueCallBack<TIMFriendResult>() {
                    @Override
                    public void onError(int i, String s) {
                        ToastyUtil.setNormalDanger(getApplicationContext(), s, Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onSuccess(TIMFriendResult timFriendResult) {
                        switch (timFriendResult.getResultCode()) {
                            case TIMFriendStatus.TIM_FRIEND_STATUS_SUCC:
                                ToastyUtil.setNormalSuccess(getApplicationContext(), "成功", Toast.LENGTH_SHORT);
                                break;
                            case TIMFriendStatus.TIM_FRIEND_PARAM_INVALID:
                                if (TextUtils.equals(timFriendResult.getResultInfo(), "Err_SNS_FriendAdd_Friend_Exist")) {
                                    ToastyUtil.setNormalInfo(getApplicationContext(), "对方已是您的好友", Toast.LENGTH_SHORT);
                                    break;
                                }
                            case TIMFriendStatus.TIM_ADD_FRIEND_STATUS_SELF_FRIEND_FULL:
                                ToastyUtil.setNormalInfo(getApplicationContext(), "您的好友数已达系统上限", Toast.LENGTH_SHORT);
                                break;
                            case TIMFriendStatus.TIM_ADD_FRIEND_STATUS_THEIR_FRIEND_FULL:
                                ToastyUtil.setNormalInfo(getApplicationContext(), "对方的好友数已达系统上限", Toast.LENGTH_SHORT);
                                break;
                            case TIMFriendStatus.TIM_ADD_FRIEND_STATUS_IN_SELF_BLACK_LIST:
                                ToastyUtil.setNormalInfo(getApplicationContext(), "被加好友在自己的黑名单中", Toast.LENGTH_SHORT);
                                break;
                            case TIMFriendStatus.TIM_ADD_FRIEND_STATUS_FRIEND_SIDE_FORBID_ADD:
                                ToastyUtil.setNormalInfo(getApplicationContext(), "对方已禁止加好友", Toast.LENGTH_SHORT);
                                break;
                            case TIMFriendStatus.TIM_ADD_FRIEND_STATUS_IN_OTHER_SIDE_BLACK_LIST:
                                ToastyUtil.setNormalInfo(getApplicationContext(), "您已被被对方设置为黑名单", Toast.LENGTH_SHORT);
                                break;
                            case TIMFriendStatus.TIM_ADD_FRIEND_STATUS_PENDING:
                                ToastyUtil.setNormalInfo(getApplicationContext(), "等待好友审核同意", Toast.LENGTH_SHORT);
                                break;
                            default:
                                ToastyUtil.setNormalInfo(getApplicationContext(), timFriendResult.getResultCode() + " " + timFriendResult.getResultInfo(), Toast.LENGTH_SHORT);
                                break;
                        }
                        addFriendDialog.dismiss();
                    }
                });
            }
        });
    }

    /**
     * 添加群组
     */
    public void addGroup() {
        AddFriendDialog addFriendDialog = new AddFriendDialog(ContactActivity.this, getString(R.string.add_group));
        try {
            Objects.requireNonNull(addFriendDialog.getWindow()).setWindowAnimations(R.style.DialogAnimations);
        } catch (Exception ignored) {

        }
        addFriendDialog.show();
        addFriendDialog.setOnClickCloseListener(new AddFriendDialog.OnClickCloseListener() {
            @Override
            public void onColseClick() {
                addFriendDialog.dismiss();
            }

            @Override
            public void onEnterClick(String id, String mAddWording) {
                if (TextUtils.isEmpty(id)) {
                    return;
                }
                TIMGroupManager.getInstance().applyJoinGroup(id, mAddWording, new TIMCallBack() {

                    @Override
                    public void onError(int i, String s) {
                        ToastyUtil.setNormalDanger(getApplicationContext(), s, Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onSuccess() {
                        ToastyUtil.setNormalSuccess(getApplicationContext(), "加群请求已发送", Toast.LENGTH_SHORT);
                        addFriendDialog.dismiss();
                    }
                });
            }
        });
    }

    private void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mContactLayout.initDefault();
    }
}
