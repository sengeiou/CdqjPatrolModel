package com.cdqj.cdqjpatrolandroid.bean;

import android.annotation.SuppressLint;

import com.cdqj.cdqjpatrolandroid.gis.bean.BasePolygonsBean;

/**
 * Created by lyf on 2018/10/8 16:25
 *
 * @author lyf
 * desc：网格对象
 */
@SuppressLint("ParcelCreator")
public class MapGridBean extends BasePolygonsBean {

    /*
     * id : 34053697
     * addUserId : 34047083
     * addTime : 2018-09-11 20:11:28
     * updateUserId : 1929
     * updateTime : 2018-09-25 11:03:01
     * domainId : 2
     * version : null
     * addUserExp : null
     * updateUserExp : null
     * areaId : 34054633
     * status : 1
     * gridNumber : CD-00005
     * gridArea : 200
     * gridCanton : 成都-磨子桥
     * gridColor : #ff6161
     * gridAreas : null
     * areaName : null
     * gridDevices : null
     * keyword : null
     * endIndex : null
     * tokenDomain : null
     * tokenUserId : null
     * tokenUserName : null
     * orderFieldType : null
     * pageSize : null
     * token : null
     * orderField : null
     * startIndex : null
     * queryData : null
     * checkPoint : 1231
     * orderFieldNextType : ASC
     * valveDevice : 123
     * middlePipeA : 111
     * pressureDevice : 123
     */
    /**
     * 添加人ID
     */
    private int addUserId;
    /**
     * 添加时间
     */
    private String addTime;
    /**
     * 更新人ID
     */
    private int updateUserId;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 域ID
     */
    private int domainId;
    /**
     * 版本
     */
    private String version;
    /**
     * 扩展添加用户名字
     */
    private String addUserExp;
    /**
     * 更新人名字
     */
    private String updateUserExp;
    /**
     * 片区ID
     */
    private int areaId;
    /**
     * 状态 1、表示启用，0 表示删除
     */
    private int status;
    /**
     * 网格名称（编号）
     */
    private String gridNumber;
    /**
     * 面积(平方米)
     */
    private int gridArea;
    /**
     * 行政区域(存储规则：省-市-区（县）)
     */
    private String gridCanton;
    /**
     * 颜色
     */
    private String gridColor;
    /**
     * 网格区域（GIS坐标点）
     */
    private String gridAreas;
    /**
     * 片区名字
     */
    private String areaName;
    /**
     * 网格内属性
     */
    private GridDevices gridDevices;
    /**
     * keyword
     */
    private String keyword;
    /**
     * endIndex
     */
    private String endIndex;
    /**
     * tokenDomain
     */
    private String tokenDomain;
    /**
     * tokenUserId
     */
    private String tokenUserId;
    /**
     * tokenUserName
     */
    private String tokenUserName;
    /**
     * orderFieldType
     */
    private String orderFieldType;
    /**
     * pageSize
     */
    private String pageSize;
    /**
     * token
     */
    private String token;
    /**
     * orderField
     */
    private String orderField;
    /**
     * startIndex
     */
    private String startIndex;
    /**
     * queryData
     */
    private String queryData;
    /**
     * checkPoint
     */
    private int checkPoint;
    /**
     * orderFieldNextType
     */
    private String orderFieldNextType;
    /**
     * valveDevice
     */
    private int valveDevice;
    /**
     * middlePipeA
     */
    private double middlePipeA;
    /**
     * pressureDevice
     */
    private int pressureDevice;

