package com.cdqj.cdqjpatrolandroid.gis.event;

/**
 * Created by gis_yj on 2018/1/3.
 * 图层状态监听
 */
public interface MapStatusInterface {

    /**
     * 获取状态
     * @param state state
     */
    void getLayerStatusSuccess(String state);

    /**
     * 获取状态
     */
    void getLayerActiveSuccess();

    /**
     * 获取状态
     * @param layerName 图层名字
     */
    void getLayerErrorSuccess(String layerName);
}
