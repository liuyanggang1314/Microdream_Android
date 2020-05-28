package com.liuyanggang.microdream.presenter;

import com.liuyanggang.microdream.entity.UserEntity;
import com.liuyanggang.microdream.model.lisentener.RegisteredListener;
import com.liuyanggang.microdream.model.RegisteredIModel;
import com.liuyanggang.microdream.view.RegisterView;

import java.lang.ref.WeakReference;

/**
 * @ClassName RegisterIPresenter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/23
 * @Version 1.0
 */
public class RegisteredIPresenter extends IPresenter {
    public RegisteredIPresenter(RegisterView registerView) {
        this.mImodel = new RegisteredIModel();
        this.mViewReference = new WeakReference<>(registerView);
    }

    public void register() {
        if (mImodel != null && mViewReference != null && mViewReference.get() != null) {
            RegisterView registerView = (RegisterView) mViewReference.get();
            UserEntity userEntity = registerView.getRegisterInfo();
            registerView = null;
            ((RegisteredIModel) mImodel).register(userEntity, new RegisteredListener() {
                @Override
                public void onSeccess() {
                    if (mViewReference.get() != null) {
                        ((RegisterView) mViewReference.get()).onRegisterSeccess();
                    }
                }

                @Override
                public void onError(String error) {
                    if (mViewReference.get() != null) {
                        ((RegisterView) mViewReference.get()).onRegisterError(error);
                    }
                }
            });
        }
    }
}
