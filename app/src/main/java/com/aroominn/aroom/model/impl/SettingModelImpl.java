package com.aroominn.aroom.model.impl;

import com.aroominn.aroom.base.BaseImpl;
import com.aroominn.aroom.bean.Result;
import com.aroominn.aroom.model.SettingModel;
import com.aroominn.aroom.presenter.impl.SettingPresenterImpl;
import com.aroominn.aroom.presenter.listener.OnSettingListener;
import com.aroominn.aroom.utils.retrofit.DefaultObserver;
import com.aroominn.aroom.utils.retrofit.RetrofitRequest;

import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.aroominn.aroom.utils.UrlTools.FEEDBACK;

public class SettingModelImpl implements SettingModel {

    OnSettingListener listener;

    public SettingModelImpl(OnSettingListener listener) {
        this.listener = listener;
    }

    @Override
    public void loadFeedBack(BaseImpl context, JSONObject param) {
        RetrofitRequest.getApiService()
                .getFeedBack(RetrofitRequest.getJSONBody(param))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Result>(context, false) {
                    @Override
                    public void onSuccess(Result result) {
                        listener.onSuccess(result);
                    }

                    @Override
                    public void onFail(Result response) {
                        listener.onError(response, FEEDBACK);
                    }
                });
    }
}
