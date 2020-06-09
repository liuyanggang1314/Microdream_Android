package com.liuyanggang.microdream.activity.chat;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.adapter.NewFriendAdapter;
import com.liuyanggang.microdream.base.BaseActivity;
import com.liuyanggang.microdream.components.AddFriendDialog;
import com.liuyanggang.microdream.utils.CustomAnimation;
import com.liuyanggang.microdream.utils.ToastyUtil;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMGroupManager;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.friendship.TIMFriendPendencyItem;
import com.tencent.imsdk.friendship.TIMFriendPendencyRequest;
import com.tencent.imsdk.friendship.TIMFriendPendencyResponse;
import com.tencent.imsdk.friendship.TIMFriendRequest;
import com.tencent.imsdk.friendship.TIMFriendResponse;
import com.tencent.imsdk.friendship.TIMFriendResult;
import com.tencent.imsdk.friendship.TIMFriendStatus;
import com.tencent.imsdk.friendship.TIMPendencyType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName NewFriendActivity
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/6/8
 * @Version 1.0
 */
public class NewFriendActivity extends BaseActivity {
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    private QMUIPopup mNormalPopup;
    private NewFriendAdapter adapter;
    private List<TIMFriendPendencyItem> datas;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);
        initView();
        initTopBar();
        initRecyclerView();
        initListener();
    }

    private void initListener() {
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            TIMFriendResponse response = new TIMFriendResponse();
            response.setIdentifier(((TIMFriendPendencyItem) adapter.getData().get(position)).getIdentifier());
            response.setResponseType(TIMFriendResponse.TIM_FRIEND_RESPONSE_AGREE_AND_ADD);
            TIMFriendshipManager.getInstance().doResponse(response, new TIMValueCallBack<TIMFriendResult>() {
                @Override
                public void onError(int i, String s) {

                }

                @Override
                public void onSuccess(TIMFriendResult timUserProfiles) {
                    ToastyUtil.setNormalSuccess(getApplicationContext(), "已同意", Toast.LENGTH_SHORT);
                    setData();
                }
            });
        });
    }

    private void initRecyclerView() {
        setData();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new NewFriendAdapter(R.layout.activity_new_friend_item, datas);
        adapter.setAnimationEnable(true);
        adapter.setAnimationFirstOnly(false);
        adapter.setAdapterAnimation(new CustomAnimation());
        //添加Android自带的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private void setData() {
        final TIMFriendPendencyRequest timFriendPendencyRequest = new TIMFriendPendencyRequest();
        timFriendPendencyRequest.setTimPendencyGetType(TIMPendencyType.TIM_PENDENCY_COME_IN);
        timFriendPendencyRequest.setSeq(0);
        timFriendPendencyRequest.setTimestamp(0);
        timFriendPendencyRequest.setNumPerPage(10);
        TIMFriendshipManager.getInstance().getPendencyList(timFriendPendencyRequest, new TIMValueCallBack<TIMFriendPendencyResponse>() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(TIMFriendPendencyResponse timFriendPendencyResponse) {
                if (timFriendPendencyResponse.getItems() != null) {
                    if (timFriendPendencyResponse.getItems().size() == 0) {
                        getEmptyView();
                        return;
                    }
                }
                adapter.addData(timFriendPendencyResponse.getItems());
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 获取recyclerview空布局
     *
     * @return
     */
    private void getEmptyView() {
        View emptyView = getLayoutInflater().inflate(R.layout.layout_emptyview, recyclerView, false);
        emptyView.setOnClickListener(v -> {

        });
        adapter.setEmptyView(emptyView);
    }

    private void initView() {
        ButterKnife.bind(this);
    }

    private void initTopBar() {
        mTopBar.setTitle("新的联系人");
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
        AddFriendDialog addFriendDialog = new AddFriendDialog(NewFriendActivity.this, "添加好友");
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
        AddFriendDialog addFriendDialog = new AddFriendDialog(NewFriendActivity.this, "添加群组");
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
}
