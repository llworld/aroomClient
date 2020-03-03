package com.aroominn.aroom.view.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;


import com.aroominn.aroom.MainActivity;
import com.aroominn.aroom.R;
import com.aroominn.aroom.adapter.LoginFragmentAdapter;
import com.aroominn.aroom.base.BaseActivity;
import com.aroominn.aroom.utils.L;
import com.aroominn.aroom.utils.SharedUtils;
import com.aroominn.aroom.utils.customview.PagerSlidingTabStrip;
import com.aroominn.aroom.utils.rongcloud.RongCloudUtils;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * 登录注册
 */

public class LoginActivity extends BaseActivity {


    @BindView(R.id.login_tab)
    PagerSlidingTabStrip tabStrip;

    @BindView(R.id.login_vp)
    ViewPager viewPager;


    private String[] fragments;
    private String[] titleList;
    private int currentIndex = 0;

    private LoginFragmentAdapter adapter;

    @Override
    public void initView(Bundle savedInstanceState) {


        boolean te = SharedUtils.getInstance().getLogin();
        if (SharedUtils.getInstance().getLogin()) {
            startActivity(new Intent(this, MainActivity.class));
            L.e("已经登陆过");
            finish();
        }


        titleList = getResources().getStringArray(R.array.login_type);
        //加载fragments
        fragments = new String[]{PhoneFragment.TAG, AccountFragment.TAG};
        adapter = new LoginFragmentAdapter(getSupportFragmentManager(), titleList, fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(titleList.length);
        viewPager.setCurrentItem(currentIndex);
        tabStrip.setViewPager(viewPager);
        tabStrip.setSelectedTextColor(currentIndex);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }
}
