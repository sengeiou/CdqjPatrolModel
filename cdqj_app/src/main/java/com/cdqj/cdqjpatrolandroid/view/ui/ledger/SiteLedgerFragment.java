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
import com.cdqj.cdqjpatrolandroid.adapter.SiteListAdapter;
import com.cdqj.cdqjpatrolandroid.base.BaseFragment;
import com.cdqj.cdqjpatrolandroid.bean.DicCacheDao;
import com.cdqj.cdqjpatrolandroid.bean.PatrolHdType;
import com.cdqj.cdqjpatrolandroid.bean.SiteBean;
import com.cdqj.cdqjpatrolandroid.bean.UserCom;
import com.cdqj.cdqjpatrolandroid.comstomview.StateView;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.presenter.SiteLedgerListPresenter;
import com.cdqj.cdqjpatrolandroid.utils.PatrolEnterPointActivity;
import com.cdqj.cdqjpatrolandroid.view.i.ISiteLedgerListView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 工地台帐
 *
 * @author lyf
 * 创建时间 2018年10月15日 17:25:28
 */
public class SiteLedgerFragment extends BaseFragment<SiteLedgerListPresenter> implements ISiteLedgerListView {

    BaseRecyclerView searchTabRv;
    BaseRecyclerView planLedgerListRv;
    CoordinatorLayout fragmentPlanListXml;

    public ArrayList<SelectSpinnerBean> status = new ArrayList<>();
    public ArrayList<SelectSpinnerBean> times = new ArrayList<>();
    public ArrayList<SelectSpinnerBean> persons = new ArrayList<>();
    private String objectsStr = "", levelStr = "", startTime = "", endTime = "", statusStr = "", personStr = "";

    private SiteListAdapter adapter;

    private ArrayList<SelectSpinnerBean> types = new ArrayList<>();
    private ArrayList<SelectSpinnerBean> levels = new ArrayList<>();
    private List<SearchTabBean> mList = new ArrayList<>(5);

    private Realm jrealm;

    @Override
    public void initView(View rootView) {
        initView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_fragment_site_ledger_list;
    }

    @Override
    protected void findView(View rootView) {
        searchTabRv = rootView.findViewById(R.id.search_tab_rv);
        planLedgerListRv = rootView.findViewById(R.id.plan_ledger_list_rv);
        fragmentPlanListXml = rootView.findViewById(R.id.fragment_plan_list_xml);
    }

    @Override
    protected String getTitleText() {
        return null;
    }

    @Override
    protected SiteLedgerListPresenter createPresenter() {
        return new SiteLedgerListPresenter(this);
    }

    @Override
    protected void loadData() {
        initSelectView();
        mPresenter.getSiteLedgerList(page, rows, true, objectsStr, levelStr, startTime, endTime, statusStr, personStr);
    }

    @Override
    public void initLoadMore() {
        mPresenter.getSiteLedgerList(page, rows, false, objectsStr, levelStr, startTime, endTime, statusStr, personStr);
    }

    @Override
    public void initRefresh() {
        mPresenter.getSiteLedgerList(page, rows, false, objectsStr, levelStr, startTime, endTime, statusStr, personStr);
    }

    /**
     * 界面初始化
     */
    @SuppressLint("InflateParams")
    private void initView() {
        mStateView = StateView.inject(planLedgerListRv);
        mStateView.setOnRetryClickListener(() -> {
            page = 1;
            mPresenter.getSiteLedgerList(page, rows, true, objectsStr, levelStr, startTime, endTime, statusStr, personStr);
        });

        planLedgerListRv.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(10)));
        planLedgerListRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<SiteBean> list = new ArrayList<>();
        adapter = new SiteListAdapter(R.layout.cdqj_patrol_site_list_item, list, getActivity());
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.bindToRecyclerView(planLedgerListRv);
        adapter.setOnItemClickListener((adapter, view, position) -> PatrolEnterPointActivity.gotoMapSiteDetailActivity(getActivity(), (SiteBean) adapter.getData().get(position), 2));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (ObjectUtils.isNotEmpty(jrealm)) {
            jrealm.close();
        }
    }

    private void initSelectView() {
        if (mList.isEmpty()) {
            mList.add(new SearchTabBean(false, false, "片区", types));
            mList.add(new SearchTabBean(false, false, "状态", status));
            mList.add(new SearchTabBean(false, false, "级别", levels));
            mList.add(new SearchTabBean(true, true, "时间", times));
            mList.add(new SearchTabBean(true, true, "上报人", persons));
        }
        new SearchTabHelper(mList, searchTabRv)
                .initView(getActivity(), searchTabRv, fragmentPlanListXml, (parentPosition, names, values, isSinge) -> {
                    switch (parentPosition) {
                        case 0:
                            objectsStr = values;
                            break;
                        case 1:
                            statusStr = values;
                            break;
                        case 2:
                            levelStr = values;
                            break;
                        case 3:
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
                        case 4:
                            personStr = values;
                            break;
                        default:
                            break;
                    }
                    mPresenter.getSiteLedgerList(page, rows, false, objectsStr, levelStr, startTime, endTime, statusStr, personStr);
                });
    }

    @Override
    public void onGetSiteLedgerListSuccess(BaseGetResponse<SiteBean> body, boolean flag) {
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

    private void setAdapter(List<SiteBean> list) {
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (types.isEmpty()) {
                jrealm = Realm.getDefaultInstance();
                RealmResults<PatrolHdType> dicCacheDaos = jrealm.where(PatrolHdType.class).equalTo("moduleCode", "AREA").findAll();
                for (PatrolHdType dicCacheDao : dicCacheDaos) {
                    types.add(new SelectSpinnerBean(dicCacheDao.getText(), String.valueOf(dicCacheDao.getValue())));
                }
            }

            if (status.isEmpty()) {
                jrealm = Realm.getDefaultInstance();
                RealmResults<DicCacheDao> dicCacheDaos = jrealm.where(DicCacheDao.class).equalTo("moduleCode", "DATA_STATUS").findAll();
                for (DicCacheDao dicCacheDao : dicCacheDaos) {
                    status.add(new SelectSpinnerBean(dicCacheDao.getDicName(), dicCacheDao.getDicCode()));
                }
            }

            if (levels.isEmpty()) {
                jrealm = Realm.getDefaultInstance();
                RealmResults<DicCacheDao> dicCacheDaos = jrealm.where(DicCacheDao.class).equalTo("moduleCode", "SITE_LEVEL").findAll();
                for (DicCacheDao dicCacheDao : dicCacheDaos) {
                    levels.add(new SelectSpinnerBean(dicCacheDao.getDicName(), dicCacheDao.getDicCode()));
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
