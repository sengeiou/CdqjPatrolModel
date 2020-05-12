package com.cdqj.cdqjpatrolandroid.view.ui.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.base.BasePictureActivity;
import com.cdqj.cdqjpatrolandroid.base.BaseRecyclerView;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmDialogListener;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmSelectDialog;
import com.cdqj.cdqjpatrolandroid.gis.bean.GisParamsBean;
import com.cdqj.cdqjpatrolandroid.gis.event.MapStatusInterface;
import com.cdqj.cdqjpatrolandroid.gis.event.MapStatusListener;
import com.cdqj.cdqjpatrolandroid.gis.event.MapTouchInterface;
import com.cdqj.cdqjpatrolandroid.gis.event.MapTouchListener;
import com.cdqj.cdqjpatrolandroid.gis.event.QueryLayerType;
import com.cdqj.cdqjpatrolandroid.gis.event.QueryParams;
import com.cdqj.cdqjpatrolandroid.gis.util.GpsUtils;
import com.cdqj.cdqjpatrolandroid.gis.util.MapConstant;
import com.cdqj.cdqjpatrolandroid.gis.util.MapToolUtil;
import com.cdqj.cdqjpatrolandroid.gis.util.MapUtil;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.image.bean.BasePhotoBean;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.bean.CarBean;
import com.cdqj.cdqjpatrolandroid.bean.DicCacheDao;
import com.cdqj.cdqjpatrolandroid.bean.GridSelectBean;
import com.cdqj.cdqjpatrolandroid.bean.HdOrderBean;
import com.cdqj.cdqjpatrolandroid.bean.MapGridBean;
import com.cdqj.cdqjpatrolandroid.bean.PatrolHdType;
import com.cdqj.cdqjpatrolandroid.bean.PatrolTaskResultBean;
import com.cdqj.cdqjpatrolandroid.bean.ResultMapSearchBean;
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
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.utils.sql.SqlDataPresenter;
import com.cdqj.cdqjpatrolandroid.view.i.IMapFragmentView;
import com.esri.arcgisruntime.data.FeatureQueryResult;
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
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

/**
 * 地图上报父界面
 *
 * @author lyf
 */
