package com.cdqj.cdqjpatrolandroid.model;

import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.http.BaseModel;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;

/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：计划修改M
 */
public class PlanModifyModel extends BaseModel {
    /**
     * 回调
     */
    private PatrolOnListener mOnListener;

    public PlanModifyModel(PatrolOnListener mOnListener) {

        this.mOnListener = mOnListener;
    }

    /**
     * 计划修改
     * @return Subscription
     */
    public BaseSubscriber subPlanModify() {
        return new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> bean) {
                        mOnListener.onSubPlanModifySuccess(bean);
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
         * 计划修改成功
         * @param body body
         */
        void onSubPlanModifySuccess(BasePostResponse<Object> body);
    }
}
