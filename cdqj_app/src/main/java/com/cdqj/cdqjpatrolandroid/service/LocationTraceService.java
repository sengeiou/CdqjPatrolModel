package com.cdqj.cdqjpatrolandroid.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.Trace;
import com.baidu.trace.api.fence.FenceAlarmPushInfo;
import com.baidu.trace.api.fence.MonitoredAction;
import com.baidu.trace.model.OnCustomAttributeListener;
import com.baidu.trace.model.OnTraceListener;
import com.baidu.trace.model.PushMessage;
import com.baidu.trace.model.StatusCodes;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.cdqj.cdqjpatrolandroid.base.App;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyf on 2018/11/13 09:36
 *
 * @author lyf
 * desc：轨迹采集类（百度鹰眼）
 */
public class LocationTraceService extends Service {

    /**
     * 初始化轨迹服务
     */
    private Trace mTrace;
    /**
     * 初始化轨迹服务客户端
     */
    private LBSTraceClient mTraceClient;

    private PowerManager powerManager = null;

    private PowerManager.WakeLock wakeLock = null;

    private TrackReceiver trackReceiver = null;

    public boolean isRegisterReceiver = false;

    /**
     * 服务是否开启标识
     */
    public boolean isTraceStarted = false;

    /**
     * 采集是否开启标识
     */
    public boolean isGatherStarted = false;


    /**
     * 轨迹服务监听器
     */
    private OnTraceListener traceListener = null;

