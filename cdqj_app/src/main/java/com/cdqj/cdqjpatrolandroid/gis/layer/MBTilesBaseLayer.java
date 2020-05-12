package com.cdqj.cdqjpatrolandroid.gis.layer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blankj.utilcode.util.LogUtils;
import com.esri.arcgisruntime.arcgisservices.TileInfo;
import com.esri.arcgisruntime.data.TileKey;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.layers.ImageTiledLayer;

/**
 * Created by v5pang on 2020/3/9.
 */

public class MBTilesBaseLayer extends ImageTiledLayer {

    private SQLiteDatabase mapDb;
    //Metadata _metadata=null;

    public MBTilesBaseLayer(TileInfo tileInfo, Envelope fullExtent, SQLiteDatabase mapDb) {
        super(tileInfo, fullExtent);
        this.mapDb = mapDb;
    }


    @Override
    protected byte[] getTile(TileKey tileKey) {
        LogUtils.d("MBTilesBaseLayer---getTile()方法执行");
        Cursor imageCur = null;
        try {
            int nLevel = tileKey.getLevel();//+_metadata.min_level;
            String exp = "SELECT tile_data FROM Level" + nLevel + " WHERE tile_column = "
                    + Integer.toString(tileKey.getColumn()) + " AND tile_row = "
                    + Integer.toString(tileKey.getRow());
            LogUtils.d("MBTilesBaseLayer---exp----->>>>>>" + exp);
            imageCur = mapDb.rawQuery(exp, null);
            if (imageCur.moveToFirst()) {
                byte[] bts = imageCur.getBlob(0);
                imageCur.close();
                return bts;
            }
            imageCur.close();
        } catch (Exception ex) {
            LogUtils.d("MBTilesBaseLayer---Exception----->>>>>>" + ex.getMessage());
            if (imageCur != null && !imageCur.isClosed()) imageCur.close();
        }
        return null;
    }
}
