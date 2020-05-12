package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.PlanSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.bean.PatrolTaskResultBean;
import com.cdqj.cdqjpatrolandroid.bean.UpImgResultBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.OnUpdateFileBackListener;
import com.cdqj.cdqjpatrolandroid.model.PlanDetailModel;
import com.cdqj.cdqjpatrolandroid.view.i.IPlanDetailView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：计划详情P
 */
public class PlanDetailPresenter extends BasePresenter<IPlanDetailView> implements PlanDetailModel.PatrolOnListener, OnUpdateFileBackListener {

    private PlanDetailModel mModel;

    public PlanDetailPresenter(IPlanDetailView view) {
        super(view);
        mModel = new PlanDetailModel(this, this);
    }

    @Override
    public void onFailure(String e) {
        mView.hideProgress();
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 计划详情成功
     * @param body body body
     */
    @Override
    public void onSubPlanDelSuccess(BasePostResponse<Object> body) {
        mView.onSubPlanDelSuccess(body);
        mView.hideProgress();
    }

    /**
     * 计划详情成功
     * @param body body body
     */
    @Override
    public void onSubPlanRevokeSuccess(BasePostResponse<Object> body) {
        mView.onSubPlanRevokeSuccess(body);
        mView.hideProgress();
    }

    /**
     * 计划巡检点上传成功
     * @param body body
     */
    @Override
    public void onUpdateCheckDataSuccess(BasePostResponse<Object> body) {
        mView.onUpdateCheckDataSuccess(body);
    }

    /**
     * 计划巡检点上报内容获取成功
     * @param body body
     */
    @Override
    public void onGetTaskReportsSuccess(BaseGetResponse<PlanSuperviseReportBean> body) {
        mView.onGetTaskReportsSuccess(body);
        mView.hideProgress();
    }

    /**
     * 计划内巡检点图层数据加载成功
     * @param body body body
     */
    @Override
    public void onGetCheckTypesSuccess(BaseGetResponse<PatrolTaskResultBean> body) {
        mView.onGetCheckTypesSuccess(body);
        mView.hideProgress();
    }

    /**
     * 文件上传成功
     * @param body body
     */
    @Override
    public void onUpdateFileSuccess(BasePostResponse<List<UpImgResultBean>> body, int type, int flag) {
        mView.onUpdateFileSuccess(body, type);
    }

    /**
     * 执行计划删除
     * @param id 计划id
     */
    public void subPlanDel(String id) {
        mView.showProgress();
        // patrol/patrolPlanInfo/obsoletePlan 计划作废，未完成状态的计划都可以作废,作废内容（已完成任务不作废）:type 1、全部作废 2、作废未开始任务
        Map<String, String> map = new HashMap<>(2);
        map.put("planId", id);
        map.put("type", "1");
        addSubscription(mApiService.subPlanDel(map), mModel.subPlanDel());
    }

    /**
     * 执行计划撤销
     * @param id 计划id
     */
    public void subPlanRevoke(String id) {
        mView.showProgress();
        // patrol/patrolPlanInfo/cancelPlan 制定计划取消，在计划状态为待审核状态，制定人可以取消计划（取消 数据库删除数据）
        Map<String, String> map = new HashMap<>(1);
        map.put("planId", id);
        addSubscription(mApiService.subPlanRevoke(map), mModel.subPlanRevoke());
    }

    /**
     * 计划内巡检点图层数据加载
     * @param id 计划id
     * @param flag
     *              1 我的计划
     *              2 计划制定
     *              3 计划审核
     *              4 计划台帐
     */
    public void getCheckTypes(String id, int flag) {
        mView.showProgress("巡检点图层加载中...");
        // patrol/patrolPlanInfo/getPlanPointList 所有
        // patrol/patrolCheckPoint/page?planId=1043982 大计划
        // patrol/patrolTaskResult/page?taskId=103456 计划内任务
        Map<String, String> map = new HashMap<>(3);
        map.put("page", "1");
        map.put("rows", "10000");
        if (flag == 1 || flag == 4) {
            map.put("taskId", id);
            addSubscription(mApiService.getCheckTypesByTask(map), mModel.getCheckTypes());
        } else {
            map.put("planId", id);
            addSubscription(mApiService.getCheckTypesByPlan(map), mModel.getCheckTypes());
        }
    }

    /**
     * 文件上传
     *
     * @param filePaths filePaths
     * @param fileType fileType
     */
    public void upLoadFile(String filePaths, int fileType) {
        // file/sysFileInfo/system/userInfo/upload
        mModel.updateFile(filePaths, fileType, 0);
    }

    /**
     * 巡检内容上传
     *
     * @param map map
     */
    public void addUpdateCheckData(HashMap<String, String> map) {
        // patrol/patrolTaskReport/report
        addSubscription(mApiService.addUpdateCheckData(map), mModel.addUpdateCheckData());
    }

    /**
     * 巡检内容获取
     *
     * @param taskId taskId
     */
    public void getTaskReports(String taskId) {
        mView.showProgress("上报内容获取中...");
        // patrol/patrolTaskReport/page
        Map<String, String> map = new HashMap<>(3);
        map.put("taskResultId", taskId);
        map.put("page", "1");
        map.put("rows", "100000");
        addSubscription(mApiService.getTaskReports(map), mModel.getTaskReports());
    }
}