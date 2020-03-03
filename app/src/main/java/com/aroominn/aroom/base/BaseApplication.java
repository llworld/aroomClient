package com.aroominn.aroom.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.aroominn.aroom.BuildConfig;
import com.aroominn.aroom.im.MyPrivateConversationProvider;
import com.aroominn.aroom.utils.L;
import com.aroominn.aroom.utils.PublicUtils;
import com.aroominn.aroom.utils.SharedUtils;
import com.aroominn.aroom.utils.retrofit.HttpCacheInterceptor;
import com.aroominn.aroom.utils.retrofit.LoggingInterceptor;
import com.aroominn.aroom.utils.rongcloud.AntihivSystemMessage;
import com.aroominn.aroom.im.IMListener;
//import com.aroominn.aroom.utils.rongcloud.MyPrivateConversationProvider;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//import cn.jiguang.verifysdk.api.JVerificationInterface;
//import cn.jpush.android.api.JPushInterface;
//import cn.jpush.im.android.api.JMessageClient;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import okhttp3.OkHttpClient;

import static com.aroominn.aroom.utils.Const.BUGLY_ID;
import static com.aroominn.aroom.utils.L.isDebug;
import static io.rong.imkit.utils.SystemUtils.getCurProcessName;


/**
 * BaseApplication
 */
public class BaseApplication extends MultiDexApplication {

    public LinkedList<Activity> activitys = new LinkedList<>();
    public static BaseApplication instance;
    public SharedUtils sp;
    public OkHttpClient.Builder okHttpClient;

    public BaseApplication() {
        instance = this;
    }

    public static synchronized Application getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (!getProcessName().equals(BuildConfig.APPLICATION_ID)) {
            return;
        }


//融云
//        RongIMClient.init(this);
//        RongIM.init(this);
//        L.e("融云初始化");

        //注册会话模板
//        RongIM.getInstance().registerConversationTemplate(new MyPrivateConversationProvider());


        /**JPush推送*/
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
        /**JMessage通讯*/
 /*       JMessageClient.setDebugMode(true);
        JMessageClient.init(this);*/
        /**JVerification认证*/
//        JVerificationInterface.init(this);
//        JVerificationInterface.setDebugMode(true);
        /**JSMS短信*/
 /*       SMSSDK.getInstance().initSdk(this);
        SMSSDK.getInstance().setIntervalTime(60000);
        SMSSDK.getInstance().setDebugMode(true);*/
        /**Bugly*/
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(context, BUGLY_ID, isDebug, strategy);


        isDebug = true;
        //Volley
        sp = SharedUtils.getInstance();
        sp.init(this);
//        intiIM();
        initIm();
        L.e("BaseApplication 初始化", ">>>>>>>>>>>>>>>>>>>>>>>>" + SharedUtils.getInstance().getToken() + "");


        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())//添加请求拦截
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .retryOnConnectionFailure(true);

        PublicUtils.initHttpLoggingInterceptor(okHttpClient);
        //Fresco
//        FrescoUtils.init(this, okHttpClient);
//        initEMessage();
    }

    private void intiIM() {
        final String imToken=SharedUtils.getInstance().getToken();
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
            RongIM.init(this);//init Im
            RongIM.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
                @Override
                public void onChanged(ConnectionStatus status) {
                    L.e("MyApplication----->Rongyun onChanged: ---" + status);
                    if (status == ConnectionStatus.TOKEN_INCORRECT) {
                        if (!TextUtils.isEmpty(imToken)) {
                            L.e("进行重连");
                            RongIM.connect(imToken, IMListener.getInstance().getConnectCallback());
                        } else {
                            L.e("token is empty, can not reconnect");
                        }
                    }
                }
            });
        }

    }


    private void initIm() {
        final String imToken = SharedUtils.getInstance().getToken();
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
            RongIM.init(this);//init Im
            RongIMClient.init(this);
            IMListener.init(this);
//            IMUserInfoManager.getInstance().openDB();
            //注册该会话模板
            RongIM.getInstance().registerConversationTemplate(new MyPrivateConversationProvider());
            //注册自定义消息
            RongIM.registerMessageType(AntihivSystemMessage.class);
            RongIM.getInstance().setMessageAttachedUserInfo(true);
//            RongIM.registerMessageType(DeleteFriendsMessage.class);
            //注册消息模板
//            RongIM.getInstance().registerMessageTemplate(new AntihivSystemMessageProvider());
            RongIM.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
                @Override
                public void onChanged(ConnectionStatus status) {
                    L.i("Application----->Rongyun onChanged: ---" + status);
                    if (status == ConnectionStatus.TOKEN_INCORRECT) {
                        if (!TextUtils.isEmpty(imToken)) {
                            RongIM.connect(imToken, IMListener.getInstance().getConnectCallback());
                        } else {
                            L.i("token is empty, can not reconnect");
                        }
                    }
                }
            });
        }

    }


    /**
     * 低版本判断
     *
     * @return
     */
    private boolean isLowMemoryDevice() {
        if (Build.VERSION.SDK_INT >= 19) {
            return ((ActivityManager) getSystemService(ACTIVITY_SERVICE)).isLowRamDevice();
        } else {
            return false;
        }
    }

    /**
     * 添加
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activitys.add(activity);

    }

    /**
     * 退出所有的activity
     */
    public void exit() {
        try {
            for (Activity activity : activitys) {
                if (activity != null) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取进程名
     *
     * @return
     */
    private String getProcessName() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return "";
        }
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return "";
        }
        for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
            if (proInfo.pid == android.os.Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName;
                }
            }
        }
        return "";
    }

}
