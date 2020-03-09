package com.aroominn.aroom.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.aroominn.aroom.base.BaseFragment;
import com.aroominn.aroom.utils.L;
import com.aroominn.aroom.view.message.FollowActivity;
import com.aroominn.aroom.view.message.follow.BossFragment;
import com.aroominn.aroom.view.message.follow.MateFragment;
import com.aroominn.aroom.view.message.follow.WaiterFragment;


public class FollowPagerAdapter extends FragmentStatePagerAdapter {


    public String[] fragments;
    private String[] titles;
    private WaiterFragment waiterFragment;
    private MateFragment mateFragment;
    private BossFragment bossFragment;
    //    private MateFragment mateFragment;

    public FollowPagerAdapter(FragmentManager fm, String[] titles, String[] fragments) {

        super(fm);

        this.titles = titles;
        this.fragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {


        return getFragmentFromFactory(fragments[position%fragments.length]);
    }
    private Fragment getFragmentFromFactory(String tag) {

        if (WaiterFragment.class.getSimpleName().equals(tag)) {
            if (waiterFragment == null) {
                waiterFragment = WaiterFragment.newInstance();
                Bundle args = new Bundle();
                args.putString("TAG", WaiterFragment.class.getSimpleName());
                waiterFragment.setArguments(args);
            }
            return waiterFragment;
        }
        if (MateFragment.class.getSimpleName().equals(tag)) {
            if (mateFragment == null) {
                mateFragment = MateFragment.newInstance();
                Bundle args = new Bundle();
                args.putString("TAG", MateFragment.class.getSimpleName());
                mateFragment.setArguments(args);
            }
            return mateFragment;
        }

        if (BossFragment.class.getSimpleName().equals(tag)) {
            if (bossFragment == null) {
                bossFragment = BossFragment.newInstance();
                Bundle args = new Bundle();
                args.putString("TAG", BossFragment.class.getSimpleName());
                bossFragment.setArguments(args);
            }
            return bossFragment;
        }

        return null;
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
