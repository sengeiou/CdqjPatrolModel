package com.cdqj.cdqjpatrolandroid.view.ui.ledger;

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
import com.cdqj.cdqjpatrolandroid.base.BaseActivity;
import com.cdqj.cdqjpatrolandroid.utils.StringUrlUtil;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.bean.HdOrderBean;
import com.cdqj.cdqjpatrolandroid.bean.OrderSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.comstomview.fragment.TabPagerAdapter;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.presenter.HdDetailPresenter;
import com.cdqj.cdqjpatrolandroid.utils.PictureProcessingUtil;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.view.i.IHdDetailView;
import com.cdqj.cdqjpatrolandroid.view.ui.order.HdUpdateFileFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 隐患台账详情
 *
 * @author lyf
 */
public class HdDetailActivity extends BaseActivity<HdDetailPresenter> implements IHdDetailView, View.OnClickListener {

    TextView mapHdDialogTitle;
    TextView mapHdDialogLevel;
    TextView mapHdDialogDistance;
    TextView mapHdDialogAddress;
    TextView mapHdDialogUpdate;
    ImageView mapHdDialogImg;
    FloatingActionButton mapHdDialogOther;
    SegmentTabLayout hdListTab;
    ViewPager hdVp;
    RecyclerView otherRv;
    SmartRefreshLayout hdRefreshLayout;

    private HdOrderBean bean;

    private String[] mTitles;
    private ArrayList<Fragment> mHdFragments = new ArrayList<>();
    private FragmentManager fragmentManager;

    private SuperListAdapter adapter;
    private List<SuperTextViewBean> beans;

    @Override
    public void initData() {
        if (ObjectUtils.isNotEmpty(bean)) {
            mPresenter.getHdDetailUpdateList(bean.getId() + "");
        } else {
            ToastBuilder.showShortWarning("当前隐患获取失败");
        }
    }

    @Override
    protected HdDetailPresenter createPresenter() {
        return new HdDetailPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_hd_detail;
    }

    @Override
    protected void findView() {
        mapHdDialogTitle = findViewById(R.id.map_hd_dialog_title);
        mapHdDialogLevel = findViewById(R.id.map_hd_dialog_level);
        mapHdDialogDistance = findViewById(R.id.map_hd_dialog_distance);
        mapHdDialogAddress = findViewById(R.id.map_hd_dialog_address);
        mapHdDialogUpdate = findViewById(R.id.map_hd_dialog_update);
        mapHdDialogImg = findViewById(R.id.map_hd_dialog_img);
        mapHdDialogOther = findViewById(R.id.hd_detail_other);
        hdListTab = findViewById(R.id.hd_list_tab);
        hdVp = findViewById(R.id.hd_vp);
        otherRv = findViewById(R.id.hd_detail_other_rv);
        hdRefreshLayout = findViewById(R.id.hd_refreshLayout);
    }

    @Override
    protected String getTitleText() {
        return "详情";
    }

    @Override
    public void initListener() {
        mapHdDialogImg.setOnClickListener(this);
        mapHdDialogOther.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.map_hd_dialog_img) {
            PictureProcessingUtil.imgMoreSHow(this, bean.getHdPicture(), (ImageView) view);

        } else if (i == R.id.hd_detail_other) {// 详情其他
            otherRv.setVisibility(otherRv.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

        }
    }

