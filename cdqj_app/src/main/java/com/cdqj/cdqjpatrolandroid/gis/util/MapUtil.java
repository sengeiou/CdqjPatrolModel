package com.cdqj.cdqjpatrolandroid.gis.util;

import android.content.Context;
import android.graphics.Color;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.gis.bean.BasePolygonsBean;
import com.cdqj.cdqjpatrolandroid.gis.bean.GisParamsBean;
import com.cdqj.cdqjpatrolandroid.gis.layer.LayerType;
import com.cdqj.cdqjpatrolandroid.gis.tianditu.MBTilesLayer;
import com.cdqj.cdqjpatrolandroid.gis.tianditu.TDTLayerType;
import com.cdqj.cdqjpatrolandroid.gis.tianditu.TianDiTuMethodsClass;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.utils.GsonUtils;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.LicenseLevel;
import com.esri.arcgisruntime.LicenseResult;
import com.esri.arcgisruntime.LicenseStatus;
import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.data.Field;
import com.esri.arcgisruntime.data.TileCache;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.layers.Layer;
import com.esri.arcgisruntime.layers.WebTiledLayer;
import com.esri.arcgisruntime.layers.WmtsLayer;
import com.esri.arcgisruntime.mapping.LayerList;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.BackgroundGrid;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyf on 2018/9/12 19:23
 *
 * @author lyf
 * desc：地图工具类
 */
public class MapUtil {

    /**
     * 初始化背景地图
     *
     * @param url    url
     * @param center center
     * @return Layer[]
     */
    public static Layer[] getBKLayer(String url, String type, String center) {
        return getLayer(url, type, getBkUrlKey());
    }

    /**
     * 获取背景图层
     *
     * @param params params
     * @return Layer[]
     */
    public static Layer[] getBKLayer(GisParamsBean params) {
        String bkLyrUrl, bkLyrType, center;
        //初始化时首先加载在线地图
        bkLyrUrl = params.appBkOnlineLyr;
        bkLyrType = params.appBkOnlineLyrType;
        center = params.appCenter;

        if (!StringUtils.isTrimEmpty(params.appBkOfflineLyr)) {
            //如果配置了离线地图参数，则查找本地是否已经下载了地图
            if (fileExist(params.appBkOfflineLyr, params.appBkOfflineLyrType)) {
                bkLyrUrl = params.appBkOfflineLyr;
                bkLyrType = params.appBkOfflineLyrType;
                LogUtils.d("离线地图--", "判断是离线地图：" + bkLyrUrl);
            }
        }
        return getBKLayer(bkLyrUrl, bkLyrType, center);
    }

    /**
     * 获取管线图层
     *
     * @param params params
     * @return Layer[]
     */
    public static Layer[] getPipeLayer(GisParamsBean params) {
        String pipeLyrUrl, pipesLyrType;
        //判断是否是离线管线地图
        if (fileExist(params.appPipesOfflineLyr, params.appPipesOfflineLyrType)) {
            pipeLyrUrl = params.appPipesOfflineLyr;
            pipesLyrType = params.appPipesOfflineLyrType;
        } else {
            pipeLyrUrl = params.appPipesOnlineLyr;
            pipesLyrType = params.appPipesOnlineLyrType;
        }
        return getLayer(pipeLyrUrl, pipesLyrType, "");
    }

    /**
     * 判断文件是否存在
     *
     * @param url  url
     * @param type type
     * @return boolean
     */
    private static boolean fileExist(String url, String type) {
        if (StringUtils.isTrimEmpty(type)) {
            return false;
        }
        int t = Integer.parseInt(type);
        String path = getUrl(url, t);
        LogUtils.d("离线地图--", "fileExist：" + path);
        return FileUtils.isFileExists(Constant.APP_PATH + File.separator + path);
    }

    /**
     * 获取管线图层
     *
     * @param url  url
     * @param type type
     * @return Layer[]
     */
    public static Layer[] getPipeLayer(String url, String type) {
        return getLayer(url, type, getPipesUrlKey());
    }

    /**
     * 获取地图
     *
     * @param url   url
     * @param type  type
     * @param lyrId lyrId
     * @return Layer[]
     */
    public static Layer[] getLayer(String url, String type, String lyrId) {
        if (StringUtils.isTrimEmpty(url) || StringUtils.isTrimEmpty(type)) {
            return null;
        }
        if (isInteger(type)) {
            int t = Integer.parseInt(type);
            String path = getUrl(url, t);
            return createLayer(path, t, lyrId);
        }
        return null;
    }

    /**
     * 获取离线图层
     *
     * @param absPath absPath
     * @param type    type
     * @param lyrId   lyrId
     * @return Layer[]
     */
    public static Layer[] getLayerFromAbsPath(String absPath, String type, String lyrId) {
        if (StringUtils.isTrimEmpty(absPath) || StringUtils.isTrimEmpty(type)) {
            return null;
        }
        if (isInteger(type)) {
            int t = Integer.parseInt(type);
            return createLayer(absPath, t, lyrId);

        }
        return null;
    }

