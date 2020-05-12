package com.cdqj.cdqjpatrolandroid.http;

import com.cdqj.cdqjpatrolandroid.gis.bean.GisParamsBean;
import com.cdqj.cdqjpatrolandroid.bean.BasicGridBean;
import com.cdqj.cdqjpatrolandroid.bean.CarBean;
import com.cdqj.cdqjpatrolandroid.bean.MyFragmentTaskNumberBean;
import com.cdqj.cdqjpatrolandroid.bean.OrderBean;
import com.cdqj.cdqjpatrolandroid.bean.PatrolTaskResultBean;
import com.cdqj.cdqjpatrolandroid.bean.DicCacheDaoData;
import com.cdqj.cdqjpatrolandroid.bean.HdOrderBean;
import com.cdqj.cdqjpatrolandroid.bean.LocationBean;
import com.cdqj.cdqjpatrolandroid.bean.LoginResultBean;
import com.cdqj.cdqjpatrolandroid.bean.MapGridBean;
import com.cdqj.cdqjpatrolandroid.bean.MsgBean;
import com.cdqj.cdqjpatrolandroid.bean.MsgFeedbackBean;
import com.cdqj.cdqjpatrolandroid.bean.OrderSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.bean.PatrolHdType;
import com.cdqj.cdqjpatrolandroid.bean.PersonLogBean;
import com.cdqj.cdqjpatrolandroid.bean.PlanListBean;
import com.cdqj.cdqjpatrolandroid.bean.PlanLogBean;
import com.cdqj.cdqjpatrolandroid.bean.PlanSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.bean.ResultMapSearchBean;
import com.cdqj.cdqjpatrolandroid.bean.SiteBean;
import com.cdqj.cdqjpatrolandroid.bean.TerminalInfoBean;
import com.cdqj.cdqjpatrolandroid.bean.UpImgResultBean;
import com.cdqj.cdqjpatrolandroid.bean.UserCom;
import com.cdqj.cdqjpatrolandroid.bean.UserLayerBean;
import com.cdqj.cdqjpatrolandroid.model.BdAddressBean;
import com.cdqj.cdqjpatrolandroid.model.ModelAcoordResultBean;
import com.cdqj.cdqjpatrolandroid.utils.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by lyf on 2018/9/3 11:48
 *
 * @author lyf
 * desc：巡线网络请求Service
 */
public interface PatrolApiService {

    /**
     * 缺省值
     * 唐昌 xunjian/
     */
    String APPEND_FH_ADDRESS_PARAM = "";
//    String APPEND_FH_ADDRESS_PARAM = "";

    /**
     * 登录接口
     *
     * @param map map
     * @return LoginResultBean LoginResultBean
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "core/platform/login")
    Observable<BasePostResponse<LoginResultBean>> login(@QueryMap Map<String, String> map);

    /**
     * 意见反馈接口
     *
     * @param body body
     * @return BasePostResponse<Object> BasePostResponse<Object>
     */
    @POST(APPEND_FH_ADDRESS_PARAM + "app/sysAppOpinion/addupdate")
    Observable<BasePostResponse<Object>> msgFeedback(//@Header("token") String token,
                                                     @Body MsgFeedbackBean body);

