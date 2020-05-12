package com.cdqj.cdqjpatrolandroid.gis.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lyf on 2018/9/19 16:57
 *
 * @author lyf
 * desc：地图图层绘制（面）基类
 */
public class BasePolygonsBean implements Parcelable {

    public String address;
    public String gisArea;
    public String id;
    public String name;

    public static final Creator<BasePolygonsBean> CREATOR = new Creator<BasePolygonsBean>() {
        @Override
        public BasePolygonsBean createFromParcel(Parcel in) {
            return new BasePolygonsBean(in);
        }

        @Override
        public BasePolygonsBean[] newArray(int size) {
            return new BasePolygonsBean[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGisArea() {
        return gisArea;
    }

    public void setGisArea(String gisArea) {
        this.gisArea = gisArea;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BasePolygonsBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeString(this.gisArea);
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    protected BasePolygonsBean(Parcel in) {
        this.address = in.readString();
        this.gisArea = in.readString();
        this.id = in.readString();
        this.name = in.readString();
    }

}
