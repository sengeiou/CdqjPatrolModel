package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.PlanListBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.model.PlanAuditModel;
import com.cdqj.cdqjpatrolandroid.view.i.IPlanAuditView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyf on 2018/9/10 14:50
 *
 * @author lyf
 * desc：计划审核P
 */
public class PlanAuditPresenter extends BasePresenter<IPlanAuditView>  implements PlanAuditModel.PatrolOnListener{

    private PlanAuditModel mModel;

    public PlanAuditPresenter(IPlanAuditView view) {
        super(view);
        mModel = new PlanAuditModel(this);
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 计划审核列表获取结果
     * @param body body
     */
    @Override
    public void onGetPlanAuditListSuccess(BaseGetResponse<PlanListBean> body, boolean flag) {
        mView.hideProgress();
        mView.onGetPlanAuditListSuccess(body, flag);
    }

    /**
     * 计划审核列表获取结果
     * @param body body
     * @param flag 通过/驳回
     */
    @Override
    public void onCheckPlanSuccess(BasePostResponse<Object> body, boolean flag) {
        mView.onCheckPlanSuccess(body, flag);
    }

    /**
     * 计划审核列表获取执行
     * @param page page
     * @param pageSize pageSize
     * @param flag flag 是否第一次请求数据|刷新数据
     * @param areaStr 片区
     * @param typeStr 类型
     * @param startTime 开始
     * @param endTime 结束时间
     *
     */
    public void getPlanAuditList(int page, int pageSize, boolean flag, String areaStr, String typeStr, String startTime, String endTime) {
        if (flag) {
            mView.showProgress();
        }
        // patrol/patrolHdOrder/getMyOrders 分页查询
        Map<String, String> map = new HashMap<>(2);
        map.put("page", String.valueOf(page));
        map.put("rows", String.valueOf(pageSize));
        map.put("areaIds", areaStr);
        map.put("planType", typeStr);
        map.put("beginTime", startTime);
        map.put("endTime", endTime);
        // sort=id?order=ase
        map.put("sort", "id");
        map.put("order", "desc");
        addSubscription(mApiService .getPlanAuditList(map), mModel.getPlanAuditList(flag));
    }

    /**
     * 计划审核执行通过/驳回
     * @param id id
     * @param isChecked 通过/驳回
     */
    public void checkPlan(String id, boolean isChecked) {
        mView.showProgress();
        // patrol/patrolPlanInfo/checkPlan 计划审核通过
        // patrol/patrolPlanInfo/failCheckPlan 计划审核不通过
        Map<String, String> map = new HashMap<>(2);
        map.put("planId", id);
        map.put("remarks", "");
        addSubscription(mApiService.checkPlan(isChecked?"checkPlan":"failCheckPlan", map), mModel.checkPlan(isChecked));
    }
}
