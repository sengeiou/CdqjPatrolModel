package com.cdqj.cdqjpatrolandroid.presenter;

import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
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
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;
import com.cdqj.cdqjpatrolandroid.http.OnUpdateFileBackListener;
import com.cdqj.cdqjpatrolandroid.http.PatrolApiService;
import com.cdqj.cdqjpatrolandroid.http.RetrofitUtils;
import com.cdqj.cdqjpatrolandroid.http.RetrofitManager;
import com.cdqj.cdqjpatrolandroid.model.MapModel;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.utils.TransformUtils;
import com.cdqj.cdqjpatrolandroid.view.i.IMapFragmentView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：意见反馈P
 */
public class MapFragmentPresenter extends BasePresenter<IMapFragmentView> implements MapModel.PatrolOnListener {

    private MapModel mModel;
    private ArrayList<PatrolHdType> patrolHdTypesList;
    private OnUpdateFileBackListener mOnUpdateFileBackListener;

    public MapFragmentPresenter(IMapFragmentView mView, OnUpdateFileBackListener mOnUpdateFileBackListener) {
        super(mView);
        this.mOnUpdateFileBackListener = mOnUpdateFileBackListener;
        mModel = new MapModel(this);
    }

    /**
     * 获取工地上报片区数据
     */
    public void getCombobox() {
        RetrofitManager.getInstance().create(PatrolApiService.class)
                .getCombobox()
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(new BaseSubscriber<ArrayList<PatrolHdType>>() {
                    @Override
                    public void onResult(ArrayList<PatrolHdType> postResponse) {
                        mView.onGetComboboxResult(postResponse);
                        mView.hideProgress();
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        ToastBuilder.showShortError(e.getMessage());
                        mView.hideProgress();
                    }
                });
    }

