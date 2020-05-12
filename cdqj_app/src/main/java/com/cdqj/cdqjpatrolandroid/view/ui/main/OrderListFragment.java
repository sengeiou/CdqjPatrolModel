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
import com.cdqj.cdqjpatrolandroid.view.ui.order.OrderAuditListFragment;
import com.cdqj.cdqjpatrolandroid.view.ui.order.OrderDistributionListFragment;
import com.cdqj.cdqjpatrolandroid.view.ui.order.OrderMyListFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.Objects;

/**
 * 巡线工单主界面
 *
 * @author lyf
 * @date 2018年7月25日 17:35:47
 */
public class OrderListFragment extends BaseFragment {

    CommonTabLayout orderListTab;
    ViewPager orderListVp;

    private String[] mTitles = {"我的工单", "工单派发", "工单审核"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private ArrayList<Fragment> mOrderFragments = new ArrayList<>();
    private FragmentManager fragmentManager;
    OrderMyListFragment orderMyListFragment;
    OrderDistributionListFragment orderDistributionListFragment;
    OrderAuditListFragment orderAuditListFragment;

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_fragment_order_list;
    }

    @Override
    protected void findView(View rootView) {
        orderListTab = rootView.findViewById(R.id.order_list_tab);
        orderListVp = rootView.findViewById(R.id.order_list_vp);
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
        if (mOrderFragments.size() == 3) {
            if (type == 1) {
                orderListVp.setCurrentItem(0, false);
                orderMyListFragment.setTopId(id);
            } else if (type == 2) {
                orderListVp.setCurrentItem(2, false);
                orderAuditListFragment.setTopId(id);
            }
        }
    }

    private void initView() {
        for (String title : mTitles) {
            mTabEntities.add(new TabEntity(title));
        }
        fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        orderMyListFragment = new OrderMyListFragment();
        orderDistributionListFragment = new OrderDistributionListFragment();
        orderAuditListFragment = new OrderAuditListFragment();
        mOrderFragments.add(orderMyListFragment);
        mOrderFragments.add(orderDistributionListFragment);
        mOrderFragments.add(orderAuditListFragment);
        orderListVp.setAdapter(new TabPagerAdapter(fragmentManager, mOrderFragments, mTitles));
        // 默认加载所有(防止二次加载)
        orderListVp.setOffscreenPageLimit(mOrderFragments.size());
        setTabData();
    }


    private void setTabData() {
        orderListTab.setTabData(mTabEntities);
        orderListTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                orderListVp.setCurrentItem(position, false);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        orderListVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                orderListTab.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        orderListVp.setCurrentItem(0);
    }
}
