package com.aroominn.aroom.model.impl;

import com.aroominn.aroom.base.BaseImpl;
import com.aroominn.aroom.bean.Comment;
import com.aroominn.aroom.bean.Result;
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
    public void loadLike(BaseImpl context, JSONObject param) {
        RetrofitRequest.getApiService()
                .likeTale(RetrofitRequest.getJSONBody(param))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Result>(context, false) {
                    @Override
                    public void onSuccess(Result result) {
                        listener.onSuccess(result);
                    }

                    @Override
                    public void onFail(Result response) {
                        listener.onError(response, STORYLIST);
                    }
                });
    }

    @Override
    public void loadCollect(BaseImpl context, JSONObject param) {
        RetrofitRequest.getApiService()
                .collectTale(RetrofitRequest.getJSONBody(param))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Result>(context, false) {
                    @Override
                    public void onSuccess(Result result) {
                        listener.onSuccess(result);
                    }

                    @Override
                    public void onFail(Result response) {
                        listener.onError(response, STORYLIST);
                    }
                });
    }

    @Override
    public void loadReport(BaseImpl context, JSONObject param) {
        RetrofitRequest.getApiService()
                .reportTale(RetrofitRequest.getJSONBody(param))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Result>(context, false) {
                    @Override
                    public void onSuccess(Result result) {
                        listener.onSuccess(result);
                    }

                    @Override
                    public void onFail(Result response) {
                        listener.onError(response, STORYLIST);
                    }
                });
    }

    @Override
    public void loadRepost(BaseImpl context, JSONObject param) {
        RetrofitRequest.getApiService()
                .repostTale(RetrofitRequest.getJSONBody(param))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Result>(context, false) {
                    @Override
                    public void onSuccess(Result result) {
                        listener.onSuccess(result);
                    }

                    @Override
                    public void onFail(Result response) {
                        listener.onError(response, STORYLIST);
                    }
                });
    }

    @Override
    public void loadComment(BaseImpl context, JSONObject param) {
        RetrofitRequest.getApiService()
                .commentTale(RetrofitRequest.getJSONBody(param))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Result>(context, false) {
                    @Override
                    public void onSuccess(Result result) {
                        listener.onSuccess(result);
                    }

                    @Override
                    public void onFail(Result response) {
                        listener.onError(response, STORYLIST);
                    }
                });
    }

    @Override
    public void loadDelete(BaseImpl context, JSONObject param) {
        RetrofitRequest.getApiService()
                .deleteTale(RetrofitRequest.getJSONBody(param))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Result>(context, false) {
                    @Override
                    public void onSuccess(Result result) {
                        listener.onSuccess(result);
                    }

                    @Override
                    public void onFail(Result response) {
                        listener.onError(response, STORYLIST);
                    }
                });
    }
}
