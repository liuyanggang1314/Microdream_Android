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

import static com.liuyanggang.microdream.entity.MicrodreamEntity.JSON;
import static com.liuyanggang.microdream.entity.MicrodreamEntity.REGISTER;
import static com.liuyanggang.microdream.entity.MicrodreamEntity.UNAUTHORIZED;

/**
 * @ClassName RegisterIMode
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/23
 * @Version 1.0
 */
public class RegisteredIMode implements IModel {
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
                        if (code != 500) {
                            String str = response.body();
                            if (!str.equals("")) {
                                JSONObject jsonObject = new JSONObject(str);
                                String status = jsonObject.getStr("status");
                                String message = jsonObject.getStr("message");
                                if (UNAUTHORIZED.equals(status)) {
                                    //无token
                                } else {
                                    lisentener.onError(message);
                                }
                            } else {
                                lisentener.onSeccess();
                            }

                        } else {
                            lisentener.onError("请求超时");
                        }

                    }
                });
    }
}
