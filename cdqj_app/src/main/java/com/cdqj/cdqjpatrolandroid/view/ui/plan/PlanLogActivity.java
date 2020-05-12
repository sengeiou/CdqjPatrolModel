package com.cdqj.cdqjpatrolandroid.view.ui.plan;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ConvertUtils;
import com.cdqj.cdqjpatrolandroid.base.BaseRecyclerView;
import com.cdqj.cdqjpatrolandroid.comstomview.recyclerview.SpacesItemDecoration;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.adapter.PlanLogListAdapter;
import com.cdqj.cdqjpatrolandroid.base.BaseActivity;
import com.cdqj.cdqjpatrolandroid.bean.PlanLogBean;
import com.cdqj.cdqjpatrolandroid.comstomview.StateView;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.presenter.PlanLogPresenter;
import com.cdqj.cdqjpatrolandroid.view.i.IPlanLogView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 计划操作日志
 *
 * @author lyf
 * @date 2018年10月16日 16:43:16
 */
public class PlanLogActivity extends BaseActivity<PlanLogPresenter> implements IPlanLogView {

    BaseRecyclerView planLogListRv;

    /**
     * 计划ID
     */
    private String planId;

    private List<PlanLogBean> list;

    private PlanLogListAdapter adapter;

    @Override
    public void initRefresh() {
        mPresenter.getPlanLogList(page, rows, false, planId);
    }

    @Override
    public void initLoadMore() {
        mPresenter.getPlanLogList(page, rows, false, planId);
    }

    @Override
    protected PlanLogPresenter createPresenter() {
        return new PlanLogPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_plan_log;
    }

    @Override
    protected void findView() {
        planLogListRv = findViewById(R.id.plan_log_list_rv);
    }

    @Override
    protected String getTitleText() {
        return "日志列表";
    }

    /**
     * 界面初始化
     */
    @SuppressLint("InflateParams")
    @Override
    public void initView() {
        mStateView = StateView.inject(planLogListRv);
        mStateView.setOnRetryClickListener(() -> {
            page = 1;
            mPresenter.getPlanLogList(page, rows, true, planId);
        });

        planId = getIntent().getStringExtra("planId");
        mPresenter = new PlanLogPresenter(this);

        planLogListRv.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(10)));
        planLogListRv.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new PlanLogListAdapter(R.layout.cdqj_patrol_plan_log_item, list, this);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.bindToRecyclerView(planLogListRv);
        mPresenter.getPlanLogList(page, rows, true, planId);

    }

    @Override
    public void onGetPlanLogListSuccess(BaseGetResponse<PlanLogBean> body, boolean flag) {
        if (flag) {
            int total = body.getTotal();
            if (total != 0) {
                if (total % rows == 0) {
                    maxPage = total / rows;
                } else {
                    maxPage = total / rows + 1;
                }
            }
        }
        setAdapter(body.getRows());
        if (adapter.getData().size() == 0) {
            mStateView.showEmpty();
        } else {
            mStateView.showContent();
        }
    }

    private void setAdapter(List<PlanLogBean> list) {
        if (page == 1) {
            adapter.setNewData(list);
        } else {
            adapter.addData(list);
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
        adapter.setNewData(new ArrayList<>());
        baseOnFailure(msg, true);
    }
}
