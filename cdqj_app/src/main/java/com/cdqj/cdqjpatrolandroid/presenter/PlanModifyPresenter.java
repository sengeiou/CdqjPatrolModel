package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.PlanListBean;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.model.PlanModifyModel;
import com.cdqj.cdqjpatrolandroid.view.i.IPlanModifyView;

/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：计划修改P
 */
public class PlanModifyPresenter extends BasePresenter<IPlanModifyView> implements PlanModifyModel.PatrolOnListener {

    private PlanModifyModel mModel;

    public PlanModifyPresenter(IPlanModifyView view) {
        super(view);
        mModel = new PlanModifyModel(this);
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 计划修改成功
     * @param body body body
     */
    @Override
    public void onSubPlanModifySuccess(BasePostResponse<Object> body) {
        mView.onSubPlanModifySuccess(body);
        mView.hideProgress();
    }

    /**
     * 执行计划修改
     */
    public void subPlanFormulate(PlanListBean bean) {
        mView.showProgress("");
        // patrol/patrolPlanInfo/addTempPlan 添加修改数据
        addSubscription(mApiService.subPlanFormulate(bean), mModel.subPlanModify());
    }
}