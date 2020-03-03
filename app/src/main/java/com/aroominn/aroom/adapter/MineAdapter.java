package com.aroominn.aroom.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.aroominn.aroom.view.mine.NobleFragment;
import com.aroominn.aroom.view.mine.TastyFragment;

public class MineAdapter extends FragmentPagerAdapter {

    private String[] titleList;
    private String[] fragments;

    private NobleFragment nobleFragment;
    private TastyFragment tastyFragment;

    public MineAdapter(FragmentManager fm,String[] titleList,String[] fragments) {
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

        if (NobleFragment.TAG.equals(tag)) {
            if (nobleFragment == null) {
                nobleFragment = NobleFragment.newInstance();
                Bundle args = new Bundle();
                args.putString("TAG", NobleFragment.TAG);
                nobleFragment.setArguments(args);
            }
            return nobleFragment;
        }


        if (TastyFragment.TAG.equals(tag)) {
            if (tastyFragment == null) {
                tastyFragment = TastyFragment.newInstance();
                Bundle args = new Bundle();
                args.putString("TAG", TastyFragment.TAG);
                tastyFragment.setArguments(args);
            }
            return tastyFragment;
        }

        return null;
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
