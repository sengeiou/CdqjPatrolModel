package com.cdqj.cdqjpatrolandroid.view.ui.my;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ConvertUtils;
import com.cdqj.cdqjpatrolandroid.base.BaseRecyclerView;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmDialogListener;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmSelectDialog;
import com.cdqj.cdqjpatrolandroid.comstomview.recyclerview.SpacesItemDecoration;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.adapter.MsgListAdapter;
import com.cdqj.cdqjpatrolandroid.base.BaseActivity;
import com.cdqj.cdqjpatrolandroid.bean.MsgBean;
import com.cdqj.cdqjpatrolandroid.comstomview.StateView;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.presenter.MsgListPresenter;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.view.i.IMsgListView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 信息列表界面
 *
 * @author lyf
 * @date 2018年8月29日 09:59:39
 */
public class MsgListActivity extends BaseActivity<MsgListPresenter> implements IMsgListView {

    BaseRecyclerView msgListRv;

    private List<MsgBean> list = new ArrayList<>();
    private MsgListAdapter adapter;

    @Override
    public void initData() {
        mPresenter.getMsgList(page, rows, true);
    }

    @Override
    protected MsgListPresenter createPresenter() {
        return new MsgListPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_msg_list;
    }

    @Override
    protected void findView() {
        msgListRv = findViewById(R.id.msg_list_rv);
    }

    @Override
    protected String getTitleText() {
        return "消息";
    }

    @Override
    public void initRefresh() {
        mPresenter.getMsgList(page, rows, false);
    }

    @Override
    public void initLoadMore() {
        mPresenter.getMsgList(page, rows, false);
    }

    /**
     * 界面初始化
     */
    @SuppressLint("InflateParams")
    @Override
    public void initView() {
        mStateView = StateView.inject(msgListRv);
        mStateView.setOnRetryClickListener(() -> {
            page = 1;
            mPresenter.getMsgList(page, rows, true);
        });

        msgListRv.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(10)));
        msgListRv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MsgListAdapter(R.layout.cdqj_patrol_msg_item, list, this);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.bindToRecyclerView(msgListRv);
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (((MsgBean) adapter.getData().get(position)).getStatus() == 2) {
                new ConfirmSelectDialog(this)
                        .setContentStr("消息是否标记为已读？")
                        .setYesStr("是")
                        .setNoStr("否")
                        .setListener(new ConfirmDialogListener() {
                            @Override
                            public void onYesClick() {
                                // 消息状态 1已读 2未读 3删除
                                mPresenter.addUpdateMsg(((MsgBean) adapter.getData().get(position)).getId(), 1);
                            }

                            @Override
                            public void onNoClick() {

                            }
                        })
                        .initView().show();
            }
        });

    }

    @Override
    public void onGetMsgListSuccess(BaseGetResponse<MsgBean> body, boolean flag) {
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

    @Override
    public void onAddUpdateMsgSuccess(BasePostResponse<Object> body) {
        if (body.isSuccess()) {
            page = 1;
            mPresenter.getMsgList(page, rows, false);
        } else {
            hideProgress();
            ToastBuilder.showShortWarning(body.getMsg());
        }
    }

    private void setAdapter(List<MsgBean> list) {
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
        baseOnFailure(msg);
    }

}
