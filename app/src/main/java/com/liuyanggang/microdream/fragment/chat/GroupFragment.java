package com.liuyanggang.microdream.fragment.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.liuyanggang.microdream.R;
import com.tencent.qcloud.tim.uikit.base.BaseFragment;
import com.tencent.qcloud.tim.uikit.modules.group.info.GroupInfo;
import com.tencent.qcloud.tim.uikit.modules.group.info.GroupInfoLayout;
import com.tencent.qcloud.tim.uikit.modules.group.member.GroupMemberDeleteFragment;
import com.tencent.qcloud.tim.uikit.modules.group.member.GroupMemberInviteFragment;
import com.tencent.qcloud.tim.uikit.modules.group.member.GroupMemberManagerFragment;
import com.tencent.qcloud.tim.uikit.modules.group.member.IGroupMemberRouter;
import com.tencent.qcloud.tim.uikit.utils.TUIKitConstants;

/**
 * @ClassName GroupFragment
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/6/9
 * @Version 1.0
 */
public class GroupFragment extends BaseFragment {

    private View mBaseView;
    private GroupInfoLayout mGroupInfoLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBaseView = inflater.inflate(R.layout.group_info_fragment, container, false);
        initView();
        return mBaseView;
    }

    private void initView() {
        mGroupInfoLayout = mBaseView.findViewById(R.id.group_info_layout);
        mGroupInfoLayout.getTitleBar().setVisibility(View.GONE);
        mGroupInfoLayout.setGroupId(getArguments().getString(TUIKitConstants.Group.GROUP_ID));
        mGroupInfoLayout.setRouter(new IGroupMemberRouter() {
            @Override
            public void forwardListMember(GroupInfo info) {
                GroupMemberManagerFragment fragment = new GroupMemberManagerFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(TUIKitConstants.Group.GROUP_INFO, info);
                fragment.setArguments(bundle);
                forward(fragment, false);
            }

            @Override
            public void forwardAddMember(GroupInfo info) {
                GroupMemberInviteFragment fragment = new GroupMemberInviteFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(TUIKitConstants.Group.GROUP_INFO, info);
                fragment.setArguments(bundle);
                forward(fragment, false);
            }

            @Override
            public void forwardDeleteMember(GroupInfo info) {
                GroupMemberDeleteFragment fragment = new GroupMemberDeleteFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(TUIKitConstants.Group.GROUP_INFO, info);
                fragment.setArguments(bundle);
                forward(fragment, false);
            }
        });

    }
}
