package com.cdqj.cdqjpatrolandroid.bean;

import android.os.Parcel;

import com.cdqj.cdqjpatrolandroid.gis.bean.BasePolygonsBean;

/**
 * Created by lyf on 2018/9/10 10:53
 *
 * @author lyf
 * desc：隐患实体类
 */
public class HdOrderBean extends BasePolygonsBean{

    /**
     * 扩展添加用户名字
     */
    private String addUserExp;
    /**
     * 添加人
     */
    private int addUserId;
    /**
     * 部门id
     */
    private int departmentId;
    /**
     * 部门名称
     */
    private String departmentName;
    /**
     * 添加时间
     */
    private String addTime;
    /**
     * 实体基础参数，系统域ID
     */
    private int domainId;
    /**
     * 添加时间
     */
    private int endIndex;
    /**
     * 班组id
     */
    private int groupId;
    /**
     * 班组名称
     */
    private String groupName;
    /**
     * 隐患编号
     */
    private String hdCode;
    /**
     * 隐患id
     */
    private int hdId;
    /**
     * 添加时间
     */
    private String keyword;
    /**
     * 添加时间
     */
    private String orderField;
    /**
     * 工单类型（协同、主流程）
     */
    private String orderFieldType;
    /**
     * 工单类型（协同、主流程）
     */
    private int orderType;
    /**
     * 工单ID(地图隐患上报时用)
     */
    private String orderId;
    /**
     * 接单时间
     */
    private String receiveDate;
    /**
     * 接单备注
     */
    private String receiveRemarks;
    /**
     * 接单人
     */
    private int receiveUserId;
    /**
     * 接单人名称
     */
    private String receiveUserName;
    /**
     * 派单时间
     */
    private String repairDate;
    /**
     * 派单人
     */
    private String repairUserId;
    /**
     * 派单人名称
     */
    private String repairUserName;
    /**
     * 备注
     */
    private String rrepairRemark;
    /**
     * 添加时间
     */
    private int startIndex;
    /**
     * 1、待派单 2、已派单 3、接单 4、回报处理  5、拒单 6、完成上报  7、驳回  8、作废  9、完成
     */
    private int status;
    /**
     * 添加时间
     */
    private String token;
    /**
     * 添加时间
     */
    private int tokenDomain;
    /**
     * 添加时间
     */
    private int tokenUserId;
    /**
     * 添加时间
     */
    private String tokenUserName;
    /**
     * 修改时间
     */
    private String updateTime;
    /**
     * 扩展修改用户名字
     */
    private String updateUserExp;
    /**
     * 修改人
     */
    private int updateUserId;
    /**
     * 版本
     */
    private String version;
    /**
     * 图片
     */
    private String hdPicture;
    private String orderNum;
    /**
     * 隐患状态
     */
    private String hdStatus;
    /**
     * 隐患状态描述 "隐患台账状态"  1 待派单 2待接单 3处理中 4审核 5驳回 6处理完成 7作废 8转抢险
     */
    private String hdStatusExp;
    /**
     * 隐患设备类型
     */
    private String hdTypeExp;
    /**
     * 隐患类型
     */
    private int hdType;
    /**
     * 隐患等级
     */
    private String hdGrand;
    /**
     * 隐患等级描述
     */
    private String hdGrandExp;
    /**
     * 隐患地址
     */
    private String hdAddress;
    /**
     * 上报人ID
     */
    private int reportUserId;
    /**
     * 上报时间
     */
    private String reportTime;
    /**
     * 上报人
     */
    private String reportUserName;
    /**
     * 最后上报人ID
     */
    private String lastReportUserId;
    /**
     * 最后上报时间
     */
    private String lastReportTime;
    /**
     * 是否可以上报
     */
    private String isCanReport;
    /**
     * 最后上报人
     */
    private String lastReportUserName;
    private String pageSize;
    private String orderFieldNextType;
    /**
     * 隐患对象
     */
    private int hdObject;
    /**
     * 隐患对象描述
     */
    private String hdObjectExp;
    /**
     * 隐患编号
     */
    private String hdNumber;
    /**
     * 行政区域
     */
    private String hdCanton;
    /**
     * 网格编号
     */
    private String hdGrid;
    /**
     * 坐标y
     */
    private String lon;
    /**
     * 坐标x
     */
    private String lat;
    /**
     * 隐患描述
     */
    private String hdDescript;
    /**
     * 隐患设备类型
     */
    private String deviceType;
    /**
     * 设备地图PPID
     */
    private String ppid;
    private String reason;
    private String describe;

