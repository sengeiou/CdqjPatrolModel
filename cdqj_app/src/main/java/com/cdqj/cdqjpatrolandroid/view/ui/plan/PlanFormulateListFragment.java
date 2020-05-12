package com.cdqj.cdqjpatrolandroid.view.ui.plan;

import android.annotation.SuppressLint;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.cdqj.cdqjpatrolandroid.base.BaseRecyclerView;
import com.cdqj.cdqjpatrolandroid.bean.TabEntity;
import com.cdqj.cdqjpatrolandroid.comstomview.dropdownmenu.SearchTabBean;
import com.cdqj.cdqjpatrolandroid.comstomview.dropdownmenu.SearchTabHelper;
import com.cdqj.cdqjpatrolandroid.comstomview.dropdownmenu.SelectSpinnerBean;
import com.cdqj.cdqjpatrolandroid.comstomview.recyclerview.SpacesItemDecoration;
import com.cdqj.cdqjpatrolandroid.config.GlobalConfig;
import com.cdqj.cdqjpatrolandroid.event.MsgEvent;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.adapter.DialogBtnAdapter;
import com.cdqj.cdqjpatrolandroid.adapter.PlanFormulateListAdapter;
import com.cdqj.cdqjpatrolandroid.base.BaseFragment;
import com.cdqj.cdqjpatrolandroid.bean.PatrolHdType;
import com.cdqj.cdqjpatrolandroid.bean.PlanListBean;
import com.cdqj.cdqjpatrolandroid.bean.UserCom;
import com.cdqj.cdqjpatrolandroid.comstomview.StateView;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.RetrofitUtils;
import com.cdqj.cdqjpatrolandroid.presenter.PlanFormulatePresenter;
import com.cdqj.cdqjpatrolandroid.utils.PatrolEnterPointActivity;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.view.i.IPlanFormulateView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ListHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by lyf on 2018/8/6 14:40
 *
 * @author lyf
 * desc：计划制定
 */
public class PlanFormulateListFragment extends BaseFragment<PlanFormulatePresenter> implements IPlanFormulateView {

    BaseRecyclerView planFormulateListRv;
    CoordinatorLayout fragmentPlanListXml;
    BaseRecyclerView searchTabRv;
    TextView btn;

    private ArrayList<SelectSpinnerBean> persons = new ArrayList<>(), times = new ArrayList<>(), areas = new ArrayList<>();
    private List<SearchTabBean> mList = new ArrayList<>();

    private List<PlanListBean> list;

    private DialogPlus mDialogPlus;

    private PlanFormulateListAdapter adapter;

    private String areaStr = "", personStr = "", startTime = "", endTime = "";

