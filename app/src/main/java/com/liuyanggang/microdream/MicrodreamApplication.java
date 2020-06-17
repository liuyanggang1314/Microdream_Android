package com.liuyanggang.microdream;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.liuyanggang.microdream.manager.AppUpgradeManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okserver.OkDownload;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.mmkv.MMKV;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.config.CustomFaceConfig;
import com.tencent.qcloud.tim.uikit.config.GeneralConfig;
import com.tencent.qcloud.tim.uikit.config.TUIKitConfigs;

import java.io.File;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

import static com.liuyanggang.microdream.entity.MicrodreamEntity.TUIKIT_SDKAPPID;

/**
 * @ClassName MicrodreamApplication
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/18
 * @Version 1.0
 */
public class MicrodreamApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initMmkv();
        initQmui();
        initOkgo();
        initUpdateConfig();
        initTUIKit();
    }

    private void initTUIKit() {
        // 配置 Config，请按需配置
        TUIKitConfigs configs = TUIKit.getConfigs();
        configs.setSdkConfig(new TIMSdkConfig(TUIKIT_SDKAPPID));
        configs.setCustomFaceConfig(new CustomFaceConfig());
        configs.setGeneralConfig(new GeneralConfig());
        TUIKit.init(this, TUIKIT_SDKAPPID, configs);
    }

    /**
     * 初始化MMKV
     */
    private void initMmkv() {
        MMKV.initialize(this);
    }

    /**
     * 初始化OKGO
     */
    private void initOkgo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.HEADERS);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        HttpHeaders headers = new HttpHeaders();
        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
        headers.put("Accept", "application/json, text/plain, */*");
        OkGo.getInstance().init(this)
                //建议设置OkHttpClient，不设置将使用默认的
                .setOkHttpClient(builder.build())
                //全局公共头
                .addCommonHeaders(headers)
                //.setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)//全局统一缓存模式，默认不使用缓存，可以不传
                //全局统一超时重连次数，默认为三次
                .setRetryCount(3);
        OkDownload instance = OkDownload.getInstance();
        String galleryPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator;
        //存储路径
        String stringBuilder = galleryPath + "/Microdream/";
        instance.setFolder(stringBuilder);
        //最大下载线程
        instance.getThreadPool().setCorePoolSize(3);
    }

    /**
     * 初始化QMUI
     */
    private void initQmui() {
        QMUISwipeBackActivityManager.init(this);
    }

    /**
     * 初始化APP更新
     */
    private void initUpdateConfig() {
        AppUpgradeManager.initUpgrade(getApplicationContext());
    }

}
