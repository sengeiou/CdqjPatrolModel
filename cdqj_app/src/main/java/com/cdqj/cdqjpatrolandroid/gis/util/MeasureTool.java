package com.cdqj.cdqjpatrolandroid.gis.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;

import com.cdqj.cdqjpatrolandroid.R;
import com.esri.arcgisruntime.geometry.GeodeticCurveType;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;

import java.text.DecimalFormat;

/**
 * Created by lyf on 2018/9/13 10:12
 *
 * @author lyf
 * desc：地图测量工具
 */
public class MeasureTool {
    /**
     * map
     */
    private MapView map;
    /**
     * 标绘图层
     */
    private GraphicsOverlay graphicsLayer;
    private MyTouchListener myTouch;
    /**
     * 符号样式
     */
    private SimpleMarkerSymbol sms;
    /**
     * 线
     */
    private SimpleLineSymbol sls;
    private SimpleFillSymbol sfs;

    public SimpleMarkerSymbol getSms() {
        return sms;
    }

    public void setSms(SimpleMarkerSymbol sms) {
        this.sms = sms;
    }

    public SimpleLineSymbol getSls() {
        return sls;
    }

    public void setSls(SimpleLineSymbol sls) {
        this.sls = sls;
    }

    public SimpleFillSymbol getSfs() {
        return sfs;
    }

    public void setSfs(SimpleFillSymbol sfs) {
        this.sfs = sfs;
    }

    private Context context;


    @SuppressLint("ClickableViewAccessibility")
    public MeasureTool(Context context, MapView map, GraphicsOverlay glayer) {
        this.map = map;
        this.graphicsLayer = glayer;
        // 实例化监听器
        myTouch = new MyTouchListener(context, map, glayer);
        // 设置绘画监听
        this.map.setOnTouchListener(myTouch);
        this.context = context;
    }

    /**
     * 设置起点
     */
    public void setStartPt(){
        this.myTouch.startPoint = null;
        if(myTouch.ptc!=null){
            myTouch.ptc.clear();
        }
        myTouch.aid = 0;
    }

    /**
     * 标绘点操作
     */
    public void drawPoint() {
        this.myTouch.setType("POINT");
        myTouch.startPoint=null;
    }

    /**
     * 标绘线操作
     */
    public void drawLine() {
        this.myTouch.setType("POLYLINE");
        myTouch.startPoint=null;
        if(myTouch.ptc!=null){
            myTouch.ptc.clear();
        }
        myTouch.aid = 0;
    }

    /**
     * 标绘面操作
     */
    public void drawPolygon() {
        this.myTouch.setType("POLYGON");
        myTouch.startPoint=null;
        if(myTouch.ptc!=null){
            myTouch.ptc.clear();
        }
        myTouch.aid = 0;
    }

    /**
     * 取消标绘
     */
    @SuppressLint("ClickableViewAccessibility")
    public void cancelDraw() {
        this.myTouch.setType("");
        myTouch.startPoint=null;
        this.map.setOnTouchListener(new DefaultMapViewOnTouchListener(context,map));
    }

    /**
     * 清除图层信息（绘制）
     */
    public void removeAll() {
        graphicsLayer.getGraphics().clear();
        myTouch.startPoint=null;
    }

    /**
     *
     * 地图屏幕触摸监听类
     * @author Lzw
     *
     */
    class MyTouchListener extends DefaultMapViewOnTouchListener {
        Polyline polyline;
        Polygon polygon;
        PointCollection ptc;
        String type = "";
        Point startPoint = null;
        int uid = 0;
        int aid = 0;
        GraphicsOverlay graphicsLayer;
        MapView mapView;
        Context ctxContext;
        private Graphic lblGraphic, polyGraphic;

        MyTouchListener(Context context, MapView view,
                        GraphicsOverlay glayer) {
            super(context, view);
            this.graphicsLayer = glayer;
            this.mapView = view;
            this.ctxContext = context;
            ptc = new PointCollection(view.getSpatialReference());
            polyline = new Polyline(ptc);
        }

        public void setType(String geometryType) {
            this.type = geometryType;
        }

        public String getType() {
            return this.type;
        }

        /**
         * 点击地图时的操作
         */
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            DecimalFormat df = new DecimalFormat(".########");
            if (sms == null) {
                sms = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, ContextCompat.getColor(context, R.color.theme_one), 5);
            }
            Point pPoint = mapView.screenToLocation(new android.graphics.Point((int) e.getX(), (int) e.getY()));
            //添加临时点
            Graphic gPt = new Graphic(pPoint, sms);
            graphicsLayer.getGraphics().add(gPt);

