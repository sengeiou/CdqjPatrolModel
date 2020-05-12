package com.cdqj.cdqjpatrolandroid.view.ui.plan;

import android.Manifest;
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.cdqj.cdqjpatrolandroid.base.BaseRecyclerView;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmDialogListener;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmSelectDialog;
import com.cdqj.cdqjpatrolandroid.comstomview.dropdownmenu.SelectSpinnerBean;
import com.cdqj.cdqjpatrolandroid.comstomview.recyclerview.SpacesItemDecoration;
import com.cdqj.cdqjpatrolandroid.event.MsgEvent;
import com.cdqj.cdqjpatrolandroid.gis.bean.GisParamsBean;
import com.cdqj.cdqjpatrolandroid.gis.bean.GpsBean;
import com.cdqj.cdqjpatrolandroid.gis.event.MapStatusInterface;
import com.cdqj.cdqjpatrolandroid.gis.event.MapStatusListener;
import com.cdqj.cdqjpatrolandroid.gis.event.MapTouchInterface;
import com.cdqj.cdqjpatrolandroid.gis.event.MapTouchListener;
import com.cdqj.cdqjpatrolandroid.gis.event.QueryLayerType;
import com.cdqj.cdqjpatrolandroid.gis.event.QueryParams;
import com.cdqj.cdqjpatrolandroid.gis.symbols.SymbolsMgr;
import com.cdqj.cdqjpatrolandroid.gis.util.GpsUtils;
import com.cdqj.cdqjpatrolandroid.gis.util.MapConstant;
import com.cdqj.cdqjpatrolandroid.gis.util.MapToolUtil;
import com.cdqj.cdqjpatrolandroid.gis.util.MapUtil;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.adapter.PlanTaskGridAdapter;
import com.cdqj.cdqjpatrolandroid.base.BaseActivity;
import com.cdqj.cdqjpatrolandroid.bean.MapGridBean;
import com.cdqj.cdqjpatrolandroid.bean.PiesChildItem;
import com.cdqj.cdqjpatrolandroid.bean.PlanListBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.RetrofitUtils;
import com.cdqj.cdqjpatrolandroid.http.inter.IGetGisParameterListener;
import com.cdqj.cdqjpatrolandroid.presenter.PlanFormulateDetailPresenter;
import com.cdqj.cdqjpatrolandroid.utils.PatrolEnterPointActivity;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.view.i.IPlanFormulateDetailView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.GeometryType;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.GeoElement;
import com.esri.arcgisruntime.mapping.LayerList;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.IdentifyGraphicsOverlayResult;
import com.esri.arcgisruntime.mapping.view.IdentifyLayerResult;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 制定计划详情
 *
 * @author lyf
 * 创建时间 2018年8月13日 14:38:22
 */
