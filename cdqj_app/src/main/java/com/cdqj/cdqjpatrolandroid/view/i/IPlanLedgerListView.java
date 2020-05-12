package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.PlanListBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;

/**
 * Created by lyf on 2018/9/10 14:48
 *
 * @author lyf
 * desc：计划台帐（周期计划执行列表）
 */
public interface IPlanLedgerListView extends BaseView{
    /**
     * 获取计划台帐（周期计划执行列表）列表结果
     * @param body body
     * @param flag flag
     */
    void onGetPlanLedgerListSuccess(BaseGetResponse<PlanListBean> body, boolean flag);
}
