package com.cdqj.cdqjpatrolandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lyf on 2018/12/3 16:44
 *
 * @author lyf
 * desc：
 */
public class OrderBean implements Parcelable {

    /**
     * id : 34066801
     * addUserId : 34055598
     * addTime : 2018-12-03 15:31:02
     * updateUserId : null
     * updateTime : null
     * domainId : null
     * version : null
     * addUserExp : 系统管理员
     * updateUserExp : null
     * orderNum : 1812030006
     * orderLevel : 3
     * status : 2
     * relevanceId : 34066733
     * relevanceType : 1
     * canton : 云州区
     * grid : 10
     * address : 山西省大同市南郊区汽修厂小区4彩票销售店西南约72米
     * lon : 113.302667
     * lat : 40.074047
     * ppid : null
     * gisarea : 113.302667,40.074047
     * Stringid : null
     * orderType : 2
     * repairDate : 2018-12-03 15:31:02
     * repairUserId : 34055598
     * repairUserName : 系统管理员
     * receiveDate : null
     * receiveUserId : 34055646
     * receiveUserName : 贺龙
     * rrepairRemark : null
     * receiveRemarks : null
     * departmentId : null
     * departmentName : null
     * groupId : 34051693
     * groupName : 巡检一班组
     * isSynergy : null
     * synergyIds : null
     * gridExp : 候亚峰
     * statusExp : 待接单
     * orderLevelExp : 三级
     * token : null
     * tokenDomain : null
     * tokenUserId : null
     * tokenUserName : null
     * endIndex : null
     * pageSize : null
     * orderField : null
     * startIndex : null
     * queryData : null
     * keyword : null
     * orderFieldType : null
     * orderFieldNextType : ASC
     */

    private int id;
    private int addUserId;
    private String addTime;
    private String updateUserId;
    private String updateTime;
    private String domainId;
    private String version;
    private String addUserExp;
    private String updateUserExp;
    private String orderNum;
    private int orderLevel;
    /**
     * 1、待派单 2、已派单 3、接单 4、回报处理  5、拒单 6、完成上报  7、驳回  8、作废  9、完成 10、转移
     */
    private int status;
    private int relevanceId;
    /**
     * 工单对象 工地|隐患等
     */
    private int relevanceType;
    private String relevanceTypeExp;
    private String canton;
    private int grid;
    private String address;
    private double lon;
    private double lat;
    private String ppid;
    private String gisarea;
    private String Stringid;
    /**
     * 工单类型 主/协同
     */
    private int orderType;
    private String orderTypeExp;
    private String repairDate;
    private int repairUserId;
    private String repairUserName;
    private String receiveDate;
    private int receiveUserId;
    private String receiveUserName;
    private String rrepairRemark;
    private String receiveRemarks;
    private String departmentId;
    private String departmentName;
    private int groupId;
    private String groupName;
    private String isSynergy;
    private String synergyIds;
    private String gridExp;
    private String statusExp;
    private String orderLevelExp;
    private String token;
    private String tokenDomain;
    private String tokenUserId;
    private String tokenUserName;
    private String endIndex;
    private String pageSize;
    private String orderField;
    private String startIndex;
    private String queryData;
    private String keyword;
    private String orderFieldType;
    private String orderFieldNextType;
    private String picUrls;

    public String getRelevanceTypeExp() {
        return relevanceTypeExp;
    }

    public void setRelevanceTypeExp(String relevanceTypeExp) {
        this.relevanceTypeExp = relevanceTypeExp;
    }

