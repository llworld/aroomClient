package com.aroominn.aroom.presenter;

import com.aroominn.aroom.base.BaseImpl;

import org.json.JSONObject;

public interface StoryPresenter {

    void getStory(BaseImpl context, JSONObject param);
    void getStoryDetails(BaseImpl context, JSONObject param);
    void putLike(BaseImpl context,JSONObject param);
    void commentStory(BaseImpl context,JSONObject param);

}
