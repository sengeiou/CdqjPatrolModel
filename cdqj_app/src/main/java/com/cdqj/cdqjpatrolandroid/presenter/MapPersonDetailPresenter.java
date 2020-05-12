package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.PersonLogBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.model.MapPersonDetailModel;
import com.cdqj.cdqjpatrolandroid.view.i.IMapPersonDetailView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyf on 2018/9/10 14:50
 *
 * @author lyf
 * desc：地图人员详情P
 */
public class MapPersonDetailPresenter extends BasePresenter<IMapPersonDetailView>  implements MapPersonDetailModel.PatrolOnListener{

    private MapPersonDetailModel mModel;

    public MapPersonDetailPresenter(IMapPersonDetailView view) {
        super(view);
        mModel = new MapPersonDetailModel(this);
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 地图人员详情列表获取结果
     * @param body body
     */
    @Override
    public void onGetMapPersonDetailSuccess(BaseGetResponse<PersonLogBean> body, boolean flag) {
        mView.hideProgress();
        mView.onGetMapPersonDetailSuccess(body, flag);
    }

    /**
     * 地图人员详情列表获取执行
     * @param flag flag 是否第一次请求数据|刷新数据
     * @param userId userId
     */
    public void getPersonReports(String userId, boolean flag) {
        if (flag) {
            mView.showProgress();
        }
        // patrol/patrolGroupUsers/getLogPage /获取用户日志
        Map<String, String> map = new HashMap<>(3);
        map.put("userId", userId);
        map.put("page", "1");
        map.put("rows", "10");
        addSubscription(mApiService.getMapPersonDetail(map), mModel.getMapPersonDetail(flag));
    }
}
