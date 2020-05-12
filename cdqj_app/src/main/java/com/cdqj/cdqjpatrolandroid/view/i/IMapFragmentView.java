package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.CarBean;
import com.cdqj.cdqjpatrolandroid.bean.HdOrderBean;
import com.cdqj.cdqjpatrolandroid.bean.MapGridBean;
import com.cdqj.cdqjpatrolandroid.bean.PatrolHdType;
import com.cdqj.cdqjpatrolandroid.bean.PatrolTaskResultBean;
import com.cdqj.cdqjpatrolandroid.bean.ResultMapSearchBean;
import com.cdqj.cdqjpatrolandroid.bean.SiteBean;
import com.cdqj.cdqjpatrolandroid.bean.UserLayerBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyf on 2018/9/3 14:43
 *
 * @author lyf
 * desc：登录
 */
public interface IMapFragmentView extends BaseView {

    void onGetComboboxResult(ArrayList<PatrolHdType> postResponse);
    void onGetHdType(ArrayList<PatrolHdType> hdTypes);

    /**
     * 上报工单
     * @param objectBasePostResponse objectBasePostResponse
     */
    void onAddupdateSuccse(BasePostResponse<Object> objectBasePostResponse);

    /**
     * 隐患上报成功
     */
    void onHdSubmitSuccse(BasePostResponse<Object> beans);

    /**
     * 计划巡检点图层数据加载成功
     * @param body body
     */
    void onGetCheckTypesSuccess(BaseGetResponse<PatrolTaskResultBean> body);

    /**
     * 人员图层数据加载结果
     * @param body body
     */
    void onGetPersonSuccess(List<UserLayerBean> body);

    /**
     * 查询数据加载结果
     * @param body body
     */
    void onGetSearchDataSuccess(ResultMapSearchBean body);

    /**
     * 隐患图层数据加载结果
     * @param body body
     */
    void onGetHdDataSuccess(List<HdOrderBean> body);

    /**
     * 工地图层数据加载结果
     * @param body body
     */
    void onGetSiteDataSuccess(List<SiteBean> body);

    /**
     * 车辆图层数据加载结果
     * @param body body
     */
    void onGetCarDataSuccess(BaseGetResponse<CarBean> body);

    /**
     * 巡检图层数据加载结果
     * @param body body
     */
    void onGetAreaDataSuccess(BaseGetResponse<PatrolTaskResultBean> body);

    /**
     * 计划巡检点上传成功
     * @param body body
     */
    void onUpdateCheckDataSuccess(BasePostResponse<Object> body);

    /**
     * 获取网格数据成功
     * @param body body
     */
    void onGetPatrolMapGridListSuccess(BaseGetResponse<MapGridBean> body);
}
