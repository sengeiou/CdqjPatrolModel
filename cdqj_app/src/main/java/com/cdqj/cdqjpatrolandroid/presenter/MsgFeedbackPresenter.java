package com.cdqj.cdqjpatrolandroid.presenter;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.MsgFeedbackBean;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.model.MsgFeedbackModel;
import com.cdqj.cdqjpatrolandroid.view.i.IMsgFeedbackView;

/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：意见反馈P
 */
public class MsgFeedbackPresenter extends BasePresenter<IMsgFeedbackView> implements MsgFeedbackModel.PatrolOnListener{

    private MsgFeedbackModel mModel;

    public MsgFeedbackPresenter(IMsgFeedbackView view) {
        super(view);
        mModel = new MsgFeedbackModel(this);
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 意见反馈返回结果
     * @param body body
     */
    @Override
    public void onMsgFeedbackSuccess(BasePostResponse<Object> body) {
        mView.hideProgress();
        mView.onMsgFeedbackSuccess(body);
    }

    /**
     * 意见反馈执行
     * @param remark 意见文本
     */
    public void msgFeedback(String remark){
        mView.showProgress();
        // app/sysAppOpinion/addupdate 添加修改数据
        MsgFeedbackBean bean = new MsgFeedbackBean();
        bean.setDiscript(remark);
        bean.setAppOs(DeviceUtils.getSDKVersionName());
        bean.setAppVersion(AppUtils.getAppVersionName());
        addSubscription(mApiService.msgFeedback(bean), mModel.msgFeedback());
    }
}
