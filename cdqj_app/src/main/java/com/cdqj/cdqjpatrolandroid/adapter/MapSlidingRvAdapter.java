package com.cdqj.cdqjpatrolandroid.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.bean.GridSelectBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * Created by lyf on 2018/5/16 10:13
 *
 * @author lyf
 * desc：网格适配器(地图图侧滑专用) RecyclerView
 */
public class MapSlidingRvAdapter extends BaseQuickAdapter<GridSelectBean, PatrolViewHolder> {

    private Context context;

    public MapSlidingRvAdapter(int layoutResId, @Nullable List<GridSelectBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(PatrolViewHolder helper, GridSelectBean item) {
        RelativeLayout rl = helper.getView(R.id.gv_main_item_rl);
        TextView title = helper.getView(R.id.gv_main_item_text);
        ImageView iv = helper.getView(R.id.gv_main_item_img);
        title.setText(item.getTitle());
        iv.setImageResource(item.isSelectStatus()?
                item.getSelectedIcon():(item.getUnSelectedIcon()==0?item.getSelectedIcon():item.getUnSelectedIcon()));
        rl.setBackgroundResource(item.isSelectStatus()?R.drawable.map_sliding_item_select:R.drawable.map_sliding_item_unselect);
    }
}
