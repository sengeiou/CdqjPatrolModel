package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.OrderSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;

import java.util.List;

/**
 * Created by lyf on 2018/9/10 14:48
 *
 * @author lyf
 * desc：主界面
 */
public interface IPatrolMainView extends BaseView {

    /**
     * 执行主界面下班成功
     * @param basePostResponse basePostResponse
     */
    void onSubmitOffWorkSuccess(BasePostResponse<Object> basePostResponse);

}
