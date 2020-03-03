package com.aroominn.aroom.view.views;


import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.HomeInfo;
import com.aroominn.aroom.bean.Stories;
import com.aroominn.aroom.bean.Story;
import com.aroominn.aroom.bean.User;

/**
 * Author : yml
 * Created on 2016/5/16.
 */
public interface HomePageView {

    void showError(BasicResponse error, String url);

    /*用户主页发布过的故事*/
    void setHomeStory(Story stories);

    /*用户主页的个人信息*/
    void setHomeInfo(HomeInfo user);

}
