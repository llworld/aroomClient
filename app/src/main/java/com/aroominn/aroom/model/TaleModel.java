package com.aroominn.aroom.model;

import com.aroominn.aroom.base.BaseImpl;

import org.json.JSONObject;

/**
 * 单个故事的操作
 */
public interface TaleModel {

    /*点赞故事*/
    void loadLike(BaseImpl context, JSONObject param);

    /*收藏故事*/
    void loadCollect(BaseImpl context, JSONObject param);

    /*举报*/
    void loadReport(BaseImpl context, JSONObject param);

    /*转发*/
    void loadRepost(BaseImpl context, JSONObject param);

    //    void blacklistStory(BaseImpl context, JSONObject param);
    /*转发给好友*/
//    void forwardStory(BaseImpl context, JSONObject param);

    //    评论故事
    void loadComment(BaseImpl context, JSONObject param);

    void loadDelete(BaseImpl context, JSONObject param);
}
