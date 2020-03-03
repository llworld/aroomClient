package com.aroominn.aroom.utils;

/**
 * 请求地址工具类
 */
public class UrlTools {

    public final static String SERVERIP = "http://192.168.1.11:80/";
//    public final static String SERVERIP = "http://192.168.1.6:80/";
    //        public final static String SERVERIP = "http://47.95.245.232/";
    public final static String WEBIP = "http://47.95.122.231:8080/";

    ////////////////////////////////登录注册/////////////////////////////////////
    /**
     * 获取验证码的url
     */
    public final static String VCODE_URL = SERVERIP + "api/jiguang/loginSmsCode";
    /**
     * 注册的url
     */
    public final static String REGISTER_URL = SERVERIP + "api/user/register";
    /**
     * 登陆的url
     */
    public final static String LOGIN_URL = SERVERIP + "api/jiguang/loginValidSmsCode";

    ////////////////////////////////我的/////////////////////////////////////
    /**
     * 加载修改用户信息
     */
    public final static String USERINFO = SERVERIP + "api/user/edit";

    ////////////////////////////////故事/////////////////////////////////////
    /**
     * 获取故事
     */
    public final static String STORYLIST = SERVERIP + "api/inn/all";

    /**
     * 发布故事
     */
    public final static String STORYBREWING = SERVERIP + "api/stories/brewing";

    /**
     * 获取故事评论列表
     */
    public final static String STORYDETAIL = SERVERIP + "api/stories/details";

    /**
     * 获取昵称头像
     */
    public final static String FINDUSERINFO = SERVERIP + "test";

    /**
     * 评论故事
     */
    public final static String STORYCOMMENT = SERVERIP + "api/stories/comment";

    /**
     * 点赞 转发 举报 分享 故事
     */
    public final static String STORYLIKE = SERVERIP + "api/stories/operation";


    public final static String STORYUPLOAD = SERVERIP + "api/stories/upload";
//    public final static String STORYBREWING = SERVERIP + "api/stories/brewing";


    /**
     * 获取主页用户信息
     */
    public final static String HOMEINFOS = SERVERIP + "api/homepage/userinfo";
    /**
     * 获取主页用户故事
     */
    public final static String HISTORYSTORY = SERVERIP + "api/homepage/stories";


    /**
     * 更新用户信息
     */
    public final static String UPDATEUSERINFO = SERVERIP + "api/user/update";
    /**
     * 修改手机号验证按
     */
    public final static String ALTERPHONESMS = SERVERIP + "api/jiguang/alterphone";


    ////////////////////////////////密友/////////////////////////////////////
    /**
     * 关注
     */
    ////////////////////////////////消息/////////////////////////////////////

    /**
     * 邀请链接
     */
//    public static final String H5_SHARE_URL = WEBIP + "index.html#/invite_to_buyers";
}
