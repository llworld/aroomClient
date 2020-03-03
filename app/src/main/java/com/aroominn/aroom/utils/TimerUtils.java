package com.aroominn.aroom.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.aroominn.aroom.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 发送记时工具类
 * Created by yml on 2016/6/16.
 */
public class TimerUtils {

    private Context mContext;
    public Timer timer = null;
    public TimerTask timerTask = null;
    public int divideTime = 1 * 1000;
    public int timeCount = 60;
    private static final int UPDATE_TEXTVIEW = 99;
    private TextView btn;

    public TimerUtils(Context context, TextView btn) {
        this.mContext = context;
        this.btn = btn;
    }

    /**
     * 设置获取验证码的状态和文字
     */
    public void verifyBtnState(Boolean state, String verifyStr) {
        btn.setText(verifyStr);
        btn.setEnabled(state);
    }

    // 开始计时
    public void startTimer() {
        stopTimer();
        verifyBtnState(false, "");
        if (timer == null) {
            timer = new Timer();
        }
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    Message msg = Message.obtain(handler, UPDATE_TEXTVIEW);
                    handler.sendMessage(msg);
                    timeCount--;
                }
            };
        }
        timer.schedule(timerTask, 0, divideTime);
    }

    // 停止计时
    public void stopTimer() {
        verifyBtnState(true, mContext.getString(R.string.send_again));
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        timeCount = 60;
    }

    // 更新UI
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXTVIEW:
                    if (timeCount == 0) {
                        stopTimer();
                        return;
                    }
                    if (timeCount >= 10) {
                        btn.setText(timeCount + mContext.getString(R.string.send_again_timer_text));
                    } else {
                        btn.setText(mContext.getString(R.string.zero_text) + timeCount + mContext.getString(R.string.send_again_timer_text));
                    }
                    break;
                default:
                    break;
            }
        }
    };
}
