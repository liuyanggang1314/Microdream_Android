package com.liuyanggang.microdream.base;

import com.liuyanggang.microdream.utils.MMKVUtil;

/**
 * @ClassName BaseInitData
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/25
 * @Version 1.0
 */
public class BaseInitData {
    /**
     * 初始化系统数据
     */
    public static void initData() {
        MMKVUtil.setStringInfo("token", "");
        MMKVUtil.setStringInfo("username", "");
        MMKVUtil.setStringInfo("nickName", "");
        MMKVUtil.setStringInfo("email", "");
        MMKVUtil.setStringInfo("phone", "");
        MMKVUtil.setStringInfo("gender", "");
        MMKVUtil.setStringInfo("avatarName", "");
        MMKVUtil.setStringInfo("avatarPath", "");
        MMKVUtil.setStringInfo("pwdResetTime", "");
        MMKVUtil.setBooleanInfo("enabled", "");
        MMKVUtil.setStringInfo("createBy", "");
        MMKVUtil.setStringInfo("updatedBy", "");
        MMKVUtil.setStringInfo("createTime", "");
        MMKVUtil.setStringInfo("updateTime", "");
        MMKVUtil.setBooleanInfo("isRememberMe", false);
    }

    /**
     * 移除系统数据
     */
    public static void removeData() {
        MMKVUtil.removeInfo("token");
        MMKVUtil.removeInfo("username");
        MMKVUtil.removeInfo("nickName");
        MMKVUtil.removeInfo("email");
        MMKVUtil.removeInfo("phone");
        MMKVUtil.removeInfo("gender");
        MMKVUtil.removeInfo("avatarName");
        MMKVUtil.removeInfo("avatarPath");
        MMKVUtil.removeInfo("pwdResetTime");
        MMKVUtil.removeInfo("enabled");
        MMKVUtil.removeInfo("createBy");
        MMKVUtil.removeInfo("updatedBy");
        MMKVUtil.removeInfo("createTime");
        MMKVUtil.removeInfo("updateTime");
        MMKVUtil.removeInfo("isRememberMe");
    }
}
