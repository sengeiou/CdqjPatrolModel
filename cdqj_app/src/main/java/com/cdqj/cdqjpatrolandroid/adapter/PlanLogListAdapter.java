package com.cdqj.cdqjpatrolandroid.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.bean.PlanLogBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * Created by lyf on 2018/8/3 16:32
 *
 * @author lyf
 * desc：计划日志列表adapter
 */
public class PlanLogListAdapter extends BaseQuickAdapter<PlanLogBean, PatrolViewHolder> {

    private Context mContext;

    public PlanLogListAdapter(int layoutResId, @Nullable List<PlanLogBean> data, Context mContext) {
        super(layoutResId, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(PatrolViewHolder helper, PlanLogBean item) {
        helper.setText(R.id.log_item_send_user, StringUtils.isTrimEmpty(item.getAddUserExp())?
                mContext.getString(R.string.null_text):item.getAddUserExp());
        helper.setText(R.id.log_item_context, StringUtils.isTrimEmpty(item.getRemarks())?
                mContext.getString(R.string.null_text):item.getRemarks());
        helper.setText(R.id.log_item_see_log, StringUtils.isTrimEmpty(item.getAddTime())?
                mContext.getString(R.string.null_text):item.getAddTime());

        TextView view = helper.getView(R.id.log_item_status);
        view.setText(Integer.valueOf(item.getLogType()) == 1?"待审核":Integer.valueOf(item.getLogType()) == 2?"未开始":
                Integer.valueOf(item.getLogType()) == 3?"进行中":Integer.valueOf(item.getLogType()) == 4?"完成":Integer.valueOf(item.getLogType()) == 5?"驳回":
                        Integer.valueOf(item.getLogType()) == 5?"作废":mContext.getString(R.string.null_text));
        // 状态（1 待审核/2 未开始/3 进行中/4 完成/5 驳回/6 作废）
        view.setTextColor(ContextCompat.getColor(mContext, Integer.valueOf(item.getLogType()) == 1 || Integer.valueOf(item.getLogType()) == 2 ?
                R.color.text_auxiliary :Integer.valueOf(item.getLogType()) == 4? R.color.theme_one : Integer.valueOf(item.getLogType()) == 4 ? R.color.green:
                Integer.valueOf(item.getLogType()) == 5 ? R.color.red : R.color.orange));
    }
}
