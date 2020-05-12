package com.cdqj.cdqjpatrolandroid.comstomview.dropdownmenu;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.base.BaseMyViewHolder;
import com.cdqj.cdqjpatrolandroid.utils.DrawableUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * Created by lyf on 2018/10/24 14:35
 *
 * @author lyf
 * desc：查询tab adapter
 *
 */
public class SearchTabAdapter extends BaseQuickAdapter<SearchTabBean, BaseMyViewHolder> {

    private Context context;

    public SearchTabAdapter(int layoutResId,List<SearchTabBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseMyViewHolder helper, SearchTabBean item) {
        TextView textView = helper.getView(R.id.search_tab_item_tv);
        textView.setText(item.getTitle());
        textView.setTextColor(ContextCompat.getColor(context, item.isSelect() ? R.color.theme_one : R.color.black_pressed));
        DrawableUtils.setDrawable(context, textView, item.isSelect() ? R.mipmap.icon_last_down_select : R.mipmap.icon_last_down,
                3, 5);
    }
}
