package com.aroominn.aroom.presenter.listener;


import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.HomeInfo;
import com.aroominn.aroom.bean.Story;
import com.aroominn.aroom.bean.User;


public interface OnHomePageListener {
    /**
     * 成功时回调
     */
    void onSuccess(Story story);



    void onSuccess(HomeInfo info);


    /**
     * 失败时回调，简单处理，没做什么
     */
    void onError(BasicResponse response, String url);

}
