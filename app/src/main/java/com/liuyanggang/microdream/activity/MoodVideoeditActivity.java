package com.liuyanggang.microdream.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.base.BaseActivity;
import com.liuyanggang.microdream.components.UnauthorizedDialog;
import com.liuyanggang.microdream.entity.MessageEventEntity;
import com.liuyanggang.microdream.manager.AppManager;
import com.liuyanggang.microdream.presenter.MoodEditIPresenter;
import com.liuyanggang.microdream.utils.AnimationUtil;
import com.liuyanggang.microdream.utils.ToastyUtil;
import com.liuyanggang.microdream.view.MoodEditIView;
import com.qmuiteam.qmui.util.QMUIKeyboardHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.liuyanggang.microdream.entity.HttpEntity.UNAUTHORIZED_STRING;


/**
 * @ClassName MoodeditActivity
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/25
 * @Version 1.0
 */
public class MoodVideoeditActivity extends BaseActivity implements MoodEditIView {
    private final static String TAG = "MoodVideoeditActivity";
    private static final int PRC_VIDEO_PICKER = 66;
    private MoodEditIPresenter mPresenter;
    private QMUITipDialog tipDialog;
    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.mood_text)
    EditText moodText;
    @BindView(R.id.video_plus)
    ImageView videoPlus;
    @BindView(R.id.detail_player)
    StandardGSYVideoPlayer videoPlayer;
    private String VIDEOPATH;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moodvideoedit);
        initView();
        initTopBar();
        initVideo();
        setData();
    }

    private void initVideo() {
        videoPlus.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, PRC_VIDEO_PICKER);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PRC_VIDEO_PICKER && resultCode == RESULT_OK && null != data) {
            Uri selectedVideo = data.getData();
            String[] filePathColumn = {MediaStore.Video.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedVideo,
                    filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            VIDEOPATH = cursor.getString(columnIndex);
            cursor.close();
            initVideoPlayer();
        }
    }

    private void initVideoPlayer() {
        videoPlus.setVisibility(View.GONE);
        videoPlayer.setVisibility(View.VISIBLE);
        videoPlayer.setUp(VIDEOPATH, true, "Microdream");
        ImageView imageView = new ImageView(this);

        Glide.with(this).load(VIDEOPATH)
                .frame(1000000)
                .placeholder(R.drawable.image_fill)
                .error(R.drawable.logo)
                .into(imageView);
        //设置封面
        videoPlayer.setThumbImageView(imageView);
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.GONE);
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.GONE);
        //设置全屏按键功能
        videoPlayer.getFullscreenButton().setOnClickListener(v -> videoPlayer.startWindowFullscreen(this, false, true));
        //防止错位设置
        videoPlayer.setPlayTag(TAG);
        //设置循环播放
        videoPlayer.setLooping(true);
        //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
        videoPlayer.setAutoFullWithSize(true);
        videoPlayer.setNeedAutoAdaptation(true);
        //音频焦点冲突时是否释放
        videoPlayer.setReleaseWhenLossAudio(false);
        //全屏动画
        videoPlayer.setShowFullAnimation(true);
        //小屏时不触摸滑动
        videoPlayer.setIsTouchWiget(false);
    }

    private void initTopBar() {
        mTopBar.setTitle("撰写");
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> {
            finish();
        });
        mTopBar.addRightTextButton("发表", R.id.topbar_right_change_button).setOnClickListener(v -> {
            QMUIKeyboardHelper.hideKeyboard(v);
            if (!validate()) {
                AnimationUtil.initAnimationShake(moodText);
                ToastyUtil.setNormalWarning(getApplicationContext(), "请先填写信息", Toast.LENGTH_SHORT);
            } else {
                mPresenter.onSaveMoodVideo();
                tipdialog("正在发表");
            }
        });
    }

    private void setData() {
        this.mPresenter = new MoodEditIPresenter(this);
    }

    private void initView() {
        ButterKnife.bind(this);
    }


    private boolean validate() {
        boolean valid = true;

        String moodText_ = moodText.getText().toString().replaceAll(" ", "");

        if (moodText_.isEmpty()) {
            moodText.setError("不能为空");
            valid = false;
        } else {
            moodText.setError(null);
        }
        if (null == VIDEOPATH) {
            ToastyUtil.setNormalWarning(getApplicationContext(), "请选择视频", Toast.LENGTH_SHORT);
            valid = false;
        }

        return valid;
    }

    /**
     * 加载UI
     *
     * @param info
     */
    private void tipdialog(String info) {
        tipDialog = new QMUITipDialog.Builder(MoodVideoeditActivity.this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(info)
                .create();
        tipDialog.show();
    }

    @Override
    public void onMoodSaveSueccess() {
        tipDialog.dismiss();
        ToastyUtil.setNormalSuccess(getApplicationContext(), "发表成功", Toast.LENGTH_SHORT);
        EventBus.getDefault().post(new MessageEventEntity(2, "onUpdateMoodListListener"));
        finish();
    }

    @Override
    public void onModdSavaError(String error) {
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
                    AppManager.getInstance().finishOtherActivity(AppManager.getInstance().currentActivity());
                    startActivity(new Intent(AppManager.getInstance().currentActivity(), LoginActivity.class));
                    AppManager.getInstance().finishActivity(AppManager.getInstance().currentActivity());
                }
            });
            unauthorizedDialog.show();
        } else {
            tipDialog.dismiss();
            ToastyUtil.setNormalDanger(getApplicationContext(), error, Toast.LENGTH_SHORT);
        }
    }


    @Override
    public String getContent() {
        return moodText.getText().toString().trim();
    }

    @Override
    public List<String> getimages() {
        List<String> list = new ArrayList();
        list.add(VIDEOPATH);
        return list;
    }

    @Override
    protected void doOnBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            videoPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        super.doOnBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mPresenter = null;
        GSYVideoManager.releaseAllVideos();
    }
}
