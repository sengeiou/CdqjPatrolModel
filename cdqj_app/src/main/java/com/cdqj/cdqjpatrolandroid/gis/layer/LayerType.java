package com.cdqj.cdqjpatrolandroid.gis.layer;

/**
 * 地图服务类型枚举类
 * @author guohui.pang	2015-06-24
 * @version 1.0.0
 */
public class LayerType {
    /**
     * 在线切片
     */
    public static final int TILED=1;
    /**
     * 在线动态
     */
    public static final int DYNAMIC=2;
    /**
     * 本地切片,指定到Layers目录
     */
    public static final int LOCAL=3;
    /**
     * 本地切片tpk
     */
    public static final int TPK=4;
    /**
     * 本地MBTILES
     */
    public static final int MBTILES=5;
    /**
     * 在线天地图
     */
    public static final int TDT=6;
    /**
     * 在线wmts服务
     */
    public static final int WMTS=7;
}