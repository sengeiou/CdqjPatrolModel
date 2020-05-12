package com.cdqj.cdqjpatrolandroid.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.image.glide.GlideImgManager;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.bean.UserLayerBean;
import com.cdqj.cdqjpatrolandroid.utils.StringUrlUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * Created by lyf on 2018/8/3 16:38
 *
 * @author lyf
 * desc：人员Adapter
 */
public class PersonListAdapter extends BaseQuickAdapter<UserLayerBean, PatrolViewHolder> {

    private Context mContext;

    public PersonListAdapter(int layoutResId, @Nullable List<UserLayerBean> data, Context context) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(PatrolViewHolder helper, UserLayerBean item) {
        GlideImgManager.loadImage(mContext, StringUrlUtil.transHttpUrlAndOnlyOne(item.getPhoto()), helper.getView(R.id.person_item_img));

        helper.setText(R.id.person_item_type, StringUtils.isTrimEmpty(item.getSysStaffName()) ?
                mContext.getString(R.string.null_text) : item.getSysStaffName());
        helper.setText(R.id.person_item_level, StringUtils.isTrimEmpty(item.getSysDeptname()) ?
                mContext.getString(R.string.null_text) : item.getSysDeptname());
        TextView status = helper.getView(R.id.person_item_status);
        status.setText(item.getStatus() == 1 ? "上班" : item.getStatus() == 2 ? "下班" : item.getStatus() == 3 ? "离线" : "越界");
        status.setTextColor(ContextCompat.getColor(mContext, item.getStatus() == 1 ? R.color.theme_one :
                item.getStatus() == 2 ? R.color.gray : item.getStatus() == 3 ? R.color.red : R.color.orange));
        helper.setText(R.id.person_item_address, StringUtils.isTrimEmpty(item.getGroupName()) ?
                mContext.getString(R.string.null_text) : item.getGroupName());
        helper.addOnClickListener(R.id.person_item_see);
    }
}
