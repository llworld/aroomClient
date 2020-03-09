package com.aroominn.aroom.presenter.impl;

import com.aroominn.aroom.base.BaseImpl;
import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.Friend;
import com.aroominn.aroom.bean.HomeInfo;
import com.aroominn.aroom.bean.Result;
import com.aroominn.aroom.bean.Story;
import com.aroominn.aroom.bean.User;
import com.aroominn.aroom.model.FriendsModel;
import com.aroominn.aroom.model.HomePageModel;
import com.aroominn.aroom.model.impl.FriendsModelImpl;
import com.aroominn.aroom.model.impl.HomePageModelImpl;
import com.aroominn.aroom.presenter.FriendsPresenter;
import com.aroominn.aroom.presenter.HomePagePresenter;
import com.aroominn.aroom.presenter.listener.OnFriendsListener;
import com.aroominn.aroom.presenter.listener.OnHomePageListener;
import com.aroominn.aroom.view.views.FriendsView;
import com.aroominn.aroom.view.views.HomePageView;

import org.json.JSONObject;

public class FriendsPresenterImpl implements FriendsPresenter, OnFriendsListener {

    private FriendsView view;
    private FriendsModel model;

    public FriendsPresenterImpl(FriendsView view) {
        this.view = view;
        model = new FriendsModelImpl(this);
    }


    @Override
    public void onSuccess(Friend friend) {
        view.setFriends(friend);
    }

    @Override
    public void onSuccess(User user) {
        view.setFriendInfo(user);
    }

    @Override
    public void onError(BasicResponse response, String url) {
        view.showError(response, url);
    }


    @Override
    public void getWaiterFriends(BaseImpl context, JSONObject param) {
        model.loadWaiterFriends(context, param);
    }

    @Override
    public void getBossFriends(BaseImpl context, JSONObject param) {
        model.loadBossFriends(context, param);
    }

    @Override
    public void getMateFriends(BaseImpl context, JSONObject param) {
        model.loadMateFriends(context, param);
    }

    @Override
    public void getUserInfo(BaseImpl context, JSONObject param) {
        model.loaFriendInfo(context,param);
    }
}
