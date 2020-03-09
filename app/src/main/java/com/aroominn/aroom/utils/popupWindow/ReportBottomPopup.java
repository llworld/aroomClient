package com.aroominn.aroom.utils.popupWindow;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.aroominn.aroom.R;

import razerdp.basepopup.BasePopupWindow;

public class ReportBottomPopup extends BasePopupWindow implements View.OnClickListener {

    private View popupView;


    public ReportBottomPopup(Context context) {
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
        return createPopupById(R.layout.popup_report_type);
    }

    private void bindEvent() {
        findViewById(R.id.report_type1).setOnClickListener(this);
        findViewById(R.id.report_type2).setOnClickListener(this);
        findViewById(R.id.report_type3).setOnClickListener(this);
        findViewById(R.id.report_type4).setOnClickListener(this);


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
