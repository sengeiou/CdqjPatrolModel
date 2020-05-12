package com.cdqj.cdqjpatrolandroid.model;

import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.PersonLogBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseModel;
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;

/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：地图人员详情M
 */
public class MapPersonDetailModel extends BaseModel {
    /**
     * 回调
     */
    private PatrolOnListener mOnListener;

    public MapPersonDetailModel(PatrolOnListener mOnListener) {

        this.mOnListener = mOnListener;
    }

    /**
     * 地图人员详情列表获取
     * @param flag flag 是否第一次请求数据|刷新数据
     * @return Subscription
     */
    public BaseSubscriber getMapPersonDetail(boolean flag) {
        return new BaseSubscriber<BaseGetResponse<PersonLogBean>>() {
                    @Override
                    public void onResult(BaseGetResponse<PersonLogBean> bean) {
                        mOnListener.onGetMapPersonDetailSuccess(bean, flag);
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
         * 地图人员详情列表获取列表获取成功
         * @param body body
         * @param flag 是否是刷新
         */
        void onGetMapPersonDetailSuccess(BaseGetResponse<PersonLogBean> body, boolean flag);
    }
}
