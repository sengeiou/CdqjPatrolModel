package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.PersonLogBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;

/**
 * Created by lyf on 2018/9/10 14:48
 *
 * @author lyf
 * desc：地图人员详情
 */
public interface IMapPersonDetailView extends BaseView{
    /**
     * 获取地图人员详情列表结果
     * @param body body
     * @param flag flag
     */
    void onGetMapPersonDetailSuccess(BaseGetResponse<PersonLogBean> body, boolean flag);
}
