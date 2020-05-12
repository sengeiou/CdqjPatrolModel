package com.cdqj.cdqjpatrolandroid.gis.bean;

import com.esri.arcgisruntime.arcgisservices.TileInfo;

import java.util.Arrays;

/**
 * 版权：成都千嘉科技公司所有
 *
 * @author lyf
 * 创建日期： 2020/3/14
 * 创建时间： 13:54
 * 描述：
 */
public class MetaData {
    public String[] bounds;
    public int max_level;
    public int min_level;
    public String[] initExtent;
    public TileInfo.ImageFormat format;
    //比例尺
    public double max_res;
    //分辨率
    public double max_scale;

    @Override
    public String toString() {
        return "MetaData{" +
                "bounds=" + Arrays.toString(bounds) +
                ", max_level=" + max_level +
                ", min_level=" + min_level +
                ", initExtent=" + Arrays.toString(initExtent) +
                ", format=" + format +
                ", max_res=" + max_res +
                ", max_scale=" + max_scale +
                '}';
    }
}
