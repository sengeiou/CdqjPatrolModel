package com.cdqj.cdqjpatrolandroid.view.ui.plan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.base.BasePictureActivity;
import com.cdqj.cdqjpatrolandroid.base.BaseRecyclerView;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmDialogListener;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmSelectDialog;
import com.cdqj.cdqjpatrolandroid.config.GlobalConfig;
import com.cdqj.cdqjpatrolandroid.event.MsgEvent;
import com.cdqj.cdqjpatrolandroid.gis.bean.GisParamsBean;
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
import com.cdqj.cdqjpatrolandroid.image.bean.BasePhotoBean;
import com.cdqj.cdqjpatrolandroid.comstomview.view.ScrollLayout;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.utils.GsonUtils;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.adapter.TaskSuperviseReportAdapter;
import com.cdqj.cdqjpatrolandroid.adapter.PlanDetailTaskAdapter;
import com.cdqj.cdqjpatrolandroid.bean.BasicGridBean;
import com.cdqj.cdqjpatrolandroid.bean.PlanSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.bean.PatrolTaskResultBean;
import com.cdqj.cdqjpatrolandroid.bean.PlanListBean;
import com.cdqj.cdqjpatrolandroid.bean.TaskBean;
import com.cdqj.cdqjpatrolandroid.bean.UpImgResultBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.RetrofitUtils;
import com.cdqj.cdqjpatrolandroid.http.inter.IGetGisParameterListener;
import com.cdqj.cdqjpatrolandroid.presenter.PlanDetailPresenter;
import com.cdqj.cdqjpatrolandroid.utils.NavigationUtil;
import com.cdqj.cdqjpatrolandroid.utils.PatrolEnterPointActivity;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.view.i.IPlanDetailView;
import com.chad.library.adapter.base.BaseQuickAdapter;
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
import com.google.gson.JsonSyntaxException;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 计划详情界面
 *
 * @author lyf
 * 创建时间 2018年8月10日 18:30:33
 */
public class PlanDetailActivity extends BasePictureActivity<PlanDetailPresenter> implements MapTouchInterface, MapStatusInterface, IPlanDetailView {

    MapView mapMv;
    ScrollLayout scrollDownLayout;
    TextView planDetailArea;
    TextView planDetailReportCount;
    TextView planStatusDesc;
    TextView planDetailDistance;
    TextView orderDetailAddress;
    TextView planDetailLine;
    TextView planDetailPeople;
    TextView planDetailReportTime;
    TextView planDetailEndTime;
    TextView planDetailTime;
    TextView planDetailLineThere;
    BaseRecyclerView planDetailTask;
    TextView mapBottomBarMap;
    TextView mapBottomBarReport;
    TextView mapBottomBarNavigation;

    private PlanListBean mBean;

    /**
     * 巡检内容对象
     */
    private PatrolTaskResultBean resultBean;
    private TaskSuperviseReportAdapter rvAdapter;

    /**
     * 上报列表
     */
    private List<PlanSuperviseReportBean> beans = new ArrayList<>();

    /**
     * 上报内容缓存
     */
    @SuppressLint("UseSparseArrays")
    private HashMap<String, List<PlanSuperviseReportBean>> mapReports = new HashMap<>(8);

    /*****  上报弹窗表单，文件 ******/
    private DialogPlus mDialogPlus;
    private View mView;
    private TextView title;
    private EditText etRemark;
    private BaseRecyclerView rvPicture;
    private BaseRecyclerView rvAudio;
    private BaseRecyclerView rvVideo;
    private ArrayList<LocalMedia> selectPictureList = new ArrayList<>();
    private ArrayList<LocalMedia> selectAudioList = new ArrayList<>();
    private ArrayList<LocalMedia> selectVideoList = new ArrayList<>();
    private ArrayList<UpImgResultBean> pictureResult = new ArrayList<>();
    private ArrayList<UpImgResultBean> audioResult = new ArrayList<>();
    private ArrayList<UpImgResultBean> videoResult = new ArrayList<>();

    private Realm mRealm;
    private List<BasicGridBean> mBasicGridBeans;

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
     * 任务图层
     */
    private GraphicsOverlay areaOverlay;
    /**
     * 文字图层
     */
    private GraphicsOverlay textOverlay;

    /**
     * 第一次进入加载元素
     */
    private boolean isFirst = true;

    /**
     * 第一次测量弹窗高度
     */
    private boolean isFirstMeasure = true;

    /**
     * 1 我的计划
     * 2 计划制定
     * 3 计划审核
     * 4 计划台帐
     */
    private int flag;

    /**
     * 是否任务弹窗
     */
    private boolean isTaskWindow;

    /**
     * 导航点
     */
    private double navX = 30.572262, navY = 104.066513;

