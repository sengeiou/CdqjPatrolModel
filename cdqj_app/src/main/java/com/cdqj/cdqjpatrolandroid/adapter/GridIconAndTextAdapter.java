package com.cdqj.cdqjpatrolandroid.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdqj.cdqjpatrolandroid.bean.GridSelectBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;
import com.cdqj.cdqjpatrolandroid.R;

/**
 * Created by lyf on 2018/8/21 15:47
 *
 * @author lyf
 * desc：图标文字Adapter
 */
public class GridIconAndTextAdapter extends BaseQuickAdapter<GridSelectBean, PatrolViewHolder>  {

    private Context context;

    public GridIconAndTextAdapter(int layoutResId, @Nullable List<GridSelectBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(PatrolViewHolder helper, GridSelectBean item) {
        TextView title = helper.getView(R.id.gv_main_item_text);
        ImageView iv = helper.getView(R.id.gv_main_item_img);
        title.setText(item.getTitle());
        iv.setImageResource(item.isSelectStatus()?
                item.getSelectedIcon():(item.getUnSelectedIcon()==0?item.getSelectedIcon():item.getUnSelectedIcon()));
        title.setTextColor(ContextCompat.getColor(context,item.isSelectStatus()?R.color.theme_one:R.color.text_theme));
    }
}
