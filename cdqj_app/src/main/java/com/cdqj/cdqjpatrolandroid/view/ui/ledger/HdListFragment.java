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
import com.cdqj.cdqjpatrolandroid.adapter.HdListAdapter;
import com.cdqj.cdqjpatrolandroid.base.BaseFragment;
import com.cdqj.cdqjpatrolandroid.bean.DicCacheDao;
import com.cdqj.cdqjpatrolandroid.bean.HdOrderBean;
import com.cdqj.cdqjpatrolandroid.bean.UserCom;
import com.cdqj.cdqjpatrolandroid.comstomview.StateView;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.presenter.HdListPresenter;
import com.cdqj.cdqjpatrolandroid.utils.OrderDataUtils;
import com.cdqj.cdqjpatrolandroid.utils.PatrolEnterPointActivity;
import com.cdqj.cdqjpatrolandroid.view.i.IHdListView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 巡线台账隐患
 *
 * @author lyf
 * @date 2018年7月25日 17:35:47
 */
public class HdListFragment extends BaseFragment<HdListPresenter> implements IHdListView {

    BaseRecyclerView planAuditListRv;
    CoordinatorLayout fragmentOrderListXml;
    BaseRecyclerView searchTabRv;

    private HdListAdapter adapter;
    private Realm jrealm;

    private List<SearchTabBean> mList = new ArrayList<>(5);
    private ArrayList<SelectSpinnerBean> status = new ArrayList<>();
    private ArrayList<SelectSpinnerBean> times = new ArrayList<>();
    private ArrayList<SelectSpinnerBean> persons = new ArrayList<>();
    public ArrayList<SelectSpinnerBean> hdTypes = new ArrayList<>();
    public ArrayList<SelectSpinnerBean> hdLevels = new ArrayList<>();
    private String objectsStr = "", hdLevelstr = "", startTime = "", endTime = "", statusStr = "", personStr = "";

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
    protected HdListPresenter createPresenter() {
        return new HdListPresenter(this);
    }

    @Override
    protected void loadData() {
        initSelectView();
        assert myInfoRefreshLayout != null;
        myInfoRefreshLayout.autoRefresh();
        mPresenter.getHdList(page, rows, false, objectsStr, hdLevelstr, startTime, endTime, statusStr, personStr);

    }

    /**
     * 界面初始化
     */
    @SuppressLint("InflateParams")
    @Override
    public void initView(View view1) {
        mStateView = StateView.inject(planAuditListRv);
        mStateView.setOnRetryClickListener(() -> {
            page = 1;
            mPresenter.getHdList(page, rows, true, objectsStr, hdLevelstr, startTime, endTime, statusStr, personStr);
        });
        planAuditListRv.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(10)));
        planAuditListRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new HdListAdapter(R.layout.cdqj_patrol_order_list_item, new ArrayList<>(), getActivity(), 4);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.bindToRecyclerView(planAuditListRv);
        adapter.setOnItemClickListener((adapter, view, position) -> PatrolEnterPointActivity.gotoHdDetailActivity(getActivity(), (HdOrderBean) (adapter.getData().get(position))));

    }

    @Override
    public void initRefresh() {
        mPresenter.getHdList(page, rows, false, objectsStr, hdLevelstr, startTime, endTime, statusStr, personStr);
    }

    @Override
    public void initLoadMore() {
        mPresenter.getHdList(page, rows, false, objectsStr, hdLevelstr, startTime, endTime, statusStr, personStr);
    }

    @Override
    public void onGetHdListSuccess(BaseGetResponse<HdOrderBean> body, boolean flag) {
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

    private void setAdapter(List<HdOrderBean> list) {
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
        if (ObjectUtils.isEmpty(jrealm)) {
            jrealm = Realm.getDefaultInstance();
        }
        if (isVisibleToUser) {
            hdTypes = OrderDataUtils.getHdCombobox(jrealm);
            hdLevels = OrderDataUtils.getHdLevel(jrealm);
            if (status.isEmpty()) {
                RealmResults<DicCacheDao> dicCacheDaos = jrealm.where(DicCacheDao.class).equalTo("moduleCode", "DATA_STATUS").findAll();
                for (DicCacheDao dicCacheDao : dicCacheDaos) {
                    status.add(new SelectSpinnerBean(dicCacheDao.getDicName(), dicCacheDao.getDicCode()));
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

    private void initSelectView() {
        if (mList.isEmpty()) {
            mList.add(new SearchTabBean(false, false, "对象", hdTypes));
            mList.add(new SearchTabBean(false, false, "状态", status));
            mList.add(new SearchTabBean(false, false, "级别", hdLevels));
            mList.add(new SearchTabBean(true, true, "时间", times));
            mList.add(new SearchTabBean(true, true, "上报人", persons));
        }
        new SearchTabHelper(mList, searchTabRv)
                .initView(getActivity(), searchTabRv, fragmentOrderListXml, (parentPosition, names, values, isSinge) -> {
                    if (parentPosition == 0) {
                        objectsStr = values;

                    } else if (parentPosition == 1) {
                        statusStr = values;

                    } else if (parentPosition == 2) {
                        hdLevelstr = values;

                    } else if (parentPosition == 3) {
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

                    } else if (parentPosition == 4) {
                        personStr = values;

                    }
                    page = 1;
                    mPresenter.getHdList(page, rows, false, objectsStr, hdLevelstr, startTime, endTime, statusStr, personStr);
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (ObjectUtils.isNotEmpty(jrealm)) {
            jrealm.close();
        }
    }
}
