package com.liuyanggang.microdream.adapter;

import android.Manifest;
import android.os.Environment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.liuyanggang.microdream.R;
import com.liuyanggang.microdream.entity.HomepageEntity;
import com.liuyanggang.microdream.manager.AppManager;
import com.liuyanggang.microdream.utils.ImageUtils;
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper;
import com.tamsiree.rxkit.RxFileTool;

import java.io.File;
import java.util.List;
import java.util.Objects;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import cn.hutool.core.thread.ThreadUtil;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.liuyanggang.microdream.entity.HttpEntity.MICRODREAM_SERVER_IMG;
import static com.liuyanggang.microdream.entity.HttpEntity.MICRODREAM_SERVER_MOOD;
import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

/**
 * @ClassName HomepageAdapter
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/25
 * @Version 1.0
 */
public class HomepageAdapter extends BaseQuickAdapter<HomepageEntity, BaseViewHolder> implements LoadMoreModule, BGANinePhotoLayout.Delegate {
    private BGANinePhotoLayout mCurrentClickNpl;
    private static final int PRC_PHOTO_PREVIEW = 1;
    public final static String TAG = "HomepageAdapter";
    private GSYVideoHelper smallVideoHelper;
    private GSYVideoHelper.GSYVideoHelperBuilder gsySmallVideoHelperBuilder;

    public HomepageAdapter(int layoutResId, @Nullable List<HomepageEntity> data) {
        super(layoutResId, data);
        addChildClickViewIds(R.id.delete);
        addChildLongClickViewIds(R.id.text);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomepageEntity item) {
        helper.setText(R.id.nikeName, item.getNikeName())
                .setText(R.id.text, item.getContent())
                .setText(R.id.update_time, item.getCreatTime());
        if (item.isDelete()) {
            helper.getView(R.id.delete).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.delete).setVisibility(View.GONE);
        }
        Glide.with(getContext()).load(MICRODREAM_SERVER_IMG + item.getAvatar())
                .placeholder(R.drawable.image_fill)
                .error(R.drawable.logo)
                .into((ImageView) helper.getView(R.id.avatar));
        mCurrentClickNpl = helper.getView(R.id.npl_item_moment_photos);

        if (item.getImages() != null) {
            mCurrentClickNpl.setVisibility(View.VISIBLE);
            mCurrentClickNpl.setDelegate(this);
            mCurrentClickNpl.setData(item.getImages());
        } else {
            mCurrentClickNpl.setVisibility(View.GONE);
        }
        RelativeLayout relativeLayout = helper.getView(R.id.relativeLayout);
        FrameLayout listItemContainer = helper.getView(R.id.list_item_container);
        ImageView listItemBtn = helper.getView(R.id.list_item_btn);
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        String url;
        if (item.getVideo() != null) {
            relativeLayout.setVisibility(View.VISIBLE);
            if (item.getVideo().contains("http")) {
                url = item.getVideo();
            } else {
                url = MICRODREAM_SERVER_MOOD + item.getVideo();
            }

            if (RxFileTool.fileExists(getContext().getCacheDir() + "/video-img/" + item.getId() + ".jpg")) {
                File file = new File(getContext().getCacheDir() + "/video-img/" + item.getId() + ".jpg");
                Glide.with(getContext()).load(file)
                        .centerCrop()
                        .placeholder(R.drawable.image_fill)
                        .error(R.drawable.logo)
                        .into(imageView);
            } else {
                ThreadUtil.execAsync(() -> {
                    String videoImg = ImageUtils.bitmap2File(getContext(), url, item.getId());
                    runOnUiThread(() -> Glide.with(getContext()).load(videoImg)
                            .centerCrop()
                            .placeholder(R.drawable.image_fill)
                            .error(R.drawable.logo)
                            .into(imageView));
                });

            }

        } else {
            url = null;
            relativeLayout.setVisibility(View.GONE);
        }
        smallVideoHelper.addVideoPlayer(helper.getAdapterPosition(), imageView, TAG, listItemContainer, listItemBtn);

        listItemBtn.setOnClickListener(v -> {
            smallVideoHelper.setPlayPositionAndTag(helper.getAdapterPosition(), TAG);
            notifyDataSetChanged();
            gsySmallVideoHelperBuilder.setVideoTitle(item.getNikeName())
                    .setUrl(url);
            smallVideoHelper.startPlay();
            smallVideoHelper.getGsyVideoPlayer().getTitleTextView().setVisibility(View.VISIBLE);
        });

    }

    public void setVideoHelper(GSYVideoHelper smallVideoHelper, GSYVideoHelper.GSYVideoHelperBuilder gsySmallVideoHelperBuilder) {
        this.smallVideoHelper = smallVideoHelper;
        this.gsySmallVideoHelperBuilder = gsySmallVideoHelperBuilder;
    }

    @Override
    public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
        mCurrentClickNpl = ninePhotoLayout;
        photoPreviewWrapper();
    }

    @Override
    public void onClickExpand(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
        ninePhotoLayout.setIsExpand(true);
        ninePhotoLayout.flushItems();
    }

    @AfterPermissionGranted(PRC_PHOTO_PREVIEW)
    private void photoPreviewWrapper() {
        if (mCurrentClickNpl == null) {
            return;
        }
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            final String galleryPath = Environment.getExternalStorageDirectory()
                    + File.separator + Environment.DIRECTORY_DCIM
                    + File.separator;
            File downloadDir = new File(galleryPath, "Microdream");
            BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(getContext());
            //保存图片
            photoPreviewIntentBuilder.saveImgDir(downloadDir);

            if (mCurrentClickNpl.getItemCount() == 1) {
                // 预览单张图片
                photoPreviewIntentBuilder.previewPhoto(mCurrentClickNpl.getCurrentClickItem());
            } else if (mCurrentClickNpl.getItemCount() > 1) {
                // 预览多张图片
                photoPreviewIntentBuilder.previewPhotos(mCurrentClickNpl.getData())
                        .currentPosition(mCurrentClickNpl.getCurrentClickItemPosition()); // 当前预览图片的索引
            }
            getContext().startActivity(photoPreviewIntentBuilder.build());
        } else {
            EasyPermissions.requestPermissions(Objects.requireNonNull(AppManager.getInstance().currentActivity()), "图片预览需要以下权限:\n\n1.访问设备上的照片", PRC_PHOTO_PREVIEW, perms);
        }
    }


}