            if (type.length() > 1 && type.equalsIgnoreCase("POINT")) {
                //===============显示坐标
                String cor = "X:" + df.format(pPoint.getX()) + "\nY:" + df.format(pPoint.getY());
                PictureMarkerSymbol pMarkerSymbol = SymobelUtils.textPicSymobel(ctxContext, cor, ContextCompat.getColor(context, R.color.theme_one), 12);
                pMarkerSymbol.setOffsetY(23);
                pMarkerSymbol.setOffsetX(-12);
                Graphic txtGraphic = new Graphic(mapView.screenToLocation(new android.graphics.Point((int) e.getX(), (int) e.getY())), pMarkerSymbol);
                graphicsLayer.getGraphics().add(txtGraphic);
                return true;
            }
            if (type.length() > 1 && ((type.equalsIgnoreCase("POLYLINE") || type.equalsIgnoreCase("POLYGON")))) {
                ptc.add(pPoint);
                int count = ptc.size();
                Log.i("CDGAS", count + "");
                df = new DecimalFormat("#.##");

                if (type.equalsIgnoreCase("POLYLINE")) {
                    if (sls == null){
                        sls = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 2);
                    }
                    polyline = new Polyline(ptc);
                    Graphic graphic = new Graphic(polyline, sls);
                    // 建图形添加到图层中
                    graphicsLayer.getGraphics().add(graphic);

                    //=================================显示距离
                    if (count >= 2) {
                        Point sPoint = ptc.get(count - 2);
                        Point ePoint = ptc.get(count - 1);
                        Double lenthDouble = GeometryEngine.distanceBetween(sPoint, ePoint);
                        String jl;
                        if (map.getSpatialReference().getWkid() == 4326) {
                            // wgs84
                            jl = df.format(lenthDouble * 100000) + "米";
                        } else if (map.getSpatialReference().getWkid() == 4490) {
                            jl = df.format(lenthDouble * 100000) + "米";
                        } else {//xian80-120
                            jl = df.format(lenthDouble) + "米";
                        }
                        PictureMarkerSymbol pMarkerSymbol = SymobelUtils.textPicSymobel(ctxContext, jl, Color.BLUE, 10);
                        pMarkerSymbol.setOffsetY(12);
                        pMarkerSymbol.setOffsetX(-12);
                        Graphic txtGraphic = new Graphic(ePoint, pMarkerSymbol);
                        graphicsLayer.getGraphics().add(txtGraphic);
                    }
                }
                if (type.equalsIgnoreCase("POLYGON")) {
                    if (sfs == null) {
                        // 设置面状样式，如果为空者创建他---------------POLYGON
                        sfs = new SimpleFillSymbol();
                        sfs.setColor(Color.argb(50, 255, 0, 0));
                        SimpleLineSymbol simpleLineSymbol = new SimpleLineSymbol();
                        simpleLineSymbol.setWidth(2);
                        simpleLineSymbol.setColor(Color.BLUE);
                        sfs.setOutline(simpleLineSymbol);
                    }
                    //==================================显示面积
                    if (count > 2) {
                        Polygon polygon = new Polygon(ptc,mapView.getSpatialReference());
                        Double area = GeometryEngine.areaGeodetic(polygon,null, GeodeticCurveType.SHAPE_PRESERVING);
                        //df.format(area/666.67)+"亩\r\n"+m_df.format(area/10000)+"公顷\r\n"+
                        String areaString = df.format(area) + "平方米";
                        PictureMarkerSymbol pMarkerSymbol = SymobelUtils.textPicSymobel(ctxContext, areaString, Color.BLUE, 12);
                        if (aid == 0) {
                            lblGraphic = new Graphic(polygon.getExtent().getCenter(), pMarkerSymbol);
                            polyGraphic = new Graphic(polygon, sfs);
                            graphicsLayer.getGraphics().add(lblGraphic);
                            graphicsLayer.getGraphics().add(polyGraphic);
                            aid =1;
                        } else {
                            graphicsLayer.getGraphics().remove(lblGraphic);
                            graphicsLayer.getGraphics().remove(polyGraphic);
                            lblGraphic.setGeometry(polygon.getExtent().getCenter());
                            lblGraphic.setSymbol(pMarkerSymbol);
                            polyGraphic.setGeometry(polygon);
                            graphicsLayer.getGraphics().add(lblGraphic);
                            graphicsLayer.getGraphics().add(polyGraphic);
                        }
                    }
                }
                return true;
            }
            return false;
        }
    }
}
