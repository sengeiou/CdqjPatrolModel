package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.PlanSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;

/**
 * Created by lyf on 2018/9/10 14:48
 *
 * @author lyf
 * desc：地图计划详情
 */
public interface IMapPlanDetailView extends BaseView{
    /**
     * 获取地图计划详情列表结果
     * @param body body
     * @param flag flag
     */
    void onGetMapPlanDetailSuccess(BaseGetResponse<PlanSuperviseReportBean> body, boolean flag);
}
