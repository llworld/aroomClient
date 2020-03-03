package com.aroominn.aroom.view.views;

import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.Comment;
import com.aroominn.aroom.bean.PageHelper;
import com.aroominn.aroom.bean.Result;
import com.aroominn.aroom.bean.Story;

public interface StoryView {

    void showError(BasicResponse error, String url);

    void setStory(Story story);

    void setStoryDetails(PageHelper comment);

    void setResult(Result result);

}
