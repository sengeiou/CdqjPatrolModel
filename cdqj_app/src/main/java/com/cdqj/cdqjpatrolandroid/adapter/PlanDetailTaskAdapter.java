package com.cdqj.cdqjpatrolandroid.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.bean.TaskBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;


/**
 * Created by lyf on 2018/8/3 16:32
 *
 * @author lyf
 * desc：计划详情任务执行情况adapter
 */
public class PlanDetailTaskAdapter extends BaseQuickAdapter<TaskBean, PatrolViewHolder> {

    /**
     * 是否显示进度
     */
    private boolean isShowCpt;

    public PlanDetailTaskAdapter(int layoutResId, @Nullable List<TaskBean> data, boolean isShowCpt) {
        super(layoutResId, data);
        this.isShowCpt = isShowCpt;
    }

    @Override
    protected void convert(PatrolViewHolder helper, TaskBean item) {
        /*
         * 中压A管线 middlePipeA
         * 阀门 valveDevice
         * 调压设备  pressureDevice
         * 巡检点  checkPoint
         */
        helper.setText(R.id.task_name, item.getName() + "(单位" +
                ("middlePipeA".equals(item.getCode())?"/M":
                        "valveDevice".equals(item.getCode())?"/个":
                                "pressureDevice".equals(item.getCode())?"/个":"/个") + ")");
        TextView current = helper.getView(R.id.task_current);
        TextView space = helper.getView(R.id.task_space);
        current.setVisibility(isShowCpt?View.VISIBLE:View.GONE);
        space.setVisibility(isShowCpt?View.VISIBLE:View.GONE);
        if ("middlePipeA".equals(item.getCode())) {
            current.setText(String.valueOf(item.getCptT()));
            helper.setText(R.id.task_total, String.valueOf(item.getCountT()));
        } else {
            current.setText(String.valueOf(item.getCpt()));
            helper.setText(R.id.task_total, String.valueOf(item.getCount()));
        }
    }
}
