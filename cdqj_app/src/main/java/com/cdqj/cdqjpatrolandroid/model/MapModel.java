package com.cdqj.cdqjpatrolandroid.model;

import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.CarBean;
import com.cdqj.cdqjpatrolandroid.bean.HdOrderBean;
import com.cdqj.cdqjpatrolandroid.bean.MapGridBean;
import com.cdqj.cdqjpatrolandroid.bean.PatrolTaskResultBean;
import com.cdqj.cdqjpatrolandroid.bean.ResultMapSearchBean;
import com.cdqj.cdqjpatrolandroid.bean.SiteBean;
import com.cdqj.cdqjpatrolandroid.bean.UserLayerBean;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseModel;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;

import java.util.List;

/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：地图M
 */
public class MapModel extends BaseModel {
    /**
     * 回调
     */
    private PatrolOnListener mOnListener;

    public MapModel(PatrolOnListener mOnListener) {
        this.mOnListener = mOnListener;
    }

    /**
     * 计划图层数据加载
     * @return Subscription
     */
    public BaseSubscriber getCheckTypes() {
        return new BaseSubscriber<BaseGetResponse<PatrolTaskResultBean>>() {
                    @Override
                    public void onResult(BaseGetResponse<PatrolTaskResultBean> bean) {
                        mOnListener.onGetCheckTypesSuccess(bean);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };
    }

    /**
     * 隐患图层数据加载
     * @return Subscription
     */
    public BaseSubscriber getHdData() {
        return new BaseSubscriber<List<HdOrderBean>>() {
                    @Override
                    public void onResult(List<HdOrderBean> bean) {
                        mOnListener.onGetHdDataSuccess(bean);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };
    }

    /**
     * 工地图层数据加载
     * @return Subscription
     */
    public BaseSubscriber getSiteData() {
        return new BaseSubscriber<List<SiteBean>>() {
                    @Override
                    public void onResult(List<SiteBean> bean) {
                        mOnListener.onGetSiteDataSuccess(bean);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };
    }

    /**
     * 车辆图层数据加载
     * @return Subscription
     */
    public BaseSubscriber getCarData() {
        return new BaseSubscriber<BaseGetResponse<CarBean>>() {
                    @Override
                    public void onResult(BaseGetResponse<CarBean> bean) {
                        mOnListener.onGetCarDataSuccess(bean);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };
    }

    /**
     * 人员图层数据加载
     * @return Subscription
     */
    public BaseSubscriber getPersonData() {
        // patrol/patrolGroupUsers/getMaplayerList 所有
        return new BaseSubscriber<List<UserLayerBean>>() {
                    @Override
                    public void onResult(List<UserLayerBean> bean) {
                        mOnListener.onGetPersonSuccess(bean);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };
    }

    /**
     * 查询数据数据加载
     * @return Subscription
     */
    public BaseSubscriber getSearchData() {
        // patrol/mapSearch/all?type=hidden,users&keyword=%E6%B5%8B%E8%AF%95
        return new BaseSubscriber<ResultMapSearchBean>() {
                    @Override
                    public void onResult(ResultMapSearchBean bean) {
                        mOnListener.onGetSearchDataSuccess(bean);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };
    }

    /**
     * 获取网格数据
     * @return Subscription
     */
    public BaseSubscriber getPatrolMapGridList() {
        // patrol/patrolMapGrid/page
        return new BaseSubscriber<BaseGetResponse<MapGridBean>>() {
                    @Override
                    public void onResult(BaseGetResponse<MapGridBean> bean) {
                        mOnListener.onGetPatrolMapGridListSuccess(bean);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };
    }

    /**
     * 巡检图层数据加载
     * @return Subscription
     */
    public BaseSubscriber getAreaData() {
        // patrol/patrolPlanInfo/getPlanPointPage 所有
        return new BaseSubscriber<BaseGetResponse<PatrolTaskResultBean>>() {
                    @Override
                    public void onResult(BaseGetResponse<PatrolTaskResultBean> bean) {
                        mOnListener.onGetAreaDataSuccess(bean);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };
    }

    /**
     * 计划巡检点上传
     * @return Subscription
     */
    public BaseSubscriber addUpdateCheckData() {
        // patrol/patrolTaskReport/report
        return new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> bean) {
                        mOnListener.onUpdateCheckDataSuccess(bean);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };
    }


    /**
     * 回调接口
     */
    public interface PatrolOnListener {
        /**
         * 网络请求失败
         * @param e e
         */
        void onFailure(ExceptionHandle.ResponeThrowable e);

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
}
