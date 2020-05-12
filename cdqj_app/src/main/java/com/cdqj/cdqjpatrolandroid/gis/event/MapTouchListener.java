package com.cdqj.cdqjpatrolandroid.gis.event;

import android.content.Context;
import android.view.MotionEvent;

import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.gis.bean.GisParamsBean;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.FeatureQueryResult;
import com.esri.arcgisruntime.data.QueryParameters;
import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.layers.Layer;
import com.esri.arcgisruntime.mapping.GeoElement;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.IdentifyGraphicsOverlayResult;
import com.esri.arcgisruntime.mapping.view.IdentifyLayerResult;
import com.esri.arcgisruntime.mapping.view.MapView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 地图点击
 */
public class MapTouchListener extends DefaultMapViewOnTouchListener {

	private MapView mapView;
    private Context context;
	private MapTouchInterface mMapTouchInterface;
	private QueryParams queryParams;
	private List<ServiceFeatureTable> serviceFeatureTables;
	public boolean isOnlineLayer = true;
	/**
	 * 点击事件标识
	 * 0.单机地图不拦截
	 * 1.导航点击
	 * 2.上报点击
	 */
	public int flag = 0;
	/**
	 * 查询单个图层URL
	 */
	private String queryOneUrl;

    public MapTouchListener(Context context, MapView view, QueryParams queryParams, MapTouchInterface mapInterface) {
        super(context, view);

		this.mapView = view;
        this.context = context;
        this.queryParams = queryParams;
		this.mMapTouchInterface = mapInterface;
    }

	/**  地图单击事件 */
	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		android.graphics.Point screenPoint = new android.graphics.Point(Math.round(e.getX()),
				Math.round(e.getY()));
		Point pointClicked = mapView.screenToLocation(screenPoint);
		//回掉地图点击获取地图坐标
		mMapTouchInterface.getMapClickPtSuccess(pointClicked, flag);

		if(flag != 0){
			return false;
		}
		isOnlineLayer = true;
		if(queryParams==null) {
			return false;
		}

		//遍历需要查询的图层
		for(QueryLayerType type:queryParams.queryLayerTypeList){
			switch (type){
				case QueryAllGpraphicsOverlay:
					identifyAllGraphicsOverlayResult(screenPoint);
					break;
				case QueryAllLayer:
					identifyAllLayerResult(screenPoint);
					break;
				case QueryGpraphicsOverlay:
					identifyGraphicsOverlayResult(screenPoint);
					break;
				case QueryLayer:
					identifyLayerResult(screenPoint);
					break;
				case QueryOnline:
					queryOnLine(screenPoint);
					break;
				case QueryOnlineOne:
					queryOnLineOne(screenPoint);
					break;
				default:break;
			}
		}

