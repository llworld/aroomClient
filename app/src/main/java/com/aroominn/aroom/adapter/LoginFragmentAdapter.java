package com.aroominn.aroom.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.aroominn.aroom.view.login.AccountFragment;
import com.aroominn.aroom.view.login.PhoneFragment;

public class LoginFragmentAdapter extends FragmentPagerAdapter {

    private String[] titleList;
    private String[] fragments;

    private AccountFragment accountFragment;
    private PhoneFragment phoneFragment;

    public LoginFragmentAdapter(FragmentManager fm,String[] titleList,String[] fragments) {
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

    @Override
    public int getCount() {
        return fragments.length;
    }
    private Fragment getFragmentFromFactory(String tag) {
        if (PhoneFragment.TAG.equals(tag)) {
            if (phoneFragment == null) {
                phoneFragment = PhoneFragment.newInstance();
                Bundle args = new Bundle();
                args.putString("TAG", PhoneFragment.TAG);
                phoneFragment.setArguments(args);
            }
            return phoneFragment;
        }
        if (AccountFragment.TAG.equals(tag)) {
            if (accountFragment == null) {
                accountFragment = AccountFragment.newInstance();
                Bundle args = new Bundle();
                args.putString("TAG", AccountFragment.TAG);
                accountFragment.setArguments(args);
            }
            return accountFragment;
        }
        return null;
    }
}
