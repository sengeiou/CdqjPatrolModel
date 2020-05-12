package com.cdqj.cdqjpatrolandroid.view.ui.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.cdqj.cdqjpatrolandroid.base.BaseRecyclerView;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.adapter.PiesRvAdapter;
import com.cdqj.cdqjpatrolandroid.base.BaseActivity;
import com.cdqj.cdqjpatrolandroid.bean.PiesChildItem;
import com.cdqj.cdqjpatrolandroid.bean.PiesParentItem;
import com.cdqj.cdqjpatrolandroid.bean.UserCom;
import com.cdqj.cdqjpatrolandroid.comstomview.StateView;
import com.cdqj.cdqjpatrolandroid.presenter.PiesScreeningPresenter;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.view.i.IPiesScreenView;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 派单筛选人员界面
 */
public class PiesScreeningActivity extends BaseActivity<PiesScreeningPresenter> implements IPiesScreenView, TextWatcher {

    BaseRecyclerView piesRv;
    EditText piesSearchEt;
    TextView piesSearch;
    Button mainSearchEsc;
    Button mainSearchYes;

    PiesRvAdapter adapter;
    ArrayList<MultiItemEntity> list = new ArrayList<>();
    ArrayList<MultiItemEntity> sxList = new ArrayList<>();

    PiesChildItem singLeResult = null;
    List<PiesChildItem> muitResult = null;

    /**
     * 1 工单派单人员选择 patrol/permission/groupUser
     * 2 临时计划制定派发人员 patrol/permission/groupUserTreeByAreaId
     */
    private int flag;

    @SuppressLint("InflateParams")
    @Override
    public void initView() {
        mStateView = StateView.inject(piesRv);
        mStateView.setOnRetryClickListener(() -> mPresenter.getGroupUser(flag == 1 ? "groupUser" : "groupUserTreeByAreaId"));
        flag = getIntent().getIntExtra("flag", 0);

        mainSearchYes.setOnClickListener(v -> {
            if (singLeResult == null && muitResult == null) {
                ToastBuilder.showShortError("请选择人员");
                return;
            }
            if (singLeResult != null) {
                setResult(RESULT_OK, new Intent().putExtra("single", singLeResult));
            }
            if (muitResult != null) {
                setResult(RESULT_OK, new Intent().putParcelableArrayListExtra("muitResult", (ArrayList<? extends Parcelable>) muitResult));
            }
            finish();
        });
        mainSearchEsc.setOnClickListener(v -> finish());
        mPresenter.getGroupUser(flag == 1 ? "groupUser" : "groupUserTreeByAreaId");
        adapter = new PiesRvAdapter(list);
        adapter.setSingle(getIntent().getBooleanExtra("isSingle", true));
        adapter.setListener(new PiesRvAdapter.onSelectResultListener() {
            @Override
            public void onSingleResult(PiesChildItem entity) {
                singLeResult = entity;
                LogUtils.e(entity.toString());
            }

            @Override
            public void onMuitResult(List<PiesChildItem> data) {
                muitResult = data;
                LogUtils.e(data.toString());
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this);
        piesRv.setLayoutManager(manager);
        piesRv.setAdapter(adapter);
        piesSearchEt.addTextChangedListener(this);
    }

    @Override
    protected PiesScreeningPresenter createPresenter() {
        return new PiesScreeningPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_pies_screening;
    }

    @Override
    protected void findView() {
        piesRv = findViewById(R.id.pies_rv);
        piesSearchEt = findViewById(R.id.pies_search_et);
        piesSearch = findViewById(R.id.pies_search);
        mainSearchEsc = findViewById(R.id.main_search_esc);
        mainSearchYes = findViewById(R.id.main_search_yes);
    }

    @Override
    protected String getTitleText() {
        return null;
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
    public void getGroupUser(ArrayList<UserCom> userComs) {
        for (int i = 0; i < userComs.size(); i++) {
            UserCom userCom = userComs.get(i);
            PiesParentItem lv0 = new PiesParentItem();
            lv0.setId(userCom.getId());
            lv0.setIndex(i);
            lv0.setPid(userCom.getPid());
            lv0.setState(userCom.getState());
            lv0.setText(userCom.getText());
            for (int k = 0; k < userCom.getChildren().size(); k++) {
                UserCom.ChildrenBean childrenBean = userCom.getChildren().get(k);
                PiesChildItem lv1 = new PiesChildItem();
                lv1.setId(childrenBean.getId());
                lv1.setIndex(k);
                lv1.setParentIndex(i);
                lv1.setPid(childrenBean.getPid());
                lv1.setState(childrenBean.getState());
                lv1.setText(childrenBean.getText());
                lv0.addSubItem(lv1);
            }
            list.add(lv0);
        }
        adapter.notifyDataSetChanged();
        adapter.expandAll();
        if (adapter.getData().size() == 0) {
            mStateView.showEmpty();
        } else {
            mStateView.showContent();
        }
    }

    @Override
    public void initListener() {
        findViewById(R.id.pies_search).setOnClickListener(this::onViewClicked);
    }

    public void onViewClicked(View view) {
        String trim = piesSearchEt.getText().toString().trim();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getItemType() == PiesRvAdapter.TYPE_LEVEL_1) {
                PiesChildItem multiItemEntity = (PiesChildItem) list.get(i);
                if (multiItemEntity.getText().contains(trim)) {
                    piesRv.scrollToPosition(i);
                    return;
                }
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        sxList.clear();
        String trim = s.toString().trim();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getItemType() == PiesRvAdapter.TYPE_LEVEL_1) {
                PiesChildItem multiItemEntity = (PiesChildItem) list.get(i);
                if (multiItemEntity.getText().contains(trim)) {
                    //multiItemEntity.setSelect(false);
                    sxList.add(multiItemEntity);
                }
            } else {
                sxList.add(list.get(i));
            }
        }
        adapter.setNewData(sxList);
        if (adapter.getData().size() == 0) {
            mStateView.showEmpty();
        } else {
            mStateView.showContent();
        }
    }
}
