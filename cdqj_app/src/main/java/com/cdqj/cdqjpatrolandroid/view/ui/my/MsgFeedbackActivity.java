package com.cdqj.cdqjpatrolandroid.view.ui.my;

import android.support.v4.content.ContextCompat;
import android.widget.EditText;

import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.base.BaseActivity;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.presenter.MsgFeedbackPresenter;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.view.i.IMsgFeedbackView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * 意见反馈
 *
 * @author lyf
 * @date 2018年8月15日 10:18:28
 */
public class MsgFeedbackActivity extends BaseActivity<MsgFeedbackPresenter> implements IMsgFeedbackView {

    EditText feedbackEt;
    SmartRefreshLayout feedbackRefreshLayout;

    /**
     * 界面初始化
     */
    @Override
    public void initView() {
        mPresenter = new MsgFeedbackPresenter(this);
        feedbackRefreshLayout.setEnablePureScrollMode(false);
        assert titleToolbar != null;
        titleToolbar.setRightTitleText("提交")
                .setRightTitleColor(ContextCompat.getColor(this, R.color.theme_one))
                .setRightTitleClickListener(v -> {
                    if (StringUtils.isTrimEmpty(feedbackEt.getText().toString())) {
                        ToastBuilder.showShortWarning("请您输入意见再提交！");
                        return;
                    }
                    mPresenter.msgFeedback(feedbackEt.getText().toString().trim());
                });
    }

    @Override
    protected MsgFeedbackPresenter createPresenter() {
        return new MsgFeedbackPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_msg_feedback;
    }

    @Override
    protected void findView() {
        feedbackEt = findViewById(R.id.feedback_et);
        feedbackRefreshLayout = findViewById(R.id.feedback_refreshLayout);
    }

    @Override
    protected String getTitleText() {
        return "意见反馈";
    }

    @Override
    public void onMsgFeedbackSuccess(BasePostResponse<Object> body) {
        if (body.isSuccess()) {
            ToastBuilder.showShort(body.getMsg());
            this.finish();
        } else {
            ToastBuilder.showShortError(body.getMsg());
        }
    }

    @Override
    public void showProgress() {
        baseShowProgress();
    }

    @Override
    public void showProgress(String str) {
        baseShowProgress(str);
    }

    @Override
    public void hideProgress() {
        baseHideProgress();
    }

    @Override
    public void onSuccess(String msg) {

    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable msg) {
        baseOnFailure(msg);
    }
}
