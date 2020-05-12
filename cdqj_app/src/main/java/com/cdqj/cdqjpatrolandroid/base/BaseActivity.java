package com.cdqj.cdqjpatrolandroid.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.cdqj.cdqjpatrolandroid.comstomview.SimpleToolbar;
import com.cdqj.cdqjpatrolandroid.comstomview.loadingdialog.CustomProgressDialog;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.util.OtherUtil;
import com.cdqj.cdqjpatrolandroid.comstomview.StateView;
import com.cdqj.cdqjpatrolandroid.event.NetworkChangeEvent;
import com.cdqj.cdqjpatrolandroid.listener.PermissionListener;
import com.cdqj.cdqjpatrolandroid.service.NetworkConnectChangedReceiver;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.view.ui.main.CdqjMainActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.noober.background.BackgroundLibrary;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.cdqj.cdqjpatrolandroid.R;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

/**
 * activity的基类
 */
public abstract class BaseActivity<T extends BasePresenter>  extends AppCompatActivity {

    /**
     * 默认检查网络状态
     */
    protected boolean mCheckNetWork = true;

    NetworkConnectChangedReceiver netWorkStateReceiver;

    View mTipView;
    WindowManager mWindowManager;
    WindowManager.LayoutParams mLayoutParams;
    protected T mPresenter;
    private static long mPreTime;
    /**
     * 对所有activity进行管理
     */
    private static Activity mCurrentActivity;
    public static List<Activity> mActivities = new LinkedList<>();
    protected Bundle savedInstanceState;
    protected StateView mStateView;
    public PermissionListener mPermissionListener;
    public ImmersionBar mImmersionBar;
    InputMethodManager inputManager;
    @Nullable
    protected SimpleToolbar titleToolbar;
    @Nullable
    protected SmartRefreshLayout myInfoRefreshLayout;

    /**
     * 分页数据
     */
    protected int page = 1, rows = 15, maxPage = 1;

    private CustomProgressDialog progressDialog = null;

    private ZLoadingDialog dialog;

    /**
     * 泸州ID 1
     *      1	3			2019/6/17 10:09:02
     *      2	1	SYSTEM	系统管理	2018/5/23 11:03:02
     *      3	2	泸州华润兴泸燃气	燃气公司	2018/12/13 16:57:27
     *      4	6681	合江星焰燃气		2019/6/17 10:09:09
     *
     *  其他
     *      大同，绵阳等
     */
    public int doMainId = 0;

    @SuppressLint("InflateParams")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        BackgroundLibrary.inject(this);

        super.onCreate(savedInstanceState);
        initTipView();

        doMainId = PreferencesUtil.getInt(Constant.DOMAIN_NAME_ID);

        EventBus.getDefault().register(this);

        // 始终竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        this.savedInstanceState = savedInstanceState;

        //初始化的时候将其添加到集合中
        synchronized (mActivities) {
            mActivities.add(this);
        }

        mPresenter = createPresenter();

        //子类不再需要设置布局ID，也不再需要使用ButterKnife.bind()
        setContentView(provideContentViewId());
        titleToolbar = findViewById(R.id.simple_toolbar);
        myInfoRefreshLayout = findViewById(R.id.my_info_refreshLayout);
        findView();


