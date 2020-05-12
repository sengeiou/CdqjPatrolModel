package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.PlanSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.model.MapPlanDetailModel;
import com.cdqj.cdqjpatrolandroid.view.i.IMapPlanDetailView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyf on 2018/9/10 14:50
 *
 * @author lyf
 * desc：地图计划详情P
 */
public class MapPlanDetailPresenter extends BasePresenter<IMapPlanDetailView>  implements MapPlanDetailModel.PatrolOnListener{

    private MapPlanDetailModel mModel;

    public MapPlanDetailPresenter(IMapPlanDetailView view) {
        super(view);
        mModel = new MapPlanDetailModel(this);
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 地图计划详情列表获取结果
     * @param body body
     */
    @Override
    public void onGetMapPlanDetailSuccess(BaseGetResponse<PlanSuperviseReportBean> body, boolean flag) {
        mView.hideProgress();
        mView.onGetMapPlanDetailSuccess(body, flag);
    }

    /**
     * 地图计划详情列表获取执行
     * @param flag flag 是否第一次请求数据|刷新数据
     * @param taskId taskId
     */
    public void getTaskReports(String taskId, boolean flag) {
        if (flag) {
            mView.showProgress();
        }
        // patrol/patrolTaskReport/page
        Map<String, String> map = new HashMap<>(3);
        map.put("taskResultId", taskId);
        map.put("page", "1");
        map.put("rows", "100000");
        addSubscription(mApiService.getTaskReports(map), mModel.getMapPlanDetail(flag));
    }
}
