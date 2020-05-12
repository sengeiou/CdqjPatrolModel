package com.cdqj.cdqjpatrolandroid.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DicCacheDaoData extends RealmObject implements Serializable{
    @PrimaryKey
    private String id;
    // 隐患-泸州
    // "PROP_LX" 类型 （占压 锈蚀 漏气）,"PROP_NAME" 设施类别,
    // "PROP_GC" 管材,"PROP_FJ" 管径,
    // "PROP_FFXZ" 防腐性质,"PROP_LQSB" 漏气设备,
    // "PROP_YHLX" 隐患类型,"PROP_FSFS" 敷设方式
    // 工地-泸州
    // 施工类型（必选）  CON_TYPE   监护原因 （可选） PROP_JHYY
    @SerializedName("PATROL:CON_TYPE")
    private RealmList<DicCacheDao> CON_TYPE; // FIXME check this code
    @SerializedName("PATROL:PROP_JHYY")
    private RealmList<DicCacheDao> PROP_JHYY; // FIXME check this code
    @SerializedName("PATROL:PROP_LX")
    private RealmList<DicCacheDao> PROP_LX; // FIXME check this code
    @SerializedName("PATROL:PROP_NAME")
    private RealmList<DicCacheDao> PROP_NAME; // FIXME check this code
    @SerializedName("PATROL:PROP_GC")
    private RealmList<DicCacheDao> PROP_GC; // FIXME check this code
    @SerializedName("PATROL:PROP_FJ")
    private RealmList<DicCacheDao> PROP_FJ; // FIXME check this code
    @SerializedName("PATROL:PROP_FFXZ")
    private RealmList<DicCacheDao> PROP_FFXZ; // FIXME check this code
    @SerializedName("PATROL:PROP_LQSB")
    private RealmList<DicCacheDao> PROP_LQSB; // FIXME check this code
    @SerializedName("PATROL:PROP_YHLX")
    private RealmList<DicCacheDao> PROP_YHLX; // FIXME check this code
    @SerializedName("PATROL:PROP_FSFS")
    private RealmList<DicCacheDao> PROP_FSFS; // FIXME check this code
    @SerializedName("PATROL:HD_STATUS")
    private RealmList<DicCacheDao> _$PATROLHD_STATUS10; // FIXME check this code
    @SerializedName("PATROL:PAT_LINE")
    private RealmList<DicCacheDao> _$PAT_LINE; // FIXME check this code
    @SerializedName("PATROL:HD_DEVICE_TYPE")
    private RealmList<DicCacheDao> _$PATROLHD_DEVICE_TYPE51; // FIXME check this code
    @SerializedName("PATROL:DEVICE_TYPE")
    private RealmList<DicCacheDao> _$PATROLGRID_COUNT_OBJECT108; // FIXME check this code
    @SerializedName("SYS_COMMON:DEFAULT_ISNO")
    private RealmList<DicCacheDao> _$SYS_COMMONDEFAULT_ISNO178; // FIXME check this code
    @SerializedName("PATROL:PLANPRE_TYPE")
    private RealmList<DicCacheDao> _$PATROLPLANPRE_TYPE50; // FIXME check this code
    @SerializedName("PATROL:RELEVANCE_TYPE")
    private RealmList<DicCacheDao> _$RELEVANCE_TYPE; // FIXME check this code
    @SerializedName("PATROL:HD_LEVEL")
    private RealmList<DicCacheDao> _$PATROLHD_LEVEL323; // FIXME check this code
    @SerializedName("SYSTEM:FILE_HOSTTYPE")
    private RealmList<DicCacheDao> _$SYSTEMFILE_HOSTTYPE75; // FIXME check this code
    @SerializedName("PATROL:SITE_STATUS")
    private RealmList<DicCacheDao> _$PATROLSITESTATUS; // FIXME check this code
    @SerializedName("PATROL:SITE_LEVEL")
    private RealmList<DicCacheDao> _$_$PATROLSITELEVEL; // FIXME check this code
    @SerializedName("PATROL:ORDER_LEVEL")
    private RealmList<DicCacheDao> _$_$ORDER_LEVEL; // FIXME check this code
    @SerializedName("PATROL:ORDER_STATUS")
    private RealmList<DicCacheDao> _$_$ORDER_STATUS; // FIXME check this code
    @SerializedName("PATROL:CANTON")
    private RealmList<DicCacheDao> _$_$PATROLCANTON; // FIXME check this code
    @SerializedName("SYS_COMMON:DATA_STATUS")
    private RealmList<DicCacheDao> _$_$DATA_STATUS; // FIXME check this code

    @Override
    public String toString() {
        return "DicCacheDaoData{" +
                "id='" + id + '\'' +
                ", CON_TYPE=" + CON_TYPE +
                ", PROP_JHYY=" + PROP_JHYY +
                ", PROP_LX=" + PROP_LX +
                ", PROP_NAME=" + PROP_NAME +
                ", PROP_GC=" + PROP_GC +
                ", PROP_FJ=" + PROP_FJ +
                ", PROP_FFXZ=" + PROP_FFXZ +
                ", PROP_LQSB=" + PROP_LQSB +
                ", PROP_YHLX=" + PROP_YHLX +
                ", PROP_FSFS=" + PROP_FSFS +
                ", _$PATROLHD_STATUS10=" + _$PATROLHD_STATUS10 +
                ", _$PAT_LINE=" + _$PAT_LINE +
                ", _$PATROLHD_DEVICE_TYPE51=" + _$PATROLHD_DEVICE_TYPE51 +
                ", _$PATROLGRID_COUNT_OBJECT108=" + _$PATROLGRID_COUNT_OBJECT108 +
                ", _$SYS_COMMONDEFAULT_ISNO178=" + _$SYS_COMMONDEFAULT_ISNO178 +
                ", _$PATROLPLANPRE_TYPE50=" + _$PATROLPLANPRE_TYPE50 +
                ", _$RELEVANCE_TYPE=" + _$RELEVANCE_TYPE +
                ", _$PATROLHD_LEVEL323=" + _$PATROLHD_LEVEL323 +
                ", _$SYSTEMFILE_HOSTTYPE75=" + _$SYSTEMFILE_HOSTTYPE75 +
                ", _$PATROLSITESTATUS=" + _$PATROLSITESTATUS +
                ", _$_$PATROLSITELEVEL=" + _$_$PATROLSITELEVEL +
                ", _$_$ORDER_LEVEL=" + _$_$ORDER_LEVEL +
                ", _$_$ORDER_STATUS=" + _$_$ORDER_STATUS +
                ", _$_$PATROLCANTON=" + _$_$PATROLCANTON +
                ", _$_$DATA_STATUS=" + _$_$DATA_STATUS +
                '}';
    }

    public RealmList<DicCacheDao> getCON_TYPE() {
        return CON_TYPE;
    }

    public void setCON_TYPE(RealmList<DicCacheDao> CON_TYPE) {
        this.CON_TYPE = CON_TYPE;
    }

    public RealmList<DicCacheDao> getPROP_JHYY() {
        return PROP_JHYY;
    }

    public void setPROP_JHYY(RealmList<DicCacheDao> PROP_JHYY) {
        this.PROP_JHYY = PROP_JHYY;
    }

    public RealmList<DicCacheDao> getPROP_LX() {
        return PROP_LX;
    }

    public void setPROP_LX(RealmList<DicCacheDao> PROP_LX) {
        this.PROP_LX = PROP_LX;
    }

    public RealmList<DicCacheDao> getPROP_NAME() {
        return PROP_NAME;
    }

    public void setPROP_NAME(RealmList<DicCacheDao> PROP_NAME) {
        this.PROP_NAME = PROP_NAME;
    }

    public RealmList<DicCacheDao> getPROP_GC() {
        return PROP_GC;
    }

    public void setPROP_GC(RealmList<DicCacheDao> PROP_GC) {
        this.PROP_GC = PROP_GC;
    }

    public RealmList<DicCacheDao> getPROP_FJ() {
        return PROP_FJ;
    }

    public void setPROP_FJ(RealmList<DicCacheDao> PROP_FJ) {
        this.PROP_FJ = PROP_FJ;
    }

    public RealmList<DicCacheDao> getPROP_FFXZ() {
        return PROP_FFXZ;
    }

    public void setPROP_FFXZ(RealmList<DicCacheDao> PROP_FFXZ) {
        this.PROP_FFXZ = PROP_FFXZ;
    }

    public RealmList<DicCacheDao> getPROP_LQSB() {
        return PROP_LQSB;
    }

    public void setPROP_LQSB(RealmList<DicCacheDao> PROP_LQSB) {
        this.PROP_LQSB = PROP_LQSB;
    }

    public RealmList<DicCacheDao> getPROP_YHLX() {
        return PROP_YHLX;
    }

    public void setPROP_YHLX(RealmList<DicCacheDao> PROP_YHLX) {
        this.PROP_YHLX = PROP_YHLX;
    }

    public RealmList<DicCacheDao> getPROP_FSFS() {
        return PROP_FSFS;
    }

    public void setPROP_FSFS(RealmList<DicCacheDao> PROP_FSFS) {
        this.PROP_FSFS = PROP_FSFS;
    }

    public RealmList<DicCacheDao> get_$_$DATA_STATUS() {
        return _$_$DATA_STATUS;
    }

    public void set_$_$DATA_STATUS(RealmList<DicCacheDao> _$_$DATA_STATUS) {
        this._$_$DATA_STATUS = _$_$DATA_STATUS;
    }

    public RealmList<DicCacheDao> get_$PAT_LINE() {
        return _$PAT_LINE;
    }

    public void set_$PAT_LINE(RealmList<DicCacheDao> _$PAT_LINE) {
        this._$PAT_LINE = _$PAT_LINE;
    }

    public RealmList<DicCacheDao> get_$_$ORDER_STATUS() {
        return _$_$ORDER_STATUS;
    }

    public void set_$_$ORDER_STATUS(RealmList<DicCacheDao> _$_$ORDER_STATUS) {
        this._$_$ORDER_STATUS = _$_$ORDER_STATUS;
    }

    public RealmList<DicCacheDao> get_$_$ORDER_LEVEL() {
        return _$_$ORDER_LEVEL;
    }

    public void set_$_$ORDER_LEVEL(RealmList<DicCacheDao> _$_$ORDER_LEVEL) {
        this._$_$ORDER_LEVEL = _$_$ORDER_LEVEL;
    }

    public RealmList<DicCacheDao> get_$RELEVANCE_TYPE() {
        return _$RELEVANCE_TYPE;
    }

    public void set_$RELEVANCE_TYPE(RealmList<DicCacheDao> _$RELEVANCE_TYPE) {
        this._$RELEVANCE_TYPE = _$RELEVANCE_TYPE;
    }

    public RealmList<DicCacheDao> get_$_$PATROLCANTON() {
        return _$_$PATROLCANTON;
    }

    public void set_$_$PATROLCANTON(RealmList<DicCacheDao> _$_$PATROLCANTON) {
        this._$_$PATROLCANTON = _$_$PATROLCANTON;
    }

    public List<DicCacheDao> get_$PATROLSITESTATUS() {
        if (_$PATROLSITESTATUS == null) {
            return new ArrayList<>();
        }
        return _$PATROLSITESTATUS;
    }

    public void set_$PATROLSITESTATUS(RealmList<DicCacheDao> _$PATROLSITESTATUS) {
        this._$PATROLSITESTATUS = _$PATROLSITESTATUS;
    }

    public List<DicCacheDao> get_$_$PATROLSITELEVEL() {
        if (_$_$PATROLSITELEVEL == null) {
            return new ArrayList<>();
        }
        return _$_$PATROLSITELEVEL;
    }

    public void set_$_$PATROLSITELEVEL(RealmList<DicCacheDao> _$_$PATROLSITELEVEL) {
        this._$_$PATROLSITELEVEL = _$_$PATROLSITELEVEL;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<DicCacheDao> get_$PATROLHD_STATUS10() {
        return _$PATROLHD_STATUS10;
    }

    public void set_$PATROLHD_STATUS10(RealmList<DicCacheDao> _$PATROLHD_STATUS10) {
        this._$PATROLHD_STATUS10 = _$PATROLHD_STATUS10;
    }

    public List<DicCacheDao> get_$PATROLHD_DEVICE_TYPE51() {
        return _$PATROLHD_DEVICE_TYPE51;
    }

    public void set_$PATROLHD_DEVICE_TYPE51(RealmList<DicCacheDao> _$PATROLHD_DEVICE_TYPE51) {
        this._$PATROLHD_DEVICE_TYPE51 = _$PATROLHD_DEVICE_TYPE51;
    }

    public List<DicCacheDao> get_$PATROLGRID_COUNT_OBJECT108() {
        return _$PATROLGRID_COUNT_OBJECT108;
    }

    public void set_$PATROLGRID_COUNT_OBJECT108(RealmList<DicCacheDao> _$PATROLGRID_COUNT_OBJECT108) {
        this._$PATROLGRID_COUNT_OBJECT108 = _$PATROLGRID_COUNT_OBJECT108;
    }

    public List<DicCacheDao> get_$SYS_COMMONDEFAULT_ISNO178() {
        return _$SYS_COMMONDEFAULT_ISNO178;
    }

    public void set_$SYS_COMMONDEFAULT_ISNO178(RealmList<DicCacheDao> _$SYS_COMMONDEFAULT_ISNO178) {
        this._$SYS_COMMONDEFAULT_ISNO178 = _$SYS_COMMONDEFAULT_ISNO178;
    }

    public List<DicCacheDao> get_$PATROLPLANPRE_TYPE50() {
        return _$PATROLPLANPRE_TYPE50;
    }

    public void set_$PATROLPLANPRE_TYPE50(RealmList<DicCacheDao> _$PATROLPLANPRE_TYPE50) {
        this._$PATROLPLANPRE_TYPE50 = _$PATROLPLANPRE_TYPE50;
    }

    public List<DicCacheDao> get_$PATROLHD_LEVEL323() {
        return _$PATROLHD_LEVEL323;
    }

    public void set_$PATROLHD_LEVEL323(RealmList<DicCacheDao> _$PATROLHD_LEVEL323) {
        this._$PATROLHD_LEVEL323 = _$PATROLHD_LEVEL323;
    }

    public List<DicCacheDao> get_$SYSTEMFILE_HOSTTYPE75() {
        return _$SYSTEMFILE_HOSTTYPE75;
    }

    public void set_$SYSTEMFILE_HOSTTYPE75(RealmList<DicCacheDao> _$SYSTEMFILE_HOSTTYPE75) {
        this._$SYSTEMFILE_HOSTTYPE75 = _$SYSTEMFILE_HOSTTYPE75;
    }
}
