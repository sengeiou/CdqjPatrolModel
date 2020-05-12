package com.cdqj.cdqjpatrolandroid.view.ui.main;

import android.Manifest;
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.base.BasePictureFragment;
import com.cdqj.cdqjpatrolandroid.base.BaseRecyclerView;
import com.cdqj.cdqjpatrolandroid.bean.TabEntity;
import com.cdqj.cdqjpatrolandroid.comstomview.CustomGridView;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmDialogListener;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmSelectDialog;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.CustomGridPicWindowBean;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.MapPointDialog;
import com.cdqj.cdqjpatrolandroid.comstomview.recyclerview.SpacesItemDecoration;
import com.cdqj.cdqjpatrolandroid.config.CdqjInitDataConfig;
import com.cdqj.cdqjpatrolandroid.config.GlobalConfig;
import com.cdqj.cdqjpatrolandroid.event.EventGpsBean;
import com.cdqj.cdqjpatrolandroid.event.MsgEvent;
import com.cdqj.cdqjpatrolandroid.gis.bean.GisOtherCoordinateBean;
import com.cdqj.cdqjpatrolandroid.gis.bean.GisParamsBean;
import com.cdqj.cdqjpatrolandroid.gis.bean.GisPointCoordinateBean;
import com.cdqj.cdqjpatrolandroid.gis.bean.GpsBean;
import com.cdqj.cdqjpatrolandroid.gis.event.MapStatusInterface;
import com.cdqj.cdqjpatrolandroid.gis.event.MapStatusListener;
import com.cdqj.cdqjpatrolandroid.gis.event.MapTouchInterface;
import com.cdqj.cdqjpatrolandroid.gis.event.MapTouchListener;
import com.cdqj.cdqjpatrolandroid.gis.event.QueryLayerType;
import com.cdqj.cdqjpatrolandroid.gis.event.QueryParams;
import com.cdqj.cdqjpatrolandroid.gis.symbols.SymbolsMgr;
import com.cdqj.cdqjpatrolandroid.gis.tianditu.MBTilesLayer;
import com.cdqj.cdqjpatrolandroid.gis.tianditu.MyFilter;
import com.cdqj.cdqjpatrolandroid.gis.util.GpsUtils;
import com.cdqj.cdqjpatrolandroid.gis.util.MapConstant;
import com.cdqj.cdqjpatrolandroid.gis.util.MapToolUtil;
import com.cdqj.cdqjpatrolandroid.gis.util.MapUtil;
import com.cdqj.cdqjpatrolandroid.gis.util.MeasureTool;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.image.bean.BasePhotoBean;
import com.cdqj.cdqjpatrolandroid.image.glide.GlideImgManager;
import com.cdqj.cdqjpatrolandroid.utils.AnimUtils;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.utils.ConvertorDataUtils;
import com.cdqj.cdqjpatrolandroid.utils.GsonUtils;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.adapter.GridIconAndTextAdapter;
import com.cdqj.cdqjpatrolandroid.adapter.MapSearchListAdapter;
import com.cdqj.cdqjpatrolandroid.adapter.MapSlidingGridAdapter;
import com.cdqj.cdqjpatrolandroid.adapter.MapSlidingRvAdapter;
import com.cdqj.cdqjpatrolandroid.bean.AreaTestBean;
import com.cdqj.cdqjpatrolandroid.bean.CarBean;
import com.cdqj.cdqjpatrolandroid.bean.DeviceBean;
import com.cdqj.cdqjpatrolandroid.bean.DicCacheDao;
import com.cdqj.cdqjpatrolandroid.bean.GridSelectBean;
import com.cdqj.cdqjpatrolandroid.bean.HdOrderBean;
import com.cdqj.cdqjpatrolandroid.bean.LoginResultBean;
import com.cdqj.cdqjpatrolandroid.bean.MapGridBean;
import com.cdqj.cdqjpatrolandroid.bean.MapSearchBean;
import com.cdqj.cdqjpatrolandroid.bean.PatrolHdType;
import com.cdqj.cdqjpatrolandroid.bean.PatrolTaskResultBean;
import com.cdqj.cdqjpatrolandroid.bean.ResultMapSearchBean;
import com.cdqj.cdqjpatrolandroid.bean.SearchRoundBean;
import com.cdqj.cdqjpatrolandroid.bean.SiteBean;
import com.cdqj.cdqjpatrolandroid.bean.UpImgResultBean;
import com.cdqj.cdqjpatrolandroid.bean.UserLayerBean;
import com.cdqj.cdqjpatrolandroid.comstomview.dialog.ConfirmListDialog;
import com.cdqj.cdqjpatrolandroid.comstomview.dialog.ConfirmListDialogListener;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.OnUpdateFileBackListener;
import com.cdqj.cdqjpatrolandroid.http.RetrofitUtils;
import com.cdqj.cdqjpatrolandroid.http.inter.IGetGisParameterListener;
import com.cdqj.cdqjpatrolandroid.presenter.MapFragmentPresenter;
import com.cdqj.cdqjpatrolandroid.utils.DataUtils;
import com.cdqj.cdqjpatrolandroid.utils.NavigationUtil;
import com.cdqj.cdqjpatrolandroid.utils.PatrolEnterPointActivity;
import com.cdqj.cdqjpatrolandroid.utils.PictureProcessingUtil;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;
import com.cdqj.cdqjpatrolandroid.utils.StringUrlUtil;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.utils.sql.SqlDataPresenter;
import com.cdqj.cdqjpatrolandroid.view.i.IMapFragmentView;
import com.cdqj.cdqjpatrolandroid.view.ui.map.MapUpdateActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.GeometryType;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.layers.Layer;
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
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.GridHolder;
import com.orhanobut.dialogplus.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;

import static android.app.Activity.RESULT_OK;
import static com.cdqj.cdqjpatrolandroid.gis.util.MapUtil.getViewpointFromParam;
import static java.util.regex.Pattern.compile;

/**
 * Created by lyf on 2018/7/26 09:40
 *
 * @author lyf
 * desc：巡线地图主界面
 */
