package com.aroominn.aroom.utils.retrofit;


import com.aroominn.aroom.base.BaseApplication;
import com.aroominn.aroom.utils.UrlTools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author yml
 *         Created on 2017/5/3 16:37
 */
public class RetrofitRequest {
    private Retrofit retrofit;
    private RetrofitApi service;

    public RetrofitRequest() {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();

        synchronized (RetrofitRequest.class) {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(UrlTools.SERVERIP)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(BaseApplication.instance.okHttpClient.build())
                        .build();
            }
        }
        service = retrofit.create(RetrofitApi.class);
    }

    /**
     * 创建单例
     */
    private static class SingletonHolder {
        private static final RetrofitRequest INSTANCE = new RetrofitRequest();
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static RetrofitApi getApiService() {
        return SingletonHolder.INSTANCE.service;
    }

    /**
     * 将Map转化为字符串
     *
     * @param param
     * @return
     */
    public static RequestBody getRequestBody(Map<String, Object> param) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : param.keySet()) {
            builder.add(key, String.valueOf(param.get(key)));
        }
        return builder.build();
    }

    /**
     * 将JSON转化为字符串
     *
     * @param param
     * @return
     */
    public static RequestBody getJSONBody(JSONObject param) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param.toString());
    }

    /**
     * 获取图片Body
     *
     * @param file
     * @return
     */
    public static MultipartBody.Part prepareFilePart(File file) {
        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);

        return body;
    }
}