package com.cdqj.cdqjpatrolandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by lyf on 2018/8/10 14:37
 *
 * @author lyf
 * desc：计划实体类
 */
public class PlanListBean implements MultiItemEntity, Parcelable {

    public static final int START = 1;
    public static final int OTHER = 2;
    /**
     * id : 1003013
     * addUserId : 1929
     * updateUserId : 1929
     * domainId : 2
     * version : null
     * addUserExp : 系统管理员
     * updateUserExp : 系统管理员
     * areaId : 34054633
     * gridAreas : 200
     * groupId : 34051693
     * examineUserId : null
     * examineTime : null
     * planTypeExp : 临时计划
     * token : null
     * keyword : null
     * endIndex : null
     * tokenDomain : null
     * tokenUserName : null
     * tokenUserId : null
     * orderField : null
     * startIndex : null
     * queryData : null
     * valveDevice : 1
     * checkPoint : 0
     * middlePipeA : 22.3
     * pageSize : null
     * orderFieldType : null
     * pressureDevice : 1
     * orderFieldNextType : ASC
     */

    private String addUserExp;
    private String updateUserExp;
    private String planTypeExp;
    private String token;
    private String keyword;
    private String endIndex;
    private String tokenDomain;
    private String tokenUserName;
    private String tokenUserId;
    private String orderField;
    private String startIndex;
    private double valveDevice;
    private double checkPoint;
    private double middlePipeA;
    private String pageSize;
    private String orderFieldType;
    private double pressureDevice;
    private double cptpressureDevice;
    private double cptvalveDevice;
    private double cptcheckPoint;
    private double cptmiddlePipeA;
    private String orderFieldNextType;

    private int taskCount;
    private int cptTaskcount;
    /**
     * id : 1042206
     * addUserId : 1929
     * updateUserId : 1929
     * domainId : 2
     * version : null
     * areaId : 34054260
     * gridAreas : 1
     * groupId : 34051693
     * examineUserId : 34047083
     * groupName : 巡检一班组
     * taskId : 1043036
     * taskStatus : 1
     * taskPlanBeginDate : 2018-10-10 00:00:00
     * taskPlanEndDate : 2018-10-13 00:00:00
     * taskStartDate : null
     * taskEndDate : null
     * taskExecuteTimes : 0
     * cptTaskCount : 0
     * nostartTaskCount : null
     * doingTaskCount : 0
     * discardTaskCount : null
     * notCptTaskCount : 0
     * token : null
     * keyword : null
     * endIndex : null
     * orderFieldType : null
     * middlePipeA : 2
     * queryData : null
     * pageSize : null
     * tokenUserId : null
     * tokenDomain : null
     * tokenUserName : null
     * orderField : null
     * startIndex : null
     * cptvalveDevice : 0
     * cptmiddlePipeA : 0
     * cptpressureDevice : 0
     */

    private String groupName;
    private int taskStatus;
    private String taskPlanBeginDate;
    private String taskPlanEndDate;
    private String taskStartDate;
    private String taskEndDate;
    private int taskExecuteTimes;
    private int cptTaskCount;
    private String nostartTaskCount;
    private int doingTaskCount;
    private String discardTaskCount;
    private int notCptTaskCount;

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    private int itemType;

    public PlanListBean() {
    }

    public PlanListBean(List<String> progress) {
        this.progress = progress;
    }

    public List<String> getProgress() {
        return progress;
    }

    public void setProgress(List<String> progress) {
        this.progress = progress;
    }

    private List<String> progress;

