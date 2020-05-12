package com.cdqj.cdqjpatrolandroid.adapter;

import android.support.annotation.Nullable;

import com.cdqj.cdqjpatrolandroid.base.BaseMyViewHolder;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.bean.MainGridBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * Created by lyf on 2018/11/15 11:51
 *
 * @author lyf
 * desc：
 */
public class MainAdapter extends BaseQuickAdapter<MainGridBean, BaseMyViewHolder> {

    public MainAdapter(int layoutResId, @Nullable List<MainGridBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseMyViewHolder helper, MainGridBean item) {
        helper.setText(R.id.item_name, item.getName());
        helper.setImageResource(R.id.item_img, item.getUrlResId());
    }
}
