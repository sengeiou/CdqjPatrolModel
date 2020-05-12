package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.PlanListBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.model.PlanMyModel;
import com.cdqj.cdqjpatrolandroid.view.i.IPlanMyView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyf on 2018/9/10 14:50
 *
 * @author lyf
 * desc：我的计划P
 */
public class PlanMyPresenter extends BasePresenter<IPlanMyView>  implements PlanMyModel.PatrolOnListener{

    private PlanMyModel mModel;

    public PlanMyPresenter(IPlanMyView view) {
        super(view);
        mModel = new PlanMyModel(this);
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 计划执行开始/结束获取结果
     * @param body body
     * @param flag 通过/驳回
     */
    @Override
    public void onUpdatePlanTaskStatusSuccess(BasePostResponse<Object> body, boolean flag) {
        mView.onUpdatePlanTaskStatusSuccess(body, flag);
    }

    /**
     * 我的计划列表获取结果
     * @param body body
     */
    @Override
    public void onGetMyPlanListSuccess(BaseGetResponse<PlanListBean> body, boolean flag) {
        mView.hideProgress();
        mView.onGetMyPlanListSuccess(body, flag);
    }

    /**
     * 我的计划列表获取执行
     * @param page page
     * @param pageSize pageSize
     * @param flag flag 是否第一次请求数据|刷新数据
     * @param areaStr 区域
     * @param startTime 开始
     * @param endTime 结束时间
     */
    public void getMyPlanList(int page, int pageSize, boolean flag, String areaStr, String startTime, String endTime) {
        if (flag) {
            mView.showProgress();
        }
        // patrol/patrolPlanInfo/getSelfPage 分页查询
        Map<String, String> map = new HashMap<>(2);
        map.put("page", String.valueOf(page));
        map.put("rows", String.valueOf(pageSize));
        map.put("areaIds", areaStr);
        map.put("beginTime", startTime);
        map.put("endTime", endTime);
        // sort=id?order=ase
        map.put("sort", "id");
        map.put("order", "desc");
        addSubscription(mApiService.getMyPlanList(map), mModel.getMyPlanList(flag));
    }


    /**
     * 计划执行开始/结束
     * @param id id
     * @param isChecked 开始/结束
     */
    public void updatePlanTaskStatus(String id, boolean isChecked) {
        mView.showProgress();
        // patrol/patrolPlanInfo/beginPlanTask 针对未开始的计划，进行开始计划操作
        // patrol/patrolPlanInfo/completePlanTask 针对开始的计划，进行完成操作
        Map<String, String> map = new HashMap<>(1);
        map.put("taskId", id);
        addSubscription(mApiService.checkPlan(isChecked?"beginPlanTask":"completePlanTask", map), mModel.updatePlanTaskStatus(isChecked));
    }
}
