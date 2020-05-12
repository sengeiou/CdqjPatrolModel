package com.cdqj.cdqjpatrolandroid.view.ui.map;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.base.BaseRecyclerView;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.adapter.TaskSuperviseReportAdapter;
import com.cdqj.cdqjpatrolandroid.base.BaseActivity;
import com.cdqj.cdqjpatrolandroid.bean.PatrolTaskResultBean;
import com.cdqj.cdqjpatrolandroid.bean.PlanSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.comstomview.StateView;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.presenter.MapPlanDetailPresenter;
import com.cdqj.cdqjpatrolandroid.utils.NavigationUtil;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.view.i.IMapPlanDetailView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 地图计划弹窗详情
 *
 * @author lyf
 * @date 2018年10月19日 11:43:12
 */
public class MapPlanDetailActivity extends BaseActivity<MapPlanDetailPresenter> implements IMapPlanDetailView {

    TextView mapPlanDetailArea;
    TextView mapPlanDetailReportCount;
    TextView mapPlanStatusDesc;
    TextView mapPlanDetailDistance;
    TextView mapPlanDetailAddress;
    TextView mapPlanDetailPeople;
    TextView mapPlanDetailReportTime;
    TextView mapPlanDetailEndTime;
    TextView mapPlanDetailTime;
    BaseRecyclerView mapPlanDetailRv;
    SmartRefreshLayout mapPlanRefreshLayout;
    TextView mapBottomBarReport;

    /**
     * 巡检内容对象
     */
    private PatrolTaskResultBean taskBean;
    private TaskSuperviseReportAdapter rvAdapter;

    private String taskId;

    /**
     * 上报列表
     */
    private List<PlanSuperviseReportBean> beans = new ArrayList<>();

    /**
     * 导航点
     */
    private double navX = 30.572262, navY = 104.066513;

    /**
     * 界面初始化
     */
    @SuppressLint("InflateParams")
    @Override
    public void initView() {
        mStateView = StateView.inject(mapPlanDetailRv);
        mStateView.setOnRetryClickListener(() -> {
            mPresenter.getTaskReports(taskId, true);
        });
        mapBottomBarReport.setVisibility(View.INVISIBLE);
        mPresenter = new MapPlanDetailPresenter(this);
        taskBean = getIntent().getParcelableExtra("PatrolTaskResultBean");

        mapPlanDetailRv.setLayoutManager(new LinearLayoutManager(this));
        rvAdapter = new TaskSuperviseReportAdapter(R.layout.cdqj_patrol_order_supervise_report_item, beans, this, taskBean);
        rvAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        rvAdapter.bindToRecyclerView(mapPlanDetailRv);

        if (ObjectUtils.isNotEmpty(taskBean)) {
            navX = taskBean.getLat();
            navY = taskBean.getLon();
            taskId = taskBean.getId();

            mapPlanRefreshLayout.setOnRefreshListener(refreshlayout -> mPresenter.getTaskReports(taskId, false));
            mapPlanRefreshLayout.setOnLoadMoreListener(refreshlayout -> {
                refreshlayout.finishLoadMoreWithNoMoreData();
                refreshlayout.setNoMoreData(false);
            });

            mPresenter.getTaskReports(taskId, true);

            mapPlanDetailArea.setText(StringUtils.isTrimEmpty(taskBean.getCheckName()) ?
                    getString(R.string.null_text) : taskBean.getCheckName());
            mapPlanDetailAddress.setText(StringUtils.isTrimEmpty(taskBean.getAddress()) ?
                    getString(R.string.null_text) : taskBean.getAddress());
            mapPlanDetailPeople.setText(StringUtils.isTrimEmpty(taskBean.getAddUserExp()) ?
                    getString(R.string.null_text) : taskBean.getAddUserExp());
            mapPlanDetailDistance.setText(StringUtils.isTrimEmpty(taskBean.getAddUserExp()) ?
                    getString(R.string.null_text) : taskBean.getAddUserExp());
            mapPlanStatusDesc.setText(StringUtils.isTrimEmpty(taskBean.getPassStatusExp()) ?
                    getString(R.string.null_text) : taskBean.getPassStatusExp());
            mapPlanDetailReportCount.setText(ObjectUtils.isEmpty(taskBean.getPassTimes()) ?
                    getString(R.string.null_text) : "上报次数：" + taskBean.getPassTimes());
            mapPlanDetailEndTime.setText(StringUtils.isTrimEmpty(taskBean.getUpdateTime()) ?
                    getString(R.string.null_text) : taskBean.getUpdateTime().split(" ")[0]);
            mapPlanDetailReportTime.setText(StringUtils.isTrimEmpty(taskBean.getAddTime()) ?
                    getString(R.string.null_text) : taskBean.getAddTime().split(" ")[0]);
            mapPlanDetailTime.setText(StringUtils.isTrimEmpty(taskBean.getRemarks()) ?
                    getString(R.string.null_text) : taskBean.getRemarks());
        } else {
            ToastBuilder.showShortError("计划巡检对象属性获取失败");
        }

    }

    @Override
    protected MapPlanDetailPresenter createPresenter() {
        return new MapPlanDetailPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_map_plan_detail;
    }

    @Override
    protected void findView() {
        mapPlanDetailArea = findViewById(R.id.map_plan_detail_area);
        mapPlanDetailReportCount = findViewById(R.id.map_plan_detail_report_count);
        mapPlanStatusDesc = findViewById(R.id.map_plan_status_desc);
        mapPlanDetailDistance = findViewById(R.id.map_plan_detail_distance);
        mapPlanDetailAddress = findViewById(R.id.map_plan_detail_address);
        mapPlanDetailPeople = findViewById(R.id.map_plan_detail_people);
        mapPlanDetailReportTime = findViewById(R.id.map_plan_detail_report_time);
        mapPlanDetailEndTime = findViewById(R.id.map_plan_detail_end_time);
        mapPlanDetailTime = findViewById(R.id.map_plan_detail_time);
        mapPlanDetailRv = findViewById(R.id.map_plan_detail_rv);
        mapPlanRefreshLayout = findViewById(R.id.map_plan_refreshLayout);
        mapBottomBarReport = findViewById(R.id.map_bottom_bar_report);
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
    }

    public void setClick(View view) {
        int i = view.getId();
        if (i == R.id.map_bottom_bar_map) {
            this.finish();
        } else if (i == R.id.map_bottom_bar_report) {
        } else if (i == R.id.map_bottom_bar_navigation) {
            NavigationUtil.showNavWindow(navX, navY, this);
        }
    }

    @Override
    public void onGetMapPlanDetailSuccess(BaseGetResponse<PlanSuperviseReportBean> body, boolean flag) {
        rvAdapter.setNewData(body.getRows());
        if (rvAdapter.getData().size() == 0) {
            mStateView.showEmpty();
        } else {
            mStateView.showContent();
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
        mapPlanRefreshLayout.finishLoadMore(false);
        mapPlanRefreshLayout.finishRefresh(false);
        baseHideProgress();
    }

    @Override
    public void onSuccess(String msg) {

    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable msg) {
        mapPlanRefreshLayout.finishLoadMore(false);
        mapPlanRefreshLayout.finishRefresh(false);
        rvAdapter.setNewData(new ArrayList<>());
        baseOnFailure(msg, true);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onUnsubscribe();
        super.onDestroy();
    }
}
