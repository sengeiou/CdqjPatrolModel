package com.cdqj.cdqjpatrolandroid.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.bean.PersonLogBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * Created by lyf on 2018/8/3 16:38
 *
 * @author lyf
 * desc：人员上下班打卡记录Adapter
 */
public class MapPersonListAdapter extends BaseQuickAdapter<PersonLogBean, PatrolViewHolder> {

    private Context mContext;

    public MapPersonListAdapter(int layoutResId, @Nullable List<PersonLogBean> data, Context context) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(PatrolViewHolder helper, PersonLogBean item) {

        TextView status = helper.getView(R.id.map_person_item_title);
        status.setText(item.getStatus() == 1 ? "上班" : item.getStatus() == 2?"下班" : "异常");
        status.setTextColor(item.getStatus() == 1 ? ContextCompat.getColor(mContext ,R.color.theme_one) :
                item.getStatus() == 2?ContextCompat.getColor(mContext ,R.color.text_auxiliary) : ContextCompat.getColor(mContext ,R.color.red));

        helper.setText(R.id.map_person_item_address, StringUtils.isTrimEmpty(item.getRemarks()) ?
                mContext.getString(R.string.null_text) : item.getRemarks());
        helper.setText(R.id.map_person_item_update, StringUtils.isTrimEmpty(item.getAddTime()) ?
                mContext.getString(R.string.null_text) : (item.getAddTime() + "上报"));
        helper.addOnClickListener(R.id.map_person_item_see);
    }
}
