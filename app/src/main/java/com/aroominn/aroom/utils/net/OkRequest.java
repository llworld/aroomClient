package com.aroominn.aroom.utils.net;


import com.aroominn.aroom.utils.okHttp.callback.FileCallBack;
import com.aroominn.aroom.utils.okHttp.callback.GosnCallback;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * @author Mr.Yu
 * @date 2017年02月24日 下午2:54:19
 */
public class OkRequest {

    private OkRequest() {

    }

    public static OkRequest newInstance() {
        return new OkRequest();
    }

    /**
     * JSONObject请求
     * @param url
     * @param params
     * @param clazz
     * @param gosnCallback
     * @param <T>
     */
    public static synchronized <T> void newGsonRequest(String url, JSONObject params, Class<T> clazz, GosnCallback<T> gosnCallback) {
        OkGsonRequest<T> request = new OkGsonRequest<T>(url, params, clazz, gosnCallback);
    }

    /**
     * JSONArray请求
     * @param url
     * @param params
     * @param clazz
     * @param gosnCallback
     * @param <T>
     */
    public static synchronized <T> void newGsonRequest(String url, JSONArray params, Class<T> clazz, GosnCallback<T> gosnCallback) {
        OkGsonRequest<T> request = new OkGsonRequest<T>(url, params, clazz, gosnCallback);
    }

    /**
     * 文件下载
     * @param url
     * @param fileCallBack
     * @param <T>
     */
    public static synchronized <T> void newGsonRequest(String url, FileCallBack fileCallBack) {
        OkGsonRequest<T> request = new OkGsonRequest<T>(url, fileCallBack);
    }
}
