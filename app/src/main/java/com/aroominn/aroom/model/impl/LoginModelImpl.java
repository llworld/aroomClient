package com.aroominn.aroom.model.impl;


import com.aroominn.aroom.base.BaseImpl;
import com.aroominn.aroom.bean.Result;
import com.aroominn.aroom.bean.User;
import com.aroominn.aroom.model.LoginModel;
import com.aroominn.aroom.presenter.listener.OnLoginListener;
import com.aroominn.aroom.utils.UrlTools;
import com.aroominn.aroom.utils.retrofit.DefaultObserver;
import com.aroominn.aroom.utils.retrofit.RetrofitRequest;

import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class LoginModelImpl implements LoginModel {

    private OnLoginListener listener;

    public LoginModelImpl(OnLoginListener listener) {
        this.listener = listener;
    }


    @Override
    public void loadVcode(BaseImpl context, JSONObject param) {
        RetrofitRequest.getApiService()
                .getVcode(RetrofitRequest.getJSONBody(param))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Result>(context,true) {
                    @Override
                    public void onSuccess(Result result) {
                        listener.onSuccess(result.getData().toString());
                    }

                    @Override
                    public void onFail(Result response) {
                        listener.onError(response, UrlTools.VCODE_URL);
                    }
                });
    }

    @Override
    public void loadRegister(BaseImpl context, JSONObject param) {
        RetrofitRequest.getApiService()
                .getRegister(RetrofitRequest.getJSONBody(param))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Result>(context,true) {
                    @Override
                    public void onSuccess(Result result) {
                        listener.onSuccess((Boolean) result.getData());
                    }

                    @Override
                    public void onFail(Result response) {
                        listener.onError(response, UrlTools.VCODE_URL);
                    }
                });
    }

    @Override
    public void loadLogin(BaseImpl context, JSONObject param) {
        RetrofitRequest.getApiService()
                .getLogin(RetrofitRequest.getJSONBody(param))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<User>(context,true) {
                    @Override
                    public void onSuccess(User user) {
                        listener.onSuccess(user);
                    }

                    @Override
                    public void onFail(User response) {
                        listener.onError(response, UrlTools.LOGIN_URL);
                    }
                });
    }

    //账号密码登录
    @Override
    public void loadAccountLogin(BaseImpl context, JSONObject param) {
        RetrofitRequest.getApiService()
                .getAccountLogin(RetrofitRequest.getJSONBody(param))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<User>(context,true) {
                    @Override
                    public void onSuccess(User user) {
                        listener.onSuccess(user);
                    }

                    @Override
                    public void onFail(User response) {
                        listener.onError(response, UrlTools.LOGIN_URL);
                    }
                });
    }
}
