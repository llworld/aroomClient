package com.aroominn.aroom.model.impl;

import com.aroominn.aroom.base.BaseImpl;
import com.aroominn.aroom.bean.Friend;
import com.aroominn.aroom.bean.HomeInfo;
import com.aroominn.aroom.bean.Result;
import com.aroominn.aroom.bean.Story;
import com.aroominn.aroom.bean.User;
import com.aroominn.aroom.model.FriendsModel;
import com.aroominn.aroom.model.HomePageModel;
import com.aroominn.aroom.presenter.listener.OnFriendsListener;
import com.aroominn.aroom.presenter.listener.OnHomePageListener;
import com.aroominn.aroom.utils.retrofit.DefaultObserver;
import com.aroominn.aroom.utils.retrofit.RetrofitRequest;

import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.aroominn.aroom.utils.UrlTools.STORYLIST;

/**
 *
 */
public class FriendsModelImpl implements FriendsModel {

    private OnFriendsListener listener;

    public FriendsModelImpl(OnFriendsListener listener) {
        this.listener = listener;
    }

    @Override
    public void loadWaiterFriends(BaseImpl context, JSONObject param) {
        RetrofitRequest.getApiService()
                .getWaiterFriends(RetrofitRequest.getJSONBody(param))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Friend>(context, false) {
                    @Override
                    public void onSuccess(Friend friend) {
                        listener.onSuccess(friend);
                    }

                    @Override
                    public void onFail(Friend friend) {
                        listener.onError(friend, STORYLIST);
                    }
                });
    }

    @Override
    public void loadBossFriends(BaseImpl context, JSONObject param) {
        RetrofitRequest.getApiService()
                .getBossFriends(RetrofitRequest.getJSONBody(param))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Friend>(context, false) {
                    @Override
                    public void onSuccess(Friend friend) {
                        listener.onSuccess(friend);
                    }

                    @Override
                    public void onFail(Friend friend) {
                        listener.onError(friend, STORYLIST);
                    }
                });
    }

    @Override
    public void loadMateFriends(BaseImpl context, JSONObject param) {
        RetrofitRequest.getApiService()
                .getMateFriends(RetrofitRequest.getJSONBody(param))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Friend>(context, false) {
                    @Override
                    public void onSuccess(Friend friend) {
                        listener.onSuccess(friend);
                    }

                    @Override
                    public void onFail(Friend friend) {
                        listener.onError(friend, STORYLIST);
                    }
                });
    }

    @Override
    public void loaFriendInfo(BaseImpl context, JSONObject param) {
        RetrofitRequest.getApiService()
                .getUserInfo(RetrofitRequest.getJSONBody(param))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<User>(context, false) {
                    @Override
                    public void onSuccess(User user) {
                        listener.onSuccess(user);
                    }

                    @Override
                    public void onFail(User user) {
                        listener.onError(user, STORYLIST);
                    }
                });

    }

/*
    @Override
    public void loadStory(BaseImpl context, JSONObject param) {
        RetrofitRequest.getApiService()
                .getStory(RetrofitRequest.getJSONBody(param))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Story>(context, false) {
                    @Override
                    public void onSuccess(Story story) {
                        listener.onSuccess(story);
                    }

                    @Override
                    public void onFail(Story response) {
                        listener.onError(response, STORYLIST);
                    }
                });
    }

    @Override
    public void loadStoryDetails(BaseImpl content, JSONObject param) {
        RetrofitRequest.getApiService()
                .getStoryDetails(RetrofitRequest.getJSONBody(param))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Comment>(content, false) {
                    @Override
                    public void onSuccess(Comment story) {
                        listener.onSuccess(story);
                    }

                    @Override
                    public void onFail(Comment response) {
                        listener.onError(response, STORYLIST);
                    }
                });
    }

*/


}
