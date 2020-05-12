package com.cdqj.cdqjpatrolandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.cdqj.cdqjpatrolandroid.gis.bean.BasePolygonsBean;

/**
 * Created by lyf on 2018/10/29 19:44
 *
 * @author lyf
 * desc：
 */
public class DeviceBean extends BasePolygonsBean implements Parcelable {
    /**
     * 所属网格
     */
    private Long gridId;
    /**
     * 设备类型
     */
    private String deviceType;
    /**
     * 设备类型
     */
    private String deviceTypeExp;
    /**
     * 经度
     */
    private Double lon;
    /**
     * 纬度
     */
    private Double lat;
    /**
     * 备注描述
     */
    private String remarks;
    /**
     * 地图OBJECTID
     */
    private String objectid;
    /**
     * 地图PPID
     */
    private String ppid;
    /**
     * 添加来源（GIS 1 /平台 2）
     */
    private Integer sourceType;
    /**
     * 设备GIS类型（点/线）
     */
    private Integer gisType;
    /**
     * 工程名称
     */
    private String projectName;
    /**
     * 扩展属性
     */
    private String deviceExp;
    /**
     * 添加人
     */
    private Long addUserId;
    /**
     * 添加时间
     */
    private String addTime;
    /**
     * 修改人
     */
    private Long updateUserId;
    /**
     * 修改时间
     */
    private String updateTime;
    /**
     * 域ID
     */
    private Long domainId;
    /**
     * 版本号
     */
    private String version;
    /**
     * 对象实例(存放JSON字符串)
     */
    private String objects;
    /**
     * 计量单位
     */
    private String unit;
    /**
     * 计量个数(米)
     */
    private Double count;
    /**
     * 删除标识
     */
    private Integer isDelete;

    public String getDeviceTypeExp() {
        return deviceTypeExp;
    }

    public void setDeviceTypeExp(String deviceTypeExp) {
        this.deviceTypeExp = deviceTypeExp;
    }

    public Long getGridId() {
        return gridId;
    }

    public void setGridId(Long gridId) {
        this.gridId = gridId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getObjectid() {
        return objectid;
    }

    public void setObjectid(String objectid) {
        this.objectid = objectid;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getGisType() {
        return gisType;
    }

    public void setGisType(Integer gisType) {
        this.gisType = gisType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDeviceExp() {
        return deviceExp;
    }

    public void setDeviceExp(String deviceExp) {
        this.deviceExp = deviceExp;
    }

    public Long getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Long addUserId) {
        this.addUserId = addUserId;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Long getDomainId() {
        return domainId;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getObjects() {
        return objects;
    }

    public void setObjects(String objects) {
        this.objects = objects;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public DeviceBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeValue(this.gridId);
        dest.writeString(this.deviceType);
        dest.writeString(this.deviceTypeExp);
        dest.writeValue(this.lon);
        dest.writeValue(this.lat);
        dest.writeString(this.remarks);
        dest.writeString(this.objectid);
        dest.writeString(this.ppid);
        dest.writeValue(this.sourceType);
        dest.writeValue(this.gisType);
        dest.writeString(this.projectName);
        dest.writeString(this.deviceExp);
        dest.writeValue(this.addUserId);
        dest.writeString(this.addTime);
        dest.writeValue(this.updateUserId);
        dest.writeString(this.updateTime);
        dest.writeValue(this.domainId);
        dest.writeString(this.version);
        dest.writeString(this.objects);
        dest.writeString(this.unit);
        dest.writeValue(this.count);
        dest.writeValue(this.isDelete);
        dest.writeString(this.address);
        dest.writeString(this.gisArea);
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    protected DeviceBean(Parcel in) {
        super(in);
        this.gridId = (Long) in.readValue(Long.class.getClassLoader());
        this.deviceType = in.readString();
        this.deviceTypeExp = in.readString();
        this.lon = (Double) in.readValue(Double.class.getClassLoader());
        this.lat = (Double) in.readValue(Double.class.getClassLoader());
        this.remarks = in.readString();
        this.objectid = in.readString();
        this.ppid = in.readString();
        this.sourceType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.gisType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.projectName = in.readString();
        this.deviceExp = in.readString();
        this.addUserId = (Long) in.readValue(Long.class.getClassLoader());
        this.addTime = in.readString();
        this.updateUserId = (Long) in.readValue(Long.class.getClassLoader());
        this.updateTime = in.readString();
        this.domainId = (Long) in.readValue(Long.class.getClassLoader());
        this.version = in.readString();
        this.objects = in.readString();
        this.unit = in.readString();
        this.count = (Double) in.readValue(Double.class.getClassLoader());
        this.isDelete = (Integer) in.readValue(Integer.class.getClassLoader());
        this.address = in.readString();
        this.gisArea = in.readString();
        this.id = in.readString();
        this.name = in.readString();
    }

    public static final Creator<DeviceBean> CREATOR = new Creator<DeviceBean>() {
        @Override
        public DeviceBean createFromParcel(Parcel source) {
            return new DeviceBean(source);
        }

        @Override
        public DeviceBean[] newArray(int size) {
            return new DeviceBean[size];
        }
    };
}
