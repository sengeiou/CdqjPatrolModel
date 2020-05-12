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
import com.cdqj.cdqjpatrolandroid.view.ui.plan.PlanAuditListFragment;
import com.cdqj.cdqjpatrolandroid.view.ui.plan.PlanFormulateListFragment;
import com.cdqj.cdqjpatrolandroid.view.ui.plan.PlanMyListFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by lyf on 2018/7/26 09:22
 *
 * @author lyf
 * desc： * 巡线计划主界面
 */
public class PlanListFragment extends BaseFragment {

    CommonTabLayout planListTab;
    ViewPager planListVp;

    private String[] mTitles = {"我的计划", "制定计划", "计划审核"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private ArrayList<Fragment> mPlanFragments = new ArrayList<>();
    private FragmentManager fragmentManager;
    PlanMyListFragment planMyListFragment;
    PlanFormulateListFragment planFormulateListFragment;
    PlanAuditListFragment planAuditListFragment;

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_fragment_plan_list;
    }

    @Override
    protected void findView(View rootView) {
        planListTab = rootView.findViewById(R.id.plan_list_tab);
        planListVp = rootView.findViewById(R.id.plan_list_vp);
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
        initView();
    }

    public void setCurrent(int type, int id) {
        if (mPlanFragments.size() == 3) {
            if (type == 3) {
                planListVp.setCurrentItem(0, false);
                planMyListFragment.setTopId(id);
            } else if (type == 4) {
                planListVp.setCurrentItem(2, false);
                planAuditListFragment.setTopId(id);
            }
        }
    }

    private void initView() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }
        planMyListFragment = new PlanMyListFragment();
        planFormulateListFragment = new PlanFormulateListFragment();
        planAuditListFragment = new PlanAuditListFragment();
        fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        mPlanFragments.add(planMyListFragment);
        mPlanFragments.add(planFormulateListFragment);
        mPlanFragments.add(planAuditListFragment);
        planListVp.setAdapter(new TabPagerAdapter(fragmentManager, mPlanFragments, mTitles));
        // 默认加载所有(防止二次加载)
        planListVp.setOffscreenPageLimit(mPlanFragments.size());
        setTabData();
    }

    private void setTabData() {
        planListTab.setTabData(mTabEntities);
        planListTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                planListVp.setCurrentItem(position, false);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        planListVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                planListTab.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        planListVp.setCurrentItem(0);
    }

}
