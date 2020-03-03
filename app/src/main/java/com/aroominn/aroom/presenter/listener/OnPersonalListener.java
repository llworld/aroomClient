package com.aroominn.aroom.presenter.listener;

import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.User;

public interface OnPersonalListener {

    void onSuccess(String code);


    void onSuccess(User user);

    void onError(BasicResponse response, String url);

}
