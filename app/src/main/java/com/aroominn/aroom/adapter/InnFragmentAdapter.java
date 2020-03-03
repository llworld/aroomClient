package com.aroominn.aroom.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.aroominn.aroom.base.BaseFragment;
import com.aroominn.aroom.view.inn.FocusFragment;
import com.aroominn.aroom.view.inn.LatesFragment;
import com.aroominn.aroom.view.inn.RecommendFragment;

public class InnFragmentAdapter extends FragmentPagerAdapter{

    private String[] titleList;
    private String[] fragments;

    private FocusFragment focusFragment;
    private RecommendFragment recommendFragment;
    private LatesFragment latesFragment;

    public InnFragmentAdapter(FragmentManager fm,String[] titleList,String[] fragments) {
        super(fm);
        this.titleList=titleList;
        this.fragments=fragments;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList[position];
    }



    @Override
    public Fragment getItem(int position) {
        return getFragmentFromFactory(fragments[position % fragments.length]);
    }

    private Fragment getFragmentFromFactory(String tag) {

        if (FocusFragment.TAG.equals(tag)) {
            if (focusFragment == null) {
                focusFragment = FocusFragment.newInstance();
                Bundle args = new Bundle();
                args.putString("TAG", FocusFragment.TAG);
                focusFragment.setArguments(args);
            }
            return focusFragment;
        }
        if (RecommendFragment.TAG.equals(tag)) {
            if (recommendFragment == null) {
                recommendFragment = RecommendFragment.newInstance();
                Bundle args = new Bundle();
                args.putString("TAG", RecommendFragment.TAG);
                recommendFragment.setArguments(args);
            }
            return recommendFragment;
        }

        if (LatesFragment.TAG.equals(tag)) {
            if (latesFragment == null) {
                latesFragment = LatesFragment.newInstance();
                Bundle args = new Bundle();
                args.putString("TAG", LatesFragment.TAG);
                latesFragment.setArguments(args);
            }
            return latesFragment;
        }

        return null;
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
