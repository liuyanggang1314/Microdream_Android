package com.liuyanggang.microdream.callback;

import android.content.Context;
import android.widget.Toast;

import com.liuyanggang.microdream.utils.ToastyUtil;

import org.lzh.framework.updatepluginlib.base.CheckCallback;
import org.lzh.framework.updatepluginlib.base.DownloadCallback;
import org.lzh.framework.updatepluginlib.model.Update;

import java.io.File;

/**
 * @ClassName ToastCallback
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/6/17
 * @Version 1.0
 */
public class ToastCallback implements CheckCallback, DownloadCallback {
    Context context;

    public ToastCallback(Context context) {
        this.context = context;
    }

    private void show(String message) {
        ToastyUtil.setNormalPrimary(context, message, Toast.LENGTH_SHORT);
    }

    @Override
    public void onCheckStart() {

    }

    @Override
    public void hasUpdate(Update update) {
        show("发现新版本");
    }

    @Override
    public void noUpdate() {
        show("已是最新版本");
    }

    @Override
    public void onCheckError(Throwable t) {
        show("更新检查失败：" + t.getMessage());
    }

    @Override
    public void onUserCancel() {
        show("取消更新");
    }

    @Override
    public void onCheckIgnore(Update update) {
        show("忽略此版本更新");
    }

    @Override
    public void onDownloadStart() {
        show("开始下载");
    }

    @Override
    public void onDownloadComplete(File file) {
        show("下载完成");
    }

    @Override
    public void onDownloadProgress(long current, long total) {

    }

    @Override
    public void onDownloadError(Throwable t) {
        show("下载失败");
    }
}
