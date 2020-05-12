package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.OrderSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;

import java.util.List;

/**
 * Created by lyf on 2018/9/10 14:48
 *
 * @author lyf
 * desc：地图工地详情
 */
public interface IMapSiteDetailView extends BaseView{
    /**
     * 获取地图工地详情列表结果
     * @param basePostResponse basePostResponse
     * @param flag flag
     */
    void onGetMapSiteDetailSuccess(BasePostResponse<List<List<OrderSuperviseReportBean>>> basePostResponse, boolean flag);
}
