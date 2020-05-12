package com.cdqj.cdqjpatrolandroid.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.comstomview.dropdownmenu.SelectSpinnerBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * Created by lyf on 2017/9/29.
 * 计划任务制定
 * @author lyf
 */
public class PlanTaskGridAdapter extends BaseQuickAdapter<SelectSpinnerBean, PatrolViewHolder>{

    private Context context;

    public PlanTaskGridAdapter(int layoutResId, @Nullable List<SelectSpinnerBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(PatrolViewHolder helper, SelectSpinnerBean item) {
        TextView textView = helper.getView(R.id.task_item_name);
        textView.setTextColor(ContextCompat.getColor(context,
                item.isSelect()?R.color.theme_one:R.color.text_gray_to));
        textView.setBackgroundResource(item.isSelect()?R.drawable.stroke_bg_radius_them_blue_4:R.drawable.stroke_bg_radius_them_4);
        helper.setText(R.id.task_item_name, item.getTitle());

    }

}