    /**
     * 获取我的工单接口
     *
     * @param map map
     * @return BaseGetResponse<HdOrderBean> BaseGetResponse<HdOrderBean>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolOrderInfo/page")
    Observable<BaseGetResponse<OrderBean>> getMyOrderList(@QueryMap Map<String, String> map);

    /**
     * 获取制定的计划
     *
     * @param map map
     * @return BaseGetResponse<PlanListBean> BaseGetResponse<PlanListBean>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolPlanInfo/getFormulatePage")
    Observable<BaseGetResponse<PlanListBean>> getPlanFormulateList(@QueryMap Map<String, String> map);

    /**
     * 获取计划日志列表
     *
     * @param map map
     * @return BaseGetResponse<PlanLogBean> BaseGetResponse<PlanLogBean>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolPlanLog/page")
    Observable<BaseGetResponse<PlanLogBean>> getPlanLog(@QueryMap Map<String, String> map);

    /**
     * 计划台帐列表获取
     *
     * @param map map
     * @return BaseGetResponse<PlanListBean> BaseGetResponse<PlanListBean>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolPlanInfo/page")
    Observable<BaseGetResponse<PlanListBean>> getPlanLedger(@QueryMap Map<String, String> map);

    /**
     * 计划台帐（周期计划执行列表）列表获取
     *
     * @param map map
     * @return BaseGetResponse<PlanListBean> BaseGetResponse<PlanListBean>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolPlanInfo/getPlanTask")
    Observable<BaseGetResponse<PlanListBean>> getPlanLedgerList(@QueryMap Map<String, String> map);

    /**
     * 工地台帐列表获取
     *
     * @param map map
     * @return BaseGetResponse<SiteBean> BaseGetResponse<SiteBean>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolConstructionSite/page")
    Observable<BaseGetResponse<SiteBean>> getSiteLedgerList(@QueryMap Map<String, String> map);

    /**
     * 获取审核的计划
     *
     * @param map map
     * @return BaseGetResponse<PlanListBean> BaseGetResponse<PlanListBean>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolPlanInfo/getCheckPage")
    Observable<BaseGetResponse<PlanListBean>> getPlanAuditList(@QueryMap Map<String, String> map);

    /**
     * 计划审核通过/驳回
     *
     * @param isChecked isChecked
     * @param map       map
     * @return BaseGetResponse<PlanListBean> BaseGetResponse<PlanListBean>
     */
    @FormUrlEncoded
    @POST(APPEND_FH_ADDRESS_PARAM + "patrol/patrolPlanInfo/{isChecked}")
    Observable<BasePostResponse<Object>> checkPlan(@Path("isChecked") String isChecked, @FieldMap Map<String, String> map);

    /**
     * 获取我的计划
     *
     * @param map map
     * @return BaseGetResponse<PlanListBean> BaseGetResponse<PlanListBean>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolPlanInfo/getSelfPage")
    Observable<BaseGetResponse<PlanListBean>> getMyPlanList(@QueryMap Map<String, String> map);

    /**
     * 获取隐患台账
     *
     * @param map map
     * @return BaseGetResponse<HdOrderBean> BaseGetResponse<HdOrderBean>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolHdInfo/page")
    Observable<BaseGetResponse<HdOrderBean>> getHdList(@QueryMap Map<String, String> map);

    /**
     * 获取人员轨迹
     *
     * @param map map
     * @return List<LocationBean> List<LocationBean>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolGroupUsers/getGisRecordPage")
    Observable<BasePostResponse<List<LocationBean>>> getPersonTrajectoryList(@QueryMap Map<String, String> map);

    /**
     * 获取人员列表
     *
     * @param map map
     * @return BaseGetResponse<HdOrderBean> BaseGetResponse<HdOrderBean>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolGroupUsers/getMaplayerPage")
    Observable<BaseGetResponse<UserLayerBean>> getPersonList(@QueryMap Map<String, String> map);

    /**
     * 获取消息（公告）
     *
     * @param map map
     * @return BaseGetResponse<MsgBean> BaseGetResponse<MsgBean>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolMsgInfo/page")
    Observable<BaseGetResponse<MsgBean>> getMsgList(@QueryMap Map<String, Integer> map);

    /**
     * 消息状态更改（公告）
     *
     * @param map map
     * @return BasePostResponse<Object>
     */
    @FormUrlEncoded
    @POST(APPEND_FH_ADDRESS_PARAM + "patrol/patrolMsgRead/addupdate")
    Observable<BasePostResponse<Object>> addUpdateMsg(@FieldMap Map<String, Integer> map);

