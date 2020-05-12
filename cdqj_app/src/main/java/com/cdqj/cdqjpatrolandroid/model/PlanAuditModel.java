package com.cdqj.cdqjpatrolandroid.model;

import android.annotation.SuppressLint;

import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.PlanListBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseModel;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;

/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：计划审核M
 */
public class PlanAuditModel extends BaseModel {
    /**
     * 回调
     */
    private PatrolOnListener mOnListener;

    public PlanAuditModel(PatrolOnListener mOnListener) {

        this.mOnListener = mOnListener;
    }

    /**
     * 计划审核列表获取
     * @param flag flag 是否第一次请求数据|刷新数据
     * @return Subscription
     */
    public BaseSubscriber getPlanAuditList(boolean flag) {
        return new BaseSubscriber<BaseGetResponse<PlanListBean>>() {
                    @Override
                    public void onResult(BaseGetResponse<PlanListBean> bean) {
                        mOnListener.onGetPlanAuditListSuccess(bean, flag);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };
    }

    /**
     * 计划审核执行通过/驳回
     * @param isChecked isChecked 通过 驳回
     * @return Subscription
     */
    @SuppressLint("MissingPermission")
    public BaseSubscriber checkPlan(boolean isChecked) {
        return new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> bean) {
                        mOnListener.onCheckPlanSuccess(bean, isChecked);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };
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
         * 计划审核列表获取成功
         * @param body body
         * @param flag 是否是刷新
         */
        void onGetPlanAuditListSuccess(BaseGetResponse<PlanListBean> body, boolean flag);

        /**
         * 计划审核通过/驳回成功
         * @param body body
         * @param flag 通过/驳回
         */
        void onCheckPlanSuccess(BasePostResponse<Object> body, boolean flag);
    }
}