    /**
     * 底部抽屉
     */
    private ScrollLayout.OnScrollChangedListener mOnScrollChangedListener = new ScrollLayout.OnScrollChangedListener() {
        @Override
        public void onScrollProgressChanged(float currentProgress) {
            if (currentProgress >= 0) {
                float precent = 255 * currentProgress;
                if (precent > 255) {
                    precent = 255;
                } else if (precent < 0) {
                    precent = 0;
                }
                scrollDownLayout.getBackground().setAlpha(255 - (int) precent);
            }
        }

        @Override
        public void onScrollFinished(ScrollLayout.Status currentStatus) {
            if (currentStatus.equals(ScrollLayout.Status.CLOSED)) {
                // 如果是任务，滑动完成后展示巡检内容
                if (isTaskWindow && ObjectUtils.isNotEmpty(resultBean)) {
                    // 上报内容缓存，提高用户体验
                    if (mapReports.containsKey(resultBean.getId())
                            && ObjectUtils.isNotEmpty(mapReports.get(resultBean.getId()))
                            && mapReports.get(resultBean.getId()).size() > 0) {
                        return;
                    }
                    mPresenter.getTaskReports(resultBean.getId());
                }
            }
        }

        @Override
        public void onChildScroll(int top) {
        }
    };

    /**
     * 上报弹窗初始化
     */
    private void setReportWindow() {
        // 图片
//        selectPictureList.clear();
        // 视频
//        selectVideoList.clear();
        // 录音
//        selectAudioList.clear();
        initClick();
    }


    @SuppressLint("InflateParams")
    private void initViewOne() {
        mView = LayoutInflater.from(this).inflate(R.layout.cdqj_patrol_order_supervise_report_window, null);
        title = mView.findViewById(R.id.supervise_report_title);
        etRemark = mView.findViewById(R.id.supervise_report_remark);
        rvPicture = mView.findViewById(R.id.supervise_report_picture_gv);
        rvAudio = mView.findViewById(R.id.supervise_report_audio_gv);
        rvVideo = mView.findViewById(R.id.supervise_report_video_gv);

        assert titleToolbar != null;
        titleToolbar.setLeftTitleClickListener(v -> back());

        flag = getIntent().getIntExtra("flag", 0);
        mBean = getIntent().getParcelableExtra("bean");

        mRealm = Realm.getDefaultInstance();
        RealmResults<BasicGridBean> element = mRealm.where(BasicGridBean.class).findAll();
        if (!element.isEmpty()) {
            mBasicGridBeans = mRealm.copyFromRealm(element);
        }
    }

    private void initViewTo() {
        scrollDownLayout.setOnScrollChangedListener(mOnScrollChangedListener);
        scrollDownLayout.getBackground().setAlpha(0);

        if (ObjectUtils.isNotEmpty(mBean)) {
            assert titleToolbar != null;
            titleToolbar.setMainTitle(StringUtils.isTrimEmpty(mBean.getPlanName()) ? "计划详情" : mBean.getPlanName());
            initPlanData();
            //可查看日志
            titleToolbar.setRightTitleText("日志");
            titleToolbar.setRightTitleClickListener(v -> {
                // 操作日志
                PatrolEnterPointActivity.gotoPlanLogActivity(this, mBean.getId());
            });
        } else {
            ToastBuilder.showShortError("计划详情获取失败");
        }
    }

    /**
     * 界面初始化
     */
    @Override
    public void initView() {
        initViewOne();
        super.initView();
        setReportWindow();
        initMap();
        initViewTo();
    }

    @Override
    protected List<BasePhotoBean> setRvView() {
        List<BasePhotoBean> basePhotoBeans = new ArrayList<>();
        selectPictureList.clear();
        // 照片
        basePhotoBeans.add(new BasePhotoBean(rvPicture, selectPictureList, PictureMimeType.ofImage(), 9));
        // 视频
        selectVideoList.clear();
        basePhotoBeans.add(new BasePhotoBean(rvVideo, selectVideoList, PictureMimeType.ofVideo(), 9));
        // 录音
        selectAudioList.clear();
        basePhotoBeans.add(new BasePhotoBean(rvAudio, selectAudioList, PictureMimeType.ofAudio(), 9));
        return basePhotoBeans;
    }

    @Override
    protected PlanDetailPresenter createPresenter() {
        return new PlanDetailPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_plan_detail;
    }

    @Override
    protected void findView() {
        mapMv = findViewById(R.id.plan_map);
        scrollDownLayout = findViewById(R.id.scroll_down_layout);
        planDetailArea = findViewById(R.id.plan_detail_area);
        planDetailReportCount = findViewById(R.id.plan_detail_report_count);
        planStatusDesc = findViewById(R.id.plan_status_desc);
        planDetailDistance = findViewById(R.id.plan_detail_distance);
        orderDetailAddress = findViewById(R.id.order_detail_address);
        planDetailLine = findViewById(R.id.plan_detail_line);
        planDetailPeople = findViewById(R.id.plan_detail_people);
        planDetailReportTime = findViewById(R.id.plan_detail_report_time);
        planDetailEndTime = findViewById(R.id.plan_detail_end_time);
        planDetailTime = findViewById(R.id.plan_detail_time);
        planDetailLineThere = findViewById(R.id.plan_detail_line_there);
        planDetailTask = findViewById(R.id.plan_detail_task);
        mapBottomBarMap = findViewById(R.id.map_bottom_bar_map);
        mapBottomBarReport = findViewById(R.id.map_bottom_bar_report);
        mapBottomBarNavigation = findViewById(R.id.map_bottom_bar_navigation);
    }

    @Override
    protected String getTitleText() {
        return "计划详情";
    }

