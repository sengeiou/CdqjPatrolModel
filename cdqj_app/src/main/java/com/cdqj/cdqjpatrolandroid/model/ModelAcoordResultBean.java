package com.cdqj.cdqjpatrolandroid.model;

import android.os.Parcel;
import android.os.Parcelable;


import com.cdqj.cdqjpatrolandroid.gis.common.GpsConvertToDoType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gis_yj on 2017/12/21.
 */

public class ModelAcoordResultBean implements Parcelable {

    /**
     * data : [{"y":"-176425.560470519","x":"2326.37040564676"}]
     * err : 0
     */

    private int err;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "ModelAcoordResultBean{" +
                "err=" + err +
                ", data=" + data +
                '}';
    }

    protected ModelAcoordResultBean(Parcel in) {
        err = in.readInt();
        data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public ModelAcoordResultBean(){
        data = new ArrayList<>();
    }

    public static final Creator<ModelAcoordResultBean> CREATOR = new Creator<ModelAcoordResultBean>() {
        @Override
        public ModelAcoordResultBean createFromParcel(Parcel in) {
            return new ModelAcoordResultBean(in);
        }

        @Override
        public ModelAcoordResultBean[] newArray(int size) {
            return new ModelAcoordResultBean[size];
        }
    };

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.err);
        dest.writeTypedList(this.data);
    }

    public static class DataBean implements Parcelable{
        /**
         * y : -176425.560470519
         * x : 2326.37040564676
         */

        private String y;
        private String x;
        private GpsConvertToDoType type;
        private String beer;

        @Override
        public String toString() {
            return "DataBean{" +
                    "y='" + y + '\'' +
                    ", x='" + x + '\'' +
                    ", type=" + type +
                    ", beer='" + beer + '\'' +
                    '}';
        }

        protected DataBean(Parcel in) {
            y = in.readString();
            x = in.readString();
        }

        public DataBean(){
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public String getBeer() {
            return beer;
        }

        public void setBeer(String beer) {
            this.beer = beer;
        }

        public GpsConvertToDoType getType() {
            return type;
        }

        public void setType(GpsConvertToDoType type) {
            this.type = type;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.x);
            dest.writeString(this.y);
        }
    }
}
