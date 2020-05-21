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
    public static final String MICRODREAM_SERVER = "http://192.168.0.102:8000/";

    public static final String LOGINNOCODE = MICRODREAM_SERVER + "auth/loginnocode";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String OK = "200";
    public static final String UNAUTHORIZED = "401";
    public static final String BAD_REQUEST = "400";
    public static final String TOKEN_STRING = "token";
    /**
     * 密码最低
     */
    public static final int PASSWORD_LESS = 6;
    /**
     * 密码最高
     */
    public static final int PASSWORD_MORE = 16;
    /**
     * 授权状态码
     */
    public static final int RC_PERM = 123;

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
