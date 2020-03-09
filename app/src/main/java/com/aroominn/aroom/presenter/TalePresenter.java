package com.aroominn.aroom.presenter;


import com.aroominn.aroom.base.BaseImpl;
import com.aroominn.aroom.view.inn.StoryActivity;

import org.json.JSONObject;

/**
 * Created by yml on 2016/5/14.
 */
public interface TalePresenter {


    /*点赞故事*/
    void likeStory(BaseImpl context, JSONObject param);

    /*收藏故事*/
    void collocationStory(BaseImpl context, JSONObject param);

    /*举报*/
    void reportStory(BaseImpl context, JSONObject param);

    /*转发*/
    void repostStory(BaseImpl context, JSONObject param);

    //    void blacklistStory(BaseImpl context, JSONObject param);
    /*转发给好友*/
//    void forwardStory(BaseImpl context, JSONObject param);

    //    评论故事
    void commentStory(BaseImpl context, JSONObject param);

    void deleteTale(BaseImpl context, JSONObject param);
}