public class PlanFormulateDetailActivity extends BaseActivity<PlanFormulateDetailPresenter> implements MapTouchInterface, MapStatusInterface,
        IPlanFormulateDetailView {

    MapView mapMv;
    TextView planFormulateArea;
    TextView planFormulateDistance;
    TextView planFormulateAddress;
    TextView planFormulateLine;
    TextView planFormulatePeopleTitle;
    TextView planFormulatePeople;
    TextView planFormulateLinePeople;
    TextView planFormulateStartTimeTitle;
    TextView planFormulateStartTime;
    TextView planFormulateLineTime;
    TextView planFormulateEndTimeTitle;
    TextView planFormulateEndTime;
    TextView planFormulateLineThere;
    TextView planFormulateTaskTitle;
    TextView planFormulateLineBottom;
    TextView planFormulateEsc;
    TextView planFormulateSubmit;
    ConstraintLayout planFormulateCl;
    ConstraintLayout planFormulateMainCl;
    BaseRecyclerView planFormulateTask;

    private List<SelectSpinnerBean> list;

    private TimePickerView pvTime;

    private StringBuilder receiveUserIds;

    private String groupId = "0";

    private PlanTaskGridAdapter adapter;

    /**
     * 计划对象
     */
    private PlanListBean mPlanBean;

    /**
     * 第一次进入请求加载网格
     */
    private boolean isFirst = true;

    /*****  地图参数 ******/
    private GisParamsBean mGisParamsBean;
    /**
     * ArcGis地图显示位置
     */
    private Viewpoint viewPoint;
    /**
     * 动态添加图层
     */
    private LayerList layerList;
    /**
     * 默认中心点
     */
    private String strAppCenter;
    /**
     * 定位辅助
     */
    private LocationDisplay mLocationDisplay;
    /**
     * 地图状态监听
     */
    private MapStatusListener mapStatusListener;
    /**
     * 地图点击监听
     */
    private MapTouchListener mapTouchListener;
    /**
     * 地图空间类型
     */
    private SpatialReference reference;
    /**
     * 临时要素显示图层（绘制图层）
     */
    private GraphicsOverlay tempGraphicsLayer;
    /**
     * 网格图层
     */
    private GraphicsOverlay areaOverlay;
    /**
     * 文字图层
     */
    private GraphicsOverlay textOverlay;

    /**
     * 界面初始化
     * position 0 计划任务
     * 1 临时任务
     */
    @SuppressLint("InflateParams")
    @Override
    public void initView() {
        initMap();
        assert titleToolbar != null;
        titleToolbar.setMainTitle(getIntent()
                .getIntExtra("position", 0) == 0 ? "计划任务制定" : "临时任务制定");
        titleToolbar.setLeftTitleClickListener(v -> onBackPressed());

        list = new ArrayList<>();
        planFormulateTask.setLayoutManager(new GridLayoutManager(this, 3));
        planFormulateTask.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(4)));
        adapter = new PlanTaskGridAdapter(R.layout.cdqj_patrol_plan_task_grid_item_layout, list, this);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.bindToRecyclerView(planFormulateTask);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            list.get(position).setSelect(!list.get(position).isSelect());
            adapter1.notifyDataSetChanged();
        });
        initTimePicker();

    }

    @Override
    protected PlanFormulateDetailPresenter createPresenter() {
        return new PlanFormulateDetailPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_plan_formulate_detail;
    }

    @Override
    protected void findView() {
        mapMv = findViewById(R.id.plan_formulate_map);
        planFormulateArea = findViewById(R.id.plan_formulate_area);
        planFormulateDistance = findViewById(R.id.plan_formulate_distance);
        planFormulateAddress = findViewById(R.id.plan_formulate_address);
        planFormulateLine = findViewById(R.id.plan_formulate_line);
        planFormulatePeopleTitle = findViewById(R.id.plan_formulate_people_title);
        planFormulatePeople = findViewById(R.id.plan_formulate_people);
        planFormulateLinePeople = findViewById(R.id.plan_formulate_line_people);
        planFormulateStartTimeTitle = findViewById(R.id.plan_formulate_start_time_title);
        planFormulateStartTime = findViewById(R.id.plan_formulate_start_time);
        planFormulateLineTime = findViewById(R.id.plan_formulate_line_time);
        planFormulateEndTimeTitle = findViewById(R.id.plan_formulate_end_time_title);
        planFormulateEndTime = findViewById(R.id.plan_formulate_end_time);
        planFormulateLineThere = findViewById(R.id.plan_formulate_line_there);
        planFormulateTaskTitle = findViewById(R.id.plan_formulate_task_title);
        planFormulateLineBottom = findViewById(R.id.plan_formulate_line_bottom);
        planFormulateEsc = findViewById(R.id.plan_formulate_esc);
        planFormulateSubmit = findViewById(R.id.plan_formulate_submit);
        planFormulateCl = findViewById(R.id.plan_formulate_cl);
        planFormulateMainCl = findViewById(R.id.plan_formulate_main_cl);
        planFormulateTask = findViewById(R.id.plan_formulate_task);
    }

    @Override
    protected String getTitleText() {
        return "计划详情";
    }

    @Override
    public void initListener() {
        findViewById(R.id.plan_formulate_people).setOnClickListener(this::setClick);
        findViewById(R.id.plan_formulate_people_title).setOnClickListener(this::setClick);
        findViewById(R.id.plan_formulate_start_time).setOnClickListener(this::setClick);
        findViewById(R.id.plan_formulate_end_time).setOnClickListener(this::setClick);
        findViewById(R.id.plan_formulate_start_time_title).setOnClickListener(this::setClick);
        findViewById(R.id.plan_formulate_end_time_title).setOnClickListener(this::setClick);
        findViewById(R.id.plan_map_gps).setOnClickListener(this::setClick);
        findViewById(R.id.plan_map_full_extent).setOnClickListener(this::setClick);
        findViewById(R.id.plan_formulate_area).setOnClickListener(this::setClick);
        findViewById(R.id.plan_formulate_esc).setOnClickListener(this::setClick);
        findViewById(R.id.plan_formulate_submit).setOnClickListener(this::setClick);
    }

    public void setClick(View view) {
        int i1 = view.getId();
        if (i1 == R.id.plan_formulate_people) {// 人员选择界面
            PatrolEnterPointActivity.gotoPiesScreeningActivityForResult(this, 2, false, 1);
        } else if (i1 == R.id.plan_formulate_people_title) {// 人员选择界面
            PatrolEnterPointActivity.gotoPiesScreeningActivityForResult(this, 2, false, 1);
        } else if (i1 == R.id.plan_formulate_start_time) {// 开始时间
            if (ObjectUtils.isNotEmpty(pvTime) && !pvTime.isShowing()) {
                pvTime.show(view);
            }
        } else if (i1 == R.id.plan_formulate_end_time) {// 结束时间
            if (ObjectUtils.isNotEmpty(pvTime) && !pvTime.isShowing()) {
                pvTime.show(view);
            }

        } else if (i1 == R.id.plan_formulate_start_time_title) {// 开始时间
            if (ObjectUtils.isNotEmpty(pvTime) && !pvTime.isShowing()) {
                pvTime.show(view);
            }
        } else if (i1 == R.id.plan_formulate_end_time_title) {// 结束时间
            if (ObjectUtils.isNotEmpty(pvTime) && !pvTime.isShowing()) {
                pvTime.show(view);
            }
        } else if (i1 == R.id.plan_map_gps) {// gps定位
            MapToolUtil.getLocation(mapMv);
        } else if (i1 == R.id.plan_map_full_extent) {// 全图
            MapToolUtil.mapFullExtent(mapMv, viewPoint);
        } else if (i1 == R.id.plan_formulate_submit) {
            if (StringUtils.isTrimEmpty(planFormulatePeople.getText().toString())) {
                ToastBuilder.showShortWarning("请选择执行人");
                return;
            }
            if (StringUtils.isTrimEmpty(planFormulateStartTime.getText().toString())) {
                ToastBuilder.showShortWarning("请选择开始时间");
                return;
            }
            if (StringUtils.isTrimEmpty(planFormulateEndTime.getText().toString())) {
                ToastBuilder.showShortWarning("请选择结束时间");
                return;
            }
            StringBuilder exTask = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isSelect()) {
                    exTask.append(list.get(i).getValue());
                    exTask.append(",");
                }
            }
            if (StringUtils.isTrimEmpty(exTask.toString())) {
                ToastBuilder.showShortWarning("请选择执行的任务");
                return;
            }
            // 添加执行人
            mPlanBean.setGroupId(Long.valueOf(groupId));
            mPlanBean.setExecuteUsers(receiveUserIds.toString());
            mPlanBean.setBeginTime(planFormulateStartTime.getText().toString() + " 00:00:00");
            mPlanBean.setEndTime(planFormulateEndTime.getText().toString() + " 00:00:00");
            mPlanBean.setCheckTypes(exTask.toString().substring(0, exTask.length() - 1));
            // 确定
            new ConfirmSelectDialog(this)
                    .setTitleStr("提示")
                    .setContentStr("确定制定临时计划？")
                    .setYesStr("是")
                    .setNoStr("否")
                    .setListener(new ConfirmDialogListener() {
                        @Override
                        public void onYesClick() {
                            // 执行提交
                            mPresenter.subPlanFormulate(mPlanBean);
                        }

                        @Override
                        public void onNoClick() {
                        }
                    }).initView().show();
        } else if (i1 == R.id.plan_formulate_esc) {// 取消
            hidWindow();
        } else if (i1 == R.id.plan_formulate_area) {
            hidWindow();
        }
    }

    private void hidWindow() {
        MapUtil.addSingelLayer(mapMv, tempGraphicsLayer).getGraphics().clear();
        // 关闭
        YoYo.with(Techniques.FadeOutDown)
                .duration(100)
                .repeat(0)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        planFormulateCl.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .playOn(planFormulateCl);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == 1) {
            ArrayList<PiesChildItem> muitResult = data.getParcelableArrayListExtra("muitResult");
            if (muitResult.isEmpty()) {
                return;
            }
            receiveUserIds = new StringBuilder();
            StringBuilder receiveUsers = new StringBuilder();
            if (ObjectUtils.isNotEmpty(muitResult.get(0))) {
                groupId = muitResult.get(0).getPid();
            }
            for (int i = 0; i < muitResult.size(); i++) {
                receiveUserIds.append(muitResult.get(i).getId());
                receiveUsers.append(muitResult.get(i).getText());
                if (i != muitResult.size() - 1) {
                    receiveUserIds.append(",");
                    receiveUsers.append("、");
                }
            }
            planFormulatePeople.setText(receiveUsers.toString());
        }
    }

    /**
     * Dialog 模式下，在底部弹出时间选择
     */
    @SuppressLint("SimpleDateFormat")
    private void initTimePicker() {

        pvTime = new TimePickerBuilder(this, (date, v) -> {
            int i = v.getId();
            if (i == R.id.plan_formulate_start_time) {
                planFormulateStartTime.setText(TimeUtils.date2String(date, new SimpleDateFormat("yyyy-MM-dd")));
            } else if (i == R.id.plan_formulate_end_time) {
                planFormulateEndTime.setText(TimeUtils.date2String(date, new SimpleDateFormat("yyyy-MM-dd")));
            }
        })
                .setTimeSelectChangeListener(date -> LogUtils.d("onTimeSelectChanged"))
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true)
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                //修改动画样式
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);
                //改成Bottom,底部显示
                dialogWindow.setGravity(Gravity.BOTTOM);
            }
        }
    }

    /* 地图相关***/

    /**
     * 初始化地图
     */
    private void initMap() {
        MapUtil.setInitMap(mapMv);
        ArcGISMap gisMap = new ArcGISMap(new Basemap());
        mapMv.setMap(gisMap);
        layerList = gisMap.getBasemap().getBaseLayers();
        // TODO
        getGisParam();
    }

    /**
     * 获取地图参数
     */
    private void getGisParam() {
        RetrofitUtils.getGisParam(this,
                new IGetGisParameterListener() {
                    @Override
                    public void onFailure(ExceptionHandle.ResponeThrowable e) {
                        getLayerQueryFailed(new Object());
                    }

                    @Override
                    public void onGetGisParam(GisParamsBean bean) {
                        if (ObjectUtils.isNotEmpty(bean)) {
                            mGisParamsBean = bean;

                            mapStatusListener = new MapStatusListener(mapMv, PlanFormulateDetailActivity.this);
                            mapMv.addLayerViewStateChangedListener(mapStatusListener);

                            initLayers(mGisParamsBean);
                        } else {
                            new ConfirmSelectDialog(PlanFormulateDetailActivity.this)
                                    .setTitleStr("警告")
                                    .setContentStr("地图参数获取失败,是否重试")
                                    .setListener(new ConfirmDialogListener() {
                                        @Override
                                        public void onYesClick() {
                                            getGisParam();
                                        }

                                        @Override
                                        public void onNoClick() {
                                            getLayerQueryFailed(new Object());
                                        }
                                    });
                        }
                    }
                });
    }


    /**
     * 加载地图
     *
     * @param gisParams gisParams
     */
    public void initLayers(GisParamsBean gisParams) {
        // 开启定位
        setupLocationDisplay();

        if (ObjectUtils.isEmpty(gisParams)) {
            // webtile layer
            MapUtil.addLayer(layerList, MapUtil.createTDTLayer());
            reference = mapMv.getSpatialReference();
            ToastBuilder.showShortError("地图参数获取失败，部分功能无法使用");
            return;
        }

        //赋值地图范围
        if (!StringUtils.isTrimEmpty(gisParams.appCenter)) {
            strAppCenter = gisParams.appCenter;
        }

        //加载背景|管线地图
        MapUtil.addLayer(layerList, MapUtil.getBKLayer(gisParams));
        MapUtil.addLayer(layerList, MapUtil.getPipeLayer(gisParams));

        // initMapListener(gisParams);
    }

    /**
     * 定位
     */
    private void setupLocationDisplay() {
        mLocationDisplay = mapMv.getLocationDisplay();
        // 添加监听
        mLocationDisplay.addDataSourceStatusChangedListener(dataSourceStatusChangedEvent -> {

            // 权限申请相关等
            if (dataSourceStatusChangedEvent.isStarted() || dataSourceStatusChangedEvent.getError() == null) {
                return;
            }

            int requestPermissionsCode = 2;
            String[] requestPermissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

            if (!((ContextCompat.checkSelfPermission(this, requestPermissions[0]) == PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, requestPermissions[1]) == PackageManager.PERMISSION_GRANTED))) {
                ActivityCompat.requestPermissions(this, requestPermissions, requestPermissionsCode);
            } else {
                String message = String.format("Error in DataSourceStatusChangedListener: %s",
                        dataSourceStatusChangedEvent.getSource().getLocationDataSource().getError().getMessage());
                ToastBuilder.showShortError(message);
            }
        });
        // 为罗盘导航设置地图平移模式，并开始位置显示。
        mLocationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.RECENTER);
        mLocationDisplay.startAsync();
    }

    /**
     * 初始化地图监听事件，在地图参数获取成功后设置
     */
    @SuppressLint("ClickableViewAccessibility")
    private void initMapListener(GisParamsBean params) {
        if (mapTouchListener == null) {
            List<QueryLayerType> list = new ArrayList<>();
            list.add(QueryLayerType.QueryOnline);
            list.add(QueryLayerType.QueryAllGpraphicsOverlay);
            QueryParams queryParams = new QueryParams(list, null, areaOverlay, 1, null);
            mapTouchListener = new MapTouchListener(this, mapMv, queryParams, this);
            // 添加网格监听
            mapTouchListener.addGisParamsToParams(QueryLayerType.QueryGpraphicsOverlay, params);
        }
        mapMv.setOnTouchListener(mapTouchListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mLocationDisplay.startAsync();
        } else {
            ToastBuilder.showShortError("权限获取失败");
        }
    }

    @Override
    public void getLayerStatusSuccess(String state) {

    }

    @Override
    public void getLayerActiveSuccess() {
        reference = mapMv.getSpatialReference();
        viewPoint = MapUtil.getViewpointFromParam(strAppCenter, reference);
        // 各个图层初始化
        areaOverlay = MapUtil.addSingelLayer(mapMv, areaOverlay);
        areaOverlay.setMinScale(MapConstant.MIN_SCALE);
        textOverlay = MapUtil.addSingelLayer(mapMv, textOverlay);
        textOverlay.setMinScale(MapConstant.MIN_SCALE);
        tempGraphicsLayer = MapUtil.addSingelLayer(mapMv, tempGraphicsLayer);
        tempGraphicsLayer.setMinScale(MapConstant.MIN_SCALE);
        //MapToolUtil.getLocation(mapMv);
        if (isFirst) {
            // 地图初始化成功后获取网格数据加载
            mPresenter.getPatrolMapGridList();
            isFirst = false;
        }
        initMapListener(mGisParamsBean);
    }

    @Override
    public void getLayerErrorSuccess(String layerName) {
        ToastBuilder.showShortError(layerName + "图层加载失败...");
    }

    @Override
    public void getMapClickPtSuccess(Point pt, int flag) {

    }

    @Override
    public void getMapLongPressPtSuccess(Point pt) {

    }

    @Override
    public void getLayerQuerySuccess(List<GeoElement> geoElementList) {

    }

    @Override
    public void getLayerQueryFailed(Object e) {
        MapUtil.addLayer(layerList, MapUtil.createTDTLayer());
        reference = mapMv.getSpatialReference();
        ToastBuilder.showShortError("地图参数获取失败，部分功能无法使用");
    }

    @Override
    public void getGraphicsQueryFailed(Object e) {

    }

    @Override
    public void getAllLayerQuerySuccess(List<IdentifyLayerResult> layerResultList) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getGraphicsOverlaySuccess(List<Graphic> graphicList) {
        if (graphicList == null || graphicList.size() == 0) {
            return;
        }
        // 清除地图图标
        MapUtil.addSingelLayer(mapMv, tempGraphicsLayer).getGraphics().clear();

        Graphic graphic = graphicList.iterator().next();
        Geometry geo = graphic.getGeometry();
        if (geo.getGeometryType() == GeometryType.POINT) {
            return;
        }
        MapUtil.addSingelLayer(mapMv, tempGraphicsLayer).getGraphics().add(SymbolsMgr.highlightGraphic(geo));
        Map<String, Object> map = graphic.getAttributes();
        if (ObjectUtils.isEmpty(map.get("gridNumber"))) {
            ToastBuilder.showShortWarning("网格编号为空...");
        } else {
            mPlanBean = new PlanListBean();
            // 网格编号获取成功
            planFormulateArea.setText(map.get("gridNumber").toString());
            mPlanBean.setGridIds(map.get("id").toString());
            mPlanBean.setAreaId(ObjectUtils.isEmpty(map.get("areaId")) ? 0L :
                    Long.valueOf(map.get("areaId").toString()));
            mPlanBean.setGridAreas(ObjectUtils.isEmpty(map.get("gridAreas")) ?
                    0.0 : Double.valueOf(map.get("gridAreas").toString()));
            mPlanBean.setPlanType(2);
            mPlanBean.setExeType(1);
            mPlanBean.setRequireExeTimes(1);
            planFormulateAddress.setText(ObjectUtils.isEmpty(map.get("address")) ?
                    getString(R.string.null_text) : map.get("address").toString());
            if (ObjectUtils.isEmpty(map.get("distance"))) {
                planFormulateDistance.setText(getString(R.string.null_text));
            } else {
                String[] xy = map.get("distance").toString().split(" ");
                List<GpsBean> list = new ArrayList<>();
                for (String location : xy) {
                    list.add(new GpsBean(location.split(",")[1], location.split(",")[0]));
                }
                double distance = GpsUtils.getMinDistance(PreferencesUtil.getString(Constant.LOCATION_LATITUDE), PreferencesUtil.getString(Constant.LOCATION_LONGITUDE), list);
                planFormulateDistance.setText("距离 " + distance + " 米");
            }
            list.clear();
            // 设置任务
            list.add(new SelectSpinnerBean("中压A管线\n(" + map.get("middlePipeA").toString() + "米)", "middlePipeA"));
            list.add(new SelectSpinnerBean("阀门\n(" + map.get("valveDevice").toString() + "个)", "valveDevice"));
            list.add(new SelectSpinnerBean("调压设备\n(" + map.get("pressureDevice").toString() + "个)", "pressureDevice"));
            list.add(new SelectSpinnerBean("巡检点\n(" + map.get("checkPoint").toString() + "个)", "checkPoint"));
            adapter.setNewData(list);
            planFormulateCl.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.FadeInUp)
                    .duration(100)
                    .repeat(0)
                    .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                    .interpolate(new AccelerateDecelerateInterpolator())
                    .playOn(planFormulateCl);
        }
    }

    @Override
    public void getAllGraphicsOverlaySuccess(List<IdentifyGraphicsOverlayResult> graphicsOverlayResults) {

    }

    @Override
    public void getOnlineLayerSuccess(FeatureQueryResult featureQueryResult) {

    }

    @Override
    public void getOnlineLayerSuccess(FeatureQueryResult featureQueryResult, Point ptClick) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mapMv.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapMv.pause();
    }

    @Override
    protected void onDestroy() {
        if (mLocationDisplay.isStarted()) {
            mLocationDisplay.stop();
        }
        if (ObjectUtils.isNotEmpty(mapMv)) {
            mapMv.dispose();
        }
        mPresenter.onUnsubscribe();
        super.onDestroy();
    }

    @Override
    public void onGetPatrolMapGridListSuccess(BaseGetResponse<MapGridBean> basePostResponse) {
        if (basePostResponse.getTotal() > 0) {
            // 加载图层
            List<MapGridBean> list = basePostResponse.getRows();
            List<Map<String, Object>> mapList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setGisArea(list.get(i).getGridAreas());
                list.get(i).setName(list.get(i).getGridNumber());
                mapList.add(json2MapInMap(list.get(i)));
            }
            MapUtil.addPolygons(this, textOverlay, areaOverlay, reference, list, mapList, basePostResponse.getRows().get(0).getGridColor(), true);
        } else {
            ToastBuilder.showShortError("网格信息获取失败");
        }
    }

    @Override
    public void onSubPlanFormulateSuccess(BasePostResponse<Object> body) {
        if (body.isSuccess()) {
            // 执行计划制定成功
            hidWindow();
            ToastBuilder.showShort(body.getMsg());
            EventBus.getDefault().post(new MsgEvent<>(2, true));
            ToastBuilder.showShort("计划制定成功");
        } else {
            ToastBuilder.showShortWarning(body.getMsg());
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
        adapter.setNewData(new ArrayList<>());
        baseOnFailure(msg);
    }


    private Map<String, Object> json2MapInMap(MapGridBean bean) {
        if (bean == null) {
            return null;
        }
        Map<String, Object> m = new HashMap<>(2);
        m.put("id", bean.getId());
        m.put("gridNumber", bean.getGridNumber());
        m.put("address", bean.getGridCanton());
        m.put("distance", bean.getGridAreas());
        // 巡检点
        m.put("checkPoint", bean.getCheckPoint());
        // 阀门
        m.put("valveDevice", bean.getValveDevice());
        // 中压管线
        m.put("middlePipeA", bean.getMiddlePipeA());
        // 调压设备
        m.put("pressureDevice", bean.getPressureDevice());
        // 片区
        m.put("areaId", bean.getAreaId());
        // 网格总面积
        m.put("gridAreas", bean.getGridArea());
        return m;
    }

    @Override
    public void onBackPressed() {
        if (planFormulateCl.getVisibility() == View.GONE) {
            back();
        } else {
            hidWindow();
        }
    }

    private void back() {
        new ConfirmSelectDialog(this)
                .setTitleStr("提示")
                .setContentStr("是否放弃计划制定直接退出")
                .setYesStr("确定")
                .setNoStr("取消")
                .setListener(new ConfirmDialogListener() {
                    @Override
                    public void onYesClick() {
                        finish();
                    }

                    @Override
                    public void onNoClick() {
                    }
                })
                .initView().show();
    }
}
