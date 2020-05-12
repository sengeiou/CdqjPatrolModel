package com.cdqj.cdqjpatrolandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.cdqj.cdqjpatrolandroid.gis.bean.BasePolygonsBean;

/**
 * Created by lyf on 2018/10/23 14:42
 *
 * @author lyf
 * desc：工地对象
 */
public class SiteBean extends BasePolygonsBean implements Parcelable {
    /**
     * 编号
     */
    private String code;
    /**
     * 描述
     */
    private String content;
    /**
     * 状态
     */
    private Long status;
    /**
     * 状态
     */
    private String statusExp;
    /**
     * 状态
     */
    private String siteStatus;
    /**
     * 1、待派单 2、已派单 3、接单 4、回报处理  5、拒单 6、完成上报  7、驳回  8、作废  9、完成
     */
    private String siteStatusExp;
    /**
     * 坐标x
     */
    private Double lon;
    /**
     * 坐标y
     */
    private Double lat;
    /**
     * 行政区
     */
    private String district;
    /**
     * 面积
     */
    private Long dimension;
    /**
     * 工地面
     */
    private String gis;
    /**
     * 所属区片区d
     */
    private Long areaId;
    /**
     * 所属网格
     */
    private Long grid;
    /**
     * 工地级别
     */
    private Long siteLevel;
    /**
     * 工地级别
     */
    private String siteLevelExp;
    /**
     * 照片
     */
    private String picture;
    /**
     * 语音
     */
    private String voice;
    /**
     * 视频
     */
    private String video;
    /**
     * 监护人ID
     */
    private Long guardianId;
    /**
     * 监护人
     */
    private String guardian;
    /**
     * 完工时间
     */
    private String completeTime;
    /**
     * 添加人
     */
    private String addUserName;
    /**
     * 添加人ID
     */
    private Long addUserId;
    /**
     * 添加时间
     */
    private String addTime;
    /**
     * 修改人ID
     */
    private Long upStringUserId;
    /**
     * 修改时间
     */
    private String upStringTime;
    /**
     * 修改人
     */
    private String upStringUserName;
    /**
     * 系统域
     */
    private Long domainId;
    /**
     * 上报经度
     */
    private Double addUserLon;
    /**
     * 上报纬度
     */
    private Double addUserLat;

    /**
     * 工单ID(地图隐患上报时用)
     */
    private String orderId;

    /**
     * 是否可以上报
     */
    private String isCanReport;
    /**
     * 联系人
     */
    private String siteLeader;
    /**
     * 联系人电话
     */
    private String siteLeaderTel;
    /**
     * 施工类型
     */
    private String conType;
    /**
     * 其他工地类型
     */
    private String otherType;
    /**
     * 监护原因
     */
    private String propJhyy;
    /**
     * 管道设施情况
     */
    private String propGdssqk;
    /**
     * 管道埋深情况
     */
    private String propGdmsqk;
    /**
     * 监护状态
     */
    private String jhStatus;

    public String getSiteLeader() {
        return siteLeader;
    }

    public void setSiteLeader(String siteLeader) {
        this.siteLeader = siteLeader;
    }

    public String getSiteLeaderTel() {
        return siteLeaderTel;
    }

    public void setSiteLeaderTel(String siteLeaderTel) {
        this.siteLeaderTel = siteLeaderTel;
    }

    public String getConType() {
        return conType;
    }

    public void setConType(String conType) {
        this.conType = conType;
    }

    public String getOtherType() {
        return otherType;
    }

    public void setOtherType(String otherType) {
        this.otherType = otherType;
    }

    public String getPropJhyy() {
        return propJhyy;
    }

    public void setPropJhyy(String propJhyy) {
        this.propJhyy = propJhyy;
    }

    public String getPropGdssqk() {
        return propGdssqk;
    }

    public void setPropGdssqk(String propGdssqk) {
        this.propGdssqk = propGdssqk;
    }

    public String getPropGdmsqk() {
        return propGdmsqk;
    }

    public void setPropGdmsqk(String propGdmsqk) {
        this.propGdmsqk = propGdmsqk;
    }

    public String getJhStatus() {
        return jhStatus;
    }

    public void setJhStatus(String jhStatus) {
        this.jhStatus = jhStatus;
    }

    public String getSiteStatus() {
        return siteStatus;
    }

    public void setSiteStatus(String siteStatus) {
        this.siteStatus = siteStatus;
    }

    public String getSiteStatusExp() {
        return siteStatusExp;
    }

    public void setSiteStatusExp(String siteStatusExp) {
        this.siteStatusExp = siteStatusExp;
    }

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

    public String getStatusExp() {
        return statusExp;
    }

    public void setStatusExp(String statusExp) {
        this.statusExp = statusExp;
    }

    public String getSiteLevelExp() {
        return siteLevelExp;
    }

