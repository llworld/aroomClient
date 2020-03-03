package com.aroominn.aroom.presenter.listener;


import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.User;

/**
 * Author : yml
 * Created on 2017/12/13.
 */

public interface OnLoginListener {
    /**
     * 成功时回调
     */
    void onSuccess(String code);

    void onSuccess(Boolean result);

    void onSuccess(User user);

    /**
     * 失败时回调，简单处理，没做什么
     */
    void onError(BasicResponse response, String url);

}
