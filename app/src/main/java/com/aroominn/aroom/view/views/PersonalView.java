package com.aroominn.aroom.view.views;

import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.User;

public interface PersonalView {
    void showError(BasicResponse error, String url);

    /*更新用户信息*/
    void setUser(User user);

    /*修改手机号*/
    void setPhoneSms(String sms);
}
