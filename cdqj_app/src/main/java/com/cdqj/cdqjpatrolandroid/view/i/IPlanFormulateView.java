package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.PlanListBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;

/**
 * Created by lyf on 2018/9/10 14:48
 *
 * @author lyf
 * desc：计划制定
 */
public interface IPlanFormulateView extends BaseView{
    /**
     * 获取计划制定列表结果
     * @param body body
     * @param flag flag
     */
    void onGetPlanFormulateListSuccess(BaseGetResponse<PlanListBean> body, boolean flag);
}
