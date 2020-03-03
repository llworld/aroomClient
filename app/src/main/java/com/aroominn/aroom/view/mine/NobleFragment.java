package com.aroominn.aroom.view.mine;

import android.os.Bundle;
import android.view.View;

import com.aroominn.aroom.R;
import com.aroominn.aroom.base.BaseFragment;

/**
 * 美酒
 * 收藏的故事
 */
public class NobleFragment extends BaseFragment{


    public static final String TAG = NobleFragment.class.getSimpleName();

    public static NobleFragment newInstance() {
        return new NobleFragment();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_noble;
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
