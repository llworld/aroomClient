package com.aroominn.aroom.model;

import com.aroominn.aroom.base.BaseImpl;

import org.json.JSONObject;

/**
 * 好友
 */
public interface FriendModel {

    //关注朋友
    void followFriend(BaseImpl context, JSONObject param);
//    拉黑朋友
    void blacklistFriend(BaseImpl context, JSONObject param);


}
