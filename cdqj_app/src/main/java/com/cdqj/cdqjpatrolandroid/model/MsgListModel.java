package com.cdqj.cdqjpatrolandroid.model;

import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.MsgBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseModel;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;

/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：信息列表M
 */
public class MsgListModel extends BaseModel {
    /**
     * 回调
     */
    private PatrolOnListener mOnListener;

    public MsgListModel(PatrolOnListener mOnListener) {

        this.mOnListener = mOnListener;
    }

    /**
     * 信息列表列表获取
     * @param flag flag 是否第一次请求数据|刷新数据
     * @return Subscription
     */
    public BaseSubscriber getMsgList(boolean flag) {
        return new BaseSubscriber<BaseGetResponse<MsgBean>>() {
                    @Override
                    public void onResult(BaseGetResponse<MsgBean> bean) {
                        mOnListener.onGetMsgListSuccess(bean, flag);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };
    }

    /**
     * 消息状态更改
     * @return Subscription
     */
    public BaseSubscriber addUpdateMsg() {
        return new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> bean) {
                        mOnListener.onAddUpdateMsgSuccess(bean);
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
         * 信息列表获取成功
         * @param body body
         * @param flag 是否是刷新
         */
        void onGetMsgListSuccess(BaseGetResponse<MsgBean> body, boolean flag);

        /**
         * 消息状态更改成功
         * @param body body
         */
        void onAddUpdateMsgSuccess(BasePostResponse<Object> body);
    }
}
