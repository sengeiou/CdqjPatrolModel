package com.cdqj.cdqjpatrolandroid.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by lyf on 2018/12/7 11:25
 *
 * @author lyf
 * desc：网格基础信息保存数据库
 */
public class BasicGridBean extends RealmObject {

    /**
     * id : 7
     * addUserId : 1929
     * addTime : 2018-10-31 11:29:56
     * updateUserId : null
     * updateTime : null
     * domainId : 2
     * version : null
     * addUserExp : null
     * updateUserExp : null
     * areaId : 1047468
     * status : 1
     * gridNumber : 蒋亚莉
     * gridArea : 0
     * gridCanton : 云州区
     * gridColor : #666AD1
     * gridAreas : 113.267089844,40.0687255858999 113.261901855,40.0640869141 113.26751709,40.0588989258 113.262084961,40.055480957 113.256286621,40.0612792968999 113.244689941,40.0540771483999 113.238525391,40.0560913086 113.246276855,40.0565185546999 113.251525879,40.0595092773 113.245910645,40.0659179688 113.253295898,40.0704956055 113.25592041,40.0718994141 113.260681152,40.0747070313 113.267089844,40.0687255858999
     * deviceInfos : null
     * areaName : null
     * exceptIds : null
     * areaStatus : null
     * tokenDomain : null
     * tokenUserId : null
     * tokenUserName : null
     * token : null
     * pageSize : null
     * startIndex : null
     * orderField : null
     * orderFieldType : null
     * keyword : null
     * queryData : null
     * orderFieldNextType : ASC
     * endIndex : null
     */
    @PrimaryKey
    private int id;
    private int addUserId;
    private String addTime;
    private String updateUserId;
    private String updateTime;
    private int domainId;
    private String version;
    private String addUserExp;
    private String updateUserExp;
    private int areaId;
    private int status;
    private String gridNumber;
    private int gridArea;
    private String gridCanton;
    private String gridColor;
    private String gridAreas;
    private String deviceInfos;
    private String areaName;
    private String exceptIds;
    private String areaStatus;
    private String tokenDomain;
    private String tokenUserId;
    private String tokenUserName;
    private String token;
    private String pageSize;
    private String startIndex;
    private String orderField;
    private String orderFieldType;
    private String keyword;
    private String queryData;
    private String orderFieldNextType;
    private String endIndex;

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

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getGridNumber() {
        return gridNumber;
    }

    public void setGridNumber(String gridNumber) {
        this.gridNumber = gridNumber;
    }

    public int getGridArea() {
        return gridArea;
    }

    public void setGridArea(int gridArea) {
        this.gridArea = gridArea;
    }

    public String getGridCanton() {
        return gridCanton;
    }

    public void setGridCanton(String gridCanton) {
        this.gridCanton = gridCanton;
    }

    public String getGridColor() {
        return gridColor;
    }

    public void setGridColor(String gridColor) {
        this.gridColor = gridColor;
    }

    public String getGridAreas() {
        return gridAreas;
    }

    public void setGridAreas(String gridAreas) {
        this.gridAreas = gridAreas;
    }

    public String getDeviceInfos() {
        return deviceInfos;
    }

    public void setDeviceInfos(String deviceInfos) {
        this.deviceInfos = deviceInfos;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getExceptIds() {
        return exceptIds;
    }

    public void setExceptIds(String exceptIds) {
        this.exceptIds = exceptIds;
    }

    public String getAreaStatus() {
        return areaStatus;
    }

    public void setAreaStatus(String areaStatus) {
        this.areaStatus = areaStatus;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(String startIndex) {
        this.startIndex = startIndex;
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getQueryData() {
        return queryData;
    }

    public void setQueryData(String queryData) {
        this.queryData = queryData;
    }

    public String getOrderFieldNextType() {
        return orderFieldNextType;
    }

    public void setOrderFieldNextType(String orderFieldNextType) {
        this.orderFieldNextType = orderFieldNextType;
    }

    public String getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(String endIndex) {
        this.endIndex = endIndex;
    }
}
