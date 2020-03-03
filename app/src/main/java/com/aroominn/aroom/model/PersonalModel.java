package com.aroominn.aroom.model;

import com.aroominn.aroom.base.BaseImpl;

import org.json.JSONObject;

public interface PersonalModel {

    /*修改手机号的验证码*/
    void loadSms(BaseImpl content, JSONObject param);

    /*更新用户信息*/
    void loadUser(BaseImpl content, JSONObject param);
}
