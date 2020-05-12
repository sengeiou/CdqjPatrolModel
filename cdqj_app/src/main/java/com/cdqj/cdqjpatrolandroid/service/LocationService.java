package com.cdqj.cdqjpatrolandroid.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.config.CdqjInitDataConfig;
import com.cdqj.cdqjpatrolandroid.config.GlobalConfig;
import com.cdqj.cdqjpatrolandroid.event.EventGpsBean;
import com.cdqj.cdqjpatrolandroid.event.MsgEvent;
import com.cdqj.cdqjpatrolandroid.event.NetworkChangeEvent;
import com.cdqj.cdqjpatrolandroid.gis.util.CoordinateUtils;
import com.cdqj.cdqjpatrolandroid.gis.util.GpsUtils;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.base.App;
import com.cdqj.cdqjpatrolandroid.bean.MapGridBean;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;
import com.cdqj.cdqjpatrolandroid.http.PatrolApiService;
import com.cdqj.cdqjpatrolandroid.http.RetrofitManager;
import com.cdqj.cdqjpatrolandroid.utils.ComUtil;
import com.cdqj.cdqjpatrolandroid.utils.LocationNotificationUtils;
import com.cdqj.cdqjpatrolandroid.utils.LocationSaveUtil;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.utils.TransformUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.realm.Realm;


/**
 * 系统级定位服务(百度定位)
 *
 * @author lyf
 */
public class LocationService extends Service {

    private Realm mRealm;

    private LocationClient mLocationClient;
    public BDLocation location;
    private int gpsFlag = 0;
    private int gpsFlag2 = 1;
    private boolean isStartTask = false;

    private PowerManager.WakeLock wakeLock;

    private Notification notification;

    private BatteryBroadcastReceiver bbr;

    private BasePostResponse<List<MapGridBean>> mMapGridBeans;

    private Handler handlerDoWorkLog = new Handler();


