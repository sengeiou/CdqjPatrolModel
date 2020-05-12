package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.PlanListBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;

/**
 * Created by lyf on 2018/9/10 14:48
 *
 * @author lyf
 * desc：审核计划
 */
public interface IPlanAuditView extends BaseView{
    /**
     * 获取审核计划列表结果
     * @param body body
     * @param flag flag
     */
    void onGetPlanAuditListSuccess(BaseGetResponse<PlanListBean> body, boolean flag);

    /**
     * 获取审核计划列表结果
     * @param body body
     * @param flag 通过/驳回
     */
    void onCheckPlanSuccess(BasePostResponse<Object> body, boolean flag);
}
