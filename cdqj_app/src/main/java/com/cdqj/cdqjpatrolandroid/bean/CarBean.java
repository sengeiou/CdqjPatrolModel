package com.cdqj.cdqjpatrolandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.cdqj.cdqjpatrolandroid.gis.bean.BasePolygonsBean;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lyf on 2018/10/23 14:42
 *
 * @author lyf
 * desc：车辆对象
 */
public class CarBean extends BasePolygonsBean implements Parcelable {
    /**
     * id : 35313229
     * addUserId : null
     * addTime : null
     * updateUserId : null
     * updateTime : null
     * domainId : 2
     * version : null
     * addUserExp : null
     * updateUserExp : null
     * type : 1
     * foreignId : null
     * addDate : null
     * addUserName : null
     * remarks : 111111
     * beginUseDate : 2019-03-25 09:18:57
     * endUseDate : 2019-03-25 09:19:02
     * dutyOfficerId : null
     * dutyOfficerName : 2
     * token : null
     * tokenDomain : null
     * tokenUserId : null
     * tokenUserName : null
     * endIndex : null
     * keyword : null
     * orderFieldType : null
     * orderField : null
     * startIndex : null
     * queryData : null
     * pageSize : null
     * orderFieldNextType : ASC
     */
    private String addUserId;
    private String addTime;
    private String updateUserId;
    private String updateTime;
    private int domainId;
    private String version;
    private String addUserExp;
    private String updateUserExp;
    private String type;
    private String foreignId;
    private String addDate;
    private String addUserName;
    private String remarks;
    private String beginUseDate;
    private String endUseDate;
    private String dutyOfficerId;
    private String dutyOfficerName;
    private String token;
    private String tokenDomain;
    private String tokenUserId;
    private String tokenUserName;
    private String endIndex;
    private String keyword;
    private String orderFieldType;
    private String orderField;
    private String startIndex;
    private String queryData;
    private String pageSize;
    private String orderFieldNextType;

    public String getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(String addUserId) {
        this.addUserId = addUserId;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getForeignId() {
        return foreignId;
    }

    public void setForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getAddUserName() {
        return addUserName;
    }

    public void setAddUserName(String addUserName) {
        this.addUserName = addUserName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getBeginUseDate() {
        return beginUseDate;
    }

    public void setBeginUseDate(String beginUseDate) {
        this.beginUseDate = beginUseDate;
    }

    public String getEndUseDate() {
        return endUseDate;
    }

    public void setEndUseDate(String endUseDate) {
        this.endUseDate = endUseDate;
    }

    public String getDutyOfficerId() {
        return dutyOfficerId;
    }

    public void setDutyOfficerId(String dutyOfficerId) {
        this.dutyOfficerId = dutyOfficerId;
    }

    public String getDutyOfficerName() {
        return dutyOfficerName;
    }

    public void setDutyOfficerName(String dutyOfficerName) {
        this.dutyOfficerName = dutyOfficerName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenDomain() {
        return tokenDomain;
    }

    public void setTokenDomain(String tokenDomain) {
        this.tokenDomain = tokenDomain;
    }

    public String getTokenUserId() {
        return tokenUserId;
    }

    public void setTokenUserId(String tokenUserId) {
        this.tokenUserId = tokenUserId;
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOrderFieldType() {
        return orderFieldType;
    }

    public void setOrderFieldType(String orderFieldType) {
        this.orderFieldType = orderFieldType;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.addUserId);
        dest.writeString(this.addTime);
        dest.writeString(this.updateUserId);
        dest.writeString(this.updateTime);
        dest.writeInt(this.domainId);
        dest.writeString(this.version);
        dest.writeString(this.addUserExp);
        dest.writeString(this.updateUserExp);
        dest.writeString(this.type);
        dest.writeString(this.foreignId);
        dest.writeString(this.addDate);
        dest.writeString(this.addUserName);
        dest.writeString(this.remarks);
        dest.writeString(this.beginUseDate);
        dest.writeString(this.endUseDate);
        dest.writeString(this.dutyOfficerId);
        dest.writeString(this.dutyOfficerName);
        dest.writeString(this.token);
        dest.writeString(this.tokenDomain);
        dest.writeString(this.tokenUserId);
        dest.writeString(this.tokenUserName);
        dest.writeString(this.endIndex);
        dest.writeString(this.keyword);
        dest.writeString(this.orderFieldType);
        dest.writeString(this.orderField);
        dest.writeString(this.startIndex);
        dest.writeString(this.queryData);
        dest.writeString(this.pageSize);
        dest.writeString(this.orderFieldNextType);
    }

    public CarBean() {
    }

    protected CarBean(Parcel in) {
        super(in);
        this.addUserId = in.readString();
        this.addTime = in.readString();
        this.updateUserId = in.readString();
        this.updateTime = in.readString();
        this.domainId = in.readInt();
        this.version = in.readString();
        this.addUserExp = in.readString();
        this.updateUserExp = in.readString();
        this.type = in.readString();
        this.foreignId = in.readString();
        this.addDate = in.readString();
        this.addUserName = in.readString();
        this.remarks = in.readString();
        this.beginUseDate = in.readString();
        this.endUseDate = in.readString();
        this.dutyOfficerId = in.readString();
        this.dutyOfficerName = in.readString();
        this.token = in.readString();
        this.tokenDomain = in.readString();
        this.tokenUserId = in.readString();
        this.tokenUserName = in.readString();
        this.endIndex = in.readString();
        this.keyword = in.readString();
        this.orderFieldType = in.readString();
        this.orderField = in.readString();
        this.startIndex = in.readString();
        this.queryData = in.readString();
        this.pageSize = in.readString();
        this.orderFieldNextType = in.readString();
    }

    public static final Creator<CarBean> CREATOR = new Creator<CarBean>() {
        @Override
        public CarBean createFromParcel(Parcel source) {
            return new CarBean(source);
        }

        @Override
        public CarBean[] newArray(int size) {
            return new CarBean[size];
        }
    };
}
