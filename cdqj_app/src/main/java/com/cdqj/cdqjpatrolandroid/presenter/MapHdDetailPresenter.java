package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.OrderSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.model.MapHdDetailModel;
import com.cdqj.cdqjpatrolandroid.view.i.IMapHdDetailView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyf on 2018/9/10 14:50
 *
 * @author lyf
 * desc：地图隐患详情P
 */
public class MapHdDetailPresenter extends BasePresenter<IMapHdDetailView>  implements MapHdDetailModel.PatrolOnListener{

    private MapHdDetailModel mModel;

    public MapHdDetailPresenter(IMapHdDetailView view) {
        super(view);
        mModel = new MapHdDetailModel(this);
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 地图隐患详情列表获取结果
     * @param body body
     */
    @Override
    public void onGetMapHdDetailSuccess(BasePostResponse<List<List<OrderSuperviseReportBean>>> body, boolean flag) {
        mView.hideProgress();
        mView.onGetMapHdDetailSuccess(body, flag);
    }

    /**
     * 地图隐患详情列表获取执行
     * @param flag flag 是否第一次请求数据|刷新数据
     * @param taskId taskId
     */
    public void getOrderReports(String taskId, boolean flag) {
        if (flag) {
            mView.showProgress();
        }
        // patrol/patrolHdInfo/getHdInfoApp
        Map<String, String> map = new HashMap<>(1);
        map.put("id", taskId);
        addSubscription(mApiService.getHdDetailUpdateList(map), mModel.getMapHdDetail(flag));
    }
}
