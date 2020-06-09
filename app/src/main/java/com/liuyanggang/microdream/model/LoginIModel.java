package com.liuyanggang.microdream.model;

import com.liuyanggang.microdream.callback.AbstractStringCallback;
import com.liuyanggang.microdream.entity.UserEntity;
import com.liuyanggang.microdream.model.lisentener.LoginLisentener;
import com.liuyanggang.microdream.utils.GenerateUserSig;
import com.liuyanggang.microdream.utils.MMKVUtil;
import com.liuyanggang.microdream.utils.RsaUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import okhttp3.RequestBody;

import static com.liuyanggang.microdream.entity.HttpEntity.JSON;
import static com.liuyanggang.microdream.entity.HttpEntity.LOGINNOCODE;
import static com.liuyanggang.microdream.entity.HttpEntity.OK;
import static com.liuyanggang.microdream.entity.HttpEntity.TIME_OUT;
import static com.liuyanggang.microdream.entity.HttpEntity.UNAUTHORIZED_INT;
import static com.liuyanggang.microdream.entity.HttpEntity.UNAUTHORIZED_STRING;

/**
 * @ClassName LoginIMode
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/19
 * @Version 1.0
 */
public class LoginIModel implements IModel {
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
                        String str = response.body();
                        switch (code) {
                            case OK:
                                JSONObject jsonObject = new JSONObject(str);
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
                                MMKVUtil.setBooleanInfo("enabled", enabled);
                                MMKVUtil.setStringInfo("createBy", createBy);
                                MMKVUtil.setStringInfo("updatedBy", updatedBy);
                                MMKVUtil.setStringInfo("createTime", createTime);
                                MMKVUtil.setStringInfo("updateTime", updateTime);
                                loginIM();
                                lisentener.onSeccess();
                                break;
                            case UNAUTHORIZED_INT:
                                lisentener.onError(UNAUTHORIZED_STRING);
                                break;
                            case TIME_OUT:
                                lisentener.onError("请求超时");
                                break;
                            default:
                                JSONObject jsonObject1 = new JSONObject(str);
                                String msg = jsonObject1.getStr("message");
                                lisentener.onError(msg);
                                break;
                        }
                    }
                });
    }

    private void loginIM() {
        String userSig = GenerateUserSig.genTestUserSig(MMKVUtil.getStringInfo("username"));
        TUIKit.login(MMKVUtil.getStringInfo("username"), userSig, new IUIKitCallBack() {
            @Override
            public void onError(String module, final int code, final String desc) {

            }

            @Override
            public void onSuccess(Object data) {

            }
        });
    }
}