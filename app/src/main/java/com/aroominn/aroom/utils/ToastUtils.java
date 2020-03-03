package com.aroominn.aroom.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aroominn.aroom.R;
import com.aroominn.aroom.base.BaseApplication;


/**
 * Toast工具类
 */
public class ToastUtils {

    public static final int DURATION = Toast.LENGTH_LONG;
    private static Toast centerToast;

    /**
     * 系统默认样式底部toast
     *
     * @param context
     * @param content
     * @param duration
     */
    public static Toast showBottomDurationToast(Context context, String content, int duration) {

        Toast toast = new Toast(context);
        View v = LayoutInflater.from(context).inflate(R.layout.toast_beautiful, null);
        TextView tv = ((TextView) v.findViewById(R.id.toast_beautiful_tv));
        toast.setView(v);

        tv.setText(content);
        toast.setDuration(duration);

        return toast;

    }

    /**
     * 中部toast
     *
     * @param context
     * @param content
     * @param duration
     * @return
     */
    public static Toast showCenterDurationToast(Context context, String content, int duration) {
        if (centerToast != null) {
            centerToast.cancel();
        }
        centerToast = new Toast(context);
        View v = LayoutInflater.from(context).inflate(R.layout.toast_beautiful, null);
        centerToast.setView(v);

        ((TextView) centerToast.getView().findViewById(R.id.toast_beautiful_tv)).setText(content);
        centerToast.setGravity(Gravity.CENTER, 0, 0);
        centerToast.setDuration(duration);
        return centerToast;
    }



    public static void showCenterToast(Context context, String content) {
        showCenterDurationToast(context, content, DURATION).show();
    }

    public static void showCenterToast(Context context, int stirngId) {
        showCenterDurationToast(context, context.getString(stirngId), DURATION).show();
    }

    public static void showBottomToast(Context context, String content) {
        showBottomDurationToast(context, content, DURATION).show();
    }


    public static void showBottomToast(Context context, int stringId) {
        showBottomDurationToast(context, context.getString(stringId), DURATION).show();
    }

    public static void showBottomToast(int stringId,int duration) {
        showBottomDurationToast(BaseApplication.getContext(), BaseApplication.getContext().getString(stringId), duration).show();
    }

    public static void showBottomToast(String content, int duration) {
        showBottomDurationToast(BaseApplication.getContext(), content, duration).show();
    }

}