    private Realm jrealm;

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_fragment_plan_formulate_list;
    }

    @Override
    protected void findView(View rootView) {
        planFormulateListRv = rootView.findViewById(R.id.plan_formulate_list_rv);
        fragmentPlanListXml = rootView.findViewById(R.id.fragment_plan_list_xml);
        searchTabRv = rootView.findViewById(R.id.search_tab_rv);
        btn = rootView.findViewById(R.id.plan_formulate_list_btn);
    }

    @Override
    protected String getTitleText() {
        return null;
    }

    @Override
    protected PlanFormulatePresenter createPresenter() {
        return new PlanFormulatePresenter(this);
    }

    @Override
    protected void loadData() {
        initSelectView();
        mPresenter.getPlanFormulateList(page, rows, true, areaStr, startTime, endTime, personStr);
    }

    @Override
    public void initView(View rootView) {
        initView();
    }

    @Override
    public void initRefresh() {
        mPresenter.getPlanFormulateList(page, rows, false, areaStr, startTime, endTime, personStr);
    }

    @Override
    public void initLoadMore() {
        mPresenter.getPlanFormulateList(page, rows, false, areaStr, startTime, endTime, personStr);
    }

    /**
     * 界面初始化
     */
    @SuppressLint("InflateParams")
    private void initView() {
        mStateView = StateView.inject(planFormulateListRv);
        mStateView.setOnRetryClickListener(() -> {
            page = 1;
            mPresenter.getPlanFormulateList(page, rows, true, areaStr, startTime, endTime, personStr);
        });

        planFormulateListRv.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(10)));
        planFormulateListRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        list = new ArrayList<>();
        adapter = new PlanFormulateListAdapter(list, getActivity());
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.bindToRecyclerView(planFormulateListRv);
        adapter.setOnItemClickListener((adapter, view, position) -> PatrolEnterPointActivity.gotoPlanDetailActivity(getActivity(), (PlanListBean) adapter.getData().get(position), 2));
    }

    @Override
    public void initListener() {
        btn.setOnClickListener(this::setOnClick);
    }

    /**
     * 计划制定按钮/点击显示计划制定弹窗
     */
    public void setOnClick(View btn) {
        if (GlobalConfig.isDoWork) {
            if (ObjectUtils.isEmpty(mDialogPlus)) {
                List<TabEntity> entities = new ArrayList<>();
                entities.add(new TabEntity("计划任务", R.mipmap.icon_plan_task, 1));
                entities.add(new TabEntity("临时任务", R.mipmap.icon_gps_location, 1));
                DialogBtnAdapter adapter = new DialogBtnAdapter(getActivity(), entities);
                mDialogPlus = DialogPlus.newDialog(Objects.requireNonNull(getActivity()))
                        .setContentHolder(new ListHolder())
                        .setContentWidth(ScreenUtils.getScreenWidth() / 3 * 2)
                        .setPadding(ConvertUtils.dp2px(25), ConvertUtils.dp2px(20),
                                ConvertUtils.dp2px(25), ConvertUtils.dp2px(20))
                        .setGravity(Gravity.CENTER)
                        .setCancelable(true)
                        .setContentBackgroundResource(R.drawable.stroke_bg_radius)
                        .setAdapter(adapter)
                        .setInAnimation(R.anim.fade_in_center)
                        .setOutAnimation(R.anim.fade_out_center)
                        .setOnItemClickListener((dialog, item, view, position) -> {
                            // 计划制定监听
                            if (position == 0) {
                                // 计划任务
                                ToastBuilder.showShortWarning("抱歉！手机端暂不支持周期计划制定");
                            } else {
                                // 临时任务
                                PatrolEnterPointActivity.gotoPlanFormulateDetailActivity(getActivity(), position);
                            }
                            mDialogPlus.dismiss();
                        })
                        .create();
            }
            if (mDialogPlus.isShowing()) {
                mDialogPlus.dismiss();
            }
            mDialogPlus.show();
        } else {
            RetrofitUtils.doOnWork(getActivity(), 1);
        }
    }

    @Override
    public void onDestroyView() {
        if (ObjectUtils.isNotEmpty(jrealm)) {
            jrealm.close();
        }
        super.onDestroyView();
    }

    @Override
    public void onGetPlanFormulateListSuccess(BaseGetResponse<PlanListBean> body, boolean flag) {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataChange(MsgEvent msgEvent) {
        if (msgEvent.getTag() != 2 || !msgEvent.isRefresh()) {
            return;
        }
        page = 1;
        assert myInfoRefreshLayout != null;
        myInfoRefreshLayout.autoRefresh();
        mPresenter.getPlanFormulateList(page, rows, false, areaStr, startTime, endTime, personStr);
    }

    private void initSelectView() {
        if (mList.isEmpty()) {
            mList.add(new SearchTabBean(false, false, "片区", areas));
            mList.add(new SearchTabBean(true, true, "时间", times));
            mList.add(new SearchTabBean(true, true, "申请人", persons));
        }
        new SearchTabHelper(mList, searchTabRv)
                .initView(getActivity(), searchTabRv, fragmentPlanListXml, (parentPosition, names, values, isSinge) -> {
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

                    } else if (parentPosition == 2) {
                        personStr = values;

                    }
                    page = 1;
                    mPresenter.getPlanFormulateList(page, rows, false, areaStr, startTime, endTime, personStr);
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
