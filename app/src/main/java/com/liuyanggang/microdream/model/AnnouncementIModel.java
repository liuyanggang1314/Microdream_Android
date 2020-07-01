package com.liuyanggang.microdream.model;

import com.liuyanggang.microdream.callback.AbstractStringCallback;
import com.liuyanggang.microdream.entity.ExaminationEntity;
import com.liuyanggang.microdream.model.lisentener.AnnouncementListener;
import com.liuyanggang.microdream.utils.TimeUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

import static com.liuyanggang.microdream.entity.HttpEntity.ANNOUNCEMENT;
import static com.liuyanggang.microdream.entity.HttpEntity.OK;
import static com.liuyanggang.microdream.entity.HttpEntity.TIME_OUT;
import static com.liuyanggang.microdream.entity.HttpEntity.UNAUTHORIZED_INT;
import static com.liuyanggang.microdream.entity.HttpEntity.UNAUTHORIZED_STRING;

/**
 * @ClassName ExaminationIModel
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/31
 * @Version 1.0
 */
public class AnnouncementIModel implements IModel {
    public void getAnnouncementList(int current, AnnouncementListener lisentener) {
        if (lisentener == null) {
            return;
        }
        OkGo.<String>get(ANNOUNCEMENT)
                .params("page", current)
                .execute(new AbstractStringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        lisentener.onAnnouncementError("网络请求错误");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        int code = response.code();
                        String str = response.body();
                        switch (code) {
                            case OK:
                                try {
                                    JSONObject jsonObject = new JSONObject(str);
                                    Integer pages = jsonObject.getInt("totalPage");
                                    Integer pageIndex = jsonObject.getInt("pageIndex");
                                    JSONArray records = jsonObject.getJSONArray("data");
                                    List<ExaminationEntity> list = new ArrayList<>();
                                    for (int i = 0; i < records.size(); i++) {
                                        ExaminationEntity examinationEntity = new ExaminationEntity();
                                        JSONObject jsonObject1 = (JSONObject) records.get(i);
                                        Long id = jsonObject1.getLong("id");
                                        String moduleName = jsonObject1.getStr("source");

                                        String title = jsonObject1.getStr("title");
                                        String updateTime = jsonObject1.getStr("time");
                                        Date date = DateUtil.parse(updateTime);
                                        examinationEntity.setUpdateTime(TimeUtil.getTimeFormatText(date));
                                        examinationEntity.setTitle(title);
                                        examinationEntity.setModuleName(moduleName);
                                        examinationEntity.setId(id);
                                        list.add(examinationEntity);
                                    }
                                    if (current > 1) {
                                        lisentener.onLoadMore(list, pageIndex);
                                    } else {
                                        lisentener.onAnnouncementSeccess(list, pages);
                                    }
                                } catch (Exception e) {
                                    lisentener.onAnnouncementSeccess(null, 0);
                                }
                                break;
                            case UNAUTHORIZED_INT:
                                lisentener.onAnnouncementError(UNAUTHORIZED_STRING);
                                break;
                            case TIME_OUT:
                                lisentener.onAnnouncementError("请求超时");
                                break;
                            default:
                                JSONObject jsonObject = new JSONObject(str);
                                String msg = jsonObject.getStr("message");
                                lisentener.onAnnouncementError(msg);
                                break;
                        }
                    }
                });
    }


}
