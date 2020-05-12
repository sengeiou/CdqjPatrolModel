package com.cdqj.cdqjpatrolandroid.model;

import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.OrderSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.http.BaseModel;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;

import java.util.List;

/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：隐患详情M
 */
public class HdDetailModel extends BaseModel {
    /**
     * 回调
     */
    private PatrolOnListener mOnListener;

    public HdDetailModel(PatrolOnListener mOnListener) {

        this.mOnListener = mOnListener;
    }

    /**
     * 隐患上报列表获取
     * @return Subscription
     */
    public BaseSubscriber getHdDetailUpdateList() {
        return new BaseSubscriber<BasePostResponse<List<List<OrderSuperviseReportBean>>>>() {
                    @Override
                    public void onResult(BasePostResponse<List<List<OrderSuperviseReportBean>>> bean) {
                        mOnListener.onGetHdDetailUpdateListSuccess(bean);
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
         * 隐患上报列表获取成功
         * @param body body
         */
        void onGetHdDetailUpdateListSuccess(BasePostResponse<List<List<OrderSuperviseReportBean>>> body);
    }
}
