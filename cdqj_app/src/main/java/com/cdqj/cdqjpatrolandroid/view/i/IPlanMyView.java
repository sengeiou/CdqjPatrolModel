package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.PlanListBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;

/**
 * Created by lyf on 2018/9/10 14:48
 *
 * @author lyf
 * desc：我的计划
 */
public interface IPlanMyView extends BaseView{
    /**
     * 获取我的计划列表结果
     * @param body body
     * @param flag flag
     */
    void onGetMyPlanListSuccess(BaseGetResponse<PlanListBean> body, boolean flag);

    /**
     * 计划执行开始/结束获取结果
     * @param body body
     * @param flag 通过/驳回
     */
    void onUpdatePlanTaskStatusSuccess(BasePostResponse<Object> body, boolean flag);
}
