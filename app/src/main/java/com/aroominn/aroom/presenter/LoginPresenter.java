package com.aroominn.aroom.presenter;


import com.aroominn.aroom.base.BaseImpl;
import com.aroominn.aroom.view.login.AccountFragment;

import org.json.JSONObject;

/**
 * Created by yml on 2016/5/14.
 */
public interface LoginPresenter {

    void getVcode(BaseImpl context, JSONObject param);


    void getRegister(BaseImpl context, JSONObject param);


    void getLogin(BaseImpl context, JSONObject param);

    void getAccountLogin(BaseImpl context, JSONObject param);
}
