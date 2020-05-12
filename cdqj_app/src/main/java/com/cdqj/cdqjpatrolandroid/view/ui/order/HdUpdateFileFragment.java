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
import com.cdqj.cdqjpatrolandroid.bean.HdOrderBean;
import com.cdqj.cdqjpatrolandroid.bean.OrderSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.comstomview.StateView;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.presenter.HdDetailPresenter;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.view.i.IHdDetailView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyf on 2018/9/28 09:40
 *
 * @author lyf
 * desc：隐患上报内容Fragment
 */
@SuppressLint("ValidFragment")
public class HdUpdateFileFragment extends BaseFragment<HdDetailPresenter> implements IHdDetailView {

    BaseRecyclerView hdUpdateRv;

    private OrderSuperviseReportAdapter rvAdapter;
    private List<OrderSuperviseReportBean> beans;
    private HdOrderBean bean;
    private int position;

    public HdUpdateFileFragment(List<OrderSuperviseReportBean> beans, HdOrderBean bean, int position) {
        this.beans = ObjectUtils.isEmpty(beans) ? new ArrayList<>() : beans;
        this.bean = ObjectUtils.isEmpty(bean) ? new HdOrderBean() : bean;
        this.position = position;
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
    @Override
    public void initView(View view1) {
        mStateView = StateView.inject(hdUpdateRv);
        mStateView.setOnRetryClickListener(() -> mPresenter.getHdDetailUpdateList(bean.getHdId() + ""));
        hdUpdateRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAdapter = new OrderSuperviseReportAdapter(
                R.layout.cdqj_patrol_order_supervise_report_item, beans, getActivity(), bean.getHdAddress(), "隐患");
        rvAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        rvAdapter.bindToRecyclerView(hdUpdateRv);
    }

    /**
     * 数据初始化
     */
    @Override
    public void initData() {

    }

    @Override
    protected HdDetailPresenter createPresenter() {
        return new HdDetailPresenter(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onGetHdDetailUpdateListSuccess(BasePostResponse<List<List<OrderSuperviseReportBean>>> basePostResponse) {
        if (basePostResponse.isSuccess()) {
            if (position == 0) {
                List<OrderSuperviseReportBean> beansAll = new ArrayList<>();
                for (int i = 0; i < basePostResponse.getObj().size(); i++) {
                    beansAll.addAll(basePostResponse.getObj().get(i));
                }
                rvAdapter.setNewData(beansAll);
            } else {
                rvAdapter.setNewData(basePostResponse.getObj().get(position));
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
        baseOnFailure(msg, true);
    }
}
