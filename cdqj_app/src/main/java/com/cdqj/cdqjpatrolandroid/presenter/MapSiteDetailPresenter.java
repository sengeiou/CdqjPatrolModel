package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.OrderSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.model.MapSiteDetailModel;
import com.cdqj.cdqjpatrolandroid.view.i.IMapSiteDetailView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyf on 2018/9/10 14:50
 *
 * @author lyf
 * desc：地图工地详情P
 */
public class MapSiteDetailPresenter extends BasePresenter<IMapSiteDetailView>  implements MapSiteDetailModel.PatrolOnListener{

    private MapSiteDetailModel mModel;

    public MapSiteDetailPresenter(IMapSiteDetailView view) {
        super(view);
        mModel = new MapSiteDetailModel(this);
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 地图工地详情列表获取结果
     * @param body body
     */
    @Override
    public void onGetMapSiteDetailSuccess(BasePostResponse<List<List<OrderSuperviseReportBean>>> body, boolean flag) {
        mView.hideProgress();
        mView.onGetMapSiteDetailSuccess(body, flag);
    }

    /**
     * 地图工地详情列表获取执行
     * @param flag flag 是否第一次请求数据|刷新数据
     * @param taskId taskId
     */
    public void getTaskReports(String taskId, boolean flag) {
        if (flag) {
            mView.showProgress();
        }
        // patrol/patrolConstructionSite/getSiteInfoApp
        Map<String, String> map = new HashMap<>(3);
        map.put("id", taskId);
        map.put("page", "1");
        map.put("rows", "100000");
        addSubscription(mApiService.getSiteReports(map), mModel.getMapSiteDetail(flag));
    }
}
