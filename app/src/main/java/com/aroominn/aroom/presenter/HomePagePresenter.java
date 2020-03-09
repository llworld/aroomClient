package com.aroominn.aroom.presenter;


import com.aroominn.aroom.base.BaseImpl;
import com.aroominn.aroom.view.inn.HomepageActivity;

import org.json.JSONObject;

/**
 * Created by yml on 2016/5/14.
 */
public interface HomePagePresenter {


    void getHomeUser(BaseImpl context, JSONObject param);


    void getHomeStory(BaseImpl context, JSONObject param);

    void getCollectStory(BaseImpl context, JSONObject param);

    /*关注用户*/
    void follow(BaseImpl context, JSONObject param);
}
