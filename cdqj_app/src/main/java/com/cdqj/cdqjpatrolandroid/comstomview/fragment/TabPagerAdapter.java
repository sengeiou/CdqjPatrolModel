package com.cdqj.cdqjpatrolandroid.comstomview.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.cdqj.cdqjpatrolandroid.base.BaseFragmentPagerAdapter;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;

/**
 * Created by lyf on 2018/8/6 14:30
 *
 * @author lyf
 * desc：
 */
public class TabPagerAdapter extends BaseFragmentPagerAdapter {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;

    public TabPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String[] titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    /**
     * 除了给定位置，其他位置的Fragment进行刷新
     * @param position position
     */
    public void notifyChangeWithoutPosition(int position) {
        String valueP = tags.valueAt(position);
        tags.clear();
        tags.put(position, valueP);
        notifyDataSetChanged();
    }
}