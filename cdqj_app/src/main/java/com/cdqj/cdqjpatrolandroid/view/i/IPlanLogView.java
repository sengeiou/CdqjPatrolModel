package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.PlanLogBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;

/**
 * Created by lyf on 2018/9/10 14:48
 *
 * @author lyf
 * desc：计划日志
 */
public interface IPlanLogView extends BaseView{
    /**
     * 获取计划日志列表结果
     * @param body body
     * @param flag flag
     */
    void onGetPlanLogListSuccess(BaseGetResponse<PlanLogBean> body, boolean flag);
}
