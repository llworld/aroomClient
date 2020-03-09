package com.aroominn.aroom.utils.popupWindow;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;

import com.aroominn.aroom.R;
import com.bumptech.glide.Glide;

import razerdp.basepopup.BasePopupWindow;
import uk.co.senab.photoview.PhotoView;

public class ImagePopup extends BasePopupWindow implements View.OnClickListener {

    private View popupView;
    Context mContext;
    private final PhotoView mPhotoView;

    public ImagePopup(Context context) {
        super(context);
        setPopupGravity(Gravity.BOTTOM);
        mContext = context;
//        bindEvent();
        mPhotoView = findViewById(R.id.popup_image_photo);

    }


    public void initView(String url) {
        Glide.with(mContext)
                .load(url)
                .into(mPhotoView);
        showPopupWindow();
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
        return createPopupById(R.layout.popup_image);
    }

    private void bindEvent() {
        findViewById(R.id.popup_menu_report).setOnClickListener(this);
        findViewById(R.id.popup_menu_delete).setOnClickListener(this);


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
                break;
            case R.id.popup_menu_delete:
                mItemOnClickListener.OnItemClickListener(v);
                break;
            default:
                break;
        }

    }
}
