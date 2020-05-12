package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;

/**
 * Created by lyf on 2018/9/3 14:43
 *
 * @author lyf
 * desc：意见反馈
 */
public interface IMsgFeedbackView extends BaseView {

    /**
     * 意见反馈成功返回结果
     * @param body body
     */
    void onMsgFeedbackSuccess(BasePostResponse<Object> body);
}
