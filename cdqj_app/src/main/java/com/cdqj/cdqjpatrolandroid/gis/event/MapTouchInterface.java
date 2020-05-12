package com.cdqj.cdqjpatrolandroid.gis.event;

import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.GeoElement;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.IdentifyGraphicsOverlayResult;
import com.esri.arcgisruntime.mapping.view.IdentifyLayerResult;

import java.util.List;

/**
 * Created by gis_yj on 2018/1/2.
 * 图层点击监听接口
 */
public interface MapTouchInterface {

    /**
     * 获取地图点击坐标点
     * @param pt pt
     * @param flag 点击事件标识
     *              0.单机地图不拦截
     *              1.导航点击
     *              2.上报点击
     *
     */
    void getMapClickPtSuccess(Point pt, int flag);

    /**
     * 获取地图长按点击坐标点
     * @param pt pt
     */
    void getMapLongPressPtSuccess(Point pt);

    /**
     * 查询业务图层之类的结果回调
     * @param geoElementList geoElementList
     */
    void getLayerQuerySuccess(List<GeoElement> geoElementList);

    /**
     * Object内容为：InterruptedException | ExecutionException
     * @param e e
     */
    void getLayerQueryFailed(Object e);

    /**
     * Object内容为：InterruptedException | ExecutionException
     * @param e e
     */
    void getGraphicsQueryFailed(Object e);

    /**
     * 查询所有的业务图层结果回调
     * @param layerResultList layerResultList
     */
    void getAllLayerQuerySuccess(List<IdentifyLayerResult> layerResultList);

    /**
     * 查询某个的graphic图层结果回调
     * @param graphicList graphicList
     */
    void getGraphicsOverlaySuccess(List<Graphic> graphicList);

    /**
     * 查询所有的graphic图层结果回调
     * @param graphicsOverlayResults graphicsOverlayResults
     */
    void getAllGraphicsOverlaySuccess(List<IdentifyGraphicsOverlayResult> graphicsOverlayResults);

    /**
     * 查询在线图层结果回调
     * @param featureQueryResult featureQueryResult
     */
    void getOnlineLayerSuccess(FeatureQueryResult featureQueryResult);

    /**
     * 查询在线图层结果回调
     * @param featureQueryResult featureQueryResult
     * @param ptClick ptClick
     */
    void getOnlineLayerSuccess(FeatureQueryResult featureQueryResult, Point ptClick);
}