    /**
     * 敷设方式
     */
    private String propFsfs;
    /**
     * 设施类别
     */
    private String propName;
    /**
     * 管材
     */
    private String propGc;
    /**
     * 管径
     */
    private String propFj;
    /**
     * 防腐性质
     */
    private String propFfxz;
    /**
     * 漏气设备
     */
    private String propLqsb;
    /**
     * 类型 （占压 锈蚀 漏气）
     */
    private String propLx;
    /**
     * 隐患类型
     */
    private String propYhlx;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getIsCanReport() {
        return isCanReport;
    }

    public void setIsCanReport(String isCanReport) {
        this.isCanReport = isCanReport;
    }

    public int getHdObject() {
        return hdObject;
    }

    public void setHdObject(int hdObject) {
        this.hdObject = hdObject;
    }

    public String getHdObjectExp() {
        return hdObjectExp;
    }

    public void setHdObjectExp(String hdObjectExp) {
        this.hdObjectExp = hdObjectExp;
    }

    public String getHdStatusExp() {
        return hdStatusExp;
    }

    public void setHdStatusExp(String hdStatusExp) {
        this.hdStatusExp = hdStatusExp;
    }

    public String getHdTypeExp() {
        return hdTypeExp;
    }

    public void setHdTypeExp(String hdTypeExp) {
        this.hdTypeExp = hdTypeExp;
    }

    public String getHdGrandExp() {
        return hdGrandExp;
    }

    public void setHdGrandExp(String hdGrandExp) {
        this.hdGrandExp = hdGrandExp;
    }

    public String getHdPicture() {
        return hdPicture;
    }

