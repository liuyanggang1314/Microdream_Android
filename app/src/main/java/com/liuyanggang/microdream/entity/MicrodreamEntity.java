package com.liuyanggang.microdream.entity;

import okhttp3.MediaType;

/**
 * @ClassName MicrodreamEntity
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/20
 * @Version 1.0
 */
public class MicrodreamEntity {
    /**
     * 域名
     */
    private static final String MICRODREAM_SERVER = "http://liuyanggang.com:8000/";

    /**
     * 登录
     */
    public static final String LOGINNOCODE = MICRODREAM_SERVER + "auth/loginnocode";
    /**
     * 注册
     */
    public static final String REGISTER = MICRODREAM_SERVER + "api/users/createNo";
    /**
     * 修改密码
     */
    public static final String CHANGEPASSWORD = MICRODREAM_SERVER + "api/users/updatePass";
    /**
     * 退出登录
     */
    public static final String LOGOUT = MICRODREAM_SERVER + "auth/logout";
    /**
     * 请求heard
     */
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    /**
     * 请求成功code
     */
    public static final String OK = "200";
    /**
     * 请求失败401 UNAUTHORIZED授权错误
     */
    public static final String UNAUTHORIZED = "401";
    /**
     * 请求错误
     */
    public static final String BAD_REQUEST = "400";
    /**
     * token
     */
    public static final String TOKEN_STRING = "token";

    /**
     * DrawerLayout边缘滑动距离
     */
    public static final Integer DRAWERLEFTEDGESIZEDP = 200;
    /**
     * 密码最低
     */
    public static final int PASSWORD_LESS = 6;
    /**
     * phone长度
     */
    public static final int PHONE_LENTH = 11;

    /**
     * 密码最高
     */
    public static final int PASSWORD_MORE = 16;
    /**
     * 授权状态码
     */
    public static final int RC_PERM = 123;

    /**
     * 正则表达式:验证手机号
     */
    public static final String REGEX_MOBILE = "^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$";

    /**
     * 正则表达式:验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * REA公匙
     */
    public static final String PUBLIC_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANL378k3RiZHWx5AfJqdH9xRNBmD9wGD\n" +
            "2iRe41HdTNF8RUhNnHit5NpMNtGL0NPTSSpPjjI1kJfVorRvaQerUgkCAwEAAQ==";
    /**
     * REA私匙
     */
    public static final String PRIVATE_KEY = "MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEA0vfvyTdGJkdbHkB8mp0f" +
            "3FE0GYP3AYPaJF7jUd1M0XxFSE2ceK3k2kw20YvQ09NJKk+OMjWQl9WitG9pB6tSCQIDAQABAkA2SimBrWC2/wvauBuYqjCFwLvYiRYqZK" +
            "ThUS3MZlebXJiLB+Ue/gUifAAKIg1avttUZsHBHrop4qfJCwAI0+YRAiEA+W3NK/RaXtnRqmoUUkb59zsZUBLpvZgQPfj1MhyHDz0CIQDYhsA" +
            "hPJ3mgS64NbUZmGWuuNKp5coY2GIj/zYDMJp6vQIgUueLFXv/eZ1ekgz2Oi67MNCk5jeTF2BurZqNLR3MSmUCIFT3Q6uHMtsB9Eha4u7hS31t" +
            "j1UWE+D+ADzp59MGnoftAiBeHT7gDMuqeJHPL4b+kC+gzV4FGTfhR9q3tTbklZkD2A==";
}
