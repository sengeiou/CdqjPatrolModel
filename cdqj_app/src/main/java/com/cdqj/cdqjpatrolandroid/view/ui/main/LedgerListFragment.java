package com.cdqj.cdqjpatrolandroid.view.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cdqj.cdqjpatrolandroid.bean.TabEntity;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.base.BaseFragment;
import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.comstomview.fragment.TabPagerAdapter;
import com.cdqj.cdqjpatrolandroid.view.ui.ledger.HdListFragment;
import com.cdqj.cdqjpatrolandroid.view.ui.ledger.PlanLedgerFragment;
import com.cdqj.cdqjpatrolandroid.view.ui.ledger.SiteLedgerFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.Objects;

/**
 * 巡线台帐主界面
 *
 * @author lyf
 * @date 2018年7月25日 17:35:47
 */
public class LedgerListFragment extends BaseFragment {

    CommonTabLayout ledgerListTab;
    ViewPager ledgerListVp;

    private String[] mTitles = {"隐患", "计划", "工地"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private ArrayList<Fragment> mLedgerFragments = new ArrayList<>();
    private FragmentManager fragmentManager;

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_fragment_ledger_list;
    }

    @Override
    protected void findView(View rootView) {
        ledgerListTab = rootView.findViewById(R.id.ledger_list_tab);
        ledgerListVp = rootView.findViewById(R.id.ledger_list_vp);
    }

    @Override
    protected String getTitleText() {
        return null;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void initView(View view) {

        for (String title : mTitles) {
            mTabEntities.add(new TabEntity(title));
        }

        fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        mLedgerFragments.add(new HdListFragment());
        //mLedgerFragments.add(new SimpleCardFragment());
        mLedgerFragments.add(new PlanLedgerFragment());
        mLedgerFragments.add(new SiteLedgerFragment());
        ledgerListVp.setAdapter(new TabPagerAdapter(fragmentManager, mLedgerFragments, mTitles));
        // 默认加载所有(防止二次加载)
        ledgerListVp.setOffscreenPageLimit(mLedgerFragments.size());
        setTabData();
    }


    private void setTabData() {
        ledgerListTab.setTabData(mTabEntities);
        ledgerListTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                ledgerListVp.setCurrentItem(position, false);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        ledgerListVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ledgerListTab.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ledgerListVp.setCurrentItem(0);
    }
}
