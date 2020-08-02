package com.aroominn.aroom.im;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.aroominn.aroom.R;
import com.aroominn.aroom.utils.L;
import com.aroominn.aroom.utils.PublicUtils;
import com.aroominn.aroom.utils.SharedUtils;
import com.aroominn.aroom.utils.SoundUtils;
import com.aroominn.aroom.utils.rongcloud.RongCloudUtils;
import com.aroominn.aroom.view.inn.HomepageActivity;
import com.aroominn.aroom.view.message.ChatActivity;

import java.util.ArrayList;

import io.rong.imkit.RongIM;
import io.rong.imkit.RongMessageItemLongClickActionManager;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.plugin.location.IUserInfoProvider;
import io.rong.imkit.widget.provider.MessageItemLongClickAction;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ContactNotificationMessage;
import io.rong.message.GroupNotificationMessage;
import io.rong.message.ImageMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;
import static com.aroominn.aroom.base.BaseApplication.getContext;

public class IMListener implements RongIM.ConversationBehaviorListener, RongIM.ConversationListBehaviorListener, RongIMClient.ConnectionStatusListener, RongIM.UserInfoProvider, RongIM.GroupInfoProvider, RongIMClient.OnReceiveMessageListener, RongIM.IGroupMembersProvider, RongIM.ConversationClickListener {
    public static final String TAG = "IMListener";

    private Context mContext;
    private static IMListener mInstance;


    public static IMListener getInstance() {
        return mInstance;
    }

    private static ArrayList<Activity> mActivities;

    public IMListener(Context mContext) {
        this.mContext = mContext;
        initListener();
        mActivities = new ArrayList<>();
    }

    public static void init(Context context) {
        if (mInstance == null) {
            synchronized (IMListener.class) {
                if (mInstance == null) {
                    mInstance = new IMListener(context);
                }
            }
        }
    }

    private void initListener() {
//        RongIM.setConversationBehaviorListener(this); 已过期
        //设置会话界面操作的监听器。
        RongIM.setConversationClickListener(this);
        RongIM.setConversationListBehaviorListener(this);
        RongIM.setConversationListBehaviorListener(this);
        RongIM.setConnectionStatusListener(this);
        RongIM.setUserInfoProvider(this, true);
        RongIM.setGroupInfoProvider(this, true);
//        RongIM.setLocationProvider(this);//设置地理位置提供者,不用位置的同学可以注掉此行代码
        RongIM.setOnReceiveMessageListener(this);//重点收到消息的回调

        setInputProvider();
        setReadReceiptConversationType();
        RongIM.getInstance().enableNewComingMessageIcon(true);
        RongIM.getInstance().enableUnreadMessageIcon(true);
        RongIM.getInstance().setGroupMembersProvider(this);
        setMessageItemLongClickAction(mContext);
    }

    private static void setMessageItemLongClickAction(Context context) {
        MessageItemLongClickAction action = new MessageItemLongClickAction.Builder()
                .titleResId(R.string.rc_dialog_item_message_delete)
                .actionListener(new MessageItemLongClickAction.MessageItemLongClickListener() {
                    @Override
                    public boolean onMessageItemLongClick(Context context, UIMessage message) {
                        Message[] messages = new Message[1];
                        messages[0] = message.getMessage();
                        RongIM.getInstance().deleteMessages(new int[]{message.getMessageId()}, null);
                        return false;
                    }
                }).build();
        RongMessageItemLongClickActionManager.getInstance().addMessageItemLongClickAction(action, 1);
    }

    private void setReadReceiptConversationType() {
        Conversation.ConversationType[] types = new Conversation.ConversationType[]{
                Conversation.ConversationType.PRIVATE,
                Conversation.ConversationType.GROUP,
                Conversation.ConversationType.DISCUSSION
        };
        RongIM.getInstance().setReadReceiptConversationTypeList(types);
    }

    /**
     * 设置输入面板
     */
    private void setInputProvider() {

        // 没有特殊需求就忽略
//        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
//        IExtensionModule defaultModule = null;
//        if (moduleList != null) {
//            for (IExtensionModule module : moduleList) {
//                if (module instanceof DefaultExtensionModule) {
//                    defaultModule = module;
//                    break;
//                }
//            }
//            if (defaultModule != null) {
//                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
//                RongExtensionManager.getInstance().registerExtensionModule(new SealExtensionModule(mContext));
//            }
//        }
    }

    public RongIMClient.ConnectCallback getConnectCallback() {
        RongIMClient.ConnectCallback connectCallback = new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                //token 错误
                L.i("ImService----->onTokenIncorrect: 融云token错误");
//                ImService.start(mContext, AppConstant.UPDATE_TOKEN);
            }

            @Override
            public void onSuccess(String userid) {
                L.i("IMListener----->onSuccess: " + userid);

            }

