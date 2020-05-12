package com.cdqj.cdqjpatrolandroid.bean;

import java.util.List;

/**
 * Created by lyf on 2018/9/29 10:47
 *
 * @author lyf
 * desc：
 */
public class test {

    /**
     * success : true
     * code : 200
     * msg : 获取数据成功
     * obj : [[{"id":34055040,"addUserId":null,"addTime":null,"updateUserId":null,"updateTime":null,"domainId":null,"version":null,"addUserExp":null,"updateUserExp":null,"hdId":34054613,"orderId":34054702,"reportStatus":null,"reportUserId":34047083,"reportUserName":"霆锋","reportTime":"2018-09-28 17:56:16","reportLon":null,"reportLat":null,"reportPicture":"/hd/1538128560186.png","reportVoice":null,"reportVideo":null,"reportDescript":null,"period":null,"token":null,"tokenDomain":null,"tokenUserId":null,"tokenUserName":null,"endIndex":null,"orderField":null,"startIndex":null,"queryData":null,"keyword":null,"pageSize":null,"orderFieldNextType":"ASC","orderFieldType":null},{"id":34055051,"addUserId":null,"addTime":null,"updateUserId":null,"updateTime":null,"domainId":null,"version":null,"addUserExp":null,"updateUserExp":null,"hdId":34054613,"orderId":34054702,"reportStatus":null,"reportUserId":34047083,"reportUserName":"霆锋","reportTime":"2018-09-29 09:57:21","reportLon":null,"reportLat":null,"reportPicture":"/hd/1538186238278.jpg","reportVoice":null,"reportVideo":null,"reportDescript":null,"period":null,"token":null,"tokenDomain":null,"tokenUserId":null,"tokenUserName":null,"endIndex":null,"orderField":null,"startIndex":null,"queryData":null,"keyword":null,"pageSize":null,"orderFieldNextType":"ASC","orderFieldType":null}],[{"id":34055010,"addUserId":null,"addTime":null,"updateUserId":null,"updateTime":null,"domainId":null,"version":null,"addUserExp":null,"updateUserExp":null,"hdId":34054613,"orderId":34054702,"reportStatus":null,"reportUserId":34041123,"reportUserName":"XX","reportTime":"2018-09-25 18:00:45","reportLon":null,"reportLat":null,"reportPicture":"/hd/1537869642552.jpg","reportVoice":"/hd/1537869642159.amr","reportVideo":"/hd/1537869644966.mp4","reportDescript":null,"period":null,"token":null,"tokenDomain":null,"tokenUserId":null,"tokenUserName":null,"endIndex":null,"orderField":null,"startIndex":null,"queryData":null,"keyword":null,"pageSize":null,"orderFieldNextType":"ASC","orderFieldType":null}]]
     */

    private boolean success;
    private int code;
    private String msg;
    private List<List<ObjBean>> obj;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<List<ObjBean>> getObj() {
        return obj;
    }

