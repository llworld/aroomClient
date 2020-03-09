package com.aroominn.aroom.view.views;

import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.Friend;
import com.aroominn.aroom.bean.User;

public interface FriendsView {


    void showError(BasicResponse error, String url);

    /*用户主页发布过的故事*/
    void setFriends(Friend friends);

    /*聊天页的 用户信息*/
    void setFriendInfo(User user);
}
