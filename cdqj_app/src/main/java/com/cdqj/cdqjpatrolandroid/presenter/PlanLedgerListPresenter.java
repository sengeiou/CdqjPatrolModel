package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.PlanListBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.model.PlanLedgerListModel;
import com.cdqj.cdqjpatrolandroid.view.i.IPlanLedgerListView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyf on 2018/9/10 14:50
 *
 * @author lyf
 * desc：计划台帐（周期计划执行列表）P
 */
public class PlanLedgerListPresenter extends BasePresenter<IPlanLedgerListView>  implements PlanLedgerListModel.PatrolOnListener{

    private PlanLedgerListModel mModel;

    public PlanLedgerListPresenter(IPlanLedgerListView view) {
        super(view);
        mModel = new PlanLedgerListModel(this);
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 计划台帐（周期计划执行列表）列表获取结果
     * @param body body
     */
    @Override
    public void onGetPlanLedgerListSuccess(BaseGetResponse<PlanListBean> body, boolean flag) {
        mView.hideProgress();
        mView.onGetPlanLedgerListSuccess(body, flag);
    }

    /**
     * 计划台帐（周期计划执行列表）列表获取执行
     * @param page page
     * @param pageSize pageSize
     * @param planId planId
     * @param flag flag 是否第一次请求数据|刷新数据
     */
    public void getPlanLedgerList(int page, int pageSize, String planId, boolean flag) {
        if (flag) {
            mView.showProgress();
        }
        // patrol/patrolPlanInfo/getPlanTask?planId=1042206
        Map<String, String> map = new HashMap<>(2);
        map.put("page", String.valueOf(page));
        map.put("rows", String.valueOf(pageSize));
        map.put("planId", planId);
        addSubscription(mApiService.getPlanLedgerList(map), mModel.getPlanLedgerList(flag));
    }
}
