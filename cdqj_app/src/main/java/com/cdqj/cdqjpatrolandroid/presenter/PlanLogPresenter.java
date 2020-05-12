package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.PlanLogBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.model.PlanLogModel;
import com.cdqj.cdqjpatrolandroid.view.i.IPlanLogView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyf on 2018/9/10 14:50
 *
 * @author lyf
 * desc：计划日志P
 */
public class PlanLogPresenter extends BasePresenter<IPlanLogView>  implements PlanLogModel.PatrolOnListener{

    private PlanLogModel mModel;

    public PlanLogPresenter(IPlanLogView view) {
        super(view);
        mModel = new PlanLogModel(this);
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 计划日志列表获取结果
     * @param body body
     */
    @Override
    public void onGetPlanLogListSuccess(BaseGetResponse<PlanLogBean> body, boolean flag) {
        mView.hideProgress();
        mView.onGetPlanLogListSuccess(body, flag);
    }

    /**
     * 计划日志列表获取执行
     * @param page page
     * @param pageSize pageSize
     * @param flag flag 是否第一次请求数据|刷新数据
     * @param planId planId
     */
    public void getPlanLogList(int page, int pageSize, boolean flag, String planId) {
        if (flag) {
            mView.showProgress();
        }
        // patrol/patrolPlanLog/page 分页查询
        Map<String, String> map = new HashMap<>(3);
        map.put("page", String.valueOf(page));
        map.put("rows", String.valueOf(pageSize));
        map.put("planId", planId);
        addSubscription(mApiService.getPlanLog(map), mModel.getPlanLogList(flag));
    }
}