    Runnable runnableDoWorkLog = new Runnable() {

        @Override
        public void run() {
            // 提示用户上班
            try {
                if (!GlobalConfig.isDoWork && CdqjInitDataConfig.isNeedWork) {
                    ComUtil.showSound(R.raw.audio_do_work_log);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                handlerDoWorkLog.postDelayed(this, Constant.DO_WORK_LOG_TIME);
            }
        }
    };


    /**
     * 坐标保存定时任务
     */
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            // handler自带方法实现定时器
            // 存坐标 判断信号是否正常
            LocationSaveUtil.saveBdLocation(mRealm, location, mMapGridBeans);
            mHandler.postDelayed(mRunnable, Constant.SAVE_POINT_TIME);
        }
    };

    /**
     * 终端信息保存定时任务
     */
    private Handler mHandlerTerminal = new Handler();
    private Runnable mRunnableTerminal = new Runnable() {
        @Override
        public void run() {
            // 存储终端信息
            LocationSaveUtil.saveTerminalInfo(mRealm);
            mHandlerTerminal.postDelayed(mRunnableTerminal, Constant.SAVE_TERMINAL_INFO_TIME);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        LocationBinder binder = new LocationBinder();
        if (!isStartTask) {
            if (ObjectUtils.isNotEmpty(mLocationClient) && ObjectUtils.isNotEmpty(notification)) {
                // 调起前台定位
                mLocationClient.restart();
                mLocationClient.enableLocInForeground(1001, notification);

            }
            if (ObjectUtils.isNotEmpty(handlerDoWorkLog) && ObjectUtils.isNotEmpty(runnableDoWorkLog)) {
                handlerDoWorkLog.postDelayed(runnableDoWorkLog, 5000);
            }
            if (ObjectUtils.isNotEmpty(mHandlerTerminal) && ObjectUtils.isNotEmpty(runnableDoWorkLog)) {
                mHandlerTerminal.postDelayed(mRunnableTerminal, 5000);
            }
        }
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.d("LocationService------>onCreate()");

        mRealm = Realm.getDefaultInstance();
        isStartTask = true;
        getBattery();
        mLocationClient = new LocationClient(App.getInstance());
        mLocationClient.registerLocationListener(new MyLocationListener());
        EventBus.getDefault().register(this);

        LocationClientOption option = new LocationClientOption();
        // 设置定位模式
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        // 返回的定位结果是gcj02坐标系
        option.setCoorType("gcj02");
        // 设置发起定位请求的间隔时间为time S
        option.setScanSpan(2000);
        // 设置是否需要地址
        option.setIsNeedAddress(true);
        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);
        //可选，V7.2版本新增能力
        option.setWifiCacheTimeOut(5*60*1000);
        //如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位

        //可选，设置是否收集Crash信息，默认收集，即参数为false
        option.SetIgnoreCacheException(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false
        option.setEnableSimulateGps(false);

        mLocationClient.setLocOption(option);

        //设置后台定位
        Notification.Builder builder;
        //android8.0及以上使用NotificationUtils
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocationNotificationUtils mNotificationUtils = new LocationNotificationUtils(this.getApplicationContext());
            builder = mNotificationUtils.getAndroidChannelNotification
                    (AppUtils.getAppName(), AppUtils.getAppName() + "GPS定位中...");
        } else {
            builder = new Notification.Builder(this.getApplicationContext());
            //获取一个Notification构造器
            Intent nfIntent = new Intent(this.getApplicationContext(), LocationService.class);
            // 设置PendingIntent
            builder.setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0))
                    // 设置下拉列表里的标题
                    .setContentTitle(AppUtils.getAppName())
                    // 设置状态栏内的小图标
                    .setSmallIcon(R.mipmap.ic_launcher)
                    // 设置上下文内容
                    .setContentText(AppUtils.getAppName() + "GPS定位中...")
                    .setAutoCancel(true)
                    // 设置该通知发生的时间
                    .setWhen(System.currentTimeMillis());
            //获取一个Notification构造器
        }
        notification = builder.build();
        //设置为默认的声音
        notification.defaults = Notification.DEFAULT_SOUND;

        // 调起前台定位
        mLocationClient.start();
        mLocationClient.enableLocInForeground(1001, notification);

        mHandler.postDelayed(mRunnable, 0);
        mHandlerTerminal.postDelayed(mRunnableTerminal, 5000);
        handlerDoWorkLog.postDelayed(runnableDoWorkLog, 5000);

        // patrol/patrolGroupUsers/getMapGrids
        // 获取当前登陆人的  网格ID 进入巡线模块后进行加载 ， 获取后，将数据缓存
        RetrofitManager.getInstance().create(PatrolApiService.class)
                .getMapGrids()
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(new BaseSubscriber<BasePostResponse<List<MapGridBean>>>() {
                    @Override
                    public void onResult(BasePostResponse<List<MapGridBean>> postResponse) {
                        mMapGridBeans = postResponse;
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        LogUtils.d(e.message);
                        e.printStackTrace();
                    }
                });
    }

    /**
     * 实现实位回调监听
     */
    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            StringBuilder sb = new StringBuilder(256);
            if (location != null) {
                getPhoneSingle();
                LocationService.this.location = location;
                sb.append("时间 : ");
                //服务端出本次结果的时间，如果位置不发生变化，则时间不变
                sb.append(location.getTime());
                sb.append("\n返回码 : ");
                sb.append(location.getLocType());
                sb.append("\n纬度 : ");
                sb.append(location.getLatitude());
                sb.append("\n经度 : ");
                sb.append(location.getLongitude());
                sb.append("\n半径: ");
                sb.append(location.getRadius());
                if (location.getLocType() == BDLocation.TypeGpsLocation) {
                    //GPS定位结果
                    sb.append("\n速度 : ");
                    sb.append(location.getSpeed());
                    sb.append("\n卫星 : ");
                    sb.append(location.getSatelliteNumber());
                    sb.append("\n方向 : ");
                    sb.append("\n地址 : ");
                    sb.append(location.getAddrStr());
                    sb.append(location.getDirection());
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                    //网络定位结果
                    sb.append("\n地址 : ");
                    sb.append(location.getAddrStr());
                    // 运营商信息
                    sb.append("\n运营商 : ");
                    sb.append(getOperatorName(location.getOperators()));
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
                    //离线定位结果
                    sb.append("\n离线地址 : ");
                    sb.append(location.getAddrStr());
                    sb.append("\n离线运营商 : ");
                    sb.append(getOperatorName(location.getOperators()));
                }
                if(location.getGpsAccuracyStatus() == BDLocation.GPS_ACCURACY_BAD){
                    //提示用户信号弱
                    PreferencesUtil.putBoolean(Constant.LOCATION_SATELLITE_NUMBER_FLAG,false);
                    gpsFlag2 = 0;
                    if (gpsFlag==0) {
                        EventBus.getDefault().post(new MsgEvent<>(3, new EventGpsBean(3, location.getNetworkLocationType())));
                        ComUtil.showSound(R.raw.audio_gps_bad);
                        ToastBuilder.showShort("当前GPS信号弱");
                        gpsFlag++;
                    }
                } else {
                    gpsFlag = 0;
                    PreferencesUtil.putBoolean(Constant.LOCATION_SATELLITE_NUMBER_FLAG,true);
                    if (gpsFlag2==0) {
                        EventBus.getDefault().post(new MsgEvent<>(3, new EventGpsBean(3, "good")));
                        ComUtil.showSound(R.raw.audio_gps_good);
                        ToastBuilder.showShort( "当前GPS信号正常");
                        gpsFlag2++;
                    }
                }

                // 判断是否获取到正确坐标
                if (0.0 != (location.getLatitude())
                        && !(location.getLatitude() + "").contains("E")
                        && !StringUtils.isTrimEmpty(location.getTime())) {

                    double lon = location.getLongitude();
                    double lat = location.getLatitude();
                    double[] gpsPoint = CoordinateUtils.gcj02ToWGS84(lon,lat);

                    PreferencesUtil.putString(Constant.LOCATION_LATITUDE, GpsUtils.formateRate(gpsPoint[1] + ""));
                    PreferencesUtil.putString(Constant.LOCATION_LONGITUDE, GpsUtils.formateRate(gpsPoint[0] + ""));
                    PreferencesUtil.putString(Constant.LOCATION_ADDRESS, location.getAddrStr() + "");
                    PreferencesUtil.putInt(Constant.LOCATION_SATELLITE_NUMBER, location.getSatelliteNumber());
                    PreferencesUtil.putString(Constant.LOCATION_DIRECTION, location.getDirection() + "");
                    PreferencesUtil.putString(Constant.LOCATION_SPEED, location.getSpeed() + "");
                    PreferencesUtil.putString(Constant.LOCATION_OPERATOR_NAME, getOperatorName(location.getOperators()));
                }
            }
            //LogUtils.d(sb.toString());
        }
    }

    private String getOperatorName(int type) {
        String name;
        switch (type) {
            case BDLocation.OPERATORS_TYPE_MOBILE:
                name = "中国移动运营商";
                break;
            case BDLocation.OPERATORS_TYPE_TELECOMU:
                name = "中国电信运营商";
                break;
            case BDLocation.OPERATORS_TYPE_UNICOM:
                name = "中国联通运营商";
                break;
            case BDLocation.OPERATORS_TYPE_UNKONW:
                name = "未知的运营商";
                break;
            default:
                name = "未知";
                break;
        }
        return name;
    }

    /**
     * 得到当前的手机蜂窝网络信号强度
     * 获取LTE网络和3G/2G网络的信号强度的方式有一点不同，
     * LTE网络强度是通过解析字符串获取的，
     * 3G/2G网络信号强度是通过API接口函数完成的。
     * asu 与 dbm 之间的换算关系是 dbm=-113 + 2*asu
     */
    private void getPhoneSingle() {
        final TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        PhoneStateListener myListener = new PhoneStateListener(){
            @Override
            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                super.onSignalStrengthsChanged(signalStrength);
                String signalInfo = signalStrength.toString();
                String[] params = signalInfo.split(" ");
                String  phoneSingle= "";
                if (ObjectUtils.isEmpty(tm)) {
                    return;
                }
                if(tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_LTE){
                    //4G网络 最佳范围   >-90dBm 越大越好
//                    long Itedbm = Long.parseLong(params[9]);
                    phoneSingle = String.valueOf(params[9]);
                }else if(tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSDPA ||
                        tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSPA ||
                        tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSUPA ||
                        tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS){
                    //3G网络最佳范围  >-90dBm  越大越好  ps:中国移动3G获取不到  返回的无效dbm值是正数（85dbm）
                    //在这个范围的已经确定是3G，但不同运营商的3G有不同的获取方法，故在此需做判断 判断运营商与网络类型的工具类在最下方
                    //获取当前运营商
                    String yys = NetworkUtils.getNetworkOperatorName();
                    if (yys.contains("中国移动")) {
                        //中国移动3G不可获取，故在此返回0
                        phoneSingle = String.valueOf(0);
                    }else if (yys.contains("中国联通")) {
                        int cdmaDbm = signalStrength.getCdmaDbm();
                        phoneSingle = String.valueOf(cdmaDbm);
                    }else if (yys.contains("中国电信")) {
                        int evdoDbm = signalStrength.getEvdoDbm();
                        phoneSingle = String.valueOf(evdoDbm);
                    }

                }else{
                    //2G网络最佳范围>-90dBm 越大越好
                    int asu = signalStrength.getGsmSignalStrength();
                    int dbm = -113 + 2*asu;
                    phoneSingle = String.valueOf(dbm);
                }
                PreferencesUtil.putString(Constant.PHONE_SINGLE, phoneSingle);
            }
        };
        //开始监听
        if (ObjectUtils.isNotEmpty(tm)) {
            tm.listen(myListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        }
    }

    /**
     * IBinder是远程对象的基本接口，是为高性能而设计的轻量级远程调用机制的核心部分。但它不仅用于远程
     * 调用，也用于进程内调用。这个接口定义了与远程对象交互的协议。
     * 不要直接实现这个接口，而应该从Binder派生。
     * Binder类已实现了IBinder接口
     */
    public class LocationBinder extends Binder {
        /**
         * 获取Service的方法
         */
        public LocationService getService() {
            return LocationService.this;
        }
    }

    @Override
    public void onDestroy() {
        stopServiceWork();
        super.onDestroy();
    }

    public void stopServiceWork() {
        LogUtils.d("LocationService------>onDestroy()");
        if (ObjectUtils.isNotEmpty(wakeLock)) {
            wakeLock.release();
        }
        if (ObjectUtils.isNotEmpty(mHandler)) {
            mHandler.removeCallbacks(mRunnable);
        }
        if (ObjectUtils.isNotEmpty(mHandlerTerminal)) {
            mHandlerTerminal.removeCallbacks(mRunnableTerminal);
        }
        if (ObjectUtils.isNotEmpty(handlerDoWorkLog)) {
            handlerDoWorkLog.removeCallbacks(runnableDoWorkLog);
        }
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
            // 关闭前台定位，同时移除通知栏
            mLocationClient.disableLocInForeground(true);
            mLocationClient = null;
        }
        if (ObjectUtils.isNotEmpty(mRealm)) {
            mRealm.close();
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        unregisterReceiver(bbr);
    }

    /**
     * 注册电量广播，获取手机电量信息
     */
    private void getBattery() {
        bbr = new BatteryBroadcastReceiver();
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        this.registerReceiver(bbr, mFilter);
    }

    @SuppressLint({"WrongConstant", "InvalidWakeLockTag"})
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //创建PowerManager对象
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (ObjectUtils.isNotEmpty(pm)) {
            //保持cpu一直运行，不管屏幕是否黑屏
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "CPUKeepRunning");
            wakeLock.acquire(10*60*1000L);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkChangeEvent(NetworkChangeEvent event) {
    }
}
