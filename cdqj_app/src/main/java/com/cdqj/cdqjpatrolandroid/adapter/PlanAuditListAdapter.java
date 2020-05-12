package com.cdqj.cdqjpatrolandroid.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.bean.PlanListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * Created by lyf on 2018/8/3 16:32
 *
 * @author lyf
 * desc：计划审核列表adapter
 */
public class PlanAuditListAdapter extends BaseQuickAdapter<PlanListBean, PatrolViewHolder> {

    private Context mContext;

    public PlanAuditListAdapter(int layoutResId, @Nullable List<PlanListBean> data, Context mContext) {
        super(layoutResId, data);
        this.mContext = mContext;
    }

    public PlanAuditListAdapter(@Nullable List<PlanListBean> data) {
        super(data);
    }

    public PlanAuditListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(PatrolViewHolder helper, PlanListBean item) {

        helper.getView(R.id.plan_type).setVisibility(View.INVISIBLE);
        if (!StringUtils.isTrimEmpty(item.getBeginTime()) && !StringUtils.isTrimEmpty(item.getEndTime())) {
            helper.setText(R.id.plan_time, item.getBeginTime().split(" ")[0]
                    + " - " + item.getEndTime().split(" ")[0]);
        } else {
            helper.setText(R.id.plan_time, R.string.null_text);
        }
        helper.setText(R.id.plan_area, (StringUtils.isTrimEmpty(item.getPlanName())?
                mContext.getString(R.string.null_text) : item.getPlanName()));
        helper.setText(R.id.plan_audit_item_report_people, (StringUtils.isTrimEmpty(item.getExecuteUsersExp())?
                mContext.getString(R.string.null_text) : item.getExecuteUsersExp()));
        StringBuilder strTask = new StringBuilder();
        if (item.getPressureDevice() != 0) {
            strTask.append("调压设备");
            strTask.append(item.getPressureDevice());
            strTask.append("个,");
        }
        if (item.getMiddlePipeA() != 0.0) {
            strTask.append("中压A管线");
            strTask.append(item.getMiddlePipeA());
            strTask.append("KM,");
        }
        if (item.getValveDevice() != 0) {
            strTask.append("阀门");
            strTask.append(item.getValveDevice());
            strTask.append("个,");
        }
        if (item.getCheckPoint() != 0) {
            strTask.append("巡检点");
            strTask.append(item.getCheckPoint());
            strTask.append("个,");
        }
        /*
         * 中压A管线 middlePipeA
         * 阀门 valveDevice
         * 调压设备  pressureDevice
         * 巡检点  checkPoint
         */
        helper.setText(R.id.plan_audit_item_report_time, strTask.length()>0?
                strTask.toString().substring(0, strTask.length()-1):mContext.getString(R.string.null_text));
        helper.addOnClickListener(R.id.plan_audit_item_submit)
                .addOnClickListener(R.id.plan_audit_item_submit_to);
    }
}
