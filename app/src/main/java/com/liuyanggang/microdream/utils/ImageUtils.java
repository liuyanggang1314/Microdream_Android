package com.liuyanggang.microdream.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * @ClassName ImageUtils
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/7/7
 * @Version 1.0
 */
public class ImageUtils {
    /**
     * Bitmap保存成File
     *
     * @param videoUrl input videoUrl
     * @param name   output file's name
     * @return String output file's path
     */

    public static String bitmap2File(Context context, String videoUrl, Long name) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime(100000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }

        File f = new File(context.getCacheDir() + "/video-img/");
        if (!f.exists()) {
            f.mkdirs();
        }
        File file = new File(f, name + ".jpg");
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            return "错误";
        }
        return file.getAbsolutePath();
    }
}
