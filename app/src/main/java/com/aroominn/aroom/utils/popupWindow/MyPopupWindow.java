package com.aroominn.aroom.utils.popupWindow;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.RadioGroup;

import com.aroominn.aroom.R;

import razerdp.basepopup.BasePopupWindow;

public class MyPopupWindow extends BasePopupWindow {

    private OnRadioPopupClickListener onRadioPopupClickListener;
    private final RadioGroup radioGroup;

    public MyPopupWindow(Context context) {
        super(context);
        setPopupGravity(Gravity.TOP);
//        setPopupGravity(Gravity.CENTER);
        radioGroup = findViewById(R.id.popup_radio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                onRadioPopupClickListener.onItemClick(i);
            }
        });
    }

  /*  public MyPopupWindow(Context context, int width, int height, boolean delayInit) {
        super(context, width, height, delayInit);
    }*/

    @Override
    public View onCreateContentView() {

        return createPopupById(R.layout.popup_story_state);
    }

    @Override
    protected Animation onCreateShowAnimation() {
        AnimationSet set = new AnimationSet(true);
        set.setInterpolator(new DecelerateInterpolator());
        set.addAnimation(getScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0));
        set.addAnimation(getDefaultAlphaAnimation());
        return set;
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        AnimationSet set = new AnimationSet(true);
        set.setInterpolator(new DecelerateInterpolator());
        set.addAnimation(getScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0));
        set.addAnimation(getDefaultAlphaAnimation(false));
        return set;
    }

    public void setOnRadioPopupClickListener(OnRadioPopupClickListener onListPopupItemClickListener) {
        onRadioPopupClickListener = onListPopupItemClickListener;
    }

    public interface OnRadioPopupClickListener {
        void onItemClick(int checkedId);
    }
}
