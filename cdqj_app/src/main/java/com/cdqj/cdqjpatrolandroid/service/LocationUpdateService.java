package com.cdqj.cdqjpatrolandroid.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.cdqj.cdqjpatrolandroid.config.GlobalConfig;
import com.cdqj.cdqjpatrolandroid.gis.util.GpsUtils;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.http.RetrofitUtils;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.bean.LocationBean;
import com.cdqj.cdqjpatrolandroid.bean.TerminalInfoBean;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;
import com.cdqj.cdqjpatrolandroid.http.PatrolApiService;
import com.cdqj.cdqjpatrolandroid.http.RetrofitManager;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;
import com.cdqj.cdqjpatrolandroid.utils.TransformUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by lyf on 2018/1/30.
 * 位置信息数据上传接口
 *
 * @author lyf
 */
public class LocationUpdateService extends Service {

    private Realm mRealm;

    private PowerManager.WakeLock wakeLock;

    private Handler handler = new Handler();
    private Handler handlerDel = new Handler();
    private Handler handlerUpdate = new Handler();
    private Handler handlerTerminal = new Handler();
    private Handler handlerGps = new Handler();

    private boolean isStartTask = false;

    /**
     * 任务集合
     */
    private List<List<LocationBean>> taskList = new ArrayList<>();

    /**
     * 终端信息
     */
    private List<TerminalInfoBean> listTerminalInfo = new ArrayList<>();

