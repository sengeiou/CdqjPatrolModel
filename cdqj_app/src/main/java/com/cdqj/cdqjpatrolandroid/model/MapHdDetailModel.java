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
 * desc：地图隐患详情M
 */
public class MapHdDetailModel extends BaseModel {
    /**
     * 回调
     */
    private PatrolOnListener mOnListener;

    public MapHdDetailModel(PatrolOnListener mOnListener) {

        this.mOnListener = mOnListener;
    }

    /**
     * 地图隐患详情列表获取
     * @param flag flag 是否第一次请求数据|刷新数据
     * @return Subscription
     */
    public BaseSubscriber getMapHdDetail(boolean flag) {

        return new BaseSubscriber<BasePostResponse<List<List<OrderSuperviseReportBean>>>>() {
                    @Override
                    public void onResult(BasePostResponse<List<List<OrderSuperviseReportBean>>> bean) {
                        mOnListener.onGetMapHdDetailSuccess(bean, flag);
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
         * 地图隐患详情列表获取列表获取成功
         * @param body body
         * @param flag 是否是刷新
         */
        void onGetMapHdDetailSuccess(BasePostResponse<List<List<OrderSuperviseReportBean>>> body, boolean flag);
    }
}
