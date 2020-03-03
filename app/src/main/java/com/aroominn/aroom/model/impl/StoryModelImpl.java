package com.aroominn.aroom.model.impl;

import com.aroominn.aroom.base.BaseImpl;
import com.aroominn.aroom.bean.Comment;
import com.aroominn.aroom.bean.PageHelper;
import com.aroominn.aroom.bean.Story;
import com.aroominn.aroom.model.StoryModel;
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
public class StoryModelImpl implements StoryModel {

    private OnStoryListener listener;

    public StoryModelImpl(OnStoryListener listener) {
        this.listener = listener;
    }

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
                .subscribe(new DefaultObserver<PageHelper>(content, false) {
                    @Override
                    public void onSuccess(PageHelper story) {
                        listener.onSuccess(story);
                    }

                    @Override
                    public void onFail(PageHelper response) {
                        listener.onError(response, STORYLIST);
                    }
                });
    }




}
