package com.cdqj.cdqjpatrolandroid.adapter;

import android.support.annotation.Nullable;

import com.allen.library.SuperTextView;
import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.bean.SuperTextViewBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * Created by lyf on 2018/8/3 16:38
 *
 * @author lyf
 * desc：SuperTextView Adapter，适用于列表快速构建
 */
public class SuperListAdapter extends BaseQuickAdapter<SuperTextViewBean, PatrolViewHolder> {

    public SuperListAdapter(@Nullable List<SuperTextViewBean> data) {
        super(R.layout.cdqj_patrol_super_text_view_item, data);
    }

    @Override
    protected void convert(PatrolViewHolder helper, SuperTextViewBean item) {
        SuperTextView view = helper.getView(R.id.super_text_view_item);
        view.setLeftString(item.getLeftCenter());
        view.setRightString(StringUtils.isEmpty(item.getRightCenter()) ? mContext.getString(R.string.null_text) : item.getRightCenter());
    }
}
