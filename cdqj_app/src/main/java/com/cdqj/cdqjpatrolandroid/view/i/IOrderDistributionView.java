package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.OrderBean;
import com.cdqj.cdqjpatrolandroid.bean.UserCom;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;

import java.util.ArrayList;

/**
 * Created by lyf on 2018/9/10 14:48
 *
 * @author lyf
 * desc：工单派单
 */
public interface IOrderDistributionView extends BaseView{
    /**
     * 获取工单列表结果（隐患）
     * @param body body
     * @param flag flag
     */
    void onGetDistributionOrderListSuccess(BaseGetResponse<OrderBean> body, boolean flag);
    /**
     * 获取人员列表
     * @param userComs
     */
    void onGetUserCombobox(ArrayList<UserCom> userComs);

    /**
     * 派单成功
     * @param body body
     */
    void onAddUpdateSuccess(BasePostResponse<Object> body);
}
