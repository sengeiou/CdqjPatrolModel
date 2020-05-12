package com.cdqj.cdqjpatrolandroid.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DicCacheDao extends RealmObject {

    /**
     * id : 34053157
     * addUserId : null
     * addTime : null
     * updateUserId : 1929
     * updateTime : 2018-09-11 11:26:14
     * domainId : null
     * version : null
     * addUserExp : null
     * updateUserExp : 系统管理员
     * parentId : null
     * moduleId : 34050700
     * dicName : 待派单
     * dicCode : 1
     * dicExp : {"icon":"icon-status-pause","iconColor":"state-primary","btnIds":[{"icon":"icon-status-dispatch","name":"派单"},{"icon":"icon-status-obsolete","name":"作废"}]}
     * dicDescript : 待派单: 派单 ，作废
     * dicStatus : 1
     * dicSort : 100
     * moduleCode : HD_STATUS
     * systypeCode : PATROL
     * systypeId : null
     * token : null
     * tokenDomain : null
     * tokenUserId : null
     * tokenUserName : null
     * endIndex : null
     * orderField : null
     * startIndex : null
     * queryData : null
     * pageSize : null
     * orderFieldType : null
     * keyword : null
     * orderFieldNextType : ASC
     */
    @PrimaryKey
    private int id;
    private String addUserId;
    private String addTime;
    private int updateUserId;
    private String updateTime;
    private String domainId;
    private String version;
    private String addUserExp;
    private String updateUserExp;
    private String parentId;
    private int moduleId;
    private String dicName;
    private String dicCode;
    private String dicExp;
    private String dicDescript;
    private int dicStatus;
    private int dicSort;
    private String moduleCode;
    private String systypeCode;
    private String systypeId;
    private String token;
    private String tokenDomain;
    private String tokenUserId;
    private String tokenUserName;
    private String endIndex;
    private String orderField;
    private String startIndex;
    private String queryData;
    private String pageSize;
    private String orderFieldType;
    private String keyword;
    private String orderFieldNextType;
    private String dataType;

    @Override
    public String toString() {
        return "DicCacheDao{" +
                "id=" + id +
                ", addUserId='" + addUserId + '\'' +
                ", addTime='" + addTime + '\'' +
                ", updateUserId=" + updateUserId +
                ", updateTime='" + updateTime + '\'' +
                ", domainId='" + domainId + '\'' +
                ", version='" + version + '\'' +
                ", addUserExp='" + addUserExp + '\'' +
                ", updateUserExp='" + updateUserExp + '\'' +
                ", parentId='" + parentId + '\'' +
                ", moduleId=" + moduleId +
                ", dicName='" + dicName + '\'' +
                ", dicCode='" + dicCode + '\'' +
                ", dicExp='" + dicExp + '\'' +
                ", dicDescript='" + dicDescript + '\'' +
                ", dicStatus=" + dicStatus +
                ", dicSort=" + dicSort +
                ", moduleCode='" + moduleCode + '\'' +
                ", systypeCode='" + systypeCode + '\'' +
                ", systypeId='" + systypeId + '\'' +
                ", token='" + token + '\'' +
                ", tokenDomain='" + tokenDomain + '\'' +
                ", tokenUserId='" + tokenUserId + '\'' +
                ", tokenUserName='" + tokenUserName + '\'' +
                ", endIndex='" + endIndex + '\'' +
                ", orderField='" + orderField + '\'' +
                ", startIndex='" + startIndex + '\'' +
                ", queryData='" + queryData + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", orderFieldType='" + orderFieldType + '\'' +
                ", keyword='" + keyword + '\'' +
                ", orderFieldNextType='" + orderFieldNextType + '\'' +
                ", dataType='" + dataType + '\'' +
                '}';
    }

    public String getDataType() {
        return dataType == null ? "" : dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }

    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
    }

    public String getDicExp() {
        return dicExp;
    }

    public void setDicExp(String dicExp) {
        this.dicExp = dicExp;
    }

    public String getDicDescript() {
        return dicDescript;
    }

    public void setDicDescript(String dicDescript) {
        this.dicDescript = dicDescript;
    }

    public int getDicStatus() {
        return dicStatus;
    }

    public void setDicStatus(int dicStatus) {
        this.dicStatus = dicStatus;
    }

    public int getDicSort() {
        return dicSort;
    }

    public void setDicSort(int dicSort) {
        this.dicSort = dicSort;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getSystypeCode() {
        return systypeCode;
    }

    public void setSystypeCode(String systypeCode) {
        this.systypeCode = systypeCode;
    }

    public String getSystypeId() {
        return systypeId;
    }

    public void setSystypeId(String systypeId) {
        this.systypeId = systypeId;
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

    public String getOrderFieldType() {
        return orderFieldType;
    }

    public void setOrderFieldType(String orderFieldType) {
        this.orderFieldType = orderFieldType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOrderFieldNextType() {
        return orderFieldNextType;
    }

    public void setOrderFieldNextType(String orderFieldNextType) {
        this.orderFieldNextType = orderFieldNextType;
    }
}
