package com.cdqj.cdqjpatrolandroid.gis.event;

import com.esri.arcgisruntime.layers.Layer;
import com.esri.arcgisruntime.mapping.view.LayerViewStateChangedEvent;
import com.esri.arcgisruntime.mapping.view.LayerViewStateChangedListener;
import com.esri.arcgisruntime.mapping.view.MapView;

public class MapStatusListener implements LayerViewStateChangedListener {

    private MapStatusInterface mInterface;

	public MapStatusListener(MapView map, MapStatusInterface mInterface){
        this.mInterface = mInterface;
	}

	@Override
	public void layerViewStateChanged(LayerViewStateChangedEvent layerViewStateChangedEvent) {
        Layer layer = layerViewStateChangedEvent.getLayer();
        String viewStatus = layerViewStateChangedEvent.getLayerViewStatus().iterator().next().toString();

        mInterface.getLayerStatusSuccess(viewStatus);

        switch(viewStatus) {
            case "ACTIVE":
                mInterface.getLayerActiveSuccess();
                 break;
            case "ERROR":
                mInterface.getLayerErrorSuccess(layer.getName());
                break;
            case "LOADING":
                break;

            case "NOT_VISIBLE":
                break;

            case "OUT_OF_SCALE":
                break;
            default:break;
        }
	}
}
