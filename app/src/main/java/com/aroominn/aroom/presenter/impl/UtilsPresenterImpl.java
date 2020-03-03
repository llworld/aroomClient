package com.aroominn.aroom.presenter.impl;

import com.aroominn.aroom.base.BaseImpl;
import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.Result;
import com.aroominn.aroom.model.UtilsModel;
import com.aroominn.aroom.model.impl.UtilsModelImpl;
import com.aroominn.aroom.presenter.UtilsPresenter;
import com.aroominn.aroom.presenter.listener.OnUtilsListener;
import com.aroominn.aroom.view.views.UtilsView;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UtilsPresenterImpl implements UtilsPresenter,OnUtilsListener {

    private UtilsView utilsView;
    private UtilsModel utilsModel;

    public UtilsPresenterImpl(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.utilsModel=new UtilsModelImpl(this);
    }

    @Override
    public void getStory(BaseImpl context, String text, List<MultipartBody.Part> parts) {
        utilsModel.upFile(context,text,parts);
    }
    /*@Override
    public void getStory(BaseImpl context, String text,Map<String, RequestBody> param) {
        utilsModel.upFile(context,text,param);
    }*/

    @Override
    public void onSuccess(Result result) {
utilsView.setStory(result);
    }

    @Override
    public void onError(BasicResponse response, String url) {
        utilsView.showError(response, url);
    }
}
