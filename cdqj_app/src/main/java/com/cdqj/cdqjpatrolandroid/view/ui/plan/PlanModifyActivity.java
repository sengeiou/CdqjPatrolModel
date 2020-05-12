package com.cdqj.cdqjpatrolandroid.view.ui.plan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.cdqj.cdqjpatrolandroid.base.BaseRecyclerView;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmDialogListener;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmSelectDialog;
import com.cdqj.cdqjpatrolandroid.comstomview.dropdownmenu.SelectSpinnerBean;
import com.cdqj.cdqjpatrolandroid.comstomview.recyclerview.SpacesItemDecoration;
import com.cdqj.cdqjpatrolandroid.event.MsgEvent;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.adapter.PlanTaskGridAdapter;
import com.cdqj.cdqjpatrolandroid.base.BaseActivity;
import com.cdqj.cdqjpatrolandroid.bean.PiesChildItem;
import com.cdqj.cdqjpatrolandroid.bean.PlanListBean;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.presenter.PlanModifyPresenter;
import com.cdqj.cdqjpatrolandroid.utils.PatrolEnterPointActivity;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.view.i.IPlanModifyView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 计划修改
 *
 * @author lyf
 * 时间 2018年10月15日 14:37:28
 */
public class PlanModifyActivity extends BaseActivity<PlanModifyPresenter> implements IPlanModifyView {

    TextView planModifyArea;
    TextView planModifyDistance;
    TextView planModifyAddress;
    TextView planModifyPeople;
    TextView planModifyStartTime;
    TextView planModifyEndTime;
    BaseRecyclerView planModifyTask;

    private PlanListBean mBean;

    private List<SelectSpinnerBean> list;

    private TimePickerView pvTime;

    private StringBuilder receiveUserIds;

    private String groupId = "0";

    private PlanTaskGridAdapter adapter;

