package com.cdqj.cdqjpatrolandroid.view.ui.order;


import com.blankj.utilcode.util.ObjectUtils;
import com.cdqj.cdqjpatrolandroid.comstomview.dropdownmenu.SelectSpinnerBean;
import com.cdqj.cdqjpatrolandroid.base.BaseFragment;
import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.bean.DicCacheDao;

import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmResults;

public abstract class OrderBaseFragment<T extends BasePresenter> extends BaseFragment {
    public ArrayList<SelectSpinnerBean> types = new ArrayList<>();
    public ArrayList<SelectSpinnerBean> levels = new ArrayList<>();
    public ArrayList<SelectSpinnerBean> hdTypes = new ArrayList<>();
    public ArrayList<SelectSpinnerBean> hdLevels = new ArrayList<>();
    public ArrayList<SelectSpinnerBean> areas = new ArrayList<>();
    Realm jrealm;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            getLevel();
            getCombobox();
            getHdCombobox();
            getHdLevel();
        }
    }

    /**
     * 获取隐患类型筛选列表
     */
    private void getCombobox() {
        if (!types.isEmpty()) {
            return;
        }
        jrealm = Realm.getDefaultInstance();
        RealmResults<DicCacheDao> dicCacheDaos = jrealm.where(DicCacheDao.class).equalTo("moduleCode", "RELEVANCE_TYPE").findAll();
        for (DicCacheDao patrolHdType : dicCacheDaos) {
            types.add(new SelectSpinnerBean(patrolHdType.getDicName(), patrolHdType.getDicCode()));
        }
    }

    /**
     * 获取数据库储存的字典表数据
     */
    private void getLevel() {
        if (!levels.isEmpty()){
            return;
        }
        jrealm = Realm.getDefaultInstance();
        RealmResults<DicCacheDao> dicCacheDaos = jrealm.where(DicCacheDao.class).equalTo("moduleCode", "ORDER_LEVEL").findAll();
        for (DicCacheDao dicCacheDao : dicCacheDaos) {
            levels.add(new SelectSpinnerBean(dicCacheDao.getDicName(), dicCacheDao.getDicCode()));
        }
    }

    /**
     * 获取隐患类型筛选列表
     */
    private void getHdCombobox() {
        if (!hdTypes.isEmpty()) {
            return;
        }
        jrealm = Realm.getDefaultInstance();
        RealmResults<DicCacheDao> dicCacheDaos = jrealm.where(DicCacheDao.class).equalTo("moduleCode", "HD_DEVICE_TYPE").findAll();
        for (DicCacheDao patrolHdType : dicCacheDaos) {
            hdTypes.add(new SelectSpinnerBean(patrolHdType.getDicName(), patrolHdType.getDicCode()));
        }
    }

    /**
     * 获取数据库储存的字典表数据
     */
    private void getHdLevel() {
        if (!hdLevels.isEmpty()){
            return;
        }
        jrealm = Realm.getDefaultInstance();
        RealmResults<DicCacheDao> dicCacheDaos = jrealm.where(DicCacheDao.class).equalTo("moduleCode", "HD_LEVEL").findAll();
        for (DicCacheDao dicCacheDao : dicCacheDaos) {
            hdLevels.add(new SelectSpinnerBean(dicCacheDao.getDicName(), dicCacheDao.getDicCode()));
        }
    }

    @Override
    public void onDestroy() {
        if (ObjectUtils.isNotEmpty(jrealm)) {
            jrealm.close();
        }
        super.onDestroy();
    }
}
