package com.cdqj.cdqjpatrolandroid.view.ui.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.cdqj.cdqjpatrolandroid.bean.TabEntity;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmDialogListener;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmSelectDialog;
import com.cdqj.cdqjpatrolandroid.gis.bean.GisParamsBean;
import com.cdqj.cdqjpatrolandroid.gis.event.MapStatusInterface;
import com.cdqj.cdqjpatrolandroid.gis.event.MapStatusListener;
import com.cdqj.cdqjpatrolandroid.gis.event.MapTouchInterface;
import com.cdqj.cdqjpatrolandroid.gis.event.MapTouchListener;
import com.cdqj.cdqjpatrolandroid.gis.event.QueryLayerType;
import com.cdqj.cdqjpatrolandroid.gis.event.QueryParams;
import com.cdqj.cdqjpatrolandroid.gis.util.MapConstant;
import com.cdqj.cdqjpatrolandroid.gis.util.MapToolUtil;
import com.cdqj.cdqjpatrolandroid.gis.util.MapUtil;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.adapter.DialogBtnAdapter;
import com.cdqj.cdqjpatrolandroid.base.BaseActivity;
import com.cdqj.cdqjpatrolandroid.bean.LocationBean;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.RetrofitUtils;
import com.cdqj.cdqjpatrolandroid.http.inter.IGetGisParameterListener;
import com.cdqj.cdqjpatrolandroid.presenter.PersonMapPresenter;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.view.i.IPersonMapView;
import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
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
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ListHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * 人员轨迹管理界面
 *
 * @author lyf
 * @date 2018年10月22日 10:17:12
 */
public class PersonTrajectoryMapActivity extends BaseActivity<PersonMapPresenter> implements MapTouchInterface, MapStatusInterface, IPersonMapView {

    MapView mapMv;

    /**
     * 人员ID
     */
    private String id;

    /**
     * 第一次进入请求加载人员默认轨迹
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
     * 轨迹图层
     */
    private GraphicsOverlay areaOverlay;
    /**
     * 文字图层
     */
    private GraphicsOverlay textOverlay;

    private DialogPlus mDialogPlus;

    /**
     * 界面初始化
     */
    @Override
    public void initView() {
        initMap();
        id = getIntent().getStringExtra("id");
        assert titleToolbar != null;
        titleToolbar.setRightTitleText("更多");
        titleToolbar.setRightTitleColor(ContextCompat.getColor(this, R.color.theme_one));
        titleToolbar.setRightTitleClickListener(v -> {
            // 轨迹时段筛选弹出框
            selectTime();
        });
        ToastBuilder.showShort("默认展示当天轨迹");
    }

    @Override
    protected PersonMapPresenter createPresenter() {
        return new PersonMapPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_person_trajectory_map;
    }

    @Override
    protected void findView() {
        mapMv = findViewById(R.id.person_trajectory_map);
    }

    @Override
    protected String getTitleText() {
        return "人员轨迹";
    }

    @Override
    public void initListener() {
        findViewById(R.id.person_trajectory_map_gps).setOnClickListener(this::setClick);
        findViewById(R.id.person_trajectory_map_full_extent).setOnClickListener(this::setClick);
    }

    public void setClick(View view) {
        int i = view.getId();
        if (i == R.id.person_trajectory_map_gps) {// gps定位
            MapToolUtil.getLocation(mapMv);
        } else if (i == R.id.person_trajectory_map_full_extent) {// 全图
            MapToolUtil.mapFullExtent(mapMv, viewPoint);
        }
    }

