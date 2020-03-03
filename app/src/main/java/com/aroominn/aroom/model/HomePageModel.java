package com.aroominn.aroom.model;

import com.aroominn.aroom.base.BaseImpl;

import org.json.JSONObject;

/**
 * 好友
 */
public interface HomePageModel {

    //关注朋友
    void UserInfo(BaseImpl context, JSONObject param);
//    拉黑朋友
    void HisStories(BaseImpl context, JSONObject param);


}
