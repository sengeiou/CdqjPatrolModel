package com.cdqj.cdqjpatrolandroid.view.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.cdqj.cdqjpatrolandroid.base.BaseActivity;
import com.cdqj.cdqjpatrolandroid.bean.TabEntity;
import com.cdqj.cdqjpatrolandroid.comstomview.NoScrollViewPager;
import com.cdqj.cdqjpatrolandroid.comstomview.appsetting.AppSettingsDialog;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmDialogListener;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmSelectDialog;
import com.cdqj.cdqjpatrolandroid.config.CdqjInitDataConfig;
import com.cdqj.cdqjpatrolandroid.config.GlobalConfig;
import com.cdqj.cdqjpatrolandroid.event.EventGpsBean;
import com.cdqj.cdqjpatrolandroid.event.MsgEvent;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.listener.PermissionListener;
import com.cdqj.cdqjpatrolandroid.utils.AnimUtils;
import com.cdqj.cdqjpatrolandroid.utils.BarOtherUtils;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.bean.DicCacheDao;
import com.cdqj.cdqjpatrolandroid.bean.DicCacheDaoData;
import com.cdqj.cdqjpatrolandroid.bean.LoginResultBean;
import com.cdqj.cdqjpatrolandroid.bean.BasicGridBean;
import com.cdqj.cdqjpatrolandroid.bean.PatrolHdType;
import com.cdqj.cdqjpatrolandroid.bean.UserCom;
import com.cdqj.cdqjpatrolandroid.comstomview.fragment.TabPagerAdapter;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;
import com.cdqj.cdqjpatrolandroid.http.PatrolApiService;
import com.cdqj.cdqjpatrolandroid.http.RetrofitManager;
import com.cdqj.cdqjpatrolandroid.presenter.PatrolMianPresenter;
import com.cdqj.cdqjpatrolandroid.service.LocationTraceService;
import com.cdqj.cdqjpatrolandroid.service.LocationUpdateService;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.utils.TransformUtils;
import com.cdqj.cdqjpatrolandroid.service.LocationService;
import com.cdqj.cdqjpatrolandroid.version.CancelReceiver;
import com.cdqj.cdqjpatrolandroid.version.VersionHelper;
import com.cdqj.cdqjpatrolandroid.view.i.IPatrolMainView;
import com.daimajia.androidanimations.library.Techniques;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 千嘉巡线主界面
 *
 * @author lyf
 * 时间 2018年7月24日 16:17:19
 */
public class CdqjMainActivity extends BaseActivity<PatrolMianPresenter> implements IPatrolMainView {

    NoScrollViewPager cdqjMainVp;
    CommonTabLayout cdqjMainTab;

    Realm realm;
    private LoginResultBean userInfo;
    /**
     * 标题
     */
    private String[] mTitles = {"工单", "计划", "地图", "台帐", "我的"};
    /**
     * 图标
     */
    private int[] mIconUnSelectIds = {
            R.mipmap.icon_work_order, R.mipmap.icon_task,
            R.mipmap.icon_map, R.mipmap.icon_list,
            R.mipmap.icon_mine};
    /**
     * 选择图标
     */
    private int[] mIconSelectIds = {
            R.mipmap.icon_work_order_select, R.mipmap.icon_task_select,
            R.mipmap.icon_map_select, R.mipmap.icon_list_select,
            R.mipmap.icon_mine_select};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private FragmentManager fragmentManager;

    /**
     * 是否显示底部导航菜单
     */
    private boolean isShowBottomBar;

    public CancelReceiver cancelReceiver;

