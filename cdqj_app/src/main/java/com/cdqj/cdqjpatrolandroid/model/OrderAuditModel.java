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
 * desc：审核工单M
 */
public class OrderAuditModel extends BaseModel {
    /**
     * 回调
     */
    private PatrolOnListener mOnListener;

    public OrderAuditModel(PatrolOnListener mOnListener) {

        this.mOnListener = mOnListener;
    }

    /**
     * 审核工单列表获取
     *
     * @param flag     flag 是否第一次请求数据|刷新数据
     * @return Subscription
     */
    @SuppressLint("MissingPermission")
    public BaseSubscriber getAuditOrderList(boolean flag) {
        return new BaseSubscriber<BaseGetResponse<OrderBean>>() {
                    @Override
                    public void onResult(BaseGetResponse<OrderBean> bean) {
                        mOnListener.onGetAuditOrderListSuccess(bean, flag);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };
    }

    /**
     * 工单审核
     *
     * @return sub
     */
    public BaseSubscriber audit() {
        return new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> bean) {
                        mOnListener.onAuditResult(bean);
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
         * 审核工单列表获取成功
         *
         * @param body body
         * @param flag 是否是刷新
         */
        void onGetAuditOrderListSuccess(BaseGetResponse<OrderBean> body, boolean flag);

        /**
         * 工单审核回调结果
         * @param body body
         */
        void onAuditResult(BasePostResponse<Object> body);
    }
}
