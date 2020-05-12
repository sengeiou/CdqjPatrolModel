package com.cdqj.cdqjpatrolandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.blankj.utilcode.util.ActivityUtils;
import com.cdqj.cdqjpatrolandroid.bean.CarBean;
import com.cdqj.cdqjpatrolandroid.bean.DeviceBean;
import com.cdqj.cdqjpatrolandroid.bean.HdOrderBean;
import com.cdqj.cdqjpatrolandroid.bean.LoginResultBean;
import com.cdqj.cdqjpatrolandroid.bean.OrderBean;
import com.cdqj.cdqjpatrolandroid.bean.PatrolTaskResultBean;
import com.cdqj.cdqjpatrolandroid.bean.PlanListBean;
import com.cdqj.cdqjpatrolandroid.bean.SiteBean;
import com.cdqj.cdqjpatrolandroid.bean.UserLayerBean;
import com.cdqj.cdqjpatrolandroid.view.ui.main.CdqjMainActivity;
import com.cdqj.cdqjpatrolandroid.view.ui.ledger.HdDetailActivity;
import com.cdqj.cdqjpatrolandroid.view.ui.map.MapDeviceDetailActivity;
import com.cdqj.cdqjpatrolandroid.view.ui.map.MapHdDetailActivity;
import com.cdqj.cdqjpatrolandroid.view.ui.map.MapPersonDetailActivity;
import com.cdqj.cdqjpatrolandroid.view.ui.map.MapPipelineDetailActivity;
import com.cdqj.cdqjpatrolandroid.view.ui.map.MapPlanDetailActivity;
import com.cdqj.cdqjpatrolandroid.view.ui.map.MapSiteDetailActivity;
import com.cdqj.cdqjpatrolandroid.view.ui.my.MsgFeedbackActivity;
import com.cdqj.cdqjpatrolandroid.view.ui.my.MsgListActivity;
import com.cdqj.cdqjpatrolandroid.view.ui.my.OffLineDataActivity;
import com.cdqj.cdqjpatrolandroid.view.ui.order.OrderDetailActivity;
import com.cdqj.cdqjpatrolandroid.view.ui.map.PersonListActivity;
import com.cdqj.cdqjpatrolandroid.view.ui.map.PersonTrajectoryMapActivity;
import com.cdqj.cdqjpatrolandroid.view.ui.order.PiesScreeningActivity;
import com.cdqj.cdqjpatrolandroid.view.ui.plan.PlanDetailActivity;
import com.cdqj.cdqjpatrolandroid.view.ui.plan.PlanFormulateDetailActivity;
import com.cdqj.cdqjpatrolandroid.view.ui.ledger.PlanLedgerListActivity;
import com.cdqj.cdqjpatrolandroid.view.ui.plan.PlanLogActivity;
import com.cdqj.cdqjpatrolandroid.view.ui.plan.PlanModifyActivity;
import com.cdqj.cdqjpatrolandroid.view.ui.main.SetActivity;
import com.cdqj.cdqjpatrolandroid.view.ui.map.MapCarDetailActivity;

/**
 * Created by lyf on 2018/7/27 17:06
 *
 * @author lyf
 * desc：巡线跳转工具类
 */
public class PatrolEnterPointActivity {

    /**
     * 跳转到设置界面
     * @param context context
     */
    public static void gotoSetActivity(Context context) {
        Intent intent = new Intent(context, SetActivity.class);
        ActivityUtils.startActivity(intent);
    }

