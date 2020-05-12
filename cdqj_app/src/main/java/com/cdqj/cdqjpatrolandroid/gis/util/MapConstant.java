package com.cdqj.cdqjpatrolandroid.gis.util;

/**
 * Created by lyf on 2018/9/12 20:45
 *
 * @author lyf
 * desc：地图常量类
 */
public class MapConstant {

    /**
     * 地图大小
     */
    public static final int SCALE = 2000;

    /**
     * 地图临时图层显示比例范围(网格，任务点等)
     */
    public static final double MIN_SCALE = 300000D;

    /**
     * 点击地图graphic的attr中key的值 工地
     */
    public static final String GD_ATTR_NAME = "gdMsg_Json";

    /**
     * 点击地图graphic的attr中key的值 车辆
     */
    public static final String CAR_ATTR_NAME = "carMsg_Json";

    /**
     * 点击地图graphic的attr中key的值 人员
     */
    public static final String PEOPLE_ATTR_NAME = "people_Json";

    /**
     * 点击地图graphic的attr中key的值 人员
     */
    public static final String PLAN_ATTR_NAME = "plan_Json";

    /**
     * 点击地图graphic的attr中key的值 隐患
     */
    public static final String HD_ATTR_NAME = "hd_Msg_Json";
}
