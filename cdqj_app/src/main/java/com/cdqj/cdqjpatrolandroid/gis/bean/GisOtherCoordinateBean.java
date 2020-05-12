package com.cdqj.cdqjpatrolandroid.gis.bean;

import java.util.List;

/**
 * Created by lyf on 2018/9/17 10:12
 *
 * @author lyf
 * desc：geo对象返回面或者线
 */
public class GisOtherCoordinateBean {

    /**
     * paths : [[[104.03029407400004,30.650296695000065],[104.03009574000004,30.65030148900007]]]
     * spatialReference : {"wkid":4490}
     */

    private SpatialReferenceBean spatialReference;
    private List<List<List<Double>>> paths;

    public SpatialReferenceBean getSpatialReference() {
        return spatialReference;
    }

    public void setSpatialReference(SpatialReferenceBean spatialReference) {
        this.spatialReference = spatialReference;
    }

    public List<List<List<Double>>> getPaths() {
        return paths;
    }

    public void setPaths(List<List<List<Double>>> paths) {
        this.paths = paths;
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
