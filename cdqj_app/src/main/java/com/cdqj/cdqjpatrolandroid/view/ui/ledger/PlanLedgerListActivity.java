package com.cdqj.cdqjpatrolandroid.view.ui.ledger;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ConvertUtils;
import com.cdqj.cdqjpatrolandroid.base.BaseRecyclerView;
import com.cdqj.cdqjpatrolandroid.comstomview.recyclerview.SpacesItemDecoration;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.adapter.PlanMyListAdapter;
import com.cdqj.cdqjpatrolandroid.base.BaseActivity;
import com.cdqj.cdqjpatrolandroid.bean.PlanListBean;
import com.cdqj.cdqjpatrolandroid.comstomview.StateView;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.presenter.PlanLedgerListPresenter;
import com.cdqj.cdqjpatrolandroid.utils.PatrolEnterPointActivity;
import com.cdqj.cdqjpatrolandroid.view.i.IPlanLedgerListView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 计划台帐（周期计划执行列表）
 *
 * @author lyf
 * @date 2018年10月15日 17:57:52
 */
public class PlanLedgerListActivity extends BaseActivity<PlanLedgerListPresenter> implements IPlanLedgerListView {

    BaseRecyclerView planLedgerListRv;

    /**
     * 计划ID
     */
    private String planId;

    private List<PlanListBean> list;

    private PlanMyListAdapter adapter;

    @Override
    public void initRefresh() {
        mPresenter.getPlanLedgerList(page, rows, planId, false);
    }

    @Override
    public void initLoadMore() {
        mPresenter.getPlanLedgerList(page, rows, planId, false);
    }

    @Override
    protected PlanLedgerListPresenter createPresenter() {
        return new PlanLedgerListPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_plan_ledger_list;
    }

    @Override
    protected void findView() {
        planLedgerListRv = findViewById(R.id.plan_ledger_list_rv);
    }

    @Override
    protected String getTitleText() {
        return "计划列表";
    }

    /**
     * 界面初始化
     */
    @SuppressLint("InflateParams")
    @Override
    public void initView() {
        mStateView = StateView.inject(planLedgerListRv);
        mStateView.setOnRetryClickListener(() -> {
            page = 1;
            mPresenter.getPlanLedgerList(page, rows, planId, true);
        });
        planId = getIntent().getStringExtra("planId");
        planLedgerListRv.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(10)));
        planLedgerListRv.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new PlanMyListAdapter(list, this, false);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.bindToRecyclerView(planLedgerListRv);
        adapter.setOnItemClickListener((adapter, view, position) -> PatrolEnterPointActivity.gotoPlanDetailActivity(this, (PlanListBean) adapter.getData().get(position), 4));
        mPresenter.getPlanLedgerList(page, rows, planId, true);
    }

    @Override
    public void onGetPlanLedgerListSuccess(BaseGetResponse<PlanListBean> body, boolean flag) {
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
        List<PlanListBean> newList = body.getRows();
        for (int i = 0; i < newList.size(); i++) {
            if (newList.get(i).getStatus() == 3) {
                newList.get(i).setItemType(PlanListBean.START);
            } else {
                newList.get(i).setItemType(PlanListBean.OTHER);
            }
        }
        setAdapter(newList);
        if (adapter.getData().size() == 0) {
            mStateView.showEmpty();
        } else {
            mStateView.showContent();
        }
    }

    private void setAdapter(List<PlanListBean> list) {
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
