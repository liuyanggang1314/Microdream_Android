package com.liuyanggang.microdream.model;

import com.liuyanggang.microdream.callback.AbstractStringCallback;
import com.liuyanggang.microdream.entity.ImageEntity;
import com.liuyanggang.microdream.model.lisentener.ImageByTypeListener;
import com.liuyanggang.microdream.model.lisentener.ImageListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

import static com.liuyanggang.microdream.entity.HttpEntity.IMAGE_BYTYPE;
import static com.liuyanggang.microdream.entity.HttpEntity.IMAGE_LIST;
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
public class ImageIModel implements IModel {
    public void getImageList(int current, ImageListener lisentener) {
        if (lisentener == null) {
            return;
        }
        OkGo.<String>get(IMAGE_LIST)
                .params("current", current)
                .params("size", 10)
                .execute(new AbstractStringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        lisentener.onImageError("网络请求错误");
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
                                    List<ImageEntity> list = new ArrayList<>();
                                    for (int i = 0; i < records.size(); i++) {
                                        ImageEntity imageEntity = new ImageEntity();
                                        JSONObject jsonObject1 = (JSONObject) records.get(i);
                                        Long id = jsonObject1.getLong("id");
                                        String name = jsonObject1.getStr("name");
                                        String url = jsonObject1.getStr("url");
                                        Long type = jsonObject1.getLong("type");

                                        imageEntity.setId(id);
                                        imageEntity.setName(name);
                                        imageEntity.setUrl(url);
                                        imageEntity.setType(type);
                                        list.add(imageEntity);
                                    }
                                    if (current > 1) {
                                        lisentener.onImageMore(list, current_);
                                    } else {
                                        lisentener.onImageSeccess(list, pages);
                                    }
                                } catch (Exception e) {
                                    lisentener.onImageSeccess(null, 0);
                                }
                                break;
                            case UNAUTHORIZED_INT:
                                lisentener.onImageError(UNAUTHORIZED_STRING);
                                break;
                            case TIME_OUT:
                                lisentener.onImageError("请求超时");
                                break;
                            default:
                                JSONObject jsonObject = new JSONObject(str);
                                String msg = jsonObject.getStr("message");
                                lisentener.onImageError(msg);
                                break;
                        }
                    }
                });
    }

    public void getImageByType(Long type, ImageByTypeListener lisentener) {
        if (lisentener == null) {
            return;
        }
        OkGo.<String>get(IMAGE_BYTYPE)
                .params("type", type)
                .execute(new AbstractStringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        lisentener.onImageError("网络请求错误");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        int code = response.code();
                        String str = response.body();
                        switch (code) {
                            case OK:
                                try {
                                    JSONArray jsonArray=new JSONArray(str);
                                    List<ImageEntity> list = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.size(); i++) {
                                        ImageEntity imageEntity = new ImageEntity();
                                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                                        Long id = jsonObject1.getLong("id");
                                        String name = jsonObject1.getStr("name");
                                        String url = jsonObject1.getStr("url");
                                        Long type = jsonObject1.getLong("type");

                                        imageEntity.setId(id);
                                        imageEntity.setName(name);
                                        imageEntity.setUrl(url);
                                        imageEntity.setType(type);
                                        list.add(imageEntity);
                                    }
                                    lisentener.onImageSeccess(list);
                                } catch (Exception e) {
                                    lisentener.onImageSeccess(null);
                                }
                                break;
                            case UNAUTHORIZED_INT:
                                lisentener.onImageError(UNAUTHORIZED_STRING);
                                break;
                            case TIME_OUT:
                                lisentener.onImageError("请求超时");
                                break;
                            default:
                                JSONObject jsonObject = new JSONObject(str);
                                String msg = jsonObject.getStr("message");
                                lisentener.onImageError(msg);
                                break;
                        }
                    }
                });
    }
}
