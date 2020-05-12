package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.PlanListBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.model.PlanLedgerModel;
import com.cdqj.cdqjpatrolandroid.view.i.IPlanLedgerView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyf on 2018/9/10 14:50
 *
 * @author lyf
 * desc：计划台帐P
 */
public class PlanLedgerPresenter extends BasePresenter<IPlanLedgerView>  implements PlanLedgerModel.PatrolOnListener{

    private PlanLedgerModel mModel;

    public PlanLedgerPresenter(IPlanLedgerView view) {
        super(view);
        mModel = new PlanLedgerModel(this);
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 计划台帐列表获取结果
     * @param body body
     */
    @Override
    public void onGetPlanLedgerListSuccess(BaseGetResponse<PlanListBean> body, boolean flag) {
        mView.hideProgress();
        mView.onGetPlanLedgerListSuccess(body, flag);
    }

    /**
     * 计划台帐列表获取执行
     * @param page page
     * @param pageSize pageSize
     * @param flag flag 是否第一次请求数据|刷新数据
     * @param areaStr 片区
     * @param personStr 申请人
     * @param startTime 开始
     * @param endTime 结束时间
     */
    public void getPlanLedgerList(int page, int pageSize, boolean flag, String areaStr, String startTime, String endTime, String personStr) {
        if (flag) {
            mView.showProgress();
        }
        // patrol/patrolPlanInfo/page 分页查询
        Map<String, String> map = new HashMap<>(2);
        map.put("page", String.valueOf(page));
        map.put("rows", String.valueOf(pageSize));
        map.put("areaIds", areaStr);
        map.put("addUserId", personStr);
        map.put("beginTime", startTime);
        map.put("endTime", endTime);
        // sort=id?order=ase
        map.put("sort", "id");
        map.put("order", "desc");
        addSubscription(mApiService.getPlanLedger(map), mModel.getPlanLedgerList(flag));
    }
}