    /**
     * 计划菜单初始化
     */
    @SuppressLint({"InflateParams", "SetTextI18n"})
    private void initPlanData() {
        planDetailReportCount.setVisibility(View.INVISIBLE);
        isTaskWindow = false;
        /*
         * 状态（1待审核/2未开始/3进行中/4完成/5驳回/6作废）
         * 制定计划取消，在计划状态为待审核状态，制定人可以取消计划（取消 数据库作废数据）
         * 计划作废，未完成状态的计划都可以作废,作废内容（已完成任务不作废）:type 1、全部作废 2、作废未开始任务
         * 未审核及驳回时可以对任务进行修改
         * 进行中计划支持巡检任务上报（我的计划）
         */
        if (flag == 2) {
            // 计划制定
            if (mBean.getStatus() == 1) {
                mapBottomBarNavigation.setText("修改");
                mapBottomBarReport.setText("撤销");
                mapBottomBarReport.setVisibility(View.VISIBLE);
            } else if (mBean.getStatus() == 5) {
                mapBottomBarNavigation.setText("修改");
                mapBottomBarReport.setVisibility(View.INVISIBLE);
            } else {
                mapBottomBarReport.setVisibility(View.INVISIBLE);
                mapBottomBarNavigation.setVisibility(View.INVISIBLE);
            }
            if (mBean.getStatus() != 6 || mBean.getStatus() != 4) {
                mapBottomBarMap.setText("查看地图");
                // mapBottomBarMap.setText("作废");
            } else {
                mapBottomBarMap.setVisibility(View.INVISIBLE);
            }
        } else {
            mapBottomBarReport.setVisibility(View.INVISIBLE);
            mapBottomBarNavigation.setVisibility(View.INVISIBLE);
        }
        /*
         * 中压A管线 middlePipeA
         * 阀门 valveDevice
         * 调压设备  pressureDevice
         * 巡检点  checkPoint
         */
        List<TaskBean> taskBeans = new ArrayList<>();
        TaskBean bean;
        planDetailArea.setText(StringUtils.isTrimEmpty(mBean.getPlanName()) ?
                getString(R.string.null_text) : mBean.getPlanName());
        orderDetailAddress.setText(StringUtils.isTrimEmpty(mBean.getPlanTypeExp()) ?
                getString(R.string.null_text) : mBean.getPlanTypeExp());
        planDetailPeople.setText(StringUtils.isTrimEmpty(mBean.getExecuteUsersExp()) ?
                getString(R.string.null_text) : mBean.getExecuteUsersExp());
        planDetailDistance.setText(StringUtils.isTrimEmpty(mBean.getAddUserExp()) ?
                getString(R.string.null_text) : mBean.getAddUserExp());
        planStatusDesc.setText(StringUtils.isTrimEmpty(mBean.getStatusExp()) ?
                getString(R.string.null_text) : mBean.getStatusExp());
        planDetailEndTime.setText(StringUtils.isTrimEmpty(mBean.getUpdateTime()) ?
                getString(R.string.null_text) : mBean.getUpdateTime().split(" ")[0]);

        if (!StringUtils.isTrimEmpty(mBean.getBeginTime()) && !StringUtils.isTrimEmpty(mBean.getEndTime())) {
            planDetailTime.setText("任务周期: " + mBean.getBeginTime().split(" ")[0]
                    + " - " + mBean.getEndTime().split(" ")[0]);
            planDetailReportTime.setText(mBean.getBeginTime().split(" ")[0]);
        } else {
            planDetailTime.setText(R.string.null_text);
        }
        if (mBean.getPressureDevice() != 0) {
            bean = new TaskBean((int) (mBean.getCptpressureDevice() / mBean.getPressureDevice()), "调压设备");
            bean.setCount((int) mBean.getPressureDevice());
            bean.setCpt((int) mBean.getCptpressureDevice());
            bean.setCode("pressureDevice");
            taskBeans.add(bean);
        }
        if (mBean.getMiddlePipeA() != 0.0) {
            bean = new TaskBean((int) (mBean.getCptmiddlePipeA() / mBean.getMiddlePipeA()), "中压A管线");
            bean.setCountT(mBean.getMiddlePipeA());
            bean.setCptT(mBean.getCptmiddlePipeA());
            bean.setCode("middlePipeA");
            taskBeans.add(bean);
        }
        if (mBean.getValveDevice() != 0) {
            bean = new TaskBean((int) (mBean.getCptvalveDevice() / mBean.getValveDevice()), "阀门");
            bean.setCount((int) mBean.getValveDevice());
            bean.setCpt((int) mBean.getCptvalveDevice());
            bean.setCode("valveDevice");
            taskBeans.add(bean);
        }
        if (mBean.getCheckPoint() != 0) {
            bean = new TaskBean((int) (mBean.getCptcheckPoint() / mBean.getCheckPoint()), "巡检点");
            bean.setCount((int) mBean.getCheckPoint());
            bean.setCpt((int) mBean.getCptcheckPoint());
            bean.setCode("checkPoint");
            taskBeans.add(bean);
        }
        planDetailTask.setLayoutManager(new LinearLayoutManager(this));
        PlanDetailTaskAdapter adapter = new PlanDetailTaskAdapter(R.layout.cdqj_patrol_plan_detail_task_item, taskBeans, flag == 1 || flag == 4);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        View view = getLayoutInflater().inflate(R.layout.cdqj_patrol_list_empty_view, null);
        view.findViewById(R.id.empty_refresh).setVisibility(View.GONE);
        adapter.setEmptyView(view);
        adapter.bindToRecyclerView(planDetailTask);
    }

