package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.UserLayerBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;

/**
 * Created by lyf on 2018/9/10 14:48
 *
 * @author lyf
 * desc：人员列表
 */
public interface IPersonListView extends BaseView{
    /**
     * 获取人员列表列表结果
     * @param body body
     * @param flag flag
     */
    void onGetPersonListSuccess(BaseGetResponse<UserLayerBean> body, boolean flag);
}
