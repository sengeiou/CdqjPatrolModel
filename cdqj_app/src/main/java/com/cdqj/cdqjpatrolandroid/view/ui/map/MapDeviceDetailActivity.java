package com.cdqj.cdqjpatrolandroid.view.ui.map;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.base.BaseRecyclerView;
import com.cdqj.cdqjpatrolandroid.bean.MapBean;
import com.cdqj.cdqjpatrolandroid.comstomview.recyclerview.SpacesItemDecoration;
import com.cdqj.cdqjpatrolandroid.gis.adapter.LineAdapter;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.base.BaseActivity;
import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.bean.DeviceBean;
import com.cdqj.cdqjpatrolandroid.utils.NavigationUtil;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备详情
 *
 * @author lyf
 * @date 2018年10月30日 09:21:55
 */
public class MapDeviceDetailActivity extends BaseActivity {

    TextView mapLineDialogTitle;
    TextView mapLineDialogDistance;
    TextView mapLineDialogAddress;
    TextView mapLineDialogLine;
    BaseRecyclerView mapPipelineRv;
    SmartRefreshLayout mapPipelineRefreshLayout;
    TextView mapBottomBarReport;

    private DeviceBean deviceBean;
    private List<MapBean> mList = new ArrayList<>();

    /**
     * 导航坐标(默认成都坐标)
     */
    private double navX = 30.572262, navY = 104.066513;

    /**
     * 界面初始化
     */
    @Override
    public void initView() {
        //是否启用上拉加载功能
        mapPipelineRefreshLayout.setEnablePureScrollMode(true);
        mapPipelineRv.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(8)));
        mapPipelineRv.setLayoutManager(new LinearLayoutManager(this));
        mapLineDialogLine.setVisibility(View.INVISIBLE);
        mapBottomBarReport.setVisibility(View.INVISIBLE);
        deviceBean = getIntent().getParcelableExtra("DeviceBean");
        if (ObjectUtils.isNotEmpty(deviceBean)) {
            if (ObjectUtils.isNotEmpty(deviceBean.getLat()) && ObjectUtils.isNotEmpty(deviceBean.getLon())) {
                navX = deviceBean.getLat();
                navY = deviceBean.getLon();
            }
            mapLineDialogTitle.setText(StringUtils.isTrimEmpty(deviceBean.getDeviceTypeExp()) ?
                    getString(R.string.null_text) : deviceBean.getDeviceTypeExp());
            mapLineDialogAddress.setText(StringUtils.isTrimEmpty(deviceBean.getAddress()) ?
                    getString(R.string.null_text) : deviceBean.getAddress());
            mapLineDialogDistance.setText(StringUtils.isTrimEmpty(deviceBean.getProjectName()) ?
                    getString(R.string.null_text) : deviceBean.getProjectName());

            @SuppressLint("InflateParams") View emptyView = getLayoutInflater().inflate(R.layout.cdqj_patrol_list_empty_view, null);
            emptyView.findViewById(R.id.empty_img).setVisibility(View.GONE);
            emptyView.findViewById(R.id.empty_log).setVisibility(View.GONE);

            mList.add(new MapBean("网格ID", ObjectUtils.isNotEmpty(deviceBean.getGridId()) ?
                    String.valueOf(deviceBean.getGridId()) : getString(R.string.null_text)));
            mList.add(new MapBean("经度", ObjectUtils.isNotEmpty(deviceBean.getLon()) ?
                    String.valueOf(deviceBean.getLon()) : getString(R.string.null_text)));
            mList.add(new MapBean("纬度", ObjectUtils.isNotEmpty(deviceBean.getLat()) ?
                    String.valueOf(deviceBean.getLat()) : getString(R.string.null_text)));
            mList.add(new MapBean("OBJECTID", StringUtils.isTrimEmpty(deviceBean.getObjectid()) ?
                    getString(R.string.null_text) : deviceBean.getObjectid()));
            mList.add(new MapBean("PPID", StringUtils.isTrimEmpty(deviceBean.getPpid()) ?
                    getString(R.string.null_text) : deviceBean.getPpid()));
            mList.add(new MapBean("添加来源", deviceBean.getSourceType() == 1 ? "GIS" : "平台"));
            mList.add(new MapBean("设备GIS类型", deviceBean.getGisType() == 1 ? "点" : "线"));
           /* mList.add(new MapBean("扩展属性", StringUtils.isTrimEmpty(deviceBean.getDeviceExp())?
                    getString(R.string.null_text):deviceBean.getDeviceExp()));*/
            mList.add(new MapBean("添加时间", StringUtils.isTrimEmpty(deviceBean.getAddTime()) ?
                    getString(R.string.null_text) : deviceBean.getAddTime()));
            mList.add(new MapBean("计量个数(米)", String.valueOf(deviceBean.getCount())));
            mList.add(new MapBean("备注描述", StringUtils.isTrimEmpty(deviceBean.getRemarks()) ?
                    getString(R.string.null_text) : deviceBean.getRemarks()));

            LineAdapter adapter = new LineAdapter(R.layout.cdqj_patrol_pipeline_detail_item, mList);
            adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
            adapter.setEmptyView(emptyView);
            adapter.bindToRecyclerView(mapPipelineRv);
        } else {
            ToastBuilder.showShortWarning("设备信息获取失败");
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_map_device_detail;
    }

    @Override
    protected void findView() {
        mapLineDialogTitle = findViewById(R.id.map_line_dialog_title);
        mapLineDialogDistance = findViewById(R.id.map_line_dialog_distance);
        mapLineDialogAddress = findViewById(R.id.map_line_dialog_address);
        mapLineDialogLine = findViewById(R.id.map_line_dialog_line);
        mapPipelineRv = findViewById(R.id.map_pipeline_rv);
        mapPipelineRefreshLayout = findViewById(R.id.map_pipeline_refreshLayout);
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
}
