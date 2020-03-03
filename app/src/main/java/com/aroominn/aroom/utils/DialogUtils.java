package com.aroominn.aroom.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aroominn.aroom.R;


public class DialogUtils {


    /**
     * 自定义加载数据的对话框
     */
    public static Dialog MyProgressDialog(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.loading_progress_dialog, null);
        Dialog loadDialog = new Dialog(context, R.style.loading_dialog);
        loadDialog.setCanceledOnTouchOutside(false);
        loadDialog.setContentView(view);
        return loadDialog;
    }


    /**
     * 自定义可改变提示信息的对话框
     */
    public static Dialog MyProgressDialogWithText(Activity activity, String loadStr) {
        View view = LayoutInflater.from(activity).inflate(R.layout.loading_progress_dialog, null);
        view.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.corners_bg));
        TextView loadTv = (TextView) view.findViewById(R.id.loading_process_tv);
        loadTv.setText(loadStr);
        loadTv.setVisibility(View.VISIBLE);
        Dialog loadDialog = new Dialog(activity, R.style.loading_dialog);
        loadDialog.setCanceledOnTouchOutside(false);
        LinearLayout.LayoutParams params = null;
        if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 横屏下
            params = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth() / 3, ScreenUtils.getScreenHeight() / 3);
        } else {
            // 竖屏下
            params = new LinearLayout.LayoutParams(ScreenUtils.getScreenHeight() / 4, ScreenUtils.getScreenHeight() / 5);

        }
        loadDialog.setContentView(view, params);
        return loadDialog;
    }

    /**
     * 自定义选择对话框(动态传入提示信息)
     */
    public static Dialog MyChoseDialog(Activity activity, View.OnClickListener clickListener, int loadStr) {
        View view = LayoutInflater.from(activity).inflate(R.layout.choose_dialog_layout, null);
        TextView cancelTv = (TextView) view.findViewById(R.id.choose_dialog_cancel_tv);
        TextView sureTv = (TextView) view.findViewById(R.id.choose_dialog_sure_tv);
        TextView loadTv = (TextView) view.findViewById(R.id.choose_dialog_title_tv);
        loadTv.setText(loadStr);
        cancelTv.setOnClickListener(clickListener);
        sureTv.setOnClickListener(clickListener);
        Dialog myChoseDialog = new Dialog(activity, R.style.other_dialog);
        myChoseDialog.setCanceledOnTouchOutside(false);
        LinearLayout.LayoutParams params = null;
        if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 横屏下
            params = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth() / 3, ScreenUtils.getScreenHeight() / 3);
        } else {
            // 竖屏下
            params = new LinearLayout.LayoutParams(ScreenUtils.getScreenHeight() / 3, ScreenUtils.getScreenHeight() / 5);
        }
        myChoseDialog.setContentView(view, params);
        return myChoseDialog;
    }

    /**
     * 自定义选择对话框(自定义按钮)
     */
    public static Dialog MyChoseDialog(Activity activity, View.OnClickListener clickListener, int loadStr, int sureStr, int cancelStr) {
        View view = LayoutInflater.from(activity).inflate(R.layout.choose_dialog_layout, null);
        TextView cancelTv = (TextView) view.findViewById(R.id.choose_dialog_cancel_tv);
        TextView sureTv = (TextView) view.findViewById(R.id.choose_dialog_sure_tv);
        TextView loadTv = (TextView) view.findViewById(R.id.choose_dialog_title_tv);
        loadTv.setText(loadStr);
        sureTv.setText(sureStr);
        cancelTv.setText(cancelStr);
        cancelTv.setOnClickListener(clickListener);
        sureTv.setOnClickListener(clickListener);
        Dialog myChoseDialog = new Dialog(activity, R.style.other_dialog);
        myChoseDialog.setCanceledOnTouchOutside(false);
        LinearLayout.LayoutParams params = null;
        if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 横屏下
            params = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth() / 3, ScreenUtils.getScreenHeight() / 3);
        } else {
            // 竖屏下
            params = new LinearLayout.LayoutParams(ScreenUtils.getScreenHeight() / 3, ScreenUtils.getScreenHeight() / 5);
        }
        myChoseDialog.setContentView(view, params);
        return myChoseDialog;
    }


    /**
     * 自定义升级对话框
     */
    public static Dialog MyUpdateDialog(Activity activity, View.OnClickListener clickListener,
                                        String messageStr, int sureStr, int cancelStr, int mode) {
        View view = LayoutInflater.from(activity).inflate(R.layout.update_dialog_layout, null);
        TextView cancelTv = (TextView) view.findViewById(R.id.update_dialog_cancel);
        TextView sureTv = (TextView) view.findViewById(R.id.update_dialog_sure);
        TextView message = (TextView) view.findViewById(R.id.update_dialog_message_tv);
        View diverCenter = view.findViewById(R.id.update_center_line);

        message.setText(messageStr);
        sureTv.setText(sureStr);
        cancelTv.setText(cancelStr);
        if (mode == 1) {
            cancelTv.setVisibility(View.GONE);
//            sureTv.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.update_dialog_cancel_bg));
            diverCenter.setVisibility(View.GONE);
        } else {
            cancelTv.setVisibility(View.VISIBLE);
//            sureTv.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.choose_dialog_cancel_bg));
            diverCenter.setVisibility(View.VISIBLE);
        }
        cancelTv.setOnClickListener(clickListener);
        sureTv.setOnClickListener(clickListener);
        Dialog myChoseDialog = new Dialog(activity, R.style.other_dialog);
        myChoseDialog.setCanceledOnTouchOutside(false);
        LinearLayout.LayoutParams params = null;
        if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 横屏下
            params = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth() / 3, ScreenUtils.getScreenHeight() / 3);
        } else {
            // 竖屏下
            params = new LinearLayout.LayoutParams(ScreenUtils.getScreenHeight() / 3, ScreenUtils.getScreenHeight() / 4);
        }
        myChoseDialog.setContentView(view, params);
        return myChoseDialog;
    }
}
