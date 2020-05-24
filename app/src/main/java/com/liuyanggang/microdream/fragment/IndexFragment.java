package com.liuyanggang.microdream.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @ClassName IndexFragment
 * @Description TODO 主页
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/24
 * @Version 1.0
 */
public class IndexFragment extends BaseFragment {
    private Unbinder unbinder;

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_index, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
