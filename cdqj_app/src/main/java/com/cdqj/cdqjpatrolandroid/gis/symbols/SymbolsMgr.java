package com.cdqj.cdqjpatrolandroid.gis.symbols;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.GeometryType;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.Symbol;

/**
 * Created by lyf on 2018/9/13 20:25
 *
 * @author lyf
 * desc：显示操作等
 */
public class SymbolsMgr {

    /**
     * 默认高亮显示查询结果
     *
     * @param geo geo
     */
    public static Graphic highlightGraphic(Geometry geo) {
        return new Graphic(geo, getDefSymbol(geo.getGeometryType()));
    }

    /**
     * 高亮显示查询结果
     *
     * @param geo geo
     */
    public static Graphic addMapClickGraphic(Geometry geo) {
        if (geo.getGeometryType() != GeometryType.POINT) {
            return null;
        }
        SimpleMarkerSymbol sym = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.argb(0, 0, 255, 255), 10);
        SimpleLineSymbol outline = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLACK, 1);
        sym.setOutline(outline);
        return new Graphic(geo, sym);
    }

    /**
     * 高亮显示地图点击
     */
    public static Graphic addMapClickCircleGraphic(Point pt, double radus, SpatialReference reference) {
        if (pt == null) {
            return null;
        }
        Polygon polygon = getCircle(new Point(pt.getX(), pt.getY()), radus, reference);
        SimpleFillSymbol symbol = new SimpleFillSymbol();
        symbol.setColor(Color.argb(75, 0, 255, 255));
        SimpleLineSymbol outline = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLACK, 1);
        symbol.setOutline(outline);
        return new Graphic(polygon, symbol);
    }

    /**
     * 获取默认的选择样式
     *
     * @param geometryType geometryType
     * @return Symbol
     */
    public static Symbol getDefSymbol(GeometryType geometryType) {
        int color = Color.rgb(0, 255, 255);
        Symbol sym = null;
        if (geometryType == GeometryType.POINT || geometryType == GeometryType.MULTIPOINT) {
            sym = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.SQUARE, color, 10);
        } else if (geometryType == GeometryType.POLYLINE) {
            sym = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, color, 8);
        } else if (geometryType == GeometryType.POLYGON) {
            SimpleFillSymbol sfs = new SimpleFillSymbol();
            sfs.setColor(Color.argb(75, 0, 255, 255));
            sym = sfs;
        }
        return sym;
    }

    /**
     * 获取定位样式
     *
     * @return Symbol
     */
    public static Symbol getLocationSymbol() {
        int color = Color.rgb(0, 255, 255);
        return new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, color, 18);
    }

    public static Graphic getStartGraphic(Context cxt, Point pt, SpatialReference reference) {
        Drawable drawable = null;
        assert drawable != null;
        PictureMarkerSymbol directionSymbol = new PictureMarkerSymbol((BitmapDrawable) drawable);
        directionSymbol.setWidth(30);
        directionSymbol.setHeight(40);
        Point gsp = (Point) GeometryEngine.project(pt, reference);
        return new Graphic(gsp, directionSymbol);
    }

    public static Graphic getEndGraphic(Context cxt, Point pt, SpatialReference reference) {
        Drawable eDrawable = null;
        assert eDrawable != null;
        PictureMarkerSymbol eDirectionsymbol = new PictureMarkerSymbol((BitmapDrawable) eDrawable);
        eDirectionsymbol.setHeight(40);
        eDirectionsymbol.setWidth(30);
        Point edp = (Point) GeometryEngine.project(pt, reference);
        return new Graphic(edp, eDirectionsymbol);
    }

    public static Graphic getNavLineGraphic(String ringString, SpatialReference reference) {
        SimpleLineSymbol sls = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 3);
        PointCollection points = new PointCollection(reference);
        Graphic g = null;
        if (!ringString.equals("")) {
            String[] xys = ringString.split(";");
            if (xys.length > 2) {
                for (String xy1 : xys) {
                    String xy[] = xy1.split(",");
                    if (xy.length == 2) {
                        double x = Double.valueOf(xy[0]);
                        double y = Double.valueOf(xy[1]);
                        points.add(new Point(x, y, reference));
                    }
                }
                Polyline polyline = new Polyline(points);
                g = new Graphic(polyline, sls);
            }
        }
        return g;
    }

    /**
     * 获取圆的几何对象
     *
     * @param center center
     * @param radius radius
     * @return Polygon
     */
    public static Polygon getCircle(Point center, double radius, SpatialReference spatialReference) {
        PointCollection pts = getPoints(center, radius, spatialReference);
        return new Polygon(pts);
    }

    /**
     * @param center           center
     * @param radius           度或者米（度条件下大约为：1m=0.0000089）
     * @param spatialReference spatialReference
     * @return PointCollection
     */
    private static PointCollection getPoints(Point center, double radius, SpatialReference spatialReference) {
        int num = 50;
        PointCollection pts = new PointCollection(spatialReference);
        double sin;
        double cos;
        double x;
        double y;
        for (double i = 0; i < num; i++) {
            sin = Math.sin(Math.PI * 2 * i / num);
            cos = Math.cos(Math.PI * 2 * i / num);
            x = center.getX() + radius * sin;
            y = center.getY() + radius * cos;
            pts.add(new Point(x, y, spatialReference));
        }
        return pts;
    }
}