    /**
     * 界面初始化
     */
    @Override
    public void initView() {
        assert titleToolbar != null;
        titleToolbar.setLeftTitleClickListener(v -> back());
        mBean = getIntent().getParcelableExtra("mBean");
        if (ObjectUtils.isNotEmpty(mBean)) {
            planModifyArea.setText(StringUtils.isTrimEmpty(mBean.getPlanName()) ?
                    getString(R.string.null_text) : mBean.getPlanName());
            planModifyDistance.setText(StringUtils.isTrimEmpty(mBean.getAddUserExp()) ?
                    getString(R.string.null_text) : mBean.getAddUserExp());
            planModifyAddress.setText(StringUtils.isTrimEmpty(mBean.getPlanTypeExp()) ?
                    getString(R.string.null_text) : mBean.getPlanTypeExp());
            planModifyPeople.setText(StringUtils.isTrimEmpty(mBean.getExecuteUsersExp()) ?
                    getString(R.string.null_text) : mBean.getExecuteUsersExp());
            planModifyStartTime.setText(StringUtils.isTrimEmpty(mBean.getBeginTime()) ?
                    getString(R.string.null_text) : mBean.getBeginTime());
            planModifyEndTime.setText(StringUtils.isTrimEmpty(mBean.getEndTime()) ?
                    getString(R.string.null_text) : mBean.getEndTime());

            list = new ArrayList<>();
            // 设置任务
            list.add(new SelectSpinnerBean("中压A管线\n(" + mBean.getMiddlePipeA() + "米)", "middlePipeA"));
            list.add(new SelectSpinnerBean("阀门\n(" + mBean.getValveDevice() + "个)", "valveDevice"));
            list.add(new SelectSpinnerBean("调压设备\n(" + mBean.getPressureDevice() + "个)", "pressureDevice"));
            list.add(new SelectSpinnerBean("巡检点\n(" + mBean.getCheckPoint() + "个)", "checkPoint"));
            if (!StringUtils.isTrimEmpty(mBean.getCheckTypes())) {
                String[] tasks = mBean.getCheckTypes().split(",");
                for (String task : tasks) {
                    if ("middlePipeA".equals(task)) {
                        list.get(0).setSelect(true);
                    } else if ("valveDevice".equals(task)) {
                        list.get(1).setSelect(true);
                    } else if ("pressureDevice".equals(task)) {
                        list.get(2).setSelect(true);
                    } else if ("checkPoint".equals(task)) {
                        list.get(3).setSelect(true);
                    }
                }
            }
            planModifyTask.setLayoutManager(new GridLayoutManager(this, 3));
            planModifyTask.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(4)));
            adapter = new PlanTaskGridAdapter(R.layout.cdqj_patrol_plan_task_grid_item_layout, list, this);
            adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
            adapter.bindToRecyclerView(planModifyTask);
            adapter.setOnItemClickListener((adapter1, view, position) -> {
                list.get(position).setSelect(!list.get(position).isSelect());
                adapter1.notifyDataSetChanged();
            });
        } else {
            ToastBuilder.showShortError("计划详情获取失败");
        }

        initTimePicker();
    }

    @Override
    protected PlanModifyPresenter createPresenter() {
        return new PlanModifyPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_plan_modify;
    }

    @Override
    protected void findView() {
        planModifyArea = findViewById(R.id.plan_modify_area);
        planModifyDistance = findViewById(R.id.plan_modify_distance);
        planModifyAddress = findViewById(R.id.plan_modify_address);
        planModifyPeople = findViewById(R.id.plan_modify_people);
        planModifyStartTime = findViewById(R.id.plan_modify_start_time);
        planModifyEndTime = findViewById(R.id.plan_modify_end_time);
        planModifyTask = findViewById(R.id.plan_modify_task);
    }

    @Override
    protected String getTitleText() {
        return "计划修改";
    }

    @Override
    public void initListener() {
        findViewById(R.id.plan_modify_submit).setOnClickListener(this::setClick);
        findViewById(R.id.plan_modify_esc).setOnClickListener(this::setClick);
        findViewById(R.id.plan_modify_people).setOnClickListener(this::setClick);
        findViewById(R.id.plan_modify_start_time).setOnClickListener(this::setClick);
        findViewById(R.id.plan_modify_end_time).setOnClickListener(this::setClick);
    }

    public void setClick(View view) {
        int i1 = view.getId();
        if (i1 == R.id.plan_modify_submit) {
            if (StringUtils.isTrimEmpty(planModifyPeople.getText().toString())) {
                ToastBuilder.showShortWarning("请选择执行人");
                return;
            }
            if (StringUtils.isTrimEmpty(planModifyStartTime.getText().toString())) {
                ToastBuilder.showShortWarning("请选择开始时间");
                return;
            }
            if (StringUtils.isTrimEmpty(planModifyEndTime.getText().toString())) {
                ToastBuilder.showShortWarning("请选择结束时间");
                return;
            }
            StringBuilder exTask = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isSelect()) {
                    exTask.append(list.get(i).getValue());
                    exTask.append(",");
                }
            }
            if (StringUtils.isTrimEmpty(exTask.toString())) {
                ToastBuilder.showShortWarning("请选择执行的任务");
                return;
            }
            // 添加执行人
            if (!"0".equals(groupId)) {
                mBean.setGroupId(Long.valueOf(groupId));
            }
            if (ObjectUtils.isNotEmpty(receiveUserIds)) {
                mBean.setExecuteUsers(receiveUserIds.toString());
            }
            mBean.setBeginTime(planModifyStartTime.getText().toString() + " 00:00:00");
            mBean.setEndTime(planModifyEndTime.getText().toString() + " 00:00:00");
            mBean.setCheckTypes(exTask.toString().substring(0, exTask.length() - 1));
            new ConfirmSelectDialog(this)
                    .setTitleStr("提示")
                    .setContentStr("是否执行计划修改")
                    .setYesStr("确定")
                    .setNoStr("取消")
                    .setListener(new ConfirmDialogListener() {
                        @Override
                        public void onYesClick() {
                            // 修改
                            mPresenter.subPlanFormulate(mBean);
                        }

                        @Override
                        public void onNoClick() {
                        }
                    })
                    .initView().show();

        } else if (i1 == R.id.plan_modify_esc) {
            back();
        } else if (i1 == R.id.plan_modify_people) {// 人员选择界面
            PatrolEnterPointActivity.gotoPiesScreeningActivityForResult(this, 2, false, 1);
        } else if (i1 == R.id.plan_modify_start_time) {// 开始时间
            if (ObjectUtils.isNotEmpty(pvTime) && !pvTime.isShowing()) {
                pvTime.show(view);
            }
        } else if (i1 == R.id.plan_modify_end_time) {// 结束时间
            if (ObjectUtils.isNotEmpty(pvTime) && !pvTime.isShowing()) {
                pvTime.show(view);
            }
        }
    }

    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        new ConfirmSelectDialog(this)
                .setTitleStr("提示")
                .setContentStr("是否放弃修改直接退出")
                .setYesStr("确定")
                .setNoStr("取消")
                .setListener(new ConfirmDialogListener() {
                    @Override
                    public void onYesClick() {
                        finish();
                    }

                    @Override
                    public void onNoClick() {
                    }
                })
                .initView().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == 1) {
            ArrayList<PiesChildItem> muitResult = data.getParcelableArrayListExtra("muitResult");
            if (muitResult.isEmpty()) {
                return;
            }
            receiveUserIds = new StringBuilder();
            StringBuilder receiveUsers = new StringBuilder();
            if (ObjectUtils.isNotEmpty(muitResult.get(0))) {
                groupId = muitResult.get(0).getPid();
            }
            for (int i = 0; i < muitResult.size(); i++) {
                receiveUserIds.append(muitResult.get(i).getId());
                receiveUsers.append(muitResult.get(i).getText());
                if (i != muitResult.size() - 1) {
                    receiveUserIds.append(",");
                    receiveUsers.append("、");
                }
            }
            planModifyPeople.setText(receiveUsers.toString());
        }
    }

    /**
     * Dialog 模式下，在底部弹出时间选择
     */
    @SuppressLint("SimpleDateFormat")
    private void initTimePicker() {

        pvTime = new TimePickerBuilder(this, (date, v) -> {
            int i = v.getId();
            if (i == R.id.plan_modify_start_time) {
                planModifyStartTime.setText(TimeUtils.date2String(date, new SimpleDateFormat("yyyy-MM-dd")));
            } else if (i == R.id.plan_modify_end_time) {
                planModifyEndTime.setText(TimeUtils.date2String(date, new SimpleDateFormat("yyyy-MM-dd")));
            }
        })
                .setTimeSelectChangeListener(date -> LogUtils.d("onTimeSelectChanged"))
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true)
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                //修改动画样式
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);
                //改成Bottom,底部显示
                dialogWindow.setGravity(Gravity.BOTTOM);
            }
        }
    }

    @Override
    public void onSubPlanModifySuccess(BasePostResponse<Object> body) {
        if (body.isSuccess()) {
            // 修改成功,发送广播让其他界面更新ui
            ToastBuilder.showShort(body.getMsg());
            EventBus.getDefault().post(new MsgEvent<>(2, true));
            finish();
        } else {
            ToastBuilder.showShortError(body.getMsg());
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
        baseOnFailure(msg);
    }
}
