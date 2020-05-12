package com.cdqj.cdqjpatrolandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lyf on 2018/8/7 18:17
 *
 * @author lyf
 * desc：工单上报回显对象
 */
public class OrderSuperviseReportBean implements Parcelable {
    /**
     * ID
     */
    private int id;
    /**
     * 关联id
     */
    private int relevanceId;
    /**
     * 工单id
     */
    private int orderId;
    /**
     * 回报状态
     */
    private int reportStatus;
    /**
     * 回报人
     */
    private int reportUserId;
    /**
     * 回报人名称
     */
    private String reportUserName;
    /**
     * 回报时间
     */
    private String reportTime;
    /**
     * 回报坐标经度
     */
    private String reportLon;
    /**
     * 回报坐标纬度
     */
    private String reportLat;
    /**
     * 照片
     */
    private String reportPicture;
    /**
     * 录音
     */
    private String reportVoice;
    /**
     * 录像
     */
    private String reportVideo;
    /**
     * 描述
     */
    private String reportDescript;
    /**
     * 添加人
     */
    private int addUserId;
    /**
     * 添加时间
     */
    private String addTime;
    /**
     * 修改人
     */
    private int upStringUserId;
    /**
     * 修改时间
     */
    private String upStringTime;
    /**
     * 版本
     */
    private String version;
    /**
     * 上报周期
     */
    private int period;
    /**
     * 回报等级
     */
    private int reportLevel;

    private String[] urls;

    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(int relevanceId) {
        this.relevanceId = relevanceId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(int reportStatus) {
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

    public String getReportLon() {
        return reportLon;
    }

    public void setReportLon(String reportLon) {
        this.reportLon = reportLon;
    }

    public String getReportLat() {
        return reportLat;
    }

    public void setReportLat(String reportLat) {
        this.reportLat = reportLat;
    }

    public String getReportPicture() {
        return reportPicture;
    }

    public void setReportPicture(String reportPicture) {
        this.reportPicture = reportPicture;
    }

    public String getReportVoice() {
        return reportVoice;
    }

    public void setReportVoice(String reportVoice) {
        this.reportVoice = reportVoice;
    }

    public String getReportVideo() {
        return reportVideo;
    }

    public void setReportVideo(String reportVideo) {
        this.reportVideo = reportVideo;
    }

    public String getReportDescript() {
        return reportDescript;
    }

    public void setReportDescript(String reportDescript) {
        this.reportDescript = reportDescript;
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

    public int getUpStringUserId() {
        return upStringUserId;
    }

    public void setUpStringUserId(int upStringUserId) {
        this.upStringUserId = upStringUserId;
    }

    public String getUpStringTime() {
        return upStringTime;
    }

    public void setUpStringTime(String upStringTime) {
        this.upStringTime = upStringTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getReportLevel() {
        return reportLevel;
    }

    public void setReportLevel(int reportLevel) {
        this.reportLevel = reportLevel;
    }

    public OrderSuperviseReportBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.relevanceId);
        dest.writeInt(this.orderId);
        dest.writeInt(this.reportStatus);
        dest.writeInt(this.reportUserId);
        dest.writeString(this.reportUserName);
        dest.writeString(this.reportTime);
        dest.writeString(this.reportLon);
        dest.writeString(this.reportLat);
        dest.writeString(this.reportPicture);
        dest.writeString(this.reportVoice);
        dest.writeString(this.reportVideo);
        dest.writeString(this.reportDescript);
        dest.writeInt(this.addUserId);
        dest.writeString(this.addTime);
        dest.writeInt(this.upStringUserId);
        dest.writeString(this.upStringTime);
        dest.writeString(this.version);
        dest.writeInt(this.period);
        dest.writeInt(this.reportLevel);
        dest.writeStringArray(this.urls);
    }

    protected OrderSuperviseReportBean(Parcel in) {
        this.id = in.readInt();
        this.relevanceId = in.readInt();
        this.orderId = in.readInt();
        this.reportStatus = in.readInt();
        this.reportUserId = in.readInt();
        this.reportUserName = in.readString();
        this.reportTime = in.readString();
        this.reportLon = in.readString();
        this.reportLat = in.readString();
        this.reportPicture = in.readString();
        this.reportVoice = in.readString();
        this.reportVideo = in.readString();
        this.reportDescript = in.readString();
        this.addUserId = in.readInt();
        this.addTime = in.readString();
        this.upStringUserId = in.readInt();
        this.upStringTime = in.readString();
        this.version = in.readString();
        this.period = in.readInt();
        this.reportLevel = in.readInt();
        this.urls = in.createStringArray();
    }

    public static final Creator<OrderSuperviseReportBean> CREATOR = new Creator<OrderSuperviseReportBean>() {
        @Override
        public OrderSuperviseReportBean createFromParcel(Parcel source) {
            return new OrderSuperviseReportBean(source);
        }

        @Override
        public OrderSuperviseReportBean[] newArray(int size) {
            return new OrderSuperviseReportBean[size];
        }
    };
}