    /**
     * 判断字符串是否是数字
     *
     * @param str str
     * @return boolean
     */
    public static boolean isInteger(String str) {
        return RegexUtils.isMatch("^[-\\+]?[\\d]*$", str);
    }

    /**
     * 根据参数获取全图范围
     *
     * @param centerParam      *.appCenter
     * @param spatialReference spatialReference
     * @return null or Viewpoint
     */
    public static Viewpoint getViewpointFromParam(String centerParam, SpatialReference spatialReference) {
        Viewpoint viewpoint = null;
        if (centerParam != null) {
            String[] strs = centerParam.split("\\|");
            if (spatialReference != null) {
                if (strs.length == 3) {
                    Point pt1 = new Point(Double.parseDouble(strs[0]), Double.parseDouble(strs[1]), spatialReference);
                    viewpoint = new Viewpoint(pt1, Double.parseDouble(strs[2]));
                } else if (strs.length == 4) {
                    Point pt1 = new Point(Double.parseDouble(strs[0]), Double.parseDouble(strs[1]), spatialReference);
                    Point pt2 = new Point(Double.parseDouble(strs[2]), Double.parseDouble(strs[3]), spatialReference);
                    viewpoint = new Viewpoint(new Envelope(pt1, pt2));
                }
            }
        }
        return viewpoint;
    }

