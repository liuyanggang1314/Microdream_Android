package com.liuyanggang.microdream.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.liuyanggang.microdream.MainActivity;
import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.base.BaseActivity;
import com.liuyanggang.microdream.components.UnauthorizedDialog;
import com.liuyanggang.microdream.entity.UserEntity;
import com.liuyanggang.microdream.manager.AppManager;
import com.liuyanggang.microdream.presenter.PersonallnformationIPresenter;
import com.liuyanggang.microdream.utils.MMKVUtil;
import com.liuyanggang.microdream.utils.TimeUtil;
import com.liuyanggang.microdream.utils.ToastyUtil;
import com.liuyanggang.microdream.view.PersonallnformationIView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import cn.hutool.core.util.ReUtil;

import static com.liuyanggang.microdream.entity.HttpEntity.MICRODREAM_SERVER_IMG;
import static com.liuyanggang.microdream.entity.HttpEntity.UNAUTHORIZED_STRING;
import static com.liuyanggang.microdream.entity.MicrodreamEntity.REGEX_EMAIL;
import static com.liuyanggang.microdream.entity.MicrodreamEntity.REGEX_MOBILE;

/**
 * @ClassName PersonalInformationActivity
 * @Description TODO 个人信息页
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/27
 * @Version 1.0
 */
public class PersonalInformationActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener, PersonallnformationIView {
    private PersonallnformationIPresenter mPresenter;
    private InvokeParam invokeParam;
    private TakePhoto takePhoto;
    private QMUITipDialog tipDialog;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;

