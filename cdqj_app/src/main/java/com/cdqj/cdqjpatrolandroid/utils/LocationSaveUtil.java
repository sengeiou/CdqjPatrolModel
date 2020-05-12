package com.cdqj.cdqjpatrolandroid.utils;

import com.baidu.location.BDLocation;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.cdqj.cdqjpatrolandroid.config.GlobalConfig;
import com.cdqj.cdqjpatrolandroid.gis.util.CoordinateUtils;
import com.cdqj.cdqjpatrolandroid.gis.util.GpsUtils;
import com.cdqj.cdqjpatrolandroid.base.App;
import com.cdqj.cdqjpatrolandroid.bean.AppInfo;
import com.cdqj.cdqjpatrolandroid.bean.LocationBean;
import com.cdqj.cdqjpatrolandroid.bean.MapGridBean;
import com.cdqj.cdqjpatrolandroid.bean.TerminalInfoBean;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.esri.arcgisruntime.geometry.Point;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by lyf on 2018/9/20 19:25
 *
 * @author lyf
 * desc：坐标存取类
 */
public class LocationSaveUtil {

    private static List<LocationBean> locationInfoBeans = new ArrayList<>();
    /**
     * 坐标采集次数 以第一次采集坐标开始算起（为了计算如果第五次坐标也没有变化则直接保存坐标）
     */
    private static int count;


    /**
     * 终端信息存储
     *
     * @param mRealm   Realm
     */
    public static void saveTerminalInfo(Realm mRealm) {
        String username = PreferencesUtil.getString(Constant.USER_NAME);
        if (!StringUtils.isTrimEmpty(username)) {
            TerminalInfoBean bean = new TerminalInfoBean();
            bean.setTime(System.currentTimeMillis());
            bean.setImei(DeviceUtils.getAndroidID());
            bean.setUserId(String.valueOf(PreferencesUtil.getInt(Constant.USER_ID)));
            bean.setUserName(username);
            bean.setTrueName(PreferencesUtil.getString(Constant.TRUE_NAME));
            bean.setSignal(PreferencesUtil.getString(Constant.PHONE_SINGLE));
            bean.setPhoneModel(DeviceUtils.getModel());
//            bean.setRegistrationId(App.registrationID);
            bean.setPhoneVersion(DeviceUtils.getSDKVersionName());
            // 存储其他已安装app信息
            List<AppUtils.AppInfo> appInfos = AppUtils.getAppsInfo();
            RealmList<AppInfo> appInfoList = new RealmList<>();
            for (AppUtils.AppInfo appInfo : appInfos) {
                // String packageName, String name, String packagePath, String versionName, int versionCode, boolean isSystem
                appInfoList.add(new AppInfo(appInfo.getPackageName(), appInfo.getName(), appInfo.getPackagePath(),
                        appInfo.getVersionName(), appInfo.getVersionCode(), appInfo.isSystem()));
            }
            bean.setInstalledAppList(appInfoList);
            bean.setVersion(AppUtils.getAppVersionName());
            // NetworkUtils.getNetworkOperatorName: 获取移动网络运营商名称
            bean.setNetworkOperatorName(NetworkUtils.getNetworkOperatorName());
            // NetworkUtils.getNetworkType        : 获取当前网络类型
            //  NETWORK_ETHERNET,
            //  NETWORK_WIFI,
            //  NETWORK_4G,
            //  NETWORK_3G,
            //  NETWORK_2G,
            //  NETWORK_UNKNOWN,
            //  NETWORK_NO
            bean.setNetwork(NetworkUtils.getNetworkType() == NetworkUtils.NetworkType.NETWORK_ETHERNET?"以太网":
                    NetworkUtils.getNetworkType() == NetworkUtils.NetworkType.NETWORK_WIFI?"WIFI":
                            NetworkUtils.getNetworkType() == NetworkUtils.NetworkType.NETWORK_4G?"4G":
                                    NetworkUtils.getNetworkType() == NetworkUtils.NetworkType.NETWORK_3G?"3G":
                                            NetworkUtils.getNetworkType() == NetworkUtils.NetworkType.NETWORK_2G?"2G":
                                                    NetworkUtils.getNetworkType() == NetworkUtils.NetworkType.NETWORK_NO?"无网络":"未知");
            bean.setEnergy(String.valueOf(PreferencesUtil.getInt(Constant.BATTERY_LEVEL)));
            // 本APP已使用流量 TODO
            bean.setTraffic(TrafficUtils.getDataSize(TrafficUtils.getInstant(App.getInstance(), null).getTrafficInfo()));
            mRealm.executeTransactionAsync(realm -> realm.copyToRealmOrUpdate(bean));
        }
    }


