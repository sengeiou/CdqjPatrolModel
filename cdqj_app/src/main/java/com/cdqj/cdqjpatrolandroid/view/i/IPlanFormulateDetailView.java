package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.MapGridBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;

/**
 * Created by lyf on 2018/9/10 14:48
 *
 * @author lyf
 * desc：计划制定
 */
public interface IPlanFormulateDetailView extends BaseView {

    /**
     * 获取计划制定界面网格数据成功
     * @param basePostResponse basePostResponse
     */
    void onGetPatrolMapGridListSuccess(BaseGetResponse<MapGridBean> basePostResponse);

    /**
     * 计划制定成功
     * @param body body
     */
    void onSubPlanFormulateSuccess(BasePostResponse<Object> body);

}
