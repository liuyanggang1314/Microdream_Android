package com.liuyanggang.microdream.model;

import com.liuyanggang.microdream.base.BaseInitData;
import com.liuyanggang.microdream.callback.AbstractStringCallback;
import com.liuyanggang.microdream.model.Lisentener.ChangePasswordListener;
import com.liuyanggang.microdream.model.Lisentener.GetUserInfoLisentener;
import com.liuyanggang.microdream.model.Lisentener.LogoutLinstener;
import com.liuyanggang.microdream.utils.MMKVUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.Map;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import okhttp3.RequestBody;

import static com.liuyanggang.microdream.entity.MicrodreamEntity.CHANGEPASSWORD;
import static com.liuyanggang.microdream.entity.MicrodreamEntity.JSON;
import static com.liuyanggang.microdream.entity.MicrodreamEntity.LOGOUT;
import static com.liuyanggang.microdream.entity.MicrodreamEntity.REGISTER;
import static com.liuyanggang.microdream.entity.MicrodreamEntity.UNAUTHORIZED;

/**
 * @ClassName MainIModel
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/24
 * @Version 1.0
 */
public class MainIModel implements IModel {

    /**
     * 密码修改
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
                        if (code != 500) {
                            String str = response.body();

                            if (!str.equals("")) {
                                JSONObject jsonObject = new JSONObject(str);
                                String status = jsonObject.getStr("status");
                                String message = jsonObject.getStr("message");
                                if (UNAUTHORIZED.equals(status)) {
                                    //无token
                                    lisentener.onLogoutError(UNAUTHORIZED);
                                } else {
                                    lisentener.onLogoutError(message);
                                }
                            } else {
                                BaseInitData.removeData();
                                lisentener.onLogoutSeccess();
                            }
                        } else {
                            lisentener.onLogoutError("请求超时");
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
                        if (code != 500) {
                            String str = response.body();

                            if (!str.equals("")) {
                                JSONObject jsonObject = new JSONObject(str);
                                String status = jsonObject.getStr("status");
                                String message = jsonObject.getStr("message");
                                if (UNAUTHORIZED.equals(status)) {
                                    //无token
                                    lisentener.onChangePasswordError(UNAUTHORIZED);
                                } else {
                                    lisentener.onChangePasswordError(message);
                                }
                            } else {
                                lisentener.onChangePasswordSuccess();
                            }
                        } else {
                            lisentener.onChangePasswordError("请求超时");
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
                        if (code != 500) {
                            String str = response.body();
                            if (!str.equals("")) {
                                JSONObject jsonObject = new JSONObject(str);
                                String status = jsonObject.getStr("status");
                                String message = jsonObject.getStr("message");
                                if (status == null) {
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
                                } else if (UNAUTHORIZED.equals(status)) {
                                    //无token
                                    lisentener.onGetUserInfoError(UNAUTHORIZED);
                                } else {
                                    lisentener.onGetUserInfoError(message);
                                }
                            }
                        } else {
                            lisentener.onGetUserInfoError("请求超时");
                        }

                    }
                });
    }
}