    @BindViews({R.id.linearLayout_useername, R.id.linearLayout_nickName, R.id.linearLayout_email, R.id.linearLayout_tel, R.id.linearLayout_sex,
            R.id.linearLayout_account_registration_time, R.id.linearLayout_last_password_modification_time})
    List<LinearLayout> linearLayouts;
    @BindViews({R.id.username, R.id.nickName, R.id.email, R.id.tel, R.id.sex,
            R.id.account_registration_time, R.id.last_password_modification_time})
    List<TextView> textViews;
    @BindView(R.id.linearLayout_avatar)
    LinearLayout linearLayoutAvatar;
    @BindView(R.id.avatar)
    ImageView avatar;
    private String avatarFilePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        initView();
        initTopBar();
        initUserInfo();
        initListener();
        setData();
    }

    private void setData() {
        this.mPresenter = new PersonallnformationIPresenter(this);
    }

    private void initListener() {
        linearLayouts.get(1).setOnClickListener(view -> {
            setInfo(getString(R.string.edit_nikename), MMKVUtil.getStringInfo("nickName"), textViews.get(1));
        });
        linearLayouts.get(2).setOnClickListener(view -> {
            setInfo(getString(R.string.edit_email), MMKVUtil.getStringInfo("email"), textViews.get(2));
        });
        linearLayouts.get(3).setOnClickListener(view -> {
            setInfo(getString(R.string.edit_tel), MMKVUtil.getStringInfo("phone"), textViews.get(3));
        });
        linearLayouts.get(4).setOnClickListener(view -> {
            final String[] items = new String[]{"男", "女"};
            final int checkedIndex = 0;
            new QMUIDialog.CheckableDialogBuilder(this)
                    .setCheckedIndex(checkedIndex)
                    .addItems(items, (dialog, which) -> {
                        textViews.get(4).setText(items[which]);
                        dialog.dismiss();
                    })
                    .create(R.style.MyDialogPink).show();
        });
        linearLayoutAvatar.setOnClickListener(view -> {
            CropOptions cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(true).create();
            takePhoto.onPickFromGalleryWithCrop(getImageCropUri(), cropOptions);
        });
    }

    private void setInfo(String title, String info, TextView textView) {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(this);

        if (title.equals(getString(R.string.edit_nikename))) {
            builder.setTitle(title)
                    .setDefaultText(info)
                    .setInputType(InputType.TYPE_CLASS_TEXT)
                    .addAction("取消", (dialog, index) -> dialog.dismiss())
                    .addAction("确定", (dialog, index) -> {
                        CharSequence text = builder.getEditText().getText();
                        if (text != null && text.length() > 0) {
                            textView.setText(text);
                            dialog.dismiss();
                        } else {
                            ToastyUtil.setNormalInfo(PersonalInformationActivity.this, "请检查是否有误", Toast.LENGTH_SHORT);
                        }
                    })
                    .create(R.style.MyDialogPink).show();
        } else if (title.equals(getString(R.string.edit_email))) {
            builder.setTitle(title)
                    .setDefaultText(info)
                    .setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                    .addAction("取消", (dialog, index) -> dialog.dismiss())
                    .addAction("确定", (dialog, index) -> {
                        CharSequence text = builder.getEditText().getText();
                        if (ReUtil.isMatch(REGEX_EMAIL, text)) {
                            textView.setText(text);
                            dialog.dismiss();
                        } else {
                            ToastyUtil.setNormalInfo(PersonalInformationActivity.this, "请检查是否有误", Toast.LENGTH_SHORT);
                        }
                    })
                    .create(R.style.MyDialogPink).show();
        } else if (title.equals(getString(R.string.edit_tel))) {
            builder.setTitle(title)
                    .setDefaultText(info)
                    .setInputType(InputType.TYPE_CLASS_PHONE)
                    .addAction("取消", (dialog, index) -> dialog.dismiss())
                    .addAction("确定", (dialog, index) -> {
                        CharSequence text = builder.getEditText().getText();
                        if (ReUtil.isMatch(REGEX_MOBILE, text)) {
                            textView.setText(text);
                            dialog.dismiss();
                        } else {
                            ToastyUtil.setNormalInfo(PersonalInformationActivity.this, "请检查是否有误", Toast.LENGTH_SHORT);
                        }
                    })
                    .create(R.style.MyDialogPink).show();
        }


    }

    /**
     * 初始化用户信息
     */
    private void initUserInfo() {
        textViews.get(0).setText(MMKVUtil.getStringInfo("username"));
        textViews.get(1).setText(MMKVUtil.getStringInfo("nickName"));
        textViews.get(2).setText(MMKVUtil.getStringInfo("email"));
        textViews.get(3).setText(MMKVUtil.getStringInfo("phone"));
        textViews.get(4).setText(MMKVUtil.getStringInfo("gender"));
        textViews.get(5).setText(TimeUtil.setCurrentTimeMillis(MMKVUtil.getStringInfo("createTime")));
        textViews.get(6).setText(TimeUtil.setCurrentTimeMillis(MMKVUtil.getStringInfo("pwdResetTime")));
        Glide.with(this).load(MICRODREAM_SERVER_IMG + MMKVUtil.getStringInfo("avatarName"))
                .placeholder(R.drawable.image_fill)
                .error(R.drawable.logo)
                .into(avatar);
    }

    private void initView() {
        ButterKnife.bind(this);
    }

    /**
     * 初始化标题栏
     */
    private void initTopBar() {
        mTopBar.setTitle("个人信息");
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> {
            finish();
        });
        mTopBar.addRightTextButton("保存", R.id.topbar_right_change_button).setOnClickListener(v -> {
            tipdialog("正在修改");
            mPresenter.updateUser();
        });
    }

    //获得照片的输出保存Uri
    private Uri getImageCropUri() {
        final String galleryPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator;
        File file = new File(galleryPath, System.currentTimeMillis() + ".png");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    /**
     * 图片裁剪成功
     *
     * @param result
     */
    @Override
    public void takeSuccess(TResult result) {
        tipdialog("正在上传头像");
        avatarFilePath = result.getImage().getOriginalPath();
        mPresenter.updateAvatar();
    }

    /**
     * 图片裁剪失败
     *
     * @param result
     * @param msg
     */
    @Override
    public void takeFail(TResult result, String msg) {

    }

    /**
     * 图片裁剪关闭
     */
    @Override
    public void takeCancel() {

    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    /**
     * 加载UI
     *
     * @param info
     */
    private void tipdialog(String info) {
        tipDialog = new QMUITipDialog.Builder(PersonalInformationActivity.this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(info)
                .create();
        tipDialog.show();
    }

    /**
     * 获取头像地址
     *
     * @return
     */
    @Override
    public String getAvatarPath() {
        return avatarFilePath;
    }

    /**
     * 头像更新成功返回
     *
     * @param avatarName
     */
    @Override
    public void onUpdateAvatarSuccess(String avatarName) {
        tipDialog.dismiss();
        ToastyUtil.setNormalSuccess(this, "头像更新成功", Toast.LENGTH_SHORT);
        Glide.with(getApplicationContext()).load(MICRODREAM_SERVER_IMG + MMKVUtil.getStringInfo("avatarName"))
                .placeholder(R.drawable.image_fill)
                .error(R.drawable.logo)
                .into(avatar);
    }

    /**
     * 头像更新失败返回
     *
     * @param error
     */
    @Override
    public void onUpdateAvatarError(String error) {
        if (UNAUTHORIZED_STRING.equals(error)) {
            tipDialog.dismiss();
            UnauthorizedDialog unauthorizedDialog = new UnauthorizedDialog(this);
            unauthorizedDialog.setOnClickCloseListener(new UnauthorizedDialog.OnClickCloseListener() {
                @Override
                public void onColseClick() {
                    unauthorizedDialog.dismiss();
                }

                @Override
                public void onEnterClick() {
                    unauthorizedDialog.dismiss();
                    AppManager.getInstance().finishOtherActivity(MainActivity.class);
                    finish();
                }
            });
            unauthorizedDialog.show();
        } else {
            tipDialog.dismiss();
            ToastyUtil.setNormalDanger(this, error, Toast.LENGTH_SHORT);
        }

    }

    /**
     * 获取用户更新信息
     *
     * @return
     */
    @Override
    public UserEntity getUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setNickName(textViews.get(1).getText().toString().trim());
        userEntity.setPhone(textViews.get(3).getText().toString().trim());
        userEntity.setGender(textViews.get(4).getText().toString().trim());
        return userEntity;
    }

    /**
     * 用户信息更新成功返回
     */
    @Override
    public void onUpdateUserSuccess() {
        tipDialog.dismiss();
        ToastyUtil.setNormalSuccess(this, "信息更新成功", Toast.LENGTH_SHORT);
    }

    /**
     * 用户信息更新失败返回
     *
     * @param error
     */
    @Override
    public void onUpdateUserError(String error) {
        if (UNAUTHORIZED_STRING.equals(error)) {
            tipDialog.dismiss();
            UnauthorizedDialog unauthorizedDialog = new UnauthorizedDialog(this);
            unauthorizedDialog.setOnClickCloseListener(new UnauthorizedDialog.OnClickCloseListener() {
                @Override
                public void onColseClick() {
                    unauthorizedDialog.dismiss();
                }

                @Override
                public void onEnterClick() {
                    unauthorizedDialog.dismiss();
                    AppManager.getInstance().finishOtherActivity(MainActivity.class);
                    finish();
                }
            });
            unauthorizedDialog.show();
        }else {
            tipDialog.dismiss();
            ToastyUtil.setNormalDanger(this, error, Toast.LENGTH_SHORT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mPresenter = null;
    }
}