    /**
     * 根据隐患对象获取隐患类型
     * */
    public void getHdType(String deviceType) {
        RetrofitManager.getInstance().create(PatrolApiService.class)
                .getPatrolHdType(deviceType)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(new BaseSubscriber<ArrayList<PatrolHdType>>() {
                    @Override
                    public void onResult(ArrayList<PatrolHdType> postResponse) {
                        mView.onGetHdType(postResponse);
                        mView.hideProgress();
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        ToastBuilder.showShortError(e.getMessage());
                        mView.hideProgress();
                    }
                });
    }
    /**
     * 隐患上报
     */
    public void hdSubmit(HashMap<String, String> map) {
        RetrofitManager.getInstance().create(PatrolApiService.class)
                .hdSubmit(map)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> postResponse) {
                        mView.onHdSubmitSuccse(postResponse);
                        mView.hideProgress();
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        ToastBuilder.showShortError(e.getMessage());
                        mView.hideProgress();
                    }
                });
    }

    /**
     * 工地上报
     */
    public void siteSubmit(HashMap<String, String> map) {
        RetrofitManager.getInstance().create(PatrolApiService.class)
                .siteSubmit(map)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> postResponse) {
                        mView.onHdSubmitSuccse(postResponse);
                        mView.hideProgress();
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        ToastBuilder.showShortError(e.getMessage());
                        mView.hideProgress();
                    }
                });
    }

    /**
     * 巡检点上报
     */
    public void pointSubmit(HashMap<String, String> map) {
        mView.showProgress();
        RetrofitManager.getInstance().create(PatrolApiService.class)
                .pointSubmit(map)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> postResponse) {
                        mView.onHdSubmitSuccse(postResponse);
                        mView.hideProgress();
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        ToastBuilder.showShortError(e.getMessage());
                        mView.hideProgress();
                    }
                });
    }

    /**
     * 设备新增
     */
    public void devNewSubmit(HashMap<String, String> map) {
        mView.showProgress();
        RetrofitManager.getInstance().create(PatrolApiService.class)
                .devNewSubmit(map)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> postResponse) {
                        mView.onHdSubmitSuccse(postResponse);
                        mView.hideProgress();
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        ToastBuilder.showShortError(e.getMessage());
                        mView.hideProgress();
                    }
                });
    }

    /**
     * 工地|隐患等元素上报
     * @param map map
     */
    public void addUpdate(HashMap<String, String> map) {
        addSubscription(mApiService.addupdate(map), new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> objectBasePostResponse) {
                        mView.onAddupdateSuccse(objectBasePostResponse);
                        mView.hideProgress();
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mView.onFailure(e);
                    }
                });
    }

    public void upLoadFile(String filePaths, int fileType, int flag) {
        RetrofitUtils.updateFiles(filePaths, fileType, mOnUpdateFileBackListener, flag);
    }

    /**
     * 计划巡检点图层数据加载
     */
    public void getCheckTypes() {
        mView.showProgress();
        // patrol/patrolPlanInfo/getPlanPointPage 所有
        addSubscription(mApiService.getCheckTypes(), mModel.getCheckTypes());
    }

    /**
     * 隐患图层数据加载
     */
    public void getHdData() {
        mView.showProgress();
        // patrol/patrolHdInfo/getListExp 所有
        addSubscription(mApiService.getHdData(), mModel.getHdData());
    }

    /**
     * 工地图层数据加载
     */
    public void getSiteData() {
        mView.showProgress();
        // patrol/patrolConstructionSite/getMaplayerPage
        // getListExp
        Map<String, String> map = new HashMap<>(2);
        map.put("page", "1");
        map.put("rows", "100000");
        addSubscription(mApiService.getSiteData(map), mModel.getSiteData());
    }

    /**
     * 车辆图层数据加载
     */
    public void getCarData() {
        mView.showProgress();
        // patrol/patrolCarBasedata/page
        // getListExp
        Map<String, String> map = new HashMap<>(2);
        map.put("page", "1");
        map.put("rows", "100000");
        addSubscription(mApiService.getCarData(map), mModel.getCarData());
    }

    /**
     * 人员图层数据加载
     */
    public void getPersonData() {
        mView.showProgress();
        addSubscription(mApiService.getPersonData(), mModel.getPersonData());
    }

    /**
     * 查询
     * @param flag flag
     * @param searchStr 查询串
     * @param typeStr 类型
     */
    public void getSearchData(boolean flag, String searchStr, String typeStr) {
        if (flag) {
            mView.showProgress();
        }
        Map<String, String> map = new HashMap<>(2);
        map.put("type", typeStr);
        map.put("keyword", searchStr);
        addSubscription(mApiService.getSearchData(map), mModel.getSearchData());
    }

    /**
     * 获取网格数据
     */
    public void getPatrolMapGridList() {
        mView.showProgress();
        Map<String, Integer> map = new HashMap<>(2);
        map.put("page", 1);
        map.put("rows", 10000);
        addSubscription(mApiService.getPatrolMapGridList(map), mModel.getPatrolMapGridList());
    }

    /**
     * 巡检图层数据加载
     */
    public void getAreaData() {
        mView.showProgress();
        addSubscription(mApiService.getAreaData(), mModel.getAreaData());
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 计划巡检点图层数据加载结果
     */
    @Override
    public void onGetCheckTypesSuccess(BaseGetResponse<PatrolTaskResultBean> body) {
        mView.onGetCheckTypesSuccess(body);
        mView.hideProgress();
    }

    /**
     * 人员图层数据加载结果
     */
    @Override
    public void onGetPersonSuccess(List<UserLayerBean> body) {
        mView.onGetPersonSuccess(body);
        mView.hideProgress();
    }

    /**
     * 查询数据加载结果
     */
    @Override
    public void onGetSearchDataSuccess(ResultMapSearchBean body) {
        mView.onGetSearchDataSuccess(body);
        mView.hideProgress();
    }

    /**
     * 获取网格数据成功
     * @param body body body
     */
    @Override
    public void onGetPatrolMapGridListSuccess(BaseGetResponse<MapGridBean> body) {
        mView.onGetPatrolMapGridListSuccess(body);
        mView.hideProgress();
    }

    /**
     * 隐患图层数据加载结果
     */
    @Override
    public void onGetHdDataSuccess(List<HdOrderBean> body) {
        mView.onGetHdDataSuccess(body);
        mView.hideProgress();
    }

    /**
     * 工地图层数据加载结果
     */
    @Override
    public void onGetSiteDataSuccess(List<SiteBean> body) {
        mView.onGetSiteDataSuccess(body);
        mView.hideProgress();
    }

    @Override
    public void onGetCarDataSuccess(BaseGetResponse<CarBean> body) {
        mView.onGetCarDataSuccess(body);
        mView.hideProgress();
    }

    /**
     * 巡检图层数据加载结果
     */
    @Override
    public void onGetAreaDataSuccess(BaseGetResponse<PatrolTaskResultBean> body) {
        mView.onGetAreaDataSuccess(body);
        mView.hideProgress();
    }

    /**
     * 计划巡检点上传成功
     * @param body body
     */
    @Override
    public void onUpdateCheckDataSuccess(BasePostResponse<Object> body) {
        mView.onUpdateCheckDataSuccess(body);
        mView.hideProgress();
    }

    /**
     * 巡检内容上传
     *
     * @param map map
     */
    public void addUpdateCheckData(HashMap<String, String> map) {
        addSubscription(mApiService.addUpdateCheckData(map), mModel.addUpdateCheckData());
    }
}