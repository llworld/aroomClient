package com.aroominn.aroom.view.radio;

import android.os.Bundle;
import android.view.View;

import com.aroominn.aroom.R;
import com.aroominn.aroom.base.BaseFragment;

/**
 * 电台 声音讲述
 */
public class RadioFragment extends BaseFragment {

    public static final String TAG = RadioFragment.class.getSimpleName();

    public static RadioFragment newInstance() {
        return new RadioFragment();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_radio;
    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initTitle(View view, Bundle savedInstanceState) {

    }
}
