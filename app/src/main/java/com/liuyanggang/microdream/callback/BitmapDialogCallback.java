package com.liuyanggang.microdream.callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.view.Window;

import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.request.base.Request;

/**
 * @ClassName BitmapDialogCallback
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/19
 * @Version 1.0
 */
public abstract class BitmapDialogCallback extends BitmapCallback {

    private ProgressDialog dialog;

    public BitmapDialogCallback(Activity activity) {
        super(1000, 1000);
        dialog = new ProgressDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("请求网络中...");
    }

    @Override
    public void onStart(Request<Bitmap, ? extends Request> request) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onFinish() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
