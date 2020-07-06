package com.liuyanggang.microdream.model;

import com.liuyanggang.microdream.callback.AbstractStringCallback;
import com.liuyanggang.microdream.model.lisentener.MoodSaveListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.List;

import cn.hutool.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.liuyanggang.microdream.entity.HttpEntity.OK;
import static com.liuyanggang.microdream.entity.HttpEntity.SAVE_MOOD;
import static com.liuyanggang.microdream.entity.HttpEntity.SAVE_MOODVIDEO;
import static com.liuyanggang.microdream.entity.HttpEntity.TIME_OUT;
import static com.liuyanggang.microdream.entity.HttpEntity.UNAUTHORIZED_INT;
import static com.liuyanggang.microdream.entity.HttpEntity.UNAUTHORIZED_STRING;

/**
 * @ClassName MoodEditIModel
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/28
 * @Version 1.0
 */
public class MoodEditIModel implements IModel {

    public void onSaveMood(String content, List<String> images, MoodSaveListener lisentener) {
        if (lisentener == null) {
            return;
        }
        MultipartBody.Builder mbody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (int i = 0; i < images.size(); i++) { //对文件进行遍历
            File file = new File(images.get(i)); //生成文件
            mbody.addFormDataPart( //给Builder添加上传的文件
                    "files",  //请求的名字
                    file.getName(), //文件的文字，服务器端用来解析的
                    RequestBody.create(MediaType.parse("file/*"), file) //创建RequestBody，把上传的文件放入
            );
        }
        mbody.addFormDataPart("content", content);
        RequestBody formBody = mbody.build();
        OkGo.<String>post(SAVE_MOOD)
                .upRequestBody(formBody)
                .execute(new AbstractStringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        lisentener.onModdSavaError("网络请求错误");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        int code = response.code();
                        String str = response.body();
                        switch (code) {
                            case OK:
                                lisentener.onMoodSaveSueccess();
                                break;
                            case UNAUTHORIZED_INT:
                                lisentener.onModdSavaError(UNAUTHORIZED_STRING);
                                break;
                            case TIME_OUT:
                                lisentener.onModdSavaError("请求超时");
                                break;
                            default:
                                JSONObject jsonObject1 = new JSONObject(str);
                                String msg = jsonObject1.getStr("message");
                                lisentener.onModdSavaError(msg);
                                break;
                        }
                    }
                });
    }
    public void onSaveMoodVideo(String content, List<String> images, MoodSaveListener lisentener) {
        if (lisentener == null) {
            return;
        }
        MultipartBody.Builder mbody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        File file = new File(images.get(0)); //生成文件
        mbody.addFormDataPart( //给Builder添加上传的文件
                "file",  //请求的名字
                file.getName(), //文件的文字，服务器端用来解析的
                RequestBody.create(MediaType.parse("file/*"), file) //创建RequestBody，把上传的文件放入
        );
        mbody.addFormDataPart("content", content);
        RequestBody formBody = mbody.build();
        OkGo.<String>post(SAVE_MOODVIDEO)
                .upRequestBody(formBody)
                .execute(new AbstractStringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        lisentener.onModdSavaError("网络请求错误");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        int code = response.code();
                        String str = response.body();
                        switch (code) {
                            case OK:
                                lisentener.onMoodSaveSueccess();
                                break;
                            case UNAUTHORIZED_INT:
                                lisentener.onModdSavaError(UNAUTHORIZED_STRING);
                                break;
                            case TIME_OUT:
                                lisentener.onModdSavaError("请求超时");
                                break;
                            default:
                                JSONObject jsonObject1 = new JSONObject(str);
                                String msg = jsonObject1.getStr("message");
                                lisentener.onModdSavaError(msg);
                                break;
                        }
                    }
                });
    }
}
