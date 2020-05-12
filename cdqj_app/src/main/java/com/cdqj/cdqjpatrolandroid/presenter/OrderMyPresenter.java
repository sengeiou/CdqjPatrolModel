package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.OrderBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.model.OrderMyModel;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;
import com.cdqj.cdqjpatrolandroid.view.i.IOrderMyView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyf on 2018/9/10 14:50
 *
 * @author lyf
 * desc：我的工单P
 */
public class OrderMyPresenter extends BasePresenter<IOrderMyView>  implements OrderMyModel.PatrolOnListener{

    private OrderMyModel mModel;

    public OrderMyPresenter(IOrderMyView view) {
        super(view);
        mModel = new OrderMyModel(this);
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 我的工单列表获取结果
     * @param body body
     */
    @Override
    public void onGetMyOrderListSuccess(BaseGetResponse<OrderBean> body, boolean flag) {
        mView.hideProgress();
        mView.onGetMyOrderListSuccess(body, flag);
    }

    @Override
    public void onTakeOrderSuccess(BasePostResponse<Object> body) {
        mView.hideProgress();
        mView.onTakeOrderSuccess(body);
    }

    @Override
    public void onRefuseOrdersSuccess(BasePostResponse<Object> body) {
        mView.hideProgress();
        mView.onRefuseOrdersSuccess(body);
    }

    /**
     * 我的工单列表获取执行
     * @param page page
     * @param pageSize pageSize
     * @param flag flag 是否第一次请求数据|刷新数据
     *
     * @param objectsStr 对象
     * @param levelStr 级别
     * @param startTime 开始
     * @param endTime 结束时间
     *  String objecsStr = "", levelStr = "", startTime = "", endTime = ""
     */
    public void getMyOrderList(int page, int pageSize, boolean flag, String objectsStr, String levelStr, String startTime, String endTime) {
        if (flag) {
            mView.showProgress();
        }
        // patrol/patrolOrderInfo/page 分页查询
        // 1、待派单 2、已派单 3、接单 4、回报处理  5、拒单 6、完成上报  7、驳回  8、作废  9、完成
        Map<String, String> map = new HashMap<>(6);
        map.put("page", String.valueOf(page));
        map.put("rows", String.valueOf(pageSize));
        // 已派单
        map.put("statuses", "2,3,4,7");
        map.put("relevanceTypes", objectsStr);
        map.put("orderLevels", levelStr);
        map.put("startDate", startTime);
        map.put("endDate", endTime);
        map.put("receiveUserId", String.valueOf(PreferencesUtil.getInt(Constant.USER_ID)));
        // sort=id?order=desc
        map.put("sort", "id");
        map.put("order", "desc");
        addSubscription(mApiService.getMyOrderList(map), mModel.getMyOrderList(flag));
    }

    /**
     * 接单
     * @param map map
     */
    public void takeOrders(Map<String, String> map) {
        mView.showProgress();
        // patrol/patrolOrderInfo/takeOrder 接单
        addSubscription(mApiService.takeOrders(map), mModel.takeOrders());
    }
    /**
     * 拒单
     * @param map map
     */
    public void refuseOrders(Map<String, String> map) {
        mView.showProgress();
        // patrol/patrolOrderInfo/refuseOrder 拒单
        addSubscription(mApiService.refuseOrders(map), mModel.refuseOrders());
    }
}
