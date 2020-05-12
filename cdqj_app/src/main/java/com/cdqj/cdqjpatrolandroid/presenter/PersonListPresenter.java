package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.UserLayerBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.model.PersonListModel;
import com.cdqj.cdqjpatrolandroid.view.i.IPersonListView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyf on 2018/9/10 14:50
 *
 * @author lyf
 * desc：人员列表P
 */
public class PersonListPresenter extends BasePresenter<IPersonListView>  implements PersonListModel.PatrolOnListener{

    private PersonListModel mModel;

    public PersonListPresenter(IPersonListView view) {
        super(view);
        mModel = new PersonListModel(this);
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 人员列表列表获取结果
     * @param body body
     */
    @Override
    public void onGetPersonListSuccess(BaseGetResponse<UserLayerBean> body, boolean flag) {
        mView.hideProgress();
        mView.onGetPersonListSuccess(body, flag);
    }

    /**
     * 人员列表列表获取执行
     * @param page page
     * @param pageSize pageSize
     * @param flag flag 是否第一次请求数据|刷新数据
     */
    public void getPersonList(int page, int pageSize, boolean flag) {
        if (flag) {
            mView.showProgress();
        }
        // patrol/patrolGroupUsers/page 分页查询
        Map<String, String> map = new HashMap<>(4);
        map.put("page", String.valueOf(page));
        map.put("rows", String.valueOf(pageSize));
        // sort=id?order=ase
        map.put("sort", "id");
        map.put("order", "desc");
        addSubscription(mApiService.getPersonList(map), mModel.getPersonList(flag));
    }
}
