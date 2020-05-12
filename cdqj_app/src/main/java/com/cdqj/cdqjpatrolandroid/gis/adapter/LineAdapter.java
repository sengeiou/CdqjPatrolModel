package com.cdqj.cdqjpatrolandroid.gis.adapter;

import android.content.Context;
import android.view.View;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.bean.MapBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * Created by lyf on 2018/8/3 16:32
 *
 * @author lyf
 * desc：管线信息列表adapter
 */
public class LineAdapter extends BaseQuickAdapter<MapBean, GisViewHolder> {

    private List<MapBean> data;

    public LineAdapter(int layoutResId, List<MapBean> data) {
        super(layoutResId, data);
        this.data = data;
    }

    public LineAdapter(List<MapBean> data) {
        super(data);
    }

    public LineAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(GisViewHolder helper, MapBean item) {
        helper.getView(R.id.line_line).setVisibility(data.size() - 1 == helper.getAdapterPosition()? View.GONE:View.VISIBLE);
        helper.setText(R.id.line_title, StringUtils.isTrimEmpty(item.getKey())?"":item.getKey() + " :");
        helper.setText(R.id.line_text, ObjectUtils.isEmpty(item.getKey())?"":item.getValue().toString());
    }
}
