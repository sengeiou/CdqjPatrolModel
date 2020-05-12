package com.cdqj.cdqjpatrolandroid.model;

import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.PlanSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseModel;
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;

/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：地图计划详情M
 */
public class MapPlanDetailModel extends BaseModel {
    /**
     * 回调
     */
    private PatrolOnListener mOnListener;

    public MapPlanDetailModel(PatrolOnListener mOnListener) {

        this.mOnListener = mOnListener;
    }

    /**
     * 地图计划详情列表获取
     * @param flag flag 是否第一次请求数据|刷新数据
     * @return Subscription
     */
    public BaseSubscriber getMapPlanDetail(boolean flag) {
        return new BaseSubscriber<BaseGetResponse<PlanSuperviseReportBean>>() {
                    @Override
                    public void onResult(BaseGetResponse<PlanSuperviseReportBean> bean) {
                        mOnListener.onGetMapPlanDetailSuccess(bean, flag);
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
         * 地图计划详情列表获取列表获取成功
         * @param body body
         * @param flag 是否是刷新
         */
        void onGetMapPlanDetailSuccess(BaseGetResponse<PlanSuperviseReportBean> body, boolean flag);
    }
}
