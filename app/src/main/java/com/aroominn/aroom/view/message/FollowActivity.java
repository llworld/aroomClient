package com.aroominn.aroom.view.message;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.TableLayout;

import com.aroominn.aroom.R;
import com.aroominn.aroom.adapter.FollowListAdapter;
import com.aroominn.aroom.adapter.FollowPagerAdapter;
import com.aroominn.aroom.base.BaseActivity;
import com.aroominn.aroom.utils.customview.PagerSlidingTabStrip;
import com.aroominn.aroom.utils.customview.TitleBar;
import com.aroominn.aroom.view.message.follow.BossFragment;
import com.aroominn.aroom.view.message.follow.MateFragment;
import com.aroominn.aroom.view.message.follow.WaiterFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import butterknife.BindView;

import static com.aroominn.aroom.base.BaseApplication.getContext;

/**
 * 关注
 */
public class FollowActivity extends BaseActivity implements OnRefreshListener, OnRefreshLoadMoreListener {

    @BindView(R.id.follow_title)
    TitleBar mTitleBar;

    @BindView(R.id.follow_refresh)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.follow_tab)
    TabLayout mTabLayout;

    @BindView(R.id.follow_vp)
    ViewPager mViewPager;

    private FollowPagerAdapter mAdapter;


    @Override
    public void initView(Bundle savedInstanceState) {
        initTitleBar(mTitleBar, R.string.title_follow);
    }

    @Override
    public void setListener() {

        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
    }

    @Override
    public void initData() {

        String[] titles = getResources().getStringArray(R.array.follow_type);
        String[] fragments = new String[]{
                WaiterFragment.class.getSimpleName(),
                MateFragment.class.getSimpleName(),
                BossFragment.class.getSimpleName()};

        mAdapter = new FollowPagerAdapter(getSupportFragmentManager(), titles, fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(titles.length);
        mTabLayout.setupWithViewPager(mViewPager, true);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_follow;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//        mAdapter.fragments[mViewPager.getCurrentItem()].onLoadMore(refreshLayout);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//        mAdapter.fragments[mViewPager.getCurrentItem()].onRefresh(refreshLayout);
    }

}
