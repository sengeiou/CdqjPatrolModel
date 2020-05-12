package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.OrderBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.model.OrderAuditModel;
import com.cdqj.cdqjpatrolandroid.view.i.IOrderAuditView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyf on 2018/9/10 14:50
 *
 * @author lyf
 * desc：我审核工单P
 */
public class OrderAuditPresenter extends BasePresenter<IOrderAuditView> implements OrderAuditModel.PatrolOnListener {

    private OrderAuditModel mModel;

    public OrderAuditPresenter(IOrderAuditView view) {
        super(view);
        mModel = new OrderAuditModel(this);
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 审核工单列表获取结果
     *
     * @param body body
     */
    @Override
    public void onGetAuditOrderListSuccess(BaseGetResponse<OrderBean> body, boolean flag) {
        mView.hideProgress();
        mView.onGetAuditOrderListSuccess(body, flag);
    }

    @Override
    public void onAuditResult(BasePostResponse<Object> body) {
        mView.onAuditResult(body);
    }

    /**
     * 审核工单列表获取执行
     *
     * @param page     page
     * @param pageSize pageSize
     * @param flag     flag 是否第一次请求数据|刷新数据
     * @param objectsStr 对象
     * @param levelStr 级别
     * @param startTime 开始
     * @param endTime 结束时间
     */
    public void getAuditOrderList(int page, int pageSize, boolean flag, String objectsStr, String levelStr, String startTime, String endTime) {
        if (flag) {
            mView.showProgress();
        }
        // patrol/patrolOrderInfo/page 分页查询
        // "1、待派单 2、已派单 3、接单 4、回报处理  5、拒单 6、完成上报  7、驳回  8、作废  9、完成 10、转移工单"
        Map<String, String> map = new HashMap<>(7);
        map.put("page", String.valueOf(page));
        map.put("rows", String.valueOf(pageSize));
        // 待审核
        map.put("statuses", "6,10");
        map.put("relevanceTypes", objectsStr);
        map.put("orderLevels", levelStr);
        map.put("startDate", startTime);
        map.put("endDate", endTime);
        // sort=id?order=ase
        map.put("orderType", "1");
        map.put("sort", "id");
        map.put("order", "desc");
        addSubscription(mApiService.getAuditOrderList(map), mModel.getAuditOrderList(flag));
    }

    /**
     * 审核工单
     */
    public void audit(Map<String, String> map, boolean flag, boolean isGo) {
        // patrol/patrolOrderInfo/passOrder 审核通过
        // patrol/patrolOrderInfo/rejectOrder 审核驳回
        // patrol/patrolOrderInfo/orderReviewPass 转工单审核通过
        // patrol/patrolOrderInfo/orderReviewReject 转工单审核驳回
        String path;
        if (isGo) {
            path = flag?"orderReviewPass":"orderReviewReject";
        } else {
            path = flag?"passOrder":"rejectOrder";
        }
        addSubscription(mApiService.audit(map, path), mModel.audit());
    }
}
