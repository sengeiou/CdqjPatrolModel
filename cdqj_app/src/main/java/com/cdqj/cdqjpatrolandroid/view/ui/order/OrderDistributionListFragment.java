package com.cdqj.cdqjpatrolandroid.view.ui.order;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.cdqj.cdqjpatrolandroid.base.BaseRecyclerView;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.CheckDialog;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.CheckDialogBuild;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.CheckDoubleLvDialog;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmDialogListener;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmSelectDialog;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.DialogAdapter;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.OnCheckDialogSelectListener;
import com.cdqj.cdqjpatrolandroid.comstomview.dropdownmenu.SearchTabBean;
import com.cdqj.cdqjpatrolandroid.comstomview.dropdownmenu.SearchTabHelper;
import com.cdqj.cdqjpatrolandroid.comstomview.dropdownmenu.SelectSpinnerBean;
import com.cdqj.cdqjpatrolandroid.comstomview.recyclerview.SpacesItemDecoration;
import com.cdqj.cdqjpatrolandroid.config.GlobalConfig;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.adapter.OrderListAdapter;
import com.cdqj.cdqjpatrolandroid.base.BaseFragment;
import com.cdqj.cdqjpatrolandroid.bean.OrderBean;
import com.cdqj.cdqjpatrolandroid.bean.PiesChildItem;
import com.cdqj.cdqjpatrolandroid.bean.UserCom;
import com.cdqj.cdqjpatrolandroid.comstomview.StateView;
import com.cdqj.cdqjpatrolandroid.event.CenterEvent;
import com.cdqj.cdqjpatrolandroid.event.MsgEvent;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.RetrofitUtils;
import com.cdqj.cdqjpatrolandroid.presenter.OrderDistributionPresenter;
import com.cdqj.cdqjpatrolandroid.utils.OrderDataUtils;
import com.cdqj.cdqjpatrolandroid.utils.PatrolEnterPointActivity;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.view.i.IOrderDistributionView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.stetho.common.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;

/**
 * 巡线工单派发主界面
 *
 * @author lyf
 * @date 2018年7月25日 17:35:47
 */
public class OrderDistributionListFragment extends BaseFragment<OrderDistributionPresenter> implements IOrderDistributionView {

    BaseRecyclerView planAuditListRv;
    CoordinatorLayout fragmentOrderListXml;
    BaseRecyclerView searchTabRv;

    HashMap<String, String> leafletsMap = new HashMap<>();
    private OrderListAdapter adapter;
    /**
     * 人员筛选弹窗数据
     */
    private List<String> mListType = new ArrayList<>();
    private List<UserCom> userComs;
    private List<OrderBean> orderBeans;
    private String objectsStr = "", levelStr = "", startTime = "", endTime = "";

