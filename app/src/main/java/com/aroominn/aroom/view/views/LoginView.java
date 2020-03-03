package com.aroominn.aroom.view.views;


import com.aroominn.aroom.base.BasicResponse;
import com.aroominn.aroom.bean.User;

/**
 * Author : yml
 * Created on 2016/5/16.
 */
public interface LoginView {

    void showError(BasicResponse error, String url);

    void setVcode(String vcode);

    void setRegister(Boolean result);

    void setLogin(User user);

}
