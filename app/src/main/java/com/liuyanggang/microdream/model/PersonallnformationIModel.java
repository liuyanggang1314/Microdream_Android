package com.liuyanggang.microdream.model;

import com.liuyanggang.microdream.callback.AbstractStringCallback;
import com.liuyanggang.microdream.entity.UserEntity;
import com.liuyanggang.microdream.model.lisentener.PersonallnformationAvatarListener;
import com.liuyanggang.microdream.model.lisentener.PersonallnformationListener;
import com.liuyanggang.microdream.utils.MMKVUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.io.File;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.liuyanggang.microdream.entity.HttpEntity.JSON;
import static com.liuyanggang.microdream.entity.HttpEntity.OK;
import static com.liuyanggang.microdream.entity.HttpEntity.TIME_OUT;
import static com.liuyanggang.microdream.entity.HttpEntity.UNAUTHORIZED_INT;
import static com.liuyanggang.microdream.entity.HttpEntity.UNAUTHORIZED_STRING;
import static com.liuyanggang.microdream.entity.HttpEntity.UPDATE_AVATAR;
import static com.liuyanggang.microdream.entity.HttpEntity.UPDATE_USERINFO;

/**
 * @ClassName PersonallnformationIModel
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/27
 * @Version 1.0
 */
public class PersonallnformationIModel implements IModel {
    /**
     * 头像更新
     *
     * @param avatarPath
     * @param lisentener
     */
    public void updateAvatar(String avatarPath, PersonallnformationAvatarListener lisentener) {
        if (lisentener == null) {
            return;
        }
        MultipartBody.Builder body = new MultipartBody.Builder().setType(MultipartBody.FORM);
        File file = new File(avatarPath); //生成文件
        body.addFormDataPart( //给Builder添加上传的文件
                "avatar",  //请求的名字
                file.getName(), //文件的文字，服务器端用来解析的
                RequestBody.create(MediaType.parse("file/*"), file) //创建RequestBody，把上传的文件放入
        );
        RequestBody formBody = body.build();
        OkGo.<String>post(UPDATE_AVATAR)
                .upRequestBody(formBody)
                .execute(new AbstractStringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        lisentener.onPersonallformationAvatarError("网络请求错误");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        int code = response.code();
                        String str = response.body();
                        switch (code) {
                            case OK:
                                JSONObject jsonObject = new JSONObject(str);
                                String avatarName = jsonObject.getStr("avatar");
                                MMKVUtil.setStringInfo("avatarName", avatarName);
                                lisentener.onPersonallformationAvatarSeccess(avatarName);
                                break;
                            case UNAUTHORIZED_INT:
                                lisentener.onPersonallformationAvatarError(UNAUTHORIZED_STRING);
                                break;
                            case TIME_OUT:
                                lisentener.onPersonallformationAvatarError("请求超时");
                                break;
                            default:
                                JSONObject jsonObject1 = new JSONObject(str);
                                String msg = jsonObject1.getStr("message");
                                lisentener.onPersonallformationAvatarError(msg);
                                break;
                        }
                    }
                });
    }

    public void updateUser(UserEntity userEntity, PersonallnformationListener lisentener) {
        if (lisentener == null) {
            return;
        }
        RequestBody body = RequestBody.create(JSON, JSONUtil.toJsonStr(userEntity));
        OkGo.<String>post(UPDATE_USERINFO)
                .upRequestBody(body)
                .execute(new AbstractStringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        lisentener.onPersonallformationError("网络请求错误");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        int code = response.code();
                        String str = response.body();
                        switch (code) {
                            case OK:
                                lisentener.onPersonallformationSeccess();
                                break;
                            case UNAUTHORIZED_INT:
                                lisentener.onPersonallformationError(UNAUTHORIZED_STRING);
                                break;
                            case TIME_OUT:
                                lisentener.onPersonallformationError("请求超时");
                                break;
                            default:
                                JSONObject jsonObject = new JSONObject(str);
                                String msg = jsonObject.getStr("message");
                                lisentener.onPersonallformationError(msg);
                                break;
                        }
                    }
                });
    }
}
