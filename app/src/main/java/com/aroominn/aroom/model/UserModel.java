package com.aroominn.aroom.model;

import com.aroominn.aroom.base.BaseImpl;

import org.json.JSONObject;

/**
 * 用户相关操作
 */
public interface UserModel {

    //    修改加载用户信息
    void loadUserInfo(BaseImpl context, JSONObject param);
}
