package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.HdOrderBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;

/**
 * Created by lyf on 2018/9/10 14:48
 *
 * @author lyf
 * desc：隐患台账
 */
public interface IHdListView extends BaseView{
    /**
     * 获取隐患台账列表结果
     * @param body body
     * @param flag flag
     */
    void onGetHdListSuccess(BaseGetResponse<HdOrderBean> body, boolean flag);
}