    /**
     * 任务菜单初始化
     *
     * @param taskBean 地图图层点击后的任务属性
     */
    @SuppressLint({"InflateParams", "SetTextI18n"})
    private void initTaskData(PatrolTaskResultBean taskBean) {
        isTaskWindow = true;
        mapBottomBarReport.setText("上报");
        mapBottomBarNavigation.setText("导航");
        planDetailReportCount.setVisibility(View.VISIBLE);
        /*
         * 状态（1待审核/2未开始/3进行中/4完成/5驳回/6作废）
         * 制定计划取消，在计划状态为待审核状态，制定人可以取消计划（取消 数据库作废数据）
         * 计划作废，未完成状态的计划都可以作废,作废内容（已完成任务不作废）:type 1、全部作废 2、作废未开始任务
         * 未审核及驳回时可以对任务进行修改
         * 进行中计划支持巡检任务上报（我的计划）
         */
        if (mBean.getStatus() == 3) {
            // 进行中，可上报
            if (flag != 1) {
                mapBottomBarReport.setVisibility(View.INVISIBLE);
            } else {
                mapBottomBarReport.setVisibility(View.VISIBLE);
            }
        } else if (mBean.getStatus() == 5) {
            mapBottomBarMap.setText("查看地图");
            mapBottomBarReport.setVisibility(View.INVISIBLE);
        } else {
            mapBottomBarReport.setVisibility(View.INVISIBLE);
        }
        mapBottomBarNavigation.setVisibility(View.VISIBLE);
        planDetailArea.setText(StringUtils.isTrimEmpty(taskBean.getCheckName()) ?
                getString(R.string.null_text) : taskBean.getCheckName());
        orderDetailAddress.setText(StringUtils.isTrimEmpty(taskBean.getAddress()) ?
                getString(R.string.null_text) : taskBean.getAddress());
        planDetailPeople.setText(StringUtils.isTrimEmpty(taskBean.getAddUserExp()) ?
                getString(R.string.null_text) : taskBean.getAddUserExp());
        planDetailDistance.setText(StringUtils.isTrimEmpty(taskBean.getAddUserExp()) ?
                getString(R.string.null_text) : taskBean.getAddUserExp());
        planStatusDesc.setText(StringUtils.isTrimEmpty(taskBean.getPassStatusExp()) ?
                getString(R.string.null_text) : taskBean.getPassStatusExp());
        planDetailReportCount.setText(ObjectUtils.isEmpty(taskBean.getPassTimes()) ?
                getString(R.string.null_text) : "上报次数：" + taskBean.getPassTimes());
        planDetailEndTime.setText(StringUtils.isTrimEmpty(taskBean.getUpdateTime()) ?
                getString(R.string.null_text) : taskBean.getUpdateTime().split(" ")[0]);
        planDetailTime.setText(StringUtils.isTrimEmpty(taskBean.getRemarks()) ?
                getString(R.string.null_text) : taskBean.getRemarks());

        planDetailTask.setLayoutManager(new LinearLayoutManager(this));
        rvAdapter = new TaskSuperviseReportAdapter(R.layout.cdqj_patrol_order_supervise_report_item,
                mapReports.containsKey(taskBean.getId()) ? mapReports.get(taskBean.getId()) : beans, this, taskBean);
        rvAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        View view = getLayoutInflater().inflate(R.layout.cdqj_patrol_list_empty_view, null);
        view.findViewById(R.id.empty_refresh).setVisibility(View.GONE);
        rvAdapter.setEmptyView(view);
        rvAdapter.bindToRecyclerView(planDetailTask);
    }

    @Override
    public void initListener() {
        findViewById(R.id.plan_map_gps).setOnClickListener(this::setClick);
        findViewById(R.id.plan_map_full_extent).setOnClickListener(this::setClick);
        findViewById(R.id.plan_info_map_view).setOnClickListener(this::setClick);
        findViewById(R.id.map_bottom_bar_map).setOnClickListener(this::setClick);
        findViewById(R.id.map_bottom_bar_report).setOnClickListener(this::setClick);
        findViewById(R.id.map_bottom_bar_navigation).setOnClickListener(this::setClick);
    }

