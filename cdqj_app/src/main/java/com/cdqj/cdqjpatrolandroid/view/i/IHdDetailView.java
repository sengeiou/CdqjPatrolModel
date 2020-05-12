package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.OrderSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;

import java.util.List;

/**
 * Created by lyf on 2018/9/10 14:48
 *
 * @author lyf
 * desc：隐患上报列表
 */
public interface IHdDetailView extends BaseView {

    /**
     * 获取隐患上报列表
     * @param basePostResponse basePostResponse
     */
    void onGetHdDetailUpdateListSuccess(BasePostResponse<List<List<OrderSuperviseReportBean>>> basePostResponse);

}