    /**
     * 初始化界面
     */
    @SuppressLint({"SetTextI18n", "RestrictedApi"})
    @Override
    public void initView() {
        mPresenter = new HdDetailPresenter(this);
        bean = getIntent().getParcelableExtra("HdOrderBean");
        hdRefreshLayout.setEnablePureScrollMode(true);
        fragmentManager = getSupportFragmentManager();

        // 域相关
        if (doMainId == 1) {
            mapHdDialogOther.setVisibility(View.VISIBLE);
            // 泸州域展示信息
            beans = new ArrayList<>();
            if (ObjectUtils.isNotEmpty(bean)) {
                beans.add(new SuperTextViewBean("设施类别", bean.getName()));
                beans.add(new SuperTextViewBean("隐患类型", bean.getPropYhlx()));
                beans.add(new SuperTextViewBean("类型", bean.getPropLx()));
                beans.add(new SuperTextViewBean("漏气设备", bean.getPropLqsb()));
                beans.add(new SuperTextViewBean("防腐性质", bean.getPropFfxz()));
                beans.add(new SuperTextViewBean("管径", bean.getPropFj()));
                beans.add(new SuperTextViewBean("管材", bean.getPropGc()));
                beans.add(new SuperTextViewBean("敷设方式", bean.getPropFsfs()));
            }
            otherRv.setLayoutManager(new LinearLayoutManager(this));
            adapter = new SuperListAdapter(beans);
            adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            adapter.isFirstOnly(false);
            adapter.bindToRecyclerView(otherRv);
        } else {
            mapHdDialogOther.setVisibility(View.GONE);
        }

        if (ObjectUtils.isNotEmpty(bean)) {
            assert titleToolbar != null;
            titleToolbar.setMainTitle(StringUtils.isTrimEmpty(bean.getHdTypeExp()) ? "详情" : bean.getHdTypeExp());
            mapHdDialogTitle.setText(StringUtils.isTrimEmpty(bean.getHdTypeExp()) ?
                    this.getString(R.string.null_text) : bean.getHdTypeExp());
            mapHdDialogLevel.setText(StringUtils.isTrimEmpty(bean.getHdGrandExp()) ?
                    this.getString(R.string.null_text) : bean.getHdGrandExp());
            mapHdDialogAddress.setText(StringUtils.isTrimEmpty(bean.getHdAddress()) ?
                    this.getString(R.string.null_text) : bean.getHdAddress());
            mapHdDialogUpdate.setText(StringUtils.isTrimEmpty(bean.getReportUserName()) ?
                    this.getString(R.string.null_text) : bean.getReportUserName()
                    + " 于 " + (StringUtils.isTrimEmpty(bean.getReportTime()) ?
                    this.getString(R.string.null_text) : bean.getReportTime()) + " 上报");
            GlideImgManager.loadImage(this, StringUrlUtil.transHttpUrlAndOnlyOne(bean.getHdPicture()), mapHdDialogImg);

            if (!StringUtils.isTrimEmpty(bean.getLat()) && !StringUtils.isTrimEmpty(bean.getLon())) {
                mapHdDialogDistance.setText("距离 " + GpsUtils.getDistance(bean.getLon(), bean.getLat(),
                        PreferencesUtil.getString(Constant.LOCATION_LATITUDE), PreferencesUtil.getString(Constant.LOCATION_LONGITUDE)) + " 米");
            } else {
                mapHdDialogDistance.setText(R.string.null_text);
            }
        } else {
            ToastBuilder.showShortWarning("数据获取失败");
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
    public void onGetHdDetailUpdateListSuccess(BasePostResponse<List<List<OrderSuperviseReportBean>>> basePostResponse) {
        mTitles = new String[basePostResponse.getObj().size() + 1];
        mTitles[0] = "全部";
        List<OrderSuperviseReportBean> beansAll = new ArrayList<>();
        for (int i = 0; i < basePostResponse.getObj().size(); i++) {
            beansAll.addAll(basePostResponse.getObj().get(i));
        }
        mHdFragments.add(new HdUpdateFileFragment(beansAll, bean, 0));
        for (int i = 0; i < basePostResponse.getObj().size(); i++) {
            List<OrderSuperviseReportBean> beans = basePostResponse.getObj().get(i);
            mTitles[i + 1] = beans.get(0).getReportUserName();
            mHdFragments.add(new HdUpdateFileFragment(beans, bean, i + 1));
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
    }

    @Override
    public void onSuccess(String msg) {

    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable msg) {
        baseOnFailure(msg);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onUnsubscribe();
        super.onDestroy();
    }
}
