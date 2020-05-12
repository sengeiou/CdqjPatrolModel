package com.cdqj.cdqjpatrolandroid.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by lyf on 2018/7/24 18:09
 *
 * @author lyf
 * desc：
 */
public abstract class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private FragmentManager mFragmentManager;

    /**
     * 保存每个Fragment的Tag，刷新页面的依据
     */
    public SparseArray<String> tags = new SparseArray<>();

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //得到缓存的fragment
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        String tag = fragment.getTag();
        //保存每个Fragment的Tag
        tags.put(position, tag);
        return fragment;
    }

    /**
     * 拿到指定位置的Fragment
     * @param position position
     * @return Fragment
     */
    public Fragment getFragmentByPosition(int position) {
        return mFragmentManager.findFragmentByTag(tags.get(position));
    }

    public List<Fragment> getFragments(){
        return mFragmentManager.getFragments();
    }

    /**
     * 刷新指定位置的Fragment
     * @param position position
     */
    public void notifyFragmentByPosition(int position) {
        tags.removeAt(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        Fragment fragment = (Fragment) object;
        //如果Item对应的Tag存在，则不进行刷新
        if (tags.indexOfValue(fragment.getTag()) > -1) {
            return super.getItemPosition(object);
        }
        return POSITION_NONE;
    }
}
