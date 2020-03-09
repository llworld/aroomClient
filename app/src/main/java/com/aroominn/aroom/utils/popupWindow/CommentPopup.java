package com.aroominn.aroom.utils.popupWindow;


import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.aroominn.aroom.R;

import razerdp.basepopup.BasePopupWindow;

public class CommentPopup extends BasePopupWindow implements View.OnClickListener {

    private TextView delete;
    private TextView report;

    private View mLikeClikcLayout;
    private View mCommentClickLayout;


    private OnCommentPopupClickListener mOnCommentPopupClickListener;

    private Handler mHandler;

    boolean isMe;

    public CommentPopup(Context context, boolean isMe) {
        super(context);
        mHandler = new Handler();
        this.isMe = isMe;
        delete = findViewById(R.id.popup_comment_delete);
        report = (TextView) findViewById(R.id.popup_comment_report);

        if (isMe) {
            report.setVisibility(View.GONE);
        } else {
            delete.setVisibility(View.GONE);
        }


        delete.setOnClickListener(this);
        report.setOnClickListener(this);

        buildAnima();
        setBackground(0);
        setAllowDismissWhenTouchOutside(true);
        setPopupGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        setBlurBackgroundEnable(true);
    }

    private AnimationSet mAnimationSet;

    private void buildAnima() {
        ScaleAnimation mScaleAnimation = new ScaleAnimation(1f, 2f, 1f, 2f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mScaleAnimation.setDuration(200);
        mScaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        mScaleAnimation.setFillAfter(false);

        AlphaAnimation mAlphaAnimation = new AlphaAnimation(1, .2f);
        mAlphaAnimation.setDuration(400);
        mAlphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        mAlphaAnimation.setFillAfter(false);

        mAnimationSet = new AnimationSet(false);
        mAnimationSet.setDuration(400);
        mAnimationSet.addAnimation(mScaleAnimation);
        mAnimationSet.addAnimation(mAlphaAnimation);
        mAnimationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, 150);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    @Override
    protected Animation onCreateShowAnimation() {
        Animation showAnima = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
                1f,
                Animation.RELATIVE_TO_PARENT,
                0,
                Animation.RELATIVE_TO_PARENT,
                0,
                Animation.RELATIVE_TO_PARENT,
                0);
        showAnima.setInterpolator(new DecelerateInterpolator());
        showAnima.setDuration(350);
        return showAnima;
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        Animation exitAnima = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,
                0f,
                Animation.RELATIVE_TO_PARENT,
                1f,
                Animation.RELATIVE_TO_PARENT,
                0,
                Animation.RELATIVE_TO_PARENT,
                0);
        exitAnima.setInterpolator(new DecelerateInterpolator());
        exitAnima.setDuration(350);
        return exitAnima;
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_comment);
    }
    //=============================================================Getter/Setter

    public OnCommentPopupClickListener getOnCommentPopupClickListener() {
        return mOnCommentPopupClickListener;
    }

    public void setOnCommentPopupClickListener(OnCommentPopupClickListener onCommentPopupClickListener) {
        mOnCommentPopupClickListener = onCommentPopupClickListener;
    }

    //=============================================================clickEvent
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.popup_comment_report:
                if (mOnCommentPopupClickListener != null) {
                    mOnCommentPopupClickListener.onLikeClick(v, report);
                    report.clearAnimation();
                    report.startAnimation(mAnimationSet);
                }
                break;
            case R.id.popup_comment_delete:
                if (mOnCommentPopupClickListener != null) {
                    mOnCommentPopupClickListener.onCommentClick(v);
                    dismiss();
                }
                break;
        }
    }

    //=============================================================InterFace
    public interface OnCommentPopupClickListener {
        void onLikeClick(View v, TextView likeText);

        void onCommentClick(View v);
    }
}