    /**
     * 初始化地图
     */
    private void initMap() {
        mPresenter = new PersonMapPresenter(this);
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

                            mapStatusListener = new MapStatusListener(mapMv, PersonTrajectoryMapActivity.this);
                            mapMv.addLayerViewStateChangedListener(mapStatusListener);

                            initLayers(mGisParamsBean);
                        } else {
                            new ConfirmSelectDialog(PersonTrajectoryMapActivity.this)
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

    @SuppressLint("SimpleDateFormat")
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
            mPresenter.getPersonTrajectoryList(id,
                    TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd")) + " 00:00:00",
                    TimeUtils.date2String(new Date()));
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

    @Override
    public void getGraphicsOverlaySuccess(List<Graphic> graphicList) {

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
    public void onGetPersonTrajectorySuccess(BasePostResponse<List<LocationBean>> body) {
        MapUtil.addSingelLayer(mapMv, areaOverlay).getGraphics().clear();
        // 绘制人员轨迹图层
        if (!body.isSuccess()) {
            ToastBuilder.showShortWarning(body.getMsg());
        }
        if (body.getObj().size() > 1) {
            PointCollection pts = new PointCollection(mapMv.getSpatialReference());
            Point pointS = new Point(body.getObj().get(0).getLon(), body.getObj().get(0).getLat(), reference);
            Point pointEnd = null;
            for (int  i= 0;i < body.getObj().size() - 1;i ++) {
                Point point = new Point(body.getObj().get(i).getLon(), body.getObj().get(i).getLat(), reference);
                if (ObjectUtils.isNotEmpty(point)) {
                    pts.add(point);
                    pointEnd = point;
                }
                MapUtil.addPointLayer(mapMv, areaOverlay, this, R.mipmap.icon_arrow, point, false);
            }
            SimpleLineSymbol polylineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID,
                    Color.parseColor("#666AD1"), ConvertUtils.dp2px(2));
            MapUtil.addLineLayer(mapMv, areaOverlay, polylineSymbol, pts, new HashMap<>(1), false);
            MapUtil.addPointLayer(mapMv, areaOverlay, this, R.mipmap.icon_route_start, pointS, new HashMap<>(1), false, false);
            MapUtil.addPointLayer(mapMv, areaOverlay, this, R.mipmap.icon_route_end, pointEnd, new HashMap<>(1), false, false);
        } else if (body.getObj().size() == 1) {
            MapUtil.addPointLayer(mapMv, areaOverlay, this, R.mipmap.icon_person_onlin, body.getObj().get(0).getLon(), body.getObj().get(0).getLat(), reference, false);
        } else {
            ToastBuilder.showShortWarning("当前人员暂无轨迹");
        }
    }

    /**
     * 轨迹时间选择
     */
    @SuppressLint("SimpleDateFormat")
    public void selectTime() {
        if (ObjectUtils.isEmpty(mDialogPlus)) {
            List<TabEntity> entities = new ArrayList<>();
            entities.add(new TabEntity("最近一天", 0, 1));
            entities.add(new TabEntity("最近三天", 0, 1));
            entities.add(new TabEntity("最近五天", 0, 1));
            DialogBtnAdapter adapter = new DialogBtnAdapter(this, entities);
            mDialogPlus = DialogPlus.newDialog(Objects.requireNonNull(this))
                    .setContentHolder(new ListHolder())
                    .setContentWidth(ScreenUtils.getScreenWidth() / 3 * 2)
                    .setPadding(ConvertUtils.dp2px(20), ConvertUtils.dp2px(35),
                            ConvertUtils.dp2px(20), ConvertUtils.dp2px(23))
                    .setGravity(Gravity.CENTER)
                    .setCancelable(true)
                    .setContentBackgroundResource(R.drawable.stroke_bg_radius)
                    .setAdapter(adapter)
                    .setInAnimation(R.anim.fade_in_center)
                    .setOutAnimation(R.anim.fade_out_center)
                    .setOnItemClickListener((dialog, item, view, position) -> {
                        mDialogPlus.dismiss();
                        String startTime = position==0?(TimeUtils.date2String(new Date(),new SimpleDateFormat("yyyy-MM-dd")) + " 00:00:00"):
                                position==1?(TimeUtils.date2String(new Date(System.currentTimeMillis() - Constant.ONE_DAY * 2),new SimpleDateFormat("yyyy-MM-dd")) + " 00:00:00"):
                                        (TimeUtils.date2String(new Date(System.currentTimeMillis() - Constant.ONE_DAY * 4),new SimpleDateFormat("yyyy-MM-dd")) + " 00:00:00");
                        String endTime = TimeUtils.date2String(new Date());
                        mPresenter.getPersonTrajectoryList(id, startTime, endTime);
                    })
                    .create();
        }
        if (mDialogPlus.isShowing()) {
            mDialogPlus.dismiss();
        }
        mDialogPlus.show();
    }
}
