package com.aroominn.aroom.utils.popupWindow;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;

import com.aroominn.aroom.R;

import razerdp.basepopup.BasePopupWindow;

public class SexBottomPopup extends BasePopupWindow implements View.OnClickListener {



    public SexBottomPopup(Context context) {
        super(context);
        setPopupGravity(Gravity.BOTTOM);
        bindEvent();
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return getTranslateVerticalAnimation(1f, 0, 500);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getTranslateVerticalAnimation(0, 1f, 500);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_sex);
    }

    private void bindEvent() {
        findViewById(R.id.sex_male).setOnClickListener(this);
        findViewById(R.id.sex_woman).setOnClickListener(this);
        findViewById(R.id.sex_secret).setOnClickListener(this);


    }


    public void setOnMenuItemListener(OnPopupItemClickListener listener) {
        this.mItemOnClickListener = listener;
    }


    private OnPopupItemClickListener mItemOnClickListener;

    @Override
    public void onClick(View v) {
        mItemOnClickListener.OnItemClickListener(v);
        dismiss();
    }
}
