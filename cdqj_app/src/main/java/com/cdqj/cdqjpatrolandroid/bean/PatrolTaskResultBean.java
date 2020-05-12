package com.cdqj.cdqjpatrolandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.cdqj.cdqjpatrolandroid.gis.bean.BasePolygonsBean;

/**
 * Created by lyf on 2018/10/11 11:42
 *
 * @author lyf
 * desc：计划任务基础及运维数据对象
 */
public class PatrolTaskResultBean extends BasePolygonsBean implements Parcelable {
    /**
     * 任务id
     */
    private Long taskId;
    /**
     * 计划id
     */
    private Long planId;
    /**
     * 经度
     */
    private Double lon;
    /**
     * 纬度
     */
    private Double lat;
    /**
     * 巡检名称
     */
    private String checkName;
    /**
     * 地图对象ID
     */
    private String objectId;
    /**
     * 是否地图对象
     */
    private int isMap;
    /**
     * 对象实例
     */
    private String objcets;
    /**
     * 对象类型（点/线/面）
     */
    private int objectType;
    /**
     * 巡检点类型(1设备/2任务点)
     */
    private int checkPointSource;
    /**
     * 巡检点类型
     */
    private String checkType;
    /**
     * 经过时间
     */
    private String passTime;
    /**
     * 经过人ID
     */
    private Long passUserid;
    /**
     * 最后上报时间
     */
    private String lastReportTime;
    /**
     * 最后上报人
     */
    private Long lastReportUserid;
    /**
     * 经过人对比坐标经度
     */
    private Double passLon;
    /**
     * 经过人对比坐标纬度
     */
    private Double passLat;
    /**
     * 添加时间
     */
    private String addTime;
    /**
     * 添加人
     */
    private Long addUserId;
    /**
     * 添加人
     */
    private String addUserExp;
    /**
     * 修改人
     */
    private Long updateUserId;
    /**
     * 修改人
     */
    private String updateUserExp;
    /**
     * 修改时间
     */
    private String updateTime;
    /**
     * 域ID
     */
    private Long domainId;
    /**
     * 版本
     */
    private String version;
    /**
     * 任务分组
     */
    private Long taskGroup;
    /**
     * 删除标识
     */
    private int isDelete;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 经过次数
     */
    private int passTimes;
    /**
     * 经过状态 1、完成 0、未完成
     */
    private int passStatus;
    /**
     * 经过状态 1、完成 0、未完成
     */
    private String passStatusExp;

    public String getPassStatusExp() {
        return passStatusExp;
    }

    public void setPassStatusExp(String passStatusExp) {
        this.passStatusExp = passStatusExp;
    }

    public String getAddUserExp() {
        return addUserExp;
    }

    public void setAddUserExp(String addUserExp) {
        this.addUserExp = addUserExp;
    }

    public String getUpdateUserExp() {
        return updateUserExp;
    }

