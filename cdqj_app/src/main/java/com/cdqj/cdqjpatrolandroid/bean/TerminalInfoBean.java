package com.cdqj.cdqjpatrolandroid.bean;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * 版权：成都千嘉科技公司所有
 *
 * @author lyf
 * 创建日期： 2019/5/14
 * 创建时间： 9:29
 * 描述：终端信息
 */
public class TerminalInfoBean extends RealmObject {

    /**
     * 电量、gps状态、网络状态、手机型号、系统版本、巡线app版本
     */
    @PrimaryKey
    private Long time;
    /**
     * 是否上传
     */
    private boolean isUpdate;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 电量
     */
    private String energy;
    /**
     * 信号
     */
    private String signal;
    /**
     * 当日使用流量
     */
    private String traffic;
    /**
     * 已安装的APP
     */
    private RealmList<AppInfo> installedAppList;
    /**
     * 运行中的APP
     */
    private RealmList<AppInfo> inserviceAppList;
    /**
     * IMEI
     */
    private String imei;
    /**
     * 网络类型
     */
    private String network;
    /**
     * 移动网络运营商名称
     */
    private String networkOperatorName;
    /**
     * 姓名
     */
    private String trueName;
    /**
     * 手机型号
     */
    private String phoneModel;
    /**
     * 手机版本
     */
    private String phoneVersion;
    /**
     * 版本号
     */
    private String version;
    /**
     * 设备编号registrationId
     * */
    private String registrationId;
    public String getNetworkOperatorName() {
        return networkOperatorName;
    }

    public void setNetworkOperatorName(String networkOperatorName) {
        this.networkOperatorName = networkOperatorName;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEnergy() {
        return energy;
    }

    public void setEnergy(String energy) {
        this.energy = energy;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public RealmList<AppInfo> getInstalledAppList() {
        return installedAppList;
    }

    public void setInstalledAppList(RealmList<AppInfo> installedAppList) {
        this.installedAppList = installedAppList;
    }

    public RealmList<AppInfo> getInserviceAppList() {
        return inserviceAppList;
    }

    public void setInserviceAppList(RealmList<AppInfo> inserviceAppList) {
        this.inserviceAppList = inserviceAppList;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getPhoneVersion() {
        return phoneVersion;
    }

    public void setPhoneVersion(String phoneVersion) {
        this.phoneVersion = phoneVersion;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }
}
