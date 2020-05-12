package com.cdqj.cdqjpatrolandroid.utils.sql;

import com.blankj.utilcode.util.ObjectUtils;
import com.cdqj.cdqjpatrolandroid.bean.DicCacheDao;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 版权：成都千嘉科技公司所有
 *
 * @author lyf
 * 创建日期： 2019/6/25
 * 创建时间： 14:31
 * 描述：获取本地数据库离线数据（字典表数据等）
 */
public class SqlDataPresenter {
    
    public static List<DicCacheDao> getHdLevel(Realm realm) {
        RealmResults<DicCacheDao> hdLevel = realm.where(DicCacheDao.class).equalTo("moduleCode", "HD_LEVEL").findAll();
        if (ObjectUtils.isNotEmpty(hdLevel)) {
            return realm.copyFromRealm(hdLevel);
        }
        return new ArrayList<>();
    }

    public static List<DicCacheDao> getSiteLevel(Realm realm) {
        RealmResults<DicCacheDao> siteLevel = realm.where(DicCacheDao.class).equalTo("moduleCode", "SITE_LEVEL").findAll();
        if (ObjectUtils.isNotEmpty(siteLevel)) {
            return realm.copyFromRealm(siteLevel);
        }
        return new ArrayList<>();
    }

    public static List<DicCacheDao> getCanton(Realm realm) {
        RealmResults<DicCacheDao> canton = realm.where(DicCacheDao.class).equalTo("moduleCode", "CANTON").findAll();
        if (ObjectUtils.isNotEmpty(canton)) {
            return realm.copyFromRealm(canton);
        }
        return new ArrayList<>();
    }

    public static List<DicCacheDao> getHdDeviceType(Realm realm) {
        RealmResults<DicCacheDao> hdDeviceType = realm.where(DicCacheDao.class).equalTo("moduleCode", "HD_DEVICE_TYPE").findAll();
        if (ObjectUtils.isNotEmpty(hdDeviceType)) {
            return realm.copyFromRealm(hdDeviceType);
        }
        return new ArrayList<>();
    }

    /**
     * 隐患上报选择字段
     * @param realm realm
     * @return 类型 （占压 锈蚀 漏气）
     */
    public static List<DicCacheDao> getLxType(Realm realm) {
        RealmResults<DicCacheDao> lxType = realm.where(DicCacheDao.class).equalTo("moduleCode", "PROP_LX").findAll();
        if (ObjectUtils.isNotEmpty(lxType)) {
            return realm.copyFromRealm(lxType);
        }
        return new ArrayList<>();
    }

    /**
     * 隐患上报选择字段
     * @param realm realm
     * @return 管径
     */
    public static List<DicCacheDao> getFjType(Realm realm) {
        RealmResults<DicCacheDao> fjType = realm.where(DicCacheDao.class).equalTo("moduleCode", "PROP_FJ").findAll();
        if (ObjectUtils.isNotEmpty(fjType)) {
            return realm.copyFromRealm(fjType);
        }
        return new ArrayList<>();
    }

    /**
     * 隐患上报选择字段
     * @param realm realm
     * @return 防腐性质
     */
    public static List<DicCacheDao> getFfxzType(Realm realm) {
        RealmResults<DicCacheDao> ffxzType = realm.where(DicCacheDao.class).equalTo("moduleCode", "PROP_FFXZ").findAll();
        if (ObjectUtils.isNotEmpty(ffxzType)) {
            return realm.copyFromRealm(ffxzType);
        }
        return new ArrayList<>();
    }

    /**
     * 隐患上报选择字段
     * @param realm realm
     * @return 敷设方式
     */
    public static List<DicCacheDao> getFsfsType(Realm realm) {
        RealmResults<DicCacheDao> fsfsType = realm.where(DicCacheDao.class).equalTo("moduleCode", "PROP_FSFS").findAll();
        if (ObjectUtils.isNotEmpty(fsfsType)) {
            return realm.copyFromRealm(fsfsType);
        }
        return new ArrayList<>();
    }

    /**
     * 隐患上报选择字段
     * @param realm realm
     * @return 管材
     */
    public static List<DicCacheDao> getGcType(Realm realm) {
        RealmResults<DicCacheDao> gcType = realm.where(DicCacheDao.class).equalTo("moduleCode", "PROP_GC").findAll();
        if (ObjectUtils.isNotEmpty(gcType)) {
            return realm.copyFromRealm(gcType);
        }
        return new ArrayList<>();
    }

    /**
     * 隐患上报选择字段
     * @param realm realm
     * @return 漏气设备
     */
    public static List<DicCacheDao> getLqsbType(Realm realm) {
        RealmResults<DicCacheDao> lqsbType = realm.where(DicCacheDao.class).equalTo("moduleCode", "PROP_LQSB").findAll();
        if (ObjectUtils.isNotEmpty(lqsbType)) {
            return realm.copyFromRealm(lqsbType);
        }
        return new ArrayList<>();
    }

    /**
     * 隐患上报选择字段
     * @param realm realm
     * @return 设施类别
     */
    public static List<DicCacheDao> getNameType(Realm realm) {
        RealmResults<DicCacheDao> nameType = realm.where(DicCacheDao.class).equalTo("moduleCode", "PROP_NAME").findAll();
        if (ObjectUtils.isNotEmpty(nameType)) {
            return realm.copyFromRealm(nameType);
        }
        return new ArrayList<>();
    }

    /**
     * 隐患上报选择字段
     * @param realm realm
     * @return 隐患类型
     */
    public static List<DicCacheDao> getYhlxType(Realm realm) {
        RealmResults<DicCacheDao> yhlxType = realm.where(DicCacheDao.class).equalTo("moduleCode", "PROP_YHLX").findAll();
        if (ObjectUtils.isNotEmpty(yhlxType)) {
            return realm.copyFromRealm(yhlxType);
        }
        return new ArrayList<>();
    }

    /**
     * 工地上报选择字段
     * @param realm realm
     * @return 监护原因
     */
    public static List<DicCacheDao> getPropJhyy(Realm realm) {
        RealmResults<DicCacheDao> yhlxType = realm.where(DicCacheDao.class).equalTo("moduleCode", "PROP_JHYY").findAll();
        if (ObjectUtils.isNotEmpty(yhlxType)) {
            return realm.copyFromRealm(yhlxType);
        }
        return new ArrayList<>();
    }

    /**
     * 工地上报选择字段
     * @param realm realm
     * @return 施工类型
     */
    public static List<DicCacheDao> getConType(Realm realm) {
        RealmResults<DicCacheDao> yhlxType = realm.where(DicCacheDao.class).equalTo("moduleCode", "CON_TYPE").findAll();
        if (ObjectUtils.isNotEmpty(yhlxType)) {
            return realm.copyFromRealm(yhlxType);
        }
        return new ArrayList<>();
    }
}
