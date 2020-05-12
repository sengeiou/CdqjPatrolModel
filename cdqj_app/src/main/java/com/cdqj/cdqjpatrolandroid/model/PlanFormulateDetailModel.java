package com.cdqj.cdqjpatrolandroid.model;

import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.MapGridBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseModel;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;

/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：计划制定M
 */
public class PlanFormulateDetailModel extends BaseModel {
    /**
     * 回调
     */
    private PatrolOnListener mOnListener;

    public PlanFormulateDetailModel(PatrolOnListener mOnListener) {

        this.mOnListener = mOnListener;
    }

    /**
     * 获取计划制定界面网格数据
     * @return Subscription
     */
    public BaseSubscriber getPatrolMapGridList() {
        return new BaseSubscriber<BaseGetResponse<MapGridBean>>() {
                    @Override
                    public void onResult(BaseGetResponse<MapGridBean> bean) {
                        mOnListener.onGetPatrolMapGridListSuccess(bean);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };
    }

    /**
     * 计划制定
     * @return Subscription
     */
    public BaseSubscriber subPlanFormulate() {
        return new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> bean) {
                        mOnListener.onSubPlanFormulateSuccess(bean);
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
         * 获取计划制定界面网格数据成功
         * @param body body
         */
        void onGetPatrolMapGridListSuccess(BaseGetResponse<MapGridBean> body);

        /**
         * 计划制定成功
         * @param body body
         */
        void onSubPlanFormulateSuccess(BasePostResponse<Object> body);
    }
}