    /**
     * 获取隐患上报内容
     *
     * @param map map
     * @return List<OrderSuperviseReportBean>> List<OrderSuperviseReportBean>>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolHdInfo/getHdInfoApp")
    Observable<BasePostResponse<List<List<OrderSuperviseReportBean>>>> getHdDetailUpdateList(@QueryMap Map<String, String> map);

    /**
     * 获取计划制定界面网格数据
     *
     * @param map map
     * @return MapGridBean
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolMapGrid/page")
    Observable<BaseGetResponse<MapGridBean>> getPatrolMapGridList(@QueryMap Map<String, Integer> map);

    /**
     * 计划制定
     *
     * @param bean MapGridBean
     * @return Object
     */
    @POST(APPEND_FH_ADDRESS_PARAM + "patrol/patrolPlanInfo/addTempPlan")
    Observable<BasePostResponse<Object>> subPlanFormulate(@Body PlanListBean bean);

    /**
     * 计划撤销
     *
     * @param map map
     * @return Object
     */
    @FormUrlEncoded
    @POST(APPEND_FH_ADDRESS_PARAM + "patrol/patrolPlanInfo/cancelPlan")
    Observable<BasePostResponse<Object>> subPlanRevoke(@FieldMap Map<String, String> map);

    /**
     * 计划内巡检点图层数据加载（任务）
     *
     * @param map map
     * @return List<PatrolTaskResultBean>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolTaskResult/page")
    Observable<BaseGetResponse<PatrolTaskResultBean>> getCheckTypesByTask(@QueryMap Map<String, String> map);

    /**
     * 计划内巡检点图层数据加载（进行中）
     *
     * @return List<PatrolTaskResultBean>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolPlanInfo/getPlanPointPage")
    Observable<BaseGetResponse<PatrolTaskResultBean>> getCheckTypes();

    /**
     * 根据坐标获取地址
     *
     * @return List<PatrolTaskResultBean>
     */
    @GET("reverse_geocoding/v3/?mcode=" + Constant.BAIDU_SHA1 + ";com.cdqj.cdqjpatrolandroid")
    Observable<BdAddressBean> getAddressByPoint(@QueryMap Map<String, String> map);

    /**
     * 查询附近位置道路信息
     *
     * @param map map
     * @return SearchRoundBean
     * Key 8015b8c1f3c7b2551b2e1a1e39245d8e
     */
    @GET("apiserver/ajaxproxy")
    Observable<String> searchRound(@QueryMap Map<String, String> map);

    /**
     * 获取管线参数
     *
     * @return String
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "gis/config/mobilemap")
    Observable<GisParamsBean> getGisParam();

    /**
     * 隐患图层数据加载
     *
     * @return List<HdOrderBean>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolHdInfo/getListExp")
    Observable<List<HdOrderBean>> getHdData();

    /**
     * 工地图层数据加载
     *
     * @return List<PatrolTaskResultBean>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolConstructionSite/getListExp")
    Observable<List<SiteBean>> getSiteData(@QueryMap Map<String, String> map);

    /**
     * 车辆图层数据加载
     *
     * @return List<PatrolTaskResultBean>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolCarBasedata/page")
    Observable<BaseGetResponse<CarBean>> getCarData(@QueryMap Map<String, String> map);

    /**
     * 人员图层数据加载
     *
     * @return List<UserLayerBean>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolGroupUsers/getMaplayerList")
    Observable<List<UserLayerBean>> getPersonData();

    /**
     * 地图查询数据加载
     *
     * @param map map
     * @return ResultMapSearchBean
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/mapSearch/all")
    Observable<ResultMapSearchBean> getSearchData(@QueryMap Map<String, String> map);

    /**
     * 巡检图层数据加载
     *
     * @return List<PatrolTaskResultBean>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolPlanInfo/getPlanPointPage")
    Observable<BaseGetResponse<PatrolTaskResultBean>> getAreaData();

    /**
     * 计划内巡检点图层数据加载（计划）
     *
     * @param map map
     * @return List<PatrolTaskResultBean>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolCheckPoint/page")
    Observable<BaseGetResponse<PatrolTaskResultBean>> getCheckTypesByPlan(@QueryMap Map<String, String> map);

    /**
     * 工地上报内容获取
     *
     * @param map map
     * @return List<List < OrderSuperviseReportBean>>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolConstructionSite/getSiteInfoApp")
    Observable<BasePostResponse<List<List<OrderSuperviseReportBean>>>> getSiteReports(@QueryMap Map<String, String> map);

    /**
     * 计划内巡检点上报内容获取
     *
     * @param map map
     * @return List<List < OrderSuperviseReportBean>>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolTaskReport/page")
    Observable<BaseGetResponse<PlanSuperviseReportBean>> getTaskReports(@QueryMap Map<String, String> map);

    /**
     * 人员获取操作日志
     *
     * @param map map
     * @return List<PersonLogBean>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolGroupUsers/getLogPage")
    Observable<BaseGetResponse<PersonLogBean>> getMapPersonDetail(@QueryMap Map<String, String> map);

    /**
     * 计划巡检点上传
     *
     * @param map map
     * @return BasePostResponse<Object>
     */
    @FormUrlEncoded
    @POST(APPEND_FH_ADDRESS_PARAM + "patrol/patrolTaskReport/report")
    Observable<BasePostResponse<Object>> addUpdateCheckData(@FieldMap Map<String, String> map);

