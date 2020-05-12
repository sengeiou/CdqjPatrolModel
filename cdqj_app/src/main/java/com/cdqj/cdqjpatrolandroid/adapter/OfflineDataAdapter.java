package com.cdqj.cdqjpatrolandroid.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cdqj.cdqjpatrolandroid.R;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * Created by lyf on 2018/8/3 16:32
 *
 * @author lyf
 * desc：计划审核列表adapter
 */
public class OfflineDataAdapter extends BaseQuickAdapter<Object, PatrolViewHolder> {

    private Context mContext;

    public OfflineDataAdapter(int layoutResId, @Nullable List<Object> data, Context mContext) {
        super(layoutResId, data);
        this.mContext = mContext;
    }

    public OfflineDataAdapter(@Nullable List<Object> data) {
        super(data);
    }

    public OfflineDataAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(PatrolViewHolder helper, Object item) {
        TextView tvUpdate = helper.getView(R.id.offline_data_update);
        TextView tvNumber = helper.getView(R.id.offline_data_progress_number);
        ProgressBar progressBar = helper.getView(R.id.offline_data_progress);
        if (helper.getAdapterPosition()%3==0 || helper.getAdapterPosition() % 2 == 0) {
            tvUpdate.setVisibility(View.VISIBLE);
            tvUpdate.setText("更新");
            progressBar.setProgress(0);
            tvNumber.setText("0%");
            tvUpdate.setBackgroundResource(R.drawable.stroke_bg_radius_order_item_theme);
            progressBar.setProgressDrawable(ContextCompat.getDrawable(mContext, R.drawable.stroke_bg_radius_progress_them));
        } else {
            tvNumber.setText("100%");
            progressBar.setProgress(100);
            tvUpdate.setText("完成");
            tvUpdate.setBackgroundResource(R.drawable.stroke_bg_radius_order_item_green_to);
            progressBar.setProgressDrawable(ContextCompat.getDrawable(mContext, R.drawable.stroke_bg_radius_progress_green));
        }
        helper.addOnClickListener(R.id.offline_data_update);
    }
}
