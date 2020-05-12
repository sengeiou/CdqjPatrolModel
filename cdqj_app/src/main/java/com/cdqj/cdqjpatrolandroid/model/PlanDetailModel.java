package com.cdqj.cdqjpatrolandroid.model;

import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.PlanSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.bean.PatrolTaskResultBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseModel;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;
import com.cdqj.cdqjpatrolandroid.http.OnUpdateFileBackListener;
import com.cdqj.cdqjpatrolandroid.http.RetrofitUtils;

/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：计划详情M
 */
public class PlanDetailModel extends BaseModel {
    /**
     * 回调
     */
    private PatrolOnListener mOnListener;

    private OnUpdateFileBackListener mOnUpdateFileBackListener;

    public PlanDetailModel(PatrolOnListener mOnListener, OnUpdateFileBackListener mOnUpdateFileBackListener) {

        this.mOnListener = mOnListener;
        this.mOnUpdateFileBackListener = mOnUpdateFileBackListener;
    }

    /**
     * 计划删除
     * @return Subscription
     */
    public BaseSubscriber subPlanDel() {
        return new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> bean) {
                        mOnListener.onSubPlanDelSuccess(bean);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };
    }

    /**
     * 计划撤回
     * @return Subscription
     */
    public BaseSubscriber subPlanRevoke() {
        return new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> bean) {
                        mOnListener.onSubPlanRevokeSuccess(bean);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };
    }

    /**
     * 计划内巡检点图层数据加载
     * @return Subscription
     */
    public BaseSubscriber getCheckTypes() {
        return new BaseSubscriber<BaseGetResponse<PatrolTaskResultBean>>() {
                    @Override
                    public void onResult(BaseGetResponse<PatrolTaskResultBean> bean) {
                        mOnListener.onGetCheckTypesSuccess(bean);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };
    }

    /**
     * 计划内巡检点上报内容获取
     * @return Subscription
     */
    public BaseSubscriber getTaskReports() {
        return new BaseSubscriber<BaseGetResponse<PlanSuperviseReportBean>>() {
                    @Override
                    public void onResult(BaseGetResponse<PlanSuperviseReportBean> bean) {
                        mOnListener.onGetTaskReportsSuccess(bean);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };
    }

    /**
     * 计划巡检点上传
     * @return Subscription
     */
    public BaseSubscriber addUpdateCheckData() {
        return new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> bean) {
                        mOnListener.onUpdateCheckDataSuccess(bean);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };
    }

    /**
     * 文件上传
     *
     * @param filePaths filePaths
     * @param fileType fileType
     */
    public void updateFile(String filePaths, int fileType, int flag) {
        RetrofitUtils.updateFiles(filePaths, fileType, mOnUpdateFileBackListener, flag);
    }

    /**
     * 回调接口
     */
    public interface PatrolOnListener {
        /**
         * 网络请求失败
         * @param e e
         */
        void onFailure(ExceptionHandle.ResponeThrowable e);

        /**
         * 计划删除成功
         * @param body body
         */
        void onSubPlanDelSuccess(BasePostResponse<Object> body);

        /**
         * 计划撤回成功
         * @param body body
         */
        void onSubPlanRevokeSuccess(BasePostResponse<Object> body);

        /**
         * 计划巡检点上传成功
         * @param body body
         */
        void onUpdateCheckDataSuccess(BasePostResponse<Object> body);

        /**
         * 计划巡检点上报内容获取成功
         * @param body body
         */
        void onGetTaskReportsSuccess(BaseGetResponse<PlanSuperviseReportBean> body);

        /**
         * 计划内巡检点图层数据加载成功
         * @param body body
         */
        void onGetCheckTypesSuccess(BaseGetResponse<PatrolTaskResultBean> body);
    }
}