		return super.onSingleTapConfirmed(e);
	}

    @Override
    public void onLongPress(MotionEvent e) {
		android.graphics.Point screenPoint = new android.graphics.Point(Math.round(e.getX()),
				Math.round(e.getY()));
    	Point pointClicked = mapView.screenToLocation(screenPoint);
        mMapTouchInterface.getMapLongPressPtSuccess(pointClicked);
    }

	/*********** 图层查询方法 *************/
	private void identifyLayerResult(android.graphics.Point screenPoint) {

		if(queryParams.mLayer==null) {
			return;
		}
		Layer layer = queryParams.mLayer;

		final ListenableFuture<IdentifyLayerResult> identifyLayerResultsFuture = mapView
				.identifyLayerAsync(layer,screenPoint, 4, false, queryParams.returnMax);

		identifyLayerResultsFuture.addDoneListener(() -> {
            try {
                IdentifyLayerResult identifyLayerResults = identifyLayerResultsFuture.get();
                if(identifyLayerResults==null) {
                    return;
                }
                List<IdentifyLayerResult> results = identifyLayerResults.getSublayerResults();
                if(results==null || results.size()==0) {
                    return;
                }
                List<GeoElement> geoElements = results.get(0).getElements();
                if(geoElements==null || geoElements.size()==0) {
                    return;
                }
                mMapTouchInterface.getLayerQuerySuccess(geoElements);
            }
            catch (InterruptedException | ExecutionException e) {
                mMapTouchInterface.getLayerQueryFailed(e);
            }
        });
	}

	/** 查询所有的可见图层 */
	private void identifyAllLayerResult(android.graphics.Point screenPoint) {

		final ListenableFuture<List<IdentifyLayerResult>> listenableFuture = mapView
				.identifyLayersAsync(screenPoint, 4, false, queryParams.returnMax);

		listenableFuture.addDoneListener(() -> {
            try {
                List<IdentifyLayerResult> layerResultList = listenableFuture.get();
                mMapTouchInterface.getAllLayerQuerySuccess(layerResultList);
            } catch (InterruptedException | ExecutionException e) {
                mMapTouchInterface.getLayerQueryFailed(e);
            }
        });
	}

	/** 查询某个graphic图层 */
	private void identifyGraphicsOverlayResult(android.graphics.Point screenPoint) {

		if(queryParams.mGraphicsoverlay==null) {
			return;
		}
		GraphicsOverlay graphicsOverlay = queryParams.mGraphicsoverlay;

		final ListenableFuture<IdentifyGraphicsOverlayResult> graphicsOverlayResults = mapView
				.identifyGraphicsOverlayAsync(graphicsOverlay,screenPoint, 4, false, queryParams.returnMax);

		graphicsOverlayResults.addDoneListener(() -> {
            try {
                IdentifyGraphicsOverlayResult identifyGraphicsOverlayResult = graphicsOverlayResults.get();
                if(identifyGraphicsOverlayResult==null) {
                    return;
                }
                List<Graphic> results = identifyGraphicsOverlayResult.getGraphics();
                if(results==null || results.size()==0) {
                    return;
                }
                isOnlineLayer = false;
                mMapTouchInterface.getGraphicsOverlaySuccess(results);
            } catch (InterruptedException | ExecutionException e) {
                mMapTouchInterface.getGraphicsQueryFailed(e);
            }
        });
	}

	/** 查询所有的可见graphic图层 */
	private void identifyAllGraphicsOverlayResult(android.graphics.Point screenPoint) {

		final ListenableFuture<List<IdentifyGraphicsOverlayResult>> identifyGraphicsOverlayResults = mapView
				.identifyGraphicsOverlaysAsync(screenPoint, 4, false, queryParams.returnMax);

		identifyGraphicsOverlayResults.addDoneListener(() -> {
            try {
                List<IdentifyGraphicsOverlayResult> graphicsOverlayResults = identifyGraphicsOverlayResults.get();
                mMapTouchInterface.getAllGraphicsOverlaySuccess(graphicsOverlayResults);
            } catch (InterruptedException | ExecutionException e) {
                mMapTouchInterface.getGraphicsQueryFailed(e);
            }
        });
	}

	/** 查询所有的可见graphic图层 */
	private void queryOnLine(android.graphics.Point screenPoint) {
		if(!isOnlineLayer){
			return;
		}

		if(queryParams==null || queryParams.mGisparams==null) {
			return;
		}
		final String index = queryParams.mGisparams.appQueryLyrIndexs;
		final String url = queryParams.mGisparams.appPipesQueryUrl;
		if(StringUtils.isEmpty(index) || StringUtils.isEmpty(url)) {
			return;
		}

		Point pointClicked = mapView.screenToLocation(screenPoint);

		int tolerance = 5;
		double mapTolerance = tolerance * mMapView.getUnitsPerDensityIndependentPixel();
		Envelope envelope = new Envelope(pointClicked.getX() - mapTolerance, pointClicked.getY() - mapTolerance,
				pointClicked.getX() + mapTolerance, pointClicked.getY() + mapTolerance, mapView.getSpatialReference());
		QueryParameters query = new QueryParameters();
		query.setGeometry(envelope);

		List<ServiceFeatureTable> list = singleListFeatureTable();
		assert list != null;
		for (ServiceFeatureTable item:list){
			final ListenableFuture<FeatureQueryResult> resultListenableFuture =
					item.queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
			resultListenableFuture.addDoneListener(() -> {
                try {
                    FeatureQueryResult results = resultListenableFuture.get();

                    if (results.iterator().hasNext()){
                        if(isOnlineLayer){
                            mMapTouchInterface.getOnlineLayerSuccess(results);
                        }
                        isOnlineLayer = false;
                    }
                }  catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
		}
	}

	/** 查询某一个图层的内容 */
	private void queryOnLineOne(android.graphics.Point screenPoint) {
		if(StringUtils.isEmpty(queryOneUrl)) {
			return;
		}

		final Point pointClicked = mapView.screenToLocation(screenPoint);

		int tolerance = 10;
		double mapTolerance = tolerance * mMapView.getUnitsPerDensityIndependentPixel();
		Envelope envelope = new Envelope(pointClicked.getX() - mapTolerance, pointClicked.getY() - mapTolerance,
				pointClicked.getX() + mapTolerance, pointClicked.getY() + mapTolerance, mapView.getSpatialReference());
		QueryParameters query = new QueryParameters();
		query.setGeometry(envelope);

		ServiceFeatureTable serviceFeatureTable = new ServiceFeatureTable(queryOneUrl);
		final ListenableFuture<FeatureQueryResult> resultListenableFuture =
				serviceFeatureTable.queryFeaturesAsync(query, ServiceFeatureTable.QueryFeatureFields.LOAD_ALL);
		resultListenableFuture.addDoneListener(() -> {
            try {
                FeatureQueryResult results = resultListenableFuture.get();

                if (results.iterator().hasNext()){
                    if(isOnlineLayer){
                        mMapTouchInterface.getOnlineLayerSuccess(results,pointClicked);
                    }
                    isOnlineLayer = false;
                }
            }  catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });
	}

	private List<ServiceFeatureTable> singleListFeatureTable(){
		if(queryParams==null || queryParams.mGisparams==null) {
			return null;
		}
		final String index = queryParams.mGisparams.appQueryLyrIndexs;
		final String url = queryParams.mGisparams.appPipesQueryUrl;
		if(StringUtils.isEmpty(index) || StringUtils.isEmpty(url)) {
			return null;
		}

		if(serviceFeatureTables!=null) {
			return serviceFeatureTables;
		}
		serviceFeatureTables = new ArrayList<>();

		String[] indexes = index.split("\\|");
		for(String item:indexes){
			ServiceFeatureTable serviceFeatureTable = new ServiceFeatureTable(url+"/"+item);
			serviceFeatureTables.add(serviceFeatureTable);
		}
		return  serviceFeatureTables;
	}

	public void addGraphicsOverlayToParams(QueryLayerType type,GraphicsOverlay overlay){
		if(!this.queryParams.queryLayerTypeList.contains(type)){
			this.queryParams.queryLayerTypeList.add(type);
		}
		this.queryParams.mGraphicsoverlay = overlay;
	}

	public void addLayerToParams(QueryLayerType type,Layer layer){
		if(!this.queryParams.queryLayerTypeList.contains(type)){
			this.queryParams.queryLayerTypeList.add(type);
		}
		this.queryParams.mLayer = layer;
	}

    /**
     * 添加gis参数，主要用于管线查询，如果不用查询管线则不必添加
     * @param gisParams gisParams
     */
    public void addGisParamsToParams(QueryLayerType type,GisParamsBean gisParams){
        if(queryParams.mGisparams==null){
			queryParams.mGisparams = gisParams;
			singleListFeatureTable();
		}
		if(!this.queryParams.queryLayerTypeList.contains(type)){
			this.queryParams.queryLayerTypeList.add(type);
		}
    }

    public void setQueryOneUrl(String url){
    	queryOneUrl = url;
	}

    private String formatStr(int size){
		if(size==0) {
			return "";
		}
		return String.format("%1$0"+size+"d",0);
	}
}
