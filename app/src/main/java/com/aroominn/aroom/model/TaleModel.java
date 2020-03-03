package com.aroominn.aroom.model;

import com.aroominn.aroom.base.BaseImpl;

import org.json.JSONObject;

/**
 * 单个故事的操作
 */
public interface TaleModel {

    //    点赞 收藏 举报 拉黑故事
    void likeStory(BaseImpl context, JSONObject param);

    void collocationStory(BaseImpl context, JSONObject param);

    void reportStory(BaseImpl context, JSONObject param);

    void repostStory(BaseImpl context, JSONObject param);

    //    void blacklistStory(BaseImpl context, JSONObject param);
    void forwardStory(BaseImpl context, JSONObject param);

    //    评论故事
    void commentStory(BaseImpl context, JSONObject param);
}
