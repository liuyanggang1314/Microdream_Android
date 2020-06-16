package com.liuyanggang.microdream.webview;

import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebView;

import com.just.agentweb.MiddlewareWebChromeBase;

/**
 * @ClassName MiddlewareChromeClient
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/6/16
 * @Version 1.0
 */
public class MiddlewareChromeClient extends MiddlewareWebChromeBase {
    public MiddlewareChromeClient() {
    }
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        Log.i("Info","onJsAlert:"+url);
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        Log.i("Info","onProgressChanged:");
    }
}