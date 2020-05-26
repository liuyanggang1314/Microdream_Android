package com.liuyanggang.microdream.presenter;

import com.liuyanggang.microdream.model.lisentener.ChangePasswordListener;
import com.liuyanggang.microdream.model.lisentener.GetUserInfoLisentener;
import com.liuyanggang.microdream.model.lisentener.LogoutLinstener;
import com.liuyanggang.microdream.model.MainIModel;
import com.liuyanggang.microdream.view.MainIView;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * @ClassName MainPresenter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/24
 * @Version 1.0
 */
public class MainIPresenter extends IPresenter {
    public MainIPresenter(MainIView mainIView) {
        this.mImodel = new MainIModel();
        this.mViewReference = new WeakReference<>(mainIView);
    }

    /**
     * 获取当前用户信息
     */
    public void getUserInfo() {
        if (mImodel != null && mViewReference != null && mViewReference.get() != null) {
            MainIView loginView = (MainIView) mViewReference.get();
            loginView = null;
            //此时LoginListener作为匿名内部类是持有外部类的引用的。
            ((MainIModel) mImodel).getUserInfo(new GetUserInfoLisentener() {
                @Override
                public void onGetUserInfoSuccess() {
                    if (mViewReference.get() != null) {
                        ((MainIView) mViewReference.get()).onGetUserInfoSuccess();
                    }
                }

                @Override
                public void onGetUserInfoError(String error) {
                    if (mViewReference.get() != null) {
                        ((MainIView) mViewReference.get()).onGetUserInfoError(error);
                    }
                }
            });
        }
    }

    /**
     * 修改密码
     */
    public void changePassword() {
        if (mImodel != null && mViewReference != null && mViewReference.get() != null) {
            MainIView loginView = (MainIView) mViewReference.get();
            Map<String, String> paawordInfo = loginView.onGetPasswordInfo();
            loginView = null;
            //此时LoginListener作为匿名内部类是持有外部类的引用的。
            ((MainIModel) mImodel).chagePawword(paawordInfo, new ChangePasswordListener() {
                @Override
                public void onChangePasswordSuccess() {
                    if (mViewReference.get() != null) {
                        ((MainIView) mViewReference.get()).onChangePasswordSuccess();
                    }
                }

                @Override
                public void onChangePasswordError(String error) {
                    if (mViewReference.get() != null) {
                        ((MainIView) mViewReference.get()).onChangePasswordError(error);
                    }
                }
            });
        }
    }

    /**
     * 退出登录
     */
    public void logout() {
        if (mImodel != null && mViewReference != null && mViewReference.get() != null) {
            MainIView loginView = (MainIView) mViewReference.get();
            loginView = null;
            //此时LoginListener作为匿名内部类是持有外部类的引用的。
            ((MainIModel) mImodel).logout(new LogoutLinstener() {
                @Override
                public void onLogoutSeccess() {
                    if (mViewReference.get() != null) {
                        ((MainIView) mViewReference.get()).onLogoutSuccess();
                    }
                }

                @Override
                public void onLogoutError(String error) {
                    if (mViewReference.get() != null) {
                        ((MainIView) mViewReference.get()).onLogoutError(error);
                    }
                }
            });
        }
    }

}
