package com.aroominn.aroom.view.views;


import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.HomeInfo;
import com.aroominn.aroom.bean.Result;
import com.aroominn.aroom.bean.Story;

/**
 * Author : yml
 * Created on 2016/5/16.
 */
public interface TaleView {

    void showError(BasicResponse error, String url);

    /*通过状态码判断操作类型 成功与否*/

    /**
     * 点赞 收藏 转发 举报  评论
     *
     * @param result
     */
    void setTale(Result result);

}
