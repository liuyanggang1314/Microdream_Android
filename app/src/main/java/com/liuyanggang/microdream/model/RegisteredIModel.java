package com.liuyanggang.microdream.model;

import com.liuyanggang.microdream.callback.AbstractStringCallback;
import com.liuyanggang.microdream.entity.RegisterJson;
import com.liuyanggang.microdream.entity.UserEntity;
import com.liuyanggang.microdream.model.lisentener.RegisteredListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import okhttp3.RequestBody;

import static com.liuyanggang.microdream.entity.HttpEntity.CREATED_OK;
import static com.liuyanggang.microdream.entity.HttpEntity.JSON;
import static com.liuyanggang.microdream.entity.HttpEntity.OK;
import static com.liuyanggang.microdream.entity.HttpEntity.REGISTER;
import static com.liuyanggang.microdream.entity.HttpEntity.TIME_OUT;
import static com.liuyanggang.microdream.entity.HttpEntity.UNAUTHORIZED_INT;
import static com.liuyanggang.microdream.entity.HttpEntity.UNAUTHORIZED_STRING;

/**
 * @ClassName RegisterIMode
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/23
 * @Version 1.0
 */
public class RegisteredIModel implements IModel {
    public void register(UserEntity userEntity, RegisteredListener lisentener) {
        if (lisentener == null) {
            return;
        }
        userEntity.setRoles(RegisterJson.getRoles());
        userEntity.setJobs(RegisterJson.getJob());
        userEntity.setDept(RegisterJson.getDept());
        RequestBody body = RequestBody.create(JSON, JSONUtil.toJsonStr(userEntity));

        OkGo.<String>post(REGISTER)
                .upRequestBody(body)
                .execute(new AbstractStringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        lisentener.onError("网络请求错误");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        int code = response.code();
                        String str = response.body();
                        switch (code) {
                            case OK:
                                lisentener.onSeccess();
                                break;
                            case CREATED_OK:
                                lisentener.onSeccess();
                                break;
                            case UNAUTHORIZED_INT:
                                lisentener.onError(UNAUTHORIZED_STRING);
                                break;
                            case TIME_OUT:
                                lisentener.onError("请求超时");
                                break;
                            default:
                                JSONObject jsonObject = new JSONObject(str);
                                String msg = jsonObject.getStr("message");
                                lisentener.onError(msg);
                                break;
                        }
                    }
                });
    }
}
