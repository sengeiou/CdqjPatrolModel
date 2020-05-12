package com.cdqj.cdqjpatrolandroid.model;

import android.annotation.SuppressLint;

import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.http.BaseModel;

/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：意见反馈M
 */
public class MsgFeedbackModel extends BaseModel {
    /**
     * 回调
     */
    private PatrolOnListener mOnListener;

    public MsgFeedbackModel(PatrolOnListener mOnListener) {

        this.mOnListener = mOnListener;
    }

    /**
     *  意见反馈
     *  @return Subscription
     */
    @SuppressLint("MissingPermission")
    public BaseSubscriber msgFeedback() {
        return new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> bean) {
                        mOnListener.onMsgFeedbackSuccess(bean);
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
         * 意见反馈成功
         * @param body body
         */
        void onMsgFeedbackSuccess(BasePostResponse<Object> body);
    }
}