    public void setClick(View view) {
        int i = view.getId();
        if (i == R.id.plan_map_gps) {// gps定位
            MapToolUtil.getLocation(mapMv);
        } else if (i == R.id.plan_info_map_view) {
            if (scrollDownLayout.getCurrentStatus() == ScrollLayout.Status.OPENED) {
                scrollDownLayout.setToClosed();
            } else {
                scrollDownLayout.setToOpen();
            }
        } else if (i == R.id.plan_map_full_extent) {// 全图
            MapToolUtil.mapFullExtent(mapMv, viewPoint);
        } else if (i == R.id.map_bottom_bar_report) {
            if (!GlobalConfig.isDoWork) {
                RetrofitUtils.doOnWork(this, 1);
                return;
            }
            if (isTaskWindow) {
                // 我的计划 上报
                showReportWindow();
            } else {
                // 计划制定界面 撤销
                new ConfirmSelectDialog(this)
                        .setContentStr("是否确定执行计划撤销")
                        .setYesStr("确定")
                        .setNoStr("取消")
                        .setListener(new ConfirmDialogListener() {
                            @Override
                            public void onYesClick() {
                                mPresenter.subPlanRevoke(mBean.getId());
                            }

                            @Override
                            public void onNoClick() {

                            }
                        }).initView().show();
            }
        } else if (i == R.id.map_bottom_bar_map) {// 计划制定界面 作废 flag == 2
            if (flag == 1001) {
                if (!GlobalConfig.isDoWork) {
                    RetrofitUtils.doOnWork(this, 1);
                    return;
                }
                new ConfirmSelectDialog(this)
                        .setContentStr("是否确定执行计划作废")
                        .setYesStr("确定")
                        .setNoStr("取消")
                        .setListener(new ConfirmDialogListener() {
                            @Override
                            public void onYesClick() {
                                mPresenter.subPlanDel(mBean.getId());
                            }

                            @Override
                            public void onNoClick() {

                            }
                        }).initView().show();
            } else {
                scrollDownLayout.setToExit();
                scrollDownLayout.getBackground().setAlpha(0);
            }
        } else if (i == R.id.map_bottom_bar_navigation) {
            /*
             * 状态（1待审核/2未开始/3进行中/4完成/5驳回/6作废）
             * 制定计划取消，在计划状态为待审核状态，制定人可以取消计划（取消 数据库作废数据）
             * 计划作废，未完成状态的计划都可以作废,作废内容（已完成任务不作废）:type 1、全部作废 2、作废未开始任务
             * 未审核及驳回时可以对任务进行修改
             */
            if (isTaskWindow) {
                // 导航
                NavigationUtil.showNavWindow(navX, navY, this);
            } else {
                if (!GlobalConfig.isDoWork) {
                    RetrofitUtils.doOnWork(this, 1);
                    return;
                }
                // 计划制定界面 修改
                new ConfirmSelectDialog(this)
                        .setContentStr("是否确定执行计划修改")
                        .setYesStr("确定")
                        .setNoStr("取消")
                        .setListener(new ConfirmDialogListener() {
                            @Override
                            public void onYesClick() {
                                PatrolEnterPointActivity.gotoPlanModifyActivity(PlanDetailActivity.this, mBean);
                            }

                            @Override
                            public void onNoClick() {

                            }
                        }).initView().show();
            }
        }
    }

