package com.aroominn.aroom.view.message;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.aroominn.aroom.R;
import com.aroominn.aroom.base.BaseActivity;
import com.aroominn.aroom.utils.L;
import com.aroominn.aroom.utils.StatusBarUtil;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class SysMessageActivity extends BaseActivity {



    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarUtil.setPaddingSmart(context,  ((ViewGroup)this.findViewById(android.R.id.content)).getChildAt(0));

    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

        /**
         * https://www.rongcloud.cn/docs/push_service.html#broadcast
         */
        Intent intent=getIntent();
        getPushMessage(intent);

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_sys_message;
    }

    /**
     * Android push 消息
     */
    private void getPushMessage(Intent intent) {

        if (intent != null && intent.getData() != null && intent.getData().getScheme().equals("rong")) {

            //该条推送消息的内容。
            String content = intent.getData().getQueryParameter("pushContent");
            //标识该推送消息的唯一 Id。
            String id = intent.getData().getQueryParameter("pushId");
            //用户自定义参数 json 格式，解析后用户可根据自己定义的 Key 、Value 值进行业务处理。
            String extra = intent.getData().getQueryParameter("extra");
            //统计通知栏点击事件.
//            RongIMClient.recordNotificationEvent(id);

            L.e("TestPushActivity", "--content:" + content + "--id:" + id + "---extra:" + extra);
        }
    }
}
