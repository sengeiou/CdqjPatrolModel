package com.cdqj.cdqjpatrolandroid.view.ui.map;

import android.annotation.SuppressLint;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.gis.util.GpsUtils;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.image.glide.GlideImgManager;
import com.cdqj.cdqjpatrolandroid.adapter.SuperListAdapter;
import com.cdqj.cdqjpatrolandroid.bean.SuperTextViewBean;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.base.BaseActivity;
import com.cdqj.cdqjpatrolandroid.bean.OrderSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.bean.SiteBean;
import com.cdqj.cdqjpatrolandroid.comstomview.fragment.TabPagerAdapter;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.presenter.MapSiteDetailPresenter;
import com.cdqj.cdqjpatrolandroid.utils.NavigationUtil;
import com.cdqj.cdqjpatrolandroid.utils.PictureProcessingUtil;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;
import com.cdqj.cdqjpatrolandroid.utils.StringUrlUtil;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.view.i.IMapSiteDetailView;
import com.cdqj.cdqjpatrolandroid.view.ui.order.SiteUpdateFileFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 地图工地弹窗详情
 *
 * @author lyf
 * 创建时间 2018年10月19日 11:43:12
 */
public class MapSiteDetailActivity extends BaseActivity<MapSiteDetailPresenter> implements IMapSiteDetailView {

    TextView mapHdDialogTitle;
    TextView mapHdDialogLevel;
    TextView mapHdDialogDistance;
    TextView mapHdDialogAddress;
    TextView mapHdDialogUpdate;
    ImageView mapHdDialogImg;
    TextView mapHdDialogLine;
    SmartRefreshLayout mapHdRefreshLayout;
    TextView mapBottomBarReport;
    TextView mapBottomBarMap;
    FloatingActionButton siteOther;
    SegmentTabLayout siteListTab;
    RecyclerView otherRv;
    ViewPager siteVp;

    /**
     * 工地内容对象
     */
    private SiteBean siteBean;

    private String[] mTitles;
    private ArrayList<Fragment> mHdFragments = new ArrayList<>();
    private FragmentManager fragmentManager;

    private String siteId;

    /**
     * 1 地图进入
     * 2 台账进入
     */
    private int flag;

    /**
     * 导航点
     */
    private double navX = 30.572262, navY = 104.066513;

    private SuperListAdapter adapter;
    private List<SuperTextViewBean> beans;

    @Override
    public void initData() {
        if (ObjectUtils.isNotEmpty(siteBean)) {
            mPresenter.getTaskReports(siteId, true);
        } else {
            ToastBuilder.showShortWarning("当前工地获取失败");
        }
    }

