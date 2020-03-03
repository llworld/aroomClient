package com.aroominn.aroom.utils.rongcloud;

import android.net.Uri;
import android.util.Log;

import com.aroominn.aroom.bean.User;
import com.aroominn.aroom.utils.L;
import com.aroominn.aroom.utils.okHttp.OkHttpUtils;
import com.aroominn.aroom.utils.retrofit.RetrofitRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Handler;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserData;
import io.rong.imlib.model.UserInfo;

import static com.aroominn.aroom.utils.UrlTools.SERVERIP;

public class RongCloudUtils {


    public static UserInfo getUserInfoById(final String userId) {
        JSONObject param = new JSONObject();
        try {
            param.put("id", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RetrofitRequest.getApiService()
                .getUserInfo(RetrofitRequest.getJSONBody(param))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        L.e("获取头像"+"onSubscribe");

                    }

                    @Override
                    public void onNext(User userInfo) {
                        if (userInfo != null && userInfo.getData() != null) {
                            User.UserData u = userInfo.getData();
                            String id = u.getId() + "";
                            String name = u.getNick();
                            String head = u.getHead();
                            L.e("获取到用户信息" + name);
                            UserInfo info = new UserInfo(id, name, Uri.parse(head));
                            RongIM.getInstance().refreshUserInfoCache(info);
                        }
//                        Log.e("获取用户信息",userInfo.getHead());
//                        L.e(userInfo.getNick());

                    }

                    @Override
                    public void onError(Throwable e) {
//                        L.e("错误异常。。。。");
                        e.printStackTrace();
//                        Log.e("获取用户信息",e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        L.e("获取用户信息---->onComplete");
                    }
                });


       /* OkHttpUtils.getInstance().post()
                .addParams("userId", "17861983600")
                .url(SERVERIP+"test")
//                .addHeader("token", "")
                .build();

        Uri u = Uri.parse("https://b-ssl.duitang.com/uploads/item/201708/14/20170814171446_VrWyQ.jpeg");
        UserInfo info = new UserInfo(userId, "自己设置的name", u);*/


        return null;
    }
}
