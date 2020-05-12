package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.OrderBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;

/**
 * Created by lyf on 2018/9/10 14:48
 *
 * @author lyf
 * desc：我的工单
 */
public interface IOrderMyView extends BaseView{
    /**
     * 获取工单列表结果
     * @param body body
     * @param flag flag
     */
    void onGetMyOrderListSuccess(BaseGetResponse<OrderBean> body, boolean flag);
    /**
     * 接单成功
     */
    void onTakeOrderSuccess(BasePostResponse<Object> body);
    /**
     * 拒单成功
     */
    void onRefuseOrdersSuccess(BasePostResponse<Object> body);
}
