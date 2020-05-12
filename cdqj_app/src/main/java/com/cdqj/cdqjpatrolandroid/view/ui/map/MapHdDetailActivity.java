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
import com.cdqj.cdqjpatrolandroid.bean.HdOrderBean;
import com.cdqj.cdqjpatrolandroid.bean.OrderSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.comstomview.fragment.TabPagerAdapter;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.presenter.MapHdDetailPresenter;
import com.cdqj.cdqjpatrolandroid.utils.NavigationUtil;
import com.cdqj.cdqjpatrolandroid.utils.PictureProcessingUtil;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;
import com.cdqj.cdqjpatrolandroid.utils.StringUrlUtil;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.view.i.IMapHdDetailView;
import com.cdqj.cdqjpatrolandroid.view.ui.order.HdUpdateFileFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 地图隐患弹窗详情
 *
 * @author lyf
 * 创建时间 2018年9月6日 14:12:38
 */
public class MapHdDetailActivity extends BaseActivity<MapHdDetailPresenter> implements IMapHdDetailView {

    TextView mapHdDialogTitle;
    TextView mapHdDialogLevel;
    TextView mapHdDialogDistance;
    TextView mapHdDialogAddress;
    TextView mapHdDialogUpdate;
    TextView mapHdDialogLine;
    ImageView mapHdDialogImg;
    TextView mapBottomBarReport;
    FloatingActionButton mapOther;
    RecyclerView otherRv;
    SegmentTabLayout hdListTab;
    SmartRefreshLayout mapHdRefreshLayout;
    ViewPager hdVp;

    private String[] mTitles;
    private ArrayList<Fragment> mHdFragments = new ArrayList<>();
    private FragmentManager fragmentManager;

    /**
     * 巡检内容对象
     */
    private HdOrderBean hdBean;

    private String taskId;

    /**
     * 导航点
     */
    private double navX = 30.572262, navY = 104.066513;

    private SuperListAdapter adapter;
    private List<SuperTextViewBean> beans;

    /**
     * 初始化界面
     */
    @SuppressLint({"InflateParams", "SetTextI18n", "RestrictedApi"})
    @Override
    public void initView() {
        hdBean = getIntent().getParcelableExtra("HdOrderBean");
        fragmentManager = getSupportFragmentManager();
        mapBottomBarReport.setVisibility(View.INVISIBLE);

        // 域相关
        if (doMainId == 1) {
            mapOther.setVisibility(View.VISIBLE);
            // 泸州域展示信息
            beans = new ArrayList<>();
            if (ObjectUtils.isNotEmpty(hdBean)) {
                beans.add(new SuperTextViewBean("设施类别", hdBean.getName()));
                beans.add(new SuperTextViewBean("隐患类型", hdBean.getPropYhlx()));
                beans.add(new SuperTextViewBean("类型", hdBean.getPropLx()));
                beans.add(new SuperTextViewBean("漏气设备", hdBean.getPropLqsb()));
                beans.add(new SuperTextViewBean("防腐性质", hdBean.getPropFfxz()));
                beans.add(new SuperTextViewBean("管径", hdBean.getPropFj()));
                beans.add(new SuperTextViewBean("管材", hdBean.getPropGc()));
                beans.add(new SuperTextViewBean("敷设方式", hdBean.getPropFsfs()));
            }

            otherRv.setLayoutManager(new LinearLayoutManager(this));
            adapter = new SuperListAdapter(beans);
            adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            adapter.isFirstOnly(false);
            adapter.bindToRecyclerView(otherRv);
        } else {
            mapOther.setVisibility(View.GONE);
        }

        if (ObjectUtils.isNotEmpty(hdBean)) {
            // 隐患
            taskId = hdBean.getId();

            mapHdDialogTitle.setText(StringUtils.isTrimEmpty(hdBean.getHdTypeExp()) ?
                    getString(R.string.null_text) : hdBean.getHdTypeExp());
            mapHdDialogAddress.setText(StringUtils.isTrimEmpty(hdBean.getHdAddress()) ?
                    getString(R.string.null_text) : hdBean.getHdAddress());
            mapHdDialogLevel.setText(StringUtils.isTrimEmpty(hdBean.getHdGrandExp()) ?
                    getString(R.string.null_text) : hdBean.getHdGrandExp());
            mapHdDialogUpdate.setText(hdBean.getLastReportUserName() + "于" + hdBean.getLastReportTime() + "上报");
            GlideImgManager.loadRoundCornerImage(this, StringUrlUtil.transHttpUrlAndOnlyOne(hdBean.getHdPicture()), mapHdDialogImg, 5);
            double dis = 0.0;
            if (!StringUtils.isTrimEmpty(hdBean.getLon()) && !StringUtils.isTrimEmpty(hdBean.getLat())) {
                navX = Double.valueOf(hdBean.getLat());
                navY = Double.valueOf(hdBean.getLon());
                dis = GpsUtils.getDistance(PreferencesUtil.getString(Constant.LOCATION_LATITUDE),
                        PreferencesUtil.getString(Constant.LOCATION_LONGITUDE), hdBean.getLat(), hdBean.getLon());
            }
            mapHdDialogDistance.setText(dis == 0.0 ? getString(R.string.null_text) : "距离" + dis + "米");

            mapHdRefreshLayout.setOnRefreshListener(refreshlayout -> mPresenter.getOrderReports(taskId, false));
            mapHdRefreshLayout.setOnLoadMoreListener(refreshlayout -> {
                refreshlayout.finishLoadMoreWithNoMoreData();
                refreshlayout.setNoMoreData(false);
            });
            mPresenter.getOrderReports(taskId, true);
        } else {
            ToastBuilder.showShortWarning("隐患信息获取失败");
        }
    }

