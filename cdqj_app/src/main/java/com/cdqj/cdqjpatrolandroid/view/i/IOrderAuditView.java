package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.OrderBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;

/**
 * Created by lyf on 2018/9/10 14:49
 *
 * @author lyf
 * desc：审核工单
 */
public interface IOrderAuditView extends BaseView {
    /**
     * 获取审核工单列表结果
     *
     * @param body body
     * @param flag flag
     */
    void onGetAuditOrderListSuccess(BaseGetResponse<OrderBean> body, boolean flag);

    /**
     * 工单审核回调结果
     * @param body
     */
    void onAuditResult(BasePostResponse<Object> body);

}
