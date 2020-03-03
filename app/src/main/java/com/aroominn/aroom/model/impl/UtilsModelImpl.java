package com.aroominn.aroom.model.impl;

import com.aroominn.aroom.base.BaseImpl;
import com.aroominn.aroom.bean.Result;
import com.aroominn.aroom.bean.Story;
import com.aroominn.aroom.model.UtilsModel;
import com.aroominn.aroom.presenter.listener.OnUtilsListener;
import com.aroominn.aroom.utils.retrofit.DefaultObserver;
import com.aroominn.aroom.utils.retrofit.RetrofitRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.aroominn.aroom.utils.UrlTools.STORYLIST;

public class UtilsModelImpl implements UtilsModel {

    private OnUtilsListener listener;

    public UtilsModelImpl(OnUtilsListener listener) {
        this.listener = listener;
    }

    //上传文件
    @Override
    public void upFile(BaseImpl context, String text,  List<MultipartBody.Part> parts) {

        RetrofitRequest.getApiService()
                .saveReport( parts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Result>(context, false) {
                    @Override
                    public void onSuccess(Result story) {
                        listener.onSuccess(story);
                    }

                    @Override
                    public void onFail(Result response) {
                        listener.onError(response, STORYLIST);
                    }
                });
    }
}