        mImmersionBar = ImmersionBar.with(this)
                .keyboardEnable(true)
                .statusBarDarkFont(true)
                .navigationBarWithKitkatEnable(false);
        mImmersionBar.init();   //所有子类都将继承这些相同的属性
        initRefreshLayout();
        initProgress();
        initView();
        setTitleText(getTitleText());
        initData();
        initListener();
    }

    private void initProgress() {
        dialog = new ZLoadingDialog(this);
        dialog.setLoadingBuilder(Z_TYPE.STAR_LOADING)//设置类型
                .setLoadingColor(ContextCompat.getColor(this, R.color.theme_one))//颜色
                .setHintText("加载中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(ContextCompat.getColor(this, R.color.theme_one))  // 设置字体颜色
//                .setDialogBackgroundColor(Color.parseColor("#CC111111")) // 设置背景色，默认白色
                .setDurationTime(0.5); // 设置动画时间百分比 - 0.5倍
    }

    @Override
    public void finish() {
        super.finish();
        //当提示View被动态添加后直接关闭页面会导致该View内存溢出，所以需要在finish时移除
        if (mTipView != null && mTipView.getParent() != null) {
            mWindowManager.removeView(mTipView);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkChangeEvent(NetworkChangeEvent event) {
        hasNetWork(event.isConnected);
    }

    private void hasNetWork(boolean has) {
        if (isCheckNetWork()) {
            if (has) {
                if (mTipView != null && mTipView.getParent() != null) {
                    mWindowManager.removeView(mTipView);
                }
            } else {
                if (mTipView.getParent() == null) {
                    mWindowManager.addView(mTipView, mLayoutParams);
                }
            }
        }
    }

    public void setCheckNetWork(boolean checkNetWork) {
        mCheckNetWork = checkNetWork;
    }

    public boolean isCheckNetWork() {
        return mCheckNetWork;
    }

    private void initTipView() {
        LayoutInflater inflater = getLayoutInflater();
        mTipView = inflater.inflate(R.layout.cdqj_patrol_layout_network_tip, null); //提示View布局
        mTipView.setOnClickListener(v -> startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS)));
        mWindowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        //使用非CENTER时，可以通过设置XY的值来改变View的位置
        mLayoutParams.gravity = Gravity.TOP;
        mLayoutParams.x = 0;
        mLayoutParams.y = ConvertUtils.dp2px(46);
    }

    public void baseShowProgress(String str) {
//        progressDialog = LoadingHelper.startProgressDialog(this, progressDialog, str);
        dialog.setHintText(str);
        dialog.show();
    }

    public void baseHideProgress() {
        if (myInfoRefreshLayout != null) {
            myInfoRefreshLayout.finishRefresh();
            myInfoRefreshLayout.finishLoadMore();
        }
//        progressDialog = LoadingHelper.stopProgressDialog(progressDialog);
        dialog.dismiss();
    }

    private void initRefreshLayout() {
        if (myInfoRefreshLayout != null) {
            myInfoRefreshLayout.setOnRefreshListener(refreshLayout -> {
                myInfoRefreshLayout.autoRefresh();
                page = 1;
                initRefresh();
            });
            myInfoRefreshLayout.setOnLoadMoreListener(refreshlayout -> {
                if (maxPage <= page) {
                    refreshlayout.finishLoadMoreWithNoMoreData();
                    refreshlayout.setNoMoreData(false);
                } else {
                    page++;
                    initLoadMore();
                }
            });
        }
    }

    public void baseOnFailure(ExceptionHandle.ResponeThrowable msg) {
        baseOnFailure(msg, false);
    }

    public void baseShowProgress() {
        baseShowProgress("正在加载...");
    }

    public void baseOnFailure(ExceptionHandle.ResponeThrowable msg , boolean isShowView) {
        if (isShowView && mStateView != null) {
            page = 1;
            mStateView.showRetry();
        }
        ToastBuilder.showShortError(msg.message);
    }

    private void setTitleText(String title) {
        if (TextUtils.isEmpty(title)) {
            return;
        }
        if (titleToolbar != null) {
            titleToolbar.setMainTitle(title);
            setTitleBar(titleToolbar);
        }
    }
    public void setTitleBar(View titleBar){
        mImmersionBar.titleBar(titleBar).keyboardEnable(true).init();
    }
    public boolean enableSlideClose() {
        return true;
    }

    public void initView() {
    }

    public void initData() {
    }

    public void initListener() {
    }

    /**
     * 界面下拉刷新
     */
    public void initRefresh() {
    }

    /**
     * 界面上拉加载
     *
     */
    public void initLoadMore() {
    }

    /**
     * 用于创建Presenter和判断是否使用MVP模式(由子类实现)
     * @return T
     */
    protected abstract T createPresenter();

    /**
     * 得到当前界面的布局文件id(由子类实现)
     * @return ViewId
     */
    protected abstract int provideContentViewId();

    /**
     * 当前界面子控件获取
     */
    protected abstract void findView();

    /**
     * 获取标题Title
     * @return TitleStr
     */
    protected abstract String getTitleText();
    @Override
    protected void onResume() {
        super.onResume();
        mCurrentActivity = this;
        if (netWorkStateReceiver == null) {
            netWorkStateReceiver = new NetworkConnectChangedReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkStateReceiver, filter);
        //在无网络情况下打开APP时，系统不会发送网络状况变更的Intent，需要自己手动检查
        hasNetWork(NetworkUtils.isConnected());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentActivity = null;
        unregisterReceiver(netWorkStateReceiver);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

        //销毁的时候从集合中移除
        synchronized (mActivities) {
            mActivities.remove(this);
        }

        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
    }

    /**
     * 隐藏软键盘
     */
    public void hideKeyboard() {
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null) {
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }

        }
    }
    /**
     * 退出应用的方法
     */
    public static void exitApp() {

        ListIterator<Activity> iterator = mActivities.listIterator();

        while (iterator.hasNext()) {
            Activity next = iterator.next();
            next.finish();
        }
    }

    public static Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    /**
     * 统一退出控制
     */
    @Override
    public void onBackPressed() {
        if (mCurrentActivity instanceof CdqjMainActivity){
            OtherUtil.back(this);
            return;
        }
        super.onBackPressed();// finish()
    }
    /**
     * 申请运行时权限
     */
    public void requestRuntimePermission(String[] permissions, PermissionListener permissionListener) {
        mPermissionListener = permissionListener;
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }

        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 1);
        } else {
            permissionListener.onGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissions.add(permission);
                        }
                    }
                    if (deniedPermissions.isEmpty()) {
                        mPermissionListener.onGranted();
                    } else {
                        mPermissionListener.onDenied(deniedPermissions);
                    }
                }
                break;
                default:break;
        }
    }
    public void onBack(View v){
        finish();
    }
}
