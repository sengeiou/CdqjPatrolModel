package com.cdqj.cdqjpatrolandroid.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.base.BaseRecyclerView;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.bean.PlanListBean;
import com.cdqj.cdqjpatrolandroid.bean.TaskBean;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lyf on 2018/8/3 16:32
 *
 * @author lyf
 * desc：制定计划列表adapter
 */
public class PlanFormulateListAdapter extends BaseMultiItemQuickAdapter<PlanListBean, BaseViewHolder> {

    private Context context;
    /**
     * 任务|计划标识
     */
    private boolean isTask;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public PlanFormulateListAdapter(List<PlanListBean> data, Context context) {
        super(data);
        addItemType(PlanListBean.START, R.layout.cdqj_patrol_plan_my_start_item);
        addItemType(PlanListBean.OTHER, R.layout.cdqj_patrol_plan_my_other_item);
        this.context = context;
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public PlanFormulateListAdapter(List<PlanListBean> data, Context context, boolean isTask) {
        super(data);
        addItemType(PlanListBean.START, R.layout.cdqj_patrol_plan_my_start_item);
        addItemType(PlanListBean.OTHER, R.layout.cdqj_patrol_plan_my_other_item);
        this.context = context;
        this.isTask = isTask;
    }

    @Override
    protected void convert(BaseViewHolder helper, PlanListBean item) {
        helper.getView(R.id.plan_type).setVisibility(View.INVISIBLE);
        if (isTask) {
            // 如果是计划展示计划开始及结束时间
            if (!StringUtils.isTrimEmpty(item.getTaskPlanBeginDate()) && !StringUtils.isTrimEmpty(item.getTaskPlanEndDate())) {
                helper.setText(R.id.plan_time, item.getTaskPlanBeginDate().split(" ")[0]
                        + " - " + item.getTaskPlanEndDate().split(" ")[0]);
            } else {
                helper.setText(R.id.plan_time, R.string.null_text);
            }
        } else {
            if (!StringUtils.isTrimEmpty(item.getBeginTime()) && !StringUtils.isTrimEmpty(item.getEndTime())) {
                helper.setText(R.id.plan_time, item.getBeginTime().split(" ")[0]
                        + " - " + item.getEndTime().split(" ")[0]);
            } else {
                helper.setText(R.id.plan_time, R.string.null_text);
            }
        }
        helper.setText(R.id.plan_area, (StringUtils.isTrimEmpty(item.getPlanName())?
                mContext.getString(R.string.null_text) : item.getPlanName()));

        switch (helper.getItemViewType()) {
            case PlanListBean.START:
                helper.getView(R.id.plan_submit).setVisibility(View.INVISIBLE);
                /*
                 * 任务总数 taskCount
                 */
                List<TaskBean> taskBeans = new ArrayList<>();
                if (item.getTaskCount() != 0) {
                    taskBeans.add(new TaskBean(item.getCptTaskcount()*100/item.getTaskCount(), item.getTaskCount() , item.getCptTaskcount()));
                }
                // 已经开始的布局需要渲染开始计划的各种任务进度
                PlanTaskProgressAdapter adapter = new PlanTaskProgressAdapter(R.layout.cdqj_patrol_plan_task_progress_item, taskBeans, mContext);
                BaseRecyclerView rv = helper.getView(R.id.rv_task_type);
                rv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false));
                adapter.bindToRecyclerView(rv);
                break;
            case PlanListBean.OTHER:
                helper.getView(R.id.plan_other_submit).setVisibility(View.INVISIBLE);
                helper.getView(R.id.plan_other_status).setVisibility(View.VISIBLE);
                TextView view = helper.getView(R.id.plan_other_status);
                view.setText(item.getStatusExp());
                // 状态（1待审核/2未开始/3进行中/4完成/5驳回/6作废）
                view.setTextColor(ContextCompat.getColor(mContext, item.getStatus() == 1 || item.getStatus() == 2 ?
                        R.color.text_auxiliary :item.getStatus() == 4? R.color.theme_one : item.getStatus() == 4 ? R.color.green:
                        item.getStatus() == 5 ? R.color.red : R.color.orange));
                break;
                default:break;
        }
    }
}
