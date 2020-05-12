package com.cdqj.cdqjpatrolandroid.bean;

/**
 * Created by lyf on 2018/10/16 15:57
 *
 * @author lyf
 * desc：计划操作日志对象
 */
public class PlanLogBean {
    /**
     * ID
     */
    private Long id;
    /**
     * 计划ID
     */
    private Long planId;
    /**
     * 日志类型（状态（1待审核/2未开始/3进行中/4完成/5驳回/6作废））
     */
    private String logType;
    /**
     * 日志类型扩展（状态（1待审核/2未开始/3进行中/4完成/5驳回/6作废））
     */
    private String logTypeExp;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 添加人员
     */
    private Long addUserId;
    /**
     * 添加人员扩展名
     */
    private String addUserExp;
    /**
     * 添加时间
     */
    private String addTime;
    /**
     * 更新人员
     */
    private Long updateUserId;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 版本
     */
    private String version;

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

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLogTypeExp() {
        return logTypeExp;
    }

    public void setLogTypeExp(String logTypeExp) {
        this.logTypeExp = logTypeExp;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Long addUserId) {
        this.addUserId = addUserId;
    }

    public String getAddUserExp() {
        return addUserExp;
    }

    public void setAddUserExp(String addUserExp) {
        this.addUserExp = addUserExp;
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
}
