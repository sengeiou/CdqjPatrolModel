package com.cdqj.cdqjpatrolandroid.event;

/**
 * 版权：成都千嘉科技公司所有
 *
 * @author lyf
 * 创建日期： 2020/4/26
 * 创建时间： 14:31
 * 描述：通知  gps对象
 */
public class EventGpsBean {
    /**
     * 标识
     * 1 首页底部导航栏显示隐藏
     * 2 返回按钮控制
     * 3 GPS信号
     * 4 地图底部导航栏显示隐藏
     */
    private int tag;
    /**
     * GPS信号
     */
    private String gpsAccuracy;

    /**
     * 首页 底部导航位置
     */
    private int position;

    public EventGpsBean(int tag) {
        this.tag = tag;
    }

    public EventGpsBean(int tag, String gpsAccuracy) {
        this.tag = tag;
        this.gpsAccuracy = gpsAccuracy;
    }

    public EventGpsBean(int tag, int position) {
        this.tag = tag;
        this.position = position;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getGpsAccuracy() {
        return gpsAccuracy;
    }

    public void setGpsAccuracy(String gpsAccuracy) {
        this.gpsAccuracy = gpsAccuracy;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
