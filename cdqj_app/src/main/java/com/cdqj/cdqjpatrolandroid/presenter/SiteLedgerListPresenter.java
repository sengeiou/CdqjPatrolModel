package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.SiteBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.model.SiteLedgerListModel;
import com.cdqj.cdqjpatrolandroid.view.i.ISiteLedgerListView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyf on 2018/9/10 14:50
 *
 * @author lyf
 * desc：工地台帐P
 */
public class SiteLedgerListPresenter extends BasePresenter<ISiteLedgerListView>  implements SiteLedgerListModel.PatrolOnListener{

    private SiteLedgerListModel mModel;

    public SiteLedgerListPresenter(ISiteLedgerListView view) {
        super(view);
        mModel = new SiteLedgerListModel(this);
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 工地台帐列表获取结果
     * @param body body
     */
    @Override
    public void onGetSiteLedgerListSuccess(BaseGetResponse<SiteBean> body, boolean flag) {
        mView.hideProgress();
        mView.onGetSiteLedgerListSuccess(body, flag);
    }

    /**
     * 工地台帐列表获取执行
     * @param page page
     * @param pageSize pageSize
     * @param flag flag 是否第一次请求数据|刷新数据
     * @param objectsStr 类型
     * @param levelStr 级别
     * @param startTime 开始
     * @param endTime 结束时间
     * @param statusStr 状态
     * @param personStr 人员
     */
    public void getSiteLedgerList(int page, int pageSize, boolean flag, String objectsStr, String levelStr,
                                  String startTime, String endTime, String statusStr, String personStr) {
        if (flag) {
            mView.showProgress();
        }
        // patrol/patrolConstructionSite/page
        Map<String, String> map = new HashMap<>(10);
        map.put("page", String.valueOf(page));
        map.put("rows", String.valueOf(pageSize));
        map.put("districts", objectsStr);
        map.put("siteLevels", levelStr);
        map.put("startDate", startTime);
        map.put("endDate", endTime);
        map.put("statuses", statusStr);
        // 数据状态 1待审核 2正式 3废除"
        //map.put("status", "2");
        map.put("addUserId", personStr);
        // sort=id?order=ase
        map.put("sort", "id");
        map.put("order", "desc");
        addSubscription(mApiService.getSiteLedgerList(map), mModel.getSiteLedgerList(flag));
    }
}
