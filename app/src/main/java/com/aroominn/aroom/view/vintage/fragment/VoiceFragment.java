package com.aroominn.aroom.view.vintage.fragment;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.aroominn.aroom.R;
import com.aroominn.aroom.base.BaseFragment;
import com.flyco.roundview.RoundTextView;
import com.yorhp.audiolibrary.AudioRecordUtil;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class VoiceFragment extends BaseFragment {

    @BindView(R.id.voice_record)
    RoundTextView textView;

    private AudioRecordUtil recordUtil;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_voice;
    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {

        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        /**
                         * 添加动态设置权限
                         */
                        recordUtil.startRecord(context);
                       /* y = event.getRawY();
                        time = System.currentTimeMillis();
                        mPop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                        try {
                            recordUtil.startRecord(MainActivity.this);
                        } catch (RuntimeException e) {
                            e.printStackTrace();
                            Snackbar.make(btn_audio, "先允许调用系统录音权限", Snackbar.LENGTH_SHORT).show();
                        }*/
                        break;
                    case MotionEvent.ACTION_UP:
                        recordUtil.stopRecord(context);        //结束录音（保存录音文件）
                        break;
                  /*  case MotionEvent.ACTION_UP:
                        if (System.currentTimeMillis() - time < 1000) {
                            Snackbar.make(btn_audio, "录音时间过短，请重试", Snackbar.LENGTH_SHORT).show();
                            recordUtil.cancelRecord(MainActivity.this);
                            mPop.dismiss();
                            break;
                        } else if (y - event.getRawY() > 300) {
                            Snackbar.make(btn_audio, "已取消录制语音", Snackbar.LENGTH_SHORT).show();
                            recordUtil.cancelRecord(MainActivity.this);
                            mPop.dismiss();
                            break;
                        } else {
                            try {
                                recordUtil.stopRecord(MainActivity.this);        //结束录音（保存录音文件）
                            } catch (Exception e) {
                                e.printStackTrace();
                                Snackbar.make(btn_audio, "先允许调用系统录音权限", Snackbar.LENGTH_SHORT);
                            }
                            mPop.dismiss();
                            break;
                        }*/
                    case MotionEvent.ACTION_CANCEL:
                        recordUtil.cancelRecord(context); //取消录音（不保存录音文件）
//                        mPop.dismiss();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        AudioRecordUtil.init(Environment.getExternalStorageDirectory() + "/audio/");
        recordUtil = AudioRecordUtil.getInstance();

        record();

//        PermissonUtil.checkPermission(this, null, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }

    private void record() {
        AudioRecordUtil.getInstance().setOnAudioStatusUpdateListener(new AudioRecordUtil.OnAudioStatusUpdateListener() {
            @Override
            public void onUpdate(double db, long time) {
                //根据分贝值来设置录音时话筒图标的上下波动，下面有讲解
//                mImageView.getDrawable().setLevel((int) (3000 + 6000 * db / 100));
//                mTextView.setText(TimeUtil.getTimeE(time).substring(14, 19));
            }

            @Override
            public void onStop(final String filePath) {
                /*Snackbar.make(btn_audio, "语音保存路径为：" + filePath, Snackbar.LENGTH_SHORT).setAction("查看", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, filePath, Toast.LENGTH_LONG).show();
                    }
                }).show();*/
            }
        });
    }

    @Override
    public void initTitle(View view, Bundle savedInstanceState) {

    }
}
