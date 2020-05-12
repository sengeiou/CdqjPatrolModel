package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.SiteBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;

/**
 * Created by lyf on 2018/9/10 14:48
 *
 * @author lyf
 * desc：工地台帐
 */
public interface ISiteLedgerListView extends BaseView{
    /**
     * 获取工地台帐列表结果
     * @param body body
     * @param flag flag
     */
    void onGetSiteLedgerListSuccess(BaseGetResponse<SiteBean> body, boolean flag);
}
