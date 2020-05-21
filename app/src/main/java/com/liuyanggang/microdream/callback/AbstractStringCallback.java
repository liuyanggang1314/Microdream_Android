package com.liuyanggang.microdream.callback;

import com.liuyanggang.microdream.utils.MMKVUtils;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.request.base.Request;

import okhttp3.Response;

import static com.liuyanggang.microdream.entity.MicrodreamEntity.TOKEN_STRING;

/**
 * @ClassName LiuStringCallback
 * @Description TODO
 * @Author 刘杨刚/Microdream
 * @Date 2020/5/21
 * @Version 1.0
 */
public abstract class AbstractStringCallback extends AbsCallback<String> {
    private StringConvert convert;

    public AbstractStringCallback() {
        convert = new StringConvert();
    }

    @Override
    public void onStart(Request<String, ? extends Request> request) {
        super.onStart(request);
        if (MMKVUtils.getStringInfo(TOKEN_STRING) != null) {
            request.headers("Authorization", MMKVUtils.getStringInfo(TOKEN_STRING));
        }
    }

    @Override
    public String convertResponse(Response response) throws Throwable {
        String s = convert.convertResponse(response);
        response.close();
        return s;
    }
}
