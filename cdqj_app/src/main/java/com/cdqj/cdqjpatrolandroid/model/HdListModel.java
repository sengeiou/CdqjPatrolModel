package com.cdqj.cdqjpatrolandroid.model;

import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.HdOrderBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseModel;
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;

/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：隐患台账M
 */
public class HdListModel extends BaseModel {
    /**
     * 回调
     */
    private PatrolOnListener mOnListener;

    public HdListModel(PatrolOnListener mOnListener) {

        this.mOnListener = mOnListener;
    }

    /**
     * 隐患台账列表获取
     * @return Subscription
     */
    public BaseSubscriber getHdList(boolean flag) {
        return new BaseSubscriber<BaseGetResponse<HdOrderBean>>() {
                    @Override
                    public void onResult(BaseGetResponse<HdOrderBean> bean) {
                        mOnListener.onGetHdListSuccess(bean, flag);
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
         * 隐患台账列表获取列表获取成功
         * @param body body
         * @param flag 是否是刷新
         */
        void onGetHdListSuccess(BaseGetResponse<HdOrderBean> body, boolean flag);
    }
}
