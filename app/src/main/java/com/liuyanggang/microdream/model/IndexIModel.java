package com.liuyanggang.microdream.model;

import com.liuyanggang.microdream.callback.AbstractStringCallback;
import com.liuyanggang.microdream.entity.DataBean;
import com.liuyanggang.microdream.entity.IndexEntity;
import com.liuyanggang.microdream.model.lisentener.GetIndexBannerListener;
import com.liuyanggang.microdream.model.lisentener.GetIndexListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

import static com.liuyanggang.microdream.entity.HttpEntity.BAD_REQUEST;
import static com.liuyanggang.microdream.entity.HttpEntity.INDEX_BANNER;
import static com.liuyanggang.microdream.entity.HttpEntity.INDEX_LIST;
import static com.liuyanggang.microdream.entity.HttpEntity.OK;
import static com.liuyanggang.microdream.entity.HttpEntity.TIME_OUT;
import static com.liuyanggang.microdream.entity.HttpEntity.UNAUTHORIZED_INT;
import static com.liuyanggang.microdream.entity.HttpEntity.UNAUTHORIZED_STRING;

/**
 * @ClassName IndexIModel
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/30
 * @Version 1.0
 */
public class IndexIModel implements IModel {
    public void getIndexBannerList(GetIndexBannerListener lisentener) {
        if (lisentener == null) {
            return;
        }
        OkGo.<String>get(INDEX_BANNER)
                .execute(new AbstractStringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        lisentener.onBannerError("网络请求错误");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        int code = response.code();
                        String str = response.body();
                        switch (code) {
                            case OK:
                                try {
                                    JSONObject jsonObject = new JSONObject(str);
                                    JSONArray jsonArray = new JSONArray(jsonObject.getStr("posts"));
                                    List<DataBean> list = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.size(); i++) {
                                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                                        Long id = jsonObject1.getLong("id");
                                        String postTitle = jsonObject1.getStr("post_title");
                                        String postMediumImage = jsonObject1.getStr("post_medium_image");
                                        String link = jsonObject1.getStr("post_permalink");
                                        DataBean dataBean = new DataBean(postMediumImage, postTitle, link, 1);
                                        list.add(dataBean);
                                    }
                                    lisentener.onBannerSeccess(list);
                                } catch (Exception e) {
                                    lisentener.onBannerError(null);
                                }
                                break;
                            case UNAUTHORIZED_INT:
                                lisentener.onBannerError(UNAUTHORIZED_STRING);
                                break;
                            case TIME_OUT:
                                lisentener.onBannerError("请求超时");
                                break;
                            default:
                                JSONObject jsonObject = new JSONObject(str);
                                String msg = jsonObject.getStr("message");
                                lisentener.onBannerError(msg);
                                break;
                        }
                    }
                });
    }

    public void getIndexList(int current, GetIndexListener lisentener) {
        if (lisentener == null) {
            return;
        }
        OkGo.<String>get(INDEX_LIST)
                .params("per_page", 10)
                .params("orderby", "date")
                .params("order", "desc")
                .params("page", current)
                .execute(new AbstractStringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        lisentener.onIndexError("网络请求错误");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        int code = response.code();
                        String str = response.body();
                        switch (code) {
                            case OK:
                                try {
                                    JSONArray jsonArray = new JSONArray(str);
                                    List<IndexEntity> list = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.size(); i++) {
                                        IndexEntity indexEntity = new IndexEntity();
                                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                                        Long id = jsonObject1.getLong("id");
                                        String link = jsonObject1.getStr("link");
                                        String title = jsonObject1.getJSONObject("title").getStr("rendered");
                                        String content = jsonObject1.getJSONObject("excerpt").getStr("rendered");
                                        String image = jsonObject1.getStr("post_medium_image");
                                        String categoryName = jsonObject1.getStr("category_name");
                                        String postDate = jsonObject1.getStr("post_date");
                                        String likeCount = jsonObject1.getStr("like_count");
                                        String pageViews = jsonObject1.getStr("pageviews");
                                        String totalComments = jsonObject1.getStr("total_comments");

                                        indexEntity.setLikeCount(likeCount);
                                        indexEntity.setPageViews(pageViews);
                                        indexEntity.setTotalComments(totalComments);
                                        indexEntity.setPostDate(postDate);
                                        indexEntity.setCategoryName(categoryName);
                                        indexEntity.setContent(content);
                                        indexEntity.setImageUrl(image);
                                        indexEntity.setTitle(title);
                                        indexEntity.setId(id);
                                        indexEntity.setLink(link);
                                        list.add(indexEntity);
                                    }
                                    if (current > 1) {
                                        lisentener.onIndexLoadmoreSeccess(list);
                                    } else {
                                        lisentener.onIndexSeccess(list);
                                    }
                                } catch (Exception e) {
                                    lisentener.onIndexSeccess(null);
                                }
                                break;
                            case UNAUTHORIZED_INT:
                                lisentener.onIndexError(UNAUTHORIZED_STRING);
                                break;
                            case TIME_OUT:
                                lisentener.onIndexError("请求超时");
                                break;
                            case BAD_REQUEST:
                                JSONObject jsonObject = new JSONObject(str);
                                String msg = jsonObject.getStr("message");
                                lisentener.onIndexError(msg);
                                break;
                            default:
                                lisentener.onIndexError("请求错误");
                                break;
                        }
                    }
                });
    }
}
