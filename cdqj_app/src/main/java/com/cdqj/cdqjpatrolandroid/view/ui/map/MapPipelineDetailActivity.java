package com.cdqj.cdqjpatrolandroid.view.ui.map;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.base.BaseRecyclerView;
import com.cdqj.cdqjpatrolandroid.comstomview.recyclerview.SpacesItemDecoration;
import com.cdqj.cdqjpatrolandroid.gis.adapter.LineAdapter;
import com.cdqj.cdqjpatrolandroid.gis.bean.GisOtherCoordinateBean;
import com.cdqj.cdqjpatrolandroid.gis.bean.GisPointCoordinateBean;
import com.cdqj.cdqjpatrolandroid.utils.ConvertorDataUtils;
import com.cdqj.cdqjpatrolandroid.utils.GsonUtils;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.base.BaseActivity;
import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.comstomview.StateView;
import com.cdqj.cdqjpatrolandroid.utils.DataUtils;
import com.cdqj.cdqjpatrolandroid.utils.NavigationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.Map;

import io.realm.Realm;

/**
 * 地图管线详情
 *
 * @author lyf
 */
public class MapPipelineDetailActivity extends BaseActivity {
    SmartRefreshLayout mapPipelineRefreshLayout;
    TextView mapLineDialogTitle;
    TextView mapLineDialogDistance;
    TextView mapLineDialogAddress;
    BaseRecyclerView mapPipelineRv;
    TextView mapLineDialogLine;
    TextView mapBottomBarReport;

    /**
     * 地图属性对象
     */
    private String mapStr, geoStr;

    /**
     * 距离
     */
    private double dis = 0.0;
    /**
     * 导航坐标(默认成都坐标)
     */
    private double navX = 30.572262, navY = 104.066513;
    /**
     * 地图对象类型
     * 0.点
     * 1.其他
     */
    private int geometryType;

    private Realm realm;

    /**
     * 界面初始化
     */
    @Override
    public void initView() {
        mStateView = StateView.inject(mapPipelineRv);
        realm = Realm.getDefaultInstance();
        //是否启用上拉加载功能
        mapPipelineRefreshLayout.setEnablePureScrollMode(true);
        mapStr = getIntent().getStringExtra("mapStr");
        geoStr = getIntent().getStringExtra("geoStr");
        geometryType = getIntent().getIntExtra("geometryType", 0);
        dis = getIntent().getDoubleExtra("dis", 0.0);

        mapPipelineRv.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(8)));
        mapPipelineRv.setLayoutManager(new LinearLayoutManager(this));
        mapLineDialogLine.setVisibility(View.INVISIBLE);
        mapBottomBarReport.setVisibility(View.INVISIBLE);

        if (!StringUtils.isTrimEmpty(mapStr)) {
            Map<String, Object> mapBeans = ConvertorDataUtils.json2Map(mapStr);
            mapLineDialogTitle.setText(DataUtils.getLineTypeStr(mapBeans.get("类型").toString(), realm));
            mapLineDialogAddress.setText(ObjectUtils.isNotEmpty(mapBeans.get("地址")) ? mapBeans.get("地址").toString() :
                    ObjectUtils.isNotEmpty(mapBeans.get("安装地址")) ? mapBeans.get("安装地址").toString() : getString(R.string.null_text));
            mapLineDialogDistance.setText(dis == 0.0 ? getString(R.string.null_text) : "距离" + dis + "米");

            if (0 == geometryType) {
                // 点类型
                GisPointCoordinateBean coordinateBean = GsonUtils.gsonBuilder.create().fromJson(geoStr, GisPointCoordinateBean.class);
                navY = coordinateBean.getY();
                navX = coordinateBean.getX();
            } else {
                // 其他类型（坐标集合）
                GisOtherCoordinateBean coordinateBean = GsonUtils.gsonBuilder.create().fromJson(geoStr, GisOtherCoordinateBean.class);
                if (ObjectUtils.isNotEmpty(coordinateBean.getPaths())
                        && coordinateBean.getPaths().size() > 0) {
                    navY = coordinateBean.getPaths().get(0).get(0).get(0);
                    navX = coordinateBean.getPaths().get(0).get(0).get(1);
                }
            }

            LineAdapter adapter = new LineAdapter(R.layout.cdqj_patrol_pipeline_detail_item, ConvertorDataUtils.convertorMap2ListBean(mapBeans));
            adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
            adapter.bindToRecyclerView(mapPipelineRv);
            if (adapter.getData().isEmpty()) {
                mStateView.showEmpty();
            } else {
                mStateView.showContent();
            }
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_map_pipeline_detail;
    }

    @Override
    protected void findView() {
         mapPipelineRefreshLayout = findViewById(R.id.map_pipeline_refreshLayout);
         mapLineDialogTitle = findViewById(R.id.map_line_dialog_title);
         mapLineDialogDistance = findViewById(R.id.map_line_dialog_distance);
         mapLineDialogAddress = findViewById(R.id.map_line_dialog_address);
         mapPipelineRv = findViewById(R.id.map_pipeline_rv);
         mapLineDialogLine = findViewById(R.id.map_line_dialog_line);
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
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
