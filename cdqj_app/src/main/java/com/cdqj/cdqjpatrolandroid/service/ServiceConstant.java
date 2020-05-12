package com.cdqj.cdqjpatrolandroid.service;

/**
 * Created by lyf on 2018/11/13 09:44
 *
 * @author lyf
 * desc：
 */
public class ServiceConstant {

    /**
     * 轨迹服务ID
     */
    public final static long TRACE_SERVICE_ID = 206511;

    /**
     * 轨迹设备标识
     */
    public final static String TRACE_ENTITY_NAME = "trace_entity_name";

    /**
     * 轨迹定位周期(单位:秒)
     */
    public final static String GATHER_INTERVAL = "gather_interval";

    /**
     * 轨迹打包回传周期(单位:秒)
     */
    public final static String PACK_INTERVAL = "pack_interval";

    /**
     * 轨迹分析查询间隔时间（1分钟）
     */
    public static final int ANALYSIS_QUERY_INTERVAL = 60;

    /**
     * 停留点默认停留时间（1分钟）
     */
    public static final int STAY_TIME = 60;

    /**
     * 启动停留时间
     */
    public static final int SPLASH_TIME = 3000;

    /**
     * 默认采集周期
     */
    public static final int DEFAULT_GATHER_INTERVAL = 5;

    /**
     * 默认打包周期
     */
    public static final int DEFAULT_PACK_INTERVAL = 30;

    /**
     * 实时定位间隔(单位:秒)
     */
    public static final int LOC_INTERVAL = 10;

    /**
     * 最后一次定位信息
     */
    public static final String LAST_LOCATION = "last_location";

    /**
     * 是否需要对象存储服务，默认为：false，关闭对象存储服务。
     * 注：鹰眼 Android SDK v3.0以上版本支持随轨迹上传图像等对象数据，若需使用此功能，该参数需设为 true，且需导入bos-android-sdk-1.0.2.jar。
     */
    public static final boolean IS_NEED_OBJECT_STORAGE = false;
}
