package com.liuyanggang.microdream.utils;

import android.content.Context;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadListener;
import com.lzy.okserver.download.DownloadTask;

import java.io.File;

/**
 * @ClassName DownloadUtils
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/6/11
 * @Version 1.0
 */
public class DownloadUtils {
    public static void downloadimg(String url, Context context) {
        DownloadTask request = OkDownload.request(url, OkGo.get(url));
        request.register(new DownloadListener(url) {
            public void onError(Progress progress) {
            }

            public void onProgress(Progress progress) {
            }

            public void onRemove(Progress progress) {
            }

            public void onStart(Progress progress) {
            }

            public void onFinish(File file, Progress progress) {
                ToastyUtil.setNormalSuccess(context, "保存成功", Toast.LENGTH_SHORT);
            }
        }).save();
        request.start();
    }
}
