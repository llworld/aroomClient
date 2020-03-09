package com.aroominn.aroom.view.views;

import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.Result;

public interface SettingView {

    void onSuccess(Result result);

    void onError(BasicResponse response, String url);
}