    /**
     * 根据id获取图层对象
     *
     * @param layerList 图层外部编号
     * @param strId     图层外部编号
     * @return Layer
     */
    public static Layer getLayer(LayerList layerList, String strId) {
        Layer result = null;
        if (strId != null) {
            for (Layer layer : layerList) {
                if (layer.getId().equals(strId)) {
                    result = layer;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 设置图层显隐
     *
     * @param layerList 地图图层集合
     * @param strId     图层外部编号
     * @param b         boolean
     */
    public static void setLayerVisible(LayerList layerList, String strId, boolean b) {
        if (strId != null) {
            for (Layer layer : layerList) {
                if (layer.getId().equals(strId)) {
                    layer.setVisible(b);
                    break;
                }
            }
        }
    }

    /**
     * 检查认证信息
     *
     * @return boolean
     */
    private static boolean checkLicense() {
        if (ArcGISRuntimeEnvironment.getLicense().getLicenseLevel() == LicenseLevel.DEVELOPER) {
            String licenseCode = "runtimelite,1000,rud7659408794,none,ZZ0RJAY3FY0GEBZNR002";
            LicenseResult licenseResult = ArcGISRuntimeEnvironment.setLicense(licenseCode);
            return (licenseResult.getLicenseStatus() == LicenseStatus.INVALID);
        }
        return true;
    }

    public static void setInitMap(MapView mapView) {
        checkLicense();
        BackgroundGrid bGrid = new BackgroundGrid();
        bGrid.setColor(Color.WHITE);
        bGrid.setGridLineColor(Color.argb(0, 0, 0, 0));

        mapView.setAttributionTextVisible(false);
        mapView.setBackgroundGrid(bGrid);
    }

    public static Point gpsLocate(MapView mapView) {
        if (!mapView.getLocationDisplay().isStarted()) {
            mapView.getLocationDisplay().setAutoPanMode(LocationDisplay.AutoPanMode.OFF);
            mapView.getLocationDisplay().startAsync();
        }
        return mapView.getLocationDisplay().getLocation().getPosition();
    }


    /**
     * 创建服务图层
     *
     * @param url   url
     * @param type  图层类型, 为LayerType类中指定的值
     * @param lyrId lyrId
     * @return Layer
     */
    public static Layer[] createLayer(String url, int type, String lyrId) {
        Layer lyr = null;
        switch (type) {
            case LayerType.TILED:
                lyr = createTiledLayer(url, lyrId);
                break;
            case LayerType.DYNAMIC:
                lyr = createDynLayer(url, lyrId);
                break;
            case LayerType.LOCAL:
            case LayerType.TPK:
                lyr = createLocalLayer(url, lyrId);
                break;
            case LayerType.TDT:
                return createTDTLayer();
            case LayerType.WMTS:
                lyr = createWMTSLayer(url, lyrId);
                break;
            case LayerType.MBTILES:
                //String fpath= getFile(url,".mbtiles");
                MBTilesLayer mbLayr=new MBTilesLayer(url);
                lyr=mbLayr.getLayer();
                break;
            default:
                break;
        }
        return new Layer[]{lyr};
    }

    /**
     * 从mbtiles文件创建自定义切片图层
     * @param path 离线地图地址
     * @return ArcGISTiledLayer
     */
    private static ArcGISTiledLayer createMbTilesLayer(String path){
        File f = new File(Constant.APP_PATH + File.separator + path);
        if (!f.exists()) {
            LogUtils.e("文件不存在.");
            ToastBuilder.showShortWarning("离线文件不存在或地址配置错误!");
            return null;
        }
        TileCache mainTileCache = new TileCache(path);
        return new ArcGISTiledLayer(mainTileCache);
    }


    /**
     * 创建动态地图服务图层
     *
     * @param url url
     * @return ArcGISTiledLayer
     */
    private static ArcGISMapImageLayer createDynLayer(String url, String lyrId) {
        //http://localhost:6080/arcgis/rest/services/cdgas/pipes_2k/MapServer
        ArcGISMapImageLayer imageLayer = new ArcGISMapImageLayer(url);
        if (lyrId != null && !StringUtils.isEmpty(lyrId)) {
            imageLayer.setId(lyrId);
        }
        return imageLayer;
    }


    /**
     * 创建切片地图服务图层
     *
     * @param url   url
     * @param lyrId lyrId
     * @return ArcGISTiledLayer
     */
    private static ArcGISTiledLayer createTiledLayer(String url, String lyrId) {
        //"http://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer"
        ArcGISTiledLayer tiledLayer = new ArcGISTiledLayer(url);
        if (lyrId != null && !StringUtils.isEmpty(lyrId)) {
            tiledLayer.setId(lyrId);
        }
        return tiledLayer;
    }

    /**
     * 创建离线地图图层
     *
     * @param path  path
     * @param lyrId lyrId
     * @return ArcGISTiledLayer
     */
    private static ArcGISTiledLayer createLocalLayer(String path, String lyrId) {
        File f = new File(Constant.APP_PATH + File.separator + path);
        if (!f.exists()) {
            LogUtils.d("离线地图","createLocalLayer:文件不存在.");
            return null;
        }
        TileCache tileCache = new TileCache(path);
        ArcGISTiledLayer tiledLayer = new ArcGISTiledLayer(tileCache);
        if (lyrId != null && !StringUtils.isEmpty(lyrId)) {
            tiledLayer.setId(lyrId);
        }
        return tiledLayer;
    }

    /**
     * 创建wmts地图服务图层
     *
     * @param url url
     * @return WmtsLayer
     */
    private static WmtsLayer createWMTSLayer(String url, String lyrId) {
        //"http://[SERVER:PORT]/[INSTANCE]/services/[SERVICE]/MapServer/WMTS/1.0.0/WMTSCapabilities.xml"
        WmtsLayer wmtsLayer = new WmtsLayer(url, java.util.UUID.randomUUID().toString());
        if (lyrId != null && !StringUtils.isEmpty(lyrId)) {
            wmtsLayer.setId(lyrId);
        }
        return wmtsLayer;
    }

    /**
     * 从mbtiles文件创建自定义切片图层
     *
     * @return WebTiledLayer
     */
    public static WebTiledLayer[] createTDTLayer() {
        WebTiledLayer lyrVec = TianDiTuMethodsClass.createTianDiTuTiledLayer(TDTLayerType.TIANDITU_VECTOR_2000);
        lyrVec.setId("tdt_vec");
        WebTiledLayer lyrCva = TianDiTuMethodsClass.createTianDiTuTiledLayer(TDTLayerType.TIANDITU_VECTOR_ANNOTATION_CHINESE_2000);
        lyrCva.setId("tdt_cva");
        return new WebTiledLayer[]{lyrVec, lyrCva};
    }

    /**
     * 在线天地图修改地图类型
     * 1 矢量图
     * 2 影像图
     * 3 地形图
     *
     * @return WebTiledLayer
     */
    public static WebTiledLayer[] changeTDTLayer(int type) {
        WebTiledLayer lyrVec, lyrCva;
        if (type == 2) {
            lyrVec = TianDiTuMethodsClass.createTianDiTuTiledLayer(TDTLayerType.TIANDITU_IMAGE_2000);
            lyrCva = TianDiTuMethodsClass.createTianDiTuTiledLayer(TDTLayerType.TIANDITU_IMAGE_ANNOTATION_CHINESE_2000);
        } else if (type == 3) {
            lyrVec = TianDiTuMethodsClass.createTianDiTuTiledLayer(TDTLayerType.TIANDITU_TERRAIN_2000);
            lyrCva = TianDiTuMethodsClass.createTianDiTuTiledLayer(TDTLayerType.TIANDITU_TERRAIN_ANNOTATION_CHINESE_2000);
        } else {
            lyrVec = TianDiTuMethodsClass.createTianDiTuTiledLayer(TDTLayerType.TIANDITU_VECTOR_2000);
            lyrCva = TianDiTuMethodsClass.createTianDiTuTiledLayer(TDTLayerType.TIANDITU_VECTOR_ANNOTATION_CHINESE_2000);
        }
        lyrVec.setId("tdt_vec");
        lyrCva.setId("tdt_cva");
        return new WebTiledLayer[]{lyrVec, lyrCva};
    }

    /**
     * 获取本地文件
     *
     * @param url  地图数据目录
     * @param type LayerType 对应的值
     * @return String
     */
    public static String getUrl(String url, int type) {
        String path = url;
        switch (type) {
            case LayerType.TPK:
                path = path + ".tpk";
                break;
            case LayerType.MBTILES:
                path = path + ".mbtiles";
                break;
            case LayerType.LOCAL:
                //返回完整路径
                break;
            default:
                path = url;
                break;
        }
        return path;
    }


    /**
     * 获取指定目录下的文件
     *
     * @param fullPath 目录	: 文件完整路径
     * @param name     文件扩展名: .tpk
     * @return String
     */
    private static String getFile(String fullPath, String name) {
        if (!StringUtils.isTrimEmpty(fullPath)
                && ObjectUtils.isNotEmpty(FileUtils.listFilesInDir(fullPath))) {
            List<File> fileList = FileUtils.listFilesInDir(fullPath);
            if (fileList.size() > 0) {
                return fileList.get(0).getAbsolutePath();
            }
        }
        return "";
    }

    public static String getBkUrlKey() {
        return "appBkLayer";
    }

    public static String getPipesUrlKey() {
        return "appPipesLayer";
    }

    /**
     * 向地图容器中添加图层
     *
     * @param layerList layerList添加者
     * @param layers    layers被添加
     * @return LayerList
     */
    public static LayerList addLayer(LayerList layerList, Layer[] layers) {
        //return ObjectUtils.isEmpty(layers)?layerList:Collections.addAll(layerList, layers)?layerList:null;
        if (ObjectUtils.isEmpty(layers)) {
            return layerList;
        } else {
            end:
            for (Layer layer : layers) {
                // 防止图层为null加载失败
                if (ObjectUtils.isNotEmpty(layer)) {
                    for (int i = 0; i < layerList.size(); i++) {
                        if (layer.getId().equals(layerList.get(i).getId())) {
                            layerList.set(i, layer);
                            continue end;
                        }
                    }
                    layerList.add(layer);
                }
            }
            return layerList;
        }
    }

    public static Map<String, Object> convertMap(FeatureQueryResult featureQueryResult) {
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> maps = featureQueryResult.iterator().next().getAttributes();
        List<Field> list = featureQueryResult.getFields();

        for (Field field : list) {
            if (maps.containsKey(field.getName())) {
                String key = field.getName();
                String key2 = field.getAlias();
                Object value = maps.get(key) == null ? "" : maps.get(key);
                returnMap.put(key2, value);
            }
        }
        return returnMap;
    }

    /**
     * 添加图层（片）
     *
     * @param context         context
     * @param text            文字显示图层
     * @param graphicsOverlay graphicsOverlay 图层实例
     * @param reference       reference
     * @param list            添加对象
     * @param sType           添加属性对象
     * @param color           片区颜色及文字颜色
     * @param isShowName      是否显示名字
     */
    public static void addPolygons(Context context, GraphicsOverlay text, GraphicsOverlay graphicsOverlay, SpatialReference reference, List<? extends BasePolygonsBean> list, List<Map<String, Object>> sType, String color, boolean isShowName) {
        String bgColorStr = color.replaceAll("#", "#2B");
        SimpleFillSymbol sfs = SymobelUtils.getSimpleFillSymbol(context, color, bgColorStr);
        PointCollection pts;
        for (int i = 0; i < list.size(); i++) {
            BasePolygonsBean bean = list.get(i);
            pts = new PointCollection(reference);
            String ringString = bean.getGisArea();
            if (!StringUtils.isTrimEmpty(ringString)) {
                String[] xys = ringString.split(" ");
                if (xys.length > 2) {
                    for (String xy : xys) {
                        double x = Double.valueOf(xy.split(",")[0]);
                        double y = Double.valueOf(xy.split(",")[1]);
                        pts.add(new Point(x, y, reference));
                    }
                    Polygon poly = new Polygon(pts);

                    if (isShowName) {
                        Point pTxtPoint = poly.getExtent().getCenter();

                        PictureMarkerSymbol pMarkerSymbol = SymobelUtils.textPicSymobel(context,
                                StringUtils.isTrimEmpty(bean.getName()) ? "未知" : bean.getName(),
                                Color.parseColor(StringUtils.isTrimEmpty(color) ? "#ff6161" : color), 15);
                        Graphic txtGraphic = new Graphic(pTxtPoint, sType.get(i), pMarkerSymbol);
                        text.getGraphics().add(txtGraphic);
                    }

                    Graphic pGraphic = new Graphic(poly, sType.get(i), sfs);
                    graphicsOverlay.getGraphics().add(pGraphic);
                }
            }
        }
    }

    /**
     * 添加图层（片）
     *
     * @param context         context
     * @param graphicsOverlay graphicsOverlay 图层实例
     * @param reference       reference
     * @param list            添加对象
     * @param sType           添加属性对象
     * @param color           片区颜色及文字颜色
     * @param isShowName      是否显示名字
     */
    public static void addPolygons(Context context, GraphicsOverlay graphicsOverlay, SpatialReference reference, List<? extends BasePolygonsBean> list, List<Map<String, Object>> sType, String color, boolean isShowName) {
        SimpleFillSymbol sfs = SymobelUtils.getSimpleFillSymbol(context, color);
        PointCollection pts;
        for (int i = 0; i < list.size(); i++) {
            BasePolygonsBean bean = list.get(i);
            pts = new PointCollection(reference);
            String ringString = bean.getGisArea();
            if (!StringUtils.isTrimEmpty(ringString)) {
                String[] xys = ringString.split(" ");
                if (xys.length > 2) {
                    for (String xy : xys) {
                        double x = Double.valueOf(xy.split(",")[0]);
                        double y = Double.valueOf(xy.split(",")[1]);
                        pts.add(new Point(x, y, reference));
                    }
                    Polygon poly = new Polygon(pts);

                    if (isShowName) {
                        Point pTxtPoint = poly.getExtent().getCenter();

                        PictureMarkerSymbol pMarkerSymbol = SymobelUtils.textPicSymobel(context,
                                StringUtils.isTrimEmpty(bean.getName()) ? "未知" : bean.getName(),
                                Color.parseColor(StringUtils.isTrimEmpty(color) ? "#ff6161" : color), 13);
                        Graphic txtGraphic = new Graphic(pTxtPoint, sType.get(i), pMarkerSymbol);
                        graphicsOverlay.getGraphics().add(txtGraphic);
                    }

                    Graphic pGraphic = new Graphic(poly, sType.get(i), sfs);
                    graphicsOverlay.getGraphics().add(pGraphic);
                }
            }
        }
    }

    /**
     * 添加图层（片）
     *
     * @param context         context
     * @param graphicsOverlay graphicsOverlay 图层实例
     * @param reference       reference
     * @param list            添加对象
     * @param sType           添加属性对象
     * @param color           片区颜色及文字颜色
     * @param bgColor         背景颜色
     * @param isShowName      是否显示名字
     */
    public static void addPolygons(Context context, GraphicsOverlay graphicsOverlay, SpatialReference reference, List<? extends BasePolygonsBean> list, List<Map<String, Object>> sType, String color, String bgColor, boolean isShowName) {
        SimpleFillSymbol sfs = SymobelUtils.getSimpleFillSymbol(context, color, bgColor);
        PointCollection pts;
        for (int i = 0; i < list.size(); i++) {
            BasePolygonsBean bean = list.get(i);
            pts = new PointCollection(reference);
            String ringString = bean.getGisArea();
            if (!StringUtils.isTrimEmpty(ringString)) {
                String[] xys = ringString.split(" ");
                if (xys.length > 2) {
                    for (String xy : xys) {
                        double x = Double.valueOf(xy.split(",")[0]);
                        double y = Double.valueOf(xy.split(",")[1]);
                        pts.add(new Point(x, y, reference));
                    }
                    Polygon poly = new Polygon(pts);

                    if (isShowName) {
                        Point pTxtPoint = poly.getExtent().getCenter();

                        PictureMarkerSymbol pMarkerSymbol = SymobelUtils.textPicSymobel(context,
                                StringUtils.isTrimEmpty(bean.getName()) ? "未知" : bean.getName(),
                                Color.parseColor(StringUtils.isTrimEmpty(color) ? "#ff6161" : color), 13);
                        Graphic txtGraphic = new Graphic(pTxtPoint, sType.get(i), pMarkerSymbol);
                        graphicsOverlay.getGraphics().add(txtGraphic);
                    }

                    Graphic pGraphic = new Graphic(poly, sType.get(i), sfs);
                    graphicsOverlay.getGraphics().add(pGraphic);
                }
            }
        }
    }

    /**
     * 添加图层（片）
     *
     * @param ctx             ctx
     * @param graphicsOverlay graphicsOverlay 图层实例
     * @param reference       reference
     * @param list            添加对象
     * @param sType           添加对象标识
     * @param red             red
     * @param green           green
     * @param blue            blue
     * @param b               b
     */
    public static void addPolygons(Context ctx, GraphicsOverlay graphicsOverlay, SpatialReference reference, List<? extends BasePolygonsBean> list, String sType, int red, int green, int blue, boolean... b) {
        SimpleFillSymbol sfs = SymobelUtils.getSimpleFillSymbol(red, green, blue);
        PointCollection pts;
        for (BasePolygonsBean bean : list) {
            pts = new PointCollection(reference);
            String ringString = bean.getGisArea();
            if (!StringUtils.isTrimEmpty(ringString)) {
                String[] xys = ringString.split(" ");
                if (xys.length > 2) {
                    for (int j = 0; j < xys.length; j += 1) {
                        double x = Double.valueOf(xys[j].split(",")[0]);
                        double y = Double.valueOf(xys[j].split(",")[1]);
                        pts.add(new Point(x, y, reference));
                    }
                    Polygon poly = new Polygon(pts);

                    Point pTxtPoint = poly.getExtent().getCenter();
                    PictureMarkerSymbol pMarkerSymbol = SymobelUtils.textPicSymobel(ctx,
                            StringUtils.isTrimEmpty(bean.getAddress()) ? bean.getName() : bean.getAddress(), Color.BLUE, 13);
                    Graphic txtGraphic = new Graphic(pTxtPoint, json2MapInMap(bean), pMarkerSymbol);
                    graphicsOverlay.getGraphics().add(txtGraphic);

                    Graphic pGraphic = new Graphic(poly, json2MapInMap(bean), sfs);
                    graphicsOverlay.getGraphics().add(pGraphic);
                }
            }
        }
    }

    /**
     * 添加图层（片）
     *
     * @param ctx             ctx
     * @param graphicsOverlay graphicsOverlay 图层实例
     * @param reference       reference
     * @param list            添加对象
     * @param sType           添加对象标识
     * @param colorId         colorId
     * @param b               b
     */
    public static void addPolygons(Context ctx, GraphicsOverlay graphicsOverlay, SpatialReference reference, List<? extends BasePolygonsBean> list, String sType, int colorId, boolean... b) {
        SimpleFillSymbol sfs = SymobelUtils.getSimpleFillSymbol(colorId);
        PointCollection pts;
        for (BasePolygonsBean bean : list) {
            pts = new PointCollection(reference);
            String ringString = bean.getGisArea();
            if (!StringUtils.isTrimEmpty(ringString)) {
                String[] xys = ringString.split(" ");
                if (xys.length > 2) {
                    for (int j = 0; j < xys.length; j += 1) {
                        double x = Double.valueOf(xys[j].split(",")[0]);
                        double y = Double.valueOf(xys[j].split(",")[1]);
                        pts.add(new Point(x, y, reference));
                    }

                    Polygon poly = new Polygon(pts);

                    Point pTxtPoint = poly.getExtent().getCenter();
                    PictureMarkerSymbol pMarkerSymbol = SymobelUtils.textPicSymobel(ctx,
                            StringUtils.isTrimEmpty(bean.getAddress()) ? bean.getName() : bean.getAddress(), Color.BLUE, 13);
                    Graphic txtGraphic = new Graphic(pTxtPoint, json2MapInMap(bean), pMarkerSymbol);
                    graphicsOverlay.getGraphics().add(txtGraphic);

                    Graphic pGraphic = new Graphic(poly, json2MapInMap(bean), sfs);
                    graphicsOverlay.getGraphics().add(pGraphic);

                }
            }
        }
    }

    /**
     * 添加图层，并保证图层单例
     *
     * @param mapView 地图
     * @param overlay 添加的图层
     */
    public static GraphicsOverlay addSingelLayer(MapView mapView, GraphicsOverlay overlay) {
        if (overlay == null) {
            overlay = new GraphicsOverlay();
            mapView.getGraphicsOverlays().add(overlay);
        }
        return overlay;
    }

    /**
     * 添加图层点，并保证图层单例（是否自动移动到中心）
     *
     * @param mapView   地图
     * @param overlay   添加的图层
     * @param resId     resId 资源ID
     * @param x         x
     * @param y         y
     * @param reference reference
     * @param isCenter  isCenter
     */
    public static void addPointLayer(MapView mapView, GraphicsOverlay overlay, Context context, int resId,
                                     double x, double y, SpatialReference reference, boolean isCenter) {
        Point point = new Point(x, y, reference);
        addPointLayer(mapView, overlay, context, resId, point, isCenter);
    }

    /**
     * 添加图层点，并保证图层单例（是否自动移动到中心）
     *
     * @param mapView  地图
     * @param overlay  添加的图层
     * @param resId    resId 资源ID
     * @param pt       Point
     * @param isCenter isCenter
     */
    public static void addPointLayer(MapView mapView, GraphicsOverlay overlay, Context context, int resId,
                                     Point pt, boolean isCenter) {
        PictureMarkerSymbol markerSymbol = SymobelUtils.pictureSymobelByResId(context, resId);
        markerSymbol.setOffsetY(13);
        addSingelLayer(mapView, overlay).getGraphics().clear();
        addSingelLayer(mapView, overlay).getGraphics().add(new Graphic(pt, markerSymbol));
        if (isCenter) {
            mapView.setViewpointCenterAsync(pt, MapConstant.SCALE);
        }
    }

    /**
     * 添加图层点，并保证图层单例（是否清除）
     *
     * @param mapView 地图
     * @param overlay 添加的图层
     * @param resId   resId 资源ID
     * @param pts     Point 集合
     * @param isClear 是否消除之前图层
     */
    public static void addPointLayer(MapView mapView, GraphicsOverlay overlay, Context context, int resId,
                                     Point[] pts, boolean isClear) {
        PictureMarkerSymbol markerSymbol = SymobelUtils.pictureSymobelByResId(context, resId);
        markerSymbol.setOffsetY(13);
        if (isClear) {
            addSingelLayer(mapView, overlay).getGraphics().clear();
        }
        for (Point point : pts) {
            addSingelLayer(mapView, overlay).getGraphics().add(new Graphic(point, markerSymbol));
        }
    }

    /**
     * 添加图层点，并保证图层单例（是否自动移动到中心）
     *
     * @param mapView   地图
     * @param overlay   添加的图层
     * @param resId     resId 资源ID
     * @param x         x
     * @param y         y
     * @param reference reference
     * @param map       属性
     * @param isCenter  isCenter
     */
    public static void addPointLayer(MapView mapView, GraphicsOverlay overlay, Context context, int resId,
                                     double x, double y, SpatialReference reference, Map<String, Object> map, boolean isCenter) {
        PictureMarkerSymbol markerSymbol = SymobelUtils.pictureSymobelByResId(context, resId);
        markerSymbol.setOffsetY(13);
        Point point = new Point(x, y, reference);
        addSingelLayer(mapView, overlay).getGraphics().clear();
        addSingelLayer(mapView, overlay).getGraphics().add(new Graphic(point, map, markerSymbol));
        if (isCenter) {
            mapView.setViewpointCenterAsync(point, MapConstant.SCALE);
        }
    }

    /**
     * 添加图层点，并保证图层单例（是否自动移动到中心）
     *
     * @param mapView  地图
     * @param overlay  添加的图层
     * @param resId    resId 资源ID
     * @param pt       Point
     * @param map      属性
     * @param isCenter isCenter
     * @param isClear  isClear
     */
    public static void addPointLayer(MapView mapView, GraphicsOverlay overlay, Context context, int resId,
                                     Point pt, Map<String, Object> map, boolean isCenter, boolean isClear) {
        PictureMarkerSymbol markerSymbol = SymobelUtils.pictureSymobelByResId(context, resId);
        markerSymbol.setOffsetY(13);
        if (isClear) {
            addSingelLayer(mapView, overlay).getGraphics().clear();
        }
        addSingelLayer(mapView, overlay).getGraphics().add(new Graphic(pt, map, markerSymbol));
        if (isCenter) {
            mapView.setViewpointCenterAsync(pt, MapConstant.SCALE);
        }
    }

    /**
     * 添加图层点，并保证图层单例（是否自动移动到中心）
     *
     * @param mapView  地图
     * @param overlay  添加的图层
     * @param resId    resId 资源ID
     * @param pt       Point
     * @param map      属性
     * @param isCenter isCenter
     */
    public static void addPointLayer(MapView mapView, GraphicsOverlay overlay, Context context, int resId,
                                     Point pt, Map<String, Object> map, boolean isCenter) {
        PictureMarkerSymbol markerSymbol = SymobelUtils.pictureSymobelByResId(context, resId);
        markerSymbol.setOffsetY(13);
        addSingelLayer(mapView, overlay).getGraphics().clear();
        addSingelLayer(mapView, overlay).getGraphics().add(new Graphic(pt, map, markerSymbol));
        if (isCenter) {
            mapView.setViewpointCenterAsync(pt, MapConstant.SCALE);
        }
    }

    /**
     * 添加图层点集合，并保证图层单例（是否清除）
     *
     * @param mapView 地图
     * @param overlay 添加的图层
     * @param resId   resId 资源ID
     * @param pts     Point 集合
     * @param map     属性
     * @param isClear 是否消除之前图层
     */
    public static void addPointLayer(MapView mapView, GraphicsOverlay overlay, Context context, int resId,
                                     Point[] pts, List<Map<String, Object>> map, boolean isClear) {
        PictureMarkerSymbol markerSymbol = SymobelUtils.pictureSymobelByResId(context, resId);
        markerSymbol.setOffsetY(13);
        if (isClear) {
            addSingelLayer(mapView, overlay).getGraphics().clear();
        }
        for (int i = 0; i < pts.length; i++) {
            Point point = pts[i];
            addSingelLayer(mapView, overlay).getGraphics().add(new Graphic(point, map.get(i), markerSymbol));
        }
    }

    /**
     * 添加图层线，并保证图层单例（是否清除）
     *
     * @param mapView  地图
     * @param overlay  添加的图层
     * @param colorStr 颜色字符串
     * @param pts      Point 集合
     * @param map      属性
     * @param isClear  是否消除之前图层
     */
    public static void addLineLayer(MapView mapView, GraphicsOverlay overlay, String colorStr,
                                    PointCollection pts, Map<String, Object> map, boolean isClear) {
        SimpleLineSymbol polylineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID,
                Color.parseColor(colorStr), ConvertUtils.dp2px(1));
        addLineLayer(mapView, overlay, polylineSymbol, pts, map, isClear);
    }

    /**
     * 添加图层线(多线链接)，并保证图层单例（是否清除）
     *
     * @param mapView  地图
     * @param overlay  添加的图层
     * @param colorStr 颜色字符串
     * @param arrPt    Point 数组集合
     * @param map      属性
     * @param isClear  是否消除之前图层
     */
    public static void addConnectLineLayer(MapView mapView, GraphicsOverlay overlay, String colorStr,
                                           Point[] arrPt, Map<String, Object> map, boolean isClear) {
        for (int i = 0; i < arrPt.length - 1; i++) {
            Point[] pointsLine = new Point[2];
            for (int k = 1; k < arrPt.length; k++) {
                pointsLine[0] = arrPt[i];
                pointsLine[1] = arrPt[k];
            }
            addLineLayer(mapView, overlay, colorStr, pointsLine, map, isClear);
        }
    }

    /**
     * 添加图层线，并保证图层单例（是否清除）
     *
     * @param mapView  地图
     * @param overlay  添加的图层
     * @param colorStr 颜色字符串
     * @param arrPt    Point 数组集合
     * @param map      属性
     * @param isClear  是否消除之前图层
     */
    public static void addLineLayer(MapView mapView, GraphicsOverlay overlay, String colorStr,
                                    Point[] arrPt, Map<String, Object> map, boolean isClear) {
        PointCollection pts = new PointCollection(mapView.getSpatialReference());
        // pts.addAll(Arrays.asList(arrPt));
        for (Point point : arrPt) {
            if (ObjectUtils.isNotEmpty(point)) {
                pts.add(point);
            }
        }
        addLineLayer(mapView, overlay, colorStr, pts, map, isClear);
    }

    /**
     * 添加图层线，并保证图层单例（是否清除）
     *
     * @param mapView      地图
     * @param overlay      添加的图层
     * @param markerSymbol SimpleFillSymbol
     * @param pts          Point 集合
     * @param map          属性
     * @param isClear      是否消除之前图层
     */
    public static void addLineLayer(MapView mapView, GraphicsOverlay overlay, SimpleLineSymbol markerSymbol,
                                    PointCollection pts, Map<String, Object> map, boolean isClear) {
        if (isClear) {
            addSingelLayer(mapView, overlay).getGraphics().clear();
        }
        Polyline polyline = new Polyline(pts);
        addSingelLayer(mapView, overlay).getGraphics().add(new Graphic(polyline, map, markerSymbol));
    }

    /**
     * 添加图层线集合，并保证图层单例（是否清除）
     *
     * @param mapView      地图
     * @param overlay      添加的图层
     * @param markerSymbol SimpleFillSymbol
     * @param pts          线 集合
     * @param mapList      属性集合
     * @param isClear      是否消除之前图层
     */
    public static void addLineLayer(MapView mapView, GraphicsOverlay overlay, SimpleLineSymbol markerSymbol,
                                    List<PointCollection> pts, List<Map<String, Object>> mapList, boolean isClear) {
        if (isClear) {
            addSingelLayer(mapView, overlay).getGraphics().clear();
        }
        for (int i = 0; i < pts.size(); i++) {
            PointCollection pc = pts.get(i);
            addLineLayer(mapView, overlay, markerSymbol, pc, mapList.get(i), isClear);
        }
    }

    /**
     * 添加图层线集合，并保证图层单例（是否清除）
     *
     * @param mapView  地图
     * @param overlay  添加的图层
     * @param colorStr colorStr
     * @param pts      线 集合
     * @param mapList  属性集合
     * @param isClear  是否消除之前图层
     */
    public static void addLineLayer(MapView mapView, GraphicsOverlay overlay, String colorStr,
                                    List<PointCollection> pts, List<Map<String, Object>> mapList, boolean isClear) {
        if (isClear) {
            addSingelLayer(mapView, overlay).getGraphics().clear();
        }
        for (int i = 0; i < pts.size(); i++) {
            PointCollection pc = pts.get(i);
            addLineLayer(mapView, overlay, colorStr, pc, mapList.get(i), isClear);
        }
    }

    public static Map<String, Object> json2MapInMap(BasePolygonsBean bean) {
        if (bean == null) {
            return null;
        }
        Map<String, Object> m = new HashMap<>(1);
        m.put("id", bean.getId());
        return m;
    }

    /**
     * 地图图层数据封装
     *
     * @param bean bean
     * @return Map<String   ,       Object>
     */
    public static Map<String, Object> jsonStr2MapInMap(BasePolygonsBean bean) {
        if (bean == null) {
            return null;
        }
        Map<String, Object> m = new HashMap<>(1);
        m.put("json", GsonUtils.gsonBuilder.create().toJson(bean));
        return m;
    }

    /**
     * 地图图层数据封装
     *
     * @param bean bean
     * @param key  用于区分图层
     * @return Map<String   ,       Object>
     */
    public static Map<String, Object> jsonStr2MapInMap(BasePolygonsBean bean, String key) {
        if (bean == null) {
            return null;
        }
        Map<String, Object> m = new HashMap<>(1);
        m.put(key, GsonUtils.gsonBuilder.create().toJson(bean));
        return m;
    }
}