    /**
     * 在Activity中，我们通过ServiceConnection接口来取得建立连接与连接意外丢失的回调
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 建立连接
            // 获取服务的操作对象
            LocationService.LocationBinder binder = (LocationService.LocationBinder) service;
            // 获取到的Service即Location
            binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 连接断开
        }
    };
    private ServiceConnection serviceConnectionUpdate = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 建立连接
            // 获取服务的操作对象
            LocationUpdateService.LocationUpdateBinder binder = (LocationUpdateService.LocationUpdateBinder) service;
            // 获取到的Service即Location
            binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 连接断开
        }
    };
    private ServiceConnection serviceConnectionTrace = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 建立连接
            // 获取服务的操作对象
            LocationTraceService.LocationTraceBinder binder = (LocationTraceService.LocationTraceBinder) service;
            // 获取到的Service即Location
            binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 连接断开
        }
    };
    OrderListFragment orderListFragment;
    PlanListFragment planListFragment;
    MapFragment mapFragment;
    LedgerListFragment ledgerListFragment;
    MyFragment myFragment;

    @Override
    public void initView() {
        realm = Realm.getDefaultInstance();
        userInfo = getIntent().getParcelableExtra("userInfo");
        cancelReceiver = new CancelReceiver();
        IntentFilter intentFilterT = new IntentFilter();
        intentFilterT.addAction(CancelReceiver.ACTION);
        intentFilterT.addAction(CancelReceiver.ACTION_INSTALL_APP);
        registerReceiver(cancelReceiver, intentFilterT);

        setPermission();
    }

    private void setPermission() {
        // 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            OtherUtil.ignoreBatteryOptimization(this);
            // 权限获取
            requestRuntimePermission(com.cdqj.cdqjpatrolandroid.util.Permission.BAIDU_PHONE_ALL, new PermissionListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onGranted() {

                }

                @Override
                public void onDenied(List<String> deniedPermissions) {
//                ToastBuilder.showShortWarning(R.string.rationale_ask);
                    for (String str : deniedPermissions) {
                        if (Manifest.permission.ACCESS_COARSE_LOCATION.equals(str)) {// 定位权限
                            new AppSettingsDialog.Builder(CdqjMainActivity.this)
                                    .setRationale("定位权限未授予将导致APP地图轨迹等无法使用，\n" +
                                            "是否手动进入权限设置界面设置？").build().show();
                        } else if (Manifest.permission.CAMERA.equals(str)) {// 相机权限
                            new AppSettingsDialog.Builder(CdqjMainActivity.this)
                                    .setRationale("相机权限未授予将导致APP图片相关功能无法使用，\n" +
                                            "是否手动进入权限设置界面设置？").build().show();
                        } else if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(str)) {// 存储卡权限
                            new AppSettingsDialog.Builder(CdqjMainActivity.this)
                                    .setRationale("访问数据权限未授予将导致APP数据无法正常访问，\n" +
                                            "是否手动进入权限设置界面设置？").build().show();
                        }
                    }
                }
            });
        }

        bindService(new Intent(CdqjMainActivity.this, LocationService.class), serviceConnection, Service.BIND_AUTO_CREATE);
        bindService(new Intent(CdqjMainActivity.this, LocationUpdateService.class), serviceConnectionUpdate, Service.BIND_AUTO_CREATE);
        //bindService(new Intent(CdqjMainActivity.this, LocationTraceService.class), serviceConnectionTrace, Service.BIND_AUTO_CREATE);
    }

    @Override
    public void initData() {
        getDicCacheDao();
        getCombobox();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnSelectIds[i]));
        }
        orderListFragment = new OrderListFragment();
        planListFragment = new PlanListFragment();
        mapFragment = new MapFragment();
        ledgerListFragment = new LedgerListFragment();
        myFragment = new MyFragment();
        Bundle mainbundle = new Bundle();
        mainbundle.putParcelable("userInfo", userInfo);
        myFragment.setArguments(mainbundle);
        mapFragment.setArguments(mainbundle);
        mFragments.add(orderListFragment);
        mFragments.add(planListFragment);
        mFragments.add(mapFragment);
        //mFragments.add(new SimpleCardFragment());
        mFragments.add(ledgerListFragment);
        mFragments.add(myFragment);
        fragmentManager = getSupportFragmentManager();
        cdqjMainVp.setAdapter(new TabPagerAdapter(fragmentManager, mFragments, mTitles));
        // 默认加载所有(防止二次加载)
        cdqjMainVp.setOffscreenPageLimit(mFragments.size());
        setTabData();
        if (!CdqjInitDataConfig.isLib && ObjectUtils.isNotEmpty(userInfo) &&
                !AppUtils.getAppVersionName().equals(userInfo.getAppinfos().getVersion())) {
            new VersionHelper(this, userInfo, cancelReceiver).show();
            cdqjMainTab.showMsg(mTabEntities.size() - 1, 1);
            cdqjMainTab.setMsgMargin(mTabEntities.size() - 1, -ConvertUtils.dp2px(7), ConvertUtils.dp2px(2));
        }
    }

    @Override
    protected PatrolMianPresenter createPresenter() {
        return new PatrolMianPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_cdqj_main;
    }

    @Override
    protected void findView() {
        cdqjMainVp = findViewById(R.id.cdqj_main_vp);
        cdqjMainTab = findViewById(R.id.cdqj_main_tab);
    }

    @Override
    protected String getTitleText() {
        return null;
    }

    private void getDicCacheDao() {
        LogUtils.e(PreferencesUtil.getInt(Constant.SYSDIC_VERSION_PREFENCE, 0));
/*if (PreferencesUtil.getInt(.SYSDIC_VERSION_PREFENCE, 0) == .SYSDIC_VERSION) {
            return;
        }*/
        realm.beginTransaction();
        realm.where(DicCacheDao.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();
        RetrofitManager.getInstance().create(PatrolApiService.class)
                .getDicCacheDao()
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(new BaseSubscriber<BasePostResponse<DicCacheDaoData>>() {
                    @Override
                    public void onResult(BasePostResponse<DicCacheDaoData> postResponse) {
                        if (postResponse.isSuccess()) {
                            DicCacheDaoData obj = postResponse.getObj();
                            realm.executeTransactionAsync(realm -> {
                                realm.copyToRealmOrUpdate(obj.get_$PATROLHD_STATUS10());
                                realm.copyToRealmOrUpdate(obj.get_$PAT_LINE());
                                realm.copyToRealmOrUpdate(obj.get_$PATROLGRID_COUNT_OBJECT108());
                                realm.copyToRealmOrUpdate(obj.get_$PATROLHD_DEVICE_TYPE51());
                                realm.copyToRealmOrUpdate(obj.get_$PATROLHD_LEVEL323());
                                realm.copyToRealmOrUpdate(obj.get_$PATROLPLANPRE_TYPE50());
                                realm.copyToRealmOrUpdate(obj.get_$SYS_COMMONDEFAULT_ISNO178());
                                realm.copyToRealmOrUpdate(obj.get_$SYSTEMFILE_HOSTTYPE75());
                                realm.copyToRealmOrUpdate(obj.get_$_$PATROLSITELEVEL());
                                realm.copyToRealmOrUpdate(obj.get_$PATROLSITESTATUS());
                                realm.copyToRealmOrUpdate(obj.get_$_$PATROLCANTON());
                                realm.copyToRealmOrUpdate(obj.get_$RELEVANCE_TYPE());
                                realm.copyToRealmOrUpdate(obj.get_$_$ORDER_LEVEL());
                                realm.copyToRealmOrUpdate(obj.get_$_$ORDER_STATUS());
                                realm.copyToRealmOrUpdate(obj.get_$_$DATA_STATUS());
                                realm.copyToRealmOrUpdate(obj.getPROP_LX());
                                realm.copyToRealmOrUpdate(obj.getPROP_FJ());
                                realm.copyToRealmOrUpdate(obj.getPROP_FFXZ());
                                realm.copyToRealmOrUpdate(obj.getPROP_FSFS());
                                realm.copyToRealmOrUpdate(obj.getPROP_GC());
                                realm.copyToRealmOrUpdate(obj.getPROP_LQSB());
                                realm.copyToRealmOrUpdate(obj.getPROP_NAME());
                                realm.copyToRealmOrUpdate(obj.getPROP_YHLX());
                                realm.copyToRealmOrUpdate(obj.getPROP_JHYY());
                                realm.copyToRealmOrUpdate(obj.getCON_TYPE());
                            }, () -> {
                                LogUtils.e("onSuccess");
                                PreferencesUtil.putInt(Constant.SYSDIC_VERSION_PREFENCE, Constant.SYSDIC_VERSION);
                            }, error -> LogUtils.e(error.getMessage()));
                        } else {
                            showDownLoadData();
                        }
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        showDownLoadData();
                    }
                });
    }

