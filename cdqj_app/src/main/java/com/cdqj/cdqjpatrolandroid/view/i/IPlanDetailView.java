package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.PlanSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.bean.PatrolTaskResultBean;
import com.cdqj.cdqjpatrolandroid.bean.UpImgResultBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;

import java.util.List;

/**
 * Created by lyf on 2018/9/10 14:48
 *
 * @author lyf
 * desc：计划详情
 */
public interface IPlanDetailView extends BaseView {

    /**
     * 计划撤销成功
     * @param body body
     */
    void onSubPlanRevokeSuccess(BasePostResponse<Object> body);

    /**
     * 计划巡检点上传成功
     * @param body body
     */
    void onUpdateCheckDataSuccess(BasePostResponse<Object> body);

    /**
     * 计划删除成功
     * @param body body
     */
    void onSubPlanDelSuccess(BasePostResponse<Object> body);

    /**
     * 计划内巡检点图层数据加载成功
     * @param body body
     */
    void onGetCheckTypesSuccess(BaseGetResponse<PatrolTaskResultBean> body);

    /**
     * 文件上传成功
     * @param body body
     * @param type type
     */
    void onUpdateFileSuccess(BasePostResponse<List<UpImgResultBean>> body, int type);

    /**
     * 计划巡检点上报内容获取成功
     * @param body body
     */
    void onGetTaskReportsSuccess(BaseGetResponse<PlanSuperviseReportBean> body);
}
