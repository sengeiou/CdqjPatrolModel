package com.cdqj.cdqjpatrolandroid.model;

import android.annotation.SuppressLint;

import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.OrderBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseModel;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;

/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：我的工单M
 */
public class OrderMyModel extends BaseModel {
    /**
     * 回调
     */
    private PatrolOnListener mOnListener;

    public OrderMyModel(PatrolOnListener mOnListener) {

        this.mOnListener = mOnListener;
    }

    /**
     *  我的工单列表获取
     * @param flag flag 是否第一次请求数据|刷新数据
     * @return Subscription
     */
    @SuppressLint("MissingPermission")
    public BaseSubscriber getMyOrderList(boolean flag) {
        return new BaseSubscriber<BaseGetResponse<OrderBean>>() {
                    @Override
                    public void onResult(BaseGetResponse<OrderBean> bean) {
                        mOnListener.onGetMyOrderListSuccess(bean, flag);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };
    }

    /**
     * 接单
     * @return Subscription
     */
    public BaseSubscriber takeOrders() {
        return new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> bean) {
                        mOnListener.onTakeOrderSuccess(bean);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };
    }

    /**
     * 拒单
     * @return Subscription
     */
    public BaseSubscriber refuseOrders() {
        return new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> bean) {
                        mOnListener.onRefuseOrdersSuccess(bean);
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
         * 我的工单列表获取成功
         * @param body body
         * @param flag 是否是刷新
         */
        void onGetMyOrderListSuccess(BaseGetResponse<OrderBean> body, boolean flag);
        /**
         * 接单成功
         */
        void onTakeOrderSuccess(BasePostResponse<Object> body);
        /**
         * 拒单成功
         */
        void onRefuseOrdersSuccess(BasePostResponse<Object> body);
    }
}
