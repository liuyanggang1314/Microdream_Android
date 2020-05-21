package com.liuyanggang.microdream.model;

import android.util.Log;

import com.liuyanggang.microdream.callback.AbstractStringCallback;
import com.liuyanggang.microdream.entity.UserEntity;
import com.liuyanggang.microdream.model.Lisentener.LoginLisentener;
import com.liuyanggang.microdream.utils.MMKVUtils;
import com.liuyanggang.microdream.utils.RsaUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import okhttp3.RequestBody;

import static com.liuyanggang.microdream.entity.MicrodreamEntity.JSON;
import static com.liuyanggang.microdream.entity.MicrodreamEntity.LOGINNOCODE;
import static com.liuyanggang.microdream.entity.MicrodreamEntity.UNAUTHORIZED;

/**
 * @ClassName LoginIMode
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/19
 * @Version 1.0
 */
public class LoginIMode implements IModel {
    //model 负责数据以及业务逻辑。

    public void login(String username, String password, LoginLisentener lisentener) {
        if (lisentener == null) {
            return;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(RsaUtils.mEncrypt(password));
        RequestBody body = RequestBody.create(JSON, JSONUtil.toJsonStr(userEntity));


        OkGo.<String>post(LOGINNOCODE)
                .upRequestBody(body)
                .execute(new AbstractStringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        lisentener.onFails("网络请求错误");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        int code = response.code();
                        if (code != 500) {
                            String str = response.body();
                            Log.d("361", str);
                            JSONObject jsonObject = new JSONObject(str);
                            String status = jsonObject.getStr("status");
                            if (status == null) {
                                String token = jsonObject.getStr("token");
                                MMKVUtils.setStringInfo("token", token);
                                lisentener.onSeccess();
                            } else {
                                String message = jsonObject.getStr("message");
                                if (UNAUTHORIZED.equals(status)) {
                                    //无token
                                } else {
                                    lisentener.onFails(message);
                                }
                            }
                        } else {
                            lisentener.onFails("请求超时");
                        }

                    }
                });

    }
}