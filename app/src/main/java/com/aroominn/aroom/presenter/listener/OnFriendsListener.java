package com.aroominn.aroom.presenter.listener;


import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.Friend;
import com.aroominn.aroom.bean.HomeInfo;
import com.aroominn.aroom.bean.Result;
import com.aroominn.aroom.bean.Story;
import com.aroominn.aroom.bean.User;


public interface OnFriendsListener {
    /**
     * 成功时回调
     */
    void onSuccess(Friend friend);
    void onSuccess(User user);


    /**
     * 失败时回调，简单处理，没做什么
     */
    void onError(BasicResponse response, String url);

}
