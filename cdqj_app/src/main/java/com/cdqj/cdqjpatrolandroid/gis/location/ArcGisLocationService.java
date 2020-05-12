package com.cdqj.cdqjpatrolandroid.gis.location;

import com.cdqj.cdqjpatrolandroid.gis.util.MapConstant;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.location.LocationDataSource;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.MapView;

/**
 * Created by lyf on 2018/9/12 20:53
 *
 * @author lyf
 * desc：ArcGis定位服务
 */
public class ArcGisLocationService extends LocationDataSource {

    private boolean isScaleToCurrentLcOnFirst = true;
    private boolean isFlow = false;
    private MapView mapView;

    @Override
    protected void onStart() {
        setLastKnownLocation(mapView);
    }

    @Override
    protected void onStop() {

    }

    public ArcGisLocationService(MapView mapView) {
        this.mapView = mapView;
    }

    /** 定位到当前位置 */
    private void setLastKnownLocation(MapView mapView){
        Point pt = new Point(mapView.getScaleX(),mapView.getScaleY(),mapView.getSpatialReference());
        Location lastLc = new Location(pt,0,0,0,true);
        this.updateLocation(lastLc);
        setScaleToCurrentLocation(mapView, pt);
    }

    /** 缩放到当前位置 */
    private void setScaleToCurrentLocation(MapView mapView,Point pt){
        if(pt!=null){
            if(pt.getX()!=Double.NaN && pt.getX()!=0){
                Viewpoint viewpoint;
                if(mapView.getMapScale() < MapConstant.SCALE){
                    viewpoint = new Viewpoint(pt,mapView.getMapScale());
                }else{
                    viewpoint = new Viewpoint(pt,MapConstant.SCALE);
                }
                if(isFlow){
                    mapView.setViewpointAsync(viewpoint,1);
                }
                if(!isFlow && isScaleToCurrentLcOnFirst){
                    mapView.setViewpointAsync(viewpoint,1);
                    isScaleToCurrentLcOnFirst = false;
                }
            }
        }
    }
}
