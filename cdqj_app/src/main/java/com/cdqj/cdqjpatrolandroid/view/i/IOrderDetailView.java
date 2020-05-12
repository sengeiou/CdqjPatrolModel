package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.OrderSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.bean.UserCom;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyf on 2018/9/10 14:48
 *
 * @author lyf
 * desc：我的工单
 */
public interface IOrderDetailView extends BaseView {

    /**
     * 上报工单
     * @param objectBasePostResponse
     */
    void onAddupdateSuccse(BasePostResponse<Object> objectBasePostResponse);

    /**
     * 获取上报列表
     * @param basePostResponse
     */
    void onGetOrderReportsSuccse(BasePostResponse<List<OrderSuperviseReportBean>> basePostResponse);

    /**
     * 工单完成
     * @param basePostResponse
     */
    void onFinishOrder(BasePostResponse<Object> basePostResponse);

    /**
     * 获取人员列表
     * @param userComs userComs
     */
    void onGetUserCombobox(ArrayList<UserCom> userComs);
}
