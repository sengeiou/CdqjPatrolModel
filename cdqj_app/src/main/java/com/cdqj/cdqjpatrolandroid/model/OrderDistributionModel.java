package com.cdqj.cdqjpatrolandroid.model;

import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.OrderBean;
import com.cdqj.cdqjpatrolandroid.bean.UserCom;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseModel;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;

import java.util.ArrayList;

/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：工单派发M
 */
public class OrderDistributionModel extends BaseModel {
    /**
     * 回调
     */
    private PatrolOnListener mOnListener;

    public OrderDistributionModel(PatrolOnListener mOnListener) {

        this.mOnListener = mOnListener;
    }

    /**
     * 工单派发列表获取
     *
     * @param flag     flag 是否第一次请求数据|刷新数据
     * @return Subscription
     */
    public BaseSubscriber getDistributionOrderList(boolean flag) {
        return new BaseSubscriber<BaseGetResponse<OrderBean>>() {
                    @Override
                    public void onResult(BaseGetResponse<OrderBean> bean) {
                        mOnListener.onGetDistributionOrderListSuccess(bean, flag);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };
    }
    public BaseSubscriber getUserCombobox() {
        return new BaseSubscriber<ArrayList<UserCom>>() {
                    @Override
                    public void onResult(ArrayList<UserCom> bean) {
                        mOnListener.onGetUserCombobox(bean);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };
    }
    public BaseSubscriber addUpdate() {
        return new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> bean) {
                        mOnListener.onLeafletsAddUpdate(bean);
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
         *
         * @param e e
         */
        void onFailure(ExceptionHandle.ResponeThrowable e);

        /**
         * 工单派发列表获取成功
         *
         * @param body body
         * @param flag 是否是刷新
         */
        void onGetDistributionOrderListSuccess(BaseGetResponse<OrderBean> body, boolean flag);

        /**
         * 获取人员列表
         * @param userComs userComs
         */
        void onGetUserCombobox(ArrayList<UserCom> userComs);
        /**
         * 派单成功
         */
        void onLeafletsAddUpdate(BasePostResponse<Object> postResponse);
    }
}
