package com.cdqj.cdqjpatrolandroid.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.bean.TaskBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;


/**
 * Created by lyf on 2018/8/3 16:32
 *
 * @author lyf
 * desc：计划列表进度adapter
 */
public class PlanTaskProgressAdapter  extends BaseQuickAdapter<TaskBean, PatrolViewHolder> {

    private Context mContext;

    public PlanTaskProgressAdapter(int layoutResId, @Nullable List<TaskBean> data, Context mContext) {
        super(layoutResId, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(PatrolViewHolder helper, TaskBean item) {
        TextView progress = helper.getView(R.id.task_progress);
        TextView progressSymbol = helper.getView(R.id.task_progress_symbol);
        progress.setText(String.valueOf(item.getProgress()));
        if (item.getProgress() <= 33) {
            progress.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            progressSymbol.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        } else if (item.getProgress() <= 66) {
            progress.setTextColor(ContextCompat.getColor(mContext, R.color.orange));
            progressSymbol.setTextColor(ContextCompat.getColor(mContext, R.color.orange));
        } else if (item.getProgress() <= 99) {
            progress.setTextColor(ContextCompat.getColor(mContext, R.color.theme_one));
            progressSymbol.setTextColor(ContextCompat.getColor(mContext, R.color.theme_one));
        } else {
            progress.setTextColor(ContextCompat.getColor(mContext, R.color.green));
            progressSymbol.setTextColor(ContextCompat.getColor(mContext, R.color.green));
        }
        helper.setText(R.id.task_progress_name, StringUtils.isTrimEmpty(item.getName())?
        "进度：" + item.getCpt() + "/" + item.getCount():item.getName());
    }
}
