package com.liuyanggang.microdream.model;

import com.liuyanggang.microdream.callback.AbstractStringCallback;
import com.liuyanggang.microdream.manager.AppDataManager;
import com.liuyanggang.microdream.model.lisentener.ChangePasswordListener;
import com.liuyanggang.microdream.model.lisentener.GetUserInfoLisentener;
import com.liuyanggang.microdream.model.lisentener.LogoutLinstener;
import com.liuyanggang.microdream.utils.MMKVUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.Map;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import okhttp3.RequestBody;

import static com.liuyanggang.microdream.entity.HttpEntity.CHANGEPASSWORD;
import static com.liuyanggang.microdream.entity.HttpEntity.JSON;
import static com.liuyanggang.microdream.entity.HttpEntity.LOGOUT;
import static com.liuyanggang.microdream.entity.HttpEntity.OK;
import static com.liuyanggang.microdream.entity.HttpEntity.REGISTER;
import static com.liuyanggang.microdream.entity.HttpEntity.TIME_OUT;
import static com.liuyanggang.microdream.entity.HttpEntity.UNAUTHORIZED_INT;
import static com.liuyanggang.microdream.entity.HttpEntity.UNAUTHORIZED_STRING;

/**
 * @ClassName MainIModel
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/24
 * @Version 1.0
 */
public class MainIModel implements IModel {

    /**
     * 退出登录
     *
     * @param lisentener
     */
    public void logout(LogoutLinstener lisentener) {
        if (lisentener == null) {
            return;
        }
        OkGo.<String>delete(LOGOUT)
                .execute(new AbstractStringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        lisentener.onLogoutError("网络请求错误");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        int code = response.code();
                        String str = response.body();
                        switch (code) {
                            case OK:
                                AppDataManager.removeData();
                                lisentener.onLogoutSeccess();
                                break;
                            case UNAUTHORIZED_INT:
                                lisentener.onLogoutError(UNAUTHORIZED_STRING);
                                break;
                            case TIME_OUT:
                                lisentener.onLogoutError("请求超时");
                                break;
                            default:
                                JSONObject jsonObject = new JSONObject(str);
                                String msg = jsonObject.getStr("message");
                                lisentener.onLogoutError(msg);
                                break;
                        }

                    }
                });
    }

    /**
     * 密码修改
     *
     * @param lisentener
     */
    public void chagePawword(Map<String, String> passwordMap, ChangePasswordListener lisentener) {
        if (lisentener == null) {
            return;
        }
        RequestBody body = RequestBody.create(JSON, JSONUtil.toJsonStr(passwordMap));
        OkGo.<String>post(CHANGEPASSWORD)
                .upRequestBody(body)
                .execute(new AbstractStringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        lisentener.onChangePasswordError("网络请求错误");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        int code = response.code();
                        String str = response.body();
                        switch (code) {
                            case OK:
                                lisentener.onChangePasswordSuccess();
                                break;
                            case UNAUTHORIZED_INT:
                                lisentener.onChangePasswordError(UNAUTHORIZED_STRING);
                                break;
                            case TIME_OUT:
                                lisentener.onChangePasswordError("请求超时");
                                break;
                            default:
                                JSONObject jsonObject = new JSONObject(str);
                                String msg = jsonObject.getStr("message");
                                lisentener.onChangePasswordError(msg);
                                break;
                        }
                    }
                });
    }

    /**
     * 获取用户信息
     *
     * @param lisentener
     */
    public void getUserInfo(GetUserInfoLisentener lisentener) {
        if (lisentener == null) {
            return;
        }
        OkGo.<String>get(REGISTER)
                .execute(new AbstractStringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        lisentener.onGetUserInfoError("网络请求错误");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        int code = response.code();
                        String str = response.body();
                        switch (code) {
                            case OK:
                                JSONObject jsonObject = new JSONObject(str);
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
                                lisentener.onGetUserInfoSuccess();
                                break;
                            case UNAUTHORIZED_INT:
                                lisentener.onGetUserInfoError(UNAUTHORIZED_STRING);
                                break;
                            case TIME_OUT:
                                lisentener.onGetUserInfoError("请求超时");
                                break;
                            default:
                                JSONObject jsonObject1 = new JSONObject(str);
                                String msg = jsonObject1.getStr("message");
                                lisentener.onGetUserInfoError(msg);
                                break;
                        }
                    }
                });
    }
}
