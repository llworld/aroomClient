package com.aroominn.aroom.utils.retrofit;

import com.aroominn.aroom.bean.Comment;
import com.aroominn.aroom.bean.Friend;
import com.aroominn.aroom.bean.HomeInfo;
import com.aroominn.aroom.bean.PageHelper;
import com.aroominn.aroom.bean.Result;
import com.aroominn.aroom.bean.Story;
import com.aroominn.aroom.bean.User;
import com.aroominn.aroom.utils.UrlTools;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.rong.imlib.model.UserInfo;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

import static com.aroominn.aroom.utils.UrlTools.STORYUPLOAD;

/**
 * Retrofit工具类
 * Created by yml on 2017/5/3 0009.
 */
public interface RetrofitApi {

    //获取验证码
    @POST(UrlTools.VCODE_URL)
    Observable<Result> getVcode(@Body RequestBody body);

    //注册
    @POST(UrlTools.REGISTER_URL)
    Observable<Result> getRegister(@Body RequestBody body);

    //登录
    @POST(UrlTools.LOGIN_URL)
    Observable<User> getLogin(@Body RequestBody body);

    //获取故事列表
    @POST(UrlTools.STORYLIST)
    Observable<Story> getStory(@Body RequestBody body);

    //主页用户信息
    @POST(UrlTools.HOMEINFOS)
    Observable<HomeInfo> getHomeInfos(@Body RequestBody body);

    /*用户主页历史故事*/
    @POST(UrlTools.HISTORYSTORY)
    Observable<Story> getHomeHisStory(@Body RequestBody body);

    /*用户主页收藏故事*/
    @POST(UrlTools.COLLECTSTORY)
    Observable<Story> getCollectStory(@Body RequestBody body);

    //获取故事评论列表
    @POST(UrlTools.STORYDETAIL)
    Observable<PageHelper> getStoryDetails(@Body RequestBody body);


    //获取用户头像昵称byId
    @POST(UrlTools.FINDUSERINFO)
    Observable<User> getUserInfo(@Body RequestBody body);

    //修改手机的验证码
    @POST(UrlTools.ALTERPHONESMS)
    Observable<Result> getPhoneSms(@Body RequestBody body);

    //修改手机的验证码
    @POST(UrlTools.UPDATEUSERINFO)
    Observable<User> getUser(@Body RequestBody body);


    @Multipart
    @POST(UrlTools.STORYUPLOAD)
    Observable<Result> saveReport(@Part List<MultipartBody.Part> parts);
//    Observable<Result> saveReport(@Part("info") RequestBody info,@Part List<MultipartBody.Part> parts);


    /**
     * 故事的操作
     */
    /*点赞*/
    @POST(UrlTools.LIKETALE)
    Observable<Result> likeTale(@Body RequestBody body);

    /*收藏*/
    @POST(UrlTools.COLLECTTALE)
    Observable<Result> collectTale(@Body RequestBody body);

    /*举报*/
    @POST(UrlTools.REPORTTALE)
    Observable<Result> reportTale(@Body RequestBody body);

    /*转发*/
    @POST(UrlTools.REPOSTTALE)
    Observable<Result> repostTale(@Body RequestBody body);

    /*评论*/
    @POST(UrlTools.COMMENTTALE)
    Observable<Result> commentTale(@Body RequestBody body);
    /*删除*/
    @POST(UrlTools.DELETETALE)
    Observable<Result> deleteTale(@Body RequestBody body);

    /*关注用户*/
    @POST(UrlTools.UN_FOLLOW)
    Observable<Result> un_follow(@Body RequestBody body);

    /*获取我的朋友 我关注的*/
    @POST(UrlTools.MYFOLLOW)
    Observable<Friend> getBossFriends(@Body RequestBody body);


    /*获取我的朋友  关注我的*/
    @POST(UrlTools.FOLLOWME)
    Observable<Friend> getWaiterFriends(@Body RequestBody body);

    /*获取我的朋友  相互关注的*/
    @POST(UrlTools.MUTUALFOLLOW)
    Observable<Friend> getMateFriends(@Body RequestBody body);
    /*获取我的朋友  相互关注的*/
    @POST(UrlTools.FEEDBACK)
    Observable<Result> getFeedBack(@Body RequestBody body);
    @POST(UrlTools.ACCOUNT_LOGIN_URL)
    Observable<User> getAccountLogin(@Body RequestBody jsonBody);
}