    public String getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(String picUrls) {
        this.picUrls = picUrls;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(int addUserId) {
        this.addUserId = addUserId;
    }

    public String getOrderTypeExp() {
        return orderTypeExp;
    }

    public void setOrderTypeExp(String orderTypeExp) {
        this.orderTypeExp = orderTypeExp;
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

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
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

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getOrderLevel() {
        return orderLevel;
    }

    public void setOrderLevel(int orderLevel) {
        this.orderLevel = orderLevel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(int relevanceId) {
        this.relevanceId = relevanceId;
    }

    public int getRelevanceType() {
        return relevanceType;
    }

    public void setRelevanceType(int relevanceType) {
        this.relevanceType = relevanceType;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public int getGrid() {
        return grid;
    }

    public void setGrid(int grid) {
        this.grid = grid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public String getGisarea() {
        return gisarea;
    }

    public void setGisarea(String gisarea) {
        this.gisarea = gisarea;
    }

    public String getStringid() {
        return Stringid;
    }

    public void setStringid(String Stringid) {
        this.Stringid = Stringid;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(String repairDate) {
        this.repairDate = repairDate;
    }

    public int getRepairUserId() {
        return repairUserId;
    }

    public void setRepairUserId(int repairUserId) {
        this.repairUserId = repairUserId;
    }

    public String getRepairUserName() {
        return repairUserName;
    }

    public void setRepairUserName(String repairUserName) {
        this.repairUserName = repairUserName;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public int getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(int receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getReceiveUserName() {
        return receiveUserName;
    }

    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }

    public String getRrepairRemark() {
        return rrepairRemark;
    }

    public void setRrepairRemark(String rrepairRemark) {
        this.rrepairRemark = rrepairRemark;
    }

    public String getReceiveRemarks() {
        return receiveRemarks;
    }

    public void setReceiveRemarks(String receiveRemarks) {
        this.receiveRemarks = receiveRemarks;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getIsSynergy() {
        return isSynergy;
    }

    public void setIsSynergy(String isSynergy) {
        this.isSynergy = isSynergy;
    }

    public String getSynergyIds() {
        return synergyIds;
    }

    public void setSynergyIds(String synergyIds) {
        this.synergyIds = synergyIds;
    }

    public String getGridExp() {
        return gridExp;
    }

    public void setGridExp(String gridExp) {
        this.gridExp = gridExp;
    }

    public String getStatusExp() {
        return statusExp;
    }

    public void setStatusExp(String statusExp) {
        this.statusExp = statusExp;
    }

    public String getOrderLevelExp() {
        return orderLevelExp;
    }

    public void setOrderLevelExp(String orderLevelExp) {
        this.orderLevelExp = orderLevelExp;
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

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
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

    public String getOrderFieldNextType() {
        return orderFieldNextType;
    }

    public void setOrderFieldNextType(String orderFieldNextType) {
        this.orderFieldNextType = orderFieldNextType;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "id=" + id +
                ", addUserId=" + addUserId +
                ", addTime='" + addTime + '\'' +
                ", updateUserId='" + updateUserId + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", domainId='" + domainId + '\'' +
                ", version='" + version + '\'' +
                ", addUserExp='" + addUserExp + '\'' +
                ", updateUserExp='" + updateUserExp + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", orderLevel=" + orderLevel +
                ", status=" + status +
                ", relevanceId=" + relevanceId +
                ", relevanceType=" + relevanceType +
                ", canton='" + canton + '\'' +
                ", grid=" + grid +
                ", address='" + address + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                ", ppid='" + ppid + '\'' +
                ", gisarea='" + gisarea + '\'' +
                ", Stringid='" + Stringid + '\'' +
                ", orderType=" + orderType +
                ", repairDate='" + repairDate + '\'' +
                ", repairUserId=" + repairUserId +
                ", repairUserName='" + repairUserName + '\'' +
                ", receiveDate='" + receiveDate + '\'' +
                ", receiveUserId=" + receiveUserId +
                ", receiveUserName='" + receiveUserName + '\'' +
                ", rrepairRemark='" + rrepairRemark + '\'' +
                ", receiveRemarks='" + receiveRemarks + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", isSynergy='" + isSynergy + '\'' +
                ", synergyIds='" + synergyIds + '\'' +
                ", gridExp='" + gridExp + '\'' +
                ", statusExp='" + statusExp + '\'' +
                ", orderLevelExp='" + orderLevelExp + '\'' +
                ", token='" + token + '\'' +
                ", tokenDomain='" + tokenDomain + '\'' +
                ", tokenUserId='" + tokenUserId + '\'' +
                ", tokenUserName='" + tokenUserName + '\'' +
                ", endIndex='" + endIndex + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", orderField='" + orderField + '\'' +
                ", startIndex='" + startIndex + '\'' +
                ", queryData='" + queryData + '\'' +
                ", keyword='" + keyword + '\'' +
                ", orderFieldType='" + orderFieldType + '\'' +
                ", orderFieldNextType='" + orderFieldNextType + '\'' +
                '}';
    }

    public OrderBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.addUserId);
        dest.writeString(this.addTime);
        dest.writeString(this.updateUserId);
        dest.writeString(this.updateTime);
        dest.writeString(this.domainId);
        dest.writeString(this.version);
        dest.writeString(this.addUserExp);
        dest.writeString(this.updateUserExp);
        dest.writeString(this.orderNum);
        dest.writeInt(this.orderLevel);
        dest.writeInt(this.status);
        dest.writeInt(this.relevanceId);
        dest.writeInt(this.relevanceType);
        dest.writeString(this.relevanceTypeExp);
        dest.writeString(this.canton);
        dest.writeInt(this.grid);
        dest.writeString(this.address);
        dest.writeDouble(this.lon);
        dest.writeDouble(this.lat);
        dest.writeString(this.ppid);
        dest.writeString(this.gisarea);
        dest.writeString(this.Stringid);
        dest.writeInt(this.orderType);
        dest.writeString(this.orderTypeExp);
        dest.writeString(this.repairDate);
        dest.writeInt(this.repairUserId);
        dest.writeString(this.repairUserName);
        dest.writeString(this.receiveDate);
        dest.writeInt(this.receiveUserId);
        dest.writeString(this.receiveUserName);
        dest.writeString(this.rrepairRemark);
        dest.writeString(this.receiveRemarks);
        dest.writeString(this.departmentId);
        dest.writeString(this.departmentName);
        dest.writeInt(this.groupId);
        dest.writeString(this.groupName);
        dest.writeString(this.isSynergy);
        dest.writeString(this.synergyIds);
        dest.writeString(this.gridExp);
        dest.writeString(this.statusExp);
        dest.writeString(this.orderLevelExp);
        dest.writeString(this.token);
        dest.writeString(this.tokenDomain);
        dest.writeString(this.tokenUserId);
        dest.writeString(this.tokenUserName);
        dest.writeString(this.endIndex);
        dest.writeString(this.pageSize);
        dest.writeString(this.orderField);
        dest.writeString(this.startIndex);
        dest.writeString(this.queryData);
        dest.writeString(this.keyword);
        dest.writeString(this.orderFieldType);
        dest.writeString(this.orderFieldNextType);
        dest.writeString(this.picUrls);
    }

    protected OrderBean(Parcel in) {
        this.id = in.readInt();
        this.addUserId = in.readInt();
        this.addTime = in.readString();
        this.updateUserId = in.readString();
        this.updateTime = in.readString();
        this.domainId = in.readString();
        this.version = in.readString();
        this.addUserExp = in.readString();
        this.updateUserExp = in.readString();
        this.orderNum = in.readString();
        this.orderLevel = in.readInt();
        this.status = in.readInt();
        this.relevanceId = in.readInt();
        this.relevanceType = in.readInt();
        this.relevanceTypeExp = in.readString();
        this.canton = in.readString();
        this.grid = in.readInt();
        this.address = in.readString();
        this.lon = in.readDouble();
        this.lat = in.readDouble();
        this.ppid = in.readString();
        this.gisarea = in.readString();
        this.Stringid = in.readString();
        this.orderType = in.readInt();
        this.orderTypeExp = in.readString();
        this.repairDate = in.readString();
        this.repairUserId = in.readInt();
        this.repairUserName = in.readString();
        this.receiveDate = in.readString();
        this.receiveUserId = in.readInt();
        this.receiveUserName = in.readString();
        this.rrepairRemark = in.readString();
        this.receiveRemarks = in.readString();
        this.departmentId = in.readString();
        this.departmentName = in.readString();
        this.groupId = in.readInt();
        this.groupName = in.readString();
        this.isSynergy = in.readString();
        this.synergyIds = in.readString();
        this.gridExp = in.readString();
        this.statusExp = in.readString();
        this.orderLevelExp = in.readString();
        this.token = in.readString();
        this.tokenDomain = in.readString();
        this.tokenUserId = in.readString();
        this.tokenUserName = in.readString();
        this.endIndex = in.readString();
        this.pageSize = in.readString();
        this.orderField = in.readString();
        this.startIndex = in.readString();
        this.queryData = in.readString();
        this.keyword = in.readString();
        this.orderFieldType = in.readString();
        this.orderFieldNextType = in.readString();
        this.picUrls = in.readString();
    }

    public static final Creator<OrderBean> CREATOR = new Creator<OrderBean>() {
        @Override
        public OrderBean createFromParcel(Parcel source) {
            return new OrderBean(source);
        }

        @Override
        public OrderBean[] newArray(int size) {
            return new OrderBean[size];
        }
    };
}
