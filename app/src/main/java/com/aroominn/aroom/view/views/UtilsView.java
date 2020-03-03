package com.aroominn.aroom.view.views;

import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.Result;

public interface UtilsView {

    void showError(BasicResponse error, String url);

    //发布故事的回调
    void setStory(Result story);
}
