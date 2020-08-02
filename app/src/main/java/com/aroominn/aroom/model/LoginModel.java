package com.aroominn.aroom.model;

import com.aroominn.aroom.base.BaseImpl;

import org.json.JSONObject;

/**
 * 登录 注册 验证码
 */
public interface LoginModel {

    void loadVcode(BaseImpl context, JSONObject param);

    void loadRegister(BaseImpl context, JSONObject param);

    void loadLogin(BaseImpl context, JSONObject param);

    void loadAccountLogin(BaseImpl context, JSONObject param);
}
