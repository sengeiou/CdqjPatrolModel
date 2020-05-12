package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.bean.OrderSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.model.HdDetailModel;
import com.cdqj.cdqjpatrolandroid.view.i.IHdDetailView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：隐患上报列表P
 */
public class HdDetailPresenter extends BasePresenter<IHdDetailView> implements HdDetailModel.PatrolOnListener {

    private HdDetailModel mModel;

    public HdDetailPresenter(IHdDetailView view) {
        super(view);
        mModel = new HdDetailModel(this);
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 隐患上报列表获取成功
     * @param body body body
     */
    @Override
    public void onGetHdDetailUpdateListSuccess(BasePostResponse<List<List<OrderSuperviseReportBean>>> body) {
        mView.onGetHdDetailUpdateListSuccess(body);
        mView.hideProgress();
    }

    /**
     * 隐患上报列表获取
     * @param hdId hdId
     */
    public void getHdDetailUpdateList(String hdId) {
        mView.showProgress();
        // patrol/patrolHdInfo/getHdInfoApp
        Map<String, String> map = new HashMap<>(1);
        map.put("id", hdId);
        addSubscription(mApiService.getHdDetailUpdateList(map), mModel.getHdDetailUpdateList());
    }
}