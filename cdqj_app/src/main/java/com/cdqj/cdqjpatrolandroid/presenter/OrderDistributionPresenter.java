package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.OrderBean;
import com.cdqj.cdqjpatrolandroid.bean.UserCom;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.model.OrderDistributionModel;
import com.cdqj.cdqjpatrolandroid.view.i.IOrderDistributionView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyf on 2018/9/10 14:50
 *
 * @author lyf
 * desc：工单派发P
 */
public class OrderDistributionPresenter extends BasePresenter<IOrderDistributionView>  implements OrderDistributionModel.PatrolOnListener{

    private OrderDistributionModel mModel;

    public OrderDistributionPresenter(IOrderDistributionView view) {
        super(view);
        mModel = new OrderDistributionModel(this);
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 工单派发列表获取结果
     * @param body body
     */
    @Override
    public void onGetDistributionOrderListSuccess(BaseGetResponse<OrderBean> body, boolean flag) {
        mView.hideProgress();
        mView.onGetDistributionOrderListSuccess(body, flag);
    }

    @Override
    public void onGetUserCombobox(ArrayList<UserCom> userComs) {
        mView.hideProgress();
        mView.onGetUserCombobox(userComs);
    }

    @Override
    public void onLeafletsAddUpdate(BasePostResponse<Object> postResponse) {
        mView.hideProgress();
        mView.onAddUpdateSuccess(postResponse);
    }

    /**
     * 工单派发列表获取执行
     * @param page page
     * @param pageSize pageSize
     * @param flag flag 是否第一次请求数据|刷新数据
     * @param objectsStr 对象
     * @param levelStr 级别
     * @param startTime 开始
     * @param endTime 结束时间
     */
    public void getDistributionOrderList(int page, int pageSize, boolean flag, String objectsStr, String levelStr, String startTime, String endTime) {
        if (flag) {
            mView.showProgress();
        }
        // patrol/patrolHdInfo/page 分页查询
        // "1、待派单 2、已派单 3、接单 4、回报处理  5、拒单 6、完成上报  7、驳回  8、作废  9、完成"
        Map<String, String> map = new HashMap<>(7);
        map.put("page", String.valueOf(page));
        map.put("rows", String.valueOf(pageSize));
        // 待派单
        map.put("statuses", "1,5");
        map.put("relevanceTypes", objectsStr);
        map.put("orderLevels", levelStr);
        map.put("startDate", startTime);
        map.put("endDate", endTime);
        // sort=id?order=ase
        map.put("orderType", "1");
        map.put("sort", "id");
        map.put("order", "desc");
        addSubscription(mApiService.getDistributionOrderList(map), mModel.getDistributionOrderList(flag));
    }

    /**
     * 获取班主人员信息
     */
    public void getUserCombobox() {
        mView.showProgress();
        addSubscription(mApiService.userCombobox(), mModel.getUserCombobox());
    }
    /**
     * 派单提交
     */
    public void addUpdate(Map<String, String> map) {
        mView.showProgress();
        //patrol/patrolOrderInfo/sendOrder 工单派单
        addSubscription(mApiService.leafletsAddUpdate(map), mModel.addUpdate());
    }
}
