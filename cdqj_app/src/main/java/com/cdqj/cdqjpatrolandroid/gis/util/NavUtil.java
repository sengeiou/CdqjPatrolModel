package com.cdqj.cdqjpatrolandroid.gis.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.blankj.utilcode.util.AppUtils;

/**
 * Created by lyf on 2018/9/13 14:11
 *
 * @author lyf
 * desc：导航工具类
 */
public class NavUtil {


    /**
     * 打开百度地图导航客户端
     * intent = Intent.getIntent("baidumap://map/navi?location=34.264642646862,108.95108518068&type=BLK&src=thirdapp.navi.you
     * location 坐标点 location与query二者必须有一个，当有location时，忽略query
     * query    搜索key   同上
     * type 路线规划类型  BLK:躲避拥堵(自驾);TIME:最短时间(自驾);DIS:最短路程(自驾);FEE:少走高速(自驾);默认DIS
     */
    public static void openBaiduNavi(String lat,String lng, Context context, String query, String type) {
        String stringBuffer = "baidumap://map/navi?location=" +
                lat + "," + lng + "&type=" + type;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(stringBuffer));
        intent.setPackage("com.baidu.BaiduMap");
        context.startActivity(intent);
    }

    /**
     * 打开百度地图导路线规划
     * Uri uri = Uri.parse("baidumap://map/direction?destination=latlng:"+"目的地lat"+","+ "目的地lng"+"|name:"+"目的地名称"+"&mode=driving");
     * Activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
     * @param  lat 坐标点
     * @param lng   lng
     * @param name 目的地名称
     */
    public static void openBaiduDirection(String lat,String lng, Context context, String name) {
        // Uri uri = Uri.parse("baidumap://map/direction?destination=latlng:"+"目的地lat"+","+ "目的地lng"+"|name:"+"目的地名称"+"&mode=driving")
        String stringBuffer = "baidumap://map/direction?destination=latlng:" +
                lat + "," + lng + "|name:" + name + "&mode=driving";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(stringBuffer));
        intent.setPackage("com.baidu.BaiduMap");
        context.startActivity(intent);
    }

    /**
     * 打开百度地图导路线规划
     * Uri uri = Uri.parse("baidumap://map/direction?destination=latlng:"+"目的地lat"+","+ "目的地lng"+"|name:"+"目的地名称"+"&mode=driving");
     * Activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
     * @param  lat 坐标点
     * @param lng   lng
     * @param name 目的地名称
     */
    public static void openBaiduDirection(double lat,double lng, Context context, String name) {
        // Uri uri = Uri.parse("baidumap://map/direction?destination=latlng:"+"目的地lat"+","+ "目的地lng"+"|name:"+"目的地名称"+"&mode=driving")
        String stringBuffer = "baidumap://map/direction?destination=latlng:" +
                lat + "," + lng + "|name:" + name + "&mode=driving";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(stringBuffer));
        intent.setPackage("com.baidu.BaiduMap");
        context.startActivity(intent);
    }

    /**
     * 打开百度地图导航客户端
     * intent = Intent.getIntent("baidumap://map/navi?location=34.264642646862,108.95108518068&type=BLK&src=thirdapp.navi.you
     * location 坐标点 location与query二者必须有一个，当有location时，忽略query
     * query    搜索key   同上
     * type 路线规划类型  BLK:躲避拥堵(自驾);TIME:最短时间(自驾);DIS:最短路程(自驾);FEE:少走高速(自驾);默认DIS
     */
    public static void openBaiduNavi(double lat,double lng, Context context, String query, String type) {
        String stringBuffer = "baidumap://map/navi?location=" +
                lat + "," + lng + "&type=" + type;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(stringBuffer));
        intent.setPackage("com.baidu.BaiduMap");
        context.startActivity(intent);
    }

    /**
     * 启动高德App线路规划
     */
    public static void openGaoDeDirection(String dlat,String dlon, String dName, Context context) {
        StringBuilder stringBuffer = new StringBuilder("androidamap://route/plan?sourceApplication=").append(AppUtils.getAppName());
        stringBuffer.append("&dlat=").append(dlat);
        stringBuffer.append("&dlon=").append(dlon);
        stringBuffer.append("&dName=").append(dName);
        stringBuffer.append("&dev=").append(0);
        stringBuffer.append("&t=").append(0);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(stringBuffer.toString()));
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }

    /**
     * 启动高德App进行路线规划导航 http://lbs.amap.com/api/amap-mobile/guide/android/route
     *
     * @param context
     * sourceApplication 必填 第三方调用应用名称。如 "appName"
     *  sid
     *  sla
     *  slon
     *  sname
     * did
     * @param dlat dlat
     * @param dlon dlon
     * @param dName dName
     *  dev               起终点是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     *  t                 t = 0（驾车）= 1（公交）= 2（步行）= 3（骑行）= 4（火车）= 5（长途客车）
     *                          （骑行仅在V788以上版本支持）
     */
    public static void openGaoDeDirection(double dlat, double dlon, String dName, Context context) {
        StringBuilder stringBuffer = new StringBuilder("androidamap://route/plan?sourceApplication=").append(AppUtils.getAppName());
//        if (!StringUtils.isEmpty(sid)) {
//            stringBuffer.append("&sid=").append(sid);
//        }
//        if (!StringUtils.isEmpty(sla)) {
//            stringBuffer.append("&sla=").append(sla);
//        }
//        if (!StringUtils.isEmpty(sla)) {
//            stringBuffer.append("&sla=").append(sla);
//        }
//        if (!StringUtils.isEmpty(slon)) {
//            stringBuffer.append("&slon=").append(slon);
//        }
//        if (!StringUtils.isEmpty(sname)) {
//            stringBuffer.append("&sname=").append(sname);
//        }
//        if (!StringUtils.isEmpty(did)) {
//            stringBuffer.append("&did=").append(did);
//        }
        stringBuffer.append("&dlat=").append(dlat);
        stringBuffer.append("&dlon=").append(dlon);
        stringBuffer.append("&dName=").append(dName);
        stringBuffer.append("&dev=").append(0);
        stringBuffer.append("&t=").append(0);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(stringBuffer.toString()));
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }

    /**
     * 启动高德App进行导航
     * sourceApplication 必填 第三方调用应用名称。如 amap
     * poiname           非必填 POI 名称
     * dev               必填 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     * style             必填 导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5 不走高速且避免收费；6 不走高速且躲避拥堵；7 躲避收费和拥堵；8 不走高速躲避收费和拥堵))
     */
    public static void openGaoDeNavi(String appName, String lat,String lng, Context context,
                                     int dev, int style) {
        String stringBuffer = "androidamap://navi?sourceApplication=" +
                appName + "&lat=" + lat +
                "&lon=" + lng +
                "&dev=" + dev +
                "&style=" + style;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(stringBuffer));
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }

    /**
     * 启动高德App进行导航
     * sourceApplication 必填 第三方调用应用名称。如 amap
     * poiname           非必填 POI 名称
     * dev               必填 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     * style             必填 导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5 不走高速且避免收费；6 不走高速且躲避拥堵；7 躲避收费和拥堵；8 不走高速躲避收费和拥堵))
     */
    public static void openGaoDeNavi(String appName, double lat,double lng, Context context,
                                     int dev, int style) {
        String stringBuffer = "androidamap://navi?sourceApplication=" +
                appName + "&lat=" + lat +
                "&lon=" + lng +
                "&dev=" + dev +
                "&style=" + style;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(stringBuffer));
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }

    /**
     * 打开google Web地图导航
     */
    public static void openWebGoogleNavi(String lat,String lng, Context context) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://ditu.google.cn/maps?hl=zh&mrt=loc&q=" + lat + "," + lng));
        context.startActivity(i);
    }

    /**
     * 打开google Web地图导航
     */
    public static void openWebGoogleNavi(double lat,double lng, Context context) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://ditu.google.cn/maps?hl=zh&mrt=loc&q=" + lat + "," + lng));
        context.startActivity(i);
    }

    /**
     * 打开google地图客户端开始导航
     * q:目的地
     * mode：d驾车 默认
     */
    public static void openGoogleNavi(String lat,String lng, Context context, String mode) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + lat + "," + lng + "&mode=" + mode));
        i.setPackage("com.google.android.apps.maps");
        context.startActivity(i);
    }

    /**
     * 打开google地图客户端开始导航
     * q:目的地
     * mode：d驾车 默认
     */
    public static void openGoogleNavi(double lat,double lng, Context context, String mode) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + lat + "," + lng + "&mode=" + mode));
        i.setPackage("com.google.android.apps.maps");
        context.startActivity(i);
    }

    /**
     * 跳转应用商店安装
     * @param appName app 包名及路径
     *                百度 market://details?id=com.baidu.BaiduMap
     *                高德 market://details?id=com.autonavi.minimap
     *                谷歌 market://details?id=com.google.android.apps.maps
     *
     */
    public static void gotoMarket(String appName, Context context) {
        //market为路径，id为包名
        //显示手机上所有的market商店
        Uri uri = Uri.parse("market://details?id=" + appName);
        Intent intent;
        intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    /**
     * 网页版百度地图 有经纬度
     * @param originLat originLat 起点
     * @param originLon originLon
     * @param originName ->注：必填
     * @param desLat desLat  目的地
     * @param desLon desLon
     * @param destination destination
     * @param region : 当给定region时，认为起点和终点都在同一城市，除非单独给定起点或终点的城市。-->注：必填，不填不会显示导航路线
     * @param appName appName
     * @return String
     */
    public static String getWebBaiduMapUri(String originLat, String originLon, String originName, String desLat, String desLon, String destination, String region, String appName) {
        String uri = "http://api.map.baidu.com/direction?origin=latlng:%1$s,%2$s|name:%3$s" +
                "&destination=latlng:%4$s,%5$s|name:%6$s&mode=driving&region=%7$s&output=html" +
                "&src=%8$s";
        return String.format(uri, originLat, originLon, originName, desLat, desLon, destination, region, appName);
    }

    /**
     * 网页版百度地图 有经纬度
     * @param originLat originLat 起点
     * @param originLon originLon
     * @param originName ->注：必填
     * @param desLat desLat  目的地
     * @param desLon desLon
     * @param destination destination
     * @param region : 当给定region时，认为起点和终点都在同一城市，除非单独给定起点或终点的城市。-->注：必填，不填不会显示导航路线
     * @param appName appName
     * @return String
     */
    public static String getWebBaiduMapUri(double originLat, double originLon, String originName, double desLat, double desLon, String destination, String region, String appName) {
        String uri = "http://api.map.baidu.com/direction?origin=latlng:%1$s,%2$s|name:%3$s" +
                "&destination=latlng:%4$s,%5$s|name:%6$s&mode=driving&region=%7$s&output=html" +
                "&src=%8$s";
        return String.format(uri, originLat, originLon, originName, desLat, desLon, destination, region, appName);
    }
}
