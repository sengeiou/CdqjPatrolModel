package com.cdqj.cdqjpatrolandroid.gis.tianditu;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.esri.arcgisruntime.arcgisservices.LevelOfDetail;
import com.esri.arcgisruntime.arcgisservices.TileInfo;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.layers.Layer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by v5pang on 2020/3/9.
 */

public class MBTilesLayer{
    private MBTilesBaseLayer _mbtLayer=null;
    private SQLiteDatabase mapDb;
    private Metadata _metadata=null;
    private static SpatialReference SPR= SpatialReference.create(4490);
    private static final Point TILE_ORIGIN = new Point(-180, 90, SPR);    //切片的原点，左上角坐标

    public MBTilesLayer(String path) {
        try {
            mapDb = SQLiteDatabase.openDatabase(path, null,
                    SQLiteDatabase.OPEN_READONLY);
            _metadata=getMetaData();

        } catch (SQLException ex) {
            Log.e("MBTilesLayer2", ex.getMessage());
            throw (ex);
        }
        if(_metadata==null){
            System.out.println("初始化GIS参数失败!");
        }
        else
        {
            Envelope fullExtent=new Envelope(Double.parseDouble(_metadata.bounds[0]),
                Double.parseDouble(_metadata.bounds[1]),
                Double.parseDouble(_metadata.bounds[2]),
                Double.parseDouble(_metadata.bounds[3]), SPR);

            Envelope initExtent =new Envelope(Double.parseDouble(_metadata.initExtent[0]),
                Double.parseDouble(_metadata.initExtent[1]),
                Double.parseDouble(_metadata.initExtent[2]),
                Double.parseDouble(_metadata.initExtent[3]),SPR);
            TileInfo tileInfo=this.getTileInfo();
            _mbtLayer=new MBTilesBaseLayer(tileInfo,fullExtent, mapDb);
        }
    }

    protected Metadata getMetaData(){
        String sql="SELECT * FROM metadata";
        Cursor cur=mapDb.rawQuery(sql,null);
        Metadata data=new Metadata();
        if (cur.moveToFirst()) {
            boolean ok=setValue(cur,data);
            if(!ok) return null;
            while(cur.moveToNext()){
                ok=setValue(cur,data);
                if(!ok) return null;
            }
        }
        cur.close();
        return data;
    }

    protected boolean setValue(Cursor cur, Metadata data){
        String name=cur.getString(cur.getColumnIndex("name"));
        if(name.equalsIgnoreCase("bounds")){
            String bd=getValue(cur);
            if(bd!=null && bd.length()>0)
            {
                data.bounds= bd.split(",");
                return true;
            }
            else
                return false;
        }
        if(name.equalsIgnoreCase("max_level")){
            int maxl=getInt(cur);
            if(maxl>0)
            {
                data.max_level=maxl;
                return true;
            }
            else
                return false;
        }
        if(name.equalsIgnoreCase("min_level")){
            int minl=getInt(cur);
            if(minl>=0)
            {
                data.min_level= minl;
                return true;
            }
            else
                return false;
        }
        if(name.equalsIgnoreCase("initExtent")){
            String et=getValue(cur);
            if(et!=null && et.length()>0)
            {
                data.initExtent= et.split(",");
                return true;
            }
            else
                return false;
        }
        if(name.equalsIgnoreCase("max_scale")){
            double ds=getDouble(cur);
            if(ds>0)
            {
                data.max_scale=ds;
                return true;
            }
            else
                return false;
        }
        if(name.equalsIgnoreCase("max_res")){
            double dr=getDouble(cur);
            if(dr>0)
            {
                data.max_res= dr;
                return true;
            }
            else
                return false;
        }
        return true;
    }

    private String getValue(Cursor cur){
        return cur.getString(cur.getColumnIndex("value"));
    }

    private Double getDouble(Cursor cur){
        String s=getValue(cur);
        if(s!=null && s.length()>0)
            return Double.parseDouble(s);
        else
            return 0d;
    }

    private int getInt(Cursor cur){
        String s=getValue(cur);
        if(s!=null && s.length()>0)
            return Integer.parseInt(s);
        else
            return 0;
    }

    /**
     * 获取切片信息
     * @return
     */
    public TileInfo getTileInfo() {
        int dpi = 96;
        int tileWidth = 256;
        int tileHeight = 256;
        return new TileInfo(
                dpi,
                TileInfo.ImageFormat.PNG24,
                getLods(),TILE_ORIGIN ,
                TILE_ORIGIN.getSpatialReference(),
                tileWidth, tileHeight);
    }

    /**
     * 创建切片信息
     * @param startLevel
     * @param endLevel
     * @param scale
     * @param resolution
     * @return
     */
    private List<LevelOfDetail> createLods(int startLevel, int endLevel, double scale, double resolution){
        List<LevelOfDetail> list = new ArrayList<>();
        int count=0;
        for (int i=startLevel;i<=endLevel ;i++)
        {
            int mod=(int) Math.pow(2,count);
            LevelOfDetail lod=new LevelOfDetail(i, resolution/mod, scale/mod);
            list.add(lod);
            count++;
        }
        return list;
    }

    /**
     * 获取切片信息，天地图的切片方案
     * int startLevel, int endLevel, double scale, double resolution
     * 3,18,73957338.8625,0.17578125   默认从第3级返回
     * @return
     */
    private List<LevelOfDetail> getLods(){
        //return createLods(3,18,73957338.8625,0.17578125);//默认从第3级返回
        return createLods(_metadata.min_level,_metadata.max_level ,_metadata.max_scale,_metadata.max_res);//默认从第3级返回

    }

    public Layer getLayer(){
        return _mbtLayer;
    }
}
