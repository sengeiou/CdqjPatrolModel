package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.MyFragmentTaskNumberBean;
import com.cdqj.cdqjpatrolandroid.bean.UpImgResultBean;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.OnUpdateFileBackListener;
import com.cdqj.cdqjpatrolandroid.model.MyFragmentModel;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;
import com.cdqj.cdqjpatrolandroid.view.i.IMyFragmentView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyf on 2018/9/28 15:48
 *
 * @author lyf
 * desc：我的界面P
 */
public class MyFragmentPresenter extends BasePresenter<IMyFragmentView> implements MyFragmentModel.PatrolOnListener, OnUpdateFileBackListener {

    private MyFragmentModel mModel;

    public MyFragmentPresenter(IMyFragmentView view) {
        super(view);
        mModel = new MyFragmentModel(this, this);
    }

    @Override
    public void onFailure(String e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 头像上传结果
     * @param body body
     */
    @Override
    public void onUpdateFileSuccess(BasePostResponse<List<UpImgResultBean>> body, int type, int flag) {
        mView.onUpdateImgSuccess(body);
    }

    /**
     * 头像地址上传结果
     * @param body body
     */
    @Override
    public void onUpdateImgToSuccess(BasePostResponse<Object> body) {
        mView.hideProgress();
        mView.onUpdateImgToSuccess(body);
    }

    /**
     * 上下班操作结果
     * @param body body
     */
    @Override
    public void onChangeWorkStatusSuccess(BasePostResponse<Object> body) {
        mView.hideProgress();
        mView.onChangeWorkStatusSuccess(body);
    }

    /**
     * 获取任务数量结果
     * @param body body
     */
    @Override
    public void onGetTaskNumberSuccess(BasePostResponse<MyFragmentTaskNumberBean> body) {
        mView.hideProgress();
        mView.onGetTaskNumberSuccess(body);
    }

    /**
     * 头像上传文件服务器执行
     * @param filePath filePath
     * @param fileType fileType
     */
    public void updateImg(String filePath, int fileType) {
        mView.showProgress();
        mModel.updateImg(filePath, fileType);
    }

    /**
     * 获取任务数量
     */
    public void getTaskNumber(boolean flag) {
        if (flag) {
            mView.showProgress("");
        }
        // patrol/statistics/performance
        addSubscription(mApiService.getTaskNumber(), mModel.getTaskNumber());
    }

    /**
     * 上下班操作
     * @param status flag 1上班 2下班
     * @param userId userId
     */
    public void submitChangeWorkStatus(int status, int userId) {
        mView.showProgress("");
        // core/sysUsers/updateWorkStatus
        Map<String, String> map = new HashMap<>(4);
        map.put("status", String.valueOf(status));
        map.put("id", String.valueOf(userId));
        map.put("lat", PreferencesUtil.getString(Constant.LOCATION_LATITUDE));
        map.put("lon", PreferencesUtil.getString(Constant.LOCATION_LONGITUDE));
        addSubscription(mApiService.submitChangeWorkStatus(map), mModel.submitChangeWorkStatus());
    }

    /**
     * 头像地址上传执行
     * @param fileUrl fileUrl
     * @param userId userId
     */
    public void updateImgTo(String fileUrl, int userId) {
        // core/sysUsers/updateUserPhoto
        Map<String, String> map = new HashMap<>(2);
        map.put("photo", fileUrl);
        map.put("id", String.valueOf(userId));
        addSubscription(mApiService.updateImgTo(map), mModel.updateImgTo());
    }
}
