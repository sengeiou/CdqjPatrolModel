package com.cdqj.cdqjpatrolandroid.bean;


/**
 * Created by lyf on 2018/8/7 18:17
 *
 * @author lyf
 * desc：计划巡检点上报回显对象
 */
public class PlanSuperviseReportBean {
    /**
     * ID
     */
    private Long id;
    /**
     * 计划Id
     */
    private Long planId;
    /**
     * 工单id
     */
    private Long taskId;
    /**
     * 上报任务结果ID
     */
    private Long taskResultId;
    /**
     * 回报人
     */
    private Long reportUserId;
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
    private Double reportLon;
    /**
     * 回报坐标纬度
     */
    private Double reportLat;
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
    private Long addUserId;
    /**
     * 添加时间
     */
    private String addTime;
    /**
     * 修改人
     */
    private Long updateUserId;
    /**
     * 修改时间
     */
    private String updateTime;
    /**
     * 版本
     */
    private String version;
    /**
     * 巡检点ID(设备或者巡检点)
     */
    private Long checkpointId;
    /**
     * 地图OBJECTID
     */
    private String objectid;
    /**
     * 地图PPID
     */
    private String ppid;
    /**
     * 是否回报任务
     */
    private Integer isTask;
    /**
     * 删除标志
     */
    private Integer isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getTaskResultId() {
        return taskResultId;
    }

    public void setTaskResultId(Long taskResultId) {
        this.taskResultId = taskResultId;
    }

    public Long getReportUserId() {
        return reportUserId;
    }

    public void setReportUserId(Long reportUserId) {
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

    public Double getReportLon() {
        return reportLon;
    }

    public void setReportLon(Double reportLon) {
        this.reportLon = reportLon;
    }

    public Double getReportLat() {
        return reportLat;
    }

    public void setReportLat(Double reportLat) {
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

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getCheckpointId() {
        return checkpointId;
    }

    public void setCheckpointId(Long checkpointId) {
        this.checkpointId = checkpointId;
    }

    public String getObjectid() {
        return objectid;
    }

    public void setObjectid(String objectid) {
        this.objectid = objectid;
    }

    public String getPpid() {
        return ppid;
    }

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public Integer getIsTask() {
        return isTask;
    }

    public void setIsTask(Integer isTask) {
        this.isTask = isTask;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