public class MapFragment extends BasePictureFragment<MapFragmentPresenter> implements IMapFragmentView, MapTouchInterface, MapStatusInterface, OnUpdateFileBackListener,
        RetrofitUtils.SearchRoundOnListener {

    CommonTabLayout mapTab;
    CustomGridView mapMapSlidingRightTypeGv;
    BaseRecyclerView mapMapSlidingRightAreaGv;
    BaseRecyclerView mapSlidingRightObjGv;
    RelativeLayout mapSlidingRightArea;
    RelativeLayout mapSlidingRightObj;
    ConstraintLayout mapLineDialogView;
    ImageView mapSignal;
    TextView mapSearchText;
    EditText mapSearchDetailText;
    ImageView mapSearchDetailScan;
    TextView mapSearchDetailLine;
    TextView mapSearchDetailSearch;
    ConstraintLayout mapSearchDetailLayout;
    BaseRecyclerView mapSearchDetailType;
    BaseRecyclerView mapSearchDetailList;
    ConstraintLayout mapSearchDetail;
    ConstraintLayout mapSearchDetailContent;
    TextView mapLineDialogDetail;
    TextView mapLineDialogReport;
    TextView mapLineDialogNavigation;
    ConstraintLayout mapLineInfo;
    MapView mapMv;
    TextView mapShowGps;
    TextView mapLineDialogTitle;
    TextView mapLineDialogDistance;
    TextView mapLineDialogAddress;
    TextView mapHdDialogTitle;
    TextView mapHdDialogDistance;
    TextView mapHdDialogAddress;
    TextView mapHdDialogLevel;
    TextView mapHdDialogUpdate;
    ImageView mapHdDialogImg;
    TextView updateHdDx;
    TextView updateHdType;
    TextView updateHdLevel;
    TextView updateHdXzqq;
    LinearLayout updateHdDtLl;
    TextView updateHdLxTitle;
    TextView updateHdLx;
    TextView updateHdFsfsTitle;
    TextView updateHdFsfs;
    TextView updateHdSslbTitle;
    TextView updateHdSslb;
    TextView updateHdGcTitle;
    TextView updateHdGc;
    TextView updateHdGjTitle;
    TextView updateHdGj;
    TextView updateHdFfxzTitle;
    TextView updateHdFfxz;
    TextView updateHdLqsbTitle;
    TextView updateHdLqsb;
    TextView updateHdYhlxTitle;
    TextView updateHdYhlx;
    LinearLayout updateHdLzLl;
    TextView updateHdAddressTitle;
    EditText updateHdAddress;
    TextView updateHdGpsTitle;
    EditText updateHdGps;
    TextView updateHdRemarkTitle;
    EditText updateHdRemark;
    ScrollView mapUpdateHdWindow;
    ImageView updateSiteEcs;
    TextView updateSiteTitle;
    TextView updateSiteSubmit;
    TextView updateSiteNameTitle;
    EditText updateSiteName;
    TextView updateSiteTypeTitle;
    TextView updateSiteType;
    TextView updateSiteXzqyTitle;
    TextView updateSiteXzqy;
    TextView updateSiteSspqTitle;
    TextView updateSiteSspq;
    LinearLayout updateSiteDtLl;
    TextView updateSiteAddressTitle;
    EditText updateSiteAddress;
    TextView updateSiteGpsTitle;
    EditText updateSiteGps;
    TextView updateSiteRemarkTitle;
    EditText updateSiteRemark;
    ScrollView mapUpdateSiteWindow;
    ImageView updateDevEcs;
    TextView updateDevTitle;
    TextView updateDevSubmit;
    TextView updateDevTypeTitle;
    EditText updateDevType;
    TextView updateDevAddressTitle;
    EditText updateDevAddress;
    TextView updateDevGpsxTitle;
    EditText updateDevGpsx;
    TextView updateDevGpsyTitle;
    EditText updateDevGpsy;
    TextView updateDevRemarkTitle;
    EditText updateDevRemark;
    ScrollView mapUpdateDevWindow;
    ImageView updatePointEcs;
    TextView updatePointTitle;
    TextView updatePointSubmit;
    TextView updatePointTypeTitle;
    EditText updatePointType;
    TextView updatePointAddressTitle;
    EditText updatePointAddress;
    TextView updatePointGpsTitle;
    EditText updatePointGps;
    TextView updatePointRemarkTitle;
    EditText updatePointRemark;
    ScrollView mapUpdatePointWindow;
    RelativeLayout mapUpdateBottomSheet;
    LinearLayout mapSearchLayout;
    TextView mapMsg;
    TextView mapLayer;
    TextView mapTrack;
    TextView mapMeasure;
    TextView mapMore;
    ImageView mapGps;
    TextView mapUpdate;
    ImageView mapSearchDetailBack;
    ImageView updateHdEcs;
    TextView updateHdTitle;
    TextView updateHdSubmit;
    TextView updateSiteConTypeTitle;
    TextView updateSiteConType;
    TextView updateSitePropJhyyTitle;
    TextView updateSitePropJhyy;
    TextView updateSiteOtherTypeTitle;
    EditText updateSiteOtherType;
    TextView updateSiteSiteLeaderTitle;
    EditText updateSiteSiteLeader;
    TextView updateSiteSiteLeaderTelTitle;
    EditText updateSiteSiteLeaderTel;
    TextView updateSitePropGdssqkTitle;
    EditText updateSitePropGdssqk;
    TextView updateSitePropGdmsqkTitle;
    EditText updateSitePropGdmsqk;
    LinearLayout updateSiteLzLl;

    private int position;
    private ArrayList<PatrolHdType> postResponse;
    private ArrayList<PatrolHdType> siteArea;
    private List<DicCacheDao> dicCacheDaos = new ArrayList<>();
    private List<DicCacheDao> dicCacheDaosSite = new ArrayList<>();
    private List<DicCacheDao> canton = new ArrayList<>();
    private List<DicCacheDao> hdDevice = new ArrayList<>();
    private List<DicCacheDao> lx = new ArrayList<>();
    private List<DicCacheDao> fj = new ArrayList<>();
    private List<DicCacheDao> ffxz = new ArrayList<>();
    private List<DicCacheDao> fsfs = new ArrayList<>();
    private List<DicCacheDao> gc = new ArrayList<>();
    private List<DicCacheDao> lqsb = new ArrayList<>();
    private List<DicCacheDao> name = new ArrayList<>();
    private List<DicCacheDao> yhlx = new ArrayList<>();
    private List<DicCacheDao> conType = new ArrayList<>();
    private List<DicCacheDao> propJhyy = new ArrayList<>();
    private HashMap<String, String> hdSubmit = new HashMap<>();
    private HashMap<String, String> siteSubmit = new HashMap<>();
    private HashMap<String, String> pointSubmit = new HashMap<>();
    private HashMap<String, String> devSubmit = new HashMap<>();
    private ArrayList<UpImgResultBean> pictureResult = new ArrayList<>();
    private ArrayList<UpImgResultBean> audioResult = new ArrayList<>();
    private ArrayList<UpImgResultBean> videoResult = new ArrayList<>();
    /**
     * 网格编号
     */
    private String gridNum = "";
    private List<MapGridBean> mMapGridBeans = new ArrayList<>();
    private DialogPlus mDialogPlus, mDialogPlusSlid;
    /**
     * 声明SlideMenu对象
     */
    private View mView;
    /**
     * 上报弹窗底部
     */
    private View updateFooter;
    /**
     * 上报弹窗表单-隐患，工地文件
     */
    private View fileView;
    private TextView title;
    private BaseRecyclerView rvPicture;
    private BaseRecyclerView rvAudio;
    private BaseRecyclerView rvVideo;
    private ArrayList<LocalMedia> selectPictureList = new ArrayList<>();
    private ArrayList<LocalMedia> selectAudioList = new ArrayList<>();
    private ArrayList<LocalMedia> selectVideoList = new ArrayList<>();
    /**
     * 属性弹窗-管线
     */
    private View viewLine;
    /**
     * 属性弹窗-隐患
     */
    private View viewHd;
    /**
     * 属性弹窗标识 类型 1、管线 2、隐患 3、人员 4、计划 5、工地 6、道路
     */
    private int type;
    /**
     * 工地对象
     */
    private SiteBean gdBean;
    /**
     * 隐患对象
     */
    private HdOrderBean hdBean;
    /**
     * 人员对象
     */
    private UserLayerBean personBean;
    /**
     * 计划对象
     */
    private PatrolTaskResultBean planBean;
    /**
     * 车辆对象
     */
    private CarBean carBean;
    /**
     * 道路对象
     */
    private MapSearchBean roundBean;
    /**
     * 标题
     */
    private String[] mTitles = {"工单", "计划", "地图", "台帐", "我的"};
    /**
     * 图标
     */
    private int[] mIconUnSelectIds = {
            R.mipmap.icon_work_order, R.mipmap.icon_task,
            R.mipmap.icon_map, R.mipmap.icon_list,
            R.mipmap.icon_mine};
    /**
     * 选择图标
     */
    private int[] mIconSelectIds = {
            R.mipmap.icon_work_order_select, R.mipmap.icon_task_select,
            R.mipmap.icon_map_select, R.mipmap.icon_list_select,
            R.mipmap.icon_mine_select};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<CustomGridPicWindowBean> mSlidingTypes;
    private MapSlidingGridAdapter mMoreAdapter, mTypeAdapter;
    private ArrayList<GridSelectBean> mSlidingAreas, mSlidingObjs;

    private Realm realm;

    /**
     * 巡检区域测试
     */
    private AreaTestBean mTestBean;
    /**
     * 拍照路径
     */
    private String imgPath;
    /**
     * 拍照是否提示
     */
    private boolean isTips = true;
    /**
     * 搜索类型
     */
    private List<GridSelectBean> beans = new ArrayList<>();
    private StringBuilder typeStr = new StringBuilder();

    private List<MultiItemEntity> gridSelectBeans;
    /**
     * 道路对象
     */
    private GridSelectBean roundSelectBean;
    private MapSearchListAdapter mMapSearchListAdapter;
    private ResultMapSearchBean body;
    private DeviceBean deviceBean;
    /*****  地图参数 ******/
    private GisParamsBean mGisParamsBean = new GisParamsBean();
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
     * 绘制工具
     */
    private MeasureTool measureTool;
    /**
     * 临时要素显示图层（绘制图层）
     */
    private GraphicsOverlay tempGraphicsLayer;
    /**
     * 地图空间类型
     */
    private SpatialReference reference;
    /**
     * 地图状态监听
     */
    private MapStatusListener mapStatusListener;
    /**
     * 地图点击监听
     */
    private MapTouchListener mapTouchListener;

    //*******图层********/
    /**
     * 是否线路规划|导航
     */
    private boolean isNav;
    /**
     * 巡检片区图层
     */
    private GraphicsOverlay areaOverlay;
    /**
     * 网格片区图层
     */
    private GraphicsOverlay gridOverlay;
    /**
     * 人员图层
     */
    private GraphicsOverlay personOverlay;
    /**
     * 隐患图层
     */
    private GraphicsOverlay hdOverlay;
    /**
     * 计划图层
     */
    private GraphicsOverlay planOverlay;
    /**
     * 工地图层
     */
    private GraphicsOverlay siteOverlay;
    /**
     * 车辆图层
     */
    private GraphicsOverlay carOverlay;
    /**
     * 正则表达式格式化管线日期
     */
    private Pattern pattern;
    /**
     * 地图属性对象
     */
    private Map<String, Object> mapBeans;
    /**
     * 距离对象
     */
    private double dis = 0.0;
    /**
     * 地图对象类型
     * 0.点
     * 1.其他
     */
    private int geometryType;
    private String mapStr, geoStr;
    /**
     * 导航坐标(默认成都坐标)
     */
    private double navX = 30.572262, navY = 104.066513;

    /**
     * 区分是否点击了设备  有无名称
     */
    private String devName = "";
    private EditText etRemark;

    private LoginResultBean userInfo;

    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    public void initListener() {
        mapGps.setOnClickListener(this::setClick);
        mapMsg.setOnClickListener(this::setClick);
        mapLayer.setOnClickListener(this::setClick);
        mapTrack.setOnClickListener(this::setClick);
        mapMeasure.setOnClickListener(this::setClick);
        mapMore.setOnClickListener(this::setClick);
        mapLineDialogDetail.setOnClickListener(this::setClick);
        mapLineDialogNavigation.setOnClickListener(this::setClick);
        mapSearchDetailSearch.setOnClickListener(this::setClick);
        mapSearchText.setOnClickListener(this::setClick);
        mapUpdate.setOnClickListener(this::setClick);
        mapSearchDetailBack.setOnClickListener(this::setClick);
        mapLineDialogReport.setOnClickListener(this::setClick);
        updateHdEcs.setOnClickListener(this::setClick);
        updateSiteEcs.setOnClickListener(this::setClick);
        updateDevEcs.setOnClickListener(this::setClick);
        updatePointEcs.setOnClickListener(this::setClick);
        updateHdSubmit.setOnClickListener(this::setClick);
        updateSiteSubmit.setOnClickListener(this::setClick);
        updateDevSubmit.setOnClickListener(this::setClick);
        updatePointSubmit.setOnClickListener(this::setClick);

        updateHdType.setOnClickListener(this::addOnClick);
        updateHdLevel.setOnClickListener(this::addOnClick);
        updateSiteType.setOnClickListener(this::addOnClick);
        updateHdDx.setOnClickListener(this::addOnClick);
        updateHdXzqq.setOnClickListener(this::addOnClick);
        updateSiteXzqy.setOnClickListener(this::addOnClick);
        updateSiteSspq.setOnClickListener(this::addOnClick);
        updateHdFsfs.setOnClickListener(this::addOnClick);
        updateHdSslb.setOnClickListener(this::addOnClick);
        updateHdGc.setOnClickListener(this::addOnClick);
        updateHdGj.setOnClickListener(this::addOnClick);
        updateHdFfxz.setOnClickListener(this::addOnClick);
        updateHdLqsb.setOnClickListener(this::addOnClick);
        updateHdLx.setOnClickListener(this::addOnClick);
        updateHdYhlx.setOnClickListener(this::addOnClick);
        updateSiteConType.setOnClickListener(this::addOnClick);
        updateSitePropJhyy.setOnClickListener(this::addOnClick);
    }

    public void setClick(View view) {
        int i = view.getId();
        if (i == R.id.map_gps) {// 我的定位
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            showMapLine(false, type);
            MapToolUtil.getLocation(mapMv);

        } else if (i == R.id.map_search_detail_search) {// 查询
            boolean flag = false;
            typeStr = new StringBuilder();
            if (ObjectUtils.isNotEmpty(roundSelectBean)
                    && ObjectUtils.isNotEmpty(roundSelectBean.getSubItems())) {
                roundSelectBean.getSubItems().clear();
            }
            if (ObjectUtils.isNotEmpty(gridSelectBeans)) {
                gridSelectBeans.clear();
            }
            if (StringUtils.isTrimEmpty(mapSearchDetailText.getText().toString())) {
                mMapSearchListAdapter.notifyDataSetChanged();
                ToastBuilder.showShortWarning("请输入查询条件");
                return;
            }
            for (GridSelectBean selectBean : beans) {
                if (selectBean.getTitle().equals("道路")) {
                    if (selectBean.isSelectStatus() && !StringUtils.isTrimEmpty(mapSearchDetailText.getText().toString())) {
                        flag = true;
                        RetrofitUtils.searchRound(this, mGisParamsBean.appCenter.replaceAll("\\|", ","), mapSearchDetailText.getText().toString(), 5);
                    }
                } else if (selectBean.isSelectStatus()) {
                    flag = true;
                    typeStr.append(selectBean.getValue());
                    typeStr.append(",");
                }
            }
            if (!flag) {
                ToastBuilder.showShortWarning("请选择至少一种查询类型");
                return;
            }
            if (StringUtils.isTrimEmpty(typeStr.toString())) {
                return;
            }
            mPresenter.getSearchData(true, mapSearchDetailText.getText().toString(), typeStr.toString());

        } else if (i == R.id.map_msg) {// 消息按钮 TODO
            showMapLine(false, type);
            PatrolEnterPointActivity.gotoMsgListActivity(getActivity());

        } else if (i == R.id.map_layer) {// 图层按钮
            showMapLine(false, type);
            showSlidingMenu();

        } else if (i == R.id.map_track) {// 跟踪
            showMapLine(false, type);
            track();

        } else if (i == R.id.map_measure) {// 测量
            showMapLine(false, type);
            showMeasureWindow();

        } else if (i == R.id.map_more) {// 更多
            showMapLine(false, type);
            showMore();

        } else if (i == R.id.map_line_dialog_detail) {// 1、管线 2、隐患 3、人员 4、计划 5、工地 6、道路
            if (1 == type && ObjectUtils.isNotEmpty(mapBeans)) {
                // 管线详情
                PatrolEnterPointActivity.gotoMapPipDetailActivity(getActivity(), mapStr, geoStr, dis, geometryType);
            } else if (1 == type && ObjectUtils.isNotEmpty(deviceBean)) {
                // 设备详情
                PatrolEnterPointActivity.gotoMapDeviceDetailActivity(getActivity(), deviceBean);
            } else if (2 == type) {
                // 隐患详情
                PatrolEnterPointActivity.gotoMapHdDetailActivity(getActivity(), hdBean);
            } else if (3 == type) {
                // 人员详情
                PatrolEnterPointActivity.gotoMapPersonDetailActivity(getActivity(), personBean);
            } else if (4 == type) {
                // 计划详情
                PatrolEnterPointActivity.gotoMapPlanDetailActivity(getActivity(), planBean);
            } else if (5 == type) {
                // 工地详情
                PatrolEnterPointActivity.gotoMapSiteDetailActivity(getActivity(), gdBean, 1);
            } else if (7 == type) {
                // 车辆详情 TODO
                PatrolEnterPointActivity.gotoMapCarDetailActivity(getActivity(), carBean);
            }

        } else if (i == R.id.map_line_dialog_navigation) {// 导航
            // showMapLine(false, type);
            new Handler().postDelayed(() -> NavigationUtil.showNavWindow(navX, navY, getActivity()), Constant.DIALOG_TIME);

        } else if (i == R.id.map_search_text) {// 显示搜索界面
            showMapLine(false, type);
            mapSearchDetail.setVisibility(View.VISIBLE);
            mapSearchDetailText.requestFocus();
            YoYo.with(Techniques.FadeInDown)
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
                            KeyboardUtils.toggleSoftInput();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    })
                    .playOn(mapSearchDetailContent);

        } else if (i == R.id.map_search_detail_back) {
            showMapLine(false, type);
            // 隐藏搜索界面/返回地图界面
            mapSearchHind();

        } else if (i == R.id.map_update) {
            if (GlobalConfig.isDoWork) {
                // 上报按钮
                showMapLine(false, type);
                cancelMeasure();
                isNav = false;
//                    isUpdate = !isUpdate;
//                    mapTouchListener.flag = isUpdate ? 2 : 0;
//                    ToastBuilder.showShortWarning(isUpdate ? "请您选择上报点" : "选点上报已关闭");
                ActivityUtils.startActivity(new Intent(getActivity(), MapUpdateActivity.class));
            } else {
                RetrofitUtils.doOnWork(getActivity(), 1);
            }

        } else if (i == R.id.map_line_dialog_report) {
            if (GlobalConfig.isDoWork) {
                // 管线|隐患上报
                showMapLine(false, type);
                if (type == 1) {
                    showUpdateWindow(new Point(navY, navX), false);
                } else {
                    showReportLayer(type);
                }
            } else {
                RetrofitUtils.doOnWork(getActivity(), 1);
            }

        } else if (i == R.id.update_hd_ecs || i == R.id.update_site_ecs || i == R.id.update_dev_ecs || i == R.id.update_point_ecs) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        } else if (i == R.id.update_hd_submit || i == R.id.update_site_submit || i == R.id.update_dev_submit || i == R.id.update_point_submit) {
            if (position == 0) {
                // 隐患 下一步
                new Handler().postDelayed(() -> showReportWindow(0), Constant.DIALOG_TIME);
            } else if (position == 1) {
                // 工地 下一步
                new Handler().postDelayed(() -> showReportWindow(1), Constant.DIALOG_TIME);
            } else if (position == 2) {
                // 巡检点 提交
                pointSubmit.put("addUserLat", PreferencesUtil.getString(Constant.LOCATION_LATITUDE));
                pointSubmit.put("addUserLon", PreferencesUtil.getString(Constant.LOCATION_LONGITUDE));
                pointSubmit.put("addType", "2");
                pointSubmit.put("gridId", gridNum);
                pointSubmit.put("taskPointName", updatePointType.getText().toString());
                pointSubmit.put("remarks", updatePointRemark.getText().toString());
                pointSubmit.put("address", updatePointAddress.getText().toString());
                mPresenter.pointSubmit(pointSubmit);
            } else if (position == 3) {
                // 设备纠错 提交
                devSubmit.put("upRemarks", updateDevRemark.getText().toString());
                devSubmit.put("addUserLat", PreferencesUtil.getString(Constant.LOCATION_LATITUDE));
                devSubmit.put("addUserLon", PreferencesUtil.getString(Constant.LOCATION_LONGITUDE));
                if (!TextUtils.isEmpty(devName)) {
                    if (ObjectUtils.isNotEmpty(mapBeans.get("PPID"))) {
                        devSubmit.put("ppid", mapBeans.get("PPID").toString());
                    }
                    devSubmit.put("objectid", mapBeans.get("OBJECTID").toString());
                    devSubmit.put("deviceType", devName);
                    devSubmit.put("nowLon", updateDevGpsx.getText().toString());
                    devSubmit.put("nowLat", updateDevGpsy.getText().toString());
                } else {
                    devSubmit.put("deviceType", updateDevType.getText().toString());
                }
                devSubmit.put("gridId", gridNum);
                mPresenter.devNewSubmit(devSubmit);
            }

        }
    }

    public void addOnClick(View view) {
        List<GridSelectBean> beans;
        int i = view.getId();
        if (i == R.id.update_hd_type) {
            beans = new ArrayList<>();
            if (yhlx.isEmpty()) {
                yhlx = SqlDataPresenter.getYhlxType(realm);
            }
            for (DicCacheDao dao : yhlx) {
                beans.add(new GridSelectBean(dao.getDicName(), String.valueOf(dao.getDicCode())));
            }
            showSelect(beans, (TextView) view, 15);

        } else if (i == R.id.update_hd_level) {
            beans = new ArrayList<>();
            if (dicCacheDaos.isEmpty()) {
                dicCacheDaos = SqlDataPresenter.getHdLevel(realm);
            }
            for (DicCacheDao dao : dicCacheDaos) {
                beans.add(new GridSelectBean(dao.getDicName(), String.valueOf(dao.getDicCode())));
            }
            showSelect(beans, (TextView) view, 2);

        } else if (i == R.id.update_site_type) {
            beans = new ArrayList<>();
            if (dicCacheDaosSite.isEmpty()) {
                dicCacheDaosSite = SqlDataPresenter.getSiteLevel(realm);
            }
            for (DicCacheDao dao : dicCacheDaosSite) {
                beans.add(new GridSelectBean(dao.getDicName(), String.valueOf(dao.getDicCode())));
            }
            showSelect(beans, (TextView) view, 3);

        } else if (i == R.id.update_hd_dx) {
            beans = new ArrayList<>();
            if (hdDevice.isEmpty()) {
                hdDevice = SqlDataPresenter.getHdDeviceType(realm);
            }
            for (DicCacheDao dao : hdDevice) {
                beans.add(new GridSelectBean(dao.getDicName(), String.valueOf(dao.getDicCode())));
            }
            showSelect(beans, (TextView) view, 4);

        } else if (i == R.id.update_hd_xzqq) {
            beans = new ArrayList<>();
            if (canton.isEmpty()) {
                canton = SqlDataPresenter.getCanton(realm);
            }
            for (DicCacheDao dao : canton) {
                beans.add(new GridSelectBean(dao.getDicName(), String.valueOf(dao.getDicCode())));
            }
            showSelect(beans, (TextView) view, 5);

        } else if (i == R.id.update_site_xzqy) {
            beans = new ArrayList<>();
            if (canton.isEmpty()) {
                canton = SqlDataPresenter.getCanton(realm);
            }
            for (DicCacheDao dao : canton) {
                beans.add(new GridSelectBean(dao.getDicName(), String.valueOf(dao.getDicCode())));
            }
            showSelect(beans, (TextView) view, 6);

        } else if (i == R.id.update_hd_fsfs) {
            beans = new ArrayList<>();
            if (fsfs.isEmpty()) {
                fsfs = SqlDataPresenter.getFsfsType(realm);
            }
            for (DicCacheDao dao : fsfs) {
                beans.add(new GridSelectBean(dao.getDicName(), String.valueOf(dao.getDicCode())));
            }
            showSelect(beans, (TextView) view, 8);

        } else if (i == R.id.update_hd_sslb) {
            beans = new ArrayList<>();
            if (name.isEmpty()) {
                name = SqlDataPresenter.getNameType(realm);
            }
            for (DicCacheDao dao : name) {
                beans.add(new GridSelectBean(dao.getDicName(), String.valueOf(dao.getDicCode())));
            }
            showSelect(beans, (TextView) view, 9);

        } else if (i == R.id.update_hd_gc) {
            beans = new ArrayList<>();
            if (gc.isEmpty()) {
                gc = SqlDataPresenter.getGcType(realm);
            }
            for (DicCacheDao dao : gc) {
                beans.add(new GridSelectBean(dao.getDicName(), String.valueOf(dao.getDicCode())));
            }
            showSelect(beans, (TextView) view, 10);

        } else if (i == R.id.update_hd_gj) {
            beans = new ArrayList<>();
            if (fj.isEmpty()) {
                fj = SqlDataPresenter.getFjType(realm);
            }
            for (DicCacheDao dao : fj) {
                beans.add(new GridSelectBean(dao.getDicName(), String.valueOf(dao.getDicCode())));
            }
            showSelect(beans, (TextView) view, 11);

        } else if (i == R.id.update_hd_ffxz) {
            beans = new ArrayList<>();
            if (ffxz.isEmpty()) {
                ffxz = SqlDataPresenter.getFfxzType(realm);
            }
            for (DicCacheDao dao : ffxz) {
                beans.add(new GridSelectBean(dao.getDicName(), String.valueOf(dao.getDicCode())));
            }
            showSelect(beans, (TextView) view, 12);

        } else if (i == R.id.update_hd_lqsb) {
            beans = new ArrayList<>();
            if (lqsb.isEmpty()) {
                lqsb = SqlDataPresenter.getLqsbType(realm);
            }
            for (DicCacheDao dao : lqsb) {
                beans.add(new GridSelectBean(dao.getDicName(), String.valueOf(dao.getDicCode())));
            }
            showSelect(beans, (TextView) view, 13);

        } else if (i == R.id.update_hd_lx) {
            beans = new ArrayList<>();
            if (lx.isEmpty()) {
                lx = SqlDataPresenter.getLxType(realm);
            }
            for (DicCacheDao dao : lx) {
                beans.add(new GridSelectBean(dao.getDicName(), String.valueOf(dao.getDicCode())));
            }
            showSelect(beans, (TextView) view, 14);

        } else if (i == R.id.update_hd_yhlx) {
            beans = new ArrayList<>();
            if (yhlx.isEmpty()) {
                yhlx = SqlDataPresenter.getYhlxType(realm);
            }
            for (DicCacheDao dao : yhlx) {
                beans.add(new GridSelectBean(dao.getDicName(), String.valueOf(dao.getDicCode())));
            }
            showSelect(beans, (TextView) view, 15);

        } else if (i == R.id.update_site_conType) {
            beans = new ArrayList<>();
            if (conType.isEmpty()) {
                conType = SqlDataPresenter.getConType(realm);
            }
            for (DicCacheDao dao : conType) {
                beans.add(new GridSelectBean(dao.getDicName(), String.valueOf(dao.getDicCode())));
            }
            showSelect(beans, (TextView) view, 16);

        } else if (i == R.id.update_site_propJhyy) {
            beans = new ArrayList<>();
            if (propJhyy.isEmpty()) {
                propJhyy = SqlDataPresenter.getPropJhyy(realm);
            }
            for (DicCacheDao dao : propJhyy) {
                beans.add(new GridSelectBean(dao.getDicName(), String.valueOf(dao.getDicCode())));
            }
            showSelect(beans, (TextView) view, 17);

        } else if (i == R.id.update_site_sspq) {
            beans = new ArrayList<>();
            if (ObjectUtils.isNotEmpty(siteArea)) {
                for (PatrolHdType dao : siteArea) {
                    beans.add(new GridSelectBean(dao.getText(), String.valueOf(dao.getValue())));
                }
            }
            showSelect(beans, (TextView) view, 7);

        }
    }

    private void showSelect(List<GridSelectBean> beans, TextView view, int flag) {
        if (beans.size() == 0) {
            ToastBuilder.showShortWarning("当前暂无可选项");
            return;
        }
        new ConfirmListDialog(getActivity())
                .setTitleStr("请选择")
                .setTitleSearchStr("请输入你想搜索的条件")
                .setSearch(true)
                .setList(beans)
                .setSingle(flag == 1 || flag == 2 || flag == 3 || flag == 4 || flag == 5
                        || flag == 6 || flag == 7 || flag == 14 || flag == 15 || flag == 16 || flag == 17)
                .setConfirmListDialogListener(new ConfirmListDialogListener() {
                    @Override
                    public void onCheckSelect(List<GridSelectBean> mList) {
                        StringBuilder name = new StringBuilder();
                        StringBuilder id = new StringBuilder();
                        for (int i = 0; i < mList.size(); i++) {
                            name.append(mList.get(i).getTitle());
                            id.append(mList.get(i).getValue());
                            if (mList.size() - 1 != i) {
                                name.append(",");
                                id.append(",");
                            }
                        }
                        view.setText(name.toString());
                        if (flag == 1) {
                            hdSubmit.put("hdType", id.toString());
                        } else if (flag == 2) {
                            hdSubmit.put("hdGrand", id.toString());
                        } else if (flag == 3) {
                            siteSubmit.put("siteLevel", id.toString());
                        } else if (flag == 4) {
                            hdSubmit.put("hdObject", id.toString());
                            mPresenter.getHdType(id.toString());
                        } else if (flag == 5) {
                            hdSubmit.put("hdCanton", id.toString());
                        } else if (flag == 6) {
                            siteSubmit.put("district", id.toString());
                        } else if (flag == 7) {
                            siteSubmit.put("areaId", id.toString());
                        } else if (flag == 8) {
                            hdSubmit.put("propFsfs", id.toString());
                        } else if (flag == 9) {
                            hdSubmit.put("propName", id.toString());
                        } else if (flag == 10) {
                            hdSubmit.put("propGc", id.toString());
                        } else if (flag == 11) {
                            hdSubmit.put("propFj", id.toString());
                        } else if (flag == 12) {
                            hdSubmit.put("propFfxz", id.toString());
                        } else if (flag == 13) {
                            hdSubmit.put("propLqsb", id.toString());
                        } else if (flag == 14) {
                            hdSubmit.put("propLx", id.toString());
                            setViewShowByType(name.toString());
                        } else if (flag == 15) {
                            hdSubmit.put("propYhlx", id.toString());
                        } else if (flag == 16) {
                            siteSubmit.put("conType", id.toString());
                        } else if (flag == 17) {
                            siteSubmit.put("propJhyy", id.toString());
                        }
                    }

                    @Override
                    public void onCheckSingle(GridSelectBean bean, int position) {

                    }

                    @Override
                    public void onItemClickListener(GridSelectBean bean, int position) {
                        view.setText(bean.getTitle());
                        if (flag == 1) {
                            hdSubmit.put("hdType", bean.getValue());
                        } else if (flag == 2) {
                            hdSubmit.put("hdGrand", bean.getValue());
                        } else if (flag == 3) {
                            siteSubmit.put("siteLevel", bean.getValue());
                        } else if (flag == 4) {
                            hdSubmit.put("hdObject", bean.getValue());
                            mPresenter.getHdType(bean.getValue());
                        } else if (flag == 5) {
                            hdSubmit.put("hdCanton", bean.getValue());
                        } else if (flag == 6) {
                            siteSubmit.put("district", bean.getValue());
                        } else if (flag == 7) {
                            siteSubmit.put("areaId", bean.getValue());
                        } else if (flag == 8) {
                            hdSubmit.put("propFsfs", bean.getValue());
                        } else if (flag == 9) {
                            hdSubmit.put("propName", bean.getValue());
                        } else if (flag == 10) {
                            hdSubmit.put("propGc", bean.getValue());
                        } else if (flag == 11) {
                            hdSubmit.put("propFj", bean.getValue());
                        } else if (flag == 12) {
                            hdSubmit.put("propFfxz", bean.getValue());
                        } else if (flag == 13) {
                            hdSubmit.put("propLqsb", bean.getValue());
                        } else if (flag == 14) {
                            hdSubmit.put("propLx", bean.getValue());
                            setViewShowByType(bean.getTitle());
                        } else if (flag == 15) {
                            hdSubmit.put("propYhlx", bean.getValue());
                        } else if (flag == 16) {
                            siteSubmit.put("conType", bean.getValue());
                        } else if (flag == 17) {
                            siteSubmit.put("propJhyy", bean.getValue());
                        } else if (flag == 18) {
                            hdSubmit.put("hdGrand", bean.getValue());
                        }
                    }
                }).show();
    }

    private void track() {
        if (mLocationDisplay.isStarted()) {
            mLocationDisplay.stop();
            ToastBuilder.showShortWarning("关闭跟踪");
        } else {
            mLocationDisplay.startAsync();
            ToastBuilder.showShortWarning("开启跟踪");
        }
    }

    /**
     * 显示图层控制弹窗
     */
    private void showSlidingMenu() {
        if (ObjectUtils.isEmpty(mDialogPlusSlid)) {
            mDialogPlusSlid = DialogPlus.newDialog(Objects.requireNonNull(getActivity()))
                    // Select different holder.ListHolder|ViewHolder(自定义布局)|GridHolder
                    .setContentHolder(new ViewHolder(mView))
                    .setContentHeight(ScreenUtils.getScreenHeight())
                    .setContentWidth(ScreenUtils.getScreenWidth() / 4 * 3)
                    .setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
                    // This will enable the expand feature, (similar to android L share dialog)
                    // default is false, only works for grid and list
                    // 这将启用扩展功能，（类似于Android L共享对话框）
                    // 默认为false，只适用于网格和列表
                    // .setExpanded(true)
                    // 动画高度
                    // .setExpanded(false, 500)
                    // Set dialog position. BOTTOM (default), TOP or CENTER. You can also combine other Gravity options.
                    // 设置对话框位置。底部（默认），顶部或中心。你也可以结合其他重力选项。
                    .setGravity(Gravity.END)
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
                    .setInAnimation(R.anim.slide_in_right)
                    .setOutAnimation(R.anim.slide_out_right)
                    .create();
        }
        mDialogPlusSlid.show();
    }

    /**
     * 显示更多工具
     */
    private void showMore() {
        mDialogPlus = DialogPlus.newDialog(Objects.requireNonNull(getActivity()))
                .setContentHolder(new GridHolder(3))
                .setContentWidth(ScreenUtils.getScreenWidth() / 3 * 2)
                .setMargin(0, 0, ConvertUtils.dp2px(12), ConvertUtils.dp2px(100))
                .setPadding(ConvertUtils.dp2px(10), ConvertUtils.dp2px(10),
                        ConvertUtils.dp2px(10), ConvertUtils.dp2px(10))
                .setGravity(Gravity.END | Gravity.CENTER)
                .setCancelable(true)
                .setContentBackgroundResource(R.drawable.stroke_bg_radius)
                .setAdapter(mMoreAdapter)
                .setInAnimation(R.anim.slide_in_right)
                .setOutAnimation(R.anim.slide_out_right)
                .setOnItemClickListener((dialog, item, view, position) -> {
                    dialog.dismiss();
                    switch (position) {
                        case 0:
                            // 跟踪
                            track();
                            break;
                        case 1:
                            // 测量
                            new Handler().postDelayed(this::showMeasureWindow, Constant.DIALOG_TIME);
                            break;
                        case 2:
                            // 全图
                            MapToolUtil.mapFullExtent(mapMv, viewPoint);
                            break;
                        case 3:
                            // 坐标
                            new Handler().postDelayed(() -> new MapPointDialog(getActivity())
                                    .setEditeListener(new MapPointDialog.ConfirmDialogEditeListener() {
                                        @Override
                                        public void onYesClick(String meg) {
                                            if (StringUtils.isTrimEmpty(meg)) {
                                                ToastBuilder.showShortError("请输入有效坐标");
                                                return;
                                            }
                                            double x = Double.valueOf(meg.split(",")[0]);
                                            double y = Double.valueOf(meg.split(",")[1]);
                                            addIconInMapFromXY(x, y);
                                        }

                                        @Override
                                        public void onNoClick(String meg) {

                                        }
                                    }).show(), Constant.DIALOG_TIME);
                            break;
                        case 4:
                            // 导航
                            cancelMeasure();
                            isNav = !isNav;
                            mapTouchListener.flag = isNav ? 1 : 0;
                            ToastBuilder.showShortWarning(isNav ? "选点导航已开启" : "选点导航已关闭");
                            break;
                        case 5:
                            // 轨迹
                            ToastBuilder.showShortWarning(R.string.application_not_open);
                            break;
                        case 6:
                            // 图例
                            PictureProcessingUtil.imgSingeShow(getActivity(), PreferencesUtil.getString(Constant.REQUEST_ADDRESS)
                                    + "img/map/legend-mobile.png");
                            break;
                        case 7:
                            // 拍照
                            if (isTips) {
                                new Handler().postDelayed(() -> new ConfirmSelectDialog(getActivity())
                                        .setTitleStr("提示")
                                        .setContentStr("拍照功能提供在网络状态不佳的情况下，" +
                                                "拍摄现场照片并加上水印以供网络良好的情况下进行水印照片的上传。存储路径：内存卡‘" + Constant.APP_DIR + "/image’目录下")
                                        .setYesStr("本次不再提示")
                                        .setNoStr("确定")
                                        .setListener(new ConfirmDialogListener() {
                                            @Override
                                            public void onYesClick() {
                                                isTips = false;
                                                imgPath = PictureProcessingUtil.takePicture(MapFragment.this);
                                            }

                                            @Override
                                            public void onNoClick() {
                                                imgPath = PictureProcessingUtil.takePicture(MapFragment.this);
                                            }
                                        }).show(), Constant.DIALOG_TIME);
                            } else {
                                imgPath = PictureProcessingUtil.takePicture(MapFragment.this);
                            }

                            break;
                        case 8:
                            // 网络
                            ToastBuilder.showShortWarning(R.string.application_not_open);
                            break;
                        default:
                            break;
                    }
                })
                .create();
        if (mDialogPlus.isShowing()) {
            mDialogPlus.dismiss();
        }
        mDialogPlus.show();
    }

    @Override
    public void onDestroyView() {
        realm.close();
        if (ObjectUtils.isNotEmpty(mLocationDisplay) && mLocationDisplay.isStarted()) {
            mLocationDisplay.stop();
        }
        if (ObjectUtils.isNotEmpty(mapMv)) {
            mapMv.dispose();
        }
        if (ObjectUtils.isNotEmpty(mPresenter)) {
            mPresenter.onUnsubscribe();
        }
        super.onDestroyView();
    }

    @Override
    public void getLayerStatusSuccess(String state) {

    }

    @Override
    public void getLayerActiveSuccess() {
        reference = mapMv.getSpatialReference();
        viewPoint = getViewpointFromParam(strAppCenter, reference);
        //初始化地图点图pic
        addCoords(null);
        // 各个图层初始化
        areaOverlay = MapUtil.addSingelLayer(mapMv, areaOverlay);
        gridOverlay = MapUtil.addSingelLayer(mapMv, gridOverlay);
        personOverlay = MapUtil.addSingelLayer(mapMv, personOverlay);
        hdOverlay = MapUtil.addSingelLayer(mapMv, hdOverlay);
        planOverlay = MapUtil.addSingelLayer(mapMv, planOverlay);
        siteOverlay = MapUtil.addSingelLayer(mapMv, siteOverlay);
        carOverlay = MapUtil.addSingelLayer(mapMv, carOverlay);
        tempGraphicsLayer = MapUtil.addSingelLayer(mapMv, tempGraphicsLayer);
        // 设置放大至指定比例尺不显示
        areaOverlay.setMinScale(MapConstant.MIN_SCALE);
        gridOverlay.setMinScale(MapConstant.MIN_SCALE);
        personOverlay.setMinScale(MapConstant.MIN_SCALE);
        hdOverlay.setMinScale(MapConstant.MIN_SCALE);
        planOverlay.setMinScale(MapConstant.MIN_SCALE);
        siteOverlay.setMinScale(MapConstant.MIN_SCALE);
        carOverlay.setMinScale(MapConstant.MIN_SCALE);
        tempGraphicsLayer.setMinScale(MapConstant.MIN_SCALE);
        //MapToolUtil.getLocation(mapMv);
    }

    @Override
    public void getLayerErrorSuccess(String layerName) {
        ToastBuilder.showShortError(layerName + "图层加载失败...");
    }

    /**
     * 添加地图点击高亮点
     */
    private void addCoords(Point pt) {
        if (pt == null) {
            return;
        }
        MapUtil.addSingelLayer(mapMv, tempGraphicsLayer).getGraphics().clear();
        MapUtil.addPointLayer(mapMv, tempGraphicsLayer, getActivity(), R.mipmap.icon_navigation_address, pt, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getMapClickPtSuccess(Point pt, int flag) {
        if (pt == null) {
            return;
        }
        if (flag != 0) {
            MapUtil.addSingelLayer(mapMv, tempGraphicsLayer).getGraphics().clear();
            Point startPt = mapMv.getLocationDisplay().getMapLocation();
            Point endPt = new Point(pt.getX(), pt.getY(), reference);
            switch (flag) {
                case 1:
                    // 导航
                    addStarEndOnMap(startPt, endPt);
                    break;
                case 2:
                    //图标
                    MapUtil.addPointLayer(mapMv, tempGraphicsLayer, getActivity(), R.mipmap.icon_gps_point, pt, false);
                    if (!GlobalConfig.isDoWork) {
                        RetrofitUtils.doOnWork(getActivity(), 1);
                        return;
                    }
                    // 上报
                    showUpdateWindow(endPt, true);
                    break;
                default:
                    break;
            }
        } else {
            showMapLine(false, 0);
            addCoords(pt);
        }
        if (ObjectUtils.isNotEmpty(pt)) {
            // 坐标显示
            mapShowGps.setText(pt.getX() + "," + pt.getY());
        }
    }

    @Override
    public void getMapLongPressPtSuccess(Point pt) {
        MapUtil.addSingelLayer(mapMv, tempGraphicsLayer).getGraphics().clear();
        ToastBuilder.showShort("临时图层已清除");
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
        ToastBuilder.showShortWarning("查询图层失败");
    }

    @Override
    public void getAllLayerQuerySuccess(List<IdentifyLayerResult> layerResultList) {

    }

    @Override
    public void getGraphicsOverlaySuccess(List<Graphic> graphicList) {

    }

    @Override
    public void getAllGraphicsOverlaySuccess(List<IdentifyGraphicsOverlayResult> graphicsOverlayResults) {
        if (graphicsOverlayResults == null) {
            return;
        }
        if (graphicsOverlayResults.size() == 0) {
            return;
        }
        MapUtil.addSingelLayer(mapMv, tempGraphicsLayer).getGraphics().clear();
        Gson gson = GsonUtils.gsonBuilder.create();
        //for (int i = 0; i < graphicsOverlayResults.size(); i++) {
        List<Graphic> graphics = graphicsOverlayResults.get(0).getGraphics();
        if (graphics != null && graphics.size() > 0 && ObjectUtils.isNotEmpty(graphics.get(0))
                && ObjectUtils.isNotEmpty(graphics.get(0).getAttributes())) {
            Geometry geo = graphics.get(0).getGeometry();
            if (geo.getGeometryType() != GeometryType.POINT) {
                // 不为点
                MapUtil.addSingelLayer(mapMv, tempGraphicsLayer).getGraphics().add(SymbolsMgr.highlightGraphic(geo));
            } else {
                Point point = new Point(geo.getExtent().getCenter().getX(), geo.getExtent().getYMin());
                MapUtil.addPointLayer(mapMv, tempGraphicsLayer, getActivity(), R.mipmap.icon_gps_point, point, false);
            }
            Map<String, Object> map = graphics.get(0).getAttributes();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                mapTouchListener.isOnlineLayer = false;
                switch (entry.getKey()) {
                    // 类型 1、管线 2、隐患 3、人员 4、计划 5、工地 6、道路 7、车辆
                    case MapConstant.GD_ATTR_NAME:
                        String gdMsgJson = entry.getValue().toString();
                        gdBean = gson.fromJson(gdMsgJson, SiteBean.class);
                        showMapLine(true, 5);
                        break;
                    case MapConstant.PEOPLE_ATTR_NAME:
                        String strJson = entry.getValue().toString();
                        personBean = gson.fromJson(strJson, UserLayerBean.class);
                        showMapLine(true, 3);
                        break;
                    case MapConstant.HD_ATTR_NAME:
                        String hdJson = entry.getValue().toString();
                        hdBean = gson.fromJson(hdJson, HdOrderBean.class);
                        showMapLine(true, 2);
                        break;
                    case MapConstant.PLAN_ATTR_NAME:
                        String planJson = entry.getValue().toString();
                        planBean = gson.fromJson(planJson, PatrolTaskResultBean.class);
                        showMapLine(true, 4);
                        break;
                    case MapConstant.CAR_ATTR_NAME:
                        // todo
                        String carJson = entry.getValue().toString();
                        carBean = gson.fromJson(carJson, CarBean.class);
                        showMapLine(true, 7);
                        break;
                    default:
                        showMapLine(false, type);
                        break;
                }
            }
            //}
        }
    }

    @Override
    public void getOnlineLayerSuccess(FeatureQueryResult featureQueryResult) {
        if (featureQueryResult == null || !featureQueryResult.iterator().hasNext()) {
            return;
        }
        // 清除地图图标
        MapUtil.addSingelLayer(mapMv, tempGraphicsLayer).getGraphics().clear();
        Geometry geo = GeometryEngine.project(featureQueryResult.iterator().next().getGeometry(), reference);
        MapUtil.addSingelLayer(mapMv, tempGraphicsLayer).getGraphics().add(SymbolsMgr.highlightGraphic(geo));
        String mapStr = GsonUtils.gsonBuilder.create().toJson(MapUtil.convertMap(featureQueryResult));

        if (!"唐昌".equals(Constant.appCompany)) {
            // 非唐昌域
            if (pattern == null) {
                pattern = compile("\\{\"year\":([0-9]{4}),\"month\":([0-9]{1,2}),\"dayOfMonth\":([0-9]{1,2}),\"hourOfDay\":([0-9]{1,2}),\"minute\":([0-9]{1,2}),\"second\":([0-9]{1,2})\\}");
            }
            Matcher m = pattern.matcher(mapStr);
            while (m.find()) {
                int moth = Integer.valueOf(m.group(2)) + 1;
                String str = m.group(1) + "-" + moth + "-" + m.group(3) + " " + m.group(4) + ":" + m.group(5) + ":" + m.group(6);
                mapStr = mapStr.replace(m.group(), "\"" + str + "\"");
            }
        }
        if (StringUtils.isTrimEmpty(mapStr)) {
            ToastBuilder.showShortWarning("管线参数获取失败");
            return;
        }
        this.mapStr = mapStr;
        geoStr = geo.toJson();
        mapBeans = ConvertorDataUtils.json2Map(mapStr);
        Point currentPoint = mapMv.getLocationDisplay().getMapLocation();
        if (geo.getGeometryType() == GeometryType.POINT) {
            // 点类型
            geometryType = 0;
            GisPointCoordinateBean coordinateBean = GsonUtils.gsonBuilder.create().fromJson(geoStr, GisPointCoordinateBean.class);
            dis = GpsUtils.getDistance(currentPoint.getY(), currentPoint.getX(),
                    coordinateBean.getY(), coordinateBean.getX());
            navX = coordinateBean.getY();
            navY = coordinateBean.getX();
        } else {
            // 其他类型（坐标集合）
            geometryType = 1;
            GisOtherCoordinateBean coordinateBean = GsonUtils.gsonBuilder.create().fromJson(geoStr, GisOtherCoordinateBean.class);
            if (ObjectUtils.isNotEmpty(coordinateBean.getPaths())
                    && coordinateBean.getPaths().size() > 0) {
                navY = coordinateBean.getPaths().get(0).get(0).get(0);
                navX = coordinateBean.getPaths().get(0).get(0).get(1);
                List<GpsBean> gpsBeans = new ArrayList<>();
                GpsBean bean;
                for (int i = 0; i < coordinateBean.getPaths().get(0).size(); i++) {
                    bean = new GpsBean(String.valueOf(coordinateBean.getPaths().get(0).get(i).get(1)),
                            String.valueOf(coordinateBean.getPaths().get(0).get(i).get(0)));
                    gpsBeans.add(bean);
                    double minDis = Math.sqrt(Math.pow(coordinateBean.getPaths().get(0).get(i).get(1) - currentPoint.getY(), 2) +
                            Math.pow(coordinateBean.getPaths().get(0).get(i).get(0) - currentPoint.getX(), 2));
                    if (i == 0) {
                        dis = minDis;
                    } else if (minDis < dis) {
                        dis = minDis;
                    }
                }
                dis = Double.parseDouble(com.cdqj.cdqjpatrolandroid.util.OtherUtil.formatDouble(dis));
                LogUtils.e(currentPoint.getY() + "--->" + currentPoint.getX());
                LogUtils.e("--->" + gpsBeans.toString());
                LogUtils.e("--->" + dis);
//                dis = GpsUtils.getMinDistance(currentPoint.getY(), currentPoint.getX(), gpsBeans);
            } else {
                dis = 0.0;
            }
        }
        if (ObjectUtils.isEmpty(mapBeans)/* || (ObjectUtils.isNotEmpty(mapBeans) && ObjectUtils.isEmpty(mapBeans.get("类型")))*/) {
            ToastBuilder.showShortWarning("管线类型未声明，请联系管理员");
            return;
        }
        showMapLine(true, 1);
    }

    @Override
    public void getOnlineLayerSuccess(FeatureQueryResult featureQueryResult, Point ptClick) {

    }

    /**
     * 线路规划，在地图上添加起点和终点并显示是否进行导航的提示
     *
     * @param starPt starPt
     * @param endPt  endPt
     */
    @SuppressLint("WrongConstant")
    private void addStarEndOnMap(Point starPt, Point endPt) {
        if (ObjectUtils.isEmpty(starPt)) {
            ToastBuilder.showShortWarning("未获取到起点");
            return;
        }
        MapUtil.addPointLayer(mapMv, tempGraphicsLayer, getActivity(), R.mipmap.icon_route_start, new Point[]{starPt}, false);
        MapUtil.addPointLayer(mapMv, tempGraphicsLayer, getActivity(), R.mipmap.icon_route_end, new Point[]{endPt}, false);
        NavigationUtil.showNavWindow(endPt.getY(), endPt.getX(), getActivity());
    }

    /**
     * 显示上报弹窗
     */
    @SuppressLint("InflateParams")
    private void showUpdateWindow(Point endPt, boolean isMapPoint) {
        boolean iswithin = false;
        if (mMapGridBeans.isEmpty()) {
            ToastBuilder.showShort("数据正在加载，请稍后再试");
            return;
        }
        for (int i = 0; i < mMapGridBeans.size(); i++) {
            MapGridBean mapGridBean = mMapGridBeans.get(i);
            if (!StringUtils.isTrimEmpty(mapGridBean.getGridAreas())) {
                String[] split = mapGridBean.getGridAreas().split(" ");
                ArrayList<Point> points = new ArrayList<>();
                for (String aSplit : split) {
                    String[] split1 = aSplit.split(",");
                    Point point = new Point(Double.parseDouble(split1[0]), Double.parseDouble(split1[1]));
                    points.add(point);
                }
                if (GpsUtils.isWithIn(endPt, points)) {
                    iswithin = true;
                    gridNum = mapGridBean.getId();
                    break;
                }
            }
        }
        if (!iswithin) {
            ToastBuilder.showShortWarning("上报点不在网格片区范围");
            return;
        }
        // 上报弹窗底部
        updateFooter = LayoutInflater.from(getActivity()).inflate(R.layout.cdqj_patrol_map_update_window_footer, null);
        // 初始化工具栏数据
        List<CustomGridPicWindowBean> updateType = new ArrayList<>();
        updateType.add(new CustomGridPicWindowBean(R.mipmap.icon_danger, "隐患"));
        updateType.add(new CustomGridPicWindowBean(R.mipmap.icon_site, "工地"));
        updateType.add(new CustomGridPicWindowBean(R.mipmap.icon_query_point, "巡检点"));
        updateType.add(new CustomGridPicWindowBean(R.mipmap.icon_equipmenterror, "设备纠错"));
        MapSlidingGridAdapter mUpdateAdapter = new MapSlidingGridAdapter(getActivity(), updateType);
        mDialogPlus = DialogPlus.newDialog(Objects.requireNonNull(getActivity()))
                .setContentHolder(new GridHolder(4))
                .setPadding(ConvertUtils.dp2px(12), ConvertUtils.dp2px(20),
                        ConvertUtils.dp2px(12), ConvertUtils.dp2px(20))
                .setGravity(Gravity.BOTTOM)
                .setFooter(updateFooter)
                .setCancelable(false)
                .setContentBackgroundResource(R.drawable.stroke_bg_radius_top)
                .setAdapter(mUpdateAdapter)
                .setInAnimation(R.anim.slide_in_bottom)
                .setOutAnimation(R.anim.slide_out_bottom)
                .setOnItemClickListener((dialog, item, view, position) -> {
                    if (position == 0 || position == 3) {
                        if (ObjectUtils.isNotEmpty(mapBeans) && /*ObjectUtils.isEmpty(mapBeans.get("ppid")) || */ObjectUtils.isEmpty(mapBeans.get("OBJECTID"))) {
                            ToastBuilder.showShortWarning("管线设备基础参数为空，请联系管理人员");
                            dialog.dismiss();
                            return;
                        }
                    }
                    dialog.dismiss();
                    showReportFirst(endPt, position, isMapPoint);
                })
                .setOnClickListener((dialog, view) -> {
                    int i = view.getId();
                    if (i == R.id.update_window_footer_img) {
                        dialog.dismiss();

                    }
                })
                .create();
        mDialogPlus.show();
    }

    /**
     * 显示地图属性弹窗
     *
     * @param flag 是否展示标识
     * @param type 类型 1、管线 2、隐患 3、人员 4、计划 5、工地 6、道路 7、车辆
     */
    @SuppressLint("SetTextI18n")
    private void showMapLine(boolean flag, int type) {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        this.type = type;
        Point currentPoint = mapMv.getLocationDisplay().getMapLocation();
        if (ObjectUtils.isNotEmpty(roundBean) && type == 6) {
            String[] points = roundBean.getLocation().split(" ");
            dis = GpsUtils.getDistance(currentPoint.getY(), currentPoint.getX(),
                    Double.valueOf(points[1]), Double.valueOf(points[0]));
            mapLineDialogTitle.setText(StringUtils.isTrimEmpty(roundBean.getName()) ?
                    Objects.requireNonNull(getActivity()).getString(R.string.null_text) : roundBean.getName());
            mapLineDialogAddress.setText(StringUtils.isTrimEmpty(roundBean.getAddress()) ?
                    getString(R.string.null_text) : roundBean.getAddress());
            mapLineDialogDistance.setText(dis == 0.0 ? Objects.requireNonNull(getActivity()).getString(R.string.null_text) : "距离" + dis + "米");
            navY = Double.valueOf(points[0]);
            navX = Double.valueOf(points[1]);
            mapLineDialogReport.setVisibility(View.INVISIBLE);
            mapLineDialogDetail.setVisibility(View.INVISIBLE);
        } else {
            mapLineDialogDetail.setVisibility(View.VISIBLE);
        }
        if (ObjectUtils.isNotEmpty(mapBeans) && type == 1) {
            mapLineDialogTitle.setText(DataUtils.getLineTypeStr(mapBeans.get("类型"), realm));
            mapLineDialogAddress.setText(ObjectUtils.isNotEmpty(mapBeans.get("地址")) ? mapBeans.get("地址").toString() : ObjectUtils.isNotEmpty(mapBeans.get("安装地址")) ? mapBeans.get("安装地址").toString() : Objects.requireNonNull(getActivity()).getString(R.string.null_text));
            mapLineDialogDistance.setText(dis == 0.0 ? Objects.requireNonNull(getActivity()).getString(R.string.null_text) : "距离" + dis + "米");
            mapLineDialogReport.setVisibility(View.VISIBLE);
            deviceBean = null;
        }
        if (ObjectUtils.isNotEmpty(carBean) && type == 7) {
            String[] points = carBean.getGisArea().split(" ");
            dis = GpsUtils.getDistance(currentPoint.getY(), currentPoint.getX(),
                    Double.valueOf(points[1]), Double.valueOf(points[0]));
            mapLineDialogTitle.setText(carBean.getName());
            mapLineDialogAddress.setText(carBean.getRemarks());
            mapLineDialogDistance.setText(dis == 0.0 ? Objects.requireNonNull(getActivity()).getString(R.string.null_text) : "距离" + dis + "米");
            mapLineDialogReport.setVisibility(View.VISIBLE);
        }
        if (ObjectUtils.isNotEmpty(hdBean) && type == 2) {
            // 隐患
            if (!StringUtils.isTrimEmpty(hdBean.getLon()) && !StringUtils.isTrimEmpty(hdBean.getLat())) {
                dis = GpsUtils.getDistance(currentPoint.getY(), currentPoint.getX(), Double.valueOf(hdBean.getLat()), Double.valueOf(hdBean.getLon()));
                navY = Double.valueOf(hdBean.getLon());
                navX = Double.valueOf(hdBean.getLat());
            } else {
                dis = 0.0;
            }
            mapHdDialogTitle.setText(StringUtils.isTrimEmpty(hdBean.getHdTypeExp()) ?
                    Objects.requireNonNull(getActivity()).getString(R.string.null_text) : hdBean.getHdTypeExp());
            mapHdDialogAddress.setText(StringUtils.isTrimEmpty(hdBean.getHdAddress()) ?
                    getActivity().getString(R.string.null_text) : hdBean.getHdAddress());
            mapHdDialogLevel.setText(StringUtils.isTrimEmpty(hdBean.getHdGrandExp()) ?
                    getActivity().getString(R.string.null_text) : hdBean.getHdGrandExp());
            mapHdDialogUpdate.setText(hdBean.getLastReportUserName() + "于" + hdBean.getLastReportTime() + "上报");
            mapHdDialogDistance.setText(dis == 0.0 ? Objects.requireNonNull(getActivity()).getString(R.string.null_text) : "距离" + dis + "米");
            GlideImgManager.loadRoundCornerImage(getActivity(), StringUrlUtil.transHttpUrlAndOnlyOne(hdBean.getHdPicture()), mapHdDialogImg, 5);
            mapLineDialogReport.setVisibility("1".equals(hdBean.getIsCanReport()) ? View.VISIBLE : View.GONE);
        }
        if (ObjectUtils.isNotEmpty(personBean) && type == 3) {
            // 人员
            mapLineDialogTitle.setText(StringUtils.isTrimEmpty(personBean.getSysStaffName()) ?
                    getString(R.string.null_text) : personBean.getSysStaffName());
            mapLineDialogAddress.setText(StringUtils.isTrimEmpty(personBean.getGroupName()) ?
                    getString(R.string.null_text) : personBean.getGroupName());
            mapLineDialogDistance.setText("最后上报时间：" + personBean.getLastReportTime());
            navY = personBean.getLastReportLon();
            navX = personBean.getLastReportLat();
            mapLineDialogReport.setVisibility(View.GONE);
        }
        if (ObjectUtils.isNotEmpty(planBean) && type == 4) {
            // 计划
            mapLineDialogTitle.setText(StringUtils.isTrimEmpty(planBean.getCheckName()) ?
                    getString(R.string.null_text) : planBean.getCheckName());
            mapLineDialogAddress.setText(StringUtils.isTrimEmpty(planBean.getAddress()) ?
                    getString(R.string.null_text) : planBean.getAddress());
            mapLineDialogDistance.setText("最后上报时间：" + (StringUtils.isTrimEmpty(planBean.getLastReportTime()) ?
                    getString(R.string.null_text) : planBean.getLastReportTime()));
            navY = planBean.getLon();
            navX = planBean.getLat();
            mapLineDialogReport.setVisibility(View.VISIBLE);
        }
        if (ObjectUtils.isNotEmpty(gdBean) && type == 5) {
            // 工地
            dis = GpsUtils.getDistance(currentPoint.getY(), currentPoint.getX(), gdBean.getLat(), gdBean.getLon());
            mapHdDialogTitle.setText(StringUtils.isTrimEmpty(gdBean.getName()) ?
                    Objects.requireNonNull(getActivity()).getString(R.string.null_text) : gdBean.getName());
            mapHdDialogAddress.setText(StringUtils.isTrimEmpty(gdBean.getAddress()) ?
                    getActivity().getString(R.string.null_text) : gdBean.getAddress());
            mapHdDialogLevel.setText(StringUtils.isTrimEmpty(gdBean.getSiteLevelExp()) ?
                    getActivity().getString(R.string.null_text) : gdBean.getSiteLevelExp());
            mapHdDialogUpdate.setText(gdBean.getAddUserName() + "于" + gdBean.getAddTime() + "上报");
            mapHdDialogDistance.setText("距离" + dis + "米");
            GlideImgManager.loadRoundCornerImage(getActivity(), StringUrlUtil.transHttpUrlAndOnlyOne(gdBean.getPicture()), mapHdDialogImg, 5);
            navY = gdBean.getLon();
            navX = gdBean.getLat();
            mapLineDialogReport.setVisibility("1".equals(gdBean.getIsCanReport()) ? View.VISIBLE : View.GONE);
        }
        if (flag) {
            if (type == 1 || type == 3 || type == 4 || type == 6 || type == 7) {
                if (ObjectUtils.isNotEmpty(mapLineInfo.getViewById(viewHd.getId()))) {
                    mapLineInfo.removeView(viewHd);
                }
                if (ObjectUtils.isEmpty(mapLineInfo.getViewById(viewLine.getId()))) {
                    viewLine.setLayoutParams(new ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.MATCH_PARENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT));
                    mapLineInfo.addView(viewLine);
                }
            }
            if (type == 2 || type == 5) {
                if (ObjectUtils.isNotEmpty(mapLineInfo.getViewById(viewLine.getId()))) {
                    mapLineInfo.removeView(viewLine);
                }
                if (ObjectUtils.isEmpty(mapLineInfo.getViewById(viewHd.getId()))) {
                    viewHd.setLayoutParams(new ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.MATCH_PARENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT));
                    mapLineInfo.addView(viewHd);
                }
            }
        }
        if (mapLineDialogView.getVisibility() == View.VISIBLE) {
            AnimUtils.setAnima(Techniques.FadeOutDown, 200, mapLineDialogView, flag);
            if (flag) {
                // 如果点击的是其他地图属性则重新弹窗
                AnimUtils.setAnima(Techniques.FadeInUp, 200, mapLineDialogView, true);
            }
        } else {
            if (flag) {
                AnimUtils.setAnima(Techniques.FadeInUp, 200, mapLineDialogView, true);
            }
        }
    }

    /**
     * 上报填写信息第一个弹窗
     *
     * @param endPt    endPt
     * @param position position
     */
    @SuppressLint("SetTextI18n")
    private void showReportFirst(Point endPt, int position, boolean isMapPoint) {
        switch (position) {
            case 1:
                mapUpdateSiteWindow.setVisibility(View.VISIBLE);
                mapUpdatePointWindow.setVisibility(View.GONE);
                mapUpdateDevWindow.setVisibility(View.GONE);
                mapUpdateHdWindow.setVisibility(View.GONE);
                break;
            case 2:
                mapUpdatePointWindow.setVisibility(View.VISIBLE);
                mapUpdateSiteWindow.setVisibility(View.GONE);
                mapUpdateDevWindow.setVisibility(View.GONE);
                mapUpdateHdWindow.setVisibility(View.GONE);
                break;
            case 3:
                mapUpdateDevWindow.setVisibility(View.VISIBLE);
                mapUpdatePointWindow.setVisibility(View.GONE);
                mapUpdateSiteWindow.setVisibility(View.GONE);
                mapUpdateHdWindow.setVisibility(View.GONE);
                break;
            default:
                mapUpdateHdWindow.setVisibility(View.VISIBLE);
                mapUpdatePointWindow.setVisibility(View.GONE);
                mapUpdateDevWindow.setVisibility(View.GONE);
                mapUpdateSiteWindow.setVisibility(View.GONE);
                break;
        }
        this.position = position;

        if (postResponse == null) {
            mPresenter.getCombobox();
        }
        //小
        String submitLat;
        //大
        String submitLon;
        if (isMapPoint) {
            devName = "";
            submitLon = GpsUtils.formateRate(endPt.getX() + "");
            submitLat = GpsUtils.formateRate(endPt.getY() + "");
        } else {
            devName = mapLineDialogTitle.getText().toString();
            submitLat = GpsUtils.formateRate(navX + "");
            submitLon = GpsUtils.formateRate(navY + "");
        }
        switch (position) {
            case 0:
                hdSubmit.put("lat", submitLat);
                hdSubmit.put("lon", submitLon);
                if (!isMapPoint) {
                    for (int i = 0; i < hdDevice.size(); i++) {
                        if (devName.endsWith(hdDevice.get(i).getDicName())) {
                            updateHdDx.setText(hdDevice.get(i).getDicName());
                        }
                    }
                    if (ObjectUtils.isNotEmpty(mapBeans.get("PPID"))) {
                        hdSubmit.put("ppid", mapBeans.get("PPID").toString());
                    }
                    hdSubmit.put("objectid", mapBeans.get("OBJECTID").toString());
                    LogUtils.e(endPt.getX() + "--" + endPt.getY());
                }
                RetrofitUtils.getAddressByPoint(new Point(Double.parseDouble(submitLat), Double.parseDouble(submitLon)), updateHdAddress);
                updateHdGps.setText(submitLon + "," + submitLat);
                break;
            case 1:
                siteSubmit.put("lat", submitLat);
                siteSubmit.put("lon", submitLon);
                RetrofitUtils.getAddressByPoint(new Point(Double.parseDouble(submitLat), Double.parseDouble(submitLon)), updateSiteAddress);
                updateSiteGps.setText(submitLon + "," + submitLat);
                break;
            case 2:
                pointSubmit.put("lat", submitLat);
                pointSubmit.put("lon", submitLon);
                RetrofitUtils.getAddressByPoint(new Point(Double.parseDouble(submitLat), Double.parseDouble(submitLon)), updatePointAddress);
                updatePointGps.setText(submitLon + "," + submitLat);
                break;
            case 3:
                updateDevGpsx.setText(submitLat);
                updateDevGpsy.setText(submitLon);
                if (TextUtils.isEmpty(devName)) {
                    updateDevType.setEnabled(true);
                    updateDevGpsx.setEnabled(false);
                    updateDevGpsy.setEnabled(false);
                    devSubmit.put("nowLat", submitLat);
                    devSubmit.put("nowLon", submitLon);
                } else {
                    updateDevType.setEnabled(false);
                    updateDevGpsx.setEnabled(true);
                    updateDevGpsy.setEnabled(true);
                    devSubmit.put("originalLat", submitLat);
                    devSubmit.put("originalLon", submitLon);
                }
                updateDevType.setText(devName);
                break;
            default:
                break;
        }
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    /**
     * 上报文件
     *
     * @param position 0隐患|1工地
     */
    private void showReportWindow(final int position) {
        title.setText(position == 0 ? "隐患上报" : "工地上报");
        etRemark.setVisibility(View.GONE);
//        rvAudio.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
//        rvVideo.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
//        videoTitle.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
//        voiceTitle.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
        mDialogPlus = DialogPlus.newDialog(Objects.requireNonNull(getActivity()))
                .setContentHolder(new ViewHolder(fileView))
                .setContentHeight(ScreenUtils.getScreenHeight() / 3 * 2)
                .setMargin(ConvertUtils.dp2px(10), 0, ConvertUtils.dp2px(10), 0)
                .setPadding(ConvertUtils.dp2px(10), ConvertUtils.dp2px(10),
                        ConvertUtils.dp2px(10), ConvertUtils.dp2px(10))
                .setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL)
                .setContentBackgroundResource(R.drawable.stroke_bg_radius_top)
                .setInAnimation(R.anim.slide_in_bottom)
                .setOutAnimation(R.anim.slide_out_bottom)
                .setOnClickListener((dialog, view) -> {
                    int i1 = view.getId();
                    if (i1 == R.id.supervise_report_ecs) {
                        dialog.dismiss();

                    } else if (i1 == R.id.supervise_report_submit) {
                        if (selectPictureList.isEmpty() && selectAudioList.isEmpty() && selectVideoList.isEmpty()) {
                            ToastBuilder.showShort("请至少添加一种文件");
                            return;
                        }
                        showProgress("文件上传中...");
                        pictureResult.clear();
                        audioResult.clear();
                        videoResult.clear();
                        if (position == 0) {
                            // 隐患 提交
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
                                mPresenter.upLoadFile(pics.toString(), Constant.IMG_TYPE, position);
                            }
                            for (int i = 0; i < selectAudioList.size(); i++) {
                                audios.append(selectAudioList.get(i).getPath());
                                if (i != selectAudioList.size() - 1) {
                                    audios.append(",");

                                }
                            }
                            if (!StringUtils.isTrimEmpty(audios.toString())) {
                                mPresenter.upLoadFile(audios.toString(), Constant.AUDIO_TYPE, position);
                            }
                            for (int i = 0; i < selectVideoList.size(); i++) {
                                videos.append(selectVideoList.get(i).getPath());
                                if (i != selectVideoList.size() - 1) {
                                    videos.append(",");

                                }
                            }
                            if (!StringUtils.isTrimEmpty(videos.toString())) {
                                mPresenter.upLoadFile(videos.toString(), Constant.VIDEO_TYPE, position);
                            }
                        } else if (position == 1) {
                            // 工地 提交
                            siteSubmit.put("siteLeader", updateSiteSiteLeader.getText().toString());
                            siteSubmit.put("siteLeaderTel", updateSiteSiteLeaderTel.getText().toString());
                            siteSubmit.put("otherType", updateSiteOtherType.getText().toString());
                            siteSubmit.put("propGdssqk", updateSitePropGdssqk.getText().toString());
                            siteSubmit.put("propGdmsqk", updateSitePropGdmsqk.getText().toString());
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
                                mPresenter.upLoadFile(pics.toString(), Constant.IMG_TYPE, position);
                            }
                            for (int i = 0; i < selectAudioList.size(); i++) {
                                audios.append(selectAudioList.get(i).getPath());
                                if (i != selectAudioList.size() - 1) {
                                    audios.append(",");

                                }
                            }
                            if (!StringUtils.isTrimEmpty(audios.toString())) {
                                mPresenter.upLoadFile(audios.toString(), Constant.AUDIO_TYPE, position);
                            }
                            for (int i = 0; i < selectVideoList.size(); i++) {
                                videos.append(selectVideoList.get(i).getPath());
                                if (i != selectVideoList.size() - 1) {
                                    videos.append(",");

                                }
                            }
                            if (!StringUtils.isTrimEmpty(videos.toString())) {
                                mPresenter.upLoadFile(videos.toString(), Constant.VIDEO_TYPE, position);
                            }
                        }

                    }
                })
                .create();
        setReportWindow();
        mDialogPlus.show();
    }

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

    /**
     * 上报文件
     *
     * @param type 类型 1、管线 2、隐患 3、人员 4、计划 5、工地
     */
    private void showReportLayer(final int type) {
        // 类型 1、管线 2、隐患 3、人员 4、计划 5、工地
        etRemark.setVisibility(View.VISIBLE);
        title.setText(type == 2 ? "隐患上报" : type == 4 ? "计划上报" : "工地上报");
        mDialogPlus = DialogPlus.newDialog(Objects.requireNonNull(getActivity()))
                .setContentHolder(new ViewHolder(fileView))
                .setContentHeight(ScreenUtils.getScreenHeight() / 3 * 2)
                .setMargin(ConvertUtils.dp2px(10), 0, ConvertUtils.dp2px(10), 0)
                .setPadding(ConvertUtils.dp2px(10), ConvertUtils.dp2px(10),
                        ConvertUtils.dp2px(10), ConvertUtils.dp2px(10))
                .setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL)
                .setContentBackgroundResource(R.drawable.stroke_bg_radius_top)
                .setInAnimation(R.anim.slide_in_bottom)
                .setOutAnimation(R.anim.slide_out_bottom)
                .setOnClickListener((dialog, view) -> {
                    int i1 = view.getId();
                    if (i1 == R.id.supervise_report_ecs) {
                        dialog.dismiss();

                    } else if (i1 == R.id.supervise_report_submit) {
                        if (selectPictureList.isEmpty() && selectAudioList.isEmpty() && selectVideoList.isEmpty()) {
                            ToastBuilder.showShort("请至少添加一种文件");
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
                        for (int i = 0; i < selectAudioList.size(); i++) {
                            audios.append(selectAudioList.get(i).getPath());
                            if (i != selectAudioList.size() - 1) {
                                audios.append(",");

                            }
                        }
                        for (int i = 0; i < selectVideoList.size(); i++) {
                            videos.append(selectVideoList.get(i).getPath());
                            if (i != selectVideoList.size() - 1) {
                                videos.append(",");

                            }
                        }
                        if (type == 2) {
                            // 隐患 提交
                            if (!StringUtils.isTrimEmpty(pics.toString())) {
                                mPresenter.upLoadFile(pics.toString(), Constant.IMG_TYPE, 3);
                            }
                            if (!StringUtils.isTrimEmpty(audios.toString())) {
                                mPresenter.upLoadFile(audios.toString(), Constant.AUDIO_TYPE, 3);
                            }
                            if (!StringUtils.isTrimEmpty(videos.toString())) {
                                mPresenter.upLoadFile(videos.toString(), Constant.VIDEO_TYPE, 3);
                            }
                        } else if (type == 4) {
                            // 计划 提交
                            if (!StringUtils.isTrimEmpty(pics.toString())) {
                                mPresenter.upLoadFile(pics.toString(), Constant.IMG_TYPE, 4);
                            }
                            if (!StringUtils.isTrimEmpty(audios.toString())) {
                                mPresenter.upLoadFile(audios.toString(), Constant.AUDIO_TYPE, 4);
                            }
                            if (!StringUtils.isTrimEmpty(videos.toString())) {
                                mPresenter.upLoadFile(videos.toString(), Constant.VIDEO_TYPE, 4);
                            }
                        } else if (type == 5) {
                            // 工地 提交
                            if (!StringUtils.isTrimEmpty(pics.toString())) {
                                mPresenter.upLoadFile(pics.toString(), Constant.IMG_TYPE, 5);
                            }
                            if (!StringUtils.isTrimEmpty(audios.toString())) {
                                mPresenter.upLoadFile(audios.toString(), Constant.AUDIO_TYPE, 5);
                            }
                            if (!StringUtils.isTrimEmpty(videos.toString())) {
                                mPresenter.upLoadFile(videos.toString(), Constant.VIDEO_TYPE, 5);
                            }
                        }

                    }
                })
                .create();
        setReportWindow();
        mDialogPlus.show();
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
        hideProgress();
        ToastBuilder.showShortError(msg.message);
    }

    @Override
    public void onFailure(String e) {
        hideProgress();
    }

    @Override
    public void onUpdateFileSuccess(BasePostResponse<List<UpImgResultBean>> beans, int type, int position) {
        if (beans.isSuccess()) {
            switch (type) {
                case Constant.IMG_TYPE:
                    pictureResult.addAll(beans.getObj());
                    break;
                case Constant.AUDIO_TYPE:
                    audioResult.addAll(beans.getObj());
                    break;
                case Constant.VIDEO_TYPE:
                    videoResult.addAll(beans.getObj());
                    break;
                default:
                    break;
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
                if (position == 0) {
                    if (reportPicture.length() > 0) {
                        hdSubmit.put("hdPicture", reportPicture.substring(0, reportPicture.length() - 1));
                    }
                    hdSubmit.put("hdAddress", updateHdAddress.getText().toString());
                    hdSubmit.put("hdDescript", updateHdRemark.getText().toString());
                    hdSubmit.put("hdGrid", gridNum);
                    hdSubmit.put("addUserLat", PreferencesUtil.getString(Constant.LOCATION_LATITUDE));
                    hdSubmit.put("addUserLon", PreferencesUtil.getString(Constant.LOCATION_LONGITUDE));
                    LogUtils.e(hdSubmit.toString());
                    mPresenter.hdSubmit(hdSubmit);
                } else if (position == 1) {
                    if (reportPicture.length() > 0) {
                        siteSubmit.put("picture", reportPicture.substring(0, reportPicture.length() - 1));
                    }
                    if (reportVoice.length() > 0) {
                        siteSubmit.put("voice", reportVoice.substring(0, reportVoice.length() - 1));
                    }
                    if (reportVideo.length() > 0) {
                        siteSubmit.put("video", reportVideo.substring(0, reportVideo.length() - 1));
                    }
                    siteSubmit.put("grid", gridNum);
                    siteSubmit.put("address", updateSiteAddress.getText().toString());
                    siteSubmit.put("name", updateSiteName.getText().toString());
                    siteSubmit.put("content", updateSiteRemark.getText().toString());
                    siteSubmit.put("addUserLat", PreferencesUtil.getString(Constant.LOCATION_LATITUDE));
                    siteSubmit.put("addUserLon", PreferencesUtil.getString(Constant.LOCATION_LONGITUDE));
                    mPresenter.siteSubmit(siteSubmit);
                } else if (position == 3) {
                    // 隐患|工单
                    HashMap<String, String> map = new HashMap<>();
                    map.put("orderId", hdBean.getOrderId());
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
                    mPresenter.addUpdate(map);
                } else if (position == 4) {
                    // 计划
                    HashMap<String, String> map = new HashMap<>(7);
                    map.put("taskResultId", planBean.getId() + "");
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
                } else if (position == 5) {
                    // 工地
                    HashMap<String, String> map = new HashMap<>(5);
                    map.put("orderId", gdBean.getOrderId());
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
                    mPresenter.addUpdate(map);
                }
            }
        } else {
            switch (type) {
                case Constant.IMG_TYPE:
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
                        mPresenter.upLoadFile(pics.toString(), Constant.IMG_TYPE, position);
                    }
                    break;
                case Constant.AUDIO_TYPE:
                    StringBuilder audios = new StringBuilder();
                    for (int i = 0; i < selectAudioList.size(); i++) {
                        audios.append(selectAudioList.get(i).getPath());
                        if (i != selectAudioList.size() - 1) {
                            audios.append(",");

                        }
                    }
                    if (!StringUtils.isTrimEmpty(audios.toString())) {
                        mPresenter.upLoadFile(audios.toString(), Constant.AUDIO_TYPE, position);
                    }
                    break;
                case Constant.VIDEO_TYPE:
                    StringBuilder videos = new StringBuilder();
                    for (int i = 0; i < selectVideoList.size(); i++) {
                        videos.append(selectVideoList.get(i).getPath());
                        if (i != selectVideoList.size() - 1) {
                            videos.append(",");

                        }
                    }
                    if (!StringUtils.isTrimEmpty(videos.toString())) {
                        mPresenter.upLoadFile(videos.toString(), Constant.VIDEO_TYPE, position);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.REQUEST_CODE_IMAGE_CAPTURE:
                    ToastBuilder.showShortWarning("后台处理照片中...");
                    // 拍照工具拍照后处理及保存
                    new Thread(() -> {
                        boolean isSave = PictureProcessingUtil.saveImgLocation(PictureProcessingUtil.drawTextLocation(imgPath));
                        ToastBuilder.showShort(isSave ? "图片已存储至内存卡:" + Constant.APP_DIR + File.separator + "image" : "保存失败，请您重试!");
                    }).start();
                    break;
                default:
                    break;
            }
        }
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
    public void initView(View rootView) {
        Bundle arguments = getArguments();
        assert arguments != null;
        userInfo = arguments.getParcelable("userInfo");
        initMap();
        initView();
        initData();
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
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_fragment_map;
    }

    @Override
    protected void findView(View rootView) {
        mapTab = rootView.findViewById(R.id.map_tab);
        mapLineDialogView = rootView.findViewById(R.id.map_line_dialog_view);
        mapSignal = rootView.findViewById(R.id.map_signal);
        mapSearchText = rootView.findViewById(R.id.map_search_text);
        mapSearchDetailText = rootView.findViewById(R.id.map_search_detail_text);
        mapSearchDetailScan = rootView.findViewById(R.id.map_search_detail_scan);
        mapSearchDetailLine = rootView.findViewById(R.id.map_search_detail_line);
        mapSearchDetailSearch = rootView.findViewById(R.id.map_search_detail_search);
        mapSearchDetailLayout = rootView.findViewById(R.id.map_search_detail_layout);
        mapSearchDetailType = rootView.findViewById(R.id.map_search_detail_type);
        mapSearchDetailList = rootView.findViewById(R.id.map_search_detail_list);
        mapSearchDetail = rootView.findViewById(R.id.map_search_detail);
        mapSearchDetailContent = rootView.findViewById(R.id.map_search_detail_content);
        mapLineDialogDetail = rootView.findViewById(R.id.map_line_dialog_detail);
        mapLineDialogReport = rootView.findViewById(R.id.map_line_dialog_report);
        mapLineDialogNavigation = rootView.findViewById(R.id.map_line_dialog_navigation);
        mapLineInfo = rootView.findViewById(R.id.map_line_dialog_info);
        mapMv = rootView.findViewById(R.id.map_mv);
        mapShowGps = rootView.findViewById(R.id.map_show_gps);
        updateHdDx = rootView.findViewById(R.id.update_hd_dx);
        updateHdType = rootView.findViewById(R.id.update_hd_type);
        updateHdLevel = rootView.findViewById(R.id.update_hd_level);
        updateHdXzqq = rootView.findViewById(R.id.update_hd_xzqq);
        updateHdDtLl = rootView.findViewById(R.id.update_hd_dt_ll);
        updateHdLxTitle = rootView.findViewById(R.id.update_hd_lx_title);
        updateHdLx = rootView.findViewById(R.id.update_hd_lx);
        updateHdFsfsTitle = rootView.findViewById(R.id.update_hd_fsfs_title);
        updateHdFsfs = rootView.findViewById(R.id.update_hd_fsfs);
        updateHdSslbTitle = rootView.findViewById(R.id.update_hd_sslb_title);
        updateHdSslb = rootView.findViewById(R.id.update_hd_sslb);
        updateHdGcTitle = rootView.findViewById(R.id.update_hd_gc_title);
        updateHdGc = rootView.findViewById(R.id.update_hd_gc);
        updateHdGjTitle = rootView.findViewById(R.id.update_hd_gj_title);
        updateHdGj = rootView.findViewById(R.id.update_hd_gj);
        updateHdFfxzTitle = rootView.findViewById(R.id.update_hd_ffxz_title);
        updateHdFfxz = rootView.findViewById(R.id.update_hd_ffxz);
        updateHdLqsbTitle = rootView.findViewById(R.id.update_hd_lqsb_title);
        updateHdLqsb = rootView.findViewById(R.id.update_hd_lqsb);
        updateHdYhlxTitle = rootView.findViewById(R.id.update_hd_yhlx_title);
        updateHdYhlx = rootView.findViewById(R.id.update_hd_yhlx);
        updateHdLzLl = rootView.findViewById(R.id.update_hd_lz_ll);
        updateHdAddressTitle = rootView.findViewById(R.id.update_hd_address_title);
        updateHdAddress = rootView.findViewById(R.id.update_hd_address);
        updateHdGpsTitle = rootView.findViewById(R.id.update_hd_gps_title);
        updateHdGps = rootView.findViewById(R.id.update_hd_gps);
        updateHdRemarkTitle = rootView.findViewById(R.id.update_hd_remark_title);
        updateHdRemark = rootView.findViewById(R.id.update_hd_remark);
        mapUpdateHdWindow = rootView.findViewById(R.id.map_update_hd_window);
        updateSiteEcs = rootView.findViewById(R.id.update_site_ecs);
        updateSiteTitle = rootView.findViewById(R.id.update_site_title);
        updateSiteSubmit = rootView.findViewById(R.id.update_site_submit);
        updateSiteNameTitle = rootView.findViewById(R.id.update_site_name_title);
        updateSiteName = rootView.findViewById(R.id.update_site_name);
        updateSiteTypeTitle = rootView.findViewById(R.id.update_site_type_title);
        updateSiteType = rootView.findViewById(R.id.update_site_type);
        updateSiteXzqyTitle = rootView.findViewById(R.id.update_site_xzqy_title);
        updateSiteXzqy = rootView.findViewById(R.id.update_site_xzqy);
        updateSiteSspqTitle = rootView.findViewById(R.id.update_site_sspq_title);
        updateSiteSspq = rootView.findViewById(R.id.update_site_sspq);
        updateSiteDtLl = rootView.findViewById(R.id.update_site_dt_ll);
        updateSiteAddressTitle = rootView.findViewById(R.id.update_site_address_title);
        updateSiteAddress = rootView.findViewById(R.id.update_site_address);
        updateSiteGpsTitle = rootView.findViewById(R.id.update_site_gps_title);
        updateSiteGps = rootView.findViewById(R.id.update_site_gps);
        updateSiteRemarkTitle = rootView.findViewById(R.id.update_site_remark_title);
        updateSiteRemark = rootView.findViewById(R.id.update_site_remark);
        mapUpdateSiteWindow = rootView.findViewById(R.id.map_update_site_window);
        updateDevEcs = rootView.findViewById(R.id.update_dev_ecs);
        updateDevTitle = rootView.findViewById(R.id.update_dev_title);
        updateDevSubmit = rootView.findViewById(R.id.update_dev_submit);
        updateDevTypeTitle = rootView.findViewById(R.id.update_dev_type_title);
        updateDevType = rootView.findViewById(R.id.update_dev_type);
        updateDevAddressTitle = rootView.findViewById(R.id.update_dev_address_title);
        updateDevAddress = rootView.findViewById(R.id.update_dev_address);
        updateDevGpsxTitle = rootView.findViewById(R.id.update_dev_gpsx_title);
        updateDevGpsx = rootView.findViewById(R.id.update_dev_gpsx);
        updateDevGpsyTitle = rootView.findViewById(R.id.update_dev_gpsy_title);
        updateDevGpsy = rootView.findViewById(R.id.update_dev_gpsy);
        updateDevRemarkTitle = rootView.findViewById(R.id.update_dev_remark_title);
        updateDevRemark = rootView.findViewById(R.id.update_dev_remark);
        mapUpdateDevWindow = rootView.findViewById(R.id.map_update_dev_window);
        updatePointEcs = rootView.findViewById(R.id.update_point_ecs);
        updatePointTitle = rootView.findViewById(R.id.update_point_title);
        updatePointSubmit = rootView.findViewById(R.id.update_point_submit);
        updatePointTypeTitle = rootView.findViewById(R.id.update_point_type_title);
        updatePointType = rootView.findViewById(R.id.update_point_type);
        updatePointAddressTitle = rootView.findViewById(R.id.update_point_address_title);
        updatePointAddress = rootView.findViewById(R.id.update_point_address);
        updatePointGpsTitle = rootView.findViewById(R.id.update_point_gps_title);
        updatePointGps = rootView.findViewById(R.id.update_point_gps);
        updatePointRemarkTitle = rootView.findViewById(R.id.update_point_remark_title);
        updatePointRemark = rootView.findViewById(R.id.update_point_remark);
        mapUpdatePointWindow = rootView.findViewById(R.id.map_update_point_window);
        mapUpdateBottomSheet = rootView.findViewById(R.id.map_update_bottom_sheet);
        mapSearchLayout = rootView.findViewById(R.id.map_search_layout);
        mapMsg = rootView.findViewById(R.id.map_msg);
        mapLayer = rootView.findViewById(R.id.map_layer);
        mapTrack = rootView.findViewById(R.id.map_track);
        mapMeasure = rootView.findViewById(R.id.map_measure);
        mapMore = rootView.findViewById(R.id.map_more);
        mapGps = rootView.findViewById(R.id.map_gps);
        mapUpdate = rootView.findViewById(R.id.map_update);
        mapSearchDetailBack = rootView.findViewById(R.id.map_search_detail_back);
        updateHdEcs = rootView.findViewById(R.id.update_hd_ecs);
        updateHdTitle = rootView.findViewById(R.id.update_hd_title);
        updateHdSubmit = rootView.findViewById(R.id.update_hd_submit);
        updateSiteConTypeTitle = rootView.findViewById(R.id.update_site_conType_title);
        updateSiteConType = rootView.findViewById(R.id.update_site_conType);
        updateSitePropJhyyTitle = rootView.findViewById(R.id.update_site_propJhyy_title);
        updateSitePropJhyy = rootView.findViewById(R.id.update_site_propJhyy);
        updateSiteOtherTypeTitle = rootView.findViewById(R.id.update_site_otherType_title);
        updateSiteOtherType = rootView.findViewById(R.id.update_site_otherType);
        updateSiteSiteLeaderTitle = rootView.findViewById(R.id.update_site_siteLeader_title);
        updateSiteSiteLeader = rootView.findViewById(R.id.update_site_siteLeader);
        updateSiteSiteLeaderTelTitle = rootView.findViewById(R.id.update_site_siteLeaderTel_title);
        updateSiteSiteLeaderTel = rootView.findViewById(R.id.update_site_siteLeaderTel);
        updateSitePropGdssqkTitle = rootView.findViewById(R.id.update_site_propGdssqk_title);
        updateSitePropGdssqk = rootView.findViewById(R.id.update_site_propGdssqk);
        updateSitePropGdmsqkTitle = rootView.findViewById(R.id.update_site_propGdmsqk_title);
        updateSitePropGdmsqk = rootView.findViewById(R.id.update_site_propGdmsqk);
        updateSiteLzLl = rootView.findViewById(R.id.update_site_lz_ll);
    }

    @Override
    protected String getTitleText() {
        return null;
    }

    // -------------- 地图操作 ---------------------//

    /**
     * 初始化地图
     */
    private void initMap() {
        //showProgress("地图管线初始化...");
        //MapUtil.setInitMap(mapMv);
        //ArcGISMap gisMap = new ArcGISMap(new Basemap());
        // mapMv.setMap(gisMap);
        //layerList = gisMap.getBasemap().getBaseLayers();

        getGisParam();


        /*
        mGisParamsBean = new GisParamsBean();
        mGisParamsBean.appPipesOnlineLyr = Constant.GIS_ADDRESS_DEFAULT + "arcgis/rest/services/DT/MapServer";
        mGisParamsBean.appPipesOnlineLyrType = "2";
        mGisParamsBean.appBkOnlineLyr = "http://t1.tianditu.com";
        mGisParamsBean.appBkOnlineLyrType = "6";
        mGisParamsBean.appCenter = "113.297247|40.080772|577791";
        mGisParamsBean.appPipesQueryUrl = Constant.GIS_ADDRESS_DEFAULT + "arcgis/rest/services/DT/MapServer";
        mGisParamsBean.appQueryLyrIndexs = "0|1|2";
        */
    }

    /**
     * 初始化背景图层
     */
    private void initBkLayerTest() {
        if (mapMv != null) {
            //加载天地图底图
/*
            String token = "fd728accfd50a8e43f33fcb520af423b";
            WebTiledLayer[] bkLayers = new TDTLayer(token).getImageLayers();
            ArcGISMap map = new ArcGISMap(new Basemap(bkLayers[0]));
            map.getBasemap().getBaseLayers().add(bkLayers[1]);
            mMapView.setMap(map);
*/
            //加载mbtiles

            String path4 = Environment.getExternalStorageDirectory().getAbsolutePath();
            String path = path4 + File.separator + "qjgis/mbtilesLayer";
            File f = new File(path);
            if (!f.exists()) {
                System.out.println("mbtiles文件不存在.");
                return;
            }
            String fpath = getFile(path, ".mbtiles");
            MBTilesLayer mb2 = new MBTilesLayer(fpath);
            Layer lyr = mb2.getLayer();
            ArcGISMap map = new ArcGISMap(new Basemap(lyr));
            mapMv.setMap(map);

            //String appCenter = "109.8104|39.6119|100000"; //通惠
            //String appCenter = "104.8104|30.6119|100000"; //成都
            String appCenter = "103.8231|30.9262|100000"; //唐昌
            //加载地图范围
            if (!appCenter.isEmpty()) {
                Viewpoint _viewPoint = getViewpointFromParam(appCenter, SpatialReference.create(4326));
                mapMv.setViewpoint(_viewPoint);
            }
        }
    }

    /**
     * 获取指定目录下的文件
     *
     * @param fullPath 目录	: 文件完整路径
     * @param name     文件扩展名: .tpk
     * @return String
     */
    public static String getFile(String fullPath, String name) {
        if (!isNullOrEmpty(fullPath)) {
            File f = new File(fullPath);
            File files[] = f.listFiles(new MyFilter(name));
            if (files != null && files.length > 0) {
                return files[0].getAbsolutePath();
            }
        }
        return "";
    }

    /**
     * 判断字符串是否为空("" 或 null)
     *
     * @param s s
     * @return boolean
     */
    public static boolean isNullOrEmpty(String s) {
        if (s == null) return true;
        return s.isEmpty();
    }

    /**
     * 获取地图参数
     */
    private void getGisParam() {
        RetrofitUtils.getGisParam(getActivity(),
                new IGetGisParameterListener() {
                    @Override
                    public void onFailure(ExceptionHandle.ResponeThrowable e) {
                        getLayerQueryFailed(new Object());
                    }

                    @Override
                    public void onGetGisParam(GisParamsBean bean) {
                        if (ObjectUtils.isNotEmpty(bean)) {
                            mGisParamsBean = bean;
                            /*
                            mGisParamsBean.appPipesOnlineLyr = "http://172.16.126.61:6080/arcgis/rest/services/tc/tc_pipes__app/MapServer";
                            mGisParamsBean.appPipesOnlineLyrType = "2";
                            mGisParamsBean.appBkOnlineLyr = "http://t1.tianditu.com";
                            mGisParamsBean.appBkOnlineLyrType = "6";
                            mGisParamsBean.appBkOfflineLyr = "qjgis/mbtilesLayer";
                            mGisParamsBean.appBkOfflineLyrType = "5";
                            mGisParamsBean.appCenter = "103.8231|30.9262|100000";
                            mGisParamsBean.appPipesQueryUrl = mGisParamsBean.appPipesOnlineLyr;
                            mGisParamsBean.appQueryLyrIndexs = "0|1|2";
                            */

                            if (!StringUtils.isTrimEmpty(mGisParamsBean.appTdtKey)) {
                                Constant.TDT_KEY = mGisParamsBean.appTdtKey;
                            }
                            if (mGisParamsBean.spatialReference != 0) {
                                Constant.SRID_2000 = mGisParamsBean.spatialReference;
                            }

                            mapStatusListener = new MapStatusListener(mapMv, MapFragment.this);
                            mapMv.addLayerViewStateChangedListener(mapStatusListener);

                            initLayers(mGisParamsBean);
                        } else {
                            new ConfirmSelectDialog(getActivity())
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

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        setUpdateDialog();
        realm = Realm.getDefaultInstance();
        bottomSheetBehavior = BottomSheetBehavior.from(mapUpdateBottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        // 折叠状态，bottom sheets只在底部显示一部分布局。显示高度可以通过 app:behavior_peekHeight 设置
                        LogUtils.d("STATE_COLLAPSED");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        // 过渡状态，此时用户正在向上或者向下拖动bottom sheet；
                        LogUtils.d("STATE_DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        // 完全展开的状态
                        LogUtils.d("STATE_EXPANDED");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        // 隐藏状态。默认是false，可通过app:behavior_hideable属性设置是否能隐藏
                        LogUtils.d("STATE_HIDDEN");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        // 视图从脱离手指自由滑动到最终停下的这一小段时间
                        LogUtils.d("STATE_SETTLING");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        setDoMainShow();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        mapUpdateSiteWindow.setOnTouchListener((v, event) -> {
            //canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
            mapUpdateSiteWindow.requestDisallowInterceptTouchEvent(mapUpdateSiteWindow.canScrollVertically(-1));
            return false;
        });
        mapUpdateDevWindow.setOnTouchListener((v, event) -> {
            mapUpdateDevWindow.requestDisallowInterceptTouchEvent(mapUpdateDevWindow.canScrollVertically(-1));
            return false;
        });
        mapUpdatePointWindow.setOnTouchListener((v, event) -> {
            mapUpdatePointWindow.requestDisallowInterceptTouchEvent(mapUpdatePointWindow.canScrollVertically(-1));
            return false;
        });
        mapUpdateHdWindow.setOnTouchListener((v, event) -> {
            mapUpdateHdWindow.requestDisallowInterceptTouchEvent(mapUpdateHdWindow.canScrollVertically(-1));
            return false;
        });

        // 初始化侧滑菜单
        setSliView();

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnSelectIds[i]));
        }
        setTabData();
        if (!CdqjInitDataConfig.isLib && ObjectUtils.isNotEmpty(userInfo) &&
                !AppUtils.getAppVersionName().equals(userInfo.getAppinfos().getVersion())) {
            mapTab.showMsg(mTabEntities.size() - 1, 1);
            mapTab.setMsgMargin(mTabEntities.size() - 1, -ConvertUtils.dp2px(7), ConvertUtils.dp2px(2));
        }
        setSearchView();

        setMapLineInfoDialog();
//        setUpdateDialog();
        mPresenter.getPatrolMapGridList();
        mapSearchDetailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听  输入结束执行该方法 即文本框改变之后 会执行的动作
                searchSubmit(s.toString());
            }
        });
        mapSearchDetailText.setOnEditorActionListener((v, actionId, event) -> {
            /*判断是否是“SEARCH”键*/
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                /*隐藏软键盘*/
                KeyboardUtils.hideSoftInput(Objects.requireNonNull(getActivity()));
                searchSubmit(mapSearchDetailText.getText().toString());
                return true;
            }
            return false;
        });
    }

    @Override
    public void initData() {

        // 初始化工具栏数据
        ArrayList<CustomGridPicWindowBean> mMores = new ArrayList<>();
        mMores.add(new CustomGridPicWindowBean(R.mipmap.icon_layer_track, "跟踪"));
        mMores.add(new CustomGridPicWindowBean(R.mipmap.icon_layer_measure, "测量"));
        mMores.add(new CustomGridPicWindowBean(R.mipmap.icon_layer_measure_to, "全图"));
        mMores.add(new CustomGridPicWindowBean(R.mipmap.icon_layer_gps_to, "坐标"));
        mMores.add(new CustomGridPicWindowBean(R.mipmap.icon_layer_nav, "导航"));
        mMores.add(new CustomGridPicWindowBean(R.mipmap.icon_layer_route, "轨迹"));

        mMores.add(new CustomGridPicWindowBean(R.mipmap.icon_plan_search, "图例"));
        mMores.add(new CustomGridPicWindowBean(R.mipmap.icon_photograph, "拍照"));
        mMores.add(new CustomGridPicWindowBean(R.mipmap.icon_layer_net, "网络"));
        mMoreAdapter = new MapSlidingGridAdapter(getActivity(), mMores);

        // 测试图层数据 TODO -网格片区
        mTestBean = GsonUtils.gsonBuilder.create().fromJson("{\n" +
                        "\t\"code\": \"\",\n" +
                        "\t\"data\": [{\n" +
                        "\t\t\t\"addDate\": \"2016-03-09 10:08:44\",\n" +
                        "\t\t\t\"addUserid\": 1929,\n" +
                        "\t\t\t\"addUsername\": \"系统管理员\",\n" +
                        "\t\t\t\"areaColor\": \"#66CC33\",\n" +
                        "\t\t\t\"black\": [],\n" +
                        "\t\t\t\"content\": \"高新巡检片区1dd\",\n" +
                        "\t\t\t\"domainId\": 2,\n" +
                        "\t\t\t\"gisArea\": \"104.057394406622,30.6506637100957 104.060871810293,30.6502630663113 104.063576370663,30.650330037821 104.067285546517,30.649060769161 104.072153822279,30.648927073429 104.075940203109,30.6486596834995 104.08096261654,30.6459200804665 104.085057648506,30.6439153793222 104.088303750892,30.6178962498581 " +
                        "104.086655325017,30.6132654425166 104.086945207726,30.6086972210626 104.086339069971,30.6058265513857 104.086144755208,30.6031443271339 104.084787460059,30.5990792279191 104.080814812343,30.6000016059846 104.078295520801,30.6005886083929 104.076939028758,30.6005886992441 104.06015433146,30.5989698678308 104.047094318929,30.6010900363135 " +
                        "104.045640812676,30.6012574534747 104.043896607224,30.6014247086556 104.040601686359,30.6025137461129 104.038227331741,30.6034351611853 104.031200516151,30.6073728546645 104.025366039085,30.6083241922232 104.021111985074,30.6130033507539 104.014537402909,30.6190187060846 104.003708522358,30.6263690371734 103.993649739077,30.6357247872725 " +
                        "103.988616307648,30.6444143478883 103.983584029692,30.6507634817927 103.97961357304,30.6561775835211 103.982026528733,30.6627982443217 103.988203516394,30.675841024572 103.994772192837,30.6848717976147 103.997085991407,30.6945685356634 104.002497036493,30.7019266372595 104.008298040626,30.705941377379 104.014101415231,30.7066126366104 " +
                        "104.020290864185,30.7089553920353 104.025319409414,30.7123003969675 104.030348434777,30.7153109562022 104.035570797571,30.7198257620894 104.041373863843,30.7251763728061 104.046791124468,30.727517693089 104.051436068896,30.72283783141 104.056467825985,30.7151489603517 104.064981198217,30.702779362255 104.070010858974,30.6967615561921 " +
                        "104.070784453058,30.6867317673093 104.068850287981,30.6827197744023 104.066529485005,30.6777048989295 104.062274975699,30.672021119639 104.056086822556,30.6700146942696 104.052415589256,30.6517933745394 104.054457850841,30.6511978969532 104.057394406622,30.6506637100957\",\n" +
                        "\t\t\t\"id\": 28365323,\n" +
                        "\t\t\t\"name\": \"高新巡检片区1\",\n" +
                        "\t\t\t\"pid\": 0,\n" +
                        "\t\t\t\"querys\": [],\n" +
                        "\t\t\t\"statuStr\": \"是\",\n" +
                        "\t\t\t\"status\": 1,\n" +
                        "\t\t\t\"tablePre\": \"\",\n" +
                        "\t\t\t\"type\": 1,\n" +
                        "\t\t\t\"typeName\": \"巡检片区\",\n" +
                        "\t\t\t\"udateDate\": \"2016-03-30 14:43:36\",\n" +
                        "\t\t\t\"updateUserid\": 1929,\n" +
                        "\t\t\t\"updateUsername\": \"系统管理员\"\n" +
                        "\t\t},\n" +
                        "\t\t{\n" +
                        "\t\t\t\"addDate\": \"2016-03-09 10:34:52\",\n" +
                        "\t\t\t\"addUserid\": 1929,\n" +
                        "\t\t\t\"addUsername\": \"系统管理员\",\n" +
                        "\t\t\t\"areaColor\": \"#9933CC\",\n" +
                        "\t\t\t\"black\": [],\n" +
                        "\t\t\t\"content\": \"西三环1\",\n" +
                        "\t\t\t\"domainId\": 2,\n" +
                        "\t\t\t\"gisArea\": \"104.058069844735,30.656852585765 104.767139783013,30.6587069846752 104.053841551408,30.6607935665029 104.476028972821,30.6558390772713 104.044295416745,30.6541694113196 104.044353387599,30.6541193144914 104.044353412922,30.6540190989152 " +
                        "104.037630275296,30.654017655274 104.034655388229,30.6531817759408 104.024107032349,30.6533455946603 104.021981691938,30.6538125173847 104.006294000883,30.6544406061282 104.001387259233,30.6538702621043 103.992154656978,30.6512929400234 103.986205034564,30.6506211873762 " +
                        "103.984351081951,30.6498850805843 103.984350849416,30.6501523219597 103.982182637454,30.6552284799236 103.982643697167,30.6581684402432 103.983258290282,30.6623110834057 103.987267508597,30.6736713964816 103.993906449151,30.6843650116158 103.998847959701,30.6923849695115 " +
                        "104.002088818614,30.7012055848909 104.007189141303,30.7050830797743 104.014146787743,30.7068232552542 104.02543297573,30.7123059266193 104.043832254534,30.7270089151187 104.047544098666,30.7282121519556 104.070899086904,30.6962788234129 104.070744297269,30.6874599082458 " +
                        "104.069043596724,30.6799771940024 104.063169413623,30.6719598503274 104.06270581672,30.6695546619059 104.057913848228,30.6695543450243 104.058069843863,30.6568604058876\",\n" +
                        "\t\t\t\"id\": 28365761,\n" +
                        "\t\t\t\"name\": \"巡检片区2\",\n" +
                        "\t\t\t\"pid\": 0,\n" +
                        "\t\t\t\"querys\": [],\n" +
                        "\t\t\t\"statuStr\": \"是\",\n" +
                        "\t\t\t\"status\": 1,\n" +
                        "\t\t\t\"tablePre\": \"\",\n" +
                        "\t\t\t\"type\": 1,\n" +
                        "\t\t\t\"typeName\": \"巡检片区\",\n" +
                        "\t\t\t\"udateDate\": \"2016-03-17 17:49:24\",\n" +
                        "\t\t\t\"updateUserid\": 1929,\n" +
                        "\t\t\t\"updateUsername\": \"系统管理员\"\n" +
                        "\t\t},\n" +
                        "\t\t{\n" +
                        "\t\t\t\"addDate\": \"2016-03-10 18:01:28\",\n" +
                        "\t\t\t\"addUserid\": 1929,\n" +
                        "\t\t\t\"addUsername\": \"系统管理员\",\n" +
                        "\t\t\t\"areaColor\": \"#33FF66\",\n" +
                        "\t\t\t\"black\": [],\n" +
                        "\t\t\t\"content\": \"gsg\",\n" +
                        "\t\t\t\"domainId\": 2,\n" +
                        "\t\t\t\"gisArea\": \"24918.25740,18179.56805,26784.95858,18031.41716,27022.00000,17231.40237,26784.95858,16016.56509,25155.29882,15394.33136,24088.61243,17409.18343,24918.25740,18179.56805\",\n" +
                        "\t\t\t\"id\": 28369970,\n" +
                        "\t\t\t\"name\": \"巡检片区\",\n" +
                        "\t\t\t\"pid\": 0,\n" +
                        "\t\t\t\"querys\": [],\n" +
                        "\t\t\t\"statuStr\": \"否\",\n" +
                        "\t\t\t\"status\": 0,\n" +
                        "\t\t\t\"tablePre\": \"\",\n" +
                        "\t\t\t\"type\": 1,\n" +
                        "\t\t\t\"typeName\": \"巡检片区\",\n" +
                        "\t\t\t\"udateDate\": \"2016-09-23 11:46:07\",\n" +
                        "\t\t\t\"updateUserid\": 1929,\n" +
                        "\t\t\t\"updateUsername\": \"系统管理员\"\n" +
                        "\t\t},\n" +
                        "\t\t{\n" +
                        "\t\t\t\"addDate\": \"2016-03-10 20:14:56\",\n" +
                        "\t\t\t\"addUserid\": 1929,\n" +
                        "\t\t\t\"addUsername\": \"系统管理员\",\n" +
                        "\t\t\t\"areaColor\": \"#996633\",\n" +
                        "\t\t\t\"black\": [],\n" +
                        "\t\t\t\"content\": \"\",\n" +
                        "\t\t\t\"domainId\": 2,\n" +
                        "\t\t\t\"gisArea\": \"104.160391007525,30.6720841432089 104.158536628547,30.671730620178 104.152683208777,30.670688478747 104.146936136487,30.6696522327555 104.144572413583,30.6694945318781 104.142803350398,30.6696423827044 104.140964011718,30.6704263731372 104.134683144848,30.6750420353696 " +
                        "104.131922205761,30.6736486743713 104.119067208864,30.6672680421628 104.108224851875,30.6639563114728 104.099903349884,30.6643378210577 " +
                        "104.099238757559,30.666050727212 104.098701324564,30.6674088747521 104.098114966177,30.6688480233241 104.095781356954,30.6745986329137 104.09491691034,30.6767303170307 104.092291928233,30.679211366544 104.087101224788,30.6817997145048 104.081821742171,30.6843205798071 104.075901410567,30.6864833843577 " +
                        "104.071240635945,30.6881841195398 104.070784169332,30.688245277032 104.070416093719,30.6882483483647 104.063674368315,30.6882956928745 104.063688302648,30.6911646055332 104.063758854723,30.6970124626559 104.064188747268,30.6992161332437 104.064220562989,30.699578614842 104.064146192797,30.7007530883343 " +
                        "104.064011565607,30.7025484387154 104.064032826115,30.7031846514349 104.064018608252,30.7036372325779 104.063919518978,30.7043468566162 104.063763748515,30.7051084286448 104.063544143106,30.7069068404585 104.063473328075,30.7077234321832 104.061473176674,30.7106381441456 104.060906720071,30.7115067452067 " +
                        "104.059292337,30.7133416724116 104.0576355111,30.7151766655312 104.05716797235,30.7176234283452 104.056728719092,30.7199234497735 104.056289353317,30.7221255210428 104.05609095185,30.7232877076881 104.055750919939,30.7249026401198 104.055807175244,30.7281079552488 104.071144251216,30.7261756162262 " +
                        "104.072588723341,30.7259186281447 104.073587089667,30.7256984389096 104.076801594182,30.7248663581763 104.086784876353,30.7222472919054 104.088622179596,30.7219197212838 104.090632903248,30.721735860806 104.096028103342,30.7215146884077 104.102881859424,30.7212194122281 104.111420273259,30.7201648139453 " +
                        "104.115724939352,30.7197106621109 104.119944362376,30.7190239778155 104.12341332183,30.7184597731881 104.126853877572,30.7177731889633 104.128014796374,30.7174055864669 104.12971362372,30.7166952658612 104.136606854361,30.7128747712757 104.145214644428,30.7113162686782 104.147663839596,30.710837693092 " +
                        "104.150678772842,30.7097470374154 104.152801573094,30.7085100593206 104.153622194036,30.7078855814872 104.154853126582,30.7067958900575 104.156437243653,30.7050086782013 104.157214639398,30.7036256952873 104.157907044323,30.7021203757151 104.158386916798,30.7004929890843 104.158611956432,30.6989268363185 " +
                        "104.158989175852,30.6934457162597 104.159449667389,30.6863251158495 104.160391007525,30.6720841432089\",\n" +
                        "\t\t\t\"id\": 143896518,\n" +
                        "\t\t\t\"name\": \"巡检四片区\",\n" +
                        "\t\t\t\"pid\": 0,\n" +
                        "\t\t\t\"querys\": [],\n" +
                        "\t\t\t\"statuStr\": \"是\",\n" +
                        "\t\t\t\"status\": 1,\n" +
                        "\t\t\t\"tablePre\": \"\",\n" +
                        "\t\t\t\"type\": 1,\n" +
                        "\t\t\t\"typeName\": \"巡检片区\",\n" +
                        "\t\t\t\"udateDate\": \"2018-03-29 15:37:53\",\n" +
                        "\t\t\t\"updateUserid\": 1929,\n" +
                        "\t\t\t\"updateUsername\": \"系统管理员\"\n" +
                        "\t\t}\n" +
                        "\t],\n" +
                        "\t\"message\": \"获取数据成功\",\n" +
                        "\t\"success\": true\n" +
                        "}",
                AreaTestBean.class);
    }

    @Override
    protected MapFragmentPresenter createPresenter() {
        return new MapFragmentPresenter(this, this);
    }

    @Override
    protected void loadData() {

    }

    /**
     * 加载地图
     *
     * @param gisParams gisParams
     */
    public void initLayers(GisParamsBean gisParams) {
        // 开启定位
        setupLocationDisplay();

        LogUtils.d("离线地图", "gisParams:" + gisParams.toString());
        if (ObjectUtils.isEmpty(gisParams)) {
            // webtile layer
            MapUtil.setInitMap(mapMv);
            ArcGISMap gisMap = new ArcGISMap(new Basemap());
            mapMv.setMap(gisMap);
            layerList = gisMap.getBasemap().getBaseLayers();
            MapUtil.addLayer(layerList, MapUtil.createTDTLayer());
            reference = mapMv.getSpatialReference();
            ToastBuilder.showShortError("地图参数获取失败，部分功能无法使用");
            return;
        }

        //赋值地图范围
        if (!StringUtils.isTrimEmpty(gisParams.appCenter)) {
            strAppCenter = gisParams.appCenter;
        }

        //加载背景地图
        Layer[] lyrs = MapUtil.getBKLayer(gisParams);
        ArcGISMap gisMap = new ArcGISMap(new Basemap(lyrs[0]));
        layerList = gisMap.getBasemap().getBaseLayers();
        mapMv.setMap(gisMap);

        //加载管线地图
        Layer[] pipeLayer = MapUtil.getPipeLayer(gisParams);
        layerList.add(pipeLayer[0]);

//        MapUtil.addLayer(layerList, MapUtil.getBKLayer(gisParams));
//        MapUtil.addLayer(layerList, MapUtil.getPipeLayer(gisParams));

        initMapListener(gisParams);
    }

    /**
     * 初始化侧滑菜单
     */
    @SuppressLint("InflateParams")
    private void setSliView() {
        mView = LayoutInflater.from(getActivity()).inflate(R.layout.cdqj_patrol_map_sliding_right_menu, null);

        mapMapSlidingRightTypeGv = mView.findViewById(R.id.map_sliding_right_type_gv);
        mapMapSlidingRightAreaGv = mView.findViewById(R.id.map_sliding_right_area_gv);
        mapSlidingRightArea = mView.findViewById(R.id.map_sliding_right_area);
        mapSlidingRightObjGv = mView.findViewById(R.id.map_sliding_right_obj_gv);
        mapSlidingRightObj = mView.findViewById(R.id.map_sliding_right_obj);

        //  初始化侧滑菜单数据
        mSlidingTypes = new ArrayList<>();
        mSlidingTypes.add(new CustomGridPicWindowBean(R.mipmap.icon_ichnography, "平面图"));
        mSlidingTypes.add(new CustomGridPicWindowBean(R.mipmap.icon_image_map, "影像图"));
        mSlidingTypes.add(new CustomGridPicWindowBean(R.mipmap.icon_topographic_map, "地形图"));
        mSlidingTypes.get(0).setSelect(true);

        mSlidingAreas = new ArrayList<>();
        mSlidingAreas.add(new GridSelectBean("网格片区", R.mipmap.icon_area_to_select, R.mipmap.icon_area_to, false));
        //mSlidingAreas.add(new GridSelectBean("巡检片区", R.mipmap.icon_mid_line_select, R.mipmap.icon_mid_line, false));
        mapMapSlidingRightAreaGv.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(5)));
        mapMapSlidingRightAreaGv.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        mSlidingObjs = new ArrayList<>();
        mSlidingObjs.add(new GridSelectBean("人员", R.mipmap.icon_personnel_select, R.mipmap.icon_personnel, false));
        mSlidingObjs.add(new GridSelectBean("隐患", R.mipmap.icon_hd_select, R.mipmap.icon_hd, false));
        mSlidingObjs.add(new GridSelectBean("计划", R.mipmap.icon_plan_select, R.mipmap.icon_plan_unselect, false));
        mSlidingObjs.add(new GridSelectBean("工地", R.mipmap.icon_sites_select, R.mipmap.icon_sites, false));
