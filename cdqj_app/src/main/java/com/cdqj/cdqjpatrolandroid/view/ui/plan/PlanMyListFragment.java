package com.cdqj.cdqjpatrolandroid.view.ui.plan;

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
import com.cdqj.cdqjpatrolandroid.event.MsgEvent;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.adapter.PlanMyListAdapter;
import com.cdqj.cdqjpatrolandroid.base.BaseFragment;
import com.cdqj.cdqjpatrolandroid.bean.PatrolHdType;
import com.cdqj.cdqjpatrolandroid.bean.PlanListBean;
import com.cdqj.cdqjpatrolandroid.comstomview.StateView;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.RetrofitUtils;
import com.cdqj.cdqjpatrolandroid.presenter.PlanMyPresenter;
import com.cdqj.cdqjpatrolandroid.utils.PatrolEnterPointActivity;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.view.i.IPlanMyView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by lyf on 2018/8/6 14:40
 *
 * @author lyf
 * desc：我的计划
 */
public class PlanMyListFragment extends BaseFragment<PlanMyPresenter> implements IPlanMyView {

    BaseRecyclerView planAuditListRv;
    CoordinatorLayout fragmentOrderListXml;
    BaseRecyclerView searchTabRv;

    private ArrayList<SelectSpinnerBean> times = new ArrayList<>(), areas = new ArrayList<>();
    private List<SearchTabBean> mList = new ArrayList<>();

    private List<PlanListBean> list;

    private PlanMyListAdapter adapter;
    private String areaStr = "", startTime = "", endTime = "";

    private Realm jrealm;
    private List<PlanListBean> planListBeanList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;

    public void setTopId(int id) {
        if (planListBeanList != null && planListBeanList.size() > 0) {
            for (int i = 0; i < planListBeanList.size(); i++) {
                if (id == Integer.parseInt(planListBeanList.get(i).getId())) {
                    if (linearLayoutManager != null) {
                        linearLayoutManager.scrollToPositionWithOffset(i, 0);
                    }
                }
            }
        }

    }

    @Override
    public void initView(View rootView) {
        initView();
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
    protected PlanMyPresenter createPresenter() {
        return new PlanMyPresenter(this);
    }

    @Override
    protected void loadData() {
        initSelectView();
        assert myInfoRefreshLayout != null;
        myInfoRefreshLayout.autoRefresh();
        mPresenter.getMyPlanList(page, rows, false, areaStr, startTime, endTime);
    }

    @Override
    public void initRefresh() {
        mPresenter.getMyPlanList(page, rows, false, areaStr, startTime, endTime);
    }

    @Override
    public void initLoadMore() {
        mPresenter.getMyPlanList(page, rows, false, areaStr, startTime, endTime);
    }

    /**
     * 界面初始化
     */
    @SuppressLint("InflateParams")
    private void initView() {
        mStateView = StateView.inject(planAuditListRv);
        mStateView.setOnRetryClickListener(() -> {
            page = 1;
            mPresenter.getMyPlanList(page, rows, true, areaStr, startTime, endTime);
        });

        planAuditListRv.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(10)));
        linearLayoutManager = new LinearLayoutManager(getActivity());
        planAuditListRv.setLayoutManager(linearLayoutManager);

