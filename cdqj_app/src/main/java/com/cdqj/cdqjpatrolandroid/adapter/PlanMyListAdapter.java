package com.cdqj.cdqjpatrolandroid.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.base.BaseRecyclerView;
import com.cdqj.cdqjpatrolandroid.bean.PlanListBean;
import com.cdqj.cdqjpatrolandroid.bean.TaskBean;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;
import com.cdqj.cdqjpatrolandroid.R;


/**
 * Created by lyf on 2018/8/3 16:32
 *
 * @author lyf
 * desc：计划列表adapter
 */
public class PlanMyListAdapter extends BaseMultiItemQuickAdapter<PlanListBean, BaseViewHolder> {

    private Context context;

    /**
     * 是否可更新计划
     */
    private boolean isChange;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public PlanMyListAdapter(List<PlanListBean> data, Context context, boolean isChange) {
        super(data);
        addItemType(PlanListBean.START, R.layout.cdqj_patrol_plan_my_start_item);
        addItemType(PlanListBean.OTHER, R.layout.cdqj_patrol_plan_my_other_item);
        this.context = context;
        this.isChange = isChange;
    }

    @Override
    protected void convert(BaseViewHolder helper, PlanListBean item) {
        helper.getView(R.id.plan_type).setVisibility(View.INVISIBLE);
        // 如果是计划展示计划开始及结束时间
        if (!StringUtils.isTrimEmpty(item.getTaskPlanBeginDate()) && !StringUtils.isTrimEmpty(item.getTaskPlanEndDate())) {
            helper.setText(R.id.plan_time, item.getTaskPlanBeginDate().split(" ")[0]
                    + " - " + item.getTaskPlanEndDate().split(" ")[0]);
        } else {
            helper.setText(R.id.plan_time, R.string.null_text);
        }
        helper.setText(R.id.plan_area, (StringUtils.isTrimEmpty(item.getPlanName())?
                mContext.getString(R.string.null_text) : item.getPlanName()));

        helper.addOnClickListener(R.id.plan_submit)
                .addOnClickListener(R.id.plan_other_submit);

        switch (helper.getItemViewType()) {
            case PlanListBean.START:
                /*
                 * 中压A管线 middlePipeA
                 * 阀门 valveDevice
                 * 调压设备  pressureDevice
                 * 巡检点  checkPoint
                 */
                helper.getView(R.id.plan_submit).setVisibility(isChange?View.VISIBLE:View.INVISIBLE);
                List<TaskBean> taskBeans = new ArrayList<>();
                if (item.getPressureDevice() != 0) {
                    taskBeans.add(new TaskBean((int) (item.getCptpressureDevice()*100/item.getPressureDevice()), "调压设备"));
                }
                if (item.getMiddlePipeA() != 0.0) {
                    taskBeans.add(new TaskBean((int)(item.getCptmiddlePipeA()*100/item.getMiddlePipeA()), "中压A管线"));
                }
                if (item.getValveDevice() != 0) {
                    taskBeans.add(new TaskBean((int) (item.getCptvalveDevice()*100/item.getValveDevice()), "阀门"));
                }
                if (item.getCheckPoint() != 0) {
                    taskBeans.add(new TaskBean((int)(item.getCptcheckPoint()*100/item.getCheckPoint()), "巡检点"));
                }
                // 已经开始的布局需要渲染开始计划的各种任务进度
                PlanTaskProgressAdapter adapter = new PlanTaskProgressAdapter(R.layout.cdqj_patrol_plan_task_progress_item, taskBeans, mContext);
                BaseRecyclerView rv = helper.getView(R.id.rv_task_type);
                rv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false));
                adapter.bindToRecyclerView(rv);
                break;
            case PlanListBean.OTHER:
                // 未开始的布局支持执行开始并更替Item
                helper.getView(R.id.plan_other_submit).setVisibility(isChange?View.VISIBLE:View.INVISIBLE);
                break;
                default:break;
        }
    }
}
