package com.liuyanggang.microdream.presenter;

import com.liuyanggang.microdream.model.Lisentener.LoginLisentener;
import com.liuyanggang.microdream.model.LoginIMode;
import com.liuyanggang.microdream.view.LoginIView;

import java.lang.ref.WeakReference;

/**
 * @ClassName LoginIPresenter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/19
 * @Version 1.0
 */
public class LoginIPresenter extends IPresenter {

    public LoginIPresenter(LoginIView loginView) {
        this.mImodel = new LoginIMode();
        this.mViewReference = new WeakReference<>(loginView);
    }

    public void login() {
        if (mImodel != null && mViewReference != null && mViewReference.get() != null) {
            LoginIView loginView = (LoginIView) mViewReference.get();
            String name = loginView.getUserName();
            String passWord = loginView.getPassword();
            loginView = null;
            //此时LoginListener作为匿名内部类是持有外部类的引用的。
            ((LoginIMode) mImodel).login(name, passWord, new LoginLisentener() {
                @Override
                public void onSeccess() {
                    if (mViewReference.get() != null) {
                        ((LoginIView) mViewReference.get()).onLoginSeccess();
                    }
                }

                @Override
                public void onFails(String fails) {
                    if (mViewReference.get() != null) {
                        if (mViewReference.get() != null) {
                            ((LoginIView) mViewReference.get()).onLoginFails(fails);
                        }
                    }
                }

            });
        }
    }
}
