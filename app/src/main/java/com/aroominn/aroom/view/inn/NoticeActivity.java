package com.aroominn.aroom.view.inn;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.aroominn.aroom.R;
import com.aroominn.aroom.base.BaseActivity;
import com.aroominn.aroom.utils.StatusBarUtil;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import butterknife.BindView;

public class NoticeActivity extends BaseActivity {

    @BindView(R.id.notice_title)
    TitleBar mTitleBar;

    @Override
    public void initView(Bundle savedInstanceState) {
//        StatusBarUtil.immersive(this);
//        StatusBarUtil.StatusBarLightMode(this, StatusBarUtil.StatusBarLightMode(this));
////        StatusBarUtil.setMargin(context, mTitleBar);
        StatusBarUtil.setPaddingSmart(context, ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0));

    }

    @Override
    public void setListener() {
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_notice;
    }
}
