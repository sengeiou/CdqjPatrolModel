package com.cdqj.cdqjpatrolandroid.view.ui.map;

import android.annotation.SuppressLint;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.blankj.utilcode.util.ConvertUtils;
import com.cdqj.cdqjpatrolandroid.base.BaseRecyclerView;
import com.cdqj.cdqjpatrolandroid.comstomview.dropdownmenu.SearchTabBean;
import com.cdqj.cdqjpatrolandroid.comstomview.dropdownmenu.SearchTabHelper;
import com.cdqj.cdqjpatrolandroid.comstomview.dropdownmenu.SelectSpinnerBean;
import com.cdqj.cdqjpatrolandroid.comstomview.recyclerview.SpacesItemDecoration;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.adapter.PersonListAdapter;
import com.cdqj.cdqjpatrolandroid.base.BaseActivity;
import com.cdqj.cdqjpatrolandroid.bean.UserLayerBean;
import com.cdqj.cdqjpatrolandroid.comstomview.StateView;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.presenter.PersonListPresenter;
import com.cdqj.cdqjpatrolandroid.utils.PatrolEnterPointActivity;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.view.i.IPersonListView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 人员列表
 *
 * @author lyf
 * @date 2018年10月22日 10:27:57
 */
public class PersonListActivity extends BaseActivity<PersonListPresenter> implements IPersonListView {

    BaseRecyclerView personListListRv;
    ConstraintLayout personListXml;
    BaseRecyclerView searchTabRv;

    public ArrayList<SelectSpinnerBean> types = new ArrayList<>();
    public ArrayList<SelectSpinnerBean> levels = new ArrayList<>();
    public ArrayList<SelectSpinnerBean> areas = new ArrayList<>();

    private PersonListAdapter adapter;

    @Override
    public void initRefresh() {
        mPresenter.getPersonList(page, rows, false);
    }

    @Override
    public void initLoadMore() {
        mPresenter.getPersonList(page, rows, false);
    }

    /**
     * 初始化界面
     */
    @SuppressLint({"InflateParams"})
    @Override
    public void initView() {
        mStateView = StateView.inject(personListListRv);
        mStateView.setOnRetryClickListener(() -> {
            page = 1;
            mPresenter.getPersonList(page, rows, true);
        });
        personListListRv.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(10)));
        personListListRv.setLayoutManager(new LinearLayoutManager(this));

        // TODO
        searchTabRv.setVisibility(View.GONE);
        types = new ArrayList<>();
        levels = new ArrayList<>();
        areas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            areas.add(new SelectSpinnerBean("片区--" + i));
            types.add(new SelectSpinnerBean("班组--" + i));
            levels.add(new SelectSpinnerBean("状态--" + i));
        }
        List<SearchTabBean> mList = new ArrayList<>();
        mList.add(new SearchTabBean(false, false, "片区", areas));
        mList.add(new SearchTabBean(false, false, "班组", types));
        mList.add(new SearchTabBean(true, true, "状态", levels));
        new SearchTabHelper(mList, searchTabRv)
                .initView(this, searchTabRv, personListXml, (int point, String names, String values, boolean isSinge) -> ToastBuilder.showShort(names));

        adapter = new PersonListAdapter(R.layout.cdqj_patrol_person_list_item, new ArrayList<>(), this);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.bindToRecyclerView(personListListRv);
        adapter.setOnItemClickListener((adapter, view, position) -> PatrolEnterPointActivity.gotoMapPersonDetailActivity(this, (UserLayerBean) (adapter.getData().get(position))));
        adapter.setOnItemChildClickListener((adapter, view, position) -> PatrolEnterPointActivity.gotoPersonTrajectoryMapActivity(this, ((UserLayerBean) Objects.requireNonNull(adapter.getItem(position))).getSysUserid()));

        mPresenter.getPersonList(page, rows, true);
    }

    @Override
    protected PersonListPresenter createPresenter() {
        return new PersonListPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_person_list;
    }

    @Override
    protected void findView() {
        personListListRv = findViewById(R.id.person_list_list_rv);
        personListXml = findViewById(R.id.person_list_xml);
        searchTabRv = findViewById(R.id.search_tab_rv);
    }

    @Override
    protected String getTitleText() {
        return "人员列表";
    }

    @Override
    public void onGetPersonListSuccess(BaseGetResponse<UserLayerBean> body, boolean flag) {
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

    private void setAdapter(List<UserLayerBean> list) {
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
}
