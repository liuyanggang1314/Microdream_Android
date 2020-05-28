package com.liuyanggang.microdream.manager;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.base.UpdateParser;
import org.lzh.framework.updatepluginlib.model.Update;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

import static com.liuyanggang.microdream.entity.HttpEntity.LAST_VERSION;
import static com.liuyanggang.microdream.entity.HttpEntity.MICRODREAM_SERVER_FILE;

/**
 * @ClassName AppUpdateManager
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/28
 * @Version 1.0
 */
public class AppUpgradeManager {
    public static void initUpgrade() {
        UpdateConfig.getConfig()
                // 必填：数据更新接口,url与checkEntity两种方式任选一种填写
                .setUrl(LAST_VERSION)
//                .setCheckEntity(new CheckEntity().setMethod(HttpMethod.GET).setUrl("http://www.baidu.com"))
                // 必填：用于从数据更新接口获取的数据response中。解析出Update实例。以便框架内部处理
                .setUpdateParser(new UpdateParser() {
                    @Override
                    public Update parse(String response) throws Exception {
                        /* 此处根据上面url或者checkEntity设置的检查更新接口的返回数据response解析出
                         * 一个update对象返回即可。更新启动时框架内部即可根据update对象的数据进行处理
                         */
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject object = (JSONObject) jsonArray.get(0);
                        Update update = new Update();
                        // 此apk包的下载地址
                        update.setUpdateUrl(MICRODREAM_SERVER_FILE + object.getStr("updateUrl"));
                        // 此apk包的版本号
                        update.setVersionCode(object.getInt("updateVerCode"));
                        // 此apk包的版本名称
                        update.setVersionName(object.getStr("updateVerName"));
                        // 此apk包的更新内容
                        update.setUpdateContent(object.getStr("updateContent"));
                        // 此apk包是否为强制更新
                        update.setForced(false);
                        // 是否显示忽略此次版本更新按钮
                        update.setIgnore(object.getBool("ignoreAble", false));
                        return update;
                    }
                });
    }

    /**
     * 点击更新
     */
    public static void check() {
        UpdateBuilder.create().check();
    }

    /**
     * 后台更新
     */
    public static void checkTask() {
        UpdateBuilder task = UpdateBuilder.create();
        task.stopDaemon();
    }

}