    /**
     * 数据库坐标获取/坐标转换
     */
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // handler自带方法实现定时器
            // 转换坐标
            try {
                // 执行坐标上传 判断上班状态和网络状态
                // 1.数据库查询获取坐标
                // 2.保存坐标到任务集合
                // 判断巡线是否上班
                if (GlobalConfig.isDoWork
                        && NetworkUtils.isConnected()) {
                    mRealm.executeTransaction(realm -> {
                        RealmResults<LocationBean> element = realm.where(LocationBean.class)
                                .equalTo("isUpdate", false)
                                .notEqualTo("status", 2)
                                .findAll();
                        if (element.size() > 0) {
                            taskList.add(realm.copyFromRealm(element));
                            for (int i = 0; i < element.size(); i++) {
                                element.get(i).setUpdate(true);
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                handler.postDelayed(this, Constant.UPDATE_POINT_TIME);
            }
        }
    };

    Runnable runnableUpdate = new Runnable() {

        @Override
        public void run() {
            // 上传坐标
            try {
                if (!GlobalConfig.isDoWork
                        || !NetworkUtils.isConnected()) {
                    return;
                }
                // 执行坐标上传
                // 1.判断任务集合是否有任务
                // 2.有任务则上传
                if (taskList.size() > 0) {
                    if (ObjectUtils.isEmpty(taskList.get(0))) {
                        return;
                    }
                    List<LocationBean> locationBeans = taskList.get(0);
                    if (Constant.appCompany.equals("唐昌")) {
                        StringBuilder builder = new StringBuilder();
                        for (LocationBean locationBean : locationBeans) {
                            if (locationBean.isTrans())return;
                            builder.append(locationBean.getLon());
                            builder.append(",");
                            builder.append(locationBean.getLat());
                            builder.append(" ");
                        }
                        if (builder.length() > 0) {
                            builder.deleteCharAt(builder.length() - 1);
                        }
                        RetrofitUtils.coorDinateTrans(builder.toString(), "2", points -> {
                            for (int i = 0; i < points.size(); i++) {
                                locationBeans.get(i).setLat(points.get(i).getX());
                                locationBeans.get(i).setLon(points.get(i).getY());
                                locationBeans.get(i).setTrans(true);
                            }
                            uploadLocation(locationBeans);
                        });
                    }else {
                        uploadLocation(locationBeans);
                    }
                }
                // gis/sysUserGisRecord/saveGisRecords 添加修改数据
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                handlerUpdate.postDelayed(this, Constant.UPDATE_POINT_TIME);
            }
        }
    };

    /*坐标上传*/
    private void uploadLocation(List<LocationBean> locationBeans) {
        RetrofitManager.getInstance().create(PatrolApiService.class)
                .locationUpdate(locationBeans)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> postResponse) {
                        if (postResponse.isSuccess()) {
                            LogUtils.d("LocationUpdateService----坐标上传成功" + TimeUtils.getNowString()
                                    + "\n上传数量-----" + taskList.get(0).size());
                            taskList.remove(0);
                        } else {
                            LogUtils.d("LocationUpdateService----坐标上传失败" + TimeUtils.getNowString()
                                    + "\n上传数量-----" + taskList.get(0).size()
                                    + "\n信息-----" + postResponse.getMsg());
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        LogUtils.d("LocationUpdateService----坐标上传失败" + TimeUtils.getNowString()
                                + "\n上传数量-----" + taskList.get(0).size());
                        LogUtils.d(e.getMessage());
                        e.printStackTrace();
                    }
                });
    }

    Runnable runnableTerminal = new Runnable() {
        @Override
        public void run() {
            // 终端信息上传
            try {
                if (!NetworkUtils.isConnected()) {
                    return;
                }
                mRealm.executeTransaction(realm -> {
                    RealmResults<TerminalInfoBean> element = realm.where(TerminalInfoBean.class)
                            .equalTo("isUpdate", false)
                            .findAll();
                    listTerminalInfo = realm.copyFromRealm(element);
                });
                if (ObjectUtils.isEmpty(listTerminalInfo)) {
                    return;
                }
                if (listTerminalInfo.isEmpty()) {
                    return;
                }
                Gson gson=new Gson();
                Log.e("result","rsgsgsgsgsg"+gson.toJson(listTerminalInfo));
                // gis/sysUserGisRecord/saveGisRecords 添加修改数据
                RetrofitManager.getInstance().create(PatrolApiService.class)
                        .terminalUpdate(listTerminalInfo)
                        .compose(TransformUtils.defaultSchedulers())
                        .subscribe(new BaseSubscriber<BasePostResponse<Object>>() {
                            @Override
                            public void onResult(BasePostResponse<Object> postResponse) {
                                if (!postResponse.isSuccess()) {
                                    LogUtils.d("LocationUpdateService----终端信息失败" + TimeUtils.getNowString()
                                            + "\n上传数量-----" + listTerminalInfo.size()
                                            + "\n信息-----" + postResponse.getMsg());
                                } else {
                                    mRealm.executeTransaction(realm -> {
                                        RealmResults<TerminalInfoBean> element = realm.where(TerminalInfoBean.class)
                                                .equalTo("isUpdate", false)
                                                .findAll();
                                        if (element.size() > 0) {
//                                            for (int i = 0; i < element.size(); i++) {
//                                                element.get(i).setUpdate(true);
//                                            }
                                            element.deleteAllFromRealm();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable e) {
                                LogUtils.d("LocationUpdateService----终端信息失败" + TimeUtils.getNowString()
                                        + "\n上传数量-----" + listTerminalInfo.size()
                                        + "\n信息-----" + e.getMessage());
                                LogUtils.d(e.getMessage());
                                e.printStackTrace();
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                handlerTerminal.postDelayed(this, Constant.SAVE_TERMINAL_INFO_TIME);
            }
        }
    };

    Runnable runnableDel = new Runnable() {
        @Override
        public void run() {
            // handler自带方法实现定时器
            // 定时清理历史数据 间隔时间1小时
            try {
                // 执行坐标删除
                // 1.计算出删除的时间分割线
                // 2.判断小于当前时间线的坐标就删除
                // 计算出删除的时间分割线
                Long delTime = System.currentTimeMillis() - Constant.DEL_POINT_TIME;
                mRealm.executeTransaction(realm -> {
                    RealmResults<LocationBean> element = realm.where(LocationBean.class)
                            .equalTo("isUpdate", true)
                            .between("time", 0, delTime)
                            .findAll();
                    for (int i = 0; i < element.size(); i++) {
                        element.deleteFromRealm(i);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                handlerDel.postDelayed(this, Constant.DEL_POINT_SUBMIT_TIME);
            }
        }
    };

    /**
     * GPS是否打开 状态上传
     */
    Runnable runnableGps = new Runnable() {
        @Override
        public void run() {
            try {
                // GPS是否打开
                Map<String, String> map = new HashMap<>();
                // 1 GPS 已开启  2  GPS 未开启
                // core/sysUsers/updateGpsStatus
                map.put("gps", GpsUtils.isOPen(LocationUpdateService.this) ? "1" : "2");
                RetrofitManager.getInstance().create(PatrolApiService.class)
                        .updateGps(map)
                        .compose(TransformUtils.defaultSchedulers())
                        .subscribe(new BaseSubscriber<BasePostResponse<Object>>() {
                            @Override
                            public void onResult(BasePostResponse<Object> postResponse) {
                                if (!postResponse.isSuccess()) {
                                    LogUtils.d("LocationUpdateService----GPS是否打开 状态上传失败" + TimeUtils.getNowString()
                                            + "\n信息-----" + postResponse.getMsg());
                                } else {
                                    LogUtils.d("LocationUpdateService----GPS是否打开 状态上传成功" + TimeUtils.getNowString());
                                }
                            }

                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable e) {
                                LogUtils.d(e.getMessage());
                                e.printStackTrace();
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                handlerGps.postDelayed(this, Constant.UPDATE_POINT_TIME);
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LocationUpdateBinder binder = new LocationUpdateBinder();
        if (!isStartTask) {
            if (ObjectUtils.isNotEmpty(handler) && ObjectUtils.isNotEmpty(runnable)) {
                handler.postDelayed(runnable, 0);
            }
            if (ObjectUtils.isNotEmpty(handlerDel) && ObjectUtils.isNotEmpty(runnableDel)) {
                handlerDel.postDelayed(runnableDel, 0);
            }
            if (ObjectUtils.isNotEmpty(handlerUpdate) && ObjectUtils.isNotEmpty(runnableUpdate)) {
                handlerUpdate.postDelayed(runnableUpdate, 5000);
            }
            if (ObjectUtils.isNotEmpty(handlerTerminal) && ObjectUtils.isNotEmpty(runnableTerminal)) {
                handlerTerminal.postDelayed(runnableTerminal, 15000);
            }
            if (PreferencesUtil.getInt(Constant.DOMAIN_NAME_ID) == 1) {
                // 泸州域
                if (ObjectUtils.isNotEmpty(handlerGps) && ObjectUtils.isNotEmpty(runnableGps)) {
                    handlerGps.postDelayed(runnableGps, 15000);
                }
            }
        }
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtils.d("LocationUpdateService----onCreate" + TimeUtils.getNowString());
        isStartTask = true;
        mRealm = Realm.getDefaultInstance();
        handler.postDelayed(runnable, 0);
        handlerDel.postDelayed(runnableDel, 0);
        handlerUpdate.postDelayed(runnableUpdate, 5000);
        handlerTerminal.postDelayed(runnableTerminal, 15000);
        if (PreferencesUtil.getInt(Constant.DOMAIN_NAME_ID) == 1) {
            // 泸州域
            handlerGps.postDelayed(runnableGps, 15000);
        }
    }

    public class LocationUpdateBinder extends Binder {
        /**
         * 获取Service的方法 * @return
         */
        public LocationUpdateService getService() {
            return LocationUpdateService.this;
        }
    }

    @Override
    public void onDestroy() {
        stopServiceWork();
        super.onDestroy();
    }


    public void stopServiceWork() {
        if (ObjectUtils.isNotEmpty(wakeLock)) {
            wakeLock.release();
        }
        if (ObjectUtils.isNotEmpty(handler)) {
            handler.removeCallbacks(runnable);
        }
        if (ObjectUtils.isNotEmpty(handlerDel)) {
            handlerDel.removeCallbacks(runnableDel);
        }
        if (ObjectUtils.isNotEmpty(handlerUpdate)) {
            handlerUpdate.removeCallbacks(runnableUpdate);
        }
        if (ObjectUtils.isNotEmpty(handlerTerminal)) {
            handlerTerminal.removeCallbacks(runnableTerminal);
        }
        if (PreferencesUtil.getInt(Constant.DOMAIN_NAME_ID) == 1) {
            // 泸州域
            if (ObjectUtils.isNotEmpty(handlerGps)) {
                handlerGps.removeCallbacks(runnableGps);
            }
        }
        if (ObjectUtils.isNotEmpty(mRealm)) {
            mRealm.close();
        }
        LogUtils.d("LocationUpdateService----onDestroy" + TimeUtils.getNowString());
    }

    @SuppressLint({"WrongConstant", "InvalidWakeLockTag"})
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //创建PowerManager对象
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (ObjectUtils.isNotEmpty(pm)) {
            //保持cpu一直运行，不管屏幕是否黑屏
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "CPUKeepRunning");
            wakeLock.acquire(10 * 60 * 1000L);
        }
        // flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }
}
