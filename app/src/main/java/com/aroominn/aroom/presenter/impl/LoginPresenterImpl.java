package com.aroominn.aroom.presenter.impl;



import com.aroominn.aroom.base.BaseImpl;
import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.User;
import com.aroominn.aroom.model.LoginModel;
import com.aroominn.aroom.model.impl.LoginModelImpl;
import com.aroominn.aroom.presenter.LoginPresenter;
import com.aroominn.aroom.presenter.listener.OnLoginListener;
import com.aroominn.aroom.view.views.LoginView;

import org.json.JSONObject;

/**
 * Author : yml
 * Created on 2015/6/20.
 */
public class LoginPresenterImpl implements LoginPresenter, OnLoginListener {

    /*Presenter作为中间层，持有View和Model的引用*/
    private LoginView loginView;
    private LoginModel loginModel;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        loginModel = new LoginModelImpl(this);
    }

    @Override
    public void onSuccess(String code) {
        loginView.setVcode(code);
    }

    @Override
    public void onSuccess(Boolean result) {
        loginView.setRegister(result);
    }

    @Override
    public void onSuccess(User user) {
        loginView.setLogin(user);
    }

    @Override
    public void onError(BasicResponse response, String url) {
        loginView.showError(response,url);
    }

    @Override
    public void getVcode(BaseImpl context, JSONObject param) {
        loginModel.loadVcode(context,param);
    }

    @Override
    public void getRegister(BaseImpl context, JSONObject param) {
        loginModel.loadRegister(context,param);
    }

    @Override
    public void getLogin(BaseImpl context, JSONObject param) {
        loginModel.loadLogin(context,param);
    }
}
