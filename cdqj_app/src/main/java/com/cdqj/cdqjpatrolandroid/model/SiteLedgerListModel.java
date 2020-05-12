package com.cdqj.cdqjpatrolandroid.model;

import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.SiteBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseModel;
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;

/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：工地台帐M
 */
public class SiteLedgerListModel extends BaseModel {
    /**
     * 回调
     */
    private PatrolOnListener mOnListener;

    public SiteLedgerListModel(PatrolOnListener mOnListener) {

        this.mOnListener = mOnListener;
    }

    /**
     * 工地台帐列表获取
     * @param flag flag 是否第一次请求数据|刷新数据
     * @return Subscription
     */
    public BaseSubscriber getSiteLedgerList(boolean flag) {
        return new BaseSubscriber<BaseGetResponse<SiteBean>>() {
                    @Override
                    public void onResult(BaseGetResponse<SiteBean> bean) {
                        mOnListener.onGetSiteLedgerListSuccess(bean, flag);
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
         * 工地台帐列表获取成功
         * @param body body
         * @param flag 是否是刷新
         */
        void onGetSiteLedgerListSuccess(BaseGetResponse<SiteBean> body, boolean flag);
    }
}
