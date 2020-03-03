package com.aroominn.aroom.presenter.listener;

import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.Comment;
import com.aroominn.aroom.bean.Result;
import com.aroominn.aroom.bean.Story;

/**
 * model DefaultObserver里自定义的 onSuccess方法
 */

public interface OnTaleListener {

//    void onSuccess(Story story);

    void onSuccess(Comment comment);

    void onSuccess(Result result);

    /**
     * 失败时回调，简单处理，没做什么
     */
    void onError(BasicResponse response, String url);
}
