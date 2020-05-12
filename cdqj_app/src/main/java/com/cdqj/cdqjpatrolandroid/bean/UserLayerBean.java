package com.cdqj.cdqjpatrolandroid.bean;

import android.os.Parcel;

import com.cdqj.cdqjpatrolandroid.gis.bean.BasePolygonsBean;

/**
 * Created by lyf on 2018/10/25 11:19
 *
 * @author lyf
 * desc：图层人员信息类（实时位置及上下班抓取等）
 */
public class UserLayerBean extends BasePolygonsBean {

    /**
     * id : 1046441
     * addUserId : 1929
     * addTime : 2018-10-24 17:59:51
     * updateUserId : 1045940
     * updateTime : 2018-10-24 17:20:42
     * domainId : 2
     * version : null
     * addUserExp : 系统管理员
     * updateUserExp : 闫红转
     * groupId : 1046381
     * sysUserid : 1045940
     * sysDeptid : 34055560
     * sysDeptname : 安全管理部
     * sysStaffName : 闫红转
     * groupName : 闫红砖班组
     * devices : 862084038938792
     * areas : 闫红砖巡检片区
     * photo : null
     * onWorkTime : 2018-10-24 17:20:42
     * onWorkLon : 104.030151
     * onWorkLat : 30.650095
     * outWorkTime : null
     * outWorkLon : null
     * outWorkLat : null
     * status : 1
     * lastReportTime : 2018-10-24 18:17:59
     * lastReportLon : 30.650033
     * lastReportLat : 104.029845
     * token : null
     * tokenUserId : null
     * tokenDomain : null
     * tokenUserName : null
     * endIndex : null
     * orderField : null
     * startIndex : null
     * queryData : null
     * orderFieldType : null
     * pageSize : null
     * orderFieldNextType : ASC
     * keyword : null
     */

    private int addUserId;
    private String addTime;
    private int updateUserId;
    private String updateTime;
    private int domainId;
    private String version;
    private String addUserExp;
    private String updateUserExp;
    private int groupId;
    private int sysUserid;
    private int sysDeptid;
    private String sysDeptname;
    private String sysStaffName;
    private String groupName;
    private String devices;
    private String areas;
    private String photo;
    private String onWorkTime;
    private double onWorkLon;
    private double onWorkLat;
    private String outWorkTime;
    private String outWorkLon;
    private String outWorkLat;
    /**
     * 上下班状态
     * 1上班   2下班 3离线 4越界
     */
    private int status;
    private String lastReportTime;
    private double lastReportLon;
    private double lastReportLat;
    private String token;
    private String tokenUserId;
    private String tokenDomain;
    private String tokenUserName;
    private String endIndex;
    private String orderField;
    private String startIndex;
    private String queryData;
    private String orderFieldType;
    private String pageSize;
    private String orderFieldNextType;
    private String keyword;

