/**
 * 系统项目名称 com.cdqj 2015年7月16日-下午4:29:28
 * 2015XX公司-版权所有
 */
package com.cdqj.cdqjpatrolandroid.gis.bean;

/**
 * GIS参数相关信息
 * @author pgh
 * @date 2015年7月16日 下午4:29:28
 * @version 1.0.0
 */
public class GisParamsBean {
    /**
     * 管线在线地图服务地址
     */
    public String appPipesOnlineLyr;

    /**
     * 管线在线地图服务类型 TILED=1; //在线切片 DYNAMIC=2; //在线动态 LOCAL=3; //本地切片,指定到Layers目录
     * TPK=4; //本地切片tpk MBTILES=5; //本地MBTILES TDT=6; //在线天地图 WMTS=7; //在线wmts服务
     */
    public String appPipesOnlineLyrType;
    /**
     * 管线离线地图服务地址
     */
    public String appPipesOfflineLyr;
    /**
     * 管线离线地图服务类型 TILED=1; //在线切片 DYNAMIC=2; //在线动态 LOCAL=3; //本地切片,指定到Layers目录
     * TPK=4; //本地切片tpk MBTILES=5; //本地MBTILES TDT=6; //在线天地图 WMTS=7; //在线wmts服务
     */
    public String appPipesOfflineLyrType;

    /**
     * 背景在线地图服务地址
     */
    public String appBkOnlineLyr;
    /**
     * 背景在线地图服务类型 TILED=1; //在线切片 DYNAMIC=2; //在线动态 LOCAL=3; //本地切片,指定到Layers目录
     * TPK=4; //本地切片tpk MBTILES=5; //本地MBTILES TDT=6; //在线天地图 WMTS=7; //在线wmts服务
     */
    public String appBkOnlineLyrType;

    /**
     * 背景离线地图服务地址
     */
    public String appBkOfflineLyr;
    /**
     * 背景地图服务类型 TILED=1; //在线切片 DYNAMIC=2; //在线动态 LOCAL=3; //本地切片,指定到Layers目录
     * TPK=4; //本地切片tpk MBTILES=5; //本地MBTILES TDT=6; //在线天地图 WMTS=7; //在线wmts服务
     */
    public String appBkOfflineLyrType;
    /**
     * 表示中心点或范围
     * 中心点: 104.123|30.345|10 分别对应 ： 经度|纬度|缩放等级
     * 范围：104.123|30.345|104.123|30.345 分别对应 ： xmin|ymin| xmax|ymax
     */
    public String appCenter;

    /**
     * 是否要进行坐标转换  null-不转换;1-成都转2000;2--2000转成都
     */
    public String appTransCoords;

    /**
     * 管线地图的查询地址
     */
    public String appPipesQueryUrl;

    /**
     * 管线查询的图层索引号,多个用(|)隔开
     */
    public String appQueryLyrIndexs;

    /**
     * 背景地图服务类型 TILED=1; //在线切片 DYNAMIC=2; //在线动态 LOCAL=3; //本地切片,指定到Layers目录
     * TPK=4; //本地切片tpk MBTILES=5; //本地MBTILES TDT=6; //在线天地图 WMTS=7; //在线wmts服务
     */
    public String appBkLayerType;

    /**
     * 巡线管网区域地址
     */
    public String poamAreaUrl;

    /**
     * 巡线管网区域类型
     */
    public String poamAreaType;

    /**
     * 查询地址
     */
    public String appSearchUrl;

    /**
     * 查询字段
     */
    public String appSearchField;

    /**
     * 调压设备查询地图F
     */
    public String editBoosterLayerUrl;

    /**
     * 调压设备查询地图
     */
    public String appTdtKey;

    /**
     * reference 坐标系参数 根据各个项目
     * 4326 -WGPS84坐标系
     * 4490 -2000坐标系
     */
    public int spatialReference;

    @Override
    public String toString() {
        return "GisParamsBean{" +
                "appPipesOnlineLyr='" + appPipesOnlineLyr + '\'' +
                ", appPipesOnlineLyrType='" + appPipesOnlineLyrType + '\'' +
                ", appPipesOfflineLyr='" + appPipesOfflineLyr + '\'' +
                ", appPipesOfflineLyrType='" + appPipesOfflineLyrType + '\'' +
                ", appBkOnlineLyr='" + appBkOnlineLyr + '\'' +
                ", appBkOnlineLyrType='" + appBkOnlineLyrType + '\'' +
                ", appBkOfflineLyr='" + appBkOfflineLyr + '\'' +
                ", appBkOfflineLyrType='" + appBkOfflineLyrType + '\'' +
                ", appCenter='" + appCenter + '\'' +
                ", appTransCoords='" + appTransCoords + '\'' +
                ", appPipesQueryUrl='" + appPipesQueryUrl + '\'' +
                ", appQueryLyrIndexs='" + appQueryLyrIndexs + '\'' +
                ", appBkLayerType='" + appBkLayerType + '\'' +
                ", poamAreaUrl='" + poamAreaUrl + '\'' +
                ", poamAreaType='" + poamAreaType + '\'' +
                ", appSearchUrl='" + appSearchUrl + '\'' +
                ", appSearchField='" + appSearchField + '\'' +
                ", editBoosterLayerUrl='" + editBoosterLayerUrl + '\'' +
                ", appTdtKey='" + appTdtKey + '\'' +
                ", spatialReference=" + spatialReference +
                '}';
    }
}
