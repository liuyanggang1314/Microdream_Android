package com.liuyanggang.microdream.model;

import com.liuyanggang.microdream.callback.AbstractStringCallback;
import com.liuyanggang.microdream.entity.ExaminationEntity;
import com.liuyanggang.microdream.model.lisentener.ExaminationListener;
import com.liuyanggang.microdream.utils.TimeUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

import static com.liuyanggang.microdream.entity.HttpEntity.EXAMINATION;
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
public class ExaminationIModel implements IModel {
    public void getExaminationList(int current, String moduleName, ExaminationListener lisentener) {
        if (lisentener == null) {
            return;
        }
        OkGo.<String>get(EXAMINATION)
                .params("moduleName", moduleName)
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
                                    List<ExaminationEntity> list = new ArrayList<>();
                                    for (int i = 0; i < records.size(); i++) {
                                        ExaminationEntity examinationEntity = new ExaminationEntity();
                                        JSONObject jsonObject1 = (JSONObject) records.get(i);
                                        Long id = jsonObject1.getLong("id");
                                        String moduleName = jsonObject1.getStr("moduleName");
                                        String category = jsonObject1.getStr("category");
                                        String title = jsonObject1.getStr("title");
                                        String content = jsonObject1.getStr("content");
                                        String updateTime = jsonObject1.getStr("updateTime");
                                        String createTime = jsonObject1.getStr("createTime");
                                        Long readingSum = jsonObject1.getLong("readingSum");

                                        Date date = DateUtil.parse(updateTime);

                                        examinationEntity.setUpdateTime(TimeUtil.getTimeFormatText(date));
                                        examinationEntity.setTitle(title);
                                        examinationEntity.setCategory(category);
                                        examinationEntity.setContent(content);
                                        examinationEntity.setCreateTime(createTime);
                                        examinationEntity.setModuleName(moduleName);
                                        examinationEntity.setId(id);
                                        examinationEntity.setReadingSum(readingSum);
                                        list.add(examinationEntity);
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


}