    public void setHdPicture(String hdPicture) {
        this.hdPicture = hdPicture;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getAddUserExp() {
        return addUserExp;
    }

    public void setAddUserExp(String addUserExp) {
        this.addUserExp = addUserExp;
    }

    public int getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(int addUserId) {
        this.addUserId = addUserId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public String getPropFsfs() {
        return propFsfs;
    }

    public void setPropFsfs(String propFsfs) {
        this.propFsfs = propFsfs;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getPropGc() {
        return propGc;
    }

    public void setPropGc(String propGc) {
        this.propGc = propGc;
    }

    public String getPropFj() {
        return propFj;
    }

    public void setPropFj(String propFj) {
        this.propFj = propFj;
    }

    public String getPropFfxz() {
        return propFfxz;
    }

    public void setPropFfxz(String propFfxz) {
        this.propFfxz = propFfxz;
    }

    public String getPropLqsb() {
        return propLqsb;
    }

    public void setPropLqsb(String propLqsb) {
        this.propLqsb = propLqsb;
    }

    public String getPropLx() {
        return propLx;
    }

    public void setPropLx(String propLx) {
        this.propLx = propLx;
    }

    public String getPropYhlx() {
        return propYhlx;
    }

    public void setPropYhlx(String propYhlx) {
        this.propYhlx = propYhlx;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getDomainId() {
        return domainId;
    }

    public void setDomainId(int domainId) {
        this.domainId = domainId;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
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

    public String getHdCode() {
        return hdCode;
    }

    public void setHdCode(String hdCode) {
        this.hdCode = hdCode;
    }

    public int getHdId() {
        return hdId;
    }

    public void setHdId(int hdId) {
        this.hdId = hdId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderFieldType() {
        return orderFieldType;
    }

    public void setOrderFieldType(String orderFieldType) {
        this.orderFieldType = orderFieldType;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getReceiveRemarks() {
        return receiveRemarks;
    }

    public void setReceiveRemarks(String receiveRemarks) {
        this.receiveRemarks = receiveRemarks;
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

    public String getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(String repairDate) {
        this.repairDate = repairDate;
    }

    public String getRepairUserId() {
        return repairUserId;
    }

    public void setRepairUserId(String repairUserId) {
        this.repairUserId = repairUserId;
    }

    public String getRepairUserName() {
        return repairUserName;
    }

    public void setRepairUserName(String repairUserName) {
        this.repairUserName = repairUserName;
    }

    public String getRrepairRemark() {
        return rrepairRemark;
    }

    public void setRrepairRemark(String rrepairRemark) {
        this.rrepairRemark = rrepairRemark;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTokenDomain() {
        return tokenDomain;
    }

    public void setTokenDomain(int tokenDomain) {
        this.tokenDomain = tokenDomain;
    }

    public int getTokenUserId() {
        return tokenUserId;
    }

    public void setTokenUserId(int tokenUserId) {
        this.tokenUserId = tokenUserId;
    }

    public String getTokenUserName() {
        return tokenUserName;
    }

    public void setTokenUserName(String tokenUserName) {
        this.tokenUserName = tokenUserName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUserExp() {
        return updateUserExp;
    }

    public void setUpdateUserExp(String updateUserExp) {
        this.updateUserExp = updateUserExp;
    }

    public int getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(int updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getHdStatus() {
        return hdStatus;
    }

    public void setHdStatus(String hdStatus) {
        this.hdStatus = hdStatus;
    }

    public int getHdType() {
        return hdType;
    }

    public void setHdType(int hdType) {
        this.hdType = hdType;
    }

    public String getHdGrand() {
        return hdGrand;
    }

    public void setHdGrand(String hdGrand) {
        this.hdGrand = hdGrand;
    }

    public String getHdAddress() {
        return hdAddress;
    }

    public void setHdAddress(String hdAddress) {
        this.hdAddress = hdAddress;
    }

    public int getReportUserId() {
        return reportUserId;
    }

    public void setReportUserId(int reportUserId) {
        this.reportUserId = reportUserId;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getReportUserName() {
        return reportUserName;
    }

    public void setReportUserName(String reportUserName) {
        this.reportUserName = reportUserName;
    }

    public String getLastReportUserId() {
        return lastReportUserId;
    }

    public void setLastReportUserId(String lastReportUserId) {
        this.lastReportUserId = lastReportUserId;
    }

    public String getLastReportTime() {
        return lastReportTime;
    }

    public void setLastReportTime(String lastReportTime) {
        this.lastReportTime = lastReportTime;
    }

    public String getLastReportUserName() {
        return lastReportUserName;
    }

    public void setLastReportUserName(String lastReportUserName) {
        this.lastReportUserName = lastReportUserName;
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

    public String getHdNumber() {
        return hdNumber;
    }

    public void setHdNumber(String hdNumber) {
        this.hdNumber = hdNumber;
    }

    public String getHdCanton() {
        return hdCanton;
    }

    public void setHdCanton(String hdCanton) {
        this.hdCanton = hdCanton;
    }

    public String getHdGrid() {
        return hdGrid;
    }

    public void setHdGrid(String hdGrid) {
        this.hdGrid = hdGrid;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getHdDescript() {
        return hdDescript;
    }

    public void setHdDescript(String hdDescript) {
        this.hdDescript = hdDescript;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public static class QueryDataBean {
    }

    public HdOrderBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.addUserExp);
        dest.writeInt(this.addUserId);
        dest.writeInt(this.departmentId);
        dest.writeString(this.departmentName);
        dest.writeString(this.addTime);
        dest.writeInt(this.domainId);
        dest.writeInt(this.endIndex);
        dest.writeInt(this.groupId);
        dest.writeString(this.groupName);
        dest.writeString(this.hdCode);
        dest.writeInt(this.hdId);
        dest.writeString(this.keyword);
        dest.writeString(this.orderField);
        dest.writeString(this.orderFieldType);
        dest.writeInt(this.orderType);
        dest.writeString(this.orderId);
        dest.writeString(this.receiveDate);
        dest.writeString(this.receiveRemarks);
        dest.writeInt(this.receiveUserId);
        dest.writeString(this.receiveUserName);
        dest.writeString(this.repairDate);
        dest.writeString(this.repairUserId);
        dest.writeString(this.repairUserName);
        dest.writeString(this.rrepairRemark);
        dest.writeInt(this.startIndex);
        dest.writeInt(this.status);
        dest.writeString(this.token);
        dest.writeInt(this.tokenDomain);
        dest.writeInt(this.tokenUserId);
        dest.writeString(this.tokenUserName);
        dest.writeString(this.updateTime);
        dest.writeString(this.updateUserExp);
        dest.writeInt(this.updateUserId);
        dest.writeString(this.version);
        dest.writeString(this.hdPicture);
        dest.writeString(this.orderNum);
        dest.writeString(this.hdStatus);
        dest.writeString(this.hdStatusExp);
        dest.writeString(this.hdTypeExp);
        dest.writeInt(this.hdType);
        dest.writeString(this.hdGrand);
        dest.writeString(this.hdGrandExp);
        dest.writeString(this.hdAddress);
        dest.writeInt(this.reportUserId);
        dest.writeString(this.reportTime);
        dest.writeString(this.reportUserName);
        dest.writeString(this.lastReportUserId);
        dest.writeString(this.lastReportTime);
        dest.writeString(this.isCanReport);
        dest.writeString(this.lastReportUserName);
        dest.writeString(this.pageSize);
        dest.writeString(this.orderFieldNextType);
        dest.writeInt(this.hdObject);
        dest.writeString(this.hdObjectExp);
        dest.writeString(this.hdNumber);
        dest.writeString(this.hdCanton);
        dest.writeString(this.hdGrid);
        dest.writeString(this.lon);
        dest.writeString(this.lat);
        dest.writeString(this.hdDescript);
        dest.writeString(this.deviceType);
        dest.writeString(this.ppid);
        dest.writeString(this.reason);
        dest.writeString(this.describe);
        dest.writeString(this.propFsfs);
        dest.writeString(this.propName);
        dest.writeString(this.propGc);
        dest.writeString(this.propFj);
        dest.writeString(this.propFfxz);
        dest.writeString(this.propLqsb);
        dest.writeString(this.propLx);
        dest.writeString(this.propYhlx);
    }

    protected HdOrderBean(Parcel in) {
        super(in);
        this.addUserExp = in.readString();
        this.addUserId = in.readInt();
        this.departmentId = in.readInt();
        this.departmentName = in.readString();
        this.addTime = in.readString();
        this.domainId = in.readInt();
        this.endIndex = in.readInt();
        this.groupId = in.readInt();
        this.groupName = in.readString();
        this.hdCode = in.readString();
        this.hdId = in.readInt();
        this.keyword = in.readString();
        this.orderField = in.readString();
        this.orderFieldType = in.readString();
        this.orderType = in.readInt();
        this.orderId = in.readString();
        this.receiveDate = in.readString();
        this.receiveRemarks = in.readString();
        this.receiveUserId = in.readInt();
        this.receiveUserName = in.readString();
        this.repairDate = in.readString();
        this.repairUserId = in.readString();
        this.repairUserName = in.readString();
        this.rrepairRemark = in.readString();
        this.startIndex = in.readInt();
        this.status = in.readInt();
        this.token = in.readString();
        this.tokenDomain = in.readInt();
        this.tokenUserId = in.readInt();
        this.tokenUserName = in.readString();
        this.updateTime = in.readString();
        this.updateUserExp = in.readString();
        this.updateUserId = in.readInt();
        this.version = in.readString();
        this.hdPicture = in.readString();
        this.orderNum = in.readString();
        this.hdStatus = in.readString();
        this.hdStatusExp = in.readString();
        this.hdTypeExp = in.readString();
        this.hdType = in.readInt();
        this.hdGrand = in.readString();
        this.hdGrandExp = in.readString();
        this.hdAddress = in.readString();
        this.reportUserId = in.readInt();
        this.reportTime = in.readString();
        this.reportUserName = in.readString();
        this.lastReportUserId = in.readString();
        this.lastReportTime = in.readString();
        this.isCanReport = in.readString();
        this.lastReportUserName = in.readString();
        this.pageSize = in.readString();
        this.orderFieldNextType = in.readString();
        this.hdObject = in.readInt();
        this.hdObjectExp = in.readString();
        this.hdNumber = in.readString();
        this.hdCanton = in.readString();
        this.hdGrid = in.readString();
        this.lon = in.readString();
        this.lat = in.readString();
        this.hdDescript = in.readString();
        this.deviceType = in.readString();
        this.ppid = in.readString();
        this.reason = in.readString();
        this.describe = in.readString();
        this.propFsfs = in.readString();
        this.propName = in.readString();
        this.propGc = in.readString();
        this.propFj = in.readString();
        this.propFfxz = in.readString();
        this.propLqsb = in.readString();
        this.propLx = in.readString();
        this.propYhlx = in.readString();
    }

    public static final Creator<HdOrderBean> CREATOR = new Creator<HdOrderBean>() {
        @Override
        public HdOrderBean createFromParcel(Parcel source) {
            return new HdOrderBean(source);
        }

        @Override
        public HdOrderBean[] newArray(int size) {
            return new HdOrderBean[size];
        }
    };
}