    public void setUpdateUserExp(String updateUserExp) {
        this.updateUserExp = updateUserExp;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
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

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getIsMap() {
        return isMap;
    }

    public void setIsMap(int isMap) {
        this.isMap = isMap;
    }

    public String getObjcets() {
        return objcets;
    }

    public void setObjcets(String objcets) {
        this.objcets = objcets;
    }

    public int getObjectType() {
        return objectType;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }

    public int getCheckPointSource() {
        return checkPointSource;
    }

    public void setCheckPointSource(int checkPointSource) {
        this.checkPointSource = checkPointSource;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getPassTime() {
        return passTime;
    }

    public void setPassTime(String passTime) {
        this.passTime = passTime;
    }

    public Long getPassUserid() {
        return passUserid;
    }

    public void setPassUserid(Long passUserid) {
        this.passUserid = passUserid;
    }

    public String getLastReportTime() {
        return lastReportTime;
    }

    public void setLastReportTime(String lastReportTime) {
        this.lastReportTime = lastReportTime;
    }

    public Long getLastReportUserid() {
        return lastReportUserid;
    }

    public void setLastReportUserid(Long lastReportUserid) {
        this.lastReportUserid = lastReportUserid;
    }

    public Double getPassLon() {
        return passLon;
    }

    public void setPassLon(Double passLon) {
        this.passLon = passLon;
    }

    public Double getPassLat() {
        return passLat;
    }

    public void setPassLat(Double passLat) {
        this.passLat = passLat;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public Long getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Long addUserId) {
        this.addUserId = addUserId;
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

    public Long getTaskGroup() {
        return taskGroup;
    }

    public void setTaskGroup(Long taskGroup) {
        this.taskGroup = taskGroup;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getPassTimes() {
        return passTimes;
    }

    public void setPassTimes(int passTimes) {
        this.passTimes = passTimes;
    }

    public int getPassStatus() {
        return passStatus;
    }

    public void setPassStatus(int passStatus) {
        this.passStatus = passStatus;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public PatrolTaskResultBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeValue(this.taskId);
        dest.writeValue(this.planId);
        dest.writeValue(this.lon);
        dest.writeValue(this.lat);
        dest.writeString(this.checkName);
        dest.writeString(this.objectId);
        dest.writeInt(this.isMap);
        dest.writeString(this.objcets);
        dest.writeInt(this.objectType);
        dest.writeInt(this.checkPointSource);
        dest.writeString(this.checkType);
        dest.writeString(this.passTime);
        dest.writeValue(this.passUserid);
        dest.writeString(this.lastReportTime);
        dest.writeValue(this.lastReportUserid);
        dest.writeValue(this.passLon);
        dest.writeValue(this.passLat);
        dest.writeString(this.addTime);
        dest.writeValue(this.addUserId);
        dest.writeString(this.addUserExp);
        dest.writeValue(this.updateUserId);
        dest.writeString(this.updateUserExp);
        dest.writeString(this.updateTime);
        dest.writeValue(this.domainId);
        dest.writeString(this.version);
        dest.writeValue(this.taskGroup);
        dest.writeInt(this.isDelete);
        dest.writeString(this.remarks);
        dest.writeInt(this.passTimes);
        dest.writeInt(this.passStatus);
        dest.writeString(this.passStatusExp);
    }

    protected PatrolTaskResultBean(Parcel in) {
        super(in);
        this.taskId = (Long) in.readValue(Long.class.getClassLoader());
        this.planId = (Long) in.readValue(Long.class.getClassLoader());
        this.lon = (Double) in.readValue(Double.class.getClassLoader());
        this.lat = (Double) in.readValue(Double.class.getClassLoader());
        this.checkName = in.readString();
        this.objectId = in.readString();
        this.isMap = in.readInt();
        this.objcets = in.readString();
        this.objectType = in.readInt();
        this.checkPointSource = in.readInt();
        this.checkType = in.readString();
        this.passTime = in.readString();
        this.passUserid = (Long) in.readValue(Long.class.getClassLoader());
        this.lastReportTime = in.readString();
        this.lastReportUserid = (Long) in.readValue(Long.class.getClassLoader());
        this.passLon = (Double) in.readValue(Double.class.getClassLoader());
        this.passLat = (Double) in.readValue(Double.class.getClassLoader());
        this.addTime = in.readString();
        this.addUserId = (Long) in.readValue(Long.class.getClassLoader());
        this.addUserExp = in.readString();
        this.updateUserId = (Long) in.readValue(Long.class.getClassLoader());
        this.updateUserExp = in.readString();
        this.updateTime = in.readString();
        this.domainId = (Long) in.readValue(Long.class.getClassLoader());
        this.version = in.readString();
        this.taskGroup = (Long) in.readValue(Long.class.getClassLoader());
        this.isDelete = in.readInt();
        this.remarks = in.readString();
        this.passTimes = in.readInt();
        this.passStatus = in.readInt();
        this.passStatusExp = in.readString();
    }

    public static final Creator<PatrolTaskResultBean> CREATOR = new Creator<PatrolTaskResultBean>() {
        @Override
        public PatrolTaskResultBean createFromParcel(Parcel source) {
            return new PatrolTaskResultBean(source);
        }

        @Override
        public PatrolTaskResultBean[] newArray(int size) {
            return new PatrolTaskResultBean[size];
        }
    };
}
