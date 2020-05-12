package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.LocationBean;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.model.PersonMapModel;
import com.cdqj.cdqjpatrolandroid.view.i.IPersonMapView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyf on 2018/9/10 14:50
 *
 * @author lyf
 * desc：人员轨迹P
 */
public class PersonMapPresenter extends BasePresenter<IPersonMapView>  implements PersonMapModel.PatrolOnListener{

    private PersonMapModel mModel;

    public PersonMapPresenter(IPersonMapView view) {
        super(view);
        mModel = new PersonMapModel(this);
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 人员轨迹列表获取结果
     * @param body body
     */
    @Override
    public void onGetPersonTrajectorySuccess(BasePostResponse<List<LocationBean>> body) {
        mView.hideProgress();
        mView.onGetPersonTrajectorySuccess(body);
    }

    /**
     * 人员轨迹列表获取执行
     * @param userId  userId
     * @param startDate  开始时间
     * @param endDate  结束时间
     */
    public void getPersonTrajectoryList(String userId, String startDate, String endDate) {
        mView.showProgress("图层加载中");
        // patrol/patrolGroupUsers/getGisRecordPage 获取巡检轨迹列表
        Map<String, String> map = new HashMap<>(3);
        map.put("userId", userId);
        map.put("stime", startDate);
        map.put("etime", endDate);
        addSubscription(mApiService.getPersonTrajectoryList(map), mModel.getPersonTrajectoryList());
    }
}
