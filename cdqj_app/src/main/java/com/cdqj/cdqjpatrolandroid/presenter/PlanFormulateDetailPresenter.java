package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.MapGridBean;
import com.cdqj.cdqjpatrolandroid.bean.PlanListBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.model.PlanFormulateDetailModel;
import com.cdqj.cdqjpatrolandroid.view.i.IPlanFormulateDetailView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：计划制定P
 */
public class PlanFormulateDetailPresenter extends BasePresenter<IPlanFormulateDetailView> implements PlanFormulateDetailModel.PatrolOnListener {

    private PlanFormulateDetailModel mModel;

    public PlanFormulateDetailPresenter(IPlanFormulateDetailView view) {
        super(view);
        mModel = new PlanFormulateDetailModel(this);
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 获取计划制定界面网格数据成功
     * @param body body body
     */
    @Override
    public void onGetPatrolMapGridListSuccess(BaseGetResponse<MapGridBean> body) {
        mView.onGetPatrolMapGridListSuccess(body);
        mView.hideProgress();
    }

    /**
     * 计划制定成功
     * @param body body body
     */
    @Override
    public void onSubPlanFormulateSuccess(BasePostResponse<Object> body) {
        mView.onSubPlanFormulateSuccess(body);
        mView.hideProgress();
    }

    /**
     * 获取计划制定界面网格数据
     */
    public void getPatrolMapGridList() {
        mView.showProgress();
        // patrol/patrolMapGrid/page
        Map<String, Integer> map = new HashMap<>(2);
        map.put("page", 1);
        map.put("rows", 10000);
        addSubscription(mApiService.getPatrolMapGridList(map), mModel.getPatrolMapGridList());
    }

    /**
     * 计划制定执行
     */
    public void subPlanFormulate(PlanListBean bean) {
        mView.showProgress("");
        // patrol/patrolPlanInfo/addTempPlan 添加修改数据
        addSubscription(mApiService.subPlanFormulate(bean), mModel.subPlanFormulate());
    }
}