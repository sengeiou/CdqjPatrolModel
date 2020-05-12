package com.cdqj.cdqjpatrolandroid.gis.event;

import com.cdqj.cdqjpatrolandroid.gis.bean.GisParamsBean;
import com.esri.arcgisruntime.layers.Layer;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gis_yj on 2018/1/2.
 */
public class QueryParams {

    public int returnMax = 1;
    public Layer mLayer;
    public GraphicsOverlay mGraphicsoverlay;
    public List<QueryLayerType> queryLayerTypeList;
    public GisParamsBean mGisparams;

    public QueryParams(List<QueryLayerType> list, Layer layer, GraphicsOverlay graphicsOverlay, int max, GisParamsBean gisParams){
        this.mGraphicsoverlay = graphicsOverlay;
        this.mLayer = layer;

        queryLayerTypeList = new ArrayList<>();
        queryLayerTypeList.add(QueryLayerType.QueryLayer);
        //默认返回查询图层
        if(list!=null){
            this.queryLayerTypeList = list;
        }
        //默认返回查询结果第一个
        if (max>1){
            this.returnMax = max;
        }
        this.mGisparams = gisParams;
    }

}