    public PlanListBean(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    /**
     * ID
     */
    private String id;
    /**
     * 计划名（保留）
     */
    private String planName;
    /**
     * 计划开始时间
     */
    private String beginTime;
    /**
     * 计划结束时间
     */
    private String endTime;
    /**
     * 状态（1待审核/2未开始/3进行中/4完成/5驳回/6作废）
     */
    private Integer status;
    /**
     * 删除状态
     */
    private Integer delStatus;
    /**
     * 片区
     */
    private Long areaId;
    /**
     * 网格
     */
    private String gridIds;
    /**
     * 网格总面积统计，网格计划面积
     */
    private Double gridAreas;
    /**
     * 任务点类型集合，多个以，分割
     */
    private String checkTypes;
    /**
     * 分组ID
     */
    private Long groupId;
    /**
     * 执行人id
     */
    private String executeUsers;
    /**
     * 计划类型（1 周期/2 临时）
     */
    private Integer planType;
    /**
     * 频率 频率类型 （1、日巡  2、周巡  3、月巡  4、每星期？巡  5、每？天巡 ） ，频率执行规则
     */
    private String frequency;
    /**
     * 执行时段 ,  全天1 ， 自定义11
     */
    private Integer exeType;
    /**
     * 执行时段 JSON 字符串 [{"start":"09:00","end":"17:00"}]
     */
    private String exePeriod;
    /**
     * 执行次数（任务执行次数）
     */
    private Integer requireExeTimes;
    /**
     * 执行次数（任务执行次数）
     */
    private Integer executeTimes;
    /**
     * 计划审核人
     */
    private Long examineUserId;
    /**
     * 审核时间
     */
    private String examineTime;
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
     * 域ID
     */
    private Long domainId;
    /**
     * 版本
     */
    private String version;
    /**
     * taskId 任务ID
     */
    private String taskId;

    private String statusExp;
    private String executeUsersExp;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getStatusExp() {
        return statusExp;
    }

    public void setStatusExp(String statusExp) {
        this.statusExp = statusExp;
    }

    public String getExecuteUsersExp() {
        return executeUsersExp;
    }

    public void setExecuteUsersExp(String executeUsersExp) {
        this.executeUsersExp = executeUsersExp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getGridIds() {
        return gridIds;
    }

    public void setGridIds(String gridIds) {
        this.gridIds = gridIds;
    }

    public Double getGridAreas() {
        return gridAreas;
    }

    public void setGridAreas(Double gridAreas) {
        this.gridAreas = gridAreas;
    }

    public String getCheckTypes() {
        return checkTypes;
    }

    public void setCheckTypes(String checkTypes) {
        this.checkTypes = checkTypes;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getExecuteUsers() {
        return executeUsers;
    }

    public void setExecuteUsers(String executeUsers) {
        this.executeUsers = executeUsers;
    }

    public Integer getPlanType() {
        return planType;
    }

    public void setPlanType(Integer planType) {
        this.planType = planType;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Integer getExeType() {
        return exeType;
    }

    public void setExeType(Integer exeType) {
        this.exeType = exeType;
    }

    public String getExePeriod() {
        return exePeriod;
    }

    public void setExePeriod(String exePeriod) {
        this.exePeriod = exePeriod;
    }

    public Integer getRequireExeTimes() {
        return requireExeTimes;
    }

    public void setRequireExeTimes(Integer requireExeTimes) {
        this.requireExeTimes = requireExeTimes;
    }

    public Integer getExecuteTimes() {
        return executeTimes;
    }

    public void setExecuteTimes(Integer executeTimes) {
        this.executeTimes = executeTimes;
    }

    public Long getExamineUserId() {
        return examineUserId;
    }

    public void setExamineUserId(Long examineUserId) {
        this.examineUserId = examineUserId;
    }

    public String getExamineTime() {
        return examineTime;
    }

    public void setExamineTime(String examineTime) {
        this.examineTime = examineTime;
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

    public Long getDomainId() {
        return domainId;
    }

    public void setDomainId(Long domainId) {
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

    public String getPlanTypeExp() {
        return planTypeExp;
    }

    public void setPlanTypeExp(String planTypeExp) {
        this.planTypeExp = planTypeExp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getTokenDomain() {
        return tokenDomain;
    }

    public void setTokenDomain(String tokenDomain) {
        this.tokenDomain = tokenDomain;
    }

    public String getTokenUserName() {
        return tokenUserName;
    }

    public void setTokenUserName(String tokenUserName) {
        this.tokenUserName = tokenUserName;
    }

    public String getTokenUserId() {
        return tokenUserId;
    }

    public void setTokenUserId(String tokenUserId) {
        this.tokenUserId = tokenUserId;
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

    public double getValveDevice() {
        return valveDevice;
    }

    public void setValveDevice(int valveDevice) {
        this.valveDevice = valveDevice;
    }

    public double getCheckPoint() {
        return checkPoint;
    }

    public void setCheckPoint(int checkPoint) {
        this.checkPoint = checkPoint;
    }

    public double getMiddlePipeA() {
        return middlePipeA;
    }

    public void setMiddlePipeA(double middlePipeA) {
        this.middlePipeA = middlePipeA;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderFieldType() {
        return orderFieldType;
    }

    public void setOrderFieldType(String orderFieldType) {
        this.orderFieldType = orderFieldType;
    }


    public void setPressureDevice(int pressureDevice) {
        this.pressureDevice = pressureDevice;
    }

    public String getOrderFieldNextType() {
        return orderFieldNextType;
    }


    public void setOrderFieldNextType(String orderFieldNextType) {
        this.orderFieldNextType = orderFieldNextType;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }

    public int getCptTaskcount() {
        return cptTaskcount;
    }

    public void setCptTaskcount(int cptTaskcount) {
        this.cptTaskcount = cptTaskcount;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskPlanBeginDate() {
        return taskPlanBeginDate;
    }

    public void setTaskPlanBeginDate(String taskPlanBeginDate) {
        this.taskPlanBeginDate = taskPlanBeginDate;
    }

    public String getTaskPlanEndDate() {
        return taskPlanEndDate;
    }

    public void setTaskPlanEndDate(String taskPlanEndDate) {
        this.taskPlanEndDate = taskPlanEndDate;
    }

    public String getTaskStartDate() {
        return taskStartDate;
    }

    public void setTaskStartDate(String taskStartDate) {
        this.taskStartDate = taskStartDate;
    }

    public String getTaskEndDate() {
        return taskEndDate;
    }

    public void setTaskEndDate(String taskEndDate) {
        this.taskEndDate = taskEndDate;
    }

    public int getTaskExecuteTimes() {
        return taskExecuteTimes;
    }

    public void setTaskExecuteTimes(int taskExecuteTimes) {
        this.taskExecuteTimes = taskExecuteTimes;
    }

    public int getCptTaskCount() {
        return cptTaskCount;
    }

    public void setCptTaskCount(int cptTaskCount) {
        this.cptTaskCount = cptTaskCount;
    }

    public String getNostartTaskCount() {
        return nostartTaskCount;
    }

    public void setNostartTaskCount(String nostartTaskCount) {
        this.nostartTaskCount = nostartTaskCount;
    }

    public int getDoingTaskCount() {
        return doingTaskCount;
    }

    public void setDoingTaskCount(int doingTaskCount) {
        this.doingTaskCount = doingTaskCount;
    }

    public String getDiscardTaskCount() {
        return discardTaskCount;
    }

    public void setDiscardTaskCount(String discardTaskCount) {
        this.discardTaskCount = discardTaskCount;
    }

    public int getNotCptTaskCount() {
        return notCptTaskCount;
    }

    public void setNotCptTaskCount(int notCptTaskCount) {
        this.notCptTaskCount = notCptTaskCount;
    }


    public void setCptvalveDevice(int cptvalveDevice) {
        this.cptvalveDevice = cptvalveDevice;
    }


    public void setCptmiddlePipeA(int cptmiddlePipeA) {
        this.cptmiddlePipeA = cptmiddlePipeA;
    }

    public void setValveDevice(double valveDevice) {
        this.valveDevice = valveDevice;
    }

    public void setCheckPoint(double checkPoint) {
        this.checkPoint = checkPoint;
    }

    public double getPressureDevice() {
        return pressureDevice;
    }

    public void setPressureDevice(double pressureDevice) {
        this.pressureDevice = pressureDevice;
    }

    public double getCptpressureDevice() {
        return cptpressureDevice;
    }

    public void setCptpressureDevice(double cptpressureDevice) {
        this.cptpressureDevice = cptpressureDevice;
    }

    public double getCptvalveDevice() {
        return cptvalveDevice;
    }

    public void setCptvalveDevice(double cptvalveDevice) {
        this.cptvalveDevice = cptvalveDevice;
    }

    public double getCptcheckPoint() {
        return cptcheckPoint;
    }

    public void setCptcheckPoint(double cptcheckPoint) {
        this.cptcheckPoint = cptcheckPoint;
    }

    public double getCptmiddlePipeA() {
        return cptmiddlePipeA;
    }

    public void setCptmiddlePipeA(double cptmiddlePipeA) {
        this.cptmiddlePipeA = cptmiddlePipeA;
    }

    public void setCptpressureDevice(int cptpressureDevice) {
        this.cptpressureDevice = cptpressureDevice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.addUserExp);
        dest.writeString(this.updateUserExp);
        dest.writeString(this.planTypeExp);
        dest.writeString(this.token);
        dest.writeString(this.keyword);
        dest.writeString(this.endIndex);
        dest.writeString(this.tokenDomain);
        dest.writeString(this.tokenUserName);
        dest.writeString(this.tokenUserId);
        dest.writeString(this.orderField);
        dest.writeString(this.startIndex);
        dest.writeDouble(this.valveDevice);
        dest.writeDouble(this.checkPoint);
        dest.writeDouble(this.middlePipeA);
        dest.writeString(this.pageSize);
        dest.writeString(this.orderFieldType);
        dest.writeDouble(this.pressureDevice);
        dest.writeDouble(this.cptpressureDevice);
        dest.writeDouble(this.cptvalveDevice);
        dest.writeDouble(this.cptcheckPoint);
        dest.writeDouble(this.cptmiddlePipeA);
        dest.writeString(this.orderFieldNextType);
        dest.writeInt(this.taskCount);
        dest.writeInt(this.cptTaskcount);
        dest.writeString(this.groupName);
        dest.writeInt(this.taskStatus);
        dest.writeString(this.taskPlanBeginDate);
        dest.writeString(this.taskPlanEndDate);
        dest.writeString(this.taskStartDate);
        dest.writeString(this.taskEndDate);
        dest.writeInt(this.taskExecuteTimes);
        dest.writeInt(this.cptTaskCount);
        dest.writeString(this.nostartTaskCount);
        dest.writeInt(this.doingTaskCount);
        dest.writeString(this.discardTaskCount);
        dest.writeInt(this.notCptTaskCount);
        dest.writeInt(this.itemType);
        dest.writeStringList(this.progress);
        dest.writeString(this.id);
        dest.writeString(this.planName);
        dest.writeString(this.beginTime);
        dest.writeString(this.endTime);
        dest.writeValue(this.status);
        dest.writeValue(this.delStatus);
        dest.writeValue(this.areaId);
        dest.writeString(this.gridIds);
        dest.writeValue(this.gridAreas);
        dest.writeString(this.checkTypes);
        dest.writeValue(this.groupId);
        dest.writeString(this.executeUsers);
        dest.writeValue(this.planType);
        dest.writeString(this.frequency);
        dest.writeValue(this.exeType);
        dest.writeString(this.exePeriod);
        dest.writeValue(this.requireExeTimes);
        dest.writeValue(this.executeTimes);
        dest.writeValue(this.examineUserId);
        dest.writeString(this.examineTime);
        dest.writeValue(this.addUserId);
        dest.writeString(this.addTime);
        dest.writeValue(this.updateUserId);
        dest.writeString(this.updateTime);
        dest.writeValue(this.domainId);
        dest.writeString(this.version);
        dest.writeString(this.taskId);
        dest.writeString(this.statusExp);
        dest.writeString(this.executeUsersExp);
    }

    protected PlanListBean(Parcel in) {
        this.addUserExp = in.readString();
        this.updateUserExp = in.readString();
        this.planTypeExp = in.readString();
        this.token = in.readString();
        this.keyword = in.readString();
        this.endIndex = in.readString();
        this.tokenDomain = in.readString();
        this.tokenUserName = in.readString();
        this.tokenUserId = in.readString();
        this.orderField = in.readString();
        this.startIndex = in.readString();
        this.valveDevice = in.readDouble();
        this.checkPoint = in.readDouble();
        this.middlePipeA = in.readDouble();
        this.pageSize = in.readString();
        this.orderFieldType = in.readString();
        this.pressureDevice = in.readDouble();
        this.cptpressureDevice = in.readDouble();
        this.cptvalveDevice = in.readDouble();
        this.cptcheckPoint = in.readDouble();
        this.cptmiddlePipeA = in.readDouble();
        this.orderFieldNextType = in.readString();
        this.taskCount = in.readInt();
        this.cptTaskcount = in.readInt();
        this.groupName = in.readString();
        this.taskStatus = in.readInt();
        this.taskPlanBeginDate = in.readString();
        this.taskPlanEndDate = in.readString();
        this.taskStartDate = in.readString();
        this.taskEndDate = in.readString();
        this.taskExecuteTimes = in.readInt();
        this.cptTaskCount = in.readInt();
        this.nostartTaskCount = in.readString();
        this.doingTaskCount = in.readInt();
        this.discardTaskCount = in.readString();
        this.notCptTaskCount = in.readInt();
        this.itemType = in.readInt();
        this.progress = in.createStringArrayList();
        this.id = in.readString();
        this.planName = in.readString();
        this.beginTime = in.readString();
        this.endTime = in.readString();
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.delStatus = (Integer) in.readValue(Integer.class.getClassLoader());
        this.areaId = (Long) in.readValue(Long.class.getClassLoader());
        this.gridIds = in.readString();
        this.gridAreas = (Double) in.readValue(Double.class.getClassLoader());
        this.checkTypes = in.readString();
        this.groupId = (Long) in.readValue(Long.class.getClassLoader());
        this.executeUsers = in.readString();
        this.planType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.frequency = in.readString();
        this.exeType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.exePeriod = in.readString();
        this.requireExeTimes = (Integer) in.readValue(Integer.class.getClassLoader());
        this.executeTimes = (Integer) in.readValue(Integer.class.getClassLoader());
        this.examineUserId = (Long) in.readValue(Long.class.getClassLoader());
        this.examineTime = in.readString();
        this.addUserId = (Long) in.readValue(Long.class.getClassLoader());
        this.addTime = in.readString();
        this.updateUserId = (Long) in.readValue(Long.class.getClassLoader());
        this.updateTime = in.readString();
        this.domainId = (Long) in.readValue(Long.class.getClassLoader());
        this.version = in.readString();
        this.taskId = in.readString();
        this.statusExp = in.readString();
        this.executeUsersExp = in.readString();
    }

    public static final Creator<PlanListBean> CREATOR = new Creator<PlanListBean>() {
        @Override
        public PlanListBean createFromParcel(Parcel source) {
            return new PlanListBean(source);
        }

        @Override
        public PlanListBean[] newArray(int size) {
            return new PlanListBean[size];
        }
    };
}
