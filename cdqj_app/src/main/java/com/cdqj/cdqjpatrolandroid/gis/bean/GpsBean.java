package com.cdqj.cdqjpatrolandroid.gis.bean;

/**
 * Created by lyf on 2018/9/14 15:48
 *
 * @author lyf
 * desc：坐标对象
 */
public class GpsBean {

    private String x;
    private String y;

    @Override
    public String toString() {
        return "GpsBean{" +
                "x='" + x + '\'' +
                ", y='" + y + '\'' +
                '}';
    }

    public GpsBean() {
    }

    public GpsBean(String x, String y) {
        this.x = x;
        this.y = y;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
}
