package com.aroominn.aroom.model;

import com.aroominn.aroom.base.BaseImpl;

import org.json.JSONObject;

public interface FriendsModel {

    void loadWaiterFriends(BaseImpl context, JSONObject param);

    void loadBossFriends(BaseImpl context, JSONObject param);

    void loadMateFriends(BaseImpl context, JSONObject param);

    /*加载聊天人的信息*/
    void loaFriendInfo(BaseImpl context, JSONObject param);
}