    @Override
    protected MapHdDetailPresenter createPresenter() {
        return new MapHdDetailPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_map_hd_detail;
    }

    @Override
    protected void findView() {
        mapHdDialogTitle = findViewById(R.id.map_hd_dialog_title);
        mapHdDialogLevel = findViewById(R.id.map_hd_dialog_level);
        mapHdDialogDistance = findViewById(R.id.map_hd_dialog_distance);
        mapHdDialogAddress = findViewById(R.id.map_hd_dialog_address);
        mapHdDialogUpdate = findViewById(R.id.map_hd_dialog_update);
        mapHdDialogLine = findViewById(R.id.map_hd_dialog_line);
        mapHdDialogImg = findViewById(R.id.map_hd_dialog_img);
        mapBottomBarReport = findViewById(R.id.map_bottom_bar_report);
        mapOther = findViewById(R.id.map_hd_detail_other);
        otherRv = findViewById(R.id.map_hd_detail_other_rv);
        hdListTab = findViewById(R.id.hd_list_tab);
        mapHdRefreshLayout = findViewById(R.id.map_hd_refreshLayout);
        hdVp = findViewById(R.id.hd_vp);
    }

    @Override
    protected String getTitleText() {
        return "详情";
    }

    @Override
    public void initListener() {
        findViewById(R.id.map_bottom_bar_map).setOnClickListener(this::setClick);
        findViewById(R.id.map_bottom_bar_report).setOnClickListener(this::setClick);
        findViewById(R.id.map_bottom_bar_navigation).setOnClickListener(this::setClick);
        findViewById(R.id.map_hd_dialog_img).setOnClickListener(this::setClick);
        findViewById(R.id.map_hd_detail_other).setOnClickListener(this::setClick);
    }

    public void setClick(View view) {
        int i = view.getId();
        if (i == R.id.map_bottom_bar_map) {
            this.finish();
        } else if (i == R.id.map_bottom_bar_report) {
        } else if (i == R.id.map_bottom_bar_navigation) {
            NavigationUtil.showNavWindow(navX, navY, this);
        } else if (i == R.id.map_hd_dialog_img) {
            PictureProcessingUtil.imgMoreSHow(this, hdBean.getHdPicture(), (ImageView) view);
        } else if (i == R.id.map_hd_detail_other) {// 详情其他
            otherRv.setVisibility(otherRv.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        }
    }

    private void setTabData() {
        hdVp.setAdapter(new TabPagerAdapter(fragmentManager, mHdFragments, mTitles));
        // 默认加载所有(防止二次加载)
        hdVp.setOffscreenPageLimit(mHdFragments.size());
        hdListTab.setTabData(mTitles);
        // 主流程
        hdListTab.showDot(1);
        hdListTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                hdVp.setCurrentItem(position, false);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        hdVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                hdListTab.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        hdVp.setCurrentItem(0);
    }

    @Override
    public void onGetMapHdDetailSuccess(BasePostResponse<List<List<OrderSuperviseReportBean>>> basePostResponse, boolean flag) {
        mTitles = new String[basePostResponse.getObj().size() + 1];
        mTitles[0] = "全部";
        List<OrderSuperviseReportBean> beansAll = new ArrayList<>();
        for (int i = 0; i < basePostResponse.getObj().size(); i++) {
            beansAll.addAll(basePostResponse.getObj().get(i));
        }
        mHdFragments.add(new HdUpdateFileFragment(beansAll, hdBean, 0));
        for (int i = 0; i < basePostResponse.getObj().size(); i++) {
            List<OrderSuperviseReportBean> beans = basePostResponse.getObj().get(i);
            mTitles[i + 1] = beans.get(0).getReportUserName();
            mHdFragments.add(new HdUpdateFileFragment(beans, hdBean, i + 1));
        }
        if (mTitles.length != 0) {
            setTabData();
        }
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
