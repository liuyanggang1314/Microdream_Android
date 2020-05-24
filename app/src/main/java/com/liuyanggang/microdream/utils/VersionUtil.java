package com.liuyanggang.microdream.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.liuyanggang.microdream.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @ClassName VersionUtils
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/19
 * @Version 1.0
 */
public class VersionUtil {
    public static String getversion(Context context) {
        //版本
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        assert packInfo != null;
        String version = packInfo.versionName;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Version. ");
        stringBuffer.append(version);
        stringBuffer.append(".");
        return String.valueOf(stringBuffer);
    }

    public static String getCopyrightInfo(Context context) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.CHINA);
        String currentYear = dateFormat.format(new java.util.Date());
        return String.format(context.getResources().getString(R.string.about_copyright), currentYear);
    }
}
