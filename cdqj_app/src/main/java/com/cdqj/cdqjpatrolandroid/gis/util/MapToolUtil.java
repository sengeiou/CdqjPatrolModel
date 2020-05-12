package com.cdqj.cdqjpatrolandroid.gis.util;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.MapView;

/**
 * Created by lyf on 2018/9/12 20:43
 *
 * @author lyf
 * desc：地图工具监听衍生
 */
public class MapToolUtil {

    /**
     * 跳转到定位位置
     * @param mapView mapView
     */
    public static void getLocation(MapView mapView) {
        if(mapView.getLocationDisplay()!=null && mapView.getLocationDisplay().getMapLocation()!=null){
            Point pt = mapView.getLocationDisplay().getMapLocation();
            if(pt.getX()!=Double.NaN && pt.getX()!=0){
                mapView.setViewpointCenterAsync(mapView.getLocationDisplay().getMapLocation(),MapConstant.SCALE);
            }
        }
    }

    /**
     * 全图显示
     * @param mapMv mapView
     * @param viewPoint viewPoint
     */
    public static void mapFullExtent(MapView mapMv, Viewpoint viewPoint) {
        if (viewPoint != null) {
            mapMv.setViewpointRotationAsync(0);
            mapMv.setViewpointAsync(viewPoint, 1);
        }
    }

    /**
     * 跳转到定位位置
     * @param mapView mapView
     */
    public static void getLocation(MapView mapView, Point point) {
        if(mapView.getLocationDisplay()!=null && point!=null){
            if(point.getX()!=Double.NaN && point.getX()!=0){
                mapView.setViewpointCenterAsync(point,MapConstant.SCALE);
            }
        }
    }

    /**
     * 跳转到定位位置
     * @param mapView mapView
     */
    public static void getLocation(MapView mapView, Point point, double scale) {
        if(mapView.getLocationDisplay()!=null && point!=null){
            if(point.getX()!=Double.NaN && point.getX()!=0){
                mapView.setViewpointCenterAsync(point,scale);
            }
        }
    }
}
