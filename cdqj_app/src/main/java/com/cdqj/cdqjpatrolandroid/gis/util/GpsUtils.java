package com.cdqj.cdqjpatrolandroid.gis.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;

import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.gis.bean.GpsBean;
import com.esri.arcgisruntime.geometry.Point;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyf on 2018/9/14 15:30
 *
 * @author lyf
 * desc：坐标处理类
 */
public class GpsUtils {

    private static final double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 保留小数点后六位
     *
     * @param rateStr 传入处理字符串
     * @return String
     */
    public static String formateRate(String rateStr) {
        if (rateStr.contains(".")) {
            //获取小数点的位置
            int num;
            num = rateStr.indexOf(".");

            //获取小数点后面的数字 是否有6位 不足6位补足6位
            String dianAfter = rateStr.substring(0, num + 1);
            StringBuilder afterData = new StringBuilder(rateStr.replace(dianAfter, ""));
            if (afterData.length() < 6) {
                int length = 6 - afterData.length();
                for (int i = 0; i < length; i++) {
                    afterData.append("0");
                }
            }
            try {
                return rateStr.substring(0, num) + "." + afterData.substring(0, 6);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        } else {
            if ("1".equals(rateStr)) {
                return "100";
            } else {
                return rateStr;
            }
        }
    }

    /**
     * 保留小数点后六位
     *
     * @param rateStr 传入处理字符串
     * @param count 保留位数
     * @return String
     */
    public static double formateRate(String rateStr, int count) {
        if (rateStr.contains(".")) {
            //获取小数点的位置
            int num;
            num = rateStr.indexOf(".");

            //获取小数点后面的数字 是否有count位 不足count位补足count位
            String dianAfter = rateStr.substring(0, num + 1);
            StringBuilder afterData = new StringBuilder(rateStr.replace(dianAfter, ""));
            if (afterData.length() < count) {
                int length = count - afterData.length();
                for (int i = 0; i < length; i++) {
                    afterData.append("0");
                }
            }
            try {
                return Double.valueOf(rateStr.substring(0, num) + "." + afterData.substring(0, count));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0.0;
        } else {
            if ("1".equals(rateStr)) {
                return 0.0;
            } else {
                return Double.valueOf(rateStr);
            }
        }
    }

    /**
     * 根据两个位置的经纬度，来计算两地的距离（单位为M）
     * 参数为String类型
     *
     * @param lat1Str 起始点经度
     * @param lng1Str 起始点纬度
     * @param lat2Str 终点经度
     * @param lng2Str 终点纬度
     * @return 距离
     */
    public static Double getDistance(String lat1Str, String lng1Str, String lat2Str, String lng2Str) {
        if (StringUtils.isTrimEmpty(lat1Str) || StringUtils.isTrimEmpty(lng1Str) ||
                StringUtils.isTrimEmpty(lat2Str) || StringUtils.isTrimEmpty(lng2Str)) {
            return 0.0;
        }
        Double lat1 = Double.parseDouble(lat1Str);
        Double lng1 = Double.parseDouble(lng1Str);
        Double lat2 = Double.parseDouble(lat2Str);
        Double lng2 = Double.parseDouble(lng2Str);

        return getDistance(lat1, lng1, lat2, lng2);
    }

    /**
     * 根据坐标集，判断最大距离
     * @return 距离
     */
    public static Double getMaxDistance(double lat1Str, double lng1Str, List<GpsBean> xys) {
        double distance = 0.0;
        for (int i = 1;i < xys.size();i ++) {
            distance = getDistance(String.valueOf(lat1Str), String.valueOf(lng1Str), xys.get(i).getX(), xys.get(i).getY())>
                    getDistance(String.valueOf(lat1Str), String.valueOf(lng1Str), xys.get(i-1).getX(), xys.get(i-1).getY())?
                    getDistance(String.valueOf(lat1Str), String.valueOf(lng1Str), xys.get(i).getX(), xys.get(i).getY()):
                    getDistance(String.valueOf(lat1Str), String.valueOf(lng1Str), xys.get(i-1).getX(), xys.get(i-1).getY());
        }
        return distance;
    }

    /**
     * 根据坐标集，判断最大距离
     * @return 距离
     */
    public static Double getMaxDistance(String lat1Str, String lng1Str, List<GpsBean> xys) {
        Double[] doubles = new Double[xys.size()];
        for (int i = 0;i < xys.size();i ++) {
            doubles[i] = getDistance(lat1Str, lng1Str, xys.get(i).getX(), xys.get(i).getY());
        }
        Arrays.sort(doubles);
        return doubles[doubles.length - 1];
    }

    /**
     * 根据坐标集，判断最小距离
     * @return 距离
     */
    public static Double getMinDistance(String lat1Str, String lng1Str, List<GpsBean> xys) {
        Double[] doubles = new Double[xys.size()];
        for (int i = 0;i < xys.size();i ++) {
            doubles[i] = getDistance(lat1Str, lng1Str, xys.get(i).getX(), xys.get(i).getY());
        }
        Arrays.sort(doubles);
        return doubles[0];
    }

    /**
     * 根据坐标集，判断最小距离
     * @return 距离
     */
    public static Double getMinDistance(double lat1Str, double lng1Str, List<GpsBean> xys) {
        double distance = 0.0;
        for (int i = 1;i < xys.size();i ++) {
            distance = getDistance(String.valueOf(lat1Str), String.valueOf(lng1Str), xys.get(i).getX(), xys.get(i).getY())>
                    getDistance(String.valueOf(lat1Str), String.valueOf(lng1Str), xys.get(i-1).getX(), xys.get(i-1).getY())?
                    getDistance(String.valueOf(lat1Str), String.valueOf(lng1Str), xys.get(i-1).getX(), xys.get(i-1).getY()):
                    getDistance(String.valueOf(lat1Str), String.valueOf(lng1Str), xys.get(i).getX(), xys.get(i).getY());
        }
        return distance;
    }

    /**
     * 根据两个位置的经纬度，来计算两地的距离（单位为M）
     * 参数为String类型
     *
     * @param lat1 起始点经度
     * @param lng1 起始点纬度
     * @param lat2 终点经度
     * @param lng2 终点纬度
     * @return 距离
     */
    public static Double getDistance(Double lat1, Double lng1, Double lat2, Double lng2) {

        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        return formateRate(String.valueOf(s), 2);
    }

    /**
     * 获取当前用户一定距离以内的经纬度值
     * 单位米 return minLat
     * 最小经度 minLng
     * 最小纬度 maxLat
     * 最大经度 maxLng
     * 最大纬度 minLat
     */
    public static Map<String, String> getAround(String latStr, String lngStr, String raidus) {
        HashMap<String, String> map = new HashMap<>(2);

        // 传值给经度
        Double latitude = Double.parseDouble(latStr);
        // 传值给纬度
        Double longitude = Double.parseDouble(lngStr);

        // 获取每度
        Double degree = (24901 * 1609) / 360.0;
        double raidusMile = Double.parseDouble(raidus);

        Double mpdLng = Double.parseDouble((degree * Math.cos(latitude * (Math.PI / 180)) + "").replace("-", ""));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * raidusMile;
        //获取最小经度
        Double minLat = longitude - radiusLng;
        // 获取最大经度
        Double maxLat = longitude + radiusLng;

        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * raidusMile;
        // 获取最小纬度
        Double minLng = latitude - radiusLat;
        // 获取最大纬度
        Double maxLng = latitude + radiusLat;

        map.put("minLat", minLat + "");
        map.put("maxLat", maxLat + "");
        map.put("minLng", minLng + "");
        map.put("maxLng", maxLng + "");

        return map;
    }

    /**
     * 判断坐标点是否落在指定的多边形区域内
     * @param point  指定的坐标点
     * @param list   多变形区域的节点集合
     * @return   True 落在范围内 False 不在范围内
     */
    public static boolean isWithIn(Point point, List<Point> list) {
        double x = point.getX();
        double y = point.getY();

        int isum, icount, index;
        double dLon1, dLon2, dLat1, dLat2, dLon;

        if (list.size() < 3) {
            return false;
        }

        isum = 0;
        icount = list.size();

        for (index = 0; index < icount - 1; index++) {
            if (index == icount - 1) {
                dLon1 = list.get(index).getX();
                dLat1 = list.get(index).getY();
                dLon2 = list.get(0).getX();
                dLat2 = list.get(0).getY();
            } else {
                dLon1 = list.get(index).getX();
                dLat1 = list.get(index).getY();
                dLon2 = list.get(index + 1).getX();
                dLat2 = list.get(index + 1).getY();
            }

            // 判断指定点的 纬度是否在 相邻两个点(不为同一点)的纬度之间
            if (((y >= dLat1) && (y < dLat2)) || ((y >= dLat2) && (y < dLat1))) {
                if (Math.abs(dLat1 - dLat2) > 0) {
                    dLon = dLon1 - ((dLon1 - dLon2) * (dLat1 - y)) / (dLat1 - dLat2);
                    if (dLon < x){
                        isum++;
                    }
                }
            }
        }
        return (isum % 2) != 0;
    }

    /**
     * 根据坐标集合字符串获取中心点
     * @param grids grids
     * @return Point
     */
    public static Point getCenterPointFromListOfCoordinates(String grids) {
        //以下为简化方法（400km以内）
        int total = grids.split(" ").length;
        double lat = 0, lon = 0;
        for (String point:grids.split(" ")) {
            lat += Double.valueOf(point.split(",")[0]) * Math.PI / 180;
            lon += Double.valueOf(point.split(",")[1]) * Math.PI / 180;
        }
        lat /= total;
        lon /= total;
        return new Point(lat * 180 / Math.PI, lon * 180 / Math.PI);
    }

    /**
     * 强制帮用户打开GPS
     * @param context context
     */
    public static void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context context
     * @return true 表示开启
     */
    public static boolean isOPen(Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        assert locationManager != null;
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps) {
            return true;
        }
        return false;
    }
}
