package com.aroominn.aroom.presenter.listener;

import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.Result;

public interface OnSettingListener {
    void onSuccess(Result result);

    void onError(BasicResponse response, String url);
}