    /**
     * 计划删除
     *
     * @param map map
     * @return Object
     */
    @FormUrlEncoded
    @POST(APPEND_FH_ADDRESS_PARAM + "patrol/patrolPlanInfo/obsoletePlan")
    Observable<BasePostResponse<Object>> subPlanDel(@FieldMap Map<String, String> map);

    /**
     * 头像地址上传执行
     *
     * @param map map
     * @return BasePostResponse<Object> BasePostResponse<Object>
     */
    @FormUrlEncoded
    @POST(APPEND_FH_ADDRESS_PARAM + "core/sysUsers/updateUserPhoto")
    Observable<BasePostResponse<Object>> updateImgTo(@FieldMap Map<String, String> map);

    /**
     * 获取任务数量
     *
     * @return BasePostResponse<MyFragmentTaskNumberBean> BasePostResponse<MyFragmentTaskNumberBean>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/statistics/performance")
    Observable<BasePostResponse<MyFragmentTaskNumberBean>> getTaskNumber();

    /**
     * 执行上下班操作
     *
     * @param map map
     * @return BasePostResponse<Object> BasePostResponse<Object>
     */
    @FormUrlEncoded
    @POST(APPEND_FH_ADDRESS_PARAM + "core/sysUsers/updateWorkStatus")
    Observable<BasePostResponse<Object>> submitChangeWorkStatus(@FieldMap Map<String, String> map);

    /**
     * 获取派单工单接口
     *
     * @param map map
     * @return BaseGetResponse<HdOrderBean> BaseGetResponse<HdOrderBean>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolOrderInfo/page")
    Observable<BaseGetResponse<OrderBean>> getDistributionOrderList(@QueryMap Map<String, String> map);

    /**
     * 获取审核工单接口
     *
     * @param map map
     * @return BaseGetResponse<HdOrderBean> BaseGetResponse<HdOrderBean>
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolOrderInfo/page")
    Observable<BaseGetResponse<OrderBean>> getAuditOrderList(@QueryMap Map<String, String> map);

    /**
     * 我的工单
     * 接单
     */
    @FormUrlEncoded
    @POST(APPEND_FH_ADDRESS_PARAM + "patrol/patrolOrderInfo/takeOrder")
    Observable<BasePostResponse<Object>> takeOrders(@FieldMap Map<String, String> map);

    /**
     * 我的工单
     * 拒单
     */
    @FormUrlEncoded
    @POST(APPEND_FH_ADDRESS_PARAM + "patrol/patrolOrderInfo/refuseOrder")
    Observable<BasePostResponse<Object>> refuseOrders(@FieldMap Map<String, String> map);

