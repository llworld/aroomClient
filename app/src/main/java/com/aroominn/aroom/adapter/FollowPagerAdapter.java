package com.aroominn.aroom.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.aroominn.aroom.view.message.FollowActivity;
import com.aroominn.aroom.view.message.FollowFragment;

public class FollowPagerAdapter extends FragmentStatePagerAdapter {

    private FollowActivity.Item[] items;
    public FollowFragment[] fragments;
    private Context context;
//    private MateFragment mateFragment;

    public FollowPagerAdapter(FragmentManager fm, Context context, FollowActivity.Item... items) {

        super(fm);
        this.context = context;
        this.items = items;
        this.fragments = new FollowFragment[items.length];
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return context.getString(items[position].nameId);
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null) {
            fragments[position] = new FollowFragment();
        }
        return fragments[position];
    }

    @Override
    public int getCount() {
        return items.length;
    }
}
