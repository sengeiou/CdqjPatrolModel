package com.cdqj.cdqjpatrolandroid.view.ui.ledger;

import android.annotation.SuppressLint;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.cdqj.cdqjpatrolandroid.base.BaseRecyclerView;
import com.cdqj.cdqjpatrolandroid.comstomview.dropdownmenu.SearchTabBean;
import com.cdqj.cdqjpatrolandroid.comstomview.dropdownmenu.SearchTabHelper;
import com.cdqj.cdqjpatrolandroid.comstomview.dropdownmenu.SelectSpinnerBean;
import com.cdqj.cdqjpatrolandroid.comstomview.recyclerview.SpacesItemDecoration;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.adapter.PlanFormulateListAdapter;
import com.cdqj.cdqjpatrolandroid.base.BaseFragment;
import com.cdqj.cdqjpatrolandroid.bean.PatrolHdType;
import com.cdqj.cdqjpatrolandroid.bean.PlanListBean;
import com.cdqj.cdqjpatrolandroid.bean.UserCom;
import com.cdqj.cdqjpatrolandroid.comstomview.StateView;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.presenter.PlanLedgerPresenter;
import com.cdqj.cdqjpatrolandroid.utils.PatrolEnterPointActivity;
import com.cdqj.cdqjpatrolandroid.view.i.IPlanLedgerView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 计划台帐
 *
 * @author lyf
 * 创建时间 2018年10月15日 17:25:28
 */
public class PlanLedgerFragment extends BaseFragment<PlanLedgerPresenter> implements IPlanLedgerView {

    BaseRecyclerView planLedgerListRv;
    CoordinatorLayout fragmentPlanListXml;
    BaseRecyclerView searchTabRv;

    private ArrayList<SelectSpinnerBean> persons = new ArrayList<>(), times = new ArrayList<>(), areas = new ArrayList<>();
    private List<SearchTabBean> mList = new ArrayList<>();

    private List<PlanListBean> list;

    private PlanFormulateListAdapter adapter;

    private String areaStr = "", personStr = "", startTime = "", endTime = "";

    private Realm jrealm;


    @Override
    public void initView(View rootView) {
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_fragment_plan_ledger_list;
    }

    @Override
    protected void findView(View rootView) {
        planLedgerListRv = rootView.findViewById(R.id.plan_ledger_list_rv);
        fragmentPlanListXml = rootView.findViewById(R.id.fragment_plan_list_xml);
        searchTabRv = rootView.findViewById(R.id.search_tab_rv);
    }

    @Override
    protected String getTitleText() {
        return "计划列表";
    }

    @Override
    protected PlanLedgerPresenter createPresenter() {
        return new PlanLedgerPresenter(this);
    }

    @Override
    protected void loadData() {
        initSelectView();
        mPresenter.getPlanLedgerList(page, rows, true, areaStr, startTime, endTime, personStr);
    }

    @Override
    public void initRefresh() {
        mPresenter.getPlanLedgerList(page, rows, false, areaStr, startTime, endTime, personStr);
    }

    @Override
    public void initLoadMore() {
        mPresenter.getPlanLedgerList(page, rows, false, areaStr, startTime, endTime, personStr);
    }

    /**
     * 界面初始化
     */
    @SuppressLint("InflateParams")
    private void initView() {
        mStateView = StateView.inject(planLedgerListRv);
        mStateView.setOnRetryClickListener(() -> {
            page = 1;
            mPresenter.getPlanLedgerList(page, rows, true, areaStr, startTime, endTime, personStr);
        });

        planLedgerListRv.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(10)));
        planLedgerListRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        list = new ArrayList<>();
        adapter = new PlanFormulateListAdapter(list, getActivity());
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.bindToRecyclerView(planLedgerListRv);
        adapter.setOnItemClickListener((adapter, view, position) -> PatrolEnterPointActivity.gotoPlanLedgerListActivity(getActivity(), ((PlanListBean) adapter.getData().get(position)).getId()));
    }

    @Override
    public void onDestroyView() {
        if (ObjectUtils.isNotEmpty(jrealm)) {
            jrealm.close();
        }
        super.onDestroyView();
    }

    @Override
    public void onGetPlanLedgerListSuccess(BaseGetResponse<PlanListBean> body, boolean flag) {
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

    private void setAdapter(List<PlanListBean> list) {
        if (page == 1) {
            adapter.setNewData(list);
        } else {
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

    private void initSelectView() {
        if (mList.isEmpty()) {
            mList.add(new SearchTabBean(false, false, "片区", areas));
            mList.add(new SearchTabBean(true, true, "时间", times));
            mList.add(new SearchTabBean(true, true, "申请人", persons));
        }
        new SearchTabHelper(mList, searchTabRv)
                .initView(getActivity(), searchTabRv, fragmentPlanListXml, (parentPosition, names, values, isSinge) -> {
                    switch (parentPosition) {
                        case 0:
                            areaStr = values;
                            break;
                        case 1:
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
                            break;
                        case 2:
                            personStr = values;
                            break;
                        default:
                            break;
                    }
                    page = 1;
                    mPresenter.getPlanLedgerList(page, rows, false, areaStr, startTime, endTime, personStr);
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

            if (persons.isEmpty()) {
                jrealm = Realm.getDefaultInstance();
                jrealm = Realm.getDefaultInstance();
                RealmResults<UserCom> dicCacheDaos = jrealm.where(UserCom.class).findAll();
                if (!dicCacheDaos.isEmpty()) {
                    persons.add(new SelectSpinnerBean("全部", ""));
                    for (UserCom userCom : dicCacheDaos) {
                        persons.add(new SelectSpinnerBean(userCom.getText(), String.valueOf(userCom.getId())));
                    }
                }
            }
        }
    }
}
