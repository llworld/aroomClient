package com.aroominn.aroom.presenter.impl;

import com.aroominn.aroom.base.BaseImpl;
import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.Comment;
import com.aroominn.aroom.bean.HomeInfo;
import com.aroominn.aroom.bean.Result;
import com.aroominn.aroom.bean.Story;
import com.aroominn.aroom.model.HomePageModel;
import com.aroominn.aroom.model.StoryModel;
import com.aroominn.aroom.model.impl.HomePageModelImpl;
import com.aroominn.aroom.model.impl.StoryModelImpl;
import com.aroominn.aroom.presenter.HomePagePresenter;
import com.aroominn.aroom.presenter.StoryPresenter;
import com.aroominn.aroom.presenter.listener.OnHomePageListener;
import com.aroominn.aroom.presenter.listener.OnStoryListener;
import com.aroominn.aroom.view.views.HomePageView;
import com.aroominn.aroom.view.views.StoryView;

import org.json.JSONObject;

public class HomePagePresenterImpl implements HomePagePresenter, OnHomePageListener {

    private HomePageView view;
    private HomePageModel model;

    public HomePagePresenterImpl(HomePageView view) {
        this.view = view;
        model = new HomePageModelImpl(this);
    }


    @Override
    public void onSuccess(Story story) {
        view.setHomeStory(story);
    }

    @Override
    public void onSuccess(HomeInfo info) {
        view.setHomeInfo(info);
    }


    @Override
    public void onError(BasicResponse response, String url) {
        view.showError(response, url);
    }

    @Override
    public void getHomeUser(BaseImpl context, JSONObject param) {
        model.UserInfo(context, param);
    }

    @Override
    public void getHomeStory(BaseImpl context, JSONObject param) {
        model.HisStories(context, param);

    }
}
