package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.LocationBean;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;

import java.util.List;

/**
 * Created by lyf on 2018/9/10 14:48
 *
 * @author lyf
 * desc：人员轨迹
 */
public interface IPersonMapView extends BaseView{
    /**
     * 获取人员轨迹列表结果
     * @param body body
     */
    void onGetPersonTrajectorySuccess(BasePostResponse<List<LocationBean>> body);
}
