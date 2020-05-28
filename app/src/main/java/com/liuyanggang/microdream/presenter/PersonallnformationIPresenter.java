package com.liuyanggang.microdream.presenter;

import com.liuyanggang.microdream.entity.UserEntity;
import com.liuyanggang.microdream.model.PersonallnformationIModel;
import com.liuyanggang.microdream.model.lisentener.PersonallnformationAvatarListener;
import com.liuyanggang.microdream.model.lisentener.PersonallnformationListener;
import com.liuyanggang.microdream.view.PersonallnformationIView;

import java.lang.ref.WeakReference;

/**
 * @ClassName LoginIPresenter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/19
 * @Version 1.0
 */
public class PersonallnformationIPresenter extends IPresenter {

    public PersonallnformationIPresenter(PersonallnformationIView personallnformationIView) {
        this.mImodel = new PersonallnformationIModel();
        this.mViewReference = new WeakReference<>(personallnformationIView);
    }

    public void updateAvatar() {
        if (mImodel != null && mViewReference != null && mViewReference.get() != null) {
            PersonallnformationIView personallnformationIView = (PersonallnformationIView) mViewReference.get();
            String avatarPath = personallnformationIView.getAvatarPath();
            personallnformationIView = null;
            //此时LoginListener作为匿名内部类是持有外部类的引用的。
            ((PersonallnformationIModel) mImodel).updateAvatar(avatarPath, new PersonallnformationAvatarListener() {
                @Override
                public void onPersonallformationAvatarSeccess(String avatarName) {
                    if (mViewReference.get() != null) {
                        ((PersonallnformationIView) mViewReference.get()).onUpdateAvatarSuccess(avatarName);
                    }
                }

                @Override
                public void onPersonallformationAvatarError(String error) {
                    if (mViewReference.get() != null) {
                        ((PersonallnformationIView) mViewReference.get()).onUpdateAvatarError(error);
                    }
                }
            });
        }
    }

    public void updateUser() {
        if (mImodel != null && mViewReference != null && mViewReference.get() != null) {
            PersonallnformationIView personallnformationIView = (PersonallnformationIView) mViewReference.get();
            UserEntity userEntity = personallnformationIView.getUser();
            personallnformationIView = null;
            //此时LoginListener作为匿名内部类是持有外部类的引用的。
            ((PersonallnformationIModel) mImodel).updateUser(userEntity, new PersonallnformationListener() {
                @Override
                public void onPersonallformationSeccess() {
                    if (mViewReference.get() != null) {
                        ((PersonallnformationIView) mViewReference.get()).onUpdateUserSuccess();
                    }
                }

                @Override
                public void onPersonallformationError(String error) {
                    if (mViewReference.get() != null) {
                        ((PersonallnformationIView) mViewReference.get()).onUpdateUserError(error);
                    }
                }
            });
        }
    }

}
