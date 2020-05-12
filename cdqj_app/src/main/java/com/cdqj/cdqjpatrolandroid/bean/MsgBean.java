package com.cdqj.cdqjpatrolandroid.bean;

/**
 * Created by lyf on 2018/9/30 10:24
 *
 * @author lyf
 * desc：消息
 */
public class MsgBean {

    /**
     * id : 34054933
     * addUserId : 1929
     * addTime : 2018-09-21 15:11:42
     * updateUserId : null
     * updateTime : null
     * domainId : 2
     * version : null
     * addUserExp : null
     * updateUserExp : null
     * type : 1
     * remarks : 消息描述信息
     * bizId : 12345
     * bizRemarks : 测试消息
     * token : null
     * tokenDomain : null
     * tokenUserId : null
     * tokenUserName : null
     * endIndex : null
     * orderField : null
     * startIndex : null
     * queryData : null
     * pageSize : null
     * keyword : null
     * orderFieldType : null
     * orderFieldNextType : ASC
     */

    private int id;
    private int addUserId;
    private String addTime;
    private String updateUserId;
    private String updateTime;
    private int domainId;
    private String version;
    private String addUserExp;
    private String updateUserExp;
    private int type;
    private String remarks;
    private int bizId;
    private String bizRemarks;
    private String token;
    private String tokenDomain;
    private String tokenUserId;
    private String tokenUserName;
    private String endIndex;
    private String orderField;
    private String startIndex;
    private String queryData;
    private String pageSize;
    private String keyword;
    private String orderFieldType;
    private String orderFieldNextType;
    /**
     * 消息状态 1已读 2未读 3删除
     */
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getBizId() {
        return bizId;
    }

    public void setBizId(int bizId) {
        this.bizId = bizId;
    }

    public String getBizRemarks() {
        return bizRemarks;
    }

    public void setBizRemarks(String bizRemarks) {
        this.bizRemarks = bizRemarks;
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
}
