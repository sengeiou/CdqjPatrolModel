package com.cdqj.cdqjpatrolandroid.comstomview.dialog;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdqj.cdqjpatrolandroid.bean.GridSelectBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.cdqj.cdqjpatrolandroid.R;

import java.util.List;

public class DialogAdapter extends CommonAdapter<GridSelectBean> {

    private boolean isSingle;
    private int flag;

    public DialogAdapter(Context context, int layoutId, List<GridSelectBean> datas, boolean isSingle, int flag) {
        super(context, layoutId, datas);
        this.isSingle = isSingle;
        this.flag = flag;
    }

    public List<GridSelectBean> getData() {
        return mDatas;
    }

    public void setData(List<GridSelectBean> data) {
        this.mDatas = data;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, GridSelectBean bean, int position) {
        viewHolder.setText(R.id.dialog_item_name,bean.getTitle());
        if (flag == 3) {
            TextView textView = viewHolder.getView(R.id.dialog_item_true);
            textView.setText(bean.getValue());
        } else {
            ImageView imageView = viewHolder.getView(R.id.dialog_item_true);
            if (!isSingle) {
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageDrawable(ContextCompat.getDrawable(mContext,
                        bean.isSelectStatus()?R.mipmap.icon_select_dialog:R.mipmap.icon_select_dialog_to));
            } else {
                imageView.setVisibility(View.GONE);
            }
        }
        TextView textView = viewHolder.getView(R.id.dialog_item_line);
        if (position == mDatas.size() - 1) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
        }
    }
}