    /**
     * 获取隐患类型筛选列表
     */
    public void getCombobox() {

 /* if (PreferencesUtil.getInt(.HDPATROL_VERSION_PREFENCE, 0) == .SYSDIC_VERSION) {
            return;
        }*/

        /*
         * 获取人员(先删除再请求添加)
         */
        realm.beginTransaction();
        realm.where(UserCom.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();
        RetrofitManager.getInstance().create(PatrolApiService.class)
                .getUser()
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(new BaseSubscriber<ArrayList<UserCom>>() {
                    @Override
                    public void onResult(ArrayList<UserCom> postResponse) {
                        realm.executeTransactionAsync(realm -> realm.copyToRealmOrUpdate(postResponse), () -> {
                            RealmResults<UserCom> hdLevel = realm.where(UserCom.class).findAll();
                            LogUtils.e(hdLevel.toString());
                        }, error -> LogUtils.e(error.getMessage()));
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        ToastBuilder.showShortError(e.getMessage());
                    }
                });

        /*
         * 获取工地上报片区数据
         */
        realm.beginTransaction();
        realm.where(PatrolHdType.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();
        RetrofitManager.getInstance().create(PatrolApiService.class)
                .getCombobox()
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(new BaseSubscriber<ArrayList<PatrolHdType>>() {
                    @Override
                    public void onResult(ArrayList<PatrolHdType> postResponse) {
                        for (PatrolHdType hdType : postResponse) {
                            hdType.setModuleCode("AREA");
                        }
                        realm.executeTransactionAsync(realm -> realm.copyToRealmOrUpdate(postResponse), () -> {
                            RealmResults<PatrolHdType> hdLevel = realm.where(PatrolHdType.class)
                                    .equalTo("moduleCode", "AREA").findAll();
                            LogUtils.e(hdLevel.toString());
                        }, error -> LogUtils.e(error.getMessage()));
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        ToastBuilder.showShortError(e.getMessage());
                    }
                });

        /*
         * 获取网格数据
         */
        realm.beginTransaction();
        realm.where(BasicGridBean.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();
        RetrofitManager.getInstance().create(PatrolApiService.class)
                .getMapBasic()
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(new BaseSubscriber<BaseGetResponse<BasicGridBean>>() {
                    @Override
                    public void onResult(BaseGetResponse<BasicGridBean> postResponse) {
                        realm.executeTransactionAsync(realm -> realm.copyToRealmOrUpdate(postResponse.getRows()), () -> {
                            RealmResults<BasicGridBean> grid = realm.where(BasicGridBean.class).findAll();
                            LogUtils.e(grid.toString());
                        }, error -> LogUtils.e(error.getMessage()));
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        LogUtils.d(e.message);
                        e.printStackTrace();
                    }
                });
    }

    private void setTabData() {
        cdqjMainTab.setTabData(mTabEntities);
        cdqjMainTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                cdqjMainVp.setCurrentItem(position, false);
                if (position == 2) {
                    isShowBottomBar = cdqjMainTab.getVisibility() != View.VISIBLE;
                    anim(isShowBottomBar);
                    EventBus.getDefault().post(new MsgEvent<>(3, true, new EventGpsBean(4)));
                }
                BarOtherUtils.changeStatusBarTextColor(CdqjMainActivity.this, position != 4);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        cdqjMainVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                cdqjMainTab.setCurrentTab(position);
                BarOtherUtils.changeStatusBarTextColor(CdqjMainActivity.this, position != 4);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        cdqjMainVp.setCurrentItem(2, false);
        cdqjMainTab.setVisibility(View.GONE);
    }

    /**
     * 底部导航栏动画
     *
     * @param flag 是否展示底部导航栏
     */
    private void anim(final boolean flag) {
        Techniques technique = flag ? Techniques.FadeInDown : Techniques.ZoomOutDown;
        if (flag) {
            AnimUtils.setAnima(technique, 200, cdqjMainTab, true);
        } else {
            cdqjMainTab.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        realm.close();
        unbindService(serviceConnection);
        unbindService(serviceConnectionUpdate);
        unregisterReceiver(cancelReceiver);
        //unbindService(serviceConnectionTrace);
        mPresenter.onUnsubscribe();
        super.onDestroy();
    }

    @Override
    public void onSubmitOffWorkSuccess(BasePostResponse<Object> basePostResponse) {
        if (basePostResponse.isSuccess()) {
            // 下班执行成功
            if (!CdqjInitDataConfig.isLib) {
                GlobalConfig.isDoWork = false;
                ToastBuilder.showShort("下班成功，即将退出APP");
                new Handler().postDelayed(AppUtils::exitApp, 1000);
            } else {
                finish();
            }
        } else {
            ToastBuilder.showShortWarning(basePostResponse.getMsg());
        }
    }

    @Override
    public void showProgress() {
        baseShowProgress();
    }

    @Override
    public void showProgress(String str) {
        baseShowProgress(str);
    }

    @Override
    public void hideProgress() {
        baseHideProgress();
    }

    @Override
    public void onSuccess(String msg) {

    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable msg) {
        baseOnFailure(msg);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataChange(MsgEvent<EventGpsBean> msgEvent) {
        if (msgEvent.getTag() != 3) {
            return;
        }
        if (msgEvent.getData().getTag() != 1) {
            return;
        }
        // isShowBottomBar = !isShowBottomBar;
        cdqjMainTab.setCurrentTab(msgEvent.getData().getPosition());
        cdqjMainVp.setCurrentItem(msgEvent.getData().getPosition(), false);
        BarOtherUtils.changeStatusBarTextColor(CdqjMainActivity.this, msgEvent.getData().getTag() != 4);
        isShowBottomBar = true;
        anim(isShowBottomBar);
    }

    @Override
    public void onBackPressed() {
        if (cdqjMainTab.getCurrentTab() == 2) {
            // 如果是地图界面
            EventBus.getDefault().post(new MsgEvent<>(3, new EventGpsBean(2)));
        } else {
            // 如果非地图界面
            back();
        }
    }

    /**
     * 返回
     */
    public void back() {
        if (CdqjInitDataConfig.isLib) {
            if (GlobalConfig.isDoWork) {
                // 上班状态，执行下班
                mPresenter.submitOffWork(PreferencesUtil.getInt(Constant.USER_ID));
            } else {
                finish();
            }
            return;
        }
        new ConfirmSelectDialog(this)
                .setTitleStr("提示")
                .setContentStr(GlobalConfig.isDoWork ?
                        "当前处于上班状态，确定直接退出APP？" : "确定退出APP")
                .setYesStr(GlobalConfig.isDoWork ?
                        "下班并退出" : "确定")
                .setNoStr("取消")
                .setListener(new ConfirmDialogListener() {
                    @Override
                    public void onYesClick() {
                        if (GlobalConfig.isDoWork) {
                            // 上班状态，执行下班
                            mPresenter.submitOffWork(PreferencesUtil.getInt(Constant.USER_ID));
                        } else {
                            finish();
                        }
                        //new Handler().postDelayed(AppUtils::exitApp, 500);
                    }

                    @Override
                    public void onNoClick() {

                    }
                })
                .initView().show();
    }

    private void showDownLoadData() {
        new ConfirmSelectDialog(this)
                .setTitleStr("提示")
                .setContentStr("后台基础数据加载失败(会导致部分数据无法正常使用)，是否重新加载")
                .setYesStr("是")
                .setNoStr("取消")
                .setListener(new ConfirmDialogListener() {
                    @Override
                    public void onYesClick() {
                        getDicCacheDao();
                    }

                    @Override
                    public void onNoClick() {
                    }
                })
                .initView().show();
    }

}
