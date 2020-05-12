package com.cdqj.cdqjpatrolandroid.model;

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
 * desc：我的计划M
 */
public class PlanMyModel extends BaseModel {
    /**
     * 回调
     */
    private PatrolOnListener mOnListener;

    public PlanMyModel(PatrolOnListener mOnListener) {

        this.mOnListener = mOnListener;
    }

    /**
     * 我的计划列表获取
     * @param flag flag 是否第一次请求数据|刷新数据
     * @return Subscription
     */
    public BaseSubscriber getMyPlanList(boolean flag) {
        return new BaseSubscriber<BaseGetResponse<PlanListBean>>() {
                    @Override
                    public void onResult(BaseGetResponse<PlanListBean> bean) {
                        mOnListener.onGetMyPlanListSuccess(bean, flag);
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
    public BaseSubscriber updatePlanTaskStatus(boolean isChecked) {
        return new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> bean) {
                        mOnListener.onUpdatePlanTaskStatusSuccess(bean, isChecked);
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
         * 我的计划列表获取成功
         * @param body body
         * @param flag 是否是刷新
         */
        void onGetMyPlanListSuccess(BaseGetResponse<PlanListBean> body, boolean flag);


        /**
         * 计划执行开始/结束
         * @param body body
         * @param flag 开始/结束
         */
        void onUpdatePlanTaskStatusSuccess(BasePostResponse<Object> body, boolean flag);
    }
}
