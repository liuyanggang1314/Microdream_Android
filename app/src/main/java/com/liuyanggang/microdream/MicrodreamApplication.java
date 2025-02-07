package com.liuyanggang.microdream;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.liuyanggang.microdream.manager.AppUpgradeManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okserver.OkDownload;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.shuyu.gsyvideoplayer.cache.CacheFactory;
import com.shuyu.gsyvideoplayer.cache.ProxyCacheManager;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;
import com.shuyu.gsyvideoplayer.player.SystemPlayerManager;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
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
        initXG();
        initGSYVideo();
    }

    private void initGSYVideo() {
        GSYVideoType.enableMediaCodec();
        GSYVideoType.enableMediaCodecTexture();
        PlayerFactory.setPlayManager(SystemPlayerManager.class);//系统模式
        CacheFactory.setCacheManager(ProxyCacheManager.class);//代理缓存模式，支持所有模式，不支持m3u8等
    }

    private void initXG() {
        XGPushConfig.enableDebug(this, false);
        XGPushManager.registerPush(this, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                //token在设备卸载重装的时候有可能会变
                Log.d("TPush", "注册成功，设备token为：" + data);
            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
            }
        });
        //小米推送
        XGPushConfig.setMiPushAppId(this, "2882303761518457749");
        XGPushConfig.setMiPushAppKey(this, "5311845716749");
        //魅族
        XGPushConfig.setMzPushAppId(this, "132066");
        XGPushConfig.setMzPushAppKey(this, "1c1e86fdf85d4e89a3ffae841891e0c5");
        //打开第三方推送
        XGPushConfig.enableOtherPush(getApplicationContext(), true);
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
