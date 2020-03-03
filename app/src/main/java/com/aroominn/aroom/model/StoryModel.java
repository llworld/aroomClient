package com.aroominn.aroom.model;

import com.aroominn.aroom.base.BaseImpl;

import org.json.JSONObject;

/**
 * 所有故事的操作
 */
public interface StoryModel {

    //    加载故事
    void loadStory(BaseImpl context, JSONObject param);

    /*加载评论*/
    void loadStoryDetails(BaseImpl content, JSONObject param);




}
