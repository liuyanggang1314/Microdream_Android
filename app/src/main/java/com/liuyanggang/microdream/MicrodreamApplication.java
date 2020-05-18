package com.liuyanggang.microdream;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okserver.OkDownload;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;

import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * @ClassName MicrodreamApplication
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/18
 * @Version 1.0
 */
public class MicrodreamApplication extends Application {
    private static MicrodreamApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;//存储引用
        initQmui();
        initOkgo();
    }

    private void initOkgo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())//建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)//全局统一缓存模式，默认不使用缓存，可以不传
                .setRetryCount(3);//全局统一超时重连次数，默认为三次
        OkDownload instance = OkDownload.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getCacheDir());
        stringBuilder.append("/microdream_file/");
        //存储路径
        instance.setFolder(stringBuilder.toString());
        //最大下载线程
        instance.getThreadPool().setCorePoolSize(3);
    }

    private void initQmui() {
        QMUISwipeBackActivityManager.init(this);
    }

    public static MicrodreamApplication getInstance() {
        return instance;
    }
}
