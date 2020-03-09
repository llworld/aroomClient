package com.aroominn.aroom.utils.popupWindow;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.aroominn.aroom.R;

import razerdp.basepopup.BasePopupWindow;

public class SlideFromBottomPopup extends BasePopupWindow implements View.OnClickListener {

    private View popupView;
    boolean isMe;

    public SlideFromBottomPopup(Context context, boolean isMe) {
        super(context);
        setPopupGravity(Gravity.BOTTOM);
        this.isMe = isMe;
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
        return createPopupById(R.layout.popup_menu);
    }

    private void bindEvent() {
        TextView report = findViewById(R.id.popup_menu_report);
        report.setOnClickListener(this);
        TextView delete = findViewById(R.id.popup_menu_delete);
        delete.setOnClickListener(this);
        TextView edit = findViewById(R.id.popup_menu_edit);
        edit.setOnClickListener(this);

        if (isMe) {
            report.setVisibility(View.GONE);
        } else {
            delete.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
        }
    }


    public void setOnMenuItemListener(OnPopupItemClickListener listener) {
        this.mItemOnClickListener = listener;
    }


    private OnPopupItemClickListener mItemOnClickListener;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.popup_menu_report:
                mItemOnClickListener.OnItemClickListener(v);
                dismiss();

                break;
            case R.id.popup_menu_delete:
                mItemOnClickListener.OnItemClickListener(v);
                dismiss();

                break;
            default:
                dismiss();
                break;
        }

    }
}
