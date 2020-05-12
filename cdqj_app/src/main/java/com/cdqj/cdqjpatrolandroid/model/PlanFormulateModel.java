package com.cdqj.cdqjpatrolandroid.model;

import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.PlanListBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseModel;
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;

/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：计划制定M
 */
public class PlanFormulateModel extends BaseModel {
    /**
     * 回调
     */
    private PatrolOnListener mOnListener;

    public PlanFormulateModel(PatrolOnListener mOnListener) {

        this.mOnListener = mOnListener;
    }

    /**
     * 计划制定列表获取
     * @param flag flag 是否第一次请求数据|刷新数据
     * @return Subscription
     */
    public BaseSubscriber getPlanFormulateList(boolean flag) {
        return new BaseSubscriber<BaseGetResponse<PlanListBean>>() {
                    @Override
                    public void onResult(BaseGetResponse<PlanListBean> bean) {
                        mOnListener.onGetPlanFormulateListSuccess(bean, flag);
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
         * 计划制定列表获取成功
         * @param body body
         * @param flag 是否是刷新
         */
        void onGetPlanFormulateListSuccess(BaseGetResponse<PlanListBean> body, boolean flag);
    }
}
