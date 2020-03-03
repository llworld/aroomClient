package com.aroominn.aroom.model.impl;

import com.aroominn.aroom.base.BaseImpl;
import com.aroominn.aroom.bean.Comment;
import com.aroominn.aroom.bean.Story;
import com.aroominn.aroom.model.StoryModel;
import com.aroominn.aroom.model.TaleModel;
import com.aroominn.aroom.presenter.listener.OnStoryListener;
import com.aroominn.aroom.presenter.listener.OnTaleListener;
import com.aroominn.aroom.utils.retrofit.DefaultObserver;
import com.aroominn.aroom.utils.retrofit.RetrofitRequest;

import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.aroominn.aroom.utils.UrlTools.STORYLIST;

/**
 * 单个故事的操作
 */
public class TaleModelImpl implements TaleModel {

    private OnTaleListener listener;

    public TaleModelImpl(OnTaleListener listener) {
        this.listener = listener;
    }


    @Override
    public void likeStory(BaseImpl context, JSONObject param) {

    }

    @Override
    public void collocationStory(BaseImpl context, JSONObject param) {

    }

    @Override
    public void reportStory(BaseImpl context, JSONObject param) {

    }

    @Override
    public void repostStory(BaseImpl context, JSONObject param) {

    }

    @Override
    public void forwardStory(BaseImpl context, JSONObject param) {

    }

    @Override
    public void commentStory(BaseImpl context, JSONObject param) {

    }
}
