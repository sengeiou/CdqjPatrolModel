package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.OrderSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.bean.UserCom;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;
import com.cdqj.cdqjpatrolandroid.http.OnUpdateFileBackListener;
import com.cdqj.cdqjpatrolandroid.http.RetrofitUtils;
import com.cdqj.cdqjpatrolandroid.view.i.IOrderDetailView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：工单详情P
 */
public class OrderDetailPresenter extends BasePresenter<IOrderDetailView> {

    private OnUpdateFileBackListener mOnUpdateFileBackListener;

    public OrderDetailPresenter(IOrderDetailView mView, OnUpdateFileBackListener mOnUpdateFileBackListener) {
        super(mView);
        this.mOnUpdateFileBackListener = mOnUpdateFileBackListener;
    }

    public void upLoadFile(String filePaths, int fileType) {
        RetrofitUtils.updateFiles(filePaths, fileType, mOnUpdateFileBackListener, 0);
    }

    public void addupdate(HashMap<String, String> map) {
        addSubscription(mApiService.addupdate(map), new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> objectBasePostResponse) {
                        mView.onAddupdateSuccse(objectBasePostResponse);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mView.onFailure(e);
                    }
                });
    }
    public void getOrderReports(String orderId) {
        mView.showProgress();
        addSubscription(mApiService.getOrderReports(orderId), new BaseSubscriber<BasePostResponse<List<OrderSuperviseReportBean>>>() {
                    @Override
                    public void onResult(BasePostResponse<List<OrderSuperviseReportBean>> listBaseGetResponse) {
                        mView.onGetOrderReportsSuccse(listBaseGetResponse);
                        mView.hideProgress();
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mView.onFailure(e);
                        mView.hideProgress();
                    }
                });
    }

    /**
     * 获取班主人员信息
     */
    public void getUserCombobox() {
        mView.showProgress();
        addSubscription(mApiService.userCombobox(),new BaseSubscriber<ArrayList<UserCom>>() {
            @Override
            public void onResult(ArrayList<UserCom> bean) {
                mView.onGetUserCombobox(bean);
                mView.hideProgress();
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                mView.onFailure(e);
                mView.hideProgress();
                e.printStackTrace();
            }
        });
    }

    public void finishOrder(String relevanceId,String id) {
        // patrol/patrolOrderInfo/finishOrder 完成上报
        mView.showProgress();
        addSubscription(mApiService.finishOrder(relevanceId,id), new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> listBaseGetResponse) {
                        mView.onFinishOrder(listBaseGetResponse);
                        mView.hideProgress();
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mView.onFailure(e);
                        mView.hideProgress();
                    }
                });
    }

    /**
     * 工单转移
     * @param receiveUserId receiveUserId
     * @param receiveUserName receiveUserName
     */
    public void transferOrder(String id,String receiveUserId,String receiveUserName) {
        // patrol/patrolOrderInfo/transferOrder
        // receiveUserId
        // receiveUserName
        mView.showProgress();
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("receiveUserId", receiveUserId);
        map.put("receiveUserName", receiveUserName);
        addSubscription(mApiService.transferOrder(map), new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> listBaseGetResponse) {
                        mView.onFinishOrder(listBaseGetResponse);
                        mView.hideProgress();
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mView.onFailure(e);
                        mView.hideProgress();
                    }
                });
    }
}