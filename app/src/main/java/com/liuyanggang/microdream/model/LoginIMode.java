package com.liuyanggang.microdream.model;

import com.liuyanggang.microdream.callback.AbstractStringCallback;
import com.liuyanggang.microdream.entity.UserEntity;
import com.liuyanggang.microdream.model.Lisentener.LoginLisentener;
import com.liuyanggang.microdream.utils.MMKVUtil;
import com.liuyanggang.microdream.utils.RsaUtil;
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

    public void login(String username, String password, boolean isRemember, LoginLisentener lisentener) {
        if (lisentener == null) {
            return;
        }
        if (isRemember) {
            MMKVUtil.setBooleanInfo("isRememberMe", true);
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(RsaUtil.mEncrypt(password));
        RequestBody body = RequestBody.create(JSON, JSONUtil.toJsonStr(userEntity));

        OkGo.<String>post(LOGINNOCODE)
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
                                if (status == null) {
                                    String token = jsonObject.getStr("token");
                                    JSONObject user = jsonObject.getJSONObject("user");
                                    JSONObject userInfo = user.getJSONObject("user");
                                    String username = userInfo.getStr("username");
                                    String nickName = userInfo.getStr("nickName");
                                    String email = userInfo.getStr("email");
                                    String phone = userInfo.getStr("phone");
                                    String gender = userInfo.getStr("gender");
                                    String avatarName = userInfo.getStr("avatarName");
                                    String avatarPath = userInfo.getStr("avatarPath");
                                    String pwdResetTime = userInfo.getStr("pwdResetTime");
                                    String enabled = userInfo.getStr("enabled");
                                    String createBy = userInfo.getStr("createBy");
                                    String updatedBy = userInfo.getStr("updatedBy");
                                    String createTime = userInfo.getStr("createTime");
                                    String updateTime = userInfo.getStr("updateTime");


                                    MMKVUtil.setStringInfo("token", token);
                                    MMKVUtil.setStringInfo("username", username);
                                    MMKVUtil.setStringInfo("nickName", nickName);
                                    MMKVUtil.setStringInfo("email", email);
                                    MMKVUtil.setStringInfo("phone", phone);
                                    MMKVUtil.setStringInfo("gender", gender);
                                    MMKVUtil.setStringInfo("avatarName", avatarName);
                                    MMKVUtil.setStringInfo("avatarPath", avatarPath);
                                    MMKVUtil.setStringInfo("pwdResetTime", pwdResetTime);
                                    MMKVUtil.setStringInfo("enabled", enabled);
                                    MMKVUtil.setStringInfo("createBy", createBy);
                                    MMKVUtil.setStringInfo("updatedBy", updatedBy);
                                    MMKVUtil.setStringInfo("createTime", createTime);
                                    MMKVUtil.setStringInfo("updateTime", updateTime);
                                    lisentener.onSeccess();
                                } else {
                                    String message = jsonObject.getStr("message");
                                    if (UNAUTHORIZED.equals(status)) {
                                        //无token
                                    } else {
                                        lisentener.onError(message);
                                    }
                                }
                            }

                        } else {
                            lisentener.onError("请求超时");
                        }

                    }
                });

    }
}