package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;

/**
 * Created by lyf on 2018/9/10 14:48
 *
 * @author lyf
 * desc：计划修改
 */
public interface IPlanModifyView extends BaseView {

    /**
     * 计划修改成功
     * @param body body
     */
    void onSubPlanModifySuccess(BasePostResponse<Object> body);

}
