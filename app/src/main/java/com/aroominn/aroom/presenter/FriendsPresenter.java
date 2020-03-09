package com.aroominn.aroom.presenter;

import com.aroominn.aroom.base.BaseImpl;
import com.aroominn.aroom.view.message.ChatActivity;

import org.json.JSONObject;

public interface FriendsPresenter {

    void getWaiterFriends(BaseImpl context, JSONObject param);

    void getBossFriends(BaseImpl context, JSONObject param);

    void getMateFriends(BaseImpl context, JSONObject param);

    void getUserInfo(BaseImpl context, JSONObject param);
}
