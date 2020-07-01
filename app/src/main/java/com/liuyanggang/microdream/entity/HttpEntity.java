package com.liuyanggang.microdream.entity;

import okhttp3.MediaType;

/**
 * @ClassName HttpEntity
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/27
 * @Version 1.0
 */
public class HttpEntity {
    /**
     * 域名
     */
    public static final String MICRODREAM_SERVER = "http://liuyanggang.com:8000/";
    /**
     * 图片域名
     */
    public static final String MICRODREAM_SERVER_IMG = MICRODREAM_SERVER + "avatar/";

    /**
     * 文件域名
     */
    public static final String MICRODREAM_SERVER_FILE = MICRODREAM_SERVER + "file/";

    /**
     * 文件域名
     */
    public static final String MICRODREAM_SERVER_MOOD = MICRODREAM_SERVER + "mood/";

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
     * 更新头像
     */
    public static final String UPDATE_AVATAR = MICRODREAM_SERVER + "api/users/updateAvatar";

    /**
     * 更新用户信息
     */
    public static final String UPDATE_USERINFO = MICRODREAM_SERVER + "api/user/new";

    /**
     * 心情列表
     */
    public static final String MOODLIST = MICRODREAM_SERVER + "api/mood";

    /**
     * 发表心情
     */
    public static final String SAVE_MOOD = MICRODREAM_SERVER + "api/mood";

    /**
     * 删除心情
     */
    public static final String DELETE_MOOD = MICRODREAM_SERVER + "api/mood";

    /**
     * 获取最新版本
     */
    public static final String LAST_VERSION = MICRODREAM_SERVER + "api/version";


    /**
     * 获取首页列表
     */
    public static final String INDEX_LIST = "https://www.liuyanggang.com/bbs/wp-json/wp/v2/posts";

    /**
     * 获取首页轮播图列表
     */
    public static final String INDEX_BANNER = "https://www.liuyanggang.com/bbs/wp-json/watch-life-net/v1/post/swipe";

    /**
     * 公务员考试列表
     */
    public static final String EXAMINATION = MICRODREAM_SERVER + "api/examination";
    /**
     * 公务员考试列表
     */
    public static final String ANNOUNCEMENT = "https://m.ynzp.com/km/publicnotice/type1/index.html?isapp=true";
    /**
     * 获取图片列表
     */
    public static final String IMAGE_LIST = MICRODREAM_SERVER + "api/android_img";
    /**
     * 根据type获取图片
     */
    public static final String IMAGE_BYTYPE = MICRODREAM_SERVER + "api/android_img/bytype";
    /**
     * 请求heard
     */
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    /**
     * 请求成功code
     */
    public static final int OK = 200;
    /**
     * CREATED成功code
     */
    public static final int CREATED_OK = 201;
    /**
     * 请求失败401 UNAUTHORIZED授权错误
     */
    public static final int UNAUTHORIZED_INT = 401;
    /**
     * 请求失败401 UNAUTHORIZED授权错误
     */
    public static final String UNAUTHORIZED_STRING = "401";
    /**
     * 请求错误
     */
    public static final int BAD_REQUEST = 400;

    /**
     * 请求超时
     */
    public static final int TIME_OUT = 500;
}