    @Override
    protected MapSiteDetailPresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_map_site_detail;
    }

    @Override
    protected void findView() {
        mapHdDialogTitle = findViewById(R.id.map_hd_dialog_title);
        mapHdDialogLevel = findViewById(R.id.map_hd_dialog_level);
        mapHdDialogDistance = findViewById(R.id.map_hd_dialog_distance);
        mapHdDialogAddress = findViewById(R.id.map_hd_dialog_address);
        mapHdDialogUpdate = findViewById(R.id.map_hd_dialog_update);
        mapHdDialogImg = findViewById(R.id.map_hd_dialog_img);
        mapHdDialogLine = findViewById(R.id.map_hd_dialog_line);
        mapHdRefreshLayout = findViewById(R.id.map_hd_refreshLayout);
        mapBottomBarReport = findViewById(R.id.map_bottom_bar_report);
        mapBottomBarMap = findViewById(R.id.map_bottom_bar_map);
        siteOther = findViewById(R.id.site_detail_other);
        siteListTab = findViewById(R.id.site_list_tab);
        otherRv = findViewById(R.id.site_detail_other_rv);
        siteVp = findViewById(R.id.site_vp);
    }

    @Override
    protected String getTitleText() {
        return "详情";
    }

    /**
     * 界面初始化
     */
    @SuppressLint({"InflateParams", "RestrictedApi"})
    @Override
    public void initView() {
        flag = getIntent().getIntExtra("flag", 0);

        mPresenter = new MapSiteDetailPresenter(this);
        mapHdRefreshLayout.setEnablePureScrollMode(true);
        mapHdDialogLine.setVisibility(View.INVISIBLE);
        mapBottomBarReport.setVisibility(View.INVISIBLE);
        mapBottomBarMap.setVisibility(flag == 1 ? View.VISIBLE : View.GONE);
        fragmentManager = getSupportFragmentManager();

        siteBean = getIntent().getParcelableExtra("SiteBean");

        // 域相关
        if (doMainId == 1) {
            siteOther.setVisibility(View.VISIBLE);
            // 泸州域展示信息
            beans = new ArrayList<>();
            if (ObjectUtils.isNotEmpty(siteBean)) {
                beans.add(new SuperTextViewBean("施工类型", siteBean.getConType()));
                beans.add(new SuperTextViewBean("施工其他类型", siteBean.getOtherType()));
                beans.add(new SuperTextViewBean("监护原因", siteBean.getPropJhyy()));
                beans.add(new SuperTextViewBean("管道设施情况", siteBean.getPropGdssqk()));
                beans.add(new SuperTextViewBean("管道埋深情况", siteBean.getPropGdmsqk()));
                beans.add(new SuperTextViewBean("监护状态", siteBean.getJhStatus()));
            }

            otherRv.setLayoutManager(new LinearLayoutManager(this));
            adapter = new SuperListAdapter(beans);
            adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            adapter.isFirstOnly(false);
            adapter.bindToRecyclerView(otherRv);
        } else {
            siteOther.setVisibility(View.GONE);
        }

        if (ObjectUtils.isNotEmpty(siteBean)) {
            siteId = siteBean.getId();
            if (ObjectUtils.isNotEmpty(siteBean.getLat()) && ObjectUtils.isNotEmpty(siteBean.getLon())) {
                navX = siteBean.getLat();
                navY = siteBean.getLon();
            }
            mapHdRefreshLayout.setOnRefreshListener(refreshlayout -> mPresenter.getTaskReports(siteId, false));
            mapHdRefreshLayout.setOnLoadMoreListener(refreshlayout -> {
                refreshlayout.finishLoadMoreWithNoMoreData();
                refreshlayout.setNoMoreData(false);
            });

            GlideImgManager.loadRoundCornerImage(this, StringUrlUtil.transHttpUrlAndOnlyOne(siteBean.getPicture()), mapHdDialogImg, 5);
            mapHdDialogTitle.setText(StringUtils.isTrimEmpty(siteBean.getName()) ?
                    getString(R.string.null_text) : siteBean.getName());
            mapHdDialogAddress.setText(StringUtils.isTrimEmpty(siteBean.getAddress()) ?
                    getString(R.string.null_text) : siteBean.getAddress());
            mapHdDialogLevel.setText(StringUtils.isTrimEmpty(siteBean.getSiteLevelExp()) ?
                    getString(R.string.null_text) : siteBean.getSiteLevelExp());
            mapHdDialogUpdate.setText(StringUtils.isTrimEmpty(siteBean.getAddTime()) ?
                    getString(R.string.null_text) : siteBean.getAddTime());
            double dis = GpsUtils.getDistance(PreferencesUtil.getString(Constant.LOCATION_LATITUDE),
                    PreferencesUtil.getString(Constant.LOCATION_LONGITUDE), String.valueOf(siteBean.getLat()), String.valueOf(siteBean.getLon()));
            mapHdDialogDistance.setText(dis == 0.0 ? getString(R.string.null_text) : "距离" + dis + "米");
        } else {
            ToastBuilder.showShortError("工地属性获取失败");
        }
    }

    private void setTabData() {
        siteVp.setAdapter(new TabPagerAdapter(fragmentManager, mHdFragments, mTitles));
        // 默认加载所有(防止二次加载)
        siteVp.setOffscreenPageLimit(mHdFragments.size());
        siteListTab.setTabData(mTitles);
        // 主流程
        siteListTab.showDot(1);
        siteListTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                siteVp.setCurrentItem(position, false);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        siteVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                siteListTab.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        siteVp.setCurrentItem(0);
    }

    @Override
    public void initListener() {
        findViewById(R.id.map_bottom_bar_map).setOnClickListener(this::setClick);
        findViewById(R.id.map_bottom_bar_report).setOnClickListener(this::setClick);
        findViewById(R.id.map_bottom_bar_navigation).setOnClickListener(this::setClick);
        findViewById(R.id.map_hd_dialog_img).setOnClickListener(this::setClick);
        findViewById(R.id.site_detail_other).setOnClickListener(this::setClick);
    }

    public void setClick(View view) {
        int i = view.getId();
        if (i == R.id.map_bottom_bar_map) {
            this.finish();
        } else if (i == R.id.map_bottom_bar_report) {
        } else if (i == R.id.map_bottom_bar_navigation) {
            NavigationUtil.showNavWindow(navX, navY, this);
        } else if (i == R.id.map_hd_dialog_img) {
            PictureProcessingUtil.imgMoreSHow(this, siteBean.getPicture(), (ImageView) view);
        } else if (i == R.id.site_detail_other) {// 详情其他
            otherRv.setVisibility(otherRv.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onGetMapSiteDetailSuccess(BasePostResponse<List<List<OrderSuperviseReportBean>>> basePostResponse, boolean flag) {
        mHdFragments.clear();
        mTitles = new String[basePostResponse.getObj().size() + 1];
        mTitles[0] = "全部";
        List<OrderSuperviseReportBean> beansAll = new ArrayList<>();
        for (int i = 0; i < basePostResponse.getObj().size(); i++) {
            beansAll.addAll(basePostResponse.getObj().get(i));
        }
        mHdFragments.add(new SiteUpdateFileFragment(beansAll, siteBean, 0));
        for (int i = 0; i < basePostResponse.getObj().size(); i++) {
            List<OrderSuperviseReportBean> beans = basePostResponse.getObj().get(i);
            mTitles[i + 1] = beans.get(0).getReportUserName();
            mHdFragments.add(new SiteUpdateFileFragment(beans, siteBean, i + 1));
        }
        setTabData();
    }

    @Override
    public void showProgress() {
        baseShowProgress();
    }

    @Override
    public void showProgress(String str) {
        baseShowProgress(str);
    }

    @Override
    public void hideProgress() {
        baseHideProgress();
        mapHdRefreshLayout.finishLoadMore();
        mapHdRefreshLayout.finishRefresh();
    }

    @Override
    public void onSuccess(String msg) {

    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable msg) {
        mapHdRefreshLayout.finishLoadMore(false);
        mapHdRefreshLayout.finishRefresh(false);
        baseOnFailure(msg);
    }
}
