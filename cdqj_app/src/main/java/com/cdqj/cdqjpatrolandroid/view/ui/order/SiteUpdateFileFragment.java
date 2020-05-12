package com.cdqj.cdqjpatrolandroid.view.ui.order;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.blankj.utilcode.util.ObjectUtils;
import com.cdqj.cdqjpatrolandroid.base.BaseRecyclerView;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.adapter.OrderSuperviseReportAdapter;
import com.cdqj.cdqjpatrolandroid.base.BaseFragment;
import com.cdqj.cdqjpatrolandroid.bean.SiteBean;
import com.cdqj.cdqjpatrolandroid.bean.OrderSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.comstomview.StateView;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.presenter.MapSiteDetailPresenter;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.view.i.IMapSiteDetailView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyf on 2018/9/28 09:40
 *
 * @author lyf
 * desc：工地上报内容Fragment
 */
@SuppressLint("ValidFragment")
public class SiteUpdateFileFragment extends BaseFragment<MapSiteDetailPresenter> implements IMapSiteDetailView {

    BaseRecyclerView hdUpdateRv;

    private OrderSuperviseReportAdapter rvAdapter;
    private List<OrderSuperviseReportBean> beans;
    private SiteBean bean;
    private int postion;

    public SiteUpdateFileFragment(List<OrderSuperviseReportBean> beans, SiteBean bean, int postion) {
        this.beans = ObjectUtils.isEmpty(beans) ? new ArrayList<>() : beans;
        this.bean = ObjectUtils.isEmpty(bean) ? new SiteBean() : bean;
        this.postion = postion;
    }

    @Override
    public void initView(View rootView) {
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_fragment_hd_update_file;
    }

    @Override
    protected void findView(View rootView) {
        hdUpdateRv = rootView.findViewById(R.id.hd_update_rv);
    }

    @Override
    protected String getTitleText() {
        return null;
    }

    /**
     * 界面初始化
     */
    @SuppressLint("InflateParams")
    private void initView() {
        mStateView = StateView.inject(hdUpdateRv);
        mStateView.setOnRetryClickListener(() -> {
            page = 1;
            mPresenter.getTaskReports(bean.getId() + "", true);
        });
        hdUpdateRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAdapter = new OrderSuperviseReportAdapter(
                R.layout.cdqj_patrol_order_supervise_report_item, beans, getActivity(), bean.getAddress(), "工地");
        rvAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);

        rvAdapter.bindToRecyclerView(hdUpdateRv);
        if (rvAdapter.getData().size() == 0) {
            mStateView.showEmpty();
        } else {
            mStateView.showContent();
        }
    }

    /**
     * 数据初始化
     */
    public void initData() {

    }

    @Override
    protected MapSiteDetailPresenter createPresenter() {
        return new MapSiteDetailPresenter(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onGetMapSiteDetailSuccess(BasePostResponse<List<List<OrderSuperviseReportBean>>> basePostResponse, boolean flag) {
        if (basePostResponse.isSuccess()) {
            if (postion == 0) {
                List<OrderSuperviseReportBean> beansAll = new ArrayList<>();
                for (int i = 0; i < basePostResponse.getObj().size(); i++) {
                    beansAll.addAll(basePostResponse.getObj().get(i));
                }
                rvAdapter.setNewData(beansAll);
            } else {
                rvAdapter.setNewData(basePostResponse.getObj().get(postion));
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
        rvAdapter.setNewData(new ArrayList<>());
        baseOnFailure(msg);
    }
}
