package com.aroominn.aroom.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;


import com.aroominn.aroom.bean.User;

import java.util.List;


/**
 * Sharedperference工具类
 *
 * @author yml
 */
public class SharedUtils {

    private static SharedUtils sharedUtile = null;
    public static SharedPreferences mPreferences;
    public Editor mEditor;
    private boolean tone;
    private boolean vibration;


    public void init(Context context) {
        mPreferences = context.getSharedPreferences(Const.SHARE_NAME, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public static SharedUtils getInstance() {
        if (sharedUtile == null) {
            sharedUtile = new SharedUtils();
        }
        return sharedUtile;
    }


    public int getInt(String key) {
        return mPreferences.getInt(key, -1);
    }

    public void setInt(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }


    public void setBoolean(String name, Boolean isBoolean) {
        mEditor.putBoolean(name, isBoolean);
        mEditor.commit();
    }

    public boolean getBoolean(String name, Boolean isBoolean) {
        return mPreferences.getBoolean(name, isBoolean);
    }

    public void setString(String name, String value) {
        mEditor.putString(name, value);
        mEditor.commit();
    }

    public String getString(String name, String defaultValue) {
        return mPreferences.getString(name, defaultValue);
    }


    /**
     * 是否登陆
     *
     * @return
     */
    public boolean getLogin() {
        return mPreferences.getBoolean(Const.IS_LOGIN, false)
                && SharedUtils.getInstance().getUserID() != 0
                && !TextUtils.isEmpty(SharedUtils.getInstance().getToken());
    }

    public void setLogin(boolean isLogin) {
        mEditor.putBoolean(Const.IS_LOGIN, isLogin);
        mEditor.commit();
    }

    /**
     * 获取用户ID
     *
     * @return
     */
    public Integer getUserID() {
        return mPreferences.getInt(Const.USER_ID, 0);
    }

    public void setUserID(Integer userid) {
        mEditor.putInt(Const.USER_ID, userid);
        mEditor.commit();
    }


    /**
     * 保存用户对象
     *
     * @return
     */
   public User.UserData getUser() {
        return (User.UserData) ShareSaveUtil.readObject(Const.USER);
    }

    public void setUser(User.UserData value) {
        ShareSaveUtil.saveObject(Const.USER, value);
    }

    /**
     * 保存用户手机
     *
     * @return
     */
    public String getPhoneNum() {
        return mPreferences.getString(Const.USER_PHONE, "");
    }

    public void setPhoneNum(String value) {
        mEditor.putString(Const.USER_PHONE, value);
        mEditor.commit();
    }

    /**
     * 获取用户账号修改状态
     *
     * @return
     */
    public Boolean getCanModifyAccount() {
        return mPreferences.getBoolean(Const.CAN_MODIFY_ACCOUNT, false);
    }

    public void setCanModifyAccount(Boolean canModifyAccount) {
        mEditor.putBoolean(Const.CAN_MODIFY_ACCOUNT, canModifyAccount);
        mEditor.commit();
    }

    /**
     * 保存用户环信号
     *
     * @return
     */
    public String getUserHxAccount() {
        return mPreferences.getString(Const.USER_HX_ACCOUNT, "");
    }

    public void setUserHxAccount(String value) {
        mEditor.putString(Const.USER_HX_ACCOUNT, value);
        mEditor.commit();
    }

    /**
     * 保存token
     *
     * @return
     */
    public String getToken() {
        return mPreferences.getString(Const.USER_TOKEN, "");
    }

    public void setToken(String value) {
        mEditor.putString(Const.USER_TOKEN, value);
        mEditor.commit();
    }

    /**
     * 经度
     *
     * @return
     */
    public String getLongitude() {
        return mPreferences.getString(Const.LONGITUDE_ID, "0.0");
    }

    public void setLongitude(String longitude) {
        mEditor.putString(Const.LONGITUDE_ID, longitude);
        mEditor.commit();
    }


    /**
     * 纬度
     *
     * @return
     */
    public String getLatitude() {
        return mPreferences.getString(Const.LATITUDE_ID, "0.0");
    }

    public void setLatitude(String latitude) {
        mEditor.putString(Const.LATITUDE_ID, latitude);
        mEditor.commit();
    }


    /**
     * 保存ip
     *
     * @return
     */
    public String getIp() {
        return mPreferences.getString(Const.IP, "");
    }

    public void setIp(String value) {
        mEditor.putString(Const.IP, value);
        mEditor.commit();
    }

    /**
     * 保存搜索对象
     *
     * @return
     */
    public List<String> getSearch() {
        return (List<String>) ShareSaveUtil.readObject(Const.SEARCH_LIST);
    }

    public void setSearch(List<String> value) {
        ShareSaveUtil.saveObject(Const.SEARCH_LIST, value);
    }


    /**
     * 是否第一次登陆
     *
     * @return
     */
    public boolean getFirst() {
        return mPreferences.getBoolean(Const.IS_FIRST, true);
    }

    public void setFirst(boolean isFirst) {
        mEditor.putBoolean(Const.IS_FIRST, isFirst);
        mEditor.commit();
    }

    public void setTone(boolean tone) {
        mEditor.putBoolean(Const.TONE, tone);
        mEditor.commit();
    }

    public boolean getTone() {
        return mPreferences.getBoolean(Const.TONE, true);
    }

    public void setVibration(boolean vibration) {
        mEditor.putBoolean(Const.VIBRATION, vibration);
        mEditor.commit();
    }

    public boolean getVibration() {
        return mPreferences.getBoolean(Const.VIBRATION, true);
    }
}
