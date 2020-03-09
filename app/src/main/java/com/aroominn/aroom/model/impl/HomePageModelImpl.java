package com.aroominn.aroom.model.impl;

import android.widget.RelativeLayout;

import com.aroominn.aroom.base.BaseImpl;
import com.aroominn.aroom.bean.Comment;
import com.aroominn.aroom.bean.HomeInfo;
import com.aroominn.aroom.bean.Result;
import com.aroominn.aroom.bean.Story;
import com.aroominn.aroom.model.HomePageModel;
import com.aroominn.aroom.model.StoryModel;
import com.aroominn.aroom.presenter.listener.OnHomePageListener;
import com.aroominn.aroom.presenter.listener.OnStoryListener;
import com.aroominn.aroom.utils.retrofit.DefaultObserver;
import com.aroominn.aroom.utils.retrofit.RetrofitRequest;

import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.aroominn.aroom.utils.UrlTools.STORYLIST;

/**
 * 所有故事的操作
 */
public class HomePageModelImpl implements HomePageModel {

    private OnHomePageListener listener;

    public HomePageModelImpl(OnHomePageListener listener) {
        this.listener = listener;
    }

    @Override
    public void UserInfo(BaseImpl context, JSONObject param) {
        RetrofitRequest.getApiService()
                .getHomeInfos(RetrofitRequest.getJSONBody(param))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<HomeInfo>(context, false) {
                    @Override
                    public void onSuccess(HomeInfo info) {
                        listener.onSuccess(info);
                    }

                    @Override
                    public void onFail(HomeInfo response) {
                        listener.onError(response, STORYLIST);
                    }
                });
    }

    @Override
    public void HisStories(BaseImpl context, JSONObject param) {
        RetrofitRequest.getApiService()
                .getHomeHisStory(RetrofitRequest.getJSONBody(param))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Story>(context, false) {
                    @Override
                    public void onSuccess(Story info) {
                        listener.onSuccess(info);
                    }

                    @Override
                    public void onFail(Story response) {
                        listener.onError(response, STORYLIST);
                    }
                });
    }

    @Override
    public void loadFollow(BaseImpl context, JSONObject param) {
        RetrofitRequest.getApiService()
                .un_follow(RetrofitRequest.getJSONBody(param))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Result>(context, false) {
                    @Override
                    public void onSuccess(Result info) {
                        listener.onSuccess(info);
                    }

                    @Override
                    public void onFail(Result response) {
                        listener.onError(response, STORYLIST);
                    }
                });
    }

    @Override
    public void collectStories(BaseImpl context, JSONObject param) {
        RetrofitRequest.getApiService()
                .getCollectStory(RetrofitRequest.getJSONBody(param))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Story>(context, false) {
                    @Override
                    public void onSuccess(Story info) {
                        listener.onSuccess(info);
                    }

                    @Override
                    public void onFail(Story response) {
                        listener.onError(response, STORYLIST);
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
