package com.liuyanggang.microdream.model;

import com.liuyanggang.microdream.callback.AbstractStringCallback;
import com.liuyanggang.microdream.entity.HomepageEntity;
import com.liuyanggang.microdream.model.lisentener.DeleteMoodListener;
import com.liuyanggang.microdream.model.lisentener.GetMoodListListener;
import com.liuyanggang.microdream.utils.MyListUtil;
import com.liuyanggang.microdream.utils.TimeUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

import static com.liuyanggang.microdream.entity.HttpEntity.DELETE_MOOD;
import static com.liuyanggang.microdream.entity.HttpEntity.MOODLIST;
import static com.liuyanggang.microdream.entity.HttpEntity.OK;
import static com.liuyanggang.microdream.entity.HttpEntity.TIME_OUT;
import static com.liuyanggang.microdream.entity.HttpEntity.UNAUTHORIZED_INT;
import static com.liuyanggang.microdream.entity.HttpEntity.UNAUTHORIZED_STRING;

/**
 * @ClassName HomepageIMode
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/28
 * @Version 1.0
 */
public class HomepageIModel implements IModel {
    public void getMoodList(int current, GetMoodListListener lisentener) {
        if (lisentener == null) {
            return;
        }
        OkGo.<String>get(MOODLIST)
                .params("current", current)
                .params("size", 10)
                .execute(new AbstractStringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        lisentener.onHomepageError("网络请求错误");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        int code = response.code();
                        String str = response.body();
                        switch (code) {
                            case OK:
                                try {
                                    JSONObject jsonObject = new JSONObject(str);
                                    Integer pages = jsonObject.getInt("pages");
                                    Integer current_ = jsonObject.getInt("current");
                                    JSONArray records = jsonObject.getJSONArray("records");
                                    List<HomepageEntity> list = new ArrayList<>();
                                    for (int i = 0; i < records.size(); i++) {
                                        HomepageEntity homepageEntity = new HomepageEntity();
                                        JSONObject jsonObject1 = (JSONObject) records.get(i);
                                        Long id = jsonObject1.getLong("id");
                                        Long userId = jsonObject1.getLong("userId");
                                        String content = jsonObject1.getStr("content");
                                        String images = jsonObject1.getStr("images");
                                        String creatTime = jsonObject1.getStr("creatTime");
                                        String nikeName = jsonObject1.getStr("nikeName");
                                        String avatarName = jsonObject1.getStr("avatarName");
                                        Boolean isDelete = jsonObject1.getBool("isDelete");

                                        Date date = DateUtil.parse(creatTime);
                                        homepageEntity.setId(id);
                                        homepageEntity.setUserId(userId);
                                        homepageEntity.setAvatar(avatarName);
                                        homepageEntity.setNikeName(nikeName);
                                        homepageEntity.setContent(content);
                                        homepageEntity.setImages(MyListUtil.setString(images));
                                        homepageEntity.setCreatTime(TimeUtil.getTimeFormatText(date));
                                        homepageEntity.setDelete(isDelete);
                                        list.add(homepageEntity);
                                    }
                                    if (current > 1) {
                                        lisentener.onLoadMore(list, current_);
                                    } else {
                                        lisentener.onHomepageSeccess(list, pages);
                                    }
                                } catch (Exception e) {
                                    lisentener.onHomepageSeccess(null, 0);
                                }
                                break;
                            case UNAUTHORIZED_INT:
                                lisentener.onHomepageError(UNAUTHORIZED_STRING);
                                break;
                            case TIME_OUT:
                                lisentener.onHomepageError("请求超时");
                                break;
                            default:
                                JSONObject jsonObject = new JSONObject(str);
                                String msg = jsonObject.getStr("message");
                                lisentener.onHomepageError(msg);
                                break;
                        }
                    }
                });
    }

    public void onDeleteMood(Long id, DeleteMoodListener lisentener) {
        if (lisentener == null) {
            return;
        }
        OkGo.<String>delete(DELETE_MOOD)
                .isMultipart(true)
                .params("id", id)
                .execute(new AbstractStringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        lisentener.onDeleteMoodError("网络请求错误");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        int code = response.code();
                        String str = response.body();
                        switch (code) {
                            case OK:
                                lisentener.onDeleteMoodSeccess();
                                break;
                            case UNAUTHORIZED_INT:
                                lisentener.onDeleteMoodError(UNAUTHORIZED_STRING);
                                break;
                            case TIME_OUT:
                                lisentener.onDeleteMoodError("请求超时");
                                break;
                            default:
                                JSONObject jsonObject = new JSONObject(str);
                                String msg = jsonObject.getStr("message");
                                lisentener.onDeleteMoodError(msg);
                                break;
                        }
                    }
                });
    }

}