    public int getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(int addUserId) {
        this.addUserId = addUserId;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public int getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(int updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getDomainId() {
        return domainId;
    }

    public void setDomainId(int domainId) {
        this.domainId = domainId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getSysUserid() {
        return sysUserid;
    }

    public void setSysUserid(int sysUserid) {
        this.sysUserid = sysUserid;
    }

    public int getSysDeptid() {
        return sysDeptid;
    }

    public void setSysDeptid(int sysDeptid) {
        this.sysDeptid = sysDeptid;
    }

    public String getSysDeptname() {
        return sysDeptname;
    }

    public void setSysDeptname(String sysDeptname) {
        this.sysDeptname = sysDeptname;
    }

    public String getSysStaffName() {
        return sysStaffName;
    }

    public void setSysStaffName(String sysStaffName) {
        this.sysStaffName = sysStaffName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDevices() {
        return devices;
    }

    public void setDevices(String devices) {
        this.devices = devices;
    }

    public String getAreas() {
        return areas;
    }

    public void setAreas(String areas) {
        this.areas = areas;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getOnWorkTime() {
        return onWorkTime;
    }

    public void setOnWorkTime(String onWorkTime) {
        this.onWorkTime = onWorkTime;
    }

    public double getOnWorkLon() {
        return onWorkLon;
    }

    public void setOnWorkLon(double onWorkLon) {
        this.onWorkLon = onWorkLon;
    }

    public double getOnWorkLat() {
        return onWorkLat;
    }

    public void setOnWorkLat(double onWorkLat) {
        this.onWorkLat = onWorkLat;
    }

    public String getOutWorkTime() {
        return outWorkTime;
    }

    public void setOutWorkTime(String outWorkTime) {
        this.outWorkTime = outWorkTime;
    }

    public String getOutWorkLon() {
        return outWorkLon;
    }

    public void setOutWorkLon(String outWorkLon) {
        this.outWorkLon = outWorkLon;
    }

    public String getOutWorkLat() {
        return outWorkLat;
    }

    public void setOutWorkLat(String outWorkLat) {
        this.outWorkLat = outWorkLat;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLastReportTime() {
        return lastReportTime;
    }

    public void setLastReportTime(String lastReportTime) {
        this.lastReportTime = lastReportTime;
    }

    public double getLastReportLon() {
        return lastReportLon;
    }

    public void setLastReportLon(double lastReportLon) {
        this.lastReportLon = lastReportLon;
    }

    public double getLastReportLat() {
        return lastReportLat;
    }

    public void setLastReportLat(double lastReportLat) {
        this.lastReportLat = lastReportLat;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenUserId() {
        return tokenUserId;
    }

    public void setTokenUserId(String tokenUserId) {
        this.tokenUserId = tokenUserId;
    }

    public String getTokenDomain() {
        return tokenDomain;
    }

    public void setTokenDomain(String tokenDomain) {
        this.tokenDomain = tokenDomain;
    }

    public String getTokenUserName() {
        return tokenUserName;
    }

    public void setTokenUserName(String tokenUserName) {
        this.tokenUserName = tokenUserName;
    }

    public String getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(String endIndex) {
        this.endIndex = endIndex;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(String startIndex) {
        this.startIndex = startIndex;
    }

    public String getQueryData() {
        return queryData;
    }

    public void setQueryData(String queryData) {
        this.queryData = queryData;
    }

    public String getOrderFieldType() {
        return orderFieldType;
    }

    public void setOrderFieldType(String orderFieldType) {
        this.orderFieldType = orderFieldType;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderFieldNextType() {
        return orderFieldNextType;
    }

    public void setOrderFieldNextType(String orderFieldNextType) {
        this.orderFieldNextType = orderFieldNextType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public UserLayerBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.addUserId);
        dest.writeString(this.addTime);
        dest.writeInt(this.updateUserId);
        dest.writeString(this.updateTime);
        dest.writeInt(this.domainId);
        dest.writeString(this.version);
        dest.writeString(this.addUserExp);
        dest.writeString(this.updateUserExp);
        dest.writeInt(this.groupId);
        dest.writeInt(this.sysUserid);
        dest.writeInt(this.sysDeptid);
        dest.writeString(this.sysDeptname);
        dest.writeString(this.sysStaffName);
        dest.writeString(this.groupName);
        dest.writeString(this.devices);
        dest.writeString(this.areas);
        dest.writeString(this.photo);
        dest.writeString(this.onWorkTime);
        dest.writeDouble(this.onWorkLon);
        dest.writeDouble(this.onWorkLat);
        dest.writeString(this.outWorkTime);
        dest.writeString(this.outWorkLon);
        dest.writeString(this.outWorkLat);
        dest.writeInt(this.status);
        dest.writeString(this.lastReportTime);
        dest.writeDouble(this.lastReportLon);
        dest.writeDouble(this.lastReportLat);
        dest.writeString(this.token);
        dest.writeString(this.tokenUserId);
        dest.writeString(this.tokenDomain);
        dest.writeString(this.tokenUserName);
        dest.writeString(this.endIndex);
        dest.writeString(this.orderField);
        dest.writeString(this.startIndex);
        dest.writeString(this.queryData);
        dest.writeString(this.orderFieldType);
        dest.writeString(this.pageSize);
        dest.writeString(this.orderFieldNextType);
        dest.writeString(this.keyword);
        dest.writeString(this.address);
        dest.writeString(this.gisArea);
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    protected UserLayerBean(Parcel in) {
        super(in);
        this.addUserId = in.readInt();
        this.addTime = in.readString();
        this.updateUserId = in.readInt();
        this.updateTime = in.readString();
        this.domainId = in.readInt();
        this.version = in.readString();
        this.addUserExp = in.readString();
        this.updateUserExp = in.readString();
        this.groupId = in.readInt();
        this.sysUserid = in.readInt();
        this.sysDeptid = in.readInt();
        this.sysDeptname = in.readString();
        this.sysStaffName = in.readString();
        this.groupName = in.readString();
        this.devices = in.readString();
        this.areas = in.readString();
        this.photo = in.readString();
        this.onWorkTime = in.readString();
        this.onWorkLon = in.readDouble();
        this.onWorkLat = in.readDouble();
        this.outWorkTime = in.readString();
        this.outWorkLon = in.readString();
        this.outWorkLat = in.readString();
        this.status = in.readInt();
        this.lastReportTime = in.readString();
        this.lastReportLon = in.readDouble();
        this.lastReportLat = in.readDouble();
        this.token = in.readString();
        this.tokenUserId = in.readString();
        this.tokenDomain = in.readString();
        this.tokenUserName = in.readString();
        this.endIndex = in.readString();
        this.orderField = in.readString();
        this.startIndex = in.readString();
        this.queryData = in.readString();
        this.orderFieldType = in.readString();
        this.pageSize = in.readString();
        this.orderFieldNextType = in.readString();
        this.keyword = in.readString();
        this.address = in.readString();
        this.gisArea = in.readString();
        this.id = in.readString();
        this.name = in.readString();
    }

    public static final Creator<UserLayerBean> CREATOR = new Creator<UserLayerBean>() {
        @Override
        public UserLayerBean createFromParcel(Parcel source) {
            return new UserLayerBean(source);
        }

        @Override
        public UserLayerBean[] newArray(int size) {
            return new UserLayerBean[size];
        }
    };
}