    public void setObj(List<List<ObjBean>> obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * id : 34055040
         * addUserId : null
         * addTime : null
         * updateUserId : null
         * updateTime : null
         * domainId : null
         * version : null
         * addUserExp : null
         * updateUserExp : null
         * hdId : 34054613
         * orderId : 34054702
         * reportStatus : null
         * reportUserId : 34047083
         * reportUserName : 霆锋
         * reportTime : 2018-09-28 17:56:16
         * reportLon : null
         * reportLat : null
         * reportPicture : /hd/1538128560186.png
         * reportVoice : null
         * reportVideo : null
         * reportDescript : null
         * period : null
         * token : null
         * tokenDomain : null
         * tokenUserId : null
         * tokenUserName : null
         * endIndex : null
         * orderField : null
         * startIndex : null
         * queryData : null
         * keyword : null
         * pageSize : null
         * orderFieldNextType : ASC
         * orderFieldType : null
         */

        private int id;
        private Object addUserId;
        private Object addTime;
        private Object updateUserId;
        private Object updateTime;
        private Object domainId;
        private Object version;
        private Object addUserExp;
        private Object updateUserExp;
        private int hdId;
        private int orderId;
        private Object reportStatus;
        private int reportUserId;
        private String reportUserName;
        private String reportTime;
        private Object reportLon;
        private Object reportLat;
        private String reportPicture;
        private Object reportVoice;
        private Object reportVideo;
        private Object reportDescript;
        private Object period;
        private Object token;
        private Object tokenDomain;
        private Object tokenUserId;
        private Object tokenUserName;
        private Object endIndex;
        private Object orderField;
        private Object startIndex;
        private Object queryData;
        private Object keyword;
        private Object pageSize;
        private String orderFieldNextType;
        private Object orderFieldType;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getAddUserId() {
            return addUserId;
        }

        public void setAddUserId(Object addUserId) {
            this.addUserId = addUserId;
        }

        public Object getAddTime() {
            return addTime;
        }

        public void setAddTime(Object addTime) {
            this.addTime = addTime;
        }

        public Object getUpdateUserId() {
            return updateUserId;
        }

        public void setUpdateUserId(Object updateUserId) {
            this.updateUserId = updateUserId;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getDomainId() {
            return domainId;
        }

        public void setDomainId(Object domainId) {
            this.domainId = domainId;
        }

        public Object getVersion() {
            return version;
        }

        public void setVersion(Object version) {
            this.version = version;
        }

        public Object getAddUserExp() {
            return addUserExp;
        }

        public void setAddUserExp(Object addUserExp) {
            this.addUserExp = addUserExp;
        }

        public Object getUpdateUserExp() {
            return updateUserExp;
        }

        public void setUpdateUserExp(Object updateUserExp) {
            this.updateUserExp = updateUserExp;
        }

        public int getHdId() {
            return hdId;
        }

        public void setHdId(int hdId) {
            this.hdId = hdId;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public Object getReportStatus() {
            return reportStatus;
        }

        public void setReportStatus(Object reportStatus) {
            this.reportStatus = reportStatus;
        }

        public int getReportUserId() {
            return reportUserId;
        }

        public void setReportUserId(int reportUserId) {
            this.reportUserId = reportUserId;
        }

        public String getReportUserName() {
            return reportUserName;
        }

        public void setReportUserName(String reportUserName) {
            this.reportUserName = reportUserName;
        }

        public String getReportTime() {
            return reportTime;
        }

        public void setReportTime(String reportTime) {
            this.reportTime = reportTime;
        }

        public Object getReportLon() {
            return reportLon;
        }

        public void setReportLon(Object reportLon) {
            this.reportLon = reportLon;
        }

        public Object getReportLat() {
            return reportLat;
        }

        public void setReportLat(Object reportLat) {
            this.reportLat = reportLat;
        }

        public String getReportPicture() {
            return reportPicture;
        }

        public void setReportPicture(String reportPicture) {
            this.reportPicture = reportPicture;
        }

        public Object getReportVoice() {
            return reportVoice;
        }

        public void setReportVoice(Object reportVoice) {
            this.reportVoice = reportVoice;
        }

        public Object getReportVideo() {
            return reportVideo;
        }

        public void setReportVideo(Object reportVideo) {
            this.reportVideo = reportVideo;
        }

        public Object getReportDescript() {
            return reportDescript;
        }

        public void setReportDescript(Object reportDescript) {
            this.reportDescript = reportDescript;
        }

        public Object getPeriod() {
            return period;
        }

        public void setPeriod(Object period) {
            this.period = period;
        }

        public Object getToken() {
            return token;
        }

        public void setToken(Object token) {
            this.token = token;
        }

        public Object getTokenDomain() {
            return tokenDomain;
        }

        public void setTokenDomain(Object tokenDomain) {
            this.tokenDomain = tokenDomain;
        }

        public Object getTokenUserId() {
            return tokenUserId;
        }

        public void setTokenUserId(Object tokenUserId) {
            this.tokenUserId = tokenUserId;
        }

        public Object getTokenUserName() {
            return tokenUserName;
        }

        public void setTokenUserName(Object tokenUserName) {
            this.tokenUserName = tokenUserName;
        }

        public Object getEndIndex() {
            return endIndex;
        }

        public void setEndIndex(Object endIndex) {
            this.endIndex = endIndex;
        }

        public Object getOrderField() {
            return orderField;
        }

        public void setOrderField(Object orderField) {
            this.orderField = orderField;
        }

        public Object getStartIndex() {
            return startIndex;
        }

        public void setStartIndex(Object startIndex) {
            this.startIndex = startIndex;
        }

        public Object getQueryData() {
            return queryData;
        }

        public void setQueryData(Object queryData) {
            this.queryData = queryData;
        }

        public Object getKeyword() {
            return keyword;
        }

        public void setKeyword(Object keyword) {
            this.keyword = keyword;
        }

        public Object getPageSize() {
            return pageSize;
        }

        public void setPageSize(Object pageSize) {
            this.pageSize = pageSize;
        }

        public String getOrderFieldNextType() {
            return orderFieldNextType;
        }

        public void setOrderFieldNextType(String orderFieldNextType) {
            this.orderFieldNextType = orderFieldNextType;
        }

        public Object getOrderFieldType() {
            return orderFieldType;
        }

        public void setOrderFieldType(Object orderFieldType) {
            this.orderFieldType = orderFieldType;
        }
    }
}
