package com.aroominn.aroom.view.inn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.aroominn.aroom.R;
import com.aroominn.aroom.adapter.InnFragmentAdapter;
import com.aroominn.aroom.base.BaseFragment;
import com.aroominn.aroom.utils.StatusBarUtil;
import com.aroominn.aroom.utils.customview.PagerSlidingTabStrip;
import com.aroominn.aroom.view.message.MessageFragment;
import com.aroominn.aroom.view.vintage.VintageActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 客栈
 */
public class InnFragment extends BaseFragment {

    @BindView(R.id.inn_notice)
    ImageView notice;

    @BindView(R.id.inn_tab)
    PagerSlidingTabStrip tabStrip;

    @BindView(R.id.inn_vp)
    ViewPager viewPager;

    @BindView(R.id.inn_vintage)
    ImageView vintage;

    public static final String TAG = InnFragment.class.getSimpleName();

    public static InnFragment newInstance() {
        return new InnFragment();
    }

    private InnFragmentAdapter adapter;

    private String[] fragments;
    private String[] titleList;
    private int currentIndex = 1;


    @Override
    public int getContentViewId() {
        return R.layout.fragment_inn;
    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        StatusBarUtil.immersive(context);
        StatusBarUtil.setPaddingSmart(context, tabStrip);
        StatusBarUtil.setMargin(context, vintage);


        titleList = getResources().getStringArray(R.array.inn_type);
        //加载fragments
        fragments = new String[]{FocusFragment.TAG, RecommendFragment.TAG, LatesFragment.TAG};
        adapter = new InnFragmentAdapter(this.getChildFragmentManager(), titleList, fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(titleList.length);
        viewPager.setCurrentItem(currentIndex);
        tabStrip.setViewPager(viewPager);
        tabStrip.setSelectedTextColor(currentIndex);

    }

    @Override
    public void initTitle(View view, Bundle savedInstanceState) {

    }

    @OnClick({R.id.inn_notice, R.id.inn_vintage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.inn_notice:
                startActivity(new Intent(context, NoticeActivity.class));
                break;
            case R.id.inn_vintage:
                startActivity(new Intent(context, VintageActivity.class));
                break;
        }
    }

}
