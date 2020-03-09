package com.aroominn.aroom.utils.rongcloud;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.aroominn.aroom.R;
import com.aroominn.aroom.utils.SharedUtils;
import com.aroominn.aroom.view.message.ChatActivity;

import io.rong.imlib.model.Message;
import io.rong.message.ImageMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.aroominn.aroom.base.BaseApplication.getContext;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
public class NotificationChannelUtil {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationChannelUtil() {
        setNone();
        setNoTone();
        setNoVibration();
        setPushToneV();
    }

    public void setPushToneV() {

        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
//            notificationManager.getNotificationChannel("my_channel_tet").enableVibration(false);
        Notification.Builder builder = new Notification.Builder(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // 通知渠道的id
            String id = "my_channel_aroom";
            // 用户可以看到的通知渠道的名字.
            CharSequence name = getContext().getString(R.string.app_name);
            // 用户可以看到的通知渠道的描述
            String description = getContext().getString(R.string.title_personal);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            //注意Name和description不能为null或者""
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // 配置通知渠道的属性
            mChannel.setDescription(description);
            Uri uri = Uri.parse("android.resource://com.aroominn.aroom/" + R.raw.tips);

            mChannel.setSound(uri, Notification.AUDIO_ATTRIBUTES_DEFAULT);
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            // 设置通知出现时的震动（如果 android 设备支持的话）

                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300});


            //最后在notificationmanager中创建该通知渠道
            notificationManager.createNotificationChannel(mChannel);
            builder.setChannelId(id);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setNoTone() {


        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
//            notificationManager.getNotificationChannel("my_channel_tet").enableVibration(false);
        Notification.Builder builder = new Notification.Builder(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            // 通知渠道的id
            String id = "my_channel_noTone";
            // 用户可以看到的通知渠道的名字.
            CharSequence name = getContext().getString(R.string.app_name);
            // 用户可以看到的通知渠道的描述
            String description = getContext().getString(R.string.title_personal);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            //注意Name和description不能为null或者""
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // 配置通知渠道的属性
            mChannel.setDescription(description);
            Uri uri = Uri.parse("android.resource://com.aroominn.aroom/" + R.raw.tips);

            mChannel.setSound(null, Notification.AUDIO_ATTRIBUTES_DEFAULT);
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            // 设置通知出现时的震动（如果 android 设备支持的话）

            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 500, 400, 300});


            //最后在notificationmanager中创建该通知渠道
            notificationManager.createNotificationChannel(mChannel);
        }
    }

    public void setNoVibration() {


        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
//            notificationManager.getNotificationChannel("my_channel_tet").enableVibration(false);
        Notification.Builder builder = new Notification.Builder(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            // 通知渠道的id
            String id = "my_channel_noVibration";
            // 用户可以看到的通知渠道的名字.
            CharSequence name = getContext().getString(R.string.app_name);
            // 用户可以看到的通知渠道的描述
            String description = getContext().getString(R.string.title_personal);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            //注意Name和description不能为null或者""
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // 配置通知渠道的属性
            mChannel.setDescription(description);
            Uri uri = Uri.parse("android.resource://com.aroominn.aroom/" + R.raw.tips);

            mChannel.setSound(uri, Notification.AUDIO_ATTRIBUTES_DEFAULT);
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            // 设置通知出现时的震动（如果 android 设备支持的话）

            mChannel.enableVibration(false);
            mChannel.setVibrationPattern(new long[]{0});


            //最后在notificationmanager中创建该通知渠道
            notificationManager.createNotificationChannel(mChannel);
        }
    }

    public void setNone() {


        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
//            notificationManager.getNotificationChannel("my_channel_tet").enableVibration(false);
        Notification.Builder builder = new Notification.Builder(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            // 通知渠道的id
            String id = "my_channel_none";
            // 用户可以看到的通知渠道的名字.
            CharSequence name = getContext().getString(R.string.app_name);
            // 用户可以看到的通知渠道的描述
            String description = getContext().getString(R.string.title_personal);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            //注意Name和description不能为null或者""
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // 配置通知渠道的属性
            mChannel.setDescription(description);
            Uri uri = Uri.parse("android.resource://com.aroominn.aroom/" + R.raw.tips);

            mChannel.setSound(null, Notification.AUDIO_ATTRIBUTES_DEFAULT);
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            // 设置通知出现时的震动（如果 android 设备支持的话）

            mChannel.enableVibration(false);
            mChannel.setVibrationPattern(new long[]{0});


            //最后在notificationmanager中创建该通知渠道
            notificationManager.createNotificationChannel(mChannel);
        }
    }
}