        list = new ArrayList<>();
        adapter = new PlanMyListAdapter(list, getActivity(), true);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.bindToRecyclerView(planAuditListRv);
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (!GlobalConfig.isDoWork) {
                RetrofitUtils.doOnWork(getActivity(), 1);
                return;
            }
            int i = view.getId();
            if (i == R.id.plan_other_submit) {// 开始
                new ConfirmSelectDialog(getActivity())
                        .setContentStr("是否确定执行计划开始")
                        .setYesStr("确定")
                        .setNoStr("取消")
                        .setListener(new ConfirmDialogListener() {
                            @Override
                            public void onYesClick() {
                                mPresenter.updatePlanTaskStatus(((List<PlanListBean>) adapter.getData()).get(position).getTaskId(), true);
                            }

                            @Override
                            public void onNoClick() {

                            }
                        }).initView().show();
            } else if (i == R.id.plan_submit) {// 完成
                new ConfirmSelectDialog(getActivity())
                        .setContentStr("是否确定执行计划完成")
                        .setYesStr("确定")
                        .setNoStr("取消")
                        .setListener(new ConfirmDialogListener() {
                            @Override
                            public void onYesClick() {
                                mPresenter.updatePlanTaskStatus(((List<PlanListBean>) adapter.getData()).get(position).getTaskId(), false);
                            }

                            @Override
                            public void onNoClick() {

                            }
                        }).initView().show();
            }
        });
        adapter.setOnItemClickListener((adapter, view, position) -> PatrolEnterPointActivity.gotoPlanDetailActivity(getActivity(), (PlanListBean) adapter.getData().get(position), 1));
    }

    @Override
    public void onDestroyView() {
        if (ObjectUtils.isNotEmpty(jrealm)) {
            jrealm.close();
        }
        super.onDestroyView();
    }

    @Override
    public void onGetMyPlanListSuccess(BaseGetResponse<PlanListBean> body, boolean flag) {
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
        List<PlanListBean> newList = body.getRows();
        for (int i = 0; i < newList.size(); i++) {
            if (newList.get(i).getStatus() == 3) {
                newList.get(i).setItemType(PlanListBean.START);
            } else {
                newList.get(i).setItemType(PlanListBean.OTHER);
            }
        }
        setAdapter(newList);
        if (adapter.getData().size() == 0) {
            mStateView.showEmpty();
        } else {
            mStateView.showContent();
        }
    }

    @Override
    public void onUpdatePlanTaskStatusSuccess(BasePostResponse<Object> body, boolean flag) {
        if (body.isSuccess()) {
            // 计划执行成功
            mPresenter.getMyPlanList(page, rows, false, areaStr, startTime, endTime);
            ToastBuilder.showShort(body.getMsg());
        } else {
            hideProgress();
            ToastBuilder.showShortError(body.getMsg());
        }
    }

    private void setAdapter(List<PlanListBean> list) {
        if (page == 1) {
            planListBeanList.clear();
            planListBeanList.addAll(list);
            adapter.setNewData(list);
        } else {
            planListBeanList.addAll(list);
            adapter.addData(list);
        }

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataChange(MsgEvent msgEvent) {
        if (msgEvent.getTag() != 2 || !msgEvent.isRefresh()) {
            return;
        }
        page = 1;
        assert myInfoRefreshLayout != null;
        myInfoRefreshLayout.autoRefresh();
        mPresenter.getMyPlanList(page, rows, false, areaStr, startTime, endTime);
    }

    private void initSelectView() {
        if (mList.isEmpty()) {
            mList.add(new SearchTabBean(false, false, "片区", areas));
            mList.add(new SearchTabBean(true, true, "时间", times));
        }
        new SearchTabHelper(mList, searchTabRv)
                .initView(getActivity(), searchTabRv, fragmentOrderListXml, (parentPosition, names, values, isSinge) -> {
                    if (parentPosition == 0) {
                        areaStr = values;
                    } else if (parentPosition == 1) {
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
                    mPresenter.getMyPlanList(page, rows, false, areaStr, startTime, endTime);
                });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (areas.isEmpty()) {
                jrealm = Realm.getDefaultInstance();
                RealmResults<PatrolHdType> dicCacheDaos = jrealm.where(PatrolHdType.class).equalTo("moduleCode", "AREA").findAll();
                for (PatrolHdType dicCacheDao : dicCacheDaos) {
                    areas.add(new SelectSpinnerBean(dicCacheDao.getText(), String.valueOf(dicCacheDao.getValue())));
                }
            }

            if (times.isEmpty()) {
                times.add(new SelectSpinnerBean("全部"));
                times.add(new SelectSpinnerBean("最近一天"));
                times.add(new SelectSpinnerBean("最近三天"));
                times.add(new SelectSpinnerBean("最近一周"));
                times.add(new SelectSpinnerBean("最近一月"));
            }
        }
    }
}