//        mSlidingObjs.add(new GridSelectBean("车辆", R.mipmap.icon_car_select, R.mipmap.icon_car, false));
        mapSlidingRightObjGv.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(5)));
        mapSlidingRightObjGv.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        initSlidAdapter();
    }

    private void setTabData() {
        mapTab.setTabData(mTabEntities);
        mapTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position != 2) {
                    anim(mapTab.getVisibility() != View.VISIBLE);
                }
                EventBus.getDefault().post(new MsgEvent<>(3, new EventGpsBean(1, position)));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mapTab.setCurrentTab(2);
    }

    /**
     * 初始化查询界面布局
     */
    private void setSearchView() {
        mapSearchDetailType.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        mapSearchDetailType.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(5)));
        mapSearchDetailList.setLayoutManager(new LinearLayoutManager(getActivity()));

        // hidden users site device
        GridSelectBean bean = new GridSelectBean("道路", R.mipmap.icon_road_select, R.mipmap.icon_road, true);
        beans.add(bean);
        bean = new GridSelectBean("设备", R.mipmap.icon_plan_select_search, R.mipmap.icon_plan_search, true);
        bean.setValue("device");
        beans.add(bean);
        bean = new GridSelectBean("人员", R.mipmap.icon_people_select, R.mipmap.icon_people, true);
        bean.setValue("users");
        beans.add(bean);
        bean = new GridSelectBean("隐患", R.mipmap.icon_trouble_select, R.mipmap.icon_trouble, true);
        bean.setValue("hidden");
        beans.add(bean);
        bean = new GridSelectBean("工地", R.mipmap.icon_pipeline_select, R.mipmap.icon_pipeline, true);
        bean.setValue("site");
        beans.add(bean);

        GridIconAndTextAdapter adapterSearchType = new GridIconAndTextAdapter(R.layout.cdqj_patrol_grid_icon_and_title_item,
                beans, getActivity());

        adapterSearchType.bindToRecyclerView(mapSearchDetailType);
        adapterSearchType.setOnItemClickListener((adapter, view, position) -> {
            beans.get(position).setSelectStatus(!beans.get(position).isSelectStatus());
            adapter.notifyDataSetChanged();
            searchSubmit(mapSearchDetailText.getText().toString());
        });

        gridSelectBeans = new ArrayList<>();
        roundSelectBean = new GridSelectBean("道路", R.mipmap.icon_layer_track, 0, false);
        gridSelectBeans.add(roundSelectBean);
        mMapSearchListAdapter = new MapSearchListAdapter(gridSelectBeans, getActivity());
        mMapSearchListAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mMapSearchListAdapter.bindToRecyclerView(mapSearchDetailList);
        mMapSearchListAdapter.expandAll();
        mMapSearchListAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (adapter.getItemViewType(position) == MapSearchListAdapter.TYPE_LEVEL_1) {
                // 是子类 // 类型 1、管线 2、隐患 3、人员 4、计划 5、工地
                MapSearchBean mapSearchBean = (MapSearchBean) adapter.getData().get(position);
                mapSearchHind();
                switch (mapSearchBean.getType()) {
                    case MapSearchBean.ROAD:
                        roundBean = mapSearchBean;
                        Point pointRound = new Point(Double.valueOf(mapSearchBean.getLocation().split(" ")[0]),
                                Double.valueOf(mapSearchBean.getLocation().split(" ")[1]), reference);
                        MapUtil.addPointLayer(mapMv, tempGraphicsLayer, getActivity(), R.mipmap.icon_gps_point, pointRound, new HashMap<>(), true, false);
                        showMapLine(true, 6);
                        break;
                    case MapSearchBean.DEVICE:
                        for (DeviceBean deviceBean : body.getDevice()) {
                            if (deviceBean.getId().equals(mapSearchBean.getId())) {
                                // 对象类型（点/线/面）
                                this.deviceBean = deviceBean;
                                if (deviceBean.getGisType() == 1) {
                                    if (!StringUtils.isTrimEmpty(deviceBean.getGisArea())) {
                                        Point point = new Point(Double.valueOf(deviceBean.getGisArea().trim().split(",")[0]),
                                                Double.valueOf(deviceBean.getGisArea().trim().split(",")[1]), reference);
                                        MapUtil.addPointLayer(mapMv, tempGraphicsLayer, getActivity(), R.mipmap.icon_point_unexecuted, point, MapUtil.jsonStr2MapInMap(deviceBean), true, false);
                                    }
                                } else if (deviceBean.getGisType() == 2) {
                                    if (!StringUtils.isTrimEmpty(deviceBean.getGisArea())) {
                                        String[] points = deviceBean.getGisArea().trim().split(" ");
                                        Point[] pointsLine = new Point[points.length];
                                        for (int k = 0; k < points.length; k++) {
                                            pointsLine[k] = new Point(Double.valueOf(points[k].split(",")[0]),
                                                    Double.valueOf(points[k].split(",")[1]), reference);
                                        }
                                        MapUtil.addLineLayer(mapMv, tempGraphicsLayer, "#666AD1", pointsLine, MapUtil.jsonStr2MapInMap(deviceBean, MapConstant.PLAN_ATTR_NAME), false);
                                    }
                                }
                                mapBeans = null;
                                navX = deviceBean.getLat();
                                navY = deviceBean.getLon();
                                mapLineDialogTitle.setText(StringUtils.isTrimEmpty(deviceBean.getDeviceTypeExp()) ?
                                        getString(R.string.null_text) : deviceBean.getDeviceTypeExp());
                                mapLineDialogAddress.setText(StringUtils.isTrimEmpty(deviceBean.getAddress()) ?
                                        getString(R.string.null_text) : deviceBean.getAddress());
                                mapLineDialogDistance.setText(StringUtils.isTrimEmpty(deviceBean.getProjectName()) ?
                                        getString(R.string.null_text) : deviceBean.getProjectName());
                                showMapLine(true, 1);
                                break;
                            }
                        }
                        break;
                    case MapSearchBean.HD:
                        for (HdOrderBean deviceBean : body.getHidden()) {
                            if (deviceBean.getId().equals(mapSearchBean.getId()) && !StringUtils.isTrimEmpty(deviceBean.getGisArea())) {
                                Point point = new Point(Double.valueOf(deviceBean.getGisArea().trim().split(",")[0]),
                                        Double.valueOf(deviceBean.getGisArea().trim().split(",")[1]), reference);
                                MapUtil.addPointLayer(mapMv, tempGraphicsLayer, getActivity(),
                                        ("1".equals(deviceBean.getHdStatus()) || "2".equals(deviceBean.getHdStatus())) ? R.mipmap.icon_hd_end :
                                                "6".equals(deviceBean.getHdStatus()) ? R.mipmap.icon_hd_unexecuted : R.mipmap.icon_hd_ing
                                        , point, MapUtil.jsonStr2MapInMap(deviceBean, MapConstant.HD_ATTR_NAME), true, false);
                                navX = point.getY();
                                navY = point.getX();
                                hdBean = deviceBean;
                                showMapLine(true, 2);
                                break;
                            }
                        }
                        break;
                    case MapSearchBean.PERSONNEL:
                        for (UserLayerBean deviceBean : body.getUsers()) {
                            if (deviceBean.getId().equals(mapSearchBean.getId()) && ObjectUtils.isNotEmpty(deviceBean.getLastReportLon()) && ObjectUtils.isNotEmpty(deviceBean.getLastReportLat())) {
                                Point point = new Point(deviceBean.getLastReportLon(), deviceBean.getLastReportLat(), reference);
                                MapUtil.addPointLayer(mapMv, tempGraphicsLayer, getActivity(), deviceBean.getStatus() == 1 ?
                                        R.mipmap.icon_person_onlin : deviceBean.getStatus() == 2 ? R.mipmap.icon_person_off_work : R.mipmap.icon_person_offline, point, MapUtil.jsonStr2MapInMap(deviceBean, MapConstant.PEOPLE_ATTR_NAME), true, false);
                                navX = point.getY();
                                navY = point.getX();
                                personBean = deviceBean;
                                showMapLine(true, 3);
                                break;
                            }
                        }
                        break;
                    case MapSearchBean.OTHER:
                        break;
                    case MapSearchBean.SITE:
                        for (SiteBean deviceBean : body.getSite()) {
                            if (deviceBean.getId().equals(mapSearchBean.getId()) && !StringUtils.isTrimEmpty(deviceBean.getGisArea())) {
                                Point point = new Point(Double.valueOf(deviceBean.getGisArea().trim().split(",")[0]),
                                        Double.valueOf(deviceBean.getGisArea().trim().split(",")[1]), reference);
                                MapUtil.addPointLayer(mapMv, tempGraphicsLayer, getActivity(),
                                        deviceBean.getStatus() == 1 ? R.mipmap.icon_site_unexecuted : R.mipmap.icon_site_unexecuted
                                        , point, MapUtil.jsonStr2MapInMap(deviceBean, MapConstant.GD_ATTR_NAME), true, false);
                                navX = point.getY();
                                navY = point.getX();
                                gdBean = deviceBean;
                                showMapLine(true, 5);
                                break;
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        mMapSearchListAdapter.setOnItemChildClickListener((adapter1, view, position) -> {
            MapSearchBean mapSearchBean = (MapSearchBean) adapter1.getData().get(position);
            int i = view.getId();
            if (i == R.id.item_road_goto) {
                switch (mapSearchBean.getType()) {
                    case MapSearchBean.ROAD:
                        NavigationUtil.showNavWindow(Double.valueOf(mapSearchBean.getLocation().split(" ")[1]),
                                Double.valueOf(mapSearchBean.getLocation().split(" ")[0]), getActivity());
                        break;
                    case MapSearchBean.DEVICE:
                        for (DeviceBean deviceBean : body.getDevice()) {
                            if (deviceBean.getId().equals(mapSearchBean.getId())) {
                                NavigationUtil.showNavWindow(deviceBean.getLat(), deviceBean.getLon(), getActivity());
                            }
                        }
                        break;
                    default:
                        break;
                }

            } else if (i == R.id.item_pipeline_img) {
                switch (mapSearchBean.getType()) {
                    case MapSearchBean.HD:
                        for (HdOrderBean deviceBean : body.getHidden()) {
                            if (deviceBean.getId().equals(mapSearchBean.getId())) {
                                PatrolEnterPointActivity.gotoMapHdDetailActivity(getActivity(), deviceBean);
                            }
                        }
                        break;
                    case MapSearchBean.PERSONNEL:
                        for (UserLayerBean deviceBean : body.getUsers()) {
                            if (deviceBean.getId().equals(mapSearchBean.getId())) {
                                PatrolEnterPointActivity.gotoMapPersonDetailActivity(getActivity(), deviceBean);
                            }
                        }
                        break;
                    case MapSearchBean.OTHER:
                        break;
                    case MapSearchBean.SITE:
                        for (SiteBean deviceBean : body.getSite()) {
                            if (deviceBean.getId().equals(mapSearchBean.getId())) {
                                PatrolEnterPointActivity.gotoMapSiteDetailActivity(getActivity(), deviceBean, 1);
                            }
                        }
                        break;
                    default:
                        break;
                }


            }
        });
    }

    /**
     * 初始化地图属性弹窗
     */
    @SuppressLint("InflateParams")
    private void setMapLineInfoDialog() {
        // 管线弹窗 R.layout.cdqj_patrol_map_line_info
        viewLine = LayoutInflater.from(getActivity()).inflate(R.layout.cdqj_patrol_map_line_info, null);
        mapLineDialogTitle = viewLine.findViewById(R.id.map_line_dialog_title);
        mapLineDialogDistance = viewLine.findViewById(R.id.map_line_dialog_distance);
        mapLineDialogAddress = viewLine.findViewById(R.id.map_line_dialog_address);
        // 隐患弹窗 R.layout.cdqj_patrol_map_hd_info
        viewHd = LayoutInflater.from(getActivity()).inflate(R.layout.cdqj_patrol_map_hd_info, null);
        mapHdDialogTitle = viewHd.findViewById(R.id.map_hd_dialog_title);
        mapHdDialogDistance = viewHd.findViewById(R.id.map_hd_dialog_distance);
        mapHdDialogAddress = viewHd.findViewById(R.id.map_hd_dialog_address);
        mapHdDialogLevel = viewHd.findViewById(R.id.map_hd_dialog_level);
        mapHdDialogUpdate = viewHd.findViewById(R.id.map_hd_dialog_update);
        mapHdDialogImg = viewHd.findViewById(R.id.map_hd_dialog_img);
        mapHdDialogImg.setOnClickListener(v -> {
            // 图片查看   2、隐患  5、工地
            if (type == 2 && ObjectUtils.isNotEmpty(hdBean)) {
                PictureProcessingUtil.imgMoreSHow(getActivity(), hdBean.getHdPicture(), (ImageView) v);
            }
            if (type == 5 && ObjectUtils.isNotEmpty(gdBean)) {
                PictureProcessingUtil.imgMoreSHow(getActivity(), gdBean.getPicture(), (ImageView) v);
            }
        });
    }

    /**
     * 上报弹窗表单-隐患，工地，巡检点，设备
     */
    @SuppressLint("InflateParams")
    private void setUpdateDialog() {
        fileView = LayoutInflater.from(getActivity()).inflate(R.layout.cdqj_patrol_order_supervise_report_window, null);
        title = fileView.findViewById(R.id.supervise_report_title);
//        TextView videoTitle = fileView.findViewById(R.id.supervise_report_video_title);
//        TextView voiceTitle = fileView.findViewById(R.id.supervise_report_voice_title);
        etRemark = fileView.findViewById(R.id.supervise_report_remark);
        rvPicture = fileView.findViewById(R.id.supervise_report_picture_gv);
        rvAudio = fileView.findViewById(R.id.supervise_report_audio_gv);
        rvVideo = fileView.findViewById(R.id.supervise_report_video_gv);
        etRemark.setVisibility(View.GONE);
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

            if (!((ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), requestPermissions[0]) == PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(getActivity(), requestPermissions[1]) == PackageManager.PERMISSION_GRANTED))) {
                ActivityCompat.requestPermissions(getActivity(), requestPermissions, requestPermissionsCode);
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
            list.add(QueryLayerType.QueryOnline);
            QueryParams queryParams = new QueryParams(list, null, null, 1, null);
            mapTouchListener = new MapTouchListener(getActivity(), mapMv, queryParams, this);
            // 添加管线查询
            mapTouchListener.addGisParamsToParams(QueryLayerType.QueryOnline, params);
            mapTouchListener.addGisParamsToParams(QueryLayerType.QueryAllGpraphicsOverlay, params);

        }
        mapMv.setOnTouchListener(mapTouchListener);
    }

    private void initSlidAdapter() {
        mTypeAdapter = new MapSlidingGridAdapter(getActivity(), mSlidingTypes);
        mapMapSlidingRightTypeGv.setAdapter(mTypeAdapter);
        mapMapSlidingRightTypeGv.setOnItemClickListener((parent, view, position, id) -> {
            if (!mSlidingTypes.get(position).isSelect()) {
                mSlidingTypes.get(position).setSelect(true);
                for (int i = 0; i < mSlidingTypes.size(); i++) {
                    if (i != position) {
                        mSlidingTypes.get(i).setSelect(false);
                    }
                }
                mTypeAdapter.notifyDataSetChanged();
                // 平面图|卫星图切换
                if ("平面图".equals(mSlidingTypes.get(position).getTitle())) {
                    MapUtil.addLayer(layerList, MapUtil.changeTDTLayer(1));
                } else if ("影像图".equals(mSlidingTypes.get(position).getTitle())) {
                    MapUtil.addLayer(layerList, MapUtil.changeTDTLayer(2));
                } else if ("地形图".equals(mSlidingTypes.get(position).getTitle())) {
                    MapUtil.addLayer(layerList, MapUtil.changeTDTLayer(3));
                }
            }
        });

        MapSlidingRvAdapter areaAdapter = new MapSlidingRvAdapter(R.layout.cdqj_patrol_map_sliding_rv_item,
                mSlidingAreas, getActivity());
        areaAdapter.bindToRecyclerView(mapMapSlidingRightAreaGv);

        MapSlidingRvAdapter objAdapter = new MapSlidingRvAdapter(R.layout.cdqj_patrol_map_sliding_rv_item,
                mSlidingObjs, getActivity());
        objAdapter.bindToRecyclerView(mapSlidingRightObjGv);

        areaAdapter.setOnItemClickListener((adapter, view, position) -> {
            mSlidingAreas.get(position).setSelectStatus(!mSlidingAreas.get(position).isSelectStatus());
            if ("巡检片区".equals(mSlidingAreas.get(position).getTitle())) {
                if (!mSlidingAreas.get(position).isSelectStatus()) {
                    MapUtil.addSingelLayer(mapMv, areaOverlay).getGraphics().clear();
                } else {
                    MapUtil.addPolygons(getActivity(), areaOverlay, reference, mTestBean.getData(), mSlidingAreas.get(position).getTitle(), 255, 0, 0);
                }
            } else if ("网格片区".equals(mSlidingAreas.get(position).getTitle())) {
                if (!mSlidingAreas.get(position).isSelectStatus()) {
                    MapUtil.addSingelLayer(mapMv, gridOverlay).getGraphics().clear();
                } else {
                    if (mMapGridBeans.size() > 0) {
                        // 加载图层
                        List<Map<String, Object>> mapList = new ArrayList<>();
                        for (int i = 0; i < mMapGridBeans.size(); i++) {
                            mMapGridBeans.get(i).setGisArea(mMapGridBeans.get(i).getGridAreas());
                            mMapGridBeans.get(i).setName(mMapGridBeans.get(i).getGridNumber());
                            mapList.add(MapUtil.jsonStr2MapInMap(mMapGridBeans.get(i)));
                        }
                        String bgColorStr = mMapGridBeans.get(0).getGridColor().replaceAll("#", "#2B");
                        MapUtil.addPolygons(getActivity(), gridOverlay, reference, mMapGridBeans, mapList, mMapGridBeans.get(0).getGridColor(), bgColorStr, true);
                    } else {
                        ToastBuilder.showShortError("暂无网格图层数据");
                    }
                }
            }
            adapter.notifyDataSetChanged();
        });
        objAdapter.setOnItemClickListener((adapter, view, position) -> {
            mSlidingObjs.get(position).setSelectStatus(!mSlidingObjs.get(position).isSelectStatus());
            if ("人员".equals(mSlidingObjs.get(position).getTitle())) {
                if (!mSlidingObjs.get(position).isSelectStatus()) {
                    MapUtil.addSingelLayer(mapMv, personOverlay).getGraphics().clear();
                } else {
                    mPresenter.getPersonData();
                }
            } else if ("隐患".equals(mSlidingObjs.get(position).getTitle())) {
                if (!mSlidingObjs.get(position).isSelectStatus()) {
                    MapUtil.addSingelLayer(mapMv, hdOverlay).getGraphics().clear();
                } else {
                    mPresenter.getHdData();
                }
            } else if ("计划".equals(mSlidingObjs.get(position).getTitle())) {
                if (!mSlidingObjs.get(position).isSelectStatus()) {
                    MapUtil.addSingelLayer(mapMv, planOverlay).getGraphics().clear();
                } else {
                    mPresenter.getCheckTypes();
                }
            } else if ("工地".equals(mSlidingObjs.get(position).getTitle())) {
                if (!mSlidingObjs.get(position).isSelectStatus()) {
                    MapUtil.addSingelLayer(mapMv, siteOverlay).getGraphics().clear();
                } else {
                    mPresenter.getSiteData();
                }
            } else if ("车辆".equals(mSlidingObjs.get(position).getTitle())) {
                if (!mSlidingObjs.get(position).isSelectStatus()) {
                    MapUtil.addSingelLayer(mapMv, carOverlay).getGraphics().clear();
                } else {
                    // 加载车辆
                    mPresenter.getCarData();
                }
            }
            adapter.notifyDataSetChanged();
        });
    }

    /**
     * 底部导航栏动画
     *
     * @param isShowBottomBar 是否展示底部导航栏
     */
    private void anim(final boolean isShowBottomBar) {
        Techniques technique = isShowBottomBar ? Techniques.SlideInUp : Techniques.SlideOutDown;
        AnimUtils.setAnima(technique, 200, mapTab, isShowBottomBar);
    }

    @Override
    public void onGetSearchDataSuccess(ResultMapSearchBean body) {
        // 查询返回结果
        this.body = body;
        gridSelectBeans = generateData(body);
        mMapSearchListAdapter.notifyDataSetChanged();
        mMapSearchListAdapter.expandAll();

    }

    @Override
    public void onSearchRoundSuccess(String str) {
        // 道路查询结果
        roundSelectBean = new GridSelectBean("道路", R.mipmap.icon_layer_track, 0, false);
        if (!StringUtils.isTrimEmpty(str)) {
            if (str.contains("=")) {
                int index = str.indexOf("=");
                String result = str.substring(index + 1, str.length() - 1);
                SearchRoundBean body = GsonUtils.gsonBuilder.create().fromJson(result, SearchRoundBean.class);
                if (ObjectUtils.isNotEmpty(body) && ObjectUtils.isNotEmpty(body.getPois())
                        && body.getPois().size() > 0) {
                    MapSearchBean bean;
                    for (int i = 0; i < body.getPois().size(); i++) {
                        SearchRoundBean.PoisBean roundBean = body.getPois().get(i);
                        bean = new MapSearchBean(i, MapSearchBean.ROAD);
                        bean.setName(roundBean.getName());
                        bean.setAddress(roundBean.getAddress());
                        bean.setMaxSize(body.getPois().size());
                        bean.setId(roundBean.getHotPointID());
                        bean.setLocation(roundBean.getLonlat());
                        roundSelectBean.addSubItem(bean);
                    }
                }
            }
        }
        if (ObjectUtils.isNotEmpty(roundSelectBean.getSubItems())
                && roundSelectBean.getSubItems().size() > 0) {
            if (!gridSelectBeans.contains(roundSelectBean)) {
                gridSelectBeans.add(roundSelectBean);
            }
        }
        mMapSearchListAdapter.notifyDataSetChanged();
        mMapSearchListAdapter.expandAll();
    }

    /**
     * 数据封装
     *
     * @return gridSelectBeans
     */
    private List<MultiItemEntity> generateData(ResultMapSearchBean body) {
        GridSelectBean gridSelectBean;
        MapSearchBean bean;
        if (ObjectUtils.isNotEmpty(roundSelectBean.getSubItems())
                && roundSelectBean.getSubItems().size() > 0) {
            if (!gridSelectBeans.contains(roundSelectBean)) {
                gridSelectBeans.add(roundSelectBean);
            }
        }
        if (body.getDevice().size() > 0) {
            // 设备
            gridSelectBean = new GridSelectBean("设备", R.mipmap.icon_layer_measure, 0, false);
            for (int i = 0; i < body.getDevice().size(); i++) {
                bean = new MapSearchBean(i, MapSearchBean.DEVICE);
                bean.setName(body.getDevice().get(i).getDeviceTypeExp());
                bean.setAddress(body.getDevice().get(i).getAddress());
                bean.setMaxSize(body.getDevice().size());
                bean.setId(body.getDevice().get(i).getId());
                gridSelectBean.addSubItem(bean);
            }
            gridSelectBeans.add(gridSelectBean);
        }
        if (body.getHidden().size() > 0) {
            // 隐患
            gridSelectBean = new GridSelectBean("隐患", R.mipmap.icon_layer_gps_to, 0, false);
            for (int i = 0; i < body.getHidden().size(); i++) {
                bean = new MapSearchBean(i, MapSearchBean.HD);
                bean.setName(body.getHidden().get(i).getHdTypeExp());
                bean.setAddress(body.getHidden().get(i).getHdAddress());
                bean.setLevel(body.getHidden().get(i).getHdGrandExp());
                bean.setMaxSize(body.getHidden().size());
                bean.setId(body.getHidden().get(i).getId());
                gridSelectBean.addSubItem(bean);
            }
            gridSelectBeans.add(gridSelectBean);
        }
        if (body.getSite().size() > 0) {
            // 工地
            gridSelectBean = new GridSelectBean("工地", R.mipmap.icon_layer_location_point, 0, false);
            for (int i = 0; i < body.getSite().size(); i++) {
                bean = new MapSearchBean(i, MapSearchBean.SITE);
                bean.setName(body.getSite().get(i).getName());
                bean.setAddress(body.getSite().get(i).getAddress());
                bean.setLevel(body.getSite().get(i).getSiteLevelExp());
                bean.setMaxSize(body.getSite().size());
                bean.setId(body.getSite().get(i).getId());
                gridSelectBean.addSubItem(bean);
            }
            gridSelectBeans.add(gridSelectBean);
        }
        if (body.getUsers().size() > 0) {
            // 用户
            gridSelectBean = new GridSelectBean("人员", R.mipmap.icon_layer_measure_to, 0, false);
            for (int i = 0; i < body.getUsers().size(); i++) {
                bean = new MapSearchBean(i, MapSearchBean.PERSONNEL);
                bean.setName(body.getUsers().get(i).getSysStaffName());
                bean.setAddress(body.getUsers().get(i).getGroupName());
                bean.setLevel(body.getUsers().get(i).getStatus() == 1 ? "上班" : body.getUsers().get(i).getStatus() == 2 ? "下班" : "异常");
                bean.setMaxSize(body.getUsers().size());
                bean.setId(body.getUsers().get(i).getId());
                gridSelectBean.addSubItem(bean);
            }
            gridSelectBeans.add(gridSelectBean);
        }
        return gridSelectBeans;
    }

    private void searchSubmit(String searchStr) {
        typeStr = new StringBuilder();
        if (ObjectUtils.isNotEmpty(roundSelectBean)
                && ObjectUtils.isNotEmpty(roundSelectBean.getSubItems())) {
            roundSelectBean.getSubItems().clear();
        }
        if (ObjectUtils.isNotEmpty(gridSelectBeans)) {
            gridSelectBeans.clear();
        }
        if (StringUtils.isTrimEmpty(searchStr)) {
            body = new ResultMapSearchBean();
            mMapSearchListAdapter.notifyDataSetChanged();
            return;
        }
        for (GridSelectBean selectBean : beans) {
            if (selectBean.getTitle().equals("道路")) {
                if (selectBean.isSelectStatus()) {
                    RetrofitUtils.searchRound(this, mGisParamsBean.appCenter.replaceAll("\\|", ","), searchStr, 5);
                }
            } else if (selectBean.isSelectStatus()) {
                typeStr.append(selectBean.getValue());
                typeStr.append(",");
            }
        }
        if (StringUtils.isTrimEmpty(typeStr.toString())) {
            return;
        }
        mPresenter.getSearchData(false, searchStr, typeStr.toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        mapMv.resume();
        //anim(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapMv.pause();
    }

    /**
     * 取消测量
     */
    private void cancelMeasure() {
        MapUtil.addSingelLayer(mapMv, tempGraphicsLayer).getGraphics().clear();
        getMeasureTool().cancelDraw();
        measureTool = null;
        if (ObjectUtils.isNotEmpty(mGisParamsBean)) {
            //重新注册地图事件
            initMapListener(mGisParamsBean);
        }
    }

    /**
     * 获取地图测量工具
     *
     * @return MeasureTool
     */
    private MeasureTool getMeasureTool() {
        if (measureTool == null) {
            measureTool = new MeasureTool(getActivity(), mapMv, MapUtil.addSingelLayer(mapMv, tempGraphicsLayer));
        }
        return measureTool;
    }

    /**
     * 获取地图测量工具
     */
    @SuppressLint("InflateParams")
    private void showMeasureWindow() {
        updateFooter = LayoutInflater.from(getActivity()).inflate(R.layout.cdqj_patrol_map_update_window_footer, null);
        // 初始化工具栏数据
        List<CustomGridPicWindowBean> updateType = new ArrayList<>();
        updateType.add(new CustomGridPicWindowBean(R.mipmap.icon_layer_location_point, "坐标"));
        updateType.add(new CustomGridPicWindowBean(R.mipmap.icon_layer_measure, "距离"));
        updateType.add(new CustomGridPicWindowBean(R.mipmap.icon_layer_measure_to, "面积"));
        updateType.add(new CustomGridPicWindowBean(R.mipmap.icon_clean, "清除"));
        updateType.add(new CustomGridPicWindowBean(R.mipmap.icon_quit, "退出"));
        MapSlidingGridAdapter mUpdateAdapter = new MapSlidingGridAdapter(getActivity(), updateType);
        mDialogPlus = DialogPlus.newDialog(Objects.requireNonNull(getActivity()))
                .setContentHolder(new GridHolder(5))
                .setPadding(ConvertUtils.dp2px(12), ConvertUtils.dp2px(20),
                        ConvertUtils.dp2px(12), ConvertUtils.dp2px(20))
                .setGravity(Gravity.BOTTOM)
                .setFooter(updateFooter)
                .setCancelable(true)
                .setContentBackgroundResource(R.drawable.stroke_bg_radius_top)
                .setAdapter(mUpdateAdapter)
                .setInAnimation(R.anim.slide_in_bottom)
                .setOutAnimation(R.anim.slide_out_bottom)
                .setOnItemClickListener((dialog, item, view, position) -> {
                    dialog.dismiss();
                    switch (position) {
                        case 0:
                            getMeasureTool().drawPoint();
                            ToastBuilder.showShort("坐标测量");
                            break;
                        case 1:
                            getMeasureTool().drawLine();
                            ToastBuilder.showShort("距离测量");
                            break;
                        case 2:
                            getMeasureTool().drawPolygon();
                            ToastBuilder.showShort("面积测量");
                            break;
                        case 3:
                            getMeasureTool().setStartPt();
                            MapUtil.addSingelLayer(mapMv, tempGraphicsLayer).getGraphics().clear();
                            ToastBuilder.showShort("清除测量图层");
                            break;
                        case 4:
                            cancelMeasure();
                            ToastBuilder.showShort("量测工具已关闭");
                            break;
                        default:
                            break;
                    }
                })
                .setOnClickListener((dialog, view) -> {
                    int i = view.getId();
                    if (i == R.id.update_window_footer_img) {
                        dialog.dismiss();

                    }
                })
                .create();
        mDialogPlus.show();
    }

    /**
     * 地图显示输入的xy坐标定位
     *
     * @param x x
     * @param y y
     */
    private void addIconInMapFromXY(double x, double y) {
        if (ObjectUtils.isEmpty(reference)) {
            ToastBuilder.showShortError("地图参数获取失败");
            return;
        }
        MapUtil.addSingelLayer(mapMv, tempGraphicsLayer).getGraphics().clear();
        MapUtil.addPointLayer(mapMv, tempGraphicsLayer, getActivity(), R.mipmap.icon_gps_point, x, y, reference, true);
    }

    /**
     * 工地上报片区数据
     */
    @Override
    public void onGetComboboxResult(ArrayList<PatrolHdType> siteArea) {
        this.siteArea = siteArea;
        if (siteArea.size() != 0) {
            siteSubmit.put("areaId", String.valueOf(siteArea.get(0).getValue()));
            updateSiteSspq.setText(siteArea.get(0).getText());
        }
    }

    @Override
    public void onGetHdType(ArrayList<PatrolHdType> hdTypes) {
        this.postResponse = hdTypes;
        if (postResponse.size() != 0) {
            hdSubmit.put("hdType", String.valueOf(postResponse.get(0).getValue()));
            updateHdType.setText(postResponse.get(0).getText());
        }
    }

    @Override
    public void onAddupdateSuccse(BasePostResponse<Object> body) {
        if (body.isSuccess()) {
            // 工单上报成功
            if (mDialogPlus.isShowing()) {
                mDialogPlus.dismiss();
            }
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            ToastBuilder.showShort(body.getMsg());
        } else {
            ToastBuilder.showShortWarning(body.getMsg());
        }
    }

    @Override
    public void onHdSubmitSuccse(BasePostResponse<Object> beans) {
        hideProgress();
        if (beans.isSuccess()) {
            if (mDialogPlus.isShowing()) {
                mDialogPlus.dismiss();
            }
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            ToastBuilder.showShort(beans.getMsg());
        } else {
            ToastBuilder.showShortWarning(beans.getMsg());
        }
    }

    @Override
    public void onGetCheckTypesSuccess(BaseGetResponse<PatrolTaskResultBean> bean) {
        List<PatrolTaskResultBean> body = bean.getRows();
        if (body.size() > 0) {
            new Thread(() -> {
                for (int i = 0; i < body.size(); i++) {
                    // 对象类型（点/线/面）
                    if (body.get(i).getObjectType() == 1) {
                        if (!StringUtils.isTrimEmpty(body.get(i).getGisArea())) {
                            Point point = new Point(Double.valueOf(body.get(i).getGisArea().trim().split(",")[0]),
                                    Double.valueOf(body.get(i).getGisArea().trim().split(",")[1]), reference);
                            MapUtil.addPointLayer(mapMv, planOverlay, getActivity(), body.get(i).getPassStatus() == 0 ?
                                    R.mipmap.icon_point_end : R.mipmap.icon_point_unexecuted, point, MapUtil.jsonStr2MapInMap(body.get(i), MapConstant.PLAN_ATTR_NAME), false, false);
                        }
                    } else if (body.get(i).getObjectType() == 2) {
                        if (!StringUtils.isTrimEmpty(body.get(i).getGisArea())) {
                            String[] points = body.get(i).getGisArea().trim().split(" ");
                            Point[] pointsLine = new Point[points.length];
                            for (int k = 0; k < points.length; k++) {
                                pointsLine[k] = new Point(Double.valueOf(points[k].split(",")[0]),
                                        Double.valueOf(points[k].split(",")[1]), reference);
                            }
                            MapUtil.addLineLayer(mapMv, planOverlay, body.get(i).getPassStatus() == 0 ? "#A3A3A3" : "#666AD1", pointsLine, MapUtil.jsonStr2MapInMap(body.get(i), MapConstant.PLAN_ATTR_NAME), false);
                        }
                    } else if (body.get(i).getObjectType() == 3) {
                        List<PatrolTaskResultBean> list = new ArrayList<>();
                        list.add(body.get(i));
                        List<Map<String, Object>> mapList = new ArrayList<>();
                        mapList.add(MapUtil.jsonStr2MapInMap(body.get(i), MapConstant.PLAN_ATTR_NAME));
                        MapUtil.addPolygons(getActivity(), null, planOverlay, reference, list, mapList, body.get(i).getPassStatus() == 0 ? "#A3A3A3" : "#666AD1", true);
                    }
                }
            }).start();
        } else {
            ToastBuilder.showShortWarning("暂无计划图层数据");
        }
    }

    @Override
    public void onGetPersonSuccess(List<UserLayerBean> body) {
        if (body.size() > 0) {
            boolean isShowPerson = false;
            for (int i = 0; i < body.size(); i++) {
                if (0.0 != body.get(i).getLastReportLon()
                        && 0.0 != body.get(i).getLastReportLat()) {
                    isShowPerson = true;
                    Point point = new Point(body.get(i).getLastReportLon(), body.get(i).getLastReportLat(), reference);
                    MapUtil.addPointLayer(mapMv, personOverlay, getActivity(), body.get(i).getStatus() == 1 ?
                            R.mipmap.icon_person_onlin : body.get(i).getStatus() == 2 ? R.mipmap.icon_person_off_work : body.get(i).getStatus() == 3 ? R.mipmap.icon_person_offline : R.mipmap.icon_person_transboundary, point, MapUtil.jsonStr2MapInMap(body.get(i), MapConstant.PEOPLE_ATTR_NAME), false, false);
                }
            }
            if (!isShowPerson) {
                mDialogPlusSlid.dismiss();
                new Handler().postDelayed(() -> new ConfirmSelectDialog(getActivity())
                        .setContentStr("暂无人员图层数据，是否进入列表查看人员信息")
                        .setListener(new ConfirmDialogListener() {
                            @Override
                            public void onYesClick() {
                                PatrolEnterPointActivity.gotoPersonListActivity(getActivity());
                            }

                            @Override
                            public void onNoClick() {

                            }
                        }).show(), Constant.DIALOG_TIME);
            }
        } else {
            ToastBuilder.showShortWarning("暂无人员信息");
        }
    }

    @Override
    public void onGetHdDataSuccess(List<HdOrderBean> body) {
        if (body.size() > 0) {
            new Thread(() -> {
                for (int i = 0; i < body.size(); i++) {
                    // 1、待派单 2、已派单 3、接单 4、回报处理  5、拒单 6、完成上报  7、驳回  8、作废  9、完成
                    if (!StringUtils.isTrimEmpty(body.get(i).getGisArea())) {
                        Point point = new Point(Double.valueOf(body.get(i).getGisArea().trim().split(",")[0]),
                                Double.valueOf(body.get(i).getGisArea().trim().split(",")[1]), reference);
                        MapUtil.addPointLayer(mapMv, hdOverlay, getActivity(),
                                ("1".equals(body.get(i).getHdStatus()) || "2".equals(body.get(i).getHdStatus())) ? R.mipmap.icon_hd_end :
                                        "9".equals(body.get(i).getHdStatus()) ? R.mipmap.icon_hd_unexecuted : R.mipmap.icon_hd_ing
                                , point, MapUtil.jsonStr2MapInMap(body.get(i), MapConstant.HD_ATTR_NAME), false, false);
                    }
                }
            }).start();
        } else {
            ToastBuilder.showShortWarning("暂无隐患图层数据");
        }
    }

    @Override
    public void onGetSiteDataSuccess(List<SiteBean> body) {
        if (body.size() > 0) {
            new Thread(() -> {
                for (int i = 0; i < body.size(); i++) {
                    // 1、待派单 2、已派单 3、接单 4、回报处理  5、拒单 6、完成上报  7、驳回  8、作废  9、完成
                    if (!StringUtils.isTrimEmpty(body.get(i).getGisArea())) {
                        Point point = new Point(Double.valueOf(body.get(i).getGisArea().trim().split(",")[0]),
                                Double.valueOf(body.get(i).getGisArea().trim().split(",")[1]), reference);
                        MapUtil.addPointLayer(mapMv, siteOverlay, getActivity(),
                                ("1".equals(body.get(i).getSiteStatus()) || "2".equals(body.get(i).getSiteStatus())) ? R.mipmap.icon_site_end :
                                        "9".equals(body.get(i).getSiteStatus()) ? R.mipmap.icon_site_unexecuted : R.mipmap.icon_site_ing
                                , point, MapUtil.jsonStr2MapInMap(body.get(i), MapConstant.GD_ATTR_NAME), false, false);
                    }
                }
            }).start();
        } else {
            ToastBuilder.showShortWarning("暂无工地图层数据");
        }
    }

    @Override
    public void onGetCarDataSuccess(BaseGetResponse<CarBean> bean) {
        //todo
        List<CarBean> body = bean.getRows();
        if (body.size() > 0) {
            for (int i = 0; i < body.size(); i++) {
                if (!StringUtils.isTrimEmpty(body.get(i).getGisArea())) {
                    Point point = new Point(Double.valueOf(body.get(i).getGisArea().trim().split(",")[0]),
                            Double.valueOf(body.get(i).getGisArea().trim().split(",")[1]), reference);
                    MapUtil.addPointLayer(mapMv, carOverlay, getActivity(), R.mipmap.icon_car_layer
                            , point, MapUtil.jsonStr2MapInMap(body.get(i), MapConstant.CAR_ATTR_NAME), false, false);
                }
            }
        } else {
            ToastBuilder.showShortWarning("暂无车辆图层数据");
        }
    }

    @Override
    public void onGetAreaDataSuccess(BaseGetResponse<PatrolTaskResultBean> bean) {
        List<PatrolTaskResultBean> body = bean.getRows();
        if (body.size() > 0) {
            ToastBuilder.showShortWarning("暂无片区图层数据");
        } else {
            ToastBuilder.showShortWarning("暂无片区图层数据");
        }
    }

    @Override
    public void onUpdateCheckDataSuccess(BasePostResponse<Object> body) {
        if (body.isSuccess()) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            // 巡检点上报成功
            if (mDialogPlus.isShowing()) {
                mDialogPlus.dismiss();
            }
            ToastBuilder.showShort(body.getMsg());
        } else {
            ToastBuilder.showShortWarning(body.getMsg());
        }
    }

    @Override
    public void onGetPatrolMapGridListSuccess(BaseGetResponse<MapGridBean> body) {
        mMapGridBeans = body.getRows();
    }

    private void mapSearchHind() {
        mapSearchDetail.setVisibility(View.GONE);
        if (KeyboardUtils.isSoftInputVisible(Objects.requireNonNull(getActivity()))) {
            KeyboardUtils.toggleSoftInput();
            KeyboardUtils.hideSoftInput(getActivity());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataChange(MsgEvent<EventGpsBean> msgEvent) {
        if (msgEvent.getTag() != 3) {
            return;
        }
        EventGpsBean bean = msgEvent.getData();
        if (bean.getTag() == 4) {
            mapTab.setCurrentTab(2);
            anim(true);
        } else if (bean.getTag() == 2) {
            if (mapLineDialogView.getVisibility() == View.VISIBLE) {
                // 弹窗|搜索界面 正在显示，则隐藏，否则退出
                showMapLine(false, type);
            } else if (mapSearchDetail.getVisibility() == View.VISIBLE) {
                mapSearchHind();
            } else {
                ((CdqjMainActivity) Objects.requireNonNull(getActivity())).back();
            }
        } else if (bean.getTag() == 3) {
            if ("good".equals(bean.getGpsAccuracy())) {
                // GPS信号良好
                mapSignal.setImageResource(R.mipmap.icon_gps_positioning);
            } else if ("wf".equals(bean.getGpsAccuracy())) {
                // "wf"： wifi定位结果 “cl“: cell定位结果 “ll”：GPS定位结果 null 没有获取到定位结果采用的类型
                mapSignal.setImageResource(R.mipmap.icon_gps_type_network);
            } else if ("cl".equals(bean.getGpsAccuracy())) {
                mapSignal.setImageResource(R.mipmap.icon_gps_type_off);

            } else if ("ll".equals(bean.getGpsAccuracy())) {
                mapSignal.setImageResource(R.mipmap.icon_gps_positioning);

            } else if (bean.getGpsAccuracy() == null) {
                mapSignal.setImageResource(R.mipmap.icon_gps_type_fail);
            }
        }
    }

    /**
     * 根据类型显隐界面字段
     *
     * @param type type
     */
    private void setViewShowByType(String type) {
        if ("漏气".equals(type)) {
            updateHdFsfsTitle.setVisibility(View.VISIBLE);
            updateHdFsfs.setVisibility(View.VISIBLE);
            updateHdLqsbTitle.setVisibility(View.VISIBLE);
            updateHdLqsb.setVisibility(View.VISIBLE);
            updateHdYhlxTitle.setVisibility(View.GONE);
            updateHdYhlx.setVisibility(View.GONE);
            hdSubmit.put("propYhlx", "");
            updateHdYhlx.setText("");
        } else {
            updateHdFsfsTitle.setVisibility(View.GONE);
            updateHdFsfs.setVisibility(View.GONE);
            hdSubmit.put("propFsfs", "");
            updateHdFsfs.setText("");
            updateHdLqsbTitle.setVisibility(View.GONE);
            updateHdLqsb.setVisibility(View.GONE);
            hdSubmit.put("propLqsb", "");
            updateHdLqsb.setText("");
            updateHdYhlxTitle.setVisibility(View.VISIBLE);
            updateHdYhlx.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 域界面控制
     */
    private void setDoMainShow() {
        dicCacheDaosSite = SqlDataPresenter.getSiteLevel(realm);
        if (!dicCacheDaosSite.isEmpty()) {
            siteSubmit.put("siteLevel", dicCacheDaosSite.get(0).getDicCode());
            updateSiteType.setText(dicCacheDaosSite.get(0).getDicName());
        }
        dicCacheDaos = SqlDataPresenter.getHdLevel(realm);
        if (!dicCacheDaos.isEmpty()) {
            hdSubmit.put("hdGrand", dicCacheDaos.get(0).getDicCode());
            updateHdLevel.setText(dicCacheDaos.get(0).getDicName());
        }
        if (1 == doMainId) {
            updateHdLzLl.setVisibility(View.VISIBLE);
            updateSiteLzLl.setVisibility(View.VISIBLE);
            updateHdDtLl.setVisibility(View.GONE);
            updateSiteDtLl.setVisibility(View.GONE);

            fsfs = SqlDataPresenter.getFsfsType(realm);
            if (!fsfs.isEmpty()) {
                hdSubmit.put("propFsfs", fsfs.get(0).getDicCode());
                updateHdFsfs.setText(fsfs.get(0).getDicName());
            }
            name = SqlDataPresenter.getNameType(realm);
            if (!name.isEmpty()) {
                hdSubmit.put("propName", name.get(0).getDicCode());
                updateHdSslb.setText(name.get(0).getDicName());
            }
            gc = SqlDataPresenter.getGcType(realm);
            if (!gc.isEmpty()) {
                hdSubmit.put("propGc", gc.get(0).getDicCode());
                updateHdGc.setText(gc.get(0).getDicName());
            }
            fj = SqlDataPresenter.getFjType(realm);
            if (!fj.isEmpty()) {
                hdSubmit.put("propFj", fj.get(0).getDicCode());
                updateHdGj.setText(fj.get(0).getDicName());
            }
            ffxz = SqlDataPresenter.getFfxzType(realm);
            if (!ffxz.isEmpty()) {
                hdSubmit.put("propFfxz", ffxz.get(0).getDicCode());
                updateHdFfxz.setText(ffxz.get(0).getDicName());
            }
            lqsb = SqlDataPresenter.getLqsbType(realm);
            if (!lqsb.isEmpty()) {
                hdSubmit.put("propLqsb", lqsb.get(0).getDicCode());
                updateHdLqsb.setText(lqsb.get(0).getDicName());
            }
            lx = SqlDataPresenter.getLxType(realm);
            if (!lx.isEmpty()) {
                hdSubmit.put("propLx", lx.get(0).getDicCode());
                updateHdLx.setText(lx.get(0).getDicName());
                setViewShowByType(lx.get(0).getDicName());
            }
            yhlx = SqlDataPresenter.getYhlxType(realm);
            if (!yhlx.isEmpty()) {
                hdSubmit.put("propYhlx", yhlx.get(0).getDicCode());
                updateHdYhlx.setText(yhlx.get(0).getDicName());
            }
            // 工地
            conType = SqlDataPresenter.getConType(realm);
            if (!conType.isEmpty()) {
                siteSubmit.put("conType", conType.get(0).getDicCode());
                updateSiteConType.setText(conType.get(0).getDicName());
            }
            propJhyy = SqlDataPresenter.getPropJhyy(realm);
            if (!propJhyy.isEmpty()) {
                siteSubmit.put("propJhyy", propJhyy.get(0).getDicCode());
                updateSitePropJhyy.setText(propJhyy.get(0).getDicName());
            }
        } else {
            updateHdLzLl.setVisibility(View.GONE);
            updateSiteLzLl.setVisibility(View.GONE);
            updateHdDtLl.setVisibility(View.VISIBLE);
            updateSiteDtLl.setVisibility(View.VISIBLE);

            if (postResponse == null) {
                mPresenter.getCombobox();
            }
            hdDevice = SqlDataPresenter.getHdDeviceType(realm);
            if (!hdDevice.isEmpty()) {
                hdSubmit.put("hdObject", hdDevice.get(0).getDicCode());
                updateHdDx.setText(hdDevice.get(0).getDicName());
            }
            canton = SqlDataPresenter.getCanton(realm);
            if (!canton.isEmpty()) {
                hdSubmit.put("hdCanton", canton.get(0).getDicCode());
                updateHdXzqq.setText(canton.get(0).getDicName());
                siteSubmit.put("district", canton.get(0).getDicCode());
                updateSiteXzqy.setText(canton.get(0).getDicName());
            }
        }
    }
}