    /**
     * 工单审核
     */
    @FormUrlEncoded
    @POST(APPEND_FH_ADDRESS_PARAM + "patrol/patrolOrderInfo/{path}")
    Observable<BasePostResponse<Object>> audit(@FieldMap Map<String, String> map, @Path("path") String path);


    /**
     * 监护上报
     * 上传文件
     */
    @Multipart
    @POST("file/sysFileInfo/HD/image/upload")
    Call<BasePostResponse<List<UpImgResultBean>>> uploadImg(@Header("qjtoken") String token,
                                                            @Part List<MultipartBody.Part> parts);

    /**
     * 上传文件
     */
    @Multipart
    @POST("file/sysFileInfo/{updateUrl1}/{updateUrl2}/upload")
    Call<BasePostResponse<List<UpImgResultBean>>> uploadImgOnlyOne(@Header("qjtoken") String token,
                                                                   @Path("updateUrl1") String updateUrl1,
                                                                   @Path("updateUrl2") String updateUrl2,
                                                                   @Part MultipartBody.Part part);

    /**
     * 上传文件
     */
    @Multipart
    @POST("{updateUrl}")
    Call<BasePostResponse<List<UpImgResultBean>>> uploadImgOnlyOne(@Header("qjtoken") String token,
                                                                   @Path("updateUrl") String updateUrl,
                                                                   @Part MultipartBody.Part part);

    /**
     * 上传文件
     */
    @Multipart
    @POST("file/sysFileInfo/{updateUrl1}/{updateUrl2}/upload")
    Call<BasePostResponse<List<UpImgResultBean>>> uploadFiles(@Header("qjtoken") String token,
                                                              @Path("updateUrl1") String updateUrl1,
                                                              @Path("updateUrl2") String updateUrl2,
                                                              @Part List<MultipartBody.Part> part);

    /**
     * 上传文件
     */
    @Multipart
    @POST("{updateUrl}")
    Call<BasePostResponse<List<UpImgResultBean>>> uploadFiles(@Header("qjtoken") String token,
                                                              @Path("updateUrl") String updateUrl,
                                                              @Part List<MultipartBody.Part> part);

    /**
     * 工单上报
     */
    @FormUrlEncoded
    @POST(APPEND_FH_ADDRESS_PARAM + "patrol/patrolReportInfo/addupdate")
    Observable<BasePostResponse<Object>> addupdate(@FieldMap Map<String, String> map);

    /**
     * 监护上报列表
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolReportInfo/getOrderReports?method=getOrderReports")
    Observable<BasePostResponse<List<OrderSuperviseReportBean>>> getOrderReports(@Query("orderId") String orderId);

    /**
     * 工单完成
     */
    @FormUrlEncoded
    @POST(APPEND_FH_ADDRESS_PARAM + "patrol/patrolOrderInfo/finishOrder")
    Observable<BasePostResponse<Object>> finishOrder(@Field("relevanceId") String relevanceId, @Field("id") String id);

    /**
     * 工单转移
     */
    @FormUrlEncoded
    @POST(APPEND_FH_ADDRESS_PARAM + "patrol/patrolOrderInfo/transferOrder")
    Observable<BasePostResponse<Object>> transferOrder(@FieldMap Map<String, String> map);

    /**
     * 获取人员列表
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "core/platform/userCombobox")
    Observable<ArrayList<UserCom>> userCombobox();

    /**
     * 查询分组权用户有树
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/permission/{path}")
    Observable<ArrayList<UserCom>> groupUser(@Path("path") String path);

    /**
     * 查询用户
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolGroupUsers/tree")
    Observable<ArrayList<UserCom>> getUser();

    /**
     * 更新下载次数
     *
     * @return BasePostResponse
     */
    @FormUrlEncoded
    @POST(APPEND_FH_ADDRESS_PARAM + "app/sysAppInfo/updateDownloadNum")
    Observable<BasePostResponse<Object>> updateDownloadNum(@FieldMap Map<String, String> map);