    /**
     * 跳转到首页界面
     * @param context context
     */
    public static void gotoCdqjMainActivity(Context context, LoginResultBean objBean) {
        Intent intent = new Intent(context, CdqjMainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("userInfo", objBean);
        intent.putExtras(bundle);
        ActivityUtils.startActivity(intent);
    }

    /**
     * 跳转到管线详情
     * @param context context
     */
    public static void gotoMapPipDetailActivity(Context context, String mapStr, String geoStr, double dis, int geometryType) {
        Intent intent = new Intent(context, MapPipelineDetailActivity.class);
        intent.putExtra("mapStr", mapStr);
        intent.putExtra("geoStr", geoStr);
        intent.putExtra("dis", dis);
        intent.putExtra("geometryType", geometryType);
        ActivityUtils.startActivity(intent);
    }

    /**
     * 跳转到设备详情
     * @param context context
     */
    public static void gotoMapDeviceDetailActivity(Context context, DeviceBean deviceBean) {
        Intent intent = new Intent(context, MapDeviceDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("DeviceBean", deviceBean);
        ActivityUtils.startActivity(intent);
    }

    /**
     * 跳转到工单详情
     * @param context context
     * @param bean bean
     * @param flag 1.详情
     *             2.上报/完成
     */
    public static void gotoOrderDetailActivity(Context context, OrderBean bean, int flag) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("OrderBean", bean);
        intent.putExtras(bundle);
        intent.putExtra("flag", flag);
        ActivityUtils.startActivity(intent);
    }

    /**
     * 跳转到工地详情
     * @param context context
     * @param taskBean taskBean
     * @param flag 1 地图进入
     *             2 台帐进入
     */
    public static void gotoMapSiteDetailActivity(Context context, SiteBean taskBean, int flag) {
        Intent intent = new Intent(context, MapSiteDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("SiteBean", taskBean);
        intent.putExtras(bundle);
        intent.putExtra("flag", flag);
        ActivityUtils.startActivity(intent);
    }

    /**
     * 跳转到车辆详情
     * @param context context
     * @param carBean carBean
     */
    public static void gotoMapCarDetailActivity(Context context, CarBean carBean) {
        Intent intent = new Intent(context, MapCarDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("CarBean", carBean);
        intent.putExtras(bundle);
        ActivityUtils.startActivity(intent);
    }

    /**
     * 跳转到隐患详情
     * @param context context
     * @param taskBean taskBean
     */
    public static void gotoMapHdDetailActivity(Context context, HdOrderBean taskBean) {
        Intent intent = new Intent(context, MapHdDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("HdOrderBean", taskBean);
        intent.putExtras(bundle);
        ActivityUtils.startActivity(intent);
    }

    /**
     * 跳转到计划详情
     * @param context context
     * @param taskBean taskBean
     */
    public static void gotoMapPlanDetailActivity(Context context, PatrolTaskResultBean taskBean) {
        Intent intent = new Intent(context, MapPlanDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("PatrolTaskResultBean", taskBean);
        intent.putExtras(bundle);
        ActivityUtils.startActivity(intent);
    }

    /**
     * 跳转到人员详情
     * @param context context
     * @param taskBean taskBean
     */
    public static void gotoMapPersonDetailActivity(Context context, UserLayerBean taskBean) {
        Intent intent = new Intent(context, MapPersonDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("UserLayerBean", taskBean);
        intent.putExtras(bundle);
        ActivityUtils.startActivity(intent);
    }

    /**
     * 跳转到隐患详情
     * @param context context
     * @param bean bean
     */
    public static void gotoHdDetailActivity(Context context, HdOrderBean bean) {
        Intent intent = new Intent(context, HdDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("HdOrderBean", bean);
        intent.putExtras(bundle);
        ActivityUtils.startActivity(intent);
    }

    /**
     * 跳转到计划详情
     * @param context context
     * @param bean PlanListBean
     * @param flag 1 我的计划
     *             2 计划制定
     *             3 计划审核
     *             4 计划台帐
     */
    public static void gotoPlanDetailActivity(Context context, PlanListBean bean, int flag) {
        Intent intent = new Intent(context, PlanDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("bean", bean);
        intent.putExtras(bundle);
        intent.putExtra("flag", flag);
        ActivityUtils.startActivity(intent);
    }

    /**
     * 跳转到计划详情
     * @param context context
     * @param planId planId
     */
    public static void gotoPlanLedgerListActivity(Context context, String planId) {
        Intent intent = new Intent(context, PlanLedgerListActivity.class);
        intent.putExtra("planId", planId);
        ActivityUtils.startActivity(intent);
    }

    /**
     * 跳转到计划制定界面
     * @param context context
     * @param position position
     *                 0 计划任务
     *                 1 临时任务
     */
    public static void gotoPlanFormulateDetailActivity(Context context, int position) {
        Intent intent = new Intent(context, PlanFormulateDetailActivity.class);
        intent.putExtra("position", position);
        ActivityUtils.startActivity(intent);
    }

    /**
     * 跳转到计划日志界面
     * @param context context
     * @param planId planId 计划ID
     */
    public static void gotoPlanLogActivity(Context context, String planId) {
        Intent intent = new Intent(context, PlanLogActivity.class);
        intent.putExtra("planId", planId);
        ActivityUtils.startActivity(intent);
    }

    /**
     * 跳转到计划修改界面
     * @param context context
     * @param mBean mBean
     */
    public static void gotoPlanModifyActivity(Context context, PlanListBean mBean) {
        Intent intent = new Intent(context, PlanModifyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("mBean", mBean);
        intent.putExtras(bundle);
        ActivityUtils.startActivity(intent);
    }

    /**
     * 跳转到意见反馈界面
     * @param context context
     */
    public static void gotoMsgFeedbackActivity(Context context) {
        Intent intent = new Intent(context, MsgFeedbackActivity.class);
        ActivityUtils.startActivity(intent);
    }

    /**
     * 跳转到离线数据管理界面
     * @param context context
     */
    public static void gotoOffLineDataActivity(Context context) {
        Intent intent = new Intent(context, OffLineDataActivity.class);
        ActivityUtils.startActivity(intent);
    }

    /**
     * 跳转到消息列表管理界面
     * @param context context
     */
    public static void gotoMsgListActivity(Context context) {
        Intent intent = new Intent(context, MsgListActivity.class);
        ActivityUtils.startActivity(intent);
    }

    /**
     * 跳转到人选数选择界面
     * @param context context
     * @param isSingle 是否单选
     * @param requestCode requestCode
     * @param flag 1 工单派单人员选择
     *             2 临时计划制定派发人员
     */
    public static void gotoPiesScreeningActivityForResult(Activity context, int flag, boolean isSingle, int requestCode) {
        Intent intent = new Intent(context, PiesScreeningActivity.class);
        intent.putExtra("flag", flag);
        intent.putExtra("isSingle", isSingle);
        ActivityUtils.startActivityForResult(context, intent, requestCode);
    }

    /**
     * 跳转到人选数选择界面
     * @param context context
     * @param isSingle 是否单选
     * @param requestCode requestCode
     * @param flag 1 工单派单人员选择
     *             2 临时计划制定派发人员
     */
    public static void gotoPiesScreeningActivityForResult(Fragment context, int flag, boolean isSingle, int requestCode) {
        Intent intent = new Intent(context.getActivity(), PiesScreeningActivity.class);
        intent.putExtra("flag", flag);
        intent.putExtra("isSingle", isSingle);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转到人员轨迹管理界面
     * @param context context
     * @param id 人员ID
     */
    public static void gotoPersonTrajectoryMapActivity(Activity context, int id) {
        Intent intent = new Intent(context, PersonTrajectoryMapActivity.class);
        intent.putExtra("id", String.valueOf(id));
        ActivityUtils.startActivity(intent);
    }

    /**
     * 跳转到人员列表
     * @param context context
     */
    public static void gotoPersonListActivity(Activity context) {
        Intent intent = new Intent(context, PersonListActivity.class);
        ActivityUtils.startActivity(intent);
    }
}
