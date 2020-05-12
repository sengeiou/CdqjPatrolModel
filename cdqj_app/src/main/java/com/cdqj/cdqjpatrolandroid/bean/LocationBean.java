package com.cdqj.cdqjpatrolandroid.bean;

import com.cdqj.cdqjpatrolandroid.utils.Ignore;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by lyf on 2018/9/19 16:26
 *
 * @author lyf
 * desc：坐标对象
 */
public class LocationBean extends RealmObject {

    /*
     * addTime : 2018-09-19T08:24:28.995Z
     * addUserExp : string
     * addUserId : 0
     * collectionTime : 2018-09-19T08:24:28.995Z
     * distance : 0
     * domainId : 0
     * endIndex : 0
     * gps : 0
     * id : 0
     * imei : string
     * keyword : string
     * lat : 0
     * lon : 0
     * orderField : string
     * orderFieldType : string
     * other : string
     * power : 0
     * queryData : {}
     * signal : 0
     * speed : 0
     * startIndex : 0
     * status : 0
     * token : string
     * tokenDomain : 0
     * tokenUserId : 0
     * tokenUserName : string
     * updateTime : 2018-09-19T08:24:28.995Z
     * updateUserExp : string
     * updateUserId : 0
     * userId : 0
     * userName : string
     * version : string
     */

    /**
     * 主键（无效，不能自增） 重点注意
     *
     * 重点注意：Realm数据库的主键字段不是自动增长的，并且不支持设置数据的自增。
     * 需要自己设置，做添加的时候如果不给id字段值，默认为是0。
     * 后面再添加的话会报错说id为0的字段已经存在。
     * 尤其是批量添加的时候要注意，当心出现只添加了一条记录的悲剧！
     */
    @Ignore
    private int keyId;
    /**
     * 添加时间
     */
    private String addTime;
    /**
     * 扩展添加用户名字
     */
    private String addUserExp;
    /**
     * 添加人
     */
    private int addUserId;
    /**
     * 坐标采集时间
     */
    private String collectionTime;
    /**
     * 距离
     */
    private double distance;
    /**
     * 当前域id
     */
    private int domainId;
    /**
     * S
     */
    private int endIndex;
    /**
     * GPS卫星数量
     */
    private int gps;
    /**
     * ID
     */
    private String id;
    /**
     * 设备IMEI
     */
    private String imei;
    /**
     * S
     */
    private String keyword;
    /**
     * 纬度
     */
    private double lat;
    /**
     * 经度
     */
    private double lon;
    /**
     * S
     */
    private String orderField;
    /**
     * S
     */
    private String orderFieldType;
    /**
     * 其他信息
     */
    private String other;
    /**
     * 电量
     */
    private int power;
    /**
     * 信号
     */
    private String signal;
    /**
     * 当前速度
     */
    private double speed;
    /**
     * S
     */
    private int startIndex;
    /**
     * 状态（上班/下班） 1、上班 2、下班 3、离线 4、越界
     */
    private int status;
    /**
     * S
     */
    private String token;
    /**
     * S
     */
    private int tokenDomain;
    /**
     * S
     */
    private int tokenUserId;
    /**
     * S
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
     * 用户ID
     */
    private int userId;
    /**
     * 用户名字
     */
    private String userName;
    /**
     * 版本
     */
    private String version;
    /**
     * 时间毫秒(作为主键)
     */
    @Ignore
    @PrimaryKey
    private Long time;
    /**
     * 方向
     */
    private float direction;
    /**
     * 耗时
     */
    private int consumTime;
    /**
     * 地址
     */
    private String address;
    /**
     * 是否上传
     */
    @Ignore
    private boolean isUpdate;
    /**
     * 坐标是否转换
     */
    private boolean isTrans;


    public boolean isTrans() {
        return isTrans;
    }

    public void setTrans(boolean trans) {
        isTrans = trans;
    }
    //    private RealmList<AppInfo> appInfos;

//    public RealmList<AppInfo> getAppInfos() {
//        return appInfos;
//    }

//    public void setAppInfos(RealmList<AppInfo> appInfos) {
//        this.appInfos = appInfos;
//    }

    public int getConsumTime() {
        return consumTime;
    }

    public void setConsumTime(int consumTime) {
        this.consumTime = consumTime;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
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

    public String getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(String collectionTime) {
        this.collectionTime = collectionTime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
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

    public int getGps() {
        return gps;
    }

    public void setGps(int gps) {
        this.gps = gps;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
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

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "LocationBean{" +
                "keyId=" + keyId +
                ", addTime='" + addTime + '\'' +
                ", addUserExp='" + addUserExp + '\'' +
                ", addUserId=" + addUserId +
                ", collectionTime='" + collectionTime + '\'' +
                ", distance=" + distance +
                ", domainId=" + domainId +
                ", endIndex=" + endIndex +
                ", gps=" + gps +
                ", id='" + id + '\'' +
                ", imei='" + imei + '\'' +
                ", keyword='" + keyword + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", orderField='" + orderField + '\'' +
                ", orderFieldType='" + orderFieldType + '\'' +
                ", other='" + other + '\'' +
                ", power=" + power +
                ", signal=" + signal +
                ", speed=" + speed +
                ", startIndex=" + startIndex +
                ", status=" + status +
                ", token='" + token + '\'' +
                ", tokenDomain=" + tokenDomain +
                ", tokenUserId=" + tokenUserId +
                ", tokenUserName='" + tokenUserName + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", updateUserExp='" + updateUserExp + '\'' +
                ", updateUserId=" + updateUserId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", version='" + version + '\'' +
                ", time=" + time +
                ", direction=" + direction +
                ", consumTime=" + consumTime +
                ", address='" + address + '\'' +
                ", isUpdate=" + isUpdate +
//                ", appInfos=" + appInfos +
                '}';
    }

    @Override
    public boolean isManaged() {
        return super.isManaged();
    }
}
