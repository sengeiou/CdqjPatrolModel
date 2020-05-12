package com.cdqj.cdqjpatrolandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by lyf on 2018/9/3 17:30
 *
 * @author lyf
 * desc：登录返回对象
 */
public class LoginResultBean implements Parcelable {

    /**
     * appinfos : {"id":34053011,"addUserId":1929,"addTime":"2018-09-04 11:30:44","updateUserId":null,"updateTime":null,"domainId":null,"version":"V0.0.1","addUserExp":"系统管理员","updateUserExp":null,"publishStatus":1,"type":1,"isTop":1,"discript":"初始化上传","filepath":"/files/app/1536031842661.apk","downloadNum":null,"setupNum":null,"tokenDomain":null,"tokenUserId":null,"tokenUserName":null,"token":null,"pageSize":null,"startIndex":null,"orderField":null,"orderFieldType":null,"queryData":null,"orderFieldNextType":"ASC","keyword":null,"endIndex":null}
     * domains : [{"id":2,"addUserId":null,"addTime":null,"updateUserId":null,"updateTime":null,"domainId":null,"version":null,"addUserExp":null,"updateUserExp":null,"domainName":"成都城市燃气","domainDescription":"燃气公司","createTime":"2018-09-03 14:15:11","userId":null,"tokenDomain":null,"tokenUserId":null,"tokenUserName":null,"token":null,"pageSize":null,"startIndex":null,"orderField":null,"orderFieldType":null,"queryData":null,"orderFieldNextType":"ASC","keyword":null,"endIndex":null}]
     * token : 70y5Gm0VEa6JqdpyeAQoM+P4qthqTs0lPp5OGVomaIoWqvttmpOxw1Tdi5H35aGESI2Gw2woTgzXrnUSmjW0HKATApYdoL8e2fOplX8AlrSKO50XT1zMGe6vcjPL4DaD835S6JPpIMlNuBhuVT5nv95aLI/y0fd4QpHoaz8D+a+Ug0h7gSfiUNzDrOZrWSXFTLEeQEqK1Tg9ZAqc23lkP2hsuJvqIDwWXJMFEY8R7qxBgqg5tZmbwPtU0TgyY78c
     */
    private AppinfosBean appinfos;
    private String token;
    private List<DomainsBean> domains;

    public AppinfosBean getAppinfos() {
        return appinfos;
    }

