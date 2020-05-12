package com.cdqj.cdqjpatrolandroid.utils;

import com.cdqj.cdqjpatrolandroid.comstomview.dropdownmenu.SelectSpinnerBean;
import com.cdqj.cdqjpatrolandroid.bean.DicCacheDao;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 工单基础数据获取工具
 * @author lyf
 */
public class OrderDataUtils {

    /**
     * 获取隐患类型筛选列表 隐患类型
     */
    public static ArrayList<SelectSpinnerBean> getHdCombobox(Realm jrealm) {
        ArrayList<SelectSpinnerBean> hdTypes = new ArrayList<>();
        jrealm = Realm.getDefaultInstance();
        RealmResults<DicCacheDao> dicCacheDaos = jrealm.where(DicCacheDao.class).equalTo("moduleCode", "HD_DEVICE_TYPE").findAll();
        for (DicCacheDao patrolHdType : dicCacheDaos) {
            hdTypes.add(new SelectSpinnerBean(patrolHdType.getDicName(), patrolHdType.getDicCode()));
        }
        return hdTypes;
    }

    /**
     * 获取数据库储存的字典表数据 隐患级别
     */
    public static ArrayList<SelectSpinnerBean> getHdLevel(Realm jrealm) {
        ArrayList<SelectSpinnerBean> hdLevels = new ArrayList<>();
        jrealm = Realm.getDefaultInstance();
        RealmResults<DicCacheDao> dicCacheDaos = jrealm.where(DicCacheDao.class).equalTo("moduleCode", "HD_LEVEL").findAll();
        for (DicCacheDao dicCacheDao : dicCacheDaos) {
            hdLevels.add(new SelectSpinnerBean(dicCacheDao.getDicName(), dicCacheDao.getDicCode()));
        }
        return hdLevels;
    }

    /**
     * 获取隐患类型筛选列表 工单类型
     */
    public static ArrayList<SelectSpinnerBean> getCombobox(Realm jrealm) {
        ArrayList<SelectSpinnerBean> types = new ArrayList<>();
        jrealm = Realm.getDefaultInstance();
        RealmResults<DicCacheDao> dicCacheDaos = jrealm.where(DicCacheDao.class).equalTo("moduleCode", "RELEVANCE_TYPE").findAll();
        for (DicCacheDao patrolHdType : dicCacheDaos) {
            types.add(new SelectSpinnerBean(patrolHdType.getDicName(), patrolHdType.getDicCode()));
        }
        return types;
    }

    /**
     * 获取数据库储存的字典表数据 工单级别
     */
    public static ArrayList<SelectSpinnerBean> getLevel(Realm jrealm) {
        ArrayList<SelectSpinnerBean> levels = new ArrayList<>();
        jrealm = Realm.getDefaultInstance();
        RealmResults<DicCacheDao> dicCacheDaos = jrealm.where(DicCacheDao.class).equalTo("moduleCode", "ORDER_LEVEL").findAll();
        for (DicCacheDao dicCacheDao : dicCacheDaos) {
            levels.add(new SelectSpinnerBean(dicCacheDao.getDicName(), dicCacheDao.getDicCode()));
        }
        return levels;
    }
}