    /**
     * 百度存取坐标
     *
     * @param mRealm   Realm
     * @param location location
     */
    public static void saveBdLocation(Realm mRealm, BDLocation location, BasePostResponse<List<MapGridBean>> mMapGridBeans) {
        String username = PreferencesUtil.getString(Constant.USER_NAME);
        if (!StringUtils.isTrimEmpty(username)
                && ObjectUtils.isNotEmpty(location)) {
            if ("NaN".equalsIgnoreCase(String.valueOf(location.getLatitude()))
                    || "NaN".equalsIgnoreCase(String.valueOf(location.getLongitude()))) {
                return;
            }
            if (ObjectUtils.isEmpty(mMapGridBeans)) {
                return;
            }
            if (String.valueOf(location.getLatitude()).contains("E")
                    || String.valueOf(location.getLongitude()).contains("E")) {
                return;
            }
            double[] gpsPoint = CoordinateUtils.gcj02ToWGS84(location.getLongitude(), location.getLatitude());
            double lat = gpsPoint[1];
            double lon = gpsPoint[0];
            try {
                //向数据库存取当前坐标值和地址
                LocationBean mpi = new LocationBean();
                mpi.setLat(lat);
                mpi.setLon(lon);
                mpi.setDirection(location.getDirection());
                mpi.setAddress(location.getAddrStr());
                mpi.setImei(DeviceUtils.getAndroidID());
                // 1、上班 2、下班 3、离线 4、越界
                if (GlobalConfig.isDoWork) {
                    // 判断是否越界
                    Point point = new Point(lon, lat);
                    if (!mMapGridBeans.isSuccess()) {
                        mpi.setStatus(1);
                    } else {
                        if (ObjectUtils.isEmpty(mMapGridBeans.getObj())) {
                            return;
                        }
                        if (mMapGridBeans.getObj().size() == 0) {
                            return;
                        }
                        if (isWithInTo(point, mMapGridBeans.getObj())) {
                            mpi.setStatus(1);
                        } else {
                            mpi.setStatus(4);
                        }
                    }
                } else {
                    mpi.setStatus(2);
                }
                mpi.setUpdate(false);
                mpi.setAddUserId(PreferencesUtil.getInt(Constant.USER_ID));
                mpi.setAddUserExp(PreferencesUtil.getString(Constant.TRUE_NAME));
                mpi.setUserId(PreferencesUtil.getInt(Constant.USER_ID));
                mpi.setDomainId(PreferencesUtil.getInt(Constant.DOMAIN_ID));
                mpi.setUserName(username);
                mpi.setPower(PreferencesUtil.getInt(Constant.BATTERY_LEVEL));
                mpi.setSignal(PreferencesUtil.getString(Constant.PHONE_SINGLE));
                mpi.setGps(location.getSatelliteNumber() > 0 ? location.getSatelliteNumber() : 0);
                mpi.setAddTime(TimeUtils.date2String(new Date()));
                mpi.setTime(System.currentTimeMillis());
                mpi.setCollectionTime(location.getTime());
                mpi.setVersion(AppUtils.getAppVersionName());

                double speed;

                if (location.getSpeed() < 0) {
                    // 速度为负取反
                    speed = -location.getSpeed();
                } else if ("NaN".equals(String.valueOf(location.getSpeed()))) {
                    speed = 0.0;
                } else {
                    speed = location.getSpeed();
                }
                mpi.setSpeed(speed);

                double distance;
                // 查询上一个点对比距离                 ;
                List<LocationBean> list = mRealm.copyFromRealm(mRealm.where(LocationBean.class)
                        .notEqualTo("status", 2)
                        .greaterThan("time", System.currentTimeMillis() - Constant.MAX_TIME)
                        .findAll());
                LocationBean mpiStart = null;
                if (ObjectUtils.isNotEmpty(list)) {
                    mpiStart = list.get(list.size()-1);
                }
                // 存储其他已安装app信息
//                List<AppUtils.AppInfo> appInfos = AppUtils.getAppsInfo();
//                RealmList<AppInfo> appInfoList = new RealmList<>();
//                for (AppUtils.AppInfo appInfo : appInfos) {
//                    // String packageName, String name, String packagePath, String versionName, int versionCode, boolean isSystem
//                    appInfoList.add(new AppInfo(appInfo.getPackageName(), appInfo.getName(), appInfo.getPackagePath(),
//                            appInfo.getVersionName(), appInfo.getVersionCode(), appInfo.isSystem()));
//                }
//                mpi.setAppInfos(appInfoList);
                if (ObjectUtils.isNotEmpty(mpiStart)) {
                    distance = GpsUtils.getDistance(mpiStart.getLon(), mpiStart.getLat(), lon, lat);
                    LogUtils.d("距离：" + distance + "==时间：" + mpiStart.getAddTime());
                    mpi.setDistance(distance);
                    long endTime = mpi.getTime();
                    long startTime = mpiStart.getTime();
                    if (endTime <= startTime) {
                        return;
                    }
                    mpi.setConsumTime((int) (endTime - startTime));
                    // 时间间隔大于指定分钟数距离存储为0
                    if ((endTime - startTime) >= Constant.MAX_TIME) {
                        distance = 0.0;
                        mpi.setDistance(distance);
                    }
                    // 时间间隔大于指定分钟数间隔时间存储为0
                    if ((endTime - startTime) >= Constant.UPDATE_POINT_TIME) {
                        mpi.setConsumTime(0);
                    }
                    // 小于等于400米/10秒（400米/10秒-->144KM/时）才存入数据库
                    if (distance <= Constant.SAVE_POINT_TIME * Constant.MAX_SPEED / 1000) {
                        if (distance < 0) {
                            // 距离为负取反
                            distance = -distance;
                        }
                        // 距离大于最大距离
                        if (distance > Constant.MAX_DISTANCE) {
                            return;
                        }
                        mpi.setDistance(distance);
                        // 存储 LocationBean
                        mRealm.executeTransactionAsync(realm -> realm.copyToRealmOrUpdate(mpi));
                    }
                } else {
                    mRealm.executeTransactionAsync(realm -> realm.copyToRealmOrUpdate(mpi));
//                    // 如果是第一个坐标点
//                    if (locationInfoBeans.size() <= 3) {
//                        locationInfoBeans.add(mpi);
//                        double dis1;
//                        // 如果坐标个数为3，则进行距离判断
//                        if (locationInfoBeans.size() == 2) {
//                            dis1 = GpsUtils.getDistance(locationInfoBeans.get(0).getLon(), locationInfoBeans.get(0).getLat(),
//                                    locationInfoBeans.get(1).getLon(), locationInfoBeans.get(1).getLat());
//                            if (dis1 <= 1.0) {
//                                locationInfoBeans.remove(1);
//                                count++;
//                                if (count == 5) {
//                                    // 如果执行了五次间距还是为0则直接存第一个坐标
//                                    locationInfoBeans.get(0).setDistance(0);
//                                    // 存储 locationInfoBeans
//                                    mRealm.executeTransactionAsync(realm -> realm.copyToRealmOrUpdate(locationInfoBeans));
//                                    count = 0;
//                                    locationInfoBeans.clear();
//                                }
//                            } else {
//                                count = 0;
//                            }
//                        } else if (locationInfoBeans.size() == 3) {
//                            dis1 = GpsUtils.getDistance(locationInfoBeans.get(0).getLon(), locationInfoBeans.get(0).getLat(),
//                                    locationInfoBeans.get(1).getLon(), locationInfoBeans.get(1).getLat());
//                            double dis2 = GpsUtils.getDistance(locationInfoBeans.get(1).getLon(), locationInfoBeans.get(1).getLat(),
//                                    locationInfoBeans.get(2).getLon(), locationInfoBeans.get(2).getLat());
//                            locationInfoBeans.get(0).setDistance(0);
//                            locationInfoBeans.get(1).setDistance(dis1);
//                            locationInfoBeans.get(2).setDistance(dis2);
//                            if (dis1 > Constant.SAVE_POINT_TIME * Constant.MAX_SPEED / 1000 && dis2 < Constant.SAVE_POINT_TIME * Constant.MAX_SPEED / 1000) {
//                                // 第一个点超出正常定位模式删除
//                                locationInfoBeans.get(1).setDistance(0);
//                                locationInfoBeans.remove(0);
//                                // 存储 locationInfoBeans
//                                mRealm.executeTransactionAsync(realm -> realm.copyToRealmOrUpdate(locationInfoBeans));
//                                locationInfoBeans.clear();
//                            } else if (dis1 < Constant.SAVE_POINT_TIME * Constant.MAX_SPEED / 1000 && dis2 < Constant.SAVE_POINT_TIME * Constant.MAX_SPEED / 1000) {
//                                // 第一个点及后续点正常则直接存
//                                // 存储 locationInfoBeans
//                                mRealm.executeTransactionAsync(realm -> realm.copyToRealmOrUpdate(locationInfoBeans));
//                                locationInfoBeans.clear();
//                            } else if (dis1 < Constant.SAVE_POINT_TIME * Constant.MAX_SPEED / 1000 && dis2 > Constant.SAVE_POINT_TIME * Constant.MAX_SPEED / 1000) {
//                                // 最后一个点不正常
//                                locationInfoBeans.remove(2);
//                                // 存储 locationInfoBeans
//                                mRealm.executeTransactionAsync(realm -> realm.copyToRealmOrUpdate(locationInfoBeans));
//                                locationInfoBeans.clear();
//                            } else {
//                                // 所有点不正常 删除所有
//                                locationInfoBeans.clear();
//                            }
//                        }
//                    } else {
//                        // 存储 locationInfoBeans
//                        mRealm.executeTransactionAsync(realm -> realm.copyToRealmOrUpdate(locationInfoBeans));
//                        locationInfoBeans.clear();
//                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断越界
     *
     * @param point         point
     * @param mMapGridBeans mMapGridBeans
     * @return boolean
     */
    private static boolean isWithInTo(Point point, List<MapGridBean> mMapGridBeans) {
        List<Point> points;
        try {
            for (MapGridBean mapGridBean : mMapGridBeans) {
                points = new ArrayList<>();
                if (!StringUtils.isTrimEmpty(mapGridBean.getGridAreas())) {
                    String[] xys = mapGridBean.getGridAreas().split(" ");
                    for (String xy : xys) {
                        points.add(new Point(Double.valueOf(xy.split(",")[0]), Double.valueOf(xy.split(",")[1])));
                    }
                    if (GpsUtils.isWithIn(point, points)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