public class BaseMapUpdateActivity extends BasePictureActivity<MapFragmentPresenter> implements MapTouchInterface,
        MapStatusInterface, IMapFragmentView, OnUpdateFileBackListener, View.OnClickListener {

    MapView mapMv;
    ImageView mapUpdateGps;
    ImageView mapUpdateFullExtent;
    RelativeLayout mapUpdateBottomSheet;
    ImageView mapUpdateBack;
    ImageView updateHdEcs;
    TextView updateHdTitle;
    TextView updateHdSubmit;
    TextView updateHdDx;
    TextView updateHdType;
    TextView updateHdLevel;
    TextView updateHdXzqq;
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
    LinearLayout updateSiteDtLl;
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

    /**
     * 上报弹窗表单-隐患，工地文件
     */
    private View fileView;
    private TextView title;
    private TextView videoTitle;
    private TextView voiceTitle;
    private EditText etRemark;
    private BaseRecyclerView rvPicture;
    private BaseRecyclerView rvAudio;
    private BaseRecyclerView rvVideo;
    private ArrayList<LocalMedia> selectPictureList = new ArrayList<>();
    private ArrayList<LocalMedia> selectAudioList = new ArrayList<>();
    private ArrayList<LocalMedia> selectVideoList = new ArrayList<>();
    private DialogPlus mDialogPlus;

    private BottomSheetBehavior bottomSheetBehavior;
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
     * 网格片区图层
     */
    private GraphicsOverlay gridOverlay;

    /**
     * 导航点
     */
    private double navX = 30.572262, navY = 104.066513;

    private int position;

    private HashMap<String, String> hdSubmit = new HashMap<>();
    private HashMap<String, String> siteSubmit = new HashMap<>();
    private HashMap<String, String> pointSubmit = new HashMap<>();
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

    private ArrayList<UpImgResultBean> pictureResult = new ArrayList<>();
    private ArrayList<UpImgResultBean> audioResult = new ArrayList<>();
    private ArrayList<UpImgResultBean> videoResult = new ArrayList<>();

    /**
     * 网格编号
     */
    String gridNum = "";
    private List<MapGridBean> mMapGridBeans = new ArrayList<>();

    private Realm realm;

    @Override
    protected MapFragmentPresenter createPresenter() {
        return new MapFragmentPresenter(this, this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_base_map_update;
    }

    @Override
    protected void findView() {
        mapMv = findViewById(R.id.map_update_map);
        mapUpdateGps = findViewById(R.id.map_update_gps);
        mapUpdateFullExtent = findViewById(R.id.map_update_full_extent);
        mapUpdateBottomSheet = findViewById(R.id.map_update_bottom_sheet);
        mapUpdateBack = findViewById(R.id.map_update_back);
        updateHdEcs = findViewById(R.id.update_hd_ecs);
        updateHdTitle = findViewById(R.id.update_hd_title);
        updateHdSubmit = findViewById(R.id.update_hd_submit);
        updateHdDx = findViewById(R.id.update_hd_dx);
        updateHdType = findViewById(R.id.update_hd_type);
        updateHdLevel = findViewById(R.id.update_hd_level);
        updateHdXzqq = findViewById(R.id.update_hd_xzqq);
        updateHdAddressTitle = findViewById(R.id.update_hd_address_title);
        updateHdAddress = findViewById(R.id.update_hd_address);
        updateHdGpsTitle = findViewById(R.id.update_hd_gps_title);
        updateHdGps = findViewById(R.id.update_hd_gps);
        updateHdRemarkTitle = findViewById(R.id.update_hd_remark_title);
        updateHdRemark = findViewById(R.id.update_hd_remark);
        mapUpdateHdWindow = findViewById(R.id.map_update_hd_window);
        updateSiteEcs = findViewById(R.id.update_site_ecs);
        updateSiteTitle = findViewById(R.id.update_site_title);
        updateSiteSubmit = findViewById(R.id.update_site_submit);
        updateSiteNameTitle = findViewById(R.id.update_site_name_title);
        updateSiteName = findViewById(R.id.update_site_name);
        updateSiteTypeTitle = findViewById(R.id.update_site_type_title);
        updateSiteType = findViewById(R.id.update_site_type);
        updateSiteXzqyTitle = findViewById(R.id.update_site_xzqy_title);
        updateSiteXzqy = findViewById(R.id.update_site_xzqy);
        updateSiteSspqTitle = findViewById(R.id.update_site_sspq_title);
        updateSiteSspq = findViewById(R.id.update_site_sspq);
        updateSiteAddressTitle = findViewById(R.id.update_site_address_title);
        updateSiteAddress = findViewById(R.id.update_site_address);
        updateSiteGpsTitle = findViewById(R.id.update_site_gps_title);
        updateSiteGps = findViewById(R.id.update_site_gps);
        updateSiteRemarkTitle = findViewById(R.id.update_site_remark_title);
        updateSiteRemark = findViewById(R.id.update_site_remark);
        mapUpdateSiteWindow = findViewById(R.id.map_update_site_window);
        updateDevEcs = findViewById(R.id.update_dev_ecs);
        updateDevTitle = findViewById(R.id.update_dev_title);
        updateDevSubmit = findViewById(R.id.update_dev_submit);
        updateDevTypeTitle = findViewById(R.id.update_dev_type_title);
        updateDevType = findViewById(R.id.update_dev_type);
        updateDevAddressTitle = findViewById(R.id.update_dev_address_title);
        updateDevAddress = findViewById(R.id.update_dev_address);
        updateDevGpsxTitle = findViewById(R.id.update_dev_gpsx_title);
        updateDevGpsx = findViewById(R.id.update_dev_gpsx);
        updateDevGpsyTitle = findViewById(R.id.update_dev_gpsy_title);
        updateDevGpsy = findViewById(R.id.update_dev_gpsy);
        updateDevRemarkTitle = findViewById(R.id.update_dev_remark_title);
        updateDevRemark = findViewById(R.id.update_dev_remark);
        mapUpdateDevWindow = findViewById(R.id.map_update_dev_window);
        updatePointEcs = findViewById(R.id.update_point_ecs);
        updatePointTitle = findViewById(R.id.update_point_title);
        updatePointSubmit = findViewById(R.id.update_point_submit);
        updatePointTypeTitle = findViewById(R.id.update_point_type_title);
        updatePointType = findViewById(R.id.update_point_type);
        updatePointAddressTitle = findViewById(R.id.update_point_address_title);
        updatePointAddress = findViewById(R.id.update_point_address);
        updatePointGpsTitle = findViewById(R.id.update_point_gps_title);
        updatePointGps = findViewById(R.id.update_point_gps);
        updatePointRemarkTitle = findViewById(R.id.update_point_remark_title);
        updatePointRemark = findViewById(R.id.update_point_remark);
        mapUpdatePointWindow = findViewById(R.id.map_update_point_window);
        updateHdDtLl = findViewById(R.id.update_hd_dt_ll);
        updateHdLxTitle = findViewById(R.id.update_hd_lx_title);
        updateHdLx = findViewById(R.id.update_hd_lx);
        updateHdFsfsTitle = findViewById(R.id.update_hd_fsfs_title);
        updateHdFsfs = findViewById(R.id.update_hd_fsfs);
        updateHdSslbTitle = findViewById(R.id.update_hd_sslb_title);
        updateHdSslb = findViewById(R.id.update_hd_sslb);
        updateHdGcTitle = findViewById(R.id.update_hd_gc_title);
        updateHdGc = findViewById(R.id.update_hd_gc);
        updateHdGjTitle = findViewById(R.id.update_hd_gj_title);
        updateHdGj = findViewById(R.id.update_hd_gj);
        updateHdFfxzTitle = findViewById(R.id.update_hd_ffxz_title);
        updateHdFfxz = findViewById(R.id.update_hd_ffxz);
        updateHdLqsbTitle = findViewById(R.id.update_hd_lqsb_title);
        updateHdLqsb = findViewById(R.id.update_hd_lqsb);
        updateHdYhlxTitle = findViewById(R.id.update_hd_yhlx_title);
        updateHdYhlx = findViewById(R.id.update_hd_yhlx);
        updateHdLzLl = findViewById(R.id.update_hd_lz_ll);
        updateSiteDtLl = findViewById(R.id.update_site_dt_ll);
        updateSiteConTypeTitle = findViewById(R.id.update_site_conType_title);
        updateSiteConType = findViewById(R.id.update_site_conType);
        updateSitePropJhyyTitle = findViewById(R.id.update_site_propJhyy_title);
        updateSitePropJhyy = findViewById(R.id.update_site_propJhyy);
        updateSiteOtherTypeTitle = findViewById(R.id.update_site_otherType_title);
        updateSiteOtherType = findViewById(R.id.update_site_otherType);
        updateSiteSiteLeaderTitle = findViewById(R.id.update_site_siteLeader_title);
        updateSiteSiteLeader = findViewById(R.id.update_site_siteLeader);
        updateSiteSiteLeaderTelTitle = findViewById(R.id.update_site_siteLeaderTel_title);
        updateSiteSiteLeaderTel = findViewById(R.id.update_site_siteLeaderTel);
        updateSitePropGdssqkTitle = findViewById(R.id.update_site_propGdssqk_title);
        updateSitePropGdssqk = findViewById(R.id.update_site_propGdssqk);
        updateSitePropGdmsqkTitle = findViewById(R.id.update_site_propGdmsqk_title);
        updateSitePropGdmsqk = findViewById(R.id.update_site_propGdmsqk);
        updateSiteLzLl = findViewById(R.id.update_site_lz_ll);
    }

    @Override
    protected String getTitleText() {
        return null;
    }

    @SuppressLint({"InflateParams", "ClickableViewAccessibility"})
    @Override
    public void initView() {
        fileView = LayoutInflater.from(this).inflate(R.layout.cdqj_patrol_order_supervise_report_window, null);
        title = fileView.findViewById(R.id.supervise_report_title);
        videoTitle = fileView.findViewById(R.id.supervise_report_video_title);
        voiceTitle = fileView.findViewById(R.id.supervise_report_voice_title);
        etRemark = fileView.findViewById(R.id.supervise_report_remark);
        rvPicture = fileView.findViewById(R.id.supervise_report_picture_gv);
        rvAudio = fileView.findViewById(R.id.supervise_report_audio_gv);
        rvVideo = fileView.findViewById(R.id.supervise_report_video_gv);
        etRemark.setVisibility(View.GONE);
        super.initView();
        initMap();
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
        bottomSheetBehavior.setPeekHeight(ScreenUtils.getScreenHeight() / 3);
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

        position = getIntent().getIntExtra("flag", 0);
        switch (position) {
            case 1:
                mapUpdateSiteWindow.setVisibility(View.VISIBLE);
                break;
            case 2:
                mapUpdatePointWindow.setVisibility(View.VISIBLE);
                break;
            case 3:
                mapUpdateDevWindow.setVisibility(View.VISIBLE);
                break;
            default:
                mapUpdateHdWindow.setVisibility(View.VISIBLE);
                break;
        }

        setDoMainShow();

//        BarUtils.setNavBarVisibility(this, false);
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

    @Override
    public void initListener() {
        mapUpdateGps.setOnClickListener(this);
        mapUpdateFullExtent.setOnClickListener(this);
        mapUpdateBack.setOnClickListener(this);
        updateHdEcs.setOnClickListener(this);
        updateSiteEcs.setOnClickListener(this);
        updateDevEcs.setOnClickListener(this);
        updatePointEcs.setOnClickListener(this);
        updateHdSubmit.setOnClickListener(this);
        updateSiteSubmit.setOnClickListener(this);
        updateDevSubmit.setOnClickListener(this);
        updatePointSubmit.setOnClickListener(this);
        updateHdType.setOnClickListener(this);
        updateHdLevel.setOnClickListener(this);
        updateSiteType.setOnClickListener(this);
        updateHdDx.setOnClickListener(this);
        updateHdXzqq.setOnClickListener(this);
        updateSiteXzqy.setOnClickListener(this);
        updateHdFsfs.setOnClickListener(this);
        updateHdSslb.setOnClickListener(this);
        updateHdGc.setOnClickListener(this);
        updateHdGj.setOnClickListener(this);
        updateHdFfxz.setOnClickListener(this);
        updateHdLqsb.setOnClickListener(this);
        updateHdLx.setOnClickListener(this);
        updateHdYhlx.setOnClickListener(this);
        updateSiteConType.setOnClickListener(this);
        updateSitePropJhyy.setOnClickListener(this);
        updateSiteSspq.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.map_update_gps) {
            // gps定位
            MapToolUtil.getLocation(mapMv);
        } else if (view.getId() == R.id.map_update_full_extent) {
            // 全图
            MapToolUtil.mapFullExtent(mapMv, viewPoint);
        } else if (view.getId() == R.id.map_update_back) {
            // 返回
            finish();
        } else if (view.getId() == R.id.update_hd_ecs) {
            // 隐藏弹出框
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        } else if (view.getId() == R.id.update_site_ecs) {
            // 隐藏弹出框
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        } else if (view.getId() == R.id.update_dev_ecs) {
            // 隐藏弹出框
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        } else if (view.getId() == R.id.update_point_ecs) {
            // 隐藏弹出框
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        } else if (view.getId() == R.id.update_hd_submit) {
            // 隐患下一步
            new Handler().postDelayed(() -> showReportWindow(0), Constant.DIALOG_TIME);
        } else if (view.getId() == R.id.update_site_submit) {
            // 工地下一步
            new Handler().postDelayed(() -> showReportWindow(1), Constant.DIALOG_TIME);
        } else if (view.getId() == R.id.update_dev_submit) {
            // 设备纠错 提交
        } else if (view.getId() == R.id.update_point_submit) {
            // 巡检点提交
            pointSubmit.put("addUserLat", PreferencesUtil.getString(Constant.LOCATION_LATITUDE));
            pointSubmit.put("addUserLon", PreferencesUtil.getString(Constant.LOCATION_LONGITUDE));
            pointSubmit.put("addType", "2");
            pointSubmit.put("gridId", gridNum);
            pointSubmit.put("taskPointName", updatePointType.getText().toString());
            pointSubmit.put("remarks", updatePointRemark.getText().toString());
            pointSubmit.put("address", updatePointAddress.getText().toString());
            mPresenter.pointSubmit(pointSubmit);
        }
        List<GridSelectBean> beans;
        int i = view.getId();
        if (i == R.id.update_hd_type) {
            beans = new ArrayList<>();
            if (ObjectUtils.isNotEmpty(postResponse)) {
                for (PatrolHdType patrolHdType : postResponse) {
                    beans.add(new GridSelectBean(patrolHdType.getText(), String.valueOf(patrolHdType.getValue())));
                }
            }
            showSelect(beans, (TextView) view, 1);

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

                            mapStatusListener = new MapStatusListener(mapMv, BaseMapUpdateActivity.this);
                            mapMv.addLayerViewStateChangedListener(mapStatusListener);

                            initLayers(mGisParamsBean);
                        } else {
                            new ConfirmSelectDialog(BaseMapUpdateActivity.this)
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
            QueryParams queryParams = new QueryParams(list, null, null, 1, null);
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
        // 设置放大至指定比例尺不显示
        gridOverlay = MapUtil.addSingelLayer(mapMv, gridOverlay);
        gridOverlay.setMinScale(MapConstant.MIN_SCALE);
        tempGraphicsLayer = MapUtil.addSingelLayer(mapMv, tempGraphicsLayer);
        tempGraphicsLayer.setMinScale(MapConstant.MIN_SCALE);
        //MapToolUtil.getLocation(mapMv);
        initMapListener(mGisParamsBean);
        if (mMapGridBeans.isEmpty()) {
            mPresenter.getPatrolMapGridList();
        }
    }

    @Override
    public void getLayerErrorSuccess(String layerName) {
        ToastBuilder.showShortError(layerName + "图层加载失败...");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getMapClickPtSuccess(Point pt, int flag) {
        // 清除地图图标重新加载 
        MapUtil.addPointLayer(mapMv, tempGraphicsLayer, this, R.mipmap.icon_gps_point, pt, false);
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
                if (GpsUtils.isWithIn(pt, points)) {
                    iswithin = true;
                    gridNum = mapGridBean.getId();
                    break;
                }
            }
        }
        if (!iswithin) {
            ToastBuilder.showShortWarning("上报点不在网格片区范围");
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            return;
        }
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        //小
        String submitLat;
        //大
        String submitLon;
        submitLon = GpsUtils.formateRate(pt.getX() + "");
        submitLat = GpsUtils.formateRate(pt.getY() + "");
        switch (position) {
            case 0:
//                hdSubmit.put("lat", String.valueOf(pt.getY()));
//                hdSubmit.put("lon", String.valueOf(pt.getX()));
                hdSubmit.put("lat", submitLat);
                hdSubmit.put("lon", submitLon);
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
                break;
            default:
                break;
        }

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
        if (ObjectUtils.isNotEmpty(mapMv)) {
            mapMv.dispose();
        }
        realm.close();
        super.onDestroy();
    }

    @Override
    public void onFailure(String e) {
        ToastBuilder.showShortError(e);
    }

    @Override
    public void onUpdateFileSuccess(BasePostResponse<List<UpImgResultBean>> body, int type, int position) {
        if (body.isSuccess()) {
            switch (type) {
                case Constant.IMG_TYPE:
                    pictureResult.addAll(body.getObj());
                    break;
                case Constant.AUDIO_TYPE:
                    audioResult.addAll(body.getObj());
                    break;
                case Constant.VIDEO_TYPE:
                    videoResult.addAll(body.getObj());
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


    private void showSelect(List<GridSelectBean> beans, TextView view, int flag) {
        if (beans.size() == 0) {
            ToastBuilder.showShortWarning("当前暂无可选项");
            return;
        }
        // 隐患类型   隐患等级  工地级别   单选
        new ConfirmListDialog(this)
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
            if (mDialogPlus != null && mDialogPlus.isShowing()) {
                mDialogPlus.dismiss();
            }
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            ToastBuilder.showShort(beans.getMsg());
        } else {
            ToastBuilder.showShortWarning(beans.getMsg());
        }
    }

    @Override
    public void onGetCheckTypesSuccess(BaseGetResponse<PatrolTaskResultBean> body) {

    }

    @Override
    public void onGetPersonSuccess(List<UserLayerBean> body) {

    }

    @Override
    public void onGetSearchDataSuccess(ResultMapSearchBean body) {

    }

    @Override
    public void onGetHdDataSuccess(List<HdOrderBean> body) {

    }

    @Override
    public void onGetSiteDataSuccess(List<SiteBean> body) {

    }

    @Override
    public void onGetCarDataSuccess(BaseGetResponse<CarBean> body) {

    }

    @Override
    public void onGetAreaDataSuccess(BaseGetResponse<PatrolTaskResultBean> body) {

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
        if (mMapGridBeans.size() > 0) {
            // 加载图层
            List<Map<String, Object>> mapList = new ArrayList<>();
//            if ("唐昌".equals(Constant.appCompany)) {
//                for (int i = 0; i < mMapGridBeans.size(); i++) {
//                    MapGridBean bean = mMapGridBeans.get(i);
//                    RetrofitUtils.coorDinateTrans(bean.getGridAreas(), "2", i, (points, flag) -> {
//                        bean.setGisArea(points);
//                        bean.setName(bean.getGridNumber());
//                        mapList.add(MapUtil.jsonStr2MapInMap(bean));
//                        if (flag == mMapGridBeans.size() - 1) {
//                            String bgColorStr = mMapGridBeans.get(0).getGridColor().replaceAll("#", "#2B");
//                            MapUtil.addPolygons(this, gridOverlay, reference, mMapGridBeans, mapList, mMapGridBeans.get(0).getGridColor(), bgColorStr, true);
//                        }
//                    });
//                }
//            } else {
            for (int i = 0; i < mMapGridBeans.size(); i++) {
                mMapGridBeans.get(i).setGisArea(mMapGridBeans.get(i).getGridAreas());
                mMapGridBeans.get(i).setName(mMapGridBeans.get(i).getGridNumber());
                mapList.add(MapUtil.jsonStr2MapInMap(mMapGridBeans.get(i)));
            }
            String bgColorStr = mMapGridBeans.get(0).getGridColor().replaceAll("#", "#2B");
            MapUtil.addPolygons(this, gridOverlay, reference, mMapGridBeans, mapList, mMapGridBeans.get(0).getGridColor(), bgColorStr, true);
//            }
        } else {
            ToastBuilder.showShortError("暂无网格图层数据");
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
        mDialogPlus = DialogPlus.newDialog(this)
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
                            siteSubmit.put("siteLeader", updateSiteSiteLeader.getText().toString());
                            siteSubmit.put("siteLeaderTel", updateSiteSiteLeaderTel.getText().toString());
                            siteSubmit.put("otherType", updateSiteOtherType.getText().toString());
                            siteSubmit.put("propGdssqk", updateSitePropGdssqk.getText().toString());
                            siteSubmit.put("propGdmsqk", updateSitePropGdmsqk.getText().toString());
                            // 工地 提交
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

                    } else {
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

    @Override
    public void onBackPressed() {
        if (!bottomSheetBehavior.isHideable()) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            return;
        }
        super.onBackPressed();
    }
}