    /**
     * 显示上报弹窗
     */
    private void showReportWindow() {
        title.setText("巡检上报");
        if (ObjectUtils.isEmpty(mDialogPlus)) {
            mDialogPlus = DialogPlus.newDialog(this)
                    // Select different holder.ListHolder|ViewHolder(自定义布局)|GridHolder
                    .setContentHolder(new ViewHolder(mView))
                    .setContentHeight(ScreenUtils.getScreenHeight() / 3 * 2)
                    .setMargin(ConvertUtils.dp2px(10), 0, ConvertUtils.dp2px(10), 0)
                    .setPadding(ConvertUtils.dp2px(10), ConvertUtils.dp2px(10),
                            ConvertUtils.dp2px(10), ConvertUtils.dp2px(10))
                    // Set dialog position. BOTTOM (default), TOP or CENTER. You can also combine other Gravity options.
                    // 设置对话框位置。底部（默认），顶部或中心。你也可以结合其他重力选项。
                    .setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL)
                    .setContentBackgroundResource(R.drawable.stroke_bg_radius_top)
                    // Define if the dialog is cancelable and should be closed when back pressed or out of dialog is clicked
                    // 定义对话框是否是可取消的，当被按下或关闭对话框时，应该关闭对话框
                    .setCancelable(true)
                    // Set Adapter, this adapter will be used to fill the content for ListHolder and GridHolder.
                    // This is required if the content holder is ListHolder or GridHolder.
                    // It is not required if the content holder is ViewHolder.
                    // 设置适配器，这个适配器将被用来填充ListHolder和GridHolder的内容。
                    // 如果内容持有者是ListHolder或GridHolder，则这是必需的。如果内容持有者是ViewHolder，则不需要。
                    // .setAdapter(adapter)
                    // Set animation resources
                    .setOnClickListener((dialog, view) -> {
                        int i1 = view.getId();
                        if (i1 == R.id.supervise_report_submit) {
                            if (selectPictureList.isEmpty() && selectAudioList.isEmpty() && selectVideoList.isEmpty()) {
                                ToastBuilder.showShortWarning("请至少添加一种文件");
                                return;
                            }
                            showProgress("文件上传中...");
                            pictureResult.clear();
                            audioResult.clear();
                            videoResult.clear();
                            StringBuilder pics = new StringBuilder();
                            StringBuilder audios = new StringBuilder();
                            StringBuilder videos = new StringBuilder();
                            for (int i = 0; i < selectPictureList.size(); i++) {
                                if (selectPictureList.get(i).isCompressed()) {
                                    pics.append(selectPictureList.get(i).getCompressPath());
                                    if (i != selectPictureList.size() - 1) {
                                        pics.append(",");
                                    }
                                } else {
                                    pics.append(selectPictureList.get(i).getPath());
                                    if (i != selectPictureList.size() - 1) {
                                        pics.append(",");
                                    }
                                }
                            }
                            if (!StringUtils.isTrimEmpty(pics.toString())) {
                                mPresenter.upLoadFile(pics.toString(), Constant.IMG_TYPE);
                            }
                            for (int i = 0; i < selectAudioList.size(); i++) {
                                audios.append(selectAudioList.get(i).getPath());
                                if (i != selectAudioList.size() - 1) {
                                    audios.append(",");
                                }
                            }
                            if (!StringUtils.isTrimEmpty(audios.toString())) {
                                mPresenter.upLoadFile(audios.toString(), Constant.AUDIO_TYPE);
                            }
                            for (int i = 0; i < selectVideoList.size(); i++) {
                                videos.append(selectVideoList.get(i).getPath());
                                if (i != selectVideoList.size() - 1) {
                                    videos.append(",");
                                }
                            }
                            if (!StringUtils.isTrimEmpty(videos.toString())) {
                                mPresenter.upLoadFile(videos.toString(), Constant.VIDEO_TYPE);
                            }

                        } else if (i1 == R.id.supervise_report_ecs) {
                            if (ObjectUtils.isNotEmpty(dialog)) {
                                if (dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                            }
                        }
                    })
                    .setInAnimation(R.anim.slide_in_bottom)
                    .setOutAnimation(R.anim.slide_out_bottom)
                    .create();
        }
        if (mDialogPlus.isShowing()) {
            mDialogPlus.dismiss();
        }
        mDialogPlus.show();
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

                            mapStatusListener = new MapStatusListener(mapMv, PlanDetailActivity.this);
                            mapMv.addLayerViewStateChangedListener(mapStatusListener);

                            initLayers(mGisParamsBean);
                        } else {
                            new ConfirmSelectDialog(PlanDetailActivity.this)
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

    private void setLocation() {
        if (!StringUtils.isTrimEmpty(mBean.getGridIds())) {
            for (int i = 0; i < mBasicGridBeans.size(); i++) {
                if (!StringUtils.isTrimEmpty(mBean.getGridIds().split(",")[0])
                        && mBean.getGridIds().split(",")[0].equals(String.valueOf(mBasicGridBeans.get(i).getId()))) {
                    String grids = mBasicGridBeans.get(i).getGridAreas();
                    if (!StringUtils.isTrimEmpty(grids)) {
                        MapToolUtil.getLocation(mapMv, new Point(GpsUtils.getCenterPointFromListOfCoordinates(grids).getX(),
                                GpsUtils.getCenterPointFromListOfCoordinates(grids).getY(), reference), 10000D);

                    }
                    break;
                }
            }
        }
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
            list.add(QueryLayerType.QueryAllGpraphicsOverlay);
            list.add(QueryLayerType.QueryOnlineOne);
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
        // 设置放大至指定比例尺不显示
        areaOverlay.setMinScale(MapConstant.MIN_SCALE);
        textOverlay = MapUtil.addSingelLayer(mapMv, textOverlay);
        textOverlay.setMinScale(MapConstant.MIN_SCALE);
        tempGraphicsLayer = MapUtil.addSingelLayer(mapMv, tempGraphicsLayer);
        tempGraphicsLayer.setMinScale(MapConstant.MIN_SCALE);
        //MapToolUtil.getLocation(mapMv);
        if (isFirst) {
            // 地图初始化成功后加载数据(地图元素等)
            // 104.160391007525,30.6720841432089
            isFirst = false;
            setLocation();
            if (ObjectUtils.isNotEmpty(mBean)) {
                // 如果是任务或者台帐
                mPresenter.getCheckTypes((flag == 1 || flag == 4) ? mBean.getTaskId() : mBean.getId(), flag);
            }
        }
        initMapListener(mGisParamsBean);
    }

    @Override
    public void getLayerErrorSuccess(String layerName) {
        ToastBuilder.showShortError(layerName + "图层加载失败...");
    }

    @Override
    public void getMapClickPtSuccess(Point pt, int flag) {
        // 清除地图图标 点击空白处 还原计划
        MapUtil.addSingelLayer(mapMv, tempGraphicsLayer).getGraphics().clear();
        initPlanData();
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
        if (graphicList == null || graphicList.size() == 0) {
            return;
        }
        // 清除地图图标
        MapUtil.addSingelLayer(mapMv, tempGraphicsLayer).getGraphics().clear();
        Geometry geo = graphicList.iterator().next().getGeometry();
        Point point = new Point(geo.getExtent().getCenter().getX(), geo.getExtent().getCenter().getY());
        navX = point.getY();
        navY = point.getX();
        if (geo.getGeometryType() != GeometryType.POINT) {
            // 不为点
            MapUtil.addSingelLayer(mapMv, tempGraphicsLayer).getGraphics().add(SymbolsMgr.highlightGraphic(geo));
        } else {
            MapUtil.addPointLayer(mapMv, tempGraphicsLayer, this, R.mipmap.icon_gps_point, point, false);
        }
        Map<String, Object> map = graphicList.iterator().next().getAttributes();
        if (ObjectUtils.isNotEmpty(map.get("json"))) {
            try {
                resultBean = GsonUtils.gsonBuilder.create().fromJson(map.get("json").toString(), PatrolTaskResultBean.class);
                initTaskData(resultBean);
            } catch (JsonSyntaxException exception) {
                resultBean = null;
                exception.printStackTrace();
                MapUtil.addSingelLayer(mapMv, tempGraphicsLayer).getGraphics().clear();
                ToastBuilder.showShortError("任务属性解析失败");
            }
        } else {
            resultBean = null;
            MapUtil.addSingelLayer(mapMv, tempGraphicsLayer).getGraphics().clear();
            ToastBuilder.showShortError("任务属性获取失败");
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

    private Handler scaleHandler = new Handler();
    private Runnable scaleRunnable = new Runnable() {
        @Override
        public void run() {
            // 如果有导航栏+导航栏高度
            int height = planDetailLine.getBottom() + (BarUtils.isNavBarVisible(getWindow()) ? BarUtils.getNavBarHeight() : 0);
            scrollDownLayout.setExitOffset(height);
            scrollDownLayout.setMaxOffset(planDetailLineThere.getBottom() + (BarUtils.isNavBarVisible(getWindow()) ? BarUtils.getNavBarHeight() : 0));
            scrollDownLayout.setToExit();
            scrollDownLayout.getBackground().setAlpha(0);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        mapMv.resume();
        if (isFirstMeasure) {
            isFirstMeasure = false;
            // 获取详情view显示的高度
            getWindow().getDecorView().post(() -> scaleHandler.post(scaleRunnable));
        }
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
        if (ObjectUtils.isNotEmpty(mRealm)) {
            mRealm.close();
        }
        mPresenter.onUnsubscribe();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        if (scrollDownLayout.getCurrentStatus() == ScrollLayout.Status.EXIT) {
            finish();
        } else {
            scrollDownLayout.setToExit();
            scrollDownLayout.getBackground().setAlpha(0);
        }
    }

    @Override
    public void onSubPlanRevokeSuccess(BasePostResponse<Object> body) {
        if (body.isSuccess()) {
            // 撤销成功,发送广播让其他界面更新ui
            ToastBuilder.showShort(body.getMsg());
            EventBus.getDefault().post(new MsgEvent<>(2, true));
            finish();
        } else {
            ToastBuilder.showShortError(body.getMsg());
        }
    }

    @Override
    public void onUpdateCheckDataSuccess(BasePostResponse<Object> body) {
        if (body.isSuccess()) {
            // 巡检点上传成功,发送广播让其他界面更新ui 本界面刷新重绘界面
            ToastBuilder.showShort(body.getMsg());
            EventBus.getDefault().post(new MsgEvent<>(2, true));
            mPresenter.getCheckTypes((flag == 1 || flag == 4) ? mBean.getTaskId() : mBean.getId(), flag);
            if (ObjectUtils.isNotEmpty(mDialogPlus)) {
                mDialogPlus.dismiss();
            }
        } else {
            ToastBuilder.showShortWarning(body.getMsg());
        }
    }

    @Override
    public void onSubPlanDelSuccess(BasePostResponse<Object> body) {
        if (body.isSuccess()) {
            // 作废成功,发送广播让其他界面更新ui
            ToastBuilder.showShort(body.getMsg());
            EventBus.getDefault().post(new MsgEvent<>(2, true));
            finish();
        } else {
            ToastBuilder.showShortWarning(body.getMsg());
        }
    }

    @Override
    public void onGetCheckTypesSuccess(BaseGetResponse<PatrolTaskResultBean> bean) {
        scrollDownLayout.setToExit();
        MapUtil.addSingelLayer(mapMv, areaOverlay).getGraphics().clear();
        MapUtil.addSingelLayer(mapMv, textOverlay).getGraphics().clear();
        List<PatrolTaskResultBean> body = bean.getRows();
        if (body.size() > 0) {
            // 启动线程来执行加载地图元素
            showProgress("地址元素加载中...");
            new Thread(() -> {
                for (int i = 0; i < body.size(); i++) {
                    // 对象类型（点/线/面）
                    if (body.get(i).getObjectType() == 1) {
                        if (!StringUtils.isTrimEmpty(body.get(i).getGisArea())) {
                            // passStatus 1、完成 0、未完成
                            Point point = new Point(Double.valueOf(body.get(i).getGisArea().trim().split(",")[0]),
                                    Double.valueOf(body.get(i).getGisArea().trim().split(",")[1]), reference);
                            MapUtil.addPointLayer(mapMv, areaOverlay, PlanDetailActivity.this, body.get(i).getPassStatus() == 0 ?
                                    R.mipmap.icon_point_end : R.mipmap.icon_point_unexecuted, point, MapUtil.jsonStr2MapInMap(body.get(i)), false, false);
                        }
                    } else if (body.get(i).getObjectType() == 2) {
                        if (!StringUtils.isTrimEmpty(body.get(i).getGisArea())) {
                            String[] points = body.get(i).getGisArea().trim().split(" ");
                            Point[] pointsLine = new Point[points.length];
                            for (int k = 0; k < points.length; k++) {
                                pointsLine[k] = new Point(Double.valueOf(points[k].split(",")[0]),
                                        Double.valueOf(points[k].split(",")[1]), reference);
                            }
                            MapUtil.addLineLayer(mapMv, areaOverlay, body.get(i).getPassStatus() == 0 ? "#A3A3A3" : "#666AD1", pointsLine, MapUtil.jsonStr2MapInMap(body.get(i)), false);
                        }
                    } else if (body.get(i).getObjectType() == 3) {
                        List<PatrolTaskResultBean> list = new ArrayList<>();
                        list.add(body.get(i));
                        List<Map<String, Object>> mapList = new ArrayList<>();
                        mapList.add(MapUtil.jsonStr2MapInMap(body.get(i)));
                        MapUtil.addPolygons(PlanDetailActivity.this, textOverlay, areaOverlay, reference, list, mapList, body.get(i).getPassStatus() == 0 ? "#A3A3A3" : "#666AD1", false);
                    }
                }
                EventBus.getDefault().post(new MsgEvent<>(4));
            }).start();
        } else {
            ToastBuilder.showShortWarning("暂无任务内容数据");
        }
    }

    /**
     * 文件上传成功结果
     *
     * @param body body
     */
    @Override
    public void onUpdateFileSuccess(BasePostResponse<List<UpImgResultBean>> body, int type) {
        // 文件上传成功
        if (body.isSuccess()) {
            if (type == Constant.IMG_TYPE) {
                pictureResult.addAll(body.getObj());
            } else if (type == Constant.AUDIO_TYPE) {
                audioResult.addAll(body.getObj());
            } else if (type == Constant.VIDEO_TYPE) {
                videoResult.addAll(body.getObj());
            }
            if (pictureResult.size() == selectPictureList.size() && audioResult.size() == selectAudioList.size()
                    && videoResult.size() == selectVideoList.size()) {
                showProgress("数据上传中...");
                StringBuilder reportPicture = new StringBuilder();
                StringBuilder reportVoice = new StringBuilder();
                StringBuilder reportVideo = new StringBuilder();
                for (UpImgResultBean bean : pictureResult) {
                    reportPicture.append(bean.getFilePath());
                    reportPicture.append(",");
                }
                for (UpImgResultBean bean : audioResult) {
                    reportVoice.append(bean.getFilePath());
                    reportVoice.append(",");
                }
                for (UpImgResultBean bean : videoResult) {
                    reportVideo.append(bean.getFilePath());
                    reportVideo.append(",");
                }
                HashMap<String, String> map = new HashMap<>(7);
                map.put("taskResultId", resultBean.getId() + "");
                map.put("reportLat", PreferencesUtil.getString(Constant.LOCATION_LATITUDE));
                map.put("reportLon", PreferencesUtil.getString(Constant.LOCATION_LONGITUDE));
                map.put("reportDescript", etRemark.getText().toString());
                if (reportPicture.length() > 0) {
                    map.put("reportPicture", reportPicture.substring(0, reportPicture.length() - 1));
                }
                if (reportVoice.length() > 0) {
                    map.put("reportVoice", reportVoice.substring(0, reportVoice.length() - 1));
                }
                if (reportVideo.length() > 0) {
                    map.put("reportVideo", reportVideo.substring(0, reportVideo.length() - 1));
                }
                mPresenter.addUpdateCheckData(map);
            }
        } else {
            if (type == Constant.IMG_TYPE) {
                StringBuilder pics = new StringBuilder();
                for (int i = 0; i < selectPictureList.size(); i++) {
                    if (selectPictureList.get(i).isCompressed()) {
                        pics.append(selectPictureList.get(i).getCompressPath());
                        if (i != selectPictureList.size() - 1) {
                            pics.append(",");
                        }
                    } else {
                        pics.append(selectPictureList.get(i).getPath());
                        if (i != selectPictureList.size() - 1) {
                            pics.append(",");
                        }
                    }
                }
                if (!StringUtils.isTrimEmpty(pics.toString())) {
                    mPresenter.upLoadFile(pics.toString(), Constant.IMG_TYPE);
                }
            } else if (type == Constant.AUDIO_TYPE) {
                StringBuilder audios = new StringBuilder();
                for (int i = 0; i < selectAudioList.size(); i++) {
                    audios.append(selectAudioList.get(i).getPath());
                    if (i != selectAudioList.size() - 1) {
                        audios.append(",");
                    }
                }
                if (!StringUtils.isTrimEmpty(audios.toString())) {
                    mPresenter.upLoadFile(audios.toString(), Constant.AUDIO_TYPE);
                }
            } else if (type == Constant.VIDEO_TYPE) {
                StringBuilder videos = new StringBuilder();
                for (int i = 0; i < selectVideoList.size(); i++) {
                    videos.append(selectVideoList.get(i).getPath());
                    if (i != selectVideoList.size() - 1) {
                        videos.append(",");
                    }
                }
                if (!StringUtils.isTrimEmpty(videos.toString())) {
                    mPresenter.upLoadFile(videos.toString(), Constant.VIDEO_TYPE);
                }
            }
        }
    }

    @Override
    public void onGetTaskReportsSuccess(BaseGetResponse<PlanSuperviseReportBean> body) {
        // 上报内容获取成功
        rvAdapter.setNewData(body.getRows());
        mapReports.put(resultBean.getId(), body.getRows());
        scrollDownLayout.getBackground().setAlpha(0);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataChange(MsgEvent msgEvent) {
        if (msgEvent.getTag() == 2 && msgEvent.isRefresh()) {
            initViewTo();
        } else if (msgEvent.getTag() == 4) {
            // 异步加载地图元素完成
            hideProgress();
        }
    }
}