    public void setSiteLevelExp(String siteLevelExp) {
        this.siteLevelExp = siteLevelExp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Long getDimension() {
        return dimension;
    }

    public void setDimension(Long dimension) {
        this.dimension = dimension;
    }

    public String getGis() {
        return gis;
    }

    public void setGis(String gis) {
        this.gis = gis;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getGrid() {
        return grid;
    }

    public void setGrid(Long grid) {
        this.grid = grid;
    }

    public Long getSiteLevel() {
        return siteLevel;
    }

    public void setSiteLevel(Long siteLevel) {
        this.siteLevel = siteLevel;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Long getGuardianId() {
        return guardianId;
    }

    public void setGuardianId(Long guardianId) {
        this.guardianId = guardianId;
    }

    public String getGuardian() {
        return guardian;
    }

    public void setGuardian(String guardian) {
        this.guardian = guardian;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getAddUserName() {
        return addUserName;
    }

    public void setAddUserName(String addUserName) {
        this.addUserName = addUserName;
    }

    public Long getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Long addUserId) {
        this.addUserId = addUserId;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public Long getUpStringUserId() {
        return upStringUserId;
    }

    public void setUpStringUserId(Long upStringUserId) {
        this.upStringUserId = upStringUserId;
    }

    public String getUpStringTime() {
        return upStringTime;
    }

    public void setUpStringTime(String upStringTime) {
        this.upStringTime = upStringTime;
    }

    public String getUpStringUserName() {
        return upStringUserName;
    }

    public void setUpStringUserName(String upStringUserName) {
        this.upStringUserName = upStringUserName;
    }

    public Long getDomainId() {
        return domainId;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
    }

    public Double getAddUserLon() {
        return addUserLon;
    }

    public void setAddUserLon(Double addUserLon) {
        this.addUserLon = addUserLon;
    }

    public Double getAddUserLat() {
        return addUserLat;
    }

    public void setAddUserLat(Double addUserLat) {
        this.addUserLat = addUserLat;
    }

    public SiteBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.code);
        dest.writeString(this.content);
        dest.writeValue(this.status);
        dest.writeString(this.statusExp);
        dest.writeValue(this.lon);
        dest.writeValue(this.lat);
        dest.writeString(this.district);
        dest.writeValue(this.dimension);
        dest.writeString(this.gis);
        dest.writeValue(this.areaId);
        dest.writeValue(this.grid);
        dest.writeValue(this.siteLevel);
        dest.writeString(this.siteLevelExp);
        dest.writeString(this.picture);
        dest.writeString(this.voice);
        dest.writeString(this.video);
        dest.writeValue(this.guardianId);
        dest.writeString(this.guardian);
        dest.writeString(this.completeTime);
        dest.writeString(this.addUserName);
        dest.writeValue(this.addUserId);
        dest.writeString(this.addTime);
        dest.writeValue(this.upStringUserId);
        dest.writeString(this.upStringTime);
        dest.writeString(this.upStringUserName);
        dest.writeValue(this.domainId);
        dest.writeValue(this.addUserLon);
        dest.writeValue(this.addUserLat);
        dest.writeString(this.address);
        dest.writeString(this.gisArea);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.orderId);
        dest.writeString(this.isCanReport);
        dest.writeString(this.siteStatus);
        dest.writeString(this.siteStatusExp);
    }

    protected SiteBean(Parcel in) {
        super(in);
        this.code = in.readString();
        this.content = in.readString();
        this.status = (Long) in.readValue(Long.class.getClassLoader());
        this.statusExp = in.readString();
        this.lon = (Double) in.readValue(Double.class.getClassLoader());
        this.lat = (Double) in.readValue(Double.class.getClassLoader());
        this.district = in.readString();
        this.dimension = (Long) in.readValue(Long.class.getClassLoader());
        this.gis = in.readString();
        this.areaId = (Long) in.readValue(Long.class.getClassLoader());
        this.grid = (Long) in.readValue(Long.class.getClassLoader());
        this.siteLevel = (Long) in.readValue(Long.class.getClassLoader());
        this.siteLevelExp = in.readString();
        this.picture = in.readString();
        this.voice = in.readString();
        this.video = in.readString();
        this.guardianId = (Long) in.readValue(Long.class.getClassLoader());
        this.guardian = in.readString();
        this.completeTime = in.readString();
        this.addUserName = in.readString();
        this.addUserId = (Long) in.readValue(Long.class.getClassLoader());
        this.addTime = in.readString();
        this.upStringUserId = (Long) in.readValue(Long.class.getClassLoader());
        this.upStringTime = in.readString();
        this.upStringUserName = in.readString();
        this.domainId = (Long) in.readValue(Long.class.getClassLoader());
        this.addUserLon = (Double) in.readValue(Double.class.getClassLoader());
        this.addUserLat = (Double) in.readValue(Double.class.getClassLoader());
        this.address = in.readString();
        this.gisArea = in.readString();
        this.id = in.readString();
        this.name = in.readString();
        this.isCanReport = in.readString();
        this.orderId = in.readString();
        this.siteStatus = in.readString();
        this.siteStatusExp = in.readString();
    }

    public static final Creator<SiteBean> CREATOR = new Creator<SiteBean>() {
        @Override
        public SiteBean createFromParcel(Parcel source) {
            return new SiteBean(source);
        }

        @Override
        public SiteBean[] newArray(int size) {
            return new SiteBean[size];
        }
    };
}
