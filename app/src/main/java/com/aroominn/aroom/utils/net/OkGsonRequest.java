package com.aroominn.aroom.utils.net;


import com.aroominn.aroom.utils.okHttp.OkHttpUtils;
import com.aroominn.aroom.utils.okHttp.callback.FileCallBack;
import com.aroominn.aroom.utils.okHttp.callback.GosnCallback;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.MediaType;


/**
 * @param <T>
 * @author Mr.Yu
 * @date 2017年02月24日 下午2:54:19
 */
public class OkGsonRequest<T> {

    public static final int GET = 0x4567;
    public static final int POST = 0x5678;
    private static final MediaType TYPE = MediaType.parse("application/json; charset=utf-8");


    public OkGsonRequest(String url, JSONObject params, Class<T> clazz, GosnCallback gosnCallback) {
        this(POST, url, params, clazz, gosnCallback);
    }

    public OkGsonRequest(String url, JSONArray params, Class<T> clazz, GosnCallback gosnCallback) {
        this(POST, url, params, clazz, gosnCallback);
    }

    public OkGsonRequest(String url, JSONObject params, TypeToken<T> typeToken, GosnCallback gosnCallback) {
        this(POST, url, params, typeToken, gosnCallback);
    }


    public OkGsonRequest(int method, String url, JSONObject params, Class<T> clazz, GosnCallback gosnCallback) {
        gosnCallback.setmClass(clazz);
        gosnCallback.setUrl(url);
        OkHttpUtils.postString()
                .url(url)
                .content(params != null ? params.toString() : "")
                .mediaType(TYPE)
                .build()
                .execute(gosnCallback);
    }

    public OkGsonRequest(int method, String url, JSONArray params, Class<T> clazz, GosnCallback gosnCallback) {
        gosnCallback.setmClass(clazz);
        gosnCallback.setUrl(url);

        OkHttpUtils.postString()
                .url(url)
                .content(params != null ? params.toString() : "")
                .mediaType(TYPE)
                .build()
                .execute(gosnCallback);
    }

    public OkGsonRequest(int method, String url, JSONObject params, TypeToken<T> typeToken, GosnCallback gosnCallback) {
        gosnCallback.setmTypeToken(typeToken);
        gosnCallback.setUrl(url);

        OkHttpUtils.postString()
                .url(url)
                .content(params != null ? params.toString() : "")
                .mediaType(TYPE)
                .build()
                .execute(gosnCallback);
    }

    public OkGsonRequest(String url, FileCallBack fileCallBack) {
        OkHttpUtils.post()
                .url(url)
                .build()
                .execute(fileCallBack);
    }
}