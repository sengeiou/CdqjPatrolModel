package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.bean.HdOrderBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.model.HdListModel;
import com.cdqj.cdqjpatrolandroid.view.i.IHdListView;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by lyf on 2018/9/10 14:50
 *
 * @author lyf
 * desc：隐患台账P
 */
public class HdListPresenter extends BasePresenter<IHdListView> implements HdListModel.PatrolOnListener{

    private HdListModel mModel;

    public HdListPresenter(IHdListView view) {
        super(view);
        mModel = new HdListModel(this);
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 隐患台账列表获取结果
     * @param body body
     */
    @Override
    public void onGetHdListSuccess(BaseGetResponse<HdOrderBean> body, boolean flag) {
        mView.hideProgress();
        mView.onGetHdListSuccess(body, flag);
    }

    /**
     * 隐患台账列表获取执行
     * @param page page
     * @param pageSize pageSize
     * @param flag flag 是否第一次请求数据|刷新数据
     * @param objectsStr 对象
     * @param levelStr 级别
     * @param startTime 开始
     * @param endTime 结束时间
     * @param statusStr 状态
     * @param personStr 人员
     */
    public void getHdList(int page, int pageSize, boolean flag, String objectsStr, String levelStr,
                          String startTime, String endTime, String statusStr, String personStr) {
        if (flag) {
            mView.showProgress();
        }
        // patrol/patrolHdInfo/page 隐患台账
        Map<String, String> map = new HashMap<>(10);
        map.put("page", String.valueOf(page));
        map.put("rows", String.valueOf(pageSize));
        map.put("hdObjects", objectsStr);
        map.put("hdGrands", levelStr);
        map.put("startDate", startTime);
        map.put("endDate", endTime);
        map.put("statuses", statusStr);
        map.put("addUserId", personStr);
        // 数据状态 1待审核 2正式 3废除"
        // map.put("status", "2");
        // sort=id?order=ase
        map.put("sort", "id");
        map.put("order", "desc");
        addSubscription(mApiService.getHdList(map) ,mModel.getHdList(flag));
    }
}
