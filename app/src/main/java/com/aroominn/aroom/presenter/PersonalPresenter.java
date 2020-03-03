package com.aroominn.aroom.presenter;

import com.aroominn.aroom.base.BaseImpl;

import org.json.JSONObject;

public interface PersonalPresenter {

    void getUser(BaseImpl content, JSONObject param);

    void getSms(BaseImpl context, JSONObject param);
}
