package com.cdqj.cdqjpatrolandroid.gis.bean;

/**
 * Created by lyf on 2018/9/17 10:12
 *
 * @author lyf
 * desc：geo对象返回点
 */
public class GisPointCoordinateBean {

    /**
     * x : 104.02913971800007
     * y : 30.649703686000066
     * spatialReference : {"wkid":4490}
     */

    private double x;
    private double y;
    private SpatialReferenceBean spatialReference;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public SpatialReferenceBean getSpatialReference() {
        return spatialReference;
    }

    public void setSpatialReference(SpatialReferenceBean spatialReference) {
        this.spatialReference = spatialReference;
    }

    public static class SpatialReferenceBean {
        /**
         * wkid : 4490
         */

        private int wkid;

        public int getWkid() {
            return wkid;
        }

        public void setWkid(int wkid) {
            this.wkid = wkid;
        }
    }
}