    @Override
    public String toString() {
        return "MapGridBean{" +
                "addUserId=" + addUserId +
                ", addTime='" + addTime + '\'' +
                ", updateUserId=" + updateUserId +
                ", updateTime='" + updateTime + '\'' +
                ", domainId=" + domainId +
                ", version='" + version + '\'' +
                ", addUserExp='" + addUserExp + '\'' +
                ", updateUserExp='" + updateUserExp + '\'' +
                ", areaId=" + areaId +
                ", status=" + status +
                ", gridNumber='" + gridNumber + '\'' +
                ", gridArea=" + gridArea +
                ", gridCanton='" + gridCanton + '\'' +
                ", gridColor='" + gridColor + '\'' +
                ", gridAreas='" + gridAreas + '\'' +
                ", areaName='" + areaName + '\'' +
                ", gridDevices=" + gridDevices +
                ", keyword='" + keyword + '\'' +
                ", endIndex='" + endIndex + '\'' +
                ", tokenDomain='" + tokenDomain + '\'' +
                ", tokenUserId='" + tokenUserId + '\'' +
                ", tokenUserName='" + tokenUserName + '\'' +
                ", orderFieldType='" + orderFieldType + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", token='" + token + '\'' +
                ", orderField='" + orderField + '\'' +
                ", startIndex='" + startIndex + '\'' +
                ", queryData='" + queryData + '\'' +
                ", checkPoint=" + checkPoint +
                ", orderFieldNextType='" + orderFieldNextType + '\'' +
                ", valveDevice=" + valveDevice +
                ", middlePipeA=" + middlePipeA +
                ", pressureDevice=" + pressureDevice +
                '}';
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

    public int getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(int updateUserId) {
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

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getGridNumber() {
        return gridNumber;
    }

    public void setGridNumber(String gridNumber) {
        this.gridNumber = gridNumber;
    }

    public int getGridArea() {
        return gridArea;
    }

    public void setGridArea(int gridArea) {
        this.gridArea = gridArea;
    }

    public String getGridCanton() {
        return gridCanton;
    }

    public void setGridCanton(String gridCanton) {
        this.gridCanton = gridCanton;
    }

    public String getGridColor() {
        return gridColor;
    }

    public void setGridColor(String gridColor) {
        this.gridColor = gridColor;
    }

    public String getGridAreas() {
        return gridAreas;
    }

    public void setGridAreas(String gridAreas) {
        this.gridAreas = gridAreas;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public GridDevices getGridDevices() {
        return gridDevices;
    }

    public void setGridDevices(GridDevices gridDevices) {
        this.gridDevices = gridDevices;
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

    public String getOrderFieldType() {
        return orderFieldType;
    }

    public void setOrderFieldType(String orderFieldType) {
        this.orderFieldType = orderFieldType;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public int getCheckPoint() {
        return checkPoint;
    }

    public void setCheckPoint(int checkPoint) {
        this.checkPoint = checkPoint;
    }

    public String getOrderFieldNextType() {
        return orderFieldNextType;
    }

    public void setOrderFieldNextType(String orderFieldNextType) {
        this.orderFieldNextType = orderFieldNextType;
    }

    public int getValveDevice() {
        return valveDevice;
    }

    public void setValveDevice(int valveDevice) {
        this.valveDevice = valveDevice;
    }

    public double getMiddlePipeA() {
        return middlePipeA;
    }

    public void setMiddlePipeA(double middlePipeA) {
        this.middlePipeA = middlePipeA;
    }

    public int getPressureDevice() {
        return pressureDevice;
    }

    public void setPressureDevice(int pressureDevice) {
        this.pressureDevice = pressureDevice;
    }

    public class GridDevices {
        /**
         * 添加时间
         */
        private String addTime;
        /**
         * 扩展添加人名字
         */
        private String addUserExp;
        /**
         * 添加人ID
         */
        private String addUserId;
        /**
         * 长度/个数
         */
        private String count;
        /**
         * 设备类型
         */
        private String deviceType;
        /**
         * 实体基础参数，系统域ID
         */
        private String domainId;
        /**
         * 网格Id
         */
        private String gridId;
        /**
         * id
         */
        private String id;
        /**
         * 图层类型
         */
        private String mapType;
        /**
         * 计量单位
         */
        private String unit;
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
        private String updateUserId;
        /**
         * version
         */
        private String version;

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

        public String getAddUserId() {
            return addUserId;
        }

        public void setAddUserId(String addUserId) {
            this.addUserId = addUserId;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getDomainId() {
            return domainId;
        }

        public void setDomainId(String domainId) {
            this.domainId = domainId;
        }

        public String getGridId() {
            return gridId;
        }

        public void setGridId(String gridId) {
            this.gridId = gridId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMapType() {
            return mapType;
        }

        public void setMapType(String mapType) {
            this.mapType = mapType;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
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

        public String getUpdateUserId() {
            return updateUserId;
        }

        public void setUpdateUserId(String updateUserId) {
            this.updateUserId = updateUserId;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