            @Override
            public void onError(final RongIMClient.ErrorCode e) {
                L.i("IMListener----->onError: " + e);
            }
        };
        return connectCallback;
    }


    /******************   融云回调   ******************/
    /*---------------   消息交互   --------------*/
    @Override
    public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        //点击头像 这里群组默认都是专家
        if (conversationType == Conversation.ConversationType.CUSTOMER_SERVICE || conversationType == Conversation.ConversationType.PUBLIC_SERVICE || conversationType == Conversation.ConversationType.APP_PUBLIC_SERVICE) {
            return false;
        }
        //开发测试时,发送系统消息的userInfo只有id不为空
        if (userInfo != null && userInfo.getName() != null && userInfo.getPortraitUri() != null) {
            String mTargetId = userInfo.getUserId();
            if (mTargetId.length() >= 6) {
                try {
                    String id = mTargetId.substring(5, mTargetId.length());
//                    AcardInfoActivity.show(context, id, AcardInfoActivity.ACARD_FRIENDS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                L.i("ChatReportActivity----->initBundleData: targetId is not right——>" + mTargetId);
            }

        }
        return true;
    }

    @Override
    public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        return false;
    }

    @Override
    public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String userId) {

        //在这里处理你想要跳转的activity，
        //userId

        Intent in = new Intent(context, HomepageActivity.class);
        in.putExtra("targetId", userInfo.getUserId());
        in.putExtra("title", userInfo.getName());
        context.startActivity(in);
        return false;
    }

    @Override
    public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {
        return false;
    }

    @Override
    public boolean onMessageClick(Context context, View view, Message message) {
        return false;
    }

    @Override
    public boolean onMessageLinkClick(Context context, String s, Message message) {
        return false;
    }

    @Override
    public boolean onMessageLinkClick(Context context, String s) {
        return false;
    }

    @Override
    public boolean onMessageLongClick(Context context, View view, Message message) {
        return false;
    }
    /*---------------   消息交互   --------------*/

    /*---------------   会话的交互   --------------*/
    @Override
    public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {

        return false;
    }

    @Override
    public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
        return false;
    }

    @Override
    public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
        return false;
    }

    @Override
    public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
        return false;
    }

    /*---------------   会话的交互   --------------*/
    @Override
    public void onChanged(ConnectionStatus connectionStatus) {
        L.d(TAG, "ConnectionStatus onChanged = " + connectionStatus.getMessage());
        if (connectionStatus.equals(ConnectionStatus.KICKED_OFFLINE_BY_OTHER_CLIENT)) {
//            GlobalDialogActivity.start(mContext);
        } else if (connectionStatus == ConnectionStatus.TOKEN_INCORRECT) {
//            SharedPreferences sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
//            final String cacheToken = sp.getString("loginToken", "");
//            if (!TextUtils.isEmpty(cacheToken)) {
//                RongIM.connect(cacheToken, getConnectCallback());
//            } else {
//                Log.e("seal", "token is empty, can not reconnect");
//            }
        }
    }


    @Override
    public UserInfo getUserInfo(String userId) {
//        IMUserInfoManager.getInstance().getUserInfo(userId);
        /**异步请求 返回null即可
         * 异步请求结束后
         * 根据返回的结果调用 {@link #refreshUserInfoCache(UserInfo)} 刷新用户信息
         */

        Uri u = Uri.parse("https://b-ssl.duitang.com/uploads/item/201708/14/20170814171446_VrWyQ.jpeg");
        UserInfo info = new UserInfo(userId, "自己设置的name", u);
        L.e("开始获取用户信息");

        RongCloudUtils.getUserInfoById(userId);




/*
            new IUserInfoProvider.UserInfoCallback() {

                @Override
                public void onGotUserInfo(UserInfo userInfo) {

                    L.e("这是什么的useInfo" + userInfo.getName());
                }
            };*/

        return null;

    }


    @Override
    public Group getGroupInfo(String groupId) {
        /*String url = AppConstant.URL_STUDIO_TEAM_INFO + "?teamRcId=" + groupId;
        HttpUtil.fastGet(url, this, new GsonCallBack<StudioTeamInfoBean>() {
            @Override
            public void onSuccess(StudioTeamInfoBean bean) {
                StudioTeamInfoBean.DataBean data = bean.getData();
                Group groupInfo = new Group(data.getTeamRcId(), data.getName(), Uri.parse(AppConstant.BASE_URL + data.getPortrait()));
                RongIM.getInstance().refreshGroupInfoCache(groupInfo);
            }

            @Override
            public void onError(Exception e, int erroCode) {
                L.d(TAG, "getGroupInfo==" + e.getMessage());
            }
        });*/
        return null;
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public boolean onReceived(Message message, int i) {
        L.e("收到的消息" + message);

        if (SharedUtils.getInstance().getTone()) {
//            SoundUtils.playSound(R.raw.tips);
        }

        Vibrator vibrator = (Vibrator) getContext().getSystemService(VIBRATOR_SERVICE);
        if (SharedUtils.getInstance().getVibration()) {
//            vibrator.vibrate(200);
        }

        /**
         * Message{
         * conversationType=PRIVATE,
         * targetId='521',
         * messageId=7,
         * messageDirection=RECEIVE,
         * senderUserId='521',
         * receivedStatus=io.rong.imlib.model.Message$ReceivedStatus@ff93dc,
         * sentStatus=SENT,
         * receivedTime=1582283612672,
         * sentTime=1582283613342,
         * objectName='RC:TxtMsg',
         * content=TextMessage{content='hello', extra='helloExtra'},
         * extra='null',
         * readReceiptInfo=io.rong.imlib.model.ReadReceiptInfo@4072ce5,
         * UId='BGCT-PU97-HGE4-FUKF'
         * }
         */

        // 如果应用在后台 就弹出悬浮窗
        if (PublicUtils.isApplicationInBackground(getContext())) {

            NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
//            notificationManager.getNotificationChannel("my_channel_tet").enableVibration(false);
            Notification.Builder builder = new Notification.Builder(getContext());

            if (SharedUtils.getInstance().getVibration() && SharedUtils.getInstance().getTone()) {
                builder.setChannelId("my_channel_aroom");
            }
            if (!SharedUtils.getInstance().getVibration() && SharedUtils.getInstance().getTone()) {
                builder.setChannelId("my_channel_noVibration");
            }
            if (SharedUtils.getInstance().getVibration() && !SharedUtils.getInstance().getTone()) {
                builder.setChannelId("my_channel_noTone");
            }
            if (!SharedUtils.getInstance().getVibration() && !SharedUtils.getInstance().getTone()) {
                builder.setChannelId("my_channel_none");
            }


            builder.setContentInfo("补充内容");
            // 判断消息类型  根据自己需求进行判断
            if (message.getContent() instanceof TextMessage) {
                String textMessageContent = ((TextMessage) message.getContent()).getContent();
                builder.setContentText(textMessageContent);
                builder.setContentTitle("有新的消息");

            } else if (message.getContent() instanceof ImageMessage) {
                builder.setContentText("图片消息");
            } else if (message.getContent() instanceof VoiceMessage) {
                builder.setContentText("语音消息");
            } else {
                builder.setContentText("其他消息");
            }
            //设置弹窗以及通知栏显示内容
            /**暂时无法获取用户昵称  考虑后期全部缓存
             *
             */
            builder.setContentTitle("有新的消息");
//            builder.setContentTitle(message.getContent().toString());
            builder.setSmallIcon(R.mipmap.logo);
            builder.setTicker("新消息");
            // 是否自动消失
            builder.setAutoCancel(true);
            // 是否显示时间
            builder.setShowWhen(true);
            // 设置接收时间
            builder.setWhen(message.getReceivedTime());

            // 设置会话界面
            Intent intent = new Intent(getContext(), ChatActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri;

            uri = Uri.parse("rong://" + getContext().getPackageName()).buildUpon()
                    .appendPath("conversation").appendPath(message.getConversationType().getName().toLowerCase())
                    .appendQueryParameter("title", "用户名字")
//                    .appendQueryParameter("title", message.getContent().getUserInfo().getName())
                    .appendQueryParameter("targetId", message.getTargetId())
//                    .appendQueryParameter("targetId", message.getContent().getUserInfo().getUserId())
                    .build();
            intent.setData(uri);
            PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            builder.setContentIntent(pendingIntent);
            Notification notification = builder.build();
            notificationManager.notify(1, notification);

            // 设置自己本地的通知铃声有几种实现方式 （声音文件自己本地创建好，在res文件夹下面创建好raw文件夹，把声音文件放进去）
            // 1 利用系统媒体播放器播放本地文件
//        MediaPlayer player = MediaPlayer.create(getContext(), R.raw.sound);
//        player.start();
            // 2 工具类实现方式

            return true;
        }




        /*return false  可接收消息的推送
         * return true 应该和push推送冲突*/
        return true;
    }


    @Override
    public void getGroupMembers(String groupId, final RongIM.IGroupMemberCallback callback) {
//获取群成员   数据库+网络
     /*   String url = AppConstant.URL_STUDIO_TEAM_GET_GROUP_LIST;
        HttpUtil.fastGet(url + "?teamRcId=" + groupId + "&pageSize=200", this, new GsonCallBack<StudioGroupMemberBean>() {
            @Override
            public void onSuccess(StudioGroupMemberBean bean) {
                StudioGroupMemberBean.DataBean data = bean.getData();
                if (data != null) {
                    List<StudioGroupMemberBean.DataBean.ListBean> list = data.getList();
                    if (CheckUtil.isNotNull(list)) {
                        List<UserInfo> userInfos = new ArrayList<>();
                        for (StudioGroupMemberBean.DataBean.ListBean listBean : list) {
                            UserInfo userInfo = new UserInfo(listBean.getId(), listBean.getName(), Uri.parse(AppConstant.BASE_URL + listBean.getPortrait()));
                            userInfos.add(userInfo);
                        }
                        callback.onGetGroupMembersResult(userInfos);

                    }
                }
            }

            @Override
            public void onError(Exception e, int erroCode) {
                callback.onGetGroupMembersResult(null);
            }
        });*/
    }

}