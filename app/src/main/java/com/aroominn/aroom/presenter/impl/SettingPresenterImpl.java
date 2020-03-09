package com.aroominn.aroom.presenter.impl;

import com.aroominn.aroom.base.BaseImpl;
import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.Result;
import com.aroominn.aroom.model.SettingModel;
import com.aroominn.aroom.model.impl.SettingModelImpl;
import com.aroominn.aroom.presenter.SettingPresenter;
import com.aroominn.aroom.presenter.listener.OnSettingListener;
import com.aroominn.aroom.view.mine.setting.FeedBackActivity;
import com.aroominn.aroom.view.views.SettingView;

import org.json.JSONObject;

public class SettingPresenterImpl implements SettingPresenter ,OnSettingListener {

    public SettingModel model;
    public SettingView view;

    public SettingPresenterImpl(SettingView view) {
        this.view = view;
        model=new SettingModelImpl(this);
    }

    @Override
    public void feedBack(BaseImpl context, JSONObject param) {
        model.loadFeedBack(context,param);
    }

    @Override
    public void onSuccess(Result result) {

        view.onSuccess(result);
    }

    @Override
    public void onError(BasicResponse response, String url) {
        view.onError(response,url);
    }
}
