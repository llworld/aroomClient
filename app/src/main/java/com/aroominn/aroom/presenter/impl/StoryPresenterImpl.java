package com.aroominn.aroom.presenter.impl;

import com.aroominn.aroom.base.BaseImpl;
import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.Comment;
import com.aroominn.aroom.bean.PageHelper;
import com.aroominn.aroom.bean.Result;
import com.aroominn.aroom.bean.Story;
import com.aroominn.aroom.model.StoryModel;
import com.aroominn.aroom.model.impl.StoryModelImpl;
import com.aroominn.aroom.presenter.StoryPresenter;
import com.aroominn.aroom.presenter.listener.OnStoryListener;
import com.aroominn.aroom.view.views.StoryView;

import org.json.JSONObject;

public class StoryPresenterImpl implements StoryPresenter, OnStoryListener {

    private StoryView storyView;
    private StoryModel storyModel;

    public StoryPresenterImpl(StoryView storyView) {
        this.storyView = storyView;
        storyModel = new StoryModelImpl(this);
    }

    @Override
    public void getStory(BaseImpl context, JSONObject param) {
        storyModel.loadStory(context, param);
    }

    @Override
    public void getStoryDetails(BaseImpl context, JSONObject param) {
        storyModel.loadStoryDetails(context, param);
    }

    @Override
    public void putLike(BaseImpl context, JSONObject param) {
//        storyModel.likeStory(context,param);
    }

    @Override
    public void commentStory(BaseImpl context, JSONObject param) {
//        storyModel.loadStoryDetails();
    }

    @Override
    public void onSuccess(Story story) {
        storyView.setStory(story);
    }

    @Override
    public void onSuccess(PageHelper pageHelper) {
        storyView.setStoryDetails(pageHelper);

    }


    @Override
    public void onSuccess(Result result) {
storyView.setResult(result);
    }

    @Override
    public void onError(BasicResponse response, String url) {
        storyView.showError(response, url);
    }
}
