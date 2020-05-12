package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.model.PatrolMainModel;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;
import com.cdqj.cdqjpatrolandroid.view.i.IPatrolMainView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：主界面P
 */
public class PatrolMianPresenter extends BasePresenter<IPatrolMainView> implements PatrolMainModel.PatrolOnListener {

    private PatrolMainModel mModel;

    public PatrolMianPresenter(IPatrolMainView view) {
        super(view);
        mModel = new PatrolMainModel(this);
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 执行主界面下班成功
     * @param body body body
     */
    @Override
    public void onSubmitOffWorkSuccess(BasePostResponse<Object> body) {
        mView.onSubmitOffWorkSuccess(body);
        mView.hideProgress();
    }

    /**
     * 执行主界面下班
     * @param userId userId
     */
    public void submitOffWork(int userId) {
        mView.showProgress();
        // patrol/patrolHdInfo/getHdInfoApp
        Map<String, String> map = new HashMap<>(4);
        map.put("status", String.valueOf(2));
        map.put("id", String.valueOf(userId));
        map.put("lat", PreferencesUtil.getString(Constant.LOCATION_LATITUDE));
        map.put("lon", PreferencesUtil.getString(Constant.LOCATION_LONGITUDE));
        addSubscription(mApiService.submitChangeWorkStatus(map), mModel.submitOffWork());
    }
}