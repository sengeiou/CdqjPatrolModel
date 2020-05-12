package com.cdqj.cdqjpatrolandroid.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.image.glide.GlideImgManager;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.bean.SiteBean;
import com.cdqj.cdqjpatrolandroid.utils.StringUrlUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * Created by lyf on 2018/8/3 16:38
 *
 * @author lyf
 * desc：工地Adapter
 */
public class SiteListAdapter extends BaseQuickAdapter<SiteBean, PatrolViewHolder> {

    private Context mContext;

    public SiteListAdapter(int layoutResId, @Nullable List<SiteBean> data, Context context) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(PatrolViewHolder helper, SiteBean item) {
        GlideImgManager.loadRoundCornerImage(mContext, StringUrlUtil.transHttpUrlAndOnlyOne(item.getPicture()), helper.getView(R.id.site_item_img), 5);

        helper.setText(R.id.site_item_type, StringUtils.isTrimEmpty(item.getName()) ?
                mContext.getString(R.string.null_text) : item.getName());
        helper.setText(R.id.site_item_level, StringUtils.isTrimEmpty(item.getSiteLevelExp()) ?
                mContext.getString(R.string.null_text) : item.getSiteLevelExp());
        helper.setText(R.id.site_item_address, StringUtils.isTrimEmpty(item.getAddress()) ?
                mContext.getString(R.string.null_text) : item.getAddress());
    }
}