    /**
     * 打包周期
     */
    public int packInterval = ServiceConstant.DEFAULT_PACK_INTERVAL;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new LocationTraceBinder();
    }

    /**
     * IBinder是远程对象的基本接口，是为高性能而设计的轻量级远程调用机制的核心部分。但它不仅用于远程
     * 调用，也用于进程内调用。这个接口定义了与远程对象交互的协议。
     * 不要直接实现这个接口，而应该从Binder派生。
     * Binder类已实现了IBinder接口
     */
    public class LocationTraceBinder extends Binder {
        /**
         * 获取Service的方法
         */
        public LocationTraceService getService() {
            return LocationTraceService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mTrace = new Trace(ServiceConstant.TRACE_SERVICE_ID, PreferencesUtil.getString(ServiceConstant.TRACE_ENTITY_NAME), ServiceConstant.IS_NEED_OBJECT_STORAGE);
        mTraceClient = new LBSTraceClient(getApplicationContext());
        powerManager = (PowerManager) App.getInstance().getSystemService(Context.POWER_SERVICE);

        // 设置定位和打包周期
        mTraceClient.setInterval(ServiceConstant.DEFAULT_GATHER_INTERVAL, packInterval);

        mTraceClient.setOnCustomAttributeListener(new OnCustomAttributeListener() {
            @Override
            public Map<String, String> onTrackAttributeCallback() {
                Map<String, String> map = new HashMap<>();
                map.put("key1", "value1");
                map.put("key2", "value2");
                return map;
            }

            @Override
            public Map<String, String> onTrackAttributeCallback(long locTime) {
                System.out.println("onTrackAttributeCallback, locTime : " + locTime);
                Map<String, String> map = new HashMap<>();
                map.put("key1", "value1");
                map.put("key2", "value2");
                return map;
            }
        });

        clearTraceStatus();
        initListener();
        if (isTraceStarted) {
            mTraceClient.stopTrace(mTrace, traceListener);
            stopRealTimeLoc();
        } else {
            mTraceClient.startTrace(mTrace, traceListener);
            if (ServiceConstant.DEFAULT_PACK_INTERVAL != packInterval) {
                stopRealTimeLoc();
            }
        }
    }

    /**
     * 清除Trace状态：初始化app时，判断上次是正常停止服务还是强制杀死进程，根据trackConf中是否有is_trace_started字段进行判断。
     * <p>
     * 停止服务成功后，会将该字段清除；若未清除，表明为非正常停止服务。
     */
    private void clearTraceStatus() {
        PreferencesUtil.putBoolean("is_trace_started", false);
        PreferencesUtil.putBoolean("is_gather_started", false);
    }

    /**
     * 注册广播（电源锁、GPS状态）
     */
    @SuppressLint("InvalidWakeLockTag")
    private void registerReceiver() {
        if (isRegisterReceiver) {
            return;
        }

        if (null == wakeLock) {
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "track upload");
        }
        if (null == trackReceiver) {
            trackReceiver = new TrackReceiver(wakeLock);
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        filter.addAction(StatusCodes.GPS_STATUS_ACTION);
        App.getInstance().registerReceiver(trackReceiver, filter);
        isRegisterReceiver = true;

    }

    private void unregisterPowerReceiver() {
        if (!isRegisterReceiver) {
            return;
        }
        if (null != trackReceiver) {
            unregisterReceiver(trackReceiver);
        }
        isRegisterReceiver = false;
    }


    public void stopRealTimeLoc() {
        mTraceClient.stopRealTimeLoc();
    }

    /**
     * 初始化监听
     */
    @SuppressLint("DefaultLocale")
    private void initListener() {
        traceListener = new OnTraceListener() {

            /**
             * 绑定服务回调接口
             * @param errorNo  状态码
             * @param message 消息
             *                <p>
             *                <pre>0：成功 </pre>
             *                <pre>1：失败</pre>
             */
            @Override
            public void onBindServiceCallback(int errorNo, String message) {
                LogUtils.d(String.format("onBindServiceCallback, errorNo:%d, message:%s ", errorNo, message));
            }

            /**
             * 开启服务回调接口
             * @param errorNo 状态码
             * @param message 消息
             *                <p>
             *                <pre>0：成功 </pre>
             *                <pre>10000：请求发送失败</pre>
             *                <pre>10001：服务开启失败</pre>
             *                <pre>10002：参数错误</pre>
             *                <pre>10003：网络连接失败</pre>
             *                <pre>10004：网络未开启</pre>
             *                <pre>10005：服务正在开启</pre>
             *                <pre>10006：服务已开启</pre>
             */
            @Override
            public void onStartTraceCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.START_TRACE_NETWORK_CONNECT_FAILED <= errorNo) {
                    isTraceStarted = true;
                    PreferencesUtil.putBoolean("is_trace_started", true);
                    registerReceiver();
                    // 开启采集
                    mTraceClient.startGather(traceListener);
                } else {
                    LogUtils.d(String.format("onStartTraceCallback, errorNo:%d, message:%s ", errorNo, message));
                }
            }

            /**
             * 停止服务回调接口
             * @param errorNo 状态码
             * @param message 消息
             *                <p>
             *                <pre>0：成功</pre>
             *                <pre>11000：请求发送失败</pre>
             *                <pre>11001：服务停止失败</pre>
             *                <pre>11002：服务未开启</pre>
             *                <pre>11003：服务正在停止</pre>
             */
            @Override
            public void onStopTraceCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.CACHE_TRACK_NOT_UPLOAD == errorNo) {
                    isTraceStarted = false;
                    isGatherStarted = false;
                    // 停止成功后，直接移除is_trace_started记录（便于区分用户没有停止服务，直接杀死进程的情况）
                    PreferencesUtil.putBoolean("is_trace_started",false);
                    PreferencesUtil.putBoolean("is_gather_started",false);
                    unregisterPowerReceiver();
                } else {
                    LogUtils.d(String.format("onStopTraceCallback, errorNo:%d, message:%s ", errorNo, message));
                }
            }

            /**
             * 开启采集回调接口
             * @param errorNo 状态码
             * @param message 消息
             *                <p>
             *                <pre>0：成功</pre>
             *                <pre>12000：请求发送失败</pre>
             *                <pre>12001：采集开启失败</pre>
             *                <pre>12002：服务未开启</pre>
             */
            @Override
            public void onStartGatherCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.GATHER_STARTED == errorNo) {
                    isGatherStarted = true;
                    PreferencesUtil.putBoolean("is_gather_started", true);
                } else {
                    LogUtils.d(String.format("onStartGatherCallback, errorNo:%d, message:%s ", errorNo, message));
                }
            }

            /**
             * 停止采集回调接口
             * @param errorNo 状态码
             * @param message 消息
             *                <p>
             *                <pre>0：成功</pre>
             *                <pre>13000：请求发送失败</pre>
             *                <pre>13001：采集停止失败</pre>
             *                <pre>13002：服务未开启</pre>
             */
            @Override
            public void onStopGatherCallback(int errorNo, String message) {
                if (StatusCodes.SUCCESS == errorNo || StatusCodes.GATHER_STOPPED == errorNo) {
                    isGatherStarted = false;
                    PreferencesUtil.putBoolean("is_gather_started", false);
                } else {
                    LogUtils.d(String.format("onStopGatherCallback, errorNo:%d, message:%s ", errorNo, message));
                }
            }

            /**
             * 推送消息回调接口
             *
             * @param messageType 状态码
             * @param pushMessage 消息
             *                  <p>
             *                  <pre>0x01：配置下发</pre>
             *                  <pre>0x02：语音消息</pre>
             *                  <pre>0x03：服务端围栏报警消息</pre>
             *                  <pre>0x04：本地围栏报警消息</pre>
             *                  <pre>0x05~0x40：系统预留</pre>
             *                  <pre>0x41~0xFF：开发者自定义</pre>
             */
            @Override
            public void onPushCallback(byte messageType, PushMessage pushMessage) {
                if (messageType < 0x03 || messageType > 0x04) {
                    LogUtils.d(pushMessage.getMessage());
                    return;
                }
                FenceAlarmPushInfo alarmPushInfo = pushMessage.getFenceAlarmPushInfo();
                if (null == alarmPushInfo) {
                    LogUtils.d(String.format("onPushCallback, messageType:%d, messageContent:%s ", messageType,
                                    pushMessage));
                    return;
                }
                String alarmInfo = "您于" +
                        TimeUtils.date2String(new Date(alarmPushInfo.getCurrentPoint().getLocTime() * 1000)) +
                        (alarmPushInfo.getMonitoredAction() == MonitoredAction.enter ? "进入" : "离开") +
                        (messageType == 0x03 ? "云端" : "本地") +
                        "围栏：" + alarmPushInfo.getFenceName();
                LogUtils.d(alarmInfo);
            }

            @Override
            public void onInitBOSCallback(int errorNo, String message) {
                LogUtils.d(String.format("onInitBOSCallback, errorNo:%d, message:%s ", errorNo, message));
            }
        };
    }
}