    /**
     * 派单
     *
     * @return BasePostResponse
     */
    @FormUrlEncoded
    @POST(APPEND_FH_ADDRESS_PARAM + "patrol/patrolOrderInfo/sendOrder")
    Observable<BasePostResponse<Object>> leafletsAddUpdate(@FieldMap Map<String, String> map);

    /**
     * 获取工地上报片区数据
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolAreaInfo/combobox")
    Observable<ArrayList<PatrolHdType>> getCombobox();

    /**
     * 获取当前登陆人的  网格ID
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolGroupUsers/getMapGrids")
    Observable<BasePostResponse<List<MapGridBean>>> getMapGrids();

    /**
     * 获取所有网格
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolMapGrid/page")
    Observable<BaseGetResponse<BasicGridBean>> getMapBasic();

    /**
     * 根据隐患对象获取隐患类型Type
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "patrol/patrolHdType/combobox")
    Observable<ArrayList<PatrolHdType>> getPatrolHdType(@Query("deviceType") String deviceType);

    /**
     * 获取字典表数据：
     */
    @GET(APPEND_FH_ADDRESS_PARAM + "dic/sysDicInfo/getDicCacheDao")
    Observable<BasePostResponse<DicCacheDaoData>> getDicCacheDao();

    /**
     * 隐患上报
     *
     * @return BasePostResponse
     */
    @FormUrlEncoded
    @POST(APPEND_FH_ADDRESS_PARAM + "patrol/patrolHdInfo/addupdate")
    Observable<BasePostResponse<Object>> hdSubmit(@FieldMap Map<String, String> map);

    /**
     * 工地上报
     *
     * @return BasePostResponse
     */
    @FormUrlEncoded
    @POST(APPEND_FH_ADDRESS_PARAM + "patrol/patrolConstructionSite/addupdate")
    Observable<BasePostResponse<Object>> siteSubmit(@FieldMap Map<String, String> map);

    /**
     * 巡检点上报
     *
     * @return BasePostResponse
     */
    @FormUrlEncoded
    @POST(APPEND_FH_ADDRESS_PARAM + "patrol/patrolTaskPoint/addupdate")
    Observable<BasePostResponse<Object>> pointSubmit(@FieldMap Map<String, String> map);

    /**
     * 设备上报
     *
     * @return BasePostResponse
     */
    @FormUrlEncoded
    @POST(APPEND_FH_ADDRESS_PARAM + "patrol/patrolDeviceCorrection/addupdate")
    Observable<BasePostResponse<Object>> devNewSubmit(@FieldMap Map<String, String> map);

    /**
     * 轨迹上传
     *
     * @param body List<LocationBean>
     * @return BasePostResponse
     */
    @POST(APPEND_FH_ADDRESS_PARAM + "gis/sysUserGisRecord/saveGisRecords")
    Observable<BasePostResponse<Object>> locationUpdate(@Body List<LocationBean> body);

    /**
     * 终端信息上传
     *
     * @param body List<TerminalInfoBean>
     * @return BasePostResponse
     */
    @POST(APPEND_FH_ADDRESS_PARAM + "log/sysMobile/saveSysMobile")
    Observable<BasePostResponse<Object>> terminalUpdate(@Body List<TerminalInfoBean> body);

    /**
     * 终端信息 GPS是否打开状态 上传
     *
     * @param map map
     * @return BasePostResponse
     */
    @FormUrlEncoded
    @POST(APPEND_FH_ADDRESS_PARAM + "core/sysUsers/updateGpsStatus")
    Observable<BasePostResponse<Object>> updateGps(@FieldMap Map<String, String> map);

    /**
     * 坐标转换（成都-经纬度）
     *
     * @return ModelAcoordResultBean ModelAcoordResultBean
     */
    @GET("process/notify/scada/testpush")
    Observable<ModelAcoordResultBean> getCoordinate(@QueryMap Map<String, String> map);

}
