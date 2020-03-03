package com.aroominn.aroom.model.impl;

import com.aroominn.aroom.base.BaseImpl;
import com.aroominn.aroom.bean.Result;
import com.aroominn.aroom.bean.Story;
import com.aroominn.aroom.bean.User;
import com.aroominn.aroom.model.PersonalModel;
import com.aroominn.aroom.presenter.listener.OnPersonalListener;
import com.aroominn.aroom.utils.retrofit.DefaultObserver;
import com.aroominn.aroom.utils.retrofit.RetrofitRequest;

import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.aroominn.aroom.utils.UrlTools.STORYLIST;

public class PersonalModelImpl implements PersonalModel {

    private OnPersonalListener listener;

    public PersonalModelImpl(OnPersonalListener listener) {
        this.listener = listener;
    }

    @Override
    public void loadSms(BaseImpl content, JSONObject param) {
        RetrofitRequest.getApiService()
                .getPhoneSms(RetrofitRequest.getJSONBody(param))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Result>(content, false) {
                    @Override
                    public void onSuccess(Result result) {
                        String code = (String) result.getData();
                        listener.onSuccess(code);
                    }

                    @Override
                    public void onFail(Result response) {
                        listener.onError(response, STORYLIST);
                    }
                });
    }

    @Override
    public void loadUser(BaseImpl content, JSONObject param) {
        RetrofitRequest.getApiService()
                .getUser(RetrofitRequest.getJSONBody(param))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<User>(content, false) {
                    @Override
                    public void onSuccess(User user) {

                        listener.onSuccess(user);
                    }

                    @Override
                    public void onFail(User response) {
                        listener.onError(response, STORYLIST);
                    }
                });
    }
}
