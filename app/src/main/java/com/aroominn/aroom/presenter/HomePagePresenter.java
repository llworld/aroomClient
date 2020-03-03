package com.aroominn.aroom.presenter;


import com.aroominn.aroom.base.BaseImpl;

import org.json.JSONObject;

/**
 * Created by yml on 2016/5/14.
 */
public interface HomePagePresenter {



    void getHomeUser(BaseImpl context, JSONObject param);


    void getHomeStory(BaseImpl context, JSONObject param);

}
