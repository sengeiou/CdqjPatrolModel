package com.cdqj.cdqjpatrolandroid.view.ui.order;

import android.annotation.SuppressLint;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.cdqj.cdqjpatrolandroid.base.BaseRecyclerView;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmDialogListener;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmSelectDialog;
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
import com.cdqj.cdqjpatrolandroid.comstomview.StateView;
import com.cdqj.cdqjpatrolandroid.event.CenterEvent;
import com.cdqj.cdqjpatrolandroid.event.MsgEvent;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.RetrofitUtils;
import com.cdqj.cdqjpatrolandroid.presenter.OrderMyPresenter;
import com.cdqj.cdqjpatrolandroid.utils.OrderDataUtils;
import com.cdqj.cdqjpatrolandroid.utils.PatrolEnterPointActivity;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.view.i.IOrderMyView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import io.realm.Realm;

/**
 * 巡线工单我的工单
 *
 * @author lyf
 * @date 2018年7月25日 17:35:47
 */
public class OrderMyListFragment extends BaseFragment<OrderMyPresenter> implements IOrderMyView {

    BaseRecyclerView planAuditListRv;
    CoordinatorLayout fragmentOrderListXml;
    BaseRecyclerView searchTabRv;

    private OrderListAdapter adapter;

    private String objectsStr = "", levelStr = "", startTime = "", endTime = "";

    public ArrayList<SelectSpinnerBean> areas = new ArrayList<>();
    public ArrayList<SelectSpinnerBean> types = new ArrayList<>();
    public ArrayList<SelectSpinnerBean> levels = new ArrayList<>();
    private Realm jrealm;
    private LinearLayoutManager linearLayoutManager;
    private List<OrderBean> orderBeanList = new ArrayList<>();

    public void setTopId(int id) {
        if (orderBeanList != null && orderBeanList.size() > 0) {
            for (int i = 0; i < orderBeanList.size(); i++) {
                if (id == orderBeanList.get(i).getId()) {
                    if (linearLayoutManager != null) {
                        linearLayoutManager.scrollToPositionWithOffset(i, 0);
                    }
                }
            }
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
    protected OrderMyPresenter createPresenter() {
        return new OrderMyPresenter(this);
    }

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
    public void initView(View rootView) {
        mStateView = StateView.inject(planAuditListRv);
        mStateView.setOnRetryClickListener(() -> {
            page = 1;
            mPresenter.getMyOrderList(page, rows, true, objectsStr, levelStr, startTime, endTime);
        });
    }

    @Override
    protected void loadData() {
        initView();
        mPresenter.getMyOrderList(page, rows, false, objectsStr, levelStr, startTime, endTime);
    }

    @Override
    public void initRefresh() {
        mPresenter.getMyOrderList(page, rows, false, objectsStr, levelStr, startTime, endTime);
    }

    @Override
    public void initLoadMore() {
        mPresenter.getMyOrderList(page, rows, false, objectsStr, levelStr, startTime, endTime);
    }

    /**
     * 界面初始化
     */
    @SuppressLint("InflateParams")
    private void initView() {
        mPresenter = new OrderMyPresenter(this);
        planAuditListRv.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(10)));
        linearLayoutManager = new LinearLayoutManager(getActivity());
        planAuditListRv.setLayoutManager(linearLayoutManager);

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
                    mPresenter.getMyOrderList(page, rows, false, objectsStr, levelStr, startTime, endTime);
                });

        adapter = new OrderListAdapter(R.layout.cdqj_patrol_order_list_item, new ArrayList<>(), getActivity(), 1);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.bindToRecyclerView(planAuditListRv);
        adapter.setOnItemClickListener((adapter, view, position) -> PatrolEnterPointActivity.gotoOrderDetailActivity(getActivity(), (OrderBean) (adapter.getData().get(position)), 1));
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (!GlobalConfig.isDoWork) {
                RetrofitUtils.doOnWork(getActivity(), 1);
                return;
            }
            List<OrderBean> data = adapter.getData();
            HashMap<String, String> map = new HashMap<>(1);
            map.put("id", data.get(position).getId() + "");
            int i = view.getId();
            if (i == R.id.order_item_receipt_submit) {
                mPresenter.takeOrders(map);
            } else if (i == R.id.order_item_refusal_submit) {
                new ConfirmSelectDialog(getActivity())
                        .setTitleStr("提示")
                        .setContentStr("确定拒单？")
                        .setYesStr("是")
                        .setNoStr("否")
                        .setListener(new ConfirmDialogListener() {
                            @Override
                            public void onYesClick() {
                                mPresenter.refuseOrders(map);
                            }

                            @Override
                            public void onNoClick() {

                            }
                        }).initView().show();
            } else if (i == R.id.order_item_supervise_submit) {
                PatrolEnterPointActivity.gotoOrderDetailActivity(getActivity(), (OrderBean) (adapter.getData().get(position)), 2);
            }
        });
    }

    @Override
    public void onGetMyOrderListSuccess(BaseGetResponse<OrderBean> body, boolean flag) {
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
        setAdapter(body.getRows());
        if (adapter.getData().size() == 0) {
            mStateView.showEmpty();
        } else {
            mStateView.showContent();
        }
    }

    private void setAdapter(List<OrderBean> list) {
        if (page == 1) {
            orderBeanList.clear();
            orderBeanList.addAll(list);
            adapter.setNewData(list);
        } else {
            orderBeanList.addAll(list);
            adapter.addData(list);
        }

    }

    @Override
    public void onTakeOrderSuccess(BasePostResponse<Object> body) {
        ToastBuilder.showShort(body.getMsg());
        page = 1;
        mPresenter.getMyOrderList(page, rows, true, objectsStr, levelStr, startTime, endTime);
    }

    @Override
    public void onRefuseOrdersSuccess(BasePostResponse<Object> body) {
        ToastBuilder.showShort(body.getMsg());
        page = 1;
        mPresenter.getMyOrderList(page, rows, true, objectsStr, levelStr, startTime, endTime);
    }


    @Override
    public void showProgress() {
        baseShowProgress();
    }

    @Override
    public void showProgress(String str) {
        baseShowProgress(str);
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
    public void onDestroy() {
        super.onDestroy();
        if (ObjectUtils.isNotEmpty(jrealm)) {
            jrealm.close();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataChange(MsgEvent msgEvent) {
        if (msgEvent.getTag() != 1) {
            return;
        }
        if (msgEvent.isRefresh()) {
            page = 1;
            mPresenter.getMyOrderList(page, rows, true, objectsStr, levelStr, startTime, endTime);
            EventBus.getDefault().post(new CenterEvent(true));
        }
    }
}
