package com.cdqj.cdqjpatrolandroid.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdqj.cdqjpatrolandroid.comstomview.SimpleToolbar;
import com.cdqj.cdqjpatrolandroid.comstomview.loadingdialog.CustomProgressDialog;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.comstomview.StateView;
import com.cdqj.cdqjpatrolandroid.event.NetworkChangeEvent;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.annotation.Nullable;

import com.cdqj.cdqjpatrolandroid.R;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.util.Objects;


/**
 * Created by lyf on 2018/11/14 13:41
 *
 * @author lyf
 * desc：Fragment的基类
 */
public abstract class BaseFragment<T extends BasePresenter> extends BaseLazyLoadFragment {
    protected T mPresenter;
    /**
     * 用于显示加载中、网络异常，空布局、内容布局
     */
    protected StateView mStateView;
    protected Activity mActivity;
    protected ImmersionBar mImmersionBar;
    @Nullable
    protected SimpleToolbar titleToolbar;
    @Nullable
    protected SmartRefreshLayout myInfoRefreshLayout;

    private View rootView;

    private CustomProgressDialog progressDialog = null;

    private ZLoadingDialog dialog;

    /**
     * 分页数据
     */
    protected int page = 1, rows = 15, maxPage = 1;

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

    /**
     * StateView的根布局，默认是整个界面，如果需要变换可以重写此方法
     */
    public View getStateViewRoot() {
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();

        doMainId = PreferencesUtil.getInt(Constant.DOMAIN_NAME_ID);
    }

    public void baseShowProgress(String str) {
//        progressDialog = LoadingHelper.startProgressDialog(getActivity(), progressDialog, str);
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

    public void baseOnFailure(ExceptionHandle.ResponeThrowable msg , boolean isShowView) {
        if (isShowView && mStateView != null) {
            page = 1;
            mStateView.showRetry();
        }
        ToastBuilder.showShortError(msg.message);
    }

    public void baseOnFailure(ExceptionHandle.ResponeThrowable msg) {
        baseOnFailure(msg, false);
    }

    public void baseShowProgress() {
        baseShowProgress("正在加载...");
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (rootView == null) {
            rootView = inflater.inflate(provideContentViewId(), container, false);
            titleToolbar = rootView.findViewById(R.id.simple_toolbar);
            myInfoRefreshLayout = rootView.findViewById(R.id.my_info_refreshLayout);
            findView(rootView);
            mStateView = StateView.inject(getStateViewRoot());
            /*if (mStateView != null){
                mStateView.setLoadingResource(R.layout.page_loading);
                mStateView.setRetryResource(R.layout.page_net_error);
            }*/
            initImmersionBar();
            initRefreshLayout();
            initProgress();
            initView(rootView);
            setTitleText(getTitleText());
            initData();
            initListener();
        } else {
            initRefreshLayout();
            initProgress();
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    private void initProgress() {
        dialog = new ZLoadingDialog(Objects.requireNonNull(getActivity()));
        dialog.setLoadingBuilder(Z_TYPE.STAR_LOADING)//设置类型
                .setLoadingColor(ContextCompat.getColor(getActivity(), R.color.theme_one))//颜色
                .setHintText("加载中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(ContextCompat.getColor(getActivity(), R.color.theme_one))  // 设置字体颜色
//                .setDialogBackgroundColor(Color.parseColor("#CC111111")) // 设置背景色，默认白色
                .setDurationTime(0.5); // 设置动画时间百分比 - 0.5倍
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

    /**
     * 得到当前界面的布局文件id(由子类实现)
     *
     * @return viewId
     */
    protected abstract int provideContentViewId();
    /**
     * 当前界面子控件获取
     */
    protected abstract void findView(View rootView);

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
                .keyboardEnable(true)
                .statusBarDarkFont(true)
                .navigationBarWithKitkatEnable(false);
        mImmersionBar.init();
    }

    /**
     * 初始化一些view
     *
     * @param rootView rootView
     */
    public void initView(View rootView) {
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
     * 获取标题Title
     *
     * @return titleStr
     */
    protected abstract String getTitleText();

    private void setTitleText(String title) {
        if (TextUtils.isEmpty(title)) {
            return;
        }
        if (titleToolbar != null) {
            titleToolbar.setMainTitle(title);
        }
    }

    /**
     * 初始化数据
     */
    public void initData() {

    }

    /**
     * 设置listener的操作
     */
    public void initListener() {

    }

    /**
     * 用于创建Presenter和判断是否使用MVP模式(由子类实现)
     *
     * @return T
     */
    protected abstract T createPresenter();

    @Override
    protected void onFragmentFirstVisible() {
        //当第一次可见的时候，加载数据
        loadData();
    }

    /**
     * 加载数据
     */
    protected abstract void loadData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (mPresenter != null) {
            // mPresenter.detachView();
            mPresenter = null;
        }
        rootView = null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkChangeEvent(NetworkChangeEvent event) {

    }
}