    public ArrayList<SelectSpinnerBean> areas = new ArrayList<>();
    public ArrayList<SelectSpinnerBean> types = new ArrayList<>();
    public ArrayList<SelectSpinnerBean> levels = new ArrayList<>();
    private Realm jrealm;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (ObjectUtils.isEmpty(jrealm)) {
            jrealm = Realm.getDefaultInstance();
        }
        if (isVisibleToUser) {
            types = OrderDataUtils.getCombobox(jrealm);
            levels = OrderDataUtils.getLevel(jrealm);
        }
    }

    @Override
    public void onGetDistributionOrderListSuccess(BaseGetResponse<OrderBean> body, boolean flag) {
        orderBeans = body.getRows();
        if (flag) {
            int total = body.getTotal();
            if (total != 0) {
                if (total % rows == 0) {
                    maxPage = total / rows;
                } else {
                    maxPage = total / rows + 1;
                }
            }
        }
        setAdapter(orderBeans);
        if (adapter.getData().size() == 0) {
            mStateView.showEmpty();
        } else {
            mStateView.showContent();
        }
    }

    private void setAdapter(List<OrderBean> list) {
        if (page == 1) {
            adapter.setNewData(list);
        } else {
            adapter.addData(list);
        }
    }

    @Override
    public void onGetUserCombobox(ArrayList<UserCom> userComs) {
        this.userComs = userComs;
        for (UserCom userCom : userComs) {
            mListType.add(userCom.getText());
        }
        showSelectMan(null);
    }

    @Override
    public void onAddUpdateSuccess(BasePostResponse<Object> body) {
        if (body.isSuccess()) {
            ToastBuilder.showShort("保存成功");
            EventBus.getDefault().post(new MsgEvent(true));
            page = 1;
            mPresenter.getDistributionOrderList(page, rows, true, objectsStr, levelStr, startTime, endTime);
        } else {
            ToastBuilder.showShortWarning(body.getMsg());
        }
    }

    /**
     * 人员筛选
     *
     * @param bean bean
     */
    private void showSelectMan(OrderBean bean) {
        if (mListType.isEmpty()) {
            mPresenter.getUserCombobox();
            return;
        }
        DialogAdapter adapter = new DialogAdapter((ArrayList<String>) mListType, getActivity());
        CheckDialogBuild dialogBuild = CheckDialog.build(getActivity(), mListType)
                .setSingle(true)
                .setSingleTextHeader(true)
                .setExpanded(true)
                .setTitle("请选择执行人员")
                .setBaseAdapter(adapter)
                .setCheckDialogSelect(new OnCheckDialogSelectListener() {
                    @Override
                    public void onCheckSelect(List titles, List positions) {

                    }

                    @Override
                    public void onCheckSingle(int position) {

                    }

                    @Override
                    public void onItemClickListener(int position) {
                        new Handler().postDelayed(() -> new ConfirmSelectDialog(getActivity())
                                .setTitleStr("提示")
                                .setContentStr("是否需要协同？")
                                .setYesStr("需要")
                                .setNoStr("不需要")
                                .setListener(new ConfirmDialogListener() {
                                    @Override
                                    public void onYesClick() {
                                        // 协同
                                        new Handler().postDelayed(() -> CheckDialog.build(getActivity(), mListType)
                                                .setSingle(false)
                                                .setExpanded(true)
                                                .setTitle("请选择协同人员（可多选）")
                                                .setBaseAdapter(adapter)
                                                .setCheckDialogSelect(new OnCheckDialogSelectListener() {
                                                    @Override
                                                    public void onCheckSelect(List titles, List positions) {
                                                        LogUtils.e(positions.toString());
                                                        for (Object o : positions) {
                                                            LogUtil.e(userComs.get(Integer.parseInt((String) o)).getText() + userComs.get(Integer.parseInt((String) o)).getValue());
                                                        }
                                                    }

                                                    @Override
                                                    public void onCheckSingle(int position1) {

                                                    }

                                                    @Override
                                                    public void onItemClickListener(int position1) {

                                                    }
                                                })
                                                .creatDialog()
                                                .show(), Constant.DIALOG_TIME);
                                    }

                                    @Override
                                    public void onNoClick() {
                                        // 不协同

                                    }
                                })
                                .initView()
                                .show(), Constant.DIALOG_TIME);
                    }
                });
        dialogBuild.creatDialog().show();
    }

    @Override
    public void initView(View rootView) {
        initView();
    }

    private void showPicker(ArrayList<String> item1, ArrayList<ArrayList<String>> item2) {
        for (int i = 0; i < 10; i++) {
            item1.add("班长" + i);
            ArrayList<String> item = new ArrayList<>();
            for (int k = 0; k < 8; k++) {
                item.add("组员" + i + k);
            }
            item2.add(item);
        }
        DialogAdapter adapter = new DialogAdapter(item1, getActivity());
        CheckDoubleLvDialog.build(getActivity(), item1, item2)
                .setBaseAdapter(adapter)
                .setSingleTextHeader(false)
                .setExpanded(true)
                .setSingle(true)
                .setTitle("请选择执行人员")
                .setCheckDialogSelect(new CheckDoubleLvDialog.OnDoubuleSelectListener() {
                    @Override
                    public void onDoubuleSelect(List titles, List positions, int gourpPosition) {

                        LogUtils.e(positions.toString());
                        LogUtils.e(titles.toString());
                        LogUtils.e(gourpPosition + "");
                    }

                    @Override
                    public void onSingleSelect(int gourpPosition, int childPosition) {
                        LogUtils.e(gourpPosition + "");
                        LogUtils.e(childPosition + "");
                    }
                }).creatDialog().show();
    }

    @Override
    public void showProgress() {
        baseShowProgress();
    }

    @Override
    public void showProgress(String str) {

    }

    @Override
    public void hideProgress() {
        baseHideProgress();
    }

    @Override
    public void onSuccess(String msg) {

    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable msg) {
        adapter.setNewData(new ArrayList<>());
        baseOnFailure(msg, true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        /*
         * 第一次选择人员
         */
        if (requestCode == 1) {
            PiesChildItem single = data.getParcelableExtra("single");
            if (single == null) {
                return;
            }
            leafletsMap.put("receiveUserId", single.getId());
            leafletsMap.put("receiveUserName", single.getText());
            leafletsMap.put("groupId", single.getPid());
            leafletsMap.put("isSynergy", 2 + "");
            leafletsMap.put("synergyIds", "");
            new ConfirmSelectDialog(getActivity())
                    .setTitleStr("提示")
                    .setContentStr("是否需要协同？")
                    .setYesStr("需要")
                    .setNoStr("不需要")
                    .setListener(new ConfirmDialogListener() {
                        @Override
                        public void onYesClick() {
                            PatrolEnterPointActivity.gotoPiesScreeningActivityForResult(OrderDistributionListFragment.this, 1, false, 2);
                        }

                        @Override
                        public void onNoClick() {
                            LogUtils.e(leafletsMap.toString());
                            mPresenter.addUpdate(leafletsMap);
                        }
                    }).initView().show();
            /*
             * 需要协同第二次多选人员返回
             */
        } else if (requestCode == 2) {
            ArrayList<PiesChildItem> muitResult = data.getParcelableArrayListExtra("muitResult");
            if (muitResult.isEmpty()) {
                return;
            }
            leafletsMap.put("isSynergy", 1 + "");
            StringBuilder receiveUserIds = new StringBuilder();
            for (PiesChildItem childItem : muitResult) {
                receiveUserIds.append(childItem.getId());
                receiveUserIds.append(",");
            }
            leafletsMap.put("synergyIds", receiveUserIds.substring(0, receiveUserIds.length() - 1));
            mPresenter.addUpdate(leafletsMap);
            LogUtils.e(leafletsMap.toString());
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_fragment_plan_audit_list;
    }

    @Override
    protected void findView(View rootView) {
        planAuditListRv = rootView.findViewById(R.id.plan_audit_list_rv);
        fragmentOrderListXml = rootView.findViewById(R.id.fragment_order_list_xml);
        searchTabRv = rootView.findViewById(R.id.search_tab_rv);
    }

    @Override
    protected String getTitleText() {
        return null;
    }

    @Override
    protected OrderDistributionPresenter createPresenter() {
        return new OrderDistributionPresenter(this);
    }

    @Override
    protected void loadData() {
        mPresenter.getDistributionOrderList(page, rows, true, objectsStr, levelStr, startTime, endTime);
    }

    @Override
    public void initRefresh() {
        mPresenter.getDistributionOrderList(page, rows, false, objectsStr, levelStr, startTime, endTime);

    }

    @Override
    public void initLoadMore() {
        mPresenter.getDistributionOrderList(page, rows, false, objectsStr, levelStr, startTime, endTime);

    }

    /**
     * 界面初始化
     */
    @SuppressLint("InflateParams")
    private void initView() {
        mStateView = StateView.inject(planAuditListRv);
        mStateView.setOnRetryClickListener(() -> {
            page = 1;
            mPresenter.getDistributionOrderList(page, rows, true, objectsStr, levelStr, startTime, endTime);
        });
        mPresenter = new OrderDistributionPresenter(this);
        planAuditListRv.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(10)));

        planAuditListRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        areas = new ArrayList<>();
        areas.add(new SelectSpinnerBean("全部"));
        areas.add(new SelectSpinnerBean("最近一天"));
        areas.add(new SelectSpinnerBean("最近三天"));
        areas.add(new SelectSpinnerBean("最近一周"));
        areas.add(new SelectSpinnerBean("最近一月"));

        List<SearchTabBean> mList = new ArrayList<>();
        mList.add(new SearchTabBean(false, false, "对象", types));
        mList.add(new SearchTabBean(false, false, "级别", levels));
        mList.add(new SearchTabBean(true, true, "时间", areas));
        new SearchTabHelper(mList, searchTabRv)
                .initView(getActivity(), searchTabRv, fragmentOrderListXml, (parentPosition, names, values, isSinge) -> {
                    if (parentPosition == 0) {
                        objectsStr = values;

                    } else if (parentPosition == 1) {
                        levelStr = values;

                    } else if (parentPosition == 2) {
                        if ("全部".equals(names)) {
                            endTime = "";
                            startTime = "";
                        } else if ("最近一天".equals(names)) {
                            endTime = TimeUtils.date2String(new Date());
                            startTime = TimeUtils.date2String(new Date(
                                    System.currentTimeMillis() - Constant.ONE_DAY
                            ));
                        } else if ("最近三天".equals(names)) {
                            endTime = TimeUtils.date2String(new Date());
                            startTime = TimeUtils.date2String(new Date(
                                    System.currentTimeMillis() - Constant.ONE_DAY * 3
                            ));
                        } else if ("最近一周".equals(names)) {
                            endTime = TimeUtils.date2String(new Date());
                            startTime = TimeUtils.date2String(new Date(
                                    System.currentTimeMillis() - Constant.ONE_DAY * 7
                            ));
                        } else if ("最近一月".equals(names)) {
                            endTime = TimeUtils.date2String(new Date());
                            startTime = TimeUtils.date2String(new Date(
                                    System.currentTimeMillis() - Constant.ONE_DAY * 30
                            ));
                        }

                    }
                    page = 1;
                    mPresenter.getDistributionOrderList(page, rows, false, objectsStr, levelStr, startTime, endTime);

                });

        adapter = new OrderListAdapter(R.layout.cdqj_patrol_order_list_item, new ArrayList<>(), getActivity(), 2);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.bindToRecyclerView(planAuditListRv);
        adapter.setOnItemClickListener((adapter, view, position) -> PatrolEnterPointActivity.gotoOrderDetailActivity(getActivity(), (OrderBean) (adapter.getData().get(position)), 1));
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (!GlobalConfig.isDoWork) {
                RetrofitUtils.doOnWork(getActivity(), 1);
                return;
            }
            if (view.getId() == R.id.order_item_distribute_submit) {
                leafletsMap.put("id", orderBeans.get(position).getId() + "");
                PatrolEnterPointActivity.gotoPiesScreeningActivityForResult(OrderDistributionListFragment.this, 1, true, 1);
            }
        });
    }

    @Override
    public void onDestroy() {
        if (ObjectUtils.isNotEmpty(jrealm)) {
            jrealm.close();
        }
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataChange(MsgEvent msgEvent) {
        if (msgEvent.getTag() != 1) {
            return;
        }
        if (msgEvent.isRefresh()) {
            page = 1;
            mPresenter.getDistributionOrderList(page, rows, false, objectsStr, levelStr, startTime, endTime);
            EventBus.getDefault().post(new CenterEvent(true));
        }
    }
}
