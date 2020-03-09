package com.aroominn.aroom.presenter.impl;

import com.aroominn.aroom.base.BaseImpl;
import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.Comment;
import com.aroominn.aroom.bean.HomeInfo;
import com.aroominn.aroom.bean.Result;
import com.aroominn.aroom.bean.Story;
import com.aroominn.aroom.model.HomePageModel;
import com.aroominn.aroom.model.TaleModel;
import com.aroominn.aroom.model.impl.HomePageModelImpl;
import com.aroominn.aroom.model.impl.TaleModelImpl;
import com.aroominn.aroom.presenter.HomePagePresenter;
import com.aroominn.aroom.presenter.TalePresenter;
import com.aroominn.aroom.presenter.listener.OnHomePageListener;
import com.aroominn.aroom.presenter.listener.OnTaleListener;
import com.aroominn.aroom.view.views.HomePageView;
import com.aroominn.aroom.view.views.TaleView;

import org.json.JSONObject;

public class TalePresenterImpl implements TalePresenter, OnTaleListener {

    private TaleView view;
    private TaleModel model;

    public TalePresenterImpl(TaleView view) {
        this.view = view;
        model = new TaleModelImpl(this);
    }


    @Override
    public void likeStory(BaseImpl context, JSONObject param) {
        model.loadLike(context, param);
    }

    @Override
    public void collocationStory(BaseImpl context, JSONObject param) {
        model.loadCollect(context, param);
    }

    @Override
    public void reportStory(BaseImpl context, JSONObject param) {
        model.loadReport(context, param);
    }

    @Override
    public void repostStory(BaseImpl context, JSONObject param) {
        model.loadRepost(context, param);
    }

    @Override
    public void commentStory(BaseImpl context, JSONObject param) {
        model.loadComment(context, param);
    }

    @Override
    public void deleteTale(BaseImpl context, JSONObject param) {
        model.loadDelete(context,param);
    }


    @Override
    public void onSuccess(Result result) {
        view.setTale(result);
    }

    @Override
    public void onError(BasicResponse response, String url) {
        view.showError(response, url);
    }
}
