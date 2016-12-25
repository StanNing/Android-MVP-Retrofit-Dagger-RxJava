package com.focuschina.ehealth_lib.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName:
 * @Description: TODO: (这里用一句话描述这个类的作用)
 * @date 2016/12/14 下午4:40
 */
public class FragVPAdapter extends FragmentStatePagerAdapter {
    String[] mNames;
    List<Fragment> mFragmentList;

    public FragVPAdapter(FragmentManager fm, String[] names, List<Fragment> fragments) {
        super(fm);
        mNames = names;
        mFragmentList = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = mNames[position];
        if (title.length() <= 2) {
            title = " " + title + " ";
        }
        return title;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
