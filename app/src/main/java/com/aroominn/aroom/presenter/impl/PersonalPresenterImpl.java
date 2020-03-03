package com.aroominn.aroom.presenter.impl;

import com.aroominn.aroom.base.BaseImpl;
import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.User;
import com.aroominn.aroom.model.PersonalModel;
import com.aroominn.aroom.model.impl.PersonalModelImpl;
import com.aroominn.aroom.presenter.PersonalPresenter;
import com.aroominn.aroom.presenter.listener.OnPersonalListener;
import com.aroominn.aroom.view.views.PersonalView;

import org.json.JSONObject;

public class PersonalPresenterImpl implements OnPersonalListener, PersonalPresenter {

    private PersonalView view;
    private PersonalModel model;

    public PersonalPresenterImpl(PersonalView view) {
        this.view = view;
        model = new PersonalModelImpl(this);
    }

    @Override
    public void getUser(BaseImpl content, JSONObject param) {
        model.loadUser(content, param);
    }

    @Override
    public void getSms(BaseImpl context, JSONObject param) {
        model.loadSms(context, param);
    }

    @Override
    public void onSuccess(String code) {
        view.setPhoneSms(code);
    }

    @Override
    public void onSuccess(User user) {
        view.setUser(user);
    }

    @Override
    public void onError(BasicResponse response, String url) {
view.showError(response,url);
    }
}
