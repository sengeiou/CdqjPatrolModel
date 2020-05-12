package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.OrderSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;

import java.util.List;

/**
 * Created by lyf on 2018/9/10 14:48
 *
 * @author lyf
 * desc：地图隐患详情
 */
public interface IMapHdDetailView extends BaseView{
    /**
     * 获取地图隐患详情列表结果
     * @param body body
     * @param flag flag
     */
    void onGetMapHdDetailSuccess(BasePostResponse<List<List<OrderSuperviseReportBean>>> body, boolean flag);
}