    public void setAppinfos(AppinfosBean appinfos) {
        this.appinfos = appinfos;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<DomainsBean> getDomains() {
        return domains;
    }

    public void setDomains(List<DomainsBean> domains) {
        this.domains = domains;
    }

    public static class AppinfosBean implements Parcelable {
        /**
         * id : 34053011
         * addUserId : 1929
         * addTime : 2018-09-04 11:30:44
         * updateUserId : null
         * updateTime : null
         * domainId : null
         * version : V0.0.1
         * addUserExp : 系统管理员
         * updateUserExp : null
         * publishStatus : 1
         * type : 1
         * isTop : 1
         * discript : 初始化上传
         * filepath : /files/app/1536031842661.apk
         * downloadNum : null
         * setupNum : null
         * tokenDomain : null
         * tokenUserId : null
         * tokenUserName : null
         * token : null
         * pageSize : null
         * startIndex : null
         * orderField : null
         * orderFieldType : null
         * queryData : null
         * orderFieldNextType : ASC
         * keyword : null
         * endIndex : null
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
        private int publishStatus;
        private int type;
        private int isTop;
        private String discript;
        private String filepath;
        private String downloadNum;
        private String setupNum;
        private String tokenDomain;
        private String tokenUserId;
        private String tokenUserName;
        private String token;
        private String pageSize;
        private String startIndex;
        private String orderField;
        private String orderFieldType;
        private String queryData;
        private String orderFieldNextType;
        private String keyword;
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

        public int getPublishStatus() {
            return publishStatus;
        }

        public void setPublishStatus(int publishStatus) {
            this.publishStatus = publishStatus;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getIsTop() {
            return isTop;
        }

        public void setIsTop(int isTop) {
            this.isTop = isTop;
        }

        public String getDiscript() {
            return discript;
        }

        public void setDiscript(String discript) {
            this.discript = discript;
        }

        public String getFilepath() {
            return filepath;
        }

        public void setFilepath(String filepath) {
            this.filepath = filepath;
        }

        public String getDownloadNum() {
            return downloadNum;
        }

        public void setDownloadNum(String downloadNum) {
            this.downloadNum = downloadNum;
        }

        public String getSetupNum() {
            return setupNum;
        }

        public void setSetupNum(String setupNum) {
            this.setupNum = setupNum;
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

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getEndIndex() {
            return endIndex;
        }

        public void setEndIndex(String endIndex) {
            this.endIndex = endIndex;
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
            dest.writeInt(this.publishStatus);
            dest.writeInt(this.type);
            dest.writeInt(this.isTop);
            dest.writeString(this.discript);
            dest.writeString(this.filepath);
            dest.writeString(this.downloadNum);
            dest.writeString(this.setupNum);
            dest.writeString(this.tokenDomain);
            dest.writeString(this.tokenUserId);
            dest.writeString(this.tokenUserName);
            dest.writeString(this.token);
            dest.writeString(this.pageSize);
            dest.writeString(this.startIndex);
            dest.writeString(this.orderField);
            dest.writeString(this.orderFieldType);
            dest.writeString(this.queryData);
            dest.writeString(this.orderFieldNextType);
            dest.writeString(this.keyword);
            dest.writeString(this.endIndex);
        }

        public AppinfosBean() {
        }

        protected AppinfosBean(Parcel in) {
            this.id = in.readInt();
            this.addUserId = in.readInt();
            this.addTime = in.readString();
            this.updateUserId = in.readString();
            this.updateTime = in.readString();
            this.domainId = in.readString();
            this.version = in.readString();
            this.addUserExp = in.readString();
            this.updateUserExp = in.readString();
            this.publishStatus = in.readInt();
            this.type = in.readInt();
            this.isTop = in.readInt();
            this.discript = in.readString();
            this.filepath = in.readString();
            this.downloadNum = in.readString();
            this.setupNum = in.readString();
            this.tokenDomain = in.readString();
            this.tokenUserId = in.readString();
            this.tokenUserName = in.readString();
            this.token = in.readString();
            this.pageSize = in.readString();
            this.startIndex = in.readString();
            this.orderField = in.readString();
            this.orderFieldType = in.readString();
            this.queryData = in.readString();
            this.orderFieldNextType = in.readString();
            this.keyword = in.readString();
            this.endIndex = in.readString();
        }

        public static final Parcelable.Creator<AppinfosBean> CREATOR = new Parcelable.Creator<AppinfosBean>() {
            @Override
            public AppinfosBean createFromParcel(Parcel source) {
                return new AppinfosBean(source);
            }

            @Override
            public AppinfosBean[] newArray(int size) {
                return new AppinfosBean[size];
            }
        };
    }

    public static class DomainsBean implements Parcelable {
        /**
         * id : 2
         * addUserId : null
         * addTime : null
         * updateUserId : null
         * updateTime : null
         * domainId : null
         * version : null
         * addUserExp : null
         * updateUserExp : null
         * domainName : 成都城市燃气
         * domainDescription : 燃气公司
         * createTime : 2018-09-03 14:15:11
         * userId : null
         * tokenDomain : null
         * tokenUserId : null
         * tokenUserName : null
         * token : null
         * pageSize : null
         * startIndex : null
         * orderField : null
         * orderFieldType : null
         * queryData : null
         * orderFieldNextType : ASC
         * keyword : null
         * endIndex : null
         */

        private int id;
        private String addUserId;
        private String addTime;
        private String updateUserId;
        private String updateTime;
        private String domainId;
        private String version;
        private String addUserExp;
        private String updateUserExp;
        private String domainName;
        private String domainDescription;
        private String createTime;
        private String userId;
        private String tokenDomain;
        private String tokenUserId;
        private String tokenUserName;
        private String token;
        private String pageSize;
        private String startIndex;
        private String orderField;
        private String orderFieldType;
        private String queryData;
        private String orderFieldNextType;
        private String keyword;
        private String endIndex;

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

        public String getDomainName() {
            return domainName;
        }

        public void setDomainName(String domainName) {
            this.domainName = domainName;
        }

        public String getDomainDescription() {
            return domainDescription;
        }

        public void setDomainDescription(String domainDescription) {
            this.domainDescription = domainDescription;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getEndIndex() {
            return endIndex;
        }

        public void setEndIndex(String endIndex) {
            this.endIndex = endIndex;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.addUserId);
            dest.writeString(this.addTime);
            dest.writeString(this.updateUserId);
            dest.writeString(this.updateTime);
            dest.writeString(this.domainId);
            dest.writeString(this.version);
            dest.writeString(this.addUserExp);
            dest.writeString(this.updateUserExp);
            dest.writeString(this.domainName);
            dest.writeString(this.domainDescription);
            dest.writeString(this.createTime);
            dest.writeString(this.userId);
            dest.writeString(this.tokenDomain);
            dest.writeString(this.tokenUserId);
            dest.writeString(this.tokenUserName);
            dest.writeString(this.token);
            dest.writeString(this.pageSize);
            dest.writeString(this.startIndex);
            dest.writeString(this.orderField);
            dest.writeString(this.orderFieldType);
            dest.writeString(this.queryData);
            dest.writeString(this.orderFieldNextType);
            dest.writeString(this.keyword);
            dest.writeString(this.endIndex);
        }

        public DomainsBean() {
        }

        protected DomainsBean(Parcel in) {
            this.id = in.readInt();
            this.addUserId = in.readString();
            this.addTime = in.readString();
            this.updateUserId = in.readString();
            this.updateTime = in.readString();
            this.domainId = in.readString();
            this.version = in.readString();
            this.addUserExp = in.readString();
            this.updateUserExp = in.readString();
            this.domainName = in.readString();
            this.domainDescription = in.readString();
            this.createTime = in.readString();
            this.userId = in.readString();
            this.tokenDomain = in.readString();
            this.tokenUserId = in.readString();
            this.tokenUserName = in.readString();
            this.token = in.readString();
            this.pageSize = in.readString();
            this.startIndex = in.readString();
            this.orderField = in.readString();
            this.orderFieldType = in.readString();
            this.queryData = in.readString();
            this.orderFieldNextType = in.readString();
            this.keyword = in.readString();
            this.endIndex = in.readString();
        }

        public static final Parcelable.Creator<DomainsBean> CREATOR = new Parcelable.Creator<DomainsBean>() {
            @Override
            public DomainsBean createFromParcel(Parcel source) {
                return new DomainsBean(source);
            }

            @Override
            public DomainsBean[] newArray(int size) {
                return new DomainsBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.appinfos, flags);
        dest.writeString(this.token);
        dest.writeTypedList(this.domains);
    }

    public LoginResultBean() {
    }

    protected LoginResultBean(Parcel in) {
        this.appinfos = in.readParcelable(AppinfosBean.class.getClassLoader());
        this.token = in.readString();
        this.domains = in.createTypedArrayList(DomainsBean.CREATOR);
    }

    public static final Parcelable.Creator<LoginResultBean> CREATOR = new Parcelable.Creator<LoginResultBean>() {
        @Override
        public LoginResultBean createFromParcel(Parcel source) {
            return new LoginResultBean(source);
        }

        @Override
        public LoginResultBean[] newArray(int size) {
            return new LoginResultBean[size];
        }
    };
}
